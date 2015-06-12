package err.com.randomchat;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class RegisterActivity extends Activity {

	Button btnGCMRegister;
	Button btnXmppRegiser;
	Button btnSendMessage;
	GoogleCloudMessaging gcm;
	Context context;
	String regId;
	AsyncTask<Void, Void, String> sendTask;
	AtomicInteger ccsMsgId = new AtomicInteger();

	static final String GOOGLE_PROJECT_ID = "497186030502";
	public static final String REG_ID = "regId";
	private static final String APP_VERSION = "appVersion";

	static final String TAG = "Register Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		context = getApplicationContext();

		btnGCMRegister = (Button) findViewById(R.id.btnGCMRegister);
		btnGCMRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(regId)) {
					regId = registerGCM();
					Log.d("RegisterActivity", "GCM RegId: " + regId);
				} else {
					Toast.makeText(getApplicationContext(),
							"Already Registered with GCM Server!",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btnXmppRegiser = (Button) findViewById(R.id.btnXmppRegiser);
		btnXmppRegiser.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(regId)) {
					Toast.makeText(getApplicationContext(), "RegId is empty!",
							Toast.LENGTH_LONG).show();
				} else {

					sendMessage("REGISTER");
				}
			}
		});

		btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
		btnSendMessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (TextUtils.isEmpty(regId)) {
					Toast.makeText(getApplicationContext(), "RegId is empty!",
							Toast.LENGTH_LONG).show();
				} else {

					sendMessage("ECHO");
				}
			}
		});
	}

	private void sendMessage(final String action) {

		sendTask = new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {

				Bundle data = new Bundle();
				data.putString("ACTION", action);
				data.putString("CLIENT_MESSAGE", "Hello GCM CCS XMPP!");
				String id = Integer.toString(ccsMsgId.incrementAndGet());

				try {
					Log.d("RegisterActivity", "messageid: " + id);
					gcm.send(GOOGLE_PROJECT_ID + "@gcm.googleapis.com", id,
							data);
					Log.d("RegisterActivity", "After gcm.send successful.");
				} catch (IOException e) {
					Log.d("RegisterActivity", "Exception: " + e);
					e.printStackTrace();
				}
				return "Sent message.";
			}

			@Override
			protected void onPostExecute(String result) {
				sendTask = null;
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_LONG).show();
			}

		};
		sendTask.execute(null, null, null);

	}

	public String registerGCM() {

		gcm = GoogleCloudMessaging.getInstance(this);
		regId = getRegistrationId(context);

		if (TextUtils.isEmpty(regId)) {

			registerInBackground();

			Log.d("RegisterActivity",
					"registerGCM - successfully registered with GCM server - regId: "
							+ regId);
		} else {
			Toast.makeText(getApplicationContext(),
					"RegId already available. RegId: " + regId,
					Toast.LENGTH_LONG).show();
		}
		return regId;
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getSharedPreferences(
				RegisterActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString(REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.d("RegisterActivity",
					"I never expected this! Going down, going down!" + e);
			throw new RuntimeException(e);
		}
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regId = gcm.register(GOOGLE_PROJECT_ID);
					Log.d("RegisterActivity", "registerInBackground - regId: "
							+ regId);
					msg = "Device registered, registration ID=" + regId;

					storeRegistrationId(context, regId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					Log.d("RegisterActivity", "Error: " + msg);
				}
				Log.d("RegisterActivity", "AsyncTask completed: " + msg);
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Toast.makeText(getApplicationContext(),
						"Registered with GCM Server." + msg, Toast.LENGTH_LONG)
						.show();
			}
		}.execute(null, null, null);
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getSharedPreferences(
				RegisterActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(REG_ID, regId);
		editor.putInt(APP_VERSION, appVersion);
		editor.commit();
	}
}
