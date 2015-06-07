package err.com.randomchat;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;


public class LoginActivity extends Activity {


    //facebook callbacks manager
    private CallbackManager cm;
    private LoginButton mFbLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        try {
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

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //init facebook sdk and


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
}
