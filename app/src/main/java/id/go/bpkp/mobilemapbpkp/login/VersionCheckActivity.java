package id.go.bpkp.mobilemapbpkp.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.dashboard.DashboardActivity;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_EMAIL;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_IMEI;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISLDAP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPLAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NOHP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_PASSWORD;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ROLEIDINT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERNAME;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERTOKEN;

public class VersionCheckActivity extends AppCompatActivity {

    CardView unduhCardView;
    String mNama,
            mUsername,
            mPassword,
            mNipBaru,
            mNipLama,
            mRoleId,
            mUserToken,
            mFoto,
            mNoHp,
            mImei,
            mEmail,
            JSON_STRING,
            mContentUrl,
            openedDrawer,
            logoutMessage,
            mAtasanLangsung,
            mNipAtasanLangsung,
            fragmentTag = null,
            mPhoneNumber;
    int
            mRoleIdInt;
    MenuItem
            profilSemuaPegawaiMenu,
            profilPegawaiMenu;
    Menu
            rootMenu;
    boolean
            isWannaQuit = false;
    NavigationView
            navigationView;
    View
            navigationHeader;
    ImageView
            navHeaderProficView;
    TextView
            navHeaderNamaView,
            navHeaderNipView;
    DrawerLayout
            drawer;
    Toolbar
            toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean
            tidakPunyaAtasanLangsung, isLdap;
    private MenuItem helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_check);

        // set up setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        //intent dashboard dari login activity
        Intent dashboardIntent = getIntent();
        //get mVar
        mNama = dashboardIntent.getStringExtra(INTENT_NAMA);
        mUsername = dashboardIntent.getStringExtra(INTENT_USERNAME);
        mPassword = dashboardIntent.getStringExtra(INTENT_PASSWORD);
        //nip baru yg digabung
        mNipBaru = dashboardIntent.getStringExtra(INTENT_NIPBARU);
        mNipLama = dashboardIntent.getStringExtra(INTENT_NIPLAMA);
        mRoleIdInt = dashboardIntent.getIntExtra(INTENT_ROLEIDINT, 99);
        mUserToken = dashboardIntent.getStringExtra(INTENT_USERTOKEN);
        mFoto = PassedIntent.getFoto(mNipLama);
        mNoHp = dashboardIntent.getStringExtra(INTENT_NOHP);
        mImei = dashboardIntent.getStringExtra(INTENT_IMEI);
        mEmail = dashboardIntent.getStringExtra(INTENT_EMAIL);
        // data atasan langsung
        tidakPunyaAtasanLangsung = dashboardIntent.getBooleanExtra(INTENT_TIDAKPUNYAATASANLANGSUNG, true);
        isLdap = dashboardIntent.getBooleanExtra(INTENT_ISLDAP, true);
        mAtasanLangsung = dashboardIntent.getStringExtra(INTENT_NAMAATASANLANGSUNG);
        mNipAtasanLangsung = dashboardIntent.getStringExtra(INTENT_NIPATASANLANGSUNG);

        initateView();
        populateView();
        setOnClick();
    }

    private void initateView() {
        unduhCardView = findViewById(R.id.version_check_download_cardview);
    }

    private void populateView() {

    }

    private void setOnClick() {
        unduhCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://app.bpkp.go.id";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        PassingIntent.signOut(VersionCheckActivity.this, mUserToken, sharedPreferences);
//        signOut();
    }

//    public void signOut() {
//        getJSON();
//    }
//
//    private void parseJSON() {
//        JSONObject jsonObject = null;
//        Intent logoutIntent = null;
//        try {
//            jsonObject = new JSONObject(JSON_STRING);
//            String message = jsonObject.getString("success");
//            if (message.equals("true")) {
//                logoutMessage = "logout sukses";
//                editor.putBoolean(PassedIntent.ISLOGGEDIN, false);
//                editor.apply();
////                Toast.makeText(getApplicationContext(), logoutMessage, Toast.LENGTH_SHORT).show();
//                logoutIntent = new Intent(VersionCheckActivity.this, LoginActivity.class);
//                logoutIntent.putExtra(INTENT_USERNAME, mUsername);
//                logoutIntent.putExtra(INTENT_PASSWORD, mPassword);
//                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(logoutIntent);
//                finish();
//            } else {
//                logoutMessage = "logout gagal";
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        startActivity(logoutIntent);
//        finish();
//    }
//
//    private void getJSON() {
//        class GetJSON extends AsyncTask<Void, Void, String> {
//
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(VersionCheckActivity.this, null, null, false, false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                JSON_STRING = s;
//                parseJSON();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                RequestHandler rh = new RequestHandler();
//                String s = rh.sendGetRequest(konfigurasi.URL_LOGOUT + mUserToken);
//                return s;
//            }
//        }
//        GetJSON gj = new GetJSON();
//        gj.execute();
//    }
}
