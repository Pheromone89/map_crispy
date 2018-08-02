package id.go.bpkp.mobilemapbpkp.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
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
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.SettingPrefs;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.splashscreen.SplashscreenActivity;

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
//        loading = findViewById(R.id.detector_loading);
        boolean isLoggedIn;

        final String username, password;

        username = sharedPreferences.getString(PassedIntent.INTENT_USERNAME, "");
        password = sharedPreferences.getString(PassedIntent.INTENT_PASSWORD, "");
        isLoggedIn = sharedPreferences.getBoolean(PassedIntent.ISLOGGEDIN, false);

        if (isLoggedIn) {
//            showProgress(true);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    lakukanLogin(username, password);
                }
            }, 3000);
        } else {
            Intent loginIntent = new Intent(LoginDetectorActivity.this, SplashscreenActivity.class);
            startActivity(loginIntent);
            this.finish();
        }
    }

    void lakukanLogin(final String username, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                PassingIntent.setUser(sharedPreferences, username, password);
                                startActivity(PassingIntent.loginIntent(
                                        LoginDetectorActivity.this,
                                        jsonObject,
                                        sharedPreferences,
                                        true
                                ));
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
