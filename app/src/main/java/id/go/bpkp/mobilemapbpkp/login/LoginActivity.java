package id.go.bpkp.mobilemapbpkp.login;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.go.bpkp.mobilemapbpkp.dashboard.DashboardActivity;
import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.SettingPrefs;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_DASHBOARDCONTENT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_EMAIL;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTO;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_IMEI;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISJAB;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISLDAP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_LDAP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPLAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NOHP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ROLEID;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ROLEIDINT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERNAME;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_PASSWORD;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERTOKEN;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    public static final String API_TOKEN = "id.go.bpkp.mobilemapbpkp.extra.API_TOKEN";
    public static final String NAMA = "id.go.bpkp.mobilemapbpkp.extra.NAMA";
    public static final String USERNAME = "id.go.bpkp.mobilemapbpkp.extra.USERNAME";
    public static final String ROLEID = "id.go.bpkp.mobilemapbpkp.extra.ROLEID";
    public static final String NIPLAMA = "id.go.bpkp.mobilemapbpkp.extra.NIPLAMA";
    public static final String KONTENDASHBOARD = "id.go.bpkp.mobilemapbpkp.extra.KONTENDASHBOARD";
    public static final String NAMAATASANLANGSUNG = "id.go.bpkp.mobilemapbpkp.extra.NAMAATASANLANGSUNG";
    public static final String NIPATASANLANGSUNG = "id.go.bpkp.mobilemapbpkp.extra.NIPATASANLANGSUNG";
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String mPhoneNumber, mImei, JSON_KONTEN;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText
            mUsername,
            phoneNumberEditText,
            mPasswordView;
    private View
            mProgressView,
            mLoginFormView,
            mPhoneInputView;
    private TextView
            loginButton;

    public static void setUsername(Context context, String username) {
        SharedPreferences prefs = context.getSharedPreferences(Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsername = (EditText) findViewById(R.id.login_username);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                }
                return false;
            }
        });

        // set up setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        phoneNumberEditText = findViewById(R.id.login_phone);

        loginButton = (TextView) findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mPhoneInputView = findViewById(R.id.login_phone_number_input);

        String username = sharedPreferences.getString(INTENT_USERNAME, "");
        String password = sharedPreferences.getString(INTENT_PASSWORD, "");

        mUsername.setText(username);
        mPasswordView.setText(password);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            mImei = telephonyManager.getDeviceId();
            mPhoneNumber = tMgr.getLine1Number();
            phoneNumberEditText.setText(mPhoneNumber);
            if (mPhoneNumber.equals("")) {
//                mPhoneInputView.setVisibility(View.VISIBLE);
                mPhoneInputView.setVisibility(View.GONE);
//                Toast.makeText(this, "permission granted but phonenum not found", Toast.LENGTH_SHORT).show();
            } else {
                mPhoneInputView.setVisibility(View.GONE);
//                Toast.makeText(this, mPhoneNumber, Toast.LENGTH_SHORT).show();
            }
        } else {
            mPhoneInputView.setVisibility(View.GONE);
//            mPhoneInputView.setVisibility(View.VISIBLE);
//            Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
        }
        phoneNumberEditText.setText("null");
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsername.setError(null);
        mPasswordView.setError(null);
        phoneNumberEditText.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsername.getText().toString();
        String password = mPasswordView.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();

        boolean cancel = false;
//        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
//            focusView = mUsername;
            cancel = true;
        }
        // Check for a valid phone number.
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberEditText.setError(getString(R.string.error_field_required));
//            focusView = mUsername;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
//            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            mAuthTask = new UserLoginTask(username, password);
//            mAuthTask.execute((Void) null);
            lakukanLogin(username, password);
        }
    }

    private boolean isUsernameValid(String userNIP) {
        //TODO: Replace this with your own logic
        return userNIP.length() < 18;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

    void lakukanLogin(final String username, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                int roleIdInt = Integer.parseInt(jsonObject.getJSONObject("message").getString("role_id"));
                                boolean belumRekamNoHp = sharedPreferences.getBoolean(SettingPrefs.SETTING_BELUMSETNOHP, true);

                                int versiUpdate = 0;
                                try {
                                    versiUpdate = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionCode;
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }

                                int versiLogin = Integer.parseInt(jsonObject.getJSONObject("message").getString("version"));
//                                versiLogin = 0;
                                boolean isVersionValid = versiLogin == versiUpdate;
                                Intent i = null;
                                if (roleIdInt == 4 && belumRekamNoHp) {
                                    i = new Intent(LoginActivity.this, PhoneVerificationActivity.class);
                                } else {
                                    i = new Intent(LoginActivity.this, DashboardActivity.class);
                                }
                                if (!isVersionValid) {
                                    i = new Intent(LoginActivity.this, VersionCheckActivity.class);
                                }
                                // api_token
                                i.putExtra(INTENT_USERTOKEN, jsonObject.getString("api_token"));
                                i.putExtra(INTENT_NAMA, jsonObject.getJSONObject("message").getString("name"));
                                i.putExtra(INTENT_USERNAME, username);
                                i.putExtra(INTENT_PASSWORD, password);
                                i.putExtra(INTENT_NIPBARU, jsonObject.getJSONObject("message").getString("nipbaru"));
                                i.putExtra(INTENT_NIPLAMA, jsonObject.getJSONObject("message").getString("user_nip"));
                                i.putExtra(INTENT_ROLEID, jsonObject.getJSONObject("message").getString("role_id"));
                                i.putExtra(INTENT_LDAP, jsonObject.getJSONObject("message").getString("is_ldap"));
                                i.putExtra(INTENT_ROLEIDINT, Integer.parseInt(jsonObject.getJSONObject("message").getString("role_id")));
                                i.putExtra(INTENT_NOHP, jsonObject.getJSONObject("message").getString("nomorhp"));
                                i.putExtra(INTENT_IMEI, mImei);
                                i.putExtra(INTENT_EMAIL, jsonObject.getJSONObject("message").getString("email"));
                                i.putExtra("is_redirect", false);
                                boolean tidakPunyaAtasanLangsung = (jsonObject.getString("atasan").equals("null"));
                                boolean isLdap = (jsonObject.getJSONObject("message").getString("is_ldap").equals("true"));
                                boolean isJab = (jsonObject.getJSONObject("message").getString("is_jab").equals("true"));
                                if (!tidakPunyaAtasanLangsung) {
                                    i.putExtra(INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);
                                    i.putExtra(INTENT_NAMAATASANLANGSUNG, jsonObject.getJSONObject("atasan").getString("nama_lengkap"));
                                    i.putExtra(INTENT_NIPATASANLANGSUNG, jsonObject.getJSONObject("atasan").getString("s_nip"));
                                }
                                i.putExtra(INTENT_ISLDAP, isLdap);
                                i.putExtra(INTENT_ISJAB, isJab);
                                editor.putString(INTENT_USERNAME, username);
                                editor.putString(INTENT_PASSWORD, password);
                                editor.putBoolean(PassedIntent.ISLOGGEDIN, true);
                                editor.apply();
                                startActivity(i);
                            } else {
                                String failedLoginMessage = jsonObject.getString("message");
                                if (failedLoginMessage.equals("Your username or password incorrect!")) {
                                    Snackbar.make(loginButton, "Username atau kata sandi yang Anda masukkan salah", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                                } else if (failedLoginMessage.equals("Your password incorrect!")) {
                                    Snackbar.make(loginButton, "Kata sandi yang Anda masukkan salah", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                                } else {
                                    Snackbar.make(loginButton, "Gagal melakukan Login", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                                }
                                showProgress(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            Snackbar.make(loginButton, "Gagal mengotentifikasi", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof ServerError) {
                            Snackbar.make(loginButton, "Masalah pada server", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof TimeoutError) {
                            Snackbar.make(loginButton, "Waktu koneksi habis", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof NetworkError) {
                            Snackbar.make(loginButton, "Gagal menghubungkan dengan jaringan", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof ParseError) {
                            Snackbar.make(loginButton, "Gagal parsing data", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        }
                        showProgress(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

