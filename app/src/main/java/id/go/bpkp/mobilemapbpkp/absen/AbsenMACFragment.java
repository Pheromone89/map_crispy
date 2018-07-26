package id.go.bpkp.mobilemapbpkp.absen;

import android.Manifest;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.YoYo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 11/04/2018.
 */

public class AbsenMACFragment extends Fragment {

    protected static final float LOCSOUTH = -6.19225f;
    protected static final float LOCEAST = 106.87157f;
    protected static final float LOCNORTH = -6.19290f;
    protected static final float LOCWEST = 106.87018f;
    // 1^0 dalam kilometer
    protected static final float KONSTAN = 111322f;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isSudahAbsenPagi, isSudahAbsenSore;
    boolean coarseLoc, fineLoc, isLatitudeOnPosition, isLongitudeOnPosition;
    float userLat, userLong, devlatfloat, devlongfloat;
    Button refreshButton;
    CardView absenCardView, absenWarningCardView, absenLoadingCardView, absenInactiveCardView;
    int currentHour;
    // NEW //
    int delay = 1000; //milliseconds
    Handler handler = new Handler();
    private View rootView;
    private TextView
            panelHeader,
            jamDatangView,
            jamPulangView,
            hariTanggalView,
            statusMessageView,
            statusDatangView,
            statusPulangView;
    private ImageView
            refreshLocationButton,
            historyButton,
            fingerIcon,
            infoButton;
    private CardView
            datangCardView,
            pulangCardView,
            statusCardView;
    private String
            JSON_STRING;
    private String
            mNama,
            mNipBaru,
            mNipLama,
            mUserToken,
            mFoto,
            mImei,
            mContentUrl;
    private String
            hari,
            jamDatang,
            jamPulang,
            statusMessage,
            hariTanggal,
            statusDatang,
            statusPulang,
            statusAbsen;
    private int
            mRoleId,
            skenarioDatang,
            skenarioPulang;
    private SimpleDateFormat
            format;
    private LinearLayout rootLayout;
    private ProgressBar rootProgressBar;
    private YoYo.YoYoString ropeDashboardPegawai;
    private String macAdress, macMAP;
    private TextView statusText;
    private CardView statusCard;

    public AbsenMACFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_presensi_mac_dashboard, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_absen);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        //bundle dari fragment sebelumnya
        //URL foto
        mFoto = this.getArguments().getString(PassedIntent.INTENT_FOTO);
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        // nama
        mNama = this.getArguments().getString(PassedIntent.INTENT_NAMA);
        //nip tanpa spasi
        mNipBaru = this.getArguments().getString(PassedIntent.INTENT_NIPBARU);
        //nip lama tanpa spasi
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        //content url
        mContentUrl = konfigurasi.URL_GET_ABSEN;
        //role id
        mRoleId = this.getArguments().getInt(PassedIntent.INTENT_ROLEIDINT);
        // IMEI
        mImei = this.getArguments().getString(PassedIntent.INTENT_IMEI);
        // MacAdress
        macAdress = getMacAdress();
        macMAP = "14:91:82:61:13:c1";


        format = new SimpleDateFormat("HH:mm:ss");

        isSudahAbsenPagi = sharedPreferences.getBoolean("sudah_absen_pagi", false);
        isSudahAbsenSore = sharedPreferences.getBoolean("sudah_absen_sore", false);
        Calendar rightNow = Calendar.getInstance();
        currentHour = rightNow.get(Calendar.HOUR_OF_DAY);

        Date date = new Date();
        String languageToLoad = "ind"; // your language
        Locale locale = new Locale(languageToLoad, "IDN");
        Locale.setDefault(locale);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", locale);

        hari = simpleDateFormat.format(date);

        getLatitudeLongitude();

        handler.postDelayed(new Runnable() {
            public void run() {
                setLayout(getMacAdress());
                handler.postDelayed(this, delay);
            }
        }, delay);

        initiateView();
        setLayout(getMacAdress());
//        getJSON();
//        setOnClickMethod();
//        revealLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLatitudeLongitude();
        setLayout(getMacAdress());
    }

    private void initiateView() {
        statusText = rootView.findViewById(R.id.dashboard_presensi_status);
        statusCard = rootView.findViewById(R.id.dashboard_presensi_status_card);

        statusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayout(getMacAdress());
            }
        });
    }

    private void populateView() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_absen);
        super.onCreateOptionsMenu(menu, inflater);
    }

    ///////////////////////
    /// NEW MAC FUNCTION //
    ///////////////////////

    private String getMacAdress() {
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String mac = wifiInfo.getBSSID();
        if (mac != null) {
            return mac;
        } else {
            return "-";
        }
    }

    private void getLatitudeLongitude() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            coarseLoc = true;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            fineLoc = true;
        }
        if (coarseLoc && fineLoc) {
            SingleShotLocationProvider.requestSingleUpdate(getActivity(),
                    new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                            userLat = location.latitude;
                            userLong = location.longitude;
                        }
                    });

        } else {
            userLat = 0f;
            userLong = 0f;
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
            Toast.makeText(getActivity(), "tolong izinkan MAP BPKP untuk mengakses GPS anda", Toast.LENGTH_SHORT).show();
        }
    }

    private String checkNull(String string) {
        if (string.equals("null")) {
            return "-";
        } else {
            return string;
        }
    }

    private void setLayout(String macAdress) {
        if (macAdress.equals(macMAP)) {
            statusText.setBackgroundResource(R.color.green);
            statusText.setText("Anda terhubung dengan jaringan MAP");
            statusText.setTextColor(getResources().getColor(R.color.whiteAlternate));
            statusCard.setVisibility(View.GONE);
        } else {
            statusCard.setVisibility(View.VISIBLE);
            statusText.setBackgroundResource(R.color.red);
            statusText.setText("Anda tidak terhubung dengan jaringan kantor");
            statusText.setTextColor(getResources().getColor(R.color.whiteAlternate));
        }
    }

    ///////////////////
    /// no means no ///
    ///////////////////

}