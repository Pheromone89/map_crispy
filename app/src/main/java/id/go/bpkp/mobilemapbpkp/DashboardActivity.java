package id.go.bpkp.mobilemapbpkp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String
            mNama,
            mNipBaru,
            mNipLama,
            mRoleId,
            mUserToken,
            mFoto,
            openedDrawer;
    int
            mRoleIdInt;
    String
            fragmentTag = null;
    MenuItem
            profilSemuaPegawaiMenu;
    Menu
            rootMenu;
    boolean
            isWannaQuit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        //intent dashboard
        Intent dashboardIntent = getIntent();
        mNama = dashboardIntent.getStringExtra(LoginActivity.NAMA);
        // nip baru yg digabung
        mNipBaru = dashboardIntent.getStringExtra(LoginActivity.USERNAME);
        mNipLama = dashboardIntent.getStringExtra(LoginActivity.NIPLAMA);
        mRoleId = dashboardIntent.getStringExtra(LoginActivity.ROLEID);
        mRoleIdInt = Integer.parseInt(mRoleId);
        mUserToken = dashboardIntent.getStringExtra(LoginActivity.API_TOKEN);
        mFoto = "http://118.97.51.140:10001/map/public/foto/"+mNipLama+".gif";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set content berdasarkan role
        String contentUrl;
        if( mRoleIdInt == 1 || mRoleIdInt == 2  || mRoleIdInt == 3 || mRoleIdInt == 5 || mRoleIdInt == 7 ){
            contentUrl = konfigurasi.URL_GET_DASHBOARD_CONTENT_ADMIN;
        } else {
            contentUrl = konfigurasi.URL_GET_DASHBOARD_CONTENT_USER;
        }
        //initiate dashboard
        DashboardFragmentAdmin dashboardFragment = new DashboardFragmentAdmin();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String titleDashboard = getResources().getString(R.string.title_fragment_dashboard_admin);
        // set content dashboard
        Bundle bundle = new Bundle();
        bundle.putString("user_token", mUserToken);
        bundle.putString("content_url", contentUrl);
        bundle.putString("nip_lama", mNipLama);
        bundle.putString("username", mNipBaru);
        bundle.putString("foto", mFoto);
        dashboardFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.content_fragment_area, dashboardFragment, titleDashboard);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //ngeset header
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navigationHeader = navigationView.getHeaderView(0);
        TextView navHeaderNamaView = navigationHeader.findViewById(R.id.navigation_drawer_header_name);
        TextView navHeaderNipView = navigationHeader.findViewById(R.id.navigation_drawer_header_nip);
        ImageView navHeaderProficView = navigationHeader.findViewById(R.id.navigation_drawer_header_profile_picture);
        Picasso.with(DashboardActivity.this).load(mFoto).into(navHeaderProficView);
        navHeaderNamaView.setText(mNama+" (role :"+mRoleId+")");
        navHeaderNipView.setText(mNipBaru);

        // if admin
        profilSemuaPegawaiMenu = navigationView.getMenu().getItem(0);
        if (mRoleIdInt == 4 || mRoleIdInt == 6 || mRoleIdInt == 8){
            profilSemuaPegawaiMenu.setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() == 0){
            Toast.makeText(this, "sentuh KEMBALI sekali lagi untuk logout", Toast.LENGTH_SHORT).show();
            isWannaQuit = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isWannaQuit){
                        signOut(getApplicationContext());
                    }
                    isWannaQuit = true;
                }
            }, 1000);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
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

        if (id == R.id.nav_profile_seluruh_pegawai) {
            if (openedDrawer != "navigation_profile") {
                String url;
                if (mRoleIdInt == 1 || mRoleIdInt == 2){
                    url = konfigurasi.URL_GET_ALLSMALL;
                } else if (mRoleIdInt == 3){
                    url = konfigurasi.URL_GET_ALLBYUNIT+mNipLama+"?api_token=";
                } else {
                    url = konfigurasi.URL_GET_ALLSMALL;
                }
                Bundle bundle = new Bundle();
                bundle.putString("user_token", mUserToken);
                bundle.putString("fragment_url", url);
                bundle.putString("extra_label", "");

                fragment = new ProfilSeluruhPegawaiFragment();
                fragment.setArguments(bundle);
                fragmentTag = getResources().getString(R.string.title_fragment_profil_seluruh_pegawai);
                openedDrawer = "nav_profile_seluruh_pegawai";
            }
        } else if (id == R.id.nav_profile) {
            if(openedDrawer != "navigation_profile"){
                Bundle bundle = new Bundle();
                bundle.putString("user_token", mUserToken);
                bundle.putString("nip_lama", mNipLama);
                bundle.putString("username", mNipBaru);
                bundle.putString("foto", mFoto);

                fragment = new ProfilPegawaiIndividuFragment();
                fragment.setArguments(bundle);
                fragmentTag = getResources().getString(R.string.title_fragment_profil_seluruh_pegawai);
                openedDrawer = "nav_profile";
            }
        } else if (id == R.id.nav_izin_cuti) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_tugas_belajar) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_izin_belajar) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_izin_kantor) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_izin_luar_negeri) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_surat_keluar) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Menu ini belum diimplemenetasikan", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_logout) {
            signOut(getApplicationContext());
        }

        if (fragment != null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content_fragment_area, fragment, fragmentTag);
            fragmentTransaction.addToBackStack(fragmentTag);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void signOut(Context context){
        SharedPreferences prefs = context.getSharedPreferences(Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("username");
        editor.apply();



        Toast.makeText(getApplicationContext(), "logout berhasil", Toast.LENGTH_SHORT).show();

        Intent logoutIntent = new Intent(DashboardActivity.this, LoginActivity.class);
        logoutIntent.putExtra("username", mNipBaru);
        startActivity(logoutIntent);
        finish();
    }
}
