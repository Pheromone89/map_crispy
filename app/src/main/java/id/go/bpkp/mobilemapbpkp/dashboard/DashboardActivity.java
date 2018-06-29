package id.go.bpkp.mobilemapbpkp.dashboard;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.absen.AbsenFragment;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDashboardAdminFragment;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDashboardPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.izinkantor.IzinKantorDashboardAdminFragment;
import id.go.bpkp.mobilemapbpkp.izinkantor.IzinKantorDashboardPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.UserRole;
import id.go.bpkp.mobilemapbpkp.monitoring.PencarianPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.monitoring.ProfilPegawaiPagerFragment;
import id.go.bpkp.mobilemapbpkp.monitoring.ProfilSeluruhPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTIMAGE;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTMESSAGE;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTSTATUS;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_BROADCASTTITLE;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_DASHBOARDCONTENT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_EMAIL;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTO;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_IMEI;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISHUT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISJAB;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISLDAP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPLAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NOHP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_PASSWORD;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ROLEIDINT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERNAME;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERTOKEN;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String
            mNama,
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
            mPhoneNumber,
            broadcastStatus,
            broadcastTitle,
            broadcastImage,
            broadcastMessage;
    int
            mRoleIdInt;
    MenuItem
            profilSemuaPegawaiMenu,
            profilPegawaiMenu;
    Menu
            rootMenu;
    boolean
            isWannaQuit = false,
            isQuitting = false,
            isBroadcastable;
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
            tidakPunyaAtasanLangsung, isLdap, isJab, isHut;
    private MenuItem helpButton;
    LinearLayout broadcastLayout;
    TextView broadcastTitleView, broadcastMessageView;
    ImageView broadcastImageView, broadcastClose;
    // konfirmasi logout
    LinearLayout konfirmasiKeluarLayout;
    CardView konfirmasiKeluarYaView, konfirmasiKeluarTidakView;
    private YoYo.YoYoString ropeBroadcastImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

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
        isJab = dashboardIntent.getBooleanExtra(INTENT_ISJAB, false);
        isHut = dashboardIntent.getBooleanExtra(INTENT_ISHUT, false);
        mAtasanLangsung = dashboardIntent.getStringExtra(INTENT_NAMAATASANLANGSUNG);
        mNipAtasanLangsung = dashboardIntent.getStringExtra(INTENT_NIPATASANLANGSUNG);
        // broadcast
        isBroadcastable = dashboardIntent.getBooleanExtra("is_broadcastable", false);
        broadcastStatus = dashboardIntent.getStringExtra(INTENT_BROADCASTSTATUS);
        broadcastImage = dashboardIntent.getStringExtra(INTENT_BROADCASTIMAGE);
        broadcastTitle = dashboardIntent.getStringExtra(INTENT_BROADCASTTITLE);
        broadcastMessage = dashboardIntent.getStringExtra(INTENT_BROADCASTMESSAGE);

        initateView();
        populateView();
        initiateDashboard();

        //callback function
        //drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //navigation view
        navigationView.setNavigationItemSelectedListener(this);
        //broadcast
        if (!broadcastStatus.equals("0") && isBroadcastable) {
            broadcastLayout.setVisibility(View.VISIBLE);
            broadcastTitleView.setText(broadcastTitle);
            broadcastMessageView.setText(broadcastMessage);
            toolbar.setVisibility(View.GONE);
            Picasso.with(DashboardActivity.this).load(broadcastImage).into(broadcastImageView);
        } else {
            broadcastLayout.setVisibility(View.GONE);
        }
        //broadcast listener
        broadcastClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                broadcastLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
            }
        });
        konfirmasiKeluarYaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        konfirmasiKeluarTidakView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateExit(false);
            }
        });
    }

    private void initateView() {
        //navigation view
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigation header
        navigationHeader = navigationView.getHeaderView(0);
        navHeaderProficView = navigationHeader.findViewById(R.id.navigation_drawer_header_profile_picture);
        navHeaderNamaView = navigationHeader.findViewById(R.id.navigation_drawer_header_name);
        navHeaderNipView = navigationHeader.findViewById(R.id.navigation_drawer_header_nip);
        //drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //menu profile semua pegawai hide/show
        profilSemuaPegawaiMenu = navigationView.getMenu().getItem(2);
        profilPegawaiMenu = navigationView.getMenu().getItem(3);
        //broadcast
        broadcastLayout = findViewById(R.id.dashboard_broadcast);
        broadcastImageView = findViewById(R.id.dashboard_broadcast_image);
        broadcastMessageView = findViewById(R.id.dashboard_broadcast_message);
        broadcastTitleView = findViewById(R.id.dashboard_broadcast_title);
        broadcastClose = findViewById(R.id.dashboard_broadcast_close);
        // konfirmasi
        konfirmasiKeluarLayout = findViewById(R.id.dashboard_konfirmasi_keluar);
        konfirmasiKeluarYaView = findViewById(R.id.dashboard_konfirmasi_keluar_ya);
        konfirmasiKeluarTidakView = findViewById(R.id.dashboard_konfirmasi_keluar_tidak);
    }

    private void populateView() {
        // header
        Picasso.with(DashboardActivity.this).load(mFoto).into(navHeaderProficView);
        navHeaderNamaView.setText(mNama);
        navHeaderNipView.setText(mNipBaru);
        // toolbar
        setSupportActionBar(toolbar);
        // broadcast
        ropeBroadcastImage = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(broadcastImageView);
    }

    private void initiateDashboard() {
        //set content dan initiate dashboard berdasarkan role
        Fragment dashboardFragment = null;
        String titleDashboard = null;
        if (mRoleIdInt == UserRole.USER_ROLE_SUPERADMIN ||
                mRoleIdInt == UserRole.USER_ROLE_ADMINPUSAT ||
                mRoleIdInt == UserRole.USER_ROLE_ADMINUNIT ||
                mRoleIdInt == UserRole.USER_ROLE_BAPERJAKAT ||
                mRoleIdInt == 7) {
            mContentUrl = konfigurasi.URL_GET_DASHBOARD_CONTENT_ADMIN;
//            dashboardFragment = new DashboardAdminFragment();
            dashboardFragment = new PencarianPegawaiFragment();
            titleDashboard = "Pencarian Pegawai";
            profilSemuaPegawaiMenu.setVisible(true);
            profilPegawaiMenu.setVisible(false);
        } else {
            mContentUrl = konfigurasi.URL_GET_DASHBOARD_CONTENT_USER;
            dashboardFragment = new DashboardPegawaiFragment();
            titleDashboard = getResources().getString(R.string.title_fragment_dashboard_pegawai);
            profilSemuaPegawaiMenu.setVisible(false);
            profilPegawaiMenu.setVisible(true);
        }
        if (isJab) {
            profilSemuaPegawaiMenu.setVisible(true);
        }
        //initiate dashboard
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // set content dashboard
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_USERTOKEN, mUserToken);
        bundle.putString(INTENT_DASHBOARDCONTENT, mContentUrl);
        bundle.putString(INTENT_NAMA, mNama);
        bundle.putString(INTENT_NIPLAMA, mNipLama);
        bundle.putString(INTENT_NIPBARU, mNipBaru);
        bundle.putString(INTENT_FOTO, mFoto);
        bundle.putString(INTENT_NOHP, mNoHp);
        bundle.putString(INTENT_IMEI, mImei);
        bundle.putString(INTENT_EMAIL, mEmail);
        bundle.putBoolean(INTENT_ISHUT, isHut);
        bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);
        dashboardFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.content_fragment_area, dashboardFragment, titleDashboard);
        fragmentTag = "dashboard";
        openedDrawer = "nav_drawer_dashboard";
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (broadcastLayout.isShown()) {
            broadcastLayout.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            return;
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() == 0 && openedDrawer.equals("nav_drawer_dashboard") && !isQuitting) {
            openedDrawer = "nav_drawer_dashboard";
//            Toast.makeText(this, "sentuh KEMBALI dua kali untuk logout", Toast.LENGTH_SHORT).show();
//            isWannaQuit = false;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (isWannaQuit) {
//                        signOut();
//                    }
//                    isWannaQuit = true;
//                }
//            }, 1000);
            if (konfirmasiKeluarLayout.isShown()) {
                initiateExit(false);
            } else {
                initiateExit(true);
            }
        } else if (getFragmentManager().getBackStackEntryCount() == 0) {
            initiateDashboard();
            navigationView.getMenu().getItem(0).setChecked(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        rootMenu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Toast.makeText(this, "Hubungi Bagian Perencanaan dan Pengembangan Pegawai di ekstensi 0165 apabila Anda membutuhkan bantuan dalam penggunaan MAP MOBILE BPKP", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here. Switch fragment here
        Fragment fragment = null;
        int id = item.getItemId();
        if (id == R.id.nav_drawer_dashboard) {
            if (openedDrawer != "nav_drawer_dashboard") {
                initiateDashboard();
            }

        } else if (id == R.id.nav_profile_seluruh_pegawai) {
            if (openedDrawer != "nav_profile_seluruh_pegawai") {
                String url;
                if (mRoleIdInt == UserRole.USER_ROLE_SUPERADMIN || mRoleIdInt == UserRole.USER_ROLE_ADMINPUSAT) {
                    url = konfigurasi.URL_GET_ALLSMALL;
                } else if (mRoleIdInt == UserRole.USER_ROLE_ADMINUNIT) {
                    url = konfigurasi.URL_GET_ALLBYUNIT + mNipLama + "?api_token=";
                } else {
                    url = konfigurasi.URL_GET_ALLSMALL;
                }
                Bundle bundle = new Bundle();
                bundle.putString(INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_FRAGMENTCONTENT, url);
                bundle.putString(INTENT_NIPLAMA, mNipLama);
                bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);

                fragment = new ProfilSeluruhPegawaiFragment();
                fragment.setArguments(bundle);
                fragmentTag = getResources().getString(R.string.title_fragment_profil_seluruh_pegawai);
                openedDrawer = "nav_profile_seluruh_pegawai";
            }
        } else if (id == R.id.nav_presensi) {
            if (openedDrawer != "nav_presensi") {
                // set content dashboard
                Bundle bundle = new Bundle();
                bundle.putString(INTENT_USERTOKEN, mUserToken);
                bundle.putString(INTENT_NAMA, mNama);
                bundle.putString(INTENT_NIPLAMA, mNipLama);
                bundle.putString(INTENT_NIPBARU, mNipBaru);
                bundle.putString(INTENT_FOTO, mFoto);
                bundle.putString(INTENT_NOHP, mNoHp);
                bundle.putString(INTENT_IMEI, mImei);
                bundle.putString(INTENT_EMAIL, mEmail);
                bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);

                fragment = new AbsenFragment();
                fragment.setArguments(bundle);
                fragmentTag = "presensi";
                openedDrawer = "nav_presensi";
            }
        } else if (id == R.id.nav_profile) {
            if (openedDrawer != "nav_profile") {
                Bundle bundle = new Bundle();
                bundle.putString(INTENT_USERTOKEN, mUserToken);
                bundle.putString(INTENT_NIPLAMA, mNipLama);
                bundle.putString(INTENT_NIPBARU, mNipBaru);
                bundle.putString(INTENT_FOTO, mFoto);
                bundle.putString(INTENT_NOHP, mNoHp);
                bundle.putString(INTENT_EMAIL, mEmail);
                bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);

                fragment = new ProfilPegawaiPagerFragment();
                fragment.setArguments(bundle);
                fragmentTag = getResources().getString(R.string.title_fragment_profil_seluruh_pegawai);
                openedDrawer = "nav_profile";
            }
        } else if (id == R.id.nav_izin_cuti) {
            if (openedDrawer != "nav_izin_cuti") {
                if (mRoleIdInt == UserRole.USER_ROLE_SUPERADMIN ||
                        mRoleIdInt == UserRole.USER_ROLE_ADMINUNIT ||
                        mRoleIdInt == UserRole.USER_ROLE_ADMINPUSAT) {
                    // admin
                    Bundle bundle = new Bundle();
                    bundle.putString(INTENT_USERTOKEN, mUserToken);
                    bundle.putString(INTENT_NIPLAMA, mNipLama);
                    bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);
                    bundle.putString(INTENT_NAMA, mNama);
                    bundle.putString(INTENT_FOTO, mFoto);
                    bundle.putString(INTENT_NIPBARU, mNipBaru);
                    bundle.putString(INTENT_NOHP, mNoHp);
                    bundle.putString(INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
                    bundle.putString(INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
                    bundle.putBoolean(INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);

                    fragment = new CutiDashboardAdminFragment();
                    fragment.setArguments(bundle);
                    fragmentTag = getResources().getString(R.string.title_fragment_cuti_dashboard_pegawai);
                    openedDrawer = "nav_izin_cuti";
                } else {
                    // pegawai
                    Bundle bundle = new Bundle();
                    bundle.putString(INTENT_USERTOKEN, mUserToken);
                    bundle.putString(INTENT_NIPLAMA, mNipLama);
                    bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);
                    bundle.putString(INTENT_NAMA, mNama);
                    bundle.putString(INTENT_FOTO, mFoto);
                    bundle.putString(INTENT_NIPBARU, mNipBaru);
                    bundle.putString(INTENT_NOHP, mNoHp);

                    fragment = new CutiDashboardPegawaiFragment();
                    fragment.setArguments(bundle);
                    fragmentTag = getResources().getString(R.string.title_fragment_cuti_dashboard_pegawai);
                    openedDrawer = "nav_izin_cuti";
                }
            }
        } else if (id == R.id.nav_tugas_belajar) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_izin_belajar) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_izin_kantor) {
            if (openedDrawer != "nav_izin_kantor") {
                if (mRoleIdInt == UserRole.USER_ROLE_SUPERADMIN ||
                        mRoleIdInt == UserRole.USER_ROLE_ADMINUNIT ||
                        mRoleIdInt == UserRole.USER_ROLE_ADMINPUSAT) {
                    // admin
                    Bundle bundle = new Bundle();
                    bundle.putString(INTENT_USERTOKEN, mUserToken);
                    bundle.putString(INTENT_NIPLAMA, mNipLama);
                    bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);
                    bundle.putString(INTENT_NAMA, mNama);
                    bundle.putString(INTENT_FOTO, mFoto);
                    bundle.putString(INTENT_NIPBARU, mNipBaru);
                    bundle.putString(INTENT_NOHP, mNoHp);
                    bundle.putString(INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
                    bundle.putString(INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
                    bundle.putBoolean(INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);

                    fragment = new IzinKantorDashboardAdminFragment();
                    fragment.setArguments(bundle);
                    fragmentTag = getResources().getString(R.string.title_fragment_izin_kantor_dashboard_pegawai);
                    openedDrawer = "nav_izin_kantor";
                } else {
                    // pegawai
                    Bundle bundle = new Bundle();
                    bundle.putString(INTENT_USERTOKEN, mUserToken);
                    bundle.putString(INTENT_NIPLAMA, mNipLama);
                    bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);
                    bundle.putString(INTENT_NAMA, mNama);
                    bundle.putString(INTENT_FOTO, mFoto);
                    bundle.putString(INTENT_NIPBARU, mNipBaru);
                    bundle.putString(INTENT_NOHP, mNoHp);

                    fragment = new IzinKantorDashboardPegawaiFragment();
                    fragment.setArguments(bundle);
                    fragmentTag = getResources().getString(R.string.title_fragment_izin_kantor_dashboard_pegawai);
                    openedDrawer = "nav_izin_kantor";
                }
            }
        } else if (id == R.id.nav_izin_luar_negeri) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_surat_keluar) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_logout) {
            signOut();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content_fragment_area, fragment, fragmentTag);
//            fragmentTransaction.addToBackStack(null);
            fragmentManager.popBackStack();
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut() {
        getJSON();
    }

    private void parseJSON() {
        JSONObject jsonObject = null;
        Intent logoutIntent = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            String message = jsonObject.getString("success");
            if (message.equals("true")) {
                logoutMessage = "logout sukses";
                editor.putBoolean(PassedIntent.ISLOGGEDIN, false);
                editor.apply();
                Toast.makeText(getApplicationContext(), logoutMessage, Toast.LENGTH_SHORT).show();
                logoutIntent = new Intent(DashboardActivity.this, LoginActivity.class);
                logoutIntent.putExtra(INTENT_USERNAME, mUsername);
                logoutIntent.putExtra(INTENT_PASSWORD, mPassword);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logoutIntent);
                finish();
            } else {
                logoutMessage = "logout gagal";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(logoutIntent);
        finish();
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                isQuitting = true;
                loading = ProgressDialog.show(DashboardActivity.this, null, null, false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                isQuitting = false;
                loading.dismiss();
                JSON_STRING = s;
                parseJSON();
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

    private void initiateExit(boolean status) {
        if (status) {
            konfirmasiKeluarLayout.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
        } else {
            konfirmasiKeluarLayout.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        }
    }
}
