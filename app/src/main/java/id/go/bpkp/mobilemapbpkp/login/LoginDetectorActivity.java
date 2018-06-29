package id.go.bpkp.mobilemapbpkp.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.dashboard.DashboardActivity;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.SettingPrefs;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.splashscreen.SplashscreenActivity;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTIMAGE;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTMESSAGE;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTSTATUS;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTTITLE;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_EMAIL;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_IMEI;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISHUT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISJAB;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISLDAP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_LDAP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPLAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NOHP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_PASSWORD;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ROLEID;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ROLEIDINT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERNAME;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERTOKEN;

public class LoginDetectorActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        // set up setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        loading = findViewById(R.id.detector_loading);
        boolean isLoggedIn;

        String username, password, imei, phoneNumber;

        imei = "";
        phoneNumber = "";

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
            phoneNumber = tMgr.getLine1Number();
        }

        username = sharedPreferences.getString(PassedIntent.INTENT_USERNAME, "");
        password = sharedPreferences.getString(PassedIntent.INTENT_PASSWORD, "");
        isLoggedIn = sharedPreferences.getBoolean(PassedIntent.ISLOGGEDIN, false);

        Intent i;
        if (isLoggedIn) {
            showProgress(true);
            lakukanLogin(username, password, imei, phoneNumber);
        } else {
            Intent loginIntent = new Intent(LoginDetectorActivity.this, SplashscreenActivity.class);
            startActivity(loginIntent);
            this.finish();
        }
    }

    void lakukanLogin(final String username, final String password, final String imei, final String phoneNumber) {
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
                                    i = new Intent(LoginDetectorActivity.this, PhoneVerificationActivity.class);
                                } else {
                                    i = new Intent(LoginDetectorActivity.this, DashboardActivity.class);
                                }
                                if (!isVersionValid) {
                                    i = new Intent(LoginDetectorActivity.this, VersionCheckActivity.class);
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
                                i.putExtra(INTENT_IMEI, imei);
                                i.putExtra(INTENT_EMAIL, jsonObject.getJSONObject("message").getString("email"));
                                i.putExtra("is_redirect", false);
                                boolean tidakPunyaAtasanLangsung = (jsonObject.getString("atasan").equals("null"));
                                boolean isLdap = (jsonObject.getJSONObject("message").getString("is_ldap").equals("true"));
                                boolean isJab = (jsonObject.getJSONObject("message").getString("is_jab").equals("true"));
                                boolean isHut = (jsonObject.getJSONObject("message").getString("is_hut").equals("true"));
                                if (!tidakPunyaAtasanLangsung) {
                                    i.putExtra(INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);
                                    i.putExtra(INTENT_NAMAATASANLANGSUNG, jsonObject.getJSONObject("atasan").getString("nama_lengkap"));
                                    i.putExtra(INTENT_NIPATASANLANGSUNG, jsonObject.getJSONObject("atasan").getString("s_nip"));
                                }
                                i.putExtra(INTENT_ISLDAP, isLdap);
                                i.putExtra(INTENT_ISJAB, isJab);
                                i.putExtra(INTENT_ISHUT, isHut);
                                // broadcast
                                i.putExtra("is_broadcastable", true);
                                i.putExtra(INTENT_BROADCASTSTATUS, jsonObject.getJSONObject("broadcast").getString("status"));
                                i.putExtra(INTENT_BROADCASTIMAGE, jsonObject.getJSONObject("broadcast").getString("images"));
                                i.putExtra(INTENT_BROADCASTTITLE, jsonObject.getJSONObject("broadcast").getString("title"));
                                i.putExtra(INTENT_BROADCASTMESSAGE, jsonObject.getJSONObject("broadcast").getString("message"));
                                editor.putString(INTENT_USERNAME, username);
                                editor.putString(INTENT_PASSWORD, password);
                                editor.putBoolean(PassedIntent.ISLOGGEDIN, true);
                                editor.commit();
                                startActivity(i);
                            } else {
                                Intent loginIntent = new Intent(LoginDetectorActivity.this, LoginActivity.class);
                                loginIntent.putExtra("username", "");
                                startActivity(loginIntent);
                                LoginDetectorActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent loginIntent = new Intent(LoginDetectorActivity.this, LoginActivity.class);
                        loginIntent.putExtra("username", "");
                        startActivity(loginIntent);
                        LoginDetectorActivity.this.finish();
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

    private void showProgress(final boolean show) {
        if (show) {
            loading.setVisibility(View.VISIBLE);
        }
    }
}
