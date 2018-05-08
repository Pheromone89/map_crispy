package id.go.bpkp.mobilemapbpkp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class PhoneVerificationActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private TextView simpanButton, confirmationMessageView, currentPhoneMessageView, messageView;
    private LinearLayout confirmationLayout;
    private CardView yesButton, noButton;
    private String mNama, mNipBaru, mNipLama, mRoleId, mUserToken, mFoto, mAtasanLangsung, mNipAtasanLangsung, mNoHp, mEmail, currentPhoneMessage;
    private int mRoleIdInt;
    private boolean tidakPunyaAtasanLangsung;
    private EditText phoneNumberInput;
    private boolean isRedirect;
    private ImageView warningImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //intent dashboard dari login activity
        Intent dashboardIntent = getIntent();
        //get mVar
        mNama = dashboardIntent.getStringExtra(PassedIntent.INTENT_NAMA);
        //nip baru yg digabung
        mNipBaru = dashboardIntent.getStringExtra(PassedIntent.INTENT_NIPBARU);
        mNipLama = dashboardIntent.getStringExtra(PassedIntent.INTENT_NIPLAMA);
        mRoleId = dashboardIntent.getStringExtra(PassedIntent.INTENT_ROLEID);
        mRoleIdInt = Integer.parseInt(mRoleId);
        mUserToken = dashboardIntent.getStringExtra(PassedIntent.INTENT_USERTOKEN);
        mNoHp = dashboardIntent.getStringExtra(PassedIntent.INTENT_NOHP);
        mEmail = dashboardIntent.getStringExtra(PassedIntent.INTENT_EMAIL);
        mFoto = PassedIntent.getFoto(mNipLama);
        // data atasan langsung
        tidakPunyaAtasanLangsung = dashboardIntent.getBooleanExtra(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, true);
        mAtasanLangsung = dashboardIntent.getStringExtra(PassedIntent.INTENT_NAMAATASANLANGSUNG);
        mNipAtasanLangsung = dashboardIntent.getStringExtra(PassedIntent.INTENT_NIPATASANLANGSUNG);

        // setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        isRedirect = dashboardIntent.getBooleanExtra("is_redirect", false);

        if (mNoHp.equals("null")) {
            setContentView(R.layout.activity_phone_verification);
            initiateInputView();
            initiateInputOnClick();
        } else {
            setContentView(R.layout.activity_phone_verification_change);
            initiateChangeView();
            initiateChangeOnClick();
        }
    }

    private void initiateInputView() {
        simpanButton = findViewById(R.id.phone_verification_simpan_button);
        confirmationMessageView = findViewById(R.id.phone_verification_confirmation_button);

        phoneNumberInput = findViewById(R.id.phone_verification_phone_input);

        confirmationLayout = findViewById(R.id.phone_verification_confirmation_layout);
        yesButton = findViewById(R.id.phone_verification_yes);
        noButton = findViewById(R.id.phone_verification_no);

        messageView = findViewById(R.id.phone_verification_message);
        warningImageView = findViewById(R.id.phone_verification_warning_image);
        String message;
        if (isRedirect) {
            message = "Masukkan nomor baru Anda";
            warningImageView.setVisibility(View.GONE);
        } else {
            message = "Ups! Nomor handphone Anda belum terekam dalam database kepegawaian BPKP";
        }
        messageView.setText(message);
    }

    private void initiateChangeView() {
        currentPhoneMessageView = findViewById(R.id.phone_verification_message_current_phone);
        currentPhoneMessage = "Apakah Anda masih menggunakan nomor " + mNoHp + "?";
        yesButton = findViewById(R.id.phone_verification_yes);
        noButton = findViewById(R.id.phone_verification_no);
        currentPhoneMessageView.setText(currentPhoneMessage);
    }

    private void initiateChangeOnClick() {
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhoneVerificationActivity.this, PhoneVerificationActivity.class);
                i.putExtra(PassedIntent.INTENT_USERTOKEN, mUserToken);
                i.putExtra(PassedIntent.INTENT_NAMA, mNama);
                i.putExtra(PassedIntent.INTENT_NIPBARU, mNipBaru);
                i.putExtra(PassedIntent.INTENT_NIPLAMA, mNipLama);
                i.putExtra(PassedIntent.INTENT_ROLEID, mRoleId);
                i.putExtra(PassedIntent.INTENT_ROLEIDINT, mRoleIdInt);
                i.putExtra(PassedIntent.INTENT_NOHP, "null");
                i.putExtra(PassedIntent.INTENT_EMAIL, mEmail);
                i.putExtra("is_redirect", true);
                if (!tidakPunyaAtasanLangsung) {
                    i.putExtra(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);
                    // nama atasan langsung
                    i.putExtra(PassedIntent.INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
                    // nip atasan langsung
                    i.putExtra(PassedIntent.INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
                }
                startActivity(i);
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhoneVerificationActivity.this, DashboardActivity.class);
                i.putExtra(PassedIntent.INTENT_USERTOKEN, mUserToken);
                i.putExtra(PassedIntent.INTENT_NAMA, mNama);
                i.putExtra(PassedIntent.INTENT_NIPBARU, mNipBaru);
                i.putExtra(PassedIntent.INTENT_NIPLAMA, mNipLama);
                i.putExtra(PassedIntent.INTENT_ROLEID, mRoleId);
                i.putExtra(PassedIntent.INTENT_ROLEIDINT, mRoleIdInt);
                i.putExtra(PassedIntent.INTENT_NOHP, mNoHp);
                i.putExtra(PassedIntent.INTENT_EMAIL, mEmail);
                if (!tidakPunyaAtasanLangsung) {
                    i.putExtra(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);
                    // nama atasan langsung
                    i.putExtra(PassedIntent.INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
                    // nip atasan langsung
                    i.putExtra(PassedIntent.INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
                }
                editor.putBoolean(SettingPrefs.SETTING_BELUMSETNOHP, false);
                editor.commit();
                startActivity(i);
                PhoneVerificationActivity.this.finish();
            }
        });
    }

    private void initiateInputOnClick() {
        simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberInput.getText().toString();
                validasiInputView(phoneNumber);
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationLayout.setVisibility(View.GONE);
                simpanButton.setVisibility(View.VISIBLE);
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberInput.getText().toString();
                setNomorHp(phoneNumber);
            }
        });
    }

    private void validasiInputView(String phoneNum) {
        phoneNumberInput.setError(null);
        boolean cancel = false;
        if (TextUtils.isEmpty(phoneNum)) {
            phoneNumberInput.setError(getString(R.string.error_field_required));
            cancel = true;
        }
        if (phoneNum.length() < 10 || phoneNum.length() > 13) {
            phoneNumberInput.setError("Panjang nomor handphone harus 10 - 13 digit");
            cancel = true;
        }
        if (!cancel) {
            confirmationLayout.setVisibility(View.VISIBLE);
            simpanButton.setVisibility(View.GONE);
        }
    }

    private void setNomorHp(final String mNoHp) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_POST_PHONE + mNipLama + "?api_token=" + mUserToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("success");
                            if (message.equals("true")) {
                                Intent i = new Intent(PhoneVerificationActivity.this, DashboardActivity.class);
                                i.putExtra(PassedIntent.INTENT_USERTOKEN, mUserToken);
                                i.putExtra(PassedIntent.INTENT_NAMA, mNama);
                                i.putExtra(PassedIntent.INTENT_NIPBARU, mNipBaru);
                                i.putExtra(PassedIntent.INTENT_NIPLAMA, mNipLama);
                                i.putExtra(PassedIntent.INTENT_ROLEIDINT, mRoleIdInt);
                                i.putExtra(PassedIntent.INTENT_NOHP, mNoHp);
                                i.putExtra(PassedIntent.INTENT_EMAIL, mEmail);
                                if (!tidakPunyaAtasanLangsung) {
                                    i.putExtra(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);
                                    i.putExtra(PassedIntent.INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
                                    i.putExtra(PassedIntent.INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
                                }
                                Toast.makeText(PhoneVerificationActivity.this, "Penyimpanan nomor handphone berhasil, data akan diupdate setelah jeda beberapa saat", Toast.LENGTH_LONG).show();
                                editor.putBoolean(SettingPrefs.SETTING_BELUMSETNOHP, false);
                                editor.commit();
                                startActivity(i);
                                PhoneVerificationActivity.this.finish();
                            } else {
                                Toast.makeText(PhoneVerificationActivity.this, "Penyimpanan nomor handphone gagal", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("no_hp", mNoHp);
                return params;
            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Accept", "application/json");
//                return params;
//            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
