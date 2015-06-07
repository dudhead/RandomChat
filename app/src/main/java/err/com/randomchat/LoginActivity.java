package err.com.randomchat;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import err.com.randomchat.gcm.RegistrationIntentService;


public class LoginActivity extends Activity {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";


    //facebook callbacks manager
    private CallbackManager cm;
    private LoginButton mFbLoginButton;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView mInformationTextView;

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this.getApplicationContext());


       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "err.com.randomchat",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //init facebook sdk and
        mInformationTextView = (TextView) findViewById(R.id.username);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
                public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER.name(), false);
                if (sentToken) {
                    mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
                    mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        //instantiate callbacks manager
        cm = CallbackManager.Factory.create();


        mFbLoginButton = (LoginButton) findViewById(R.id.login_button);

        //set permissions
        List<String> permissionNeeds = Arrays.asList("email", "user_birthday");

        mFbLoginButton.setReadPermissions(permissionNeeds);

        mFbLoginButton.registerCallback(cm, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.e("TOKEN", loginResult.getAccessToken().getToken());

                mProfileTracker=new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        //Profile.setCurrentProfile(currentProfile);
                        Log.e("MY_NAME", currentProfile.getFirstName());
                        this.stopTracking();
                    }
                };
                mProfileTracker.startTracking();
                Log.e("MY_NAME", Profile.getCurrentProfile().getFirstName());
            }

            @Override
            public void onCancel() {
                // App code
                Log.e("SOS", "facebook login canceled");

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("SOS","facebook login failed error");
            }
        });
        // register callback
        //means hey facebook after login call onActivityResult of **this**
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE.name()));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
