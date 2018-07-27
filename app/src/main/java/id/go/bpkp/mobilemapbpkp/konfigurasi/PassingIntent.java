package id.go.bpkp.mobilemapbpkp.konfigurasi;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.dashboard.DashboardActivity;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;
import id.go.bpkp.mobilemapbpkp.login.PhoneVerificationActivity;
import id.go.bpkp.mobilemapbpkp.login.VersionCheckActivity;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTIMAGE;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTMESSAGE;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTSTATUS;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTTITLE;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_EMAIL;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_IMEI;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISATASAN;
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

public class PassingIntent {

    private static String JSON_STRING;
    private static String fotoUrl;

    public static void setUser(SharedPreferences sharedPreferences, String username, String password) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(INTENT_USERNAME, username);
        editor.putString(INTENT_PASSWORD, password);

        editor.apply();
    }

    public static Intent loginIntent(
            Context context,
            JSONObject jsonObject,
            SharedPreferences sharedPreferences,
            boolean showBroadcast) {

        String username = sharedPreferences.getString(PassedIntent.INTENT_USERNAME, "");
        String password = sharedPreferences.getString(PassedIntent.INTENT_PASSWORD, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String imei = "";
        String phoneNumber = "";

        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tMgr = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
            phoneNumber = tMgr.getLine1Number();
        }

        Intent i = null;
        try {
            int roleIdInt = Integer.parseInt(jsonObject.getJSONObject("message").getString("role_id"));
            boolean belumRekamNoHp = sharedPreferences.getBoolean(SettingPrefs.SETTING_BELUMSETNOHP, true);

            int versiUpdate = 0;
            try {
                versiUpdate = context
                        .getApplicationContext()
                        .getPackageManager()
                        .getPackageInfo(context
                                .getApplicationContext()
                                .getPackageName(), 0)
                        .versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            int versiLogin = Integer.parseInt(jsonObject.getJSONObject("message").getString("version"));
            boolean isVersionValid = versiLogin == versiUpdate;

            if (roleIdInt == 4 && belumRekamNoHp) {
                i = new Intent(context, PhoneVerificationActivity.class);
            } else {
                i = new Intent(context, DashboardActivity.class);
            }
            if (!isVersionValid) {
                i = new Intent(context, VersionCheckActivity.class);
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
            String jenisJabatan = null;
            if (!jsonObject.getJSONObject("message").getString("pegawai").equals("null")) {
                JSONObject pegawaiJson = jsonObject.getJSONObject("message").getJSONObject("pegawai");
                jenisJabatan = pegawaiJson.getString("jenis_jab");
            } else {
                jenisJabatan = "tidak ada rincian pegawai";
            }
            i.putExtra("jenis_jabatan", jenisJabatan);
            i.putExtra(INTENT_IMEI, imei);
            i.putExtra(INTENT_EMAIL, jsonObject.getJSONObject("message").getString("email"));
            i.putExtra("is_redirect", false);
            boolean tidakPunyaAtasanLangsung = (jsonObject.getString("atasan").equals("null"));
            boolean isAtasan = (jsonObject.getJSONObject("message").getString(konfigurasi.TAG_ISATASAN).equals("true"));
            boolean isLdap = (jsonObject.getJSONObject("message").getString("is_ldap").equals("true"));
            boolean isJab = (jsonObject.getJSONObject("message").getString("is_jab").equals("true"));
            boolean isHut = (jsonObject.getJSONObject("message").getString("is_hut").equals("true"));
            if (!tidakPunyaAtasanLangsung) {
                i.putExtra(INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);
                i.putExtra(INTENT_NAMAATASANLANGSUNG, jsonObject.getJSONObject("atasan").getString("nama_lengkap"));
                i.putExtra(INTENT_NIPATASANLANGSUNG, jsonObject.getJSONObject("atasan").getString("s_nip"));
            }
            i.putExtra(INTENT_ISATASAN, isAtasan);
            i.putExtra(INTENT_ISLDAP, isLdap);
            i.putExtra(INTENT_ISJAB, isJab);
            i.putExtra(INTENT_ISHUT, isHut);
            // broadcast
            i.putExtra("is_broadcastable", showBroadcast);
            i.putExtra(INTENT_BROADCASTSTATUS, jsonObject.getJSONObject("broadcast").getString("status"));
            i.putExtra(INTENT_BROADCASTIMAGE, jsonObject.getJSONObject("broadcast").getString("images"));
            i.putExtra(INTENT_BROADCASTTITLE, jsonObject.getJSONObject("broadcast").getString("title"));
            i.putExtra(INTENT_BROADCASTMESSAGE, jsonObject.getJSONObject("broadcast").getString("message"));

            // TEST FOTO //

            fotoUrl = jsonObject.getJSONObject("message").getString("url_foto");
//            Toast.makeText(context, fotoUrl, Toast.LENGTH_SHORT).show();
            editor.putString("foto_url", fotoUrl);

            // TEST FOTO //

            editor.putString(INTENT_USERNAME, username);
            editor.putString(INTENT_PASSWORD, password);
            editor.putBoolean(PassedIntent.ISLOGGEDIN, true);
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static Intent logoutIntent(
            Context context,
            SharedPreferences sharedPreferences) {
        Intent i = null;
        String username = sharedPreferences.getString(PassedIntent.INTENT_USERNAME, "");
        String password = sharedPreferences.getString(PassedIntent.INTENT_PASSWORD, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(PassedIntent.ISLOGGEDIN, false);
        editor.putString("foto_url", null);
        editor.apply();
        i = new Intent(context, LoginActivity.class);
        i.putExtra(INTENT_USERNAME, username);
        i.putExtra(INTENT_PASSWORD, password);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return i;
    }

    public static void signOut(final Context context, final String mUserToken, final SharedPreferences sharedPreferences) {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, null, "keluar ...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;

                JSONObject jsonObject = null;
                Intent logoutIntent = null;
                String logoutMessage;
                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    String message = jsonObject.getString("success");
                    if (message.equals("true")) {
                        logoutMessage = "logout sukses";
                    } else {
                        logoutMessage = "logout gagal";
                    }
                } catch (JSONException e) {
                    logoutMessage = "logout gagal";
                }
                SettingPrefs.clearProfil(sharedPreferences);
                Toast.makeText(context, logoutMessage, Toast.LENGTH_SHORT).show();
                context.startActivity(PassingIntent.logoutIntent(context, sharedPreferences));
                ((Activity) context).finish();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_LOGOUT + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
