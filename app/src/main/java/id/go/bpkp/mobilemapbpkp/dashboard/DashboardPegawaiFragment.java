package id.go.bpkp.mobilemapbpkp.dashboard;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 26/01/2018.
 */

public class DashboardPegawaiFragment extends Fragment {

    protected static final float LOCSOUTH = -6.19225f;
    protected static final float LOCEAST = 106.87157f;
    protected static final float LOCNORTH = -6.19290f;
    protected static final float LOCWEST = 106.87018f;
    //    protected static final float LOCSOUTH = 0f;
//    protected static final float LOCEAST = 0f;
//    protected static final float LOCNORTH = 0f;
//    protected static final float LOCWEST = 0f;
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
    private View rootView;
    private TextView
            panelHeader,
            jamDatangView,
            jamDatangAltView,
            jamPulangAltView,
            jamPulangView,
            jamEfektifView,
            macAdressView,
            profilNamaView,
            profilNipView,
            hariTanggalView,
            statusMessageView,
            statusDatangView,
            statusPulangView;
    private ImageView
            profilFotoView,
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
            jamEfektif,
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
    private LinearLayout rootLayout, datangBackground, pulangBackground;
    private ProgressBar rootProgressBar;
    private YoYo.YoYoString ropeDashboardPegawai;
    // panel cardview
    private LinearLayout panelProfilCardView, panelJaringanCardView, panelPresensiCardView, panelTunjanganCardView, panelCutiCardView;
    private YoYo.YoYoString ropePanelProfil, ropePanelJaringan, ropePanelPresensi, ropePanelTunjangan, ropePanelCuti;

    public DashboardPegawaiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_pegawai, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_dashboard_pegawai);

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

        initiateView();
        showPanel(konfigurasi.DASHBOARD_PANEL_PRESENSI);
        showPanel(konfigurasi.DASHBOARD_PANEL_PROFIL);
        showPanel(konfigurasi.DASHBOARD_PANEL_JARINGAN);
        showPanel(konfigurasi.DASHBOARD_PANEL_CUTI);
        showPanel(konfigurasi.DASHBOARD_PANEL_TUNJANGAN);
        populateView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_dashboard_pegawai);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initiateView() {
        rootLayout = rootView.findViewById(R.id.dashboard_pegawai_layout);
        rootProgressBar = rootView.findViewById(R.id.dashboard_pegawai_progress_bar);
        rootProgressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);
    }

    private void populateView() {
        rootLayout.setVisibility(View.VISIBLE);
        rootProgressBar.setVisibility(View.GONE);
        ropeDashboardPegawai = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(rootLayout);
    }

    private String checkNull(String string) {
        if (string.equals("null")) {
            return "-";
        } else {
            return string;
        }
    }

    public String getMacId() {
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getBSSID();
    }

    private void showPanel(int panelId) {
        switch (panelId) {
            case konfigurasi.DASHBOARD_PANEL_PROFIL:
                initiateViewPanelProfil();
                populatePanelProfil();
                break;
            case konfigurasi.DASHBOARD_PANEL_JARINGAN:
                initiateViewPanelJaringan();
                populatePanelJaringan();
                break;
            case konfigurasi.DASHBOARD_PANEL_PRESENSI:
                initiateViewPanelPresensi();
                getJSONPresensi();
                break;
            case konfigurasi.DASHBOARD_PANEL_TUNJANGAN:
                initiateViewPanelTunjangan();
                populatePanelTunjangan();
                break;
            case konfigurasi.DASHBOARD_PANEL_CUTI:
                initiateViewPanelCuti();
                populatePanelCuti();
                break;
        }
    }

    private void initiateViewPanelProfil() {
        // profil
        panelProfilCardView = rootView.findViewById(R.id.dashboard_panel_profil);
        panelProfilCardView.setVisibility(View.GONE);
        profilFotoView = rootView.findViewById(R.id.dashboard_profil_foto);
        profilNamaView = rootView.findViewById(R.id.dashboard_profil_nama_val);
        profilNipView = rootView.findViewById(R.id.dashboard_profil_nip_val);
    }

    private void populatePanelProfil() {
        Picasso.with(getActivity()).load(mFoto).into(profilFotoView);
        profilNamaView.setText(mNama);
        profilNipView.setText(mNipBaru);
        panelProfilCardView.setVisibility(View.VISIBLE);
        ropePanelProfil = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelProfilCardView);
    }

    private void initiateViewPanelJaringan() {
        // mac address
        panelJaringanCardView = rootView.findViewById(R.id.dashboard_panel_jaringan);
        panelJaringanCardView.setVisibility(View.GONE);
        macAdressView = rootView.findViewById(R.id.dashboard_presensi_mac_address_val);
    }

    private void populatePanelJaringan() {
        String macId;
        if (getMacId() == null) {
            macId = "Anda tidak terhubung jaringan";
        } else {
            macId = getMacId();
        }
        macAdressView.setText(macId);
        panelJaringanCardView.setVisibility(View.VISIBLE);
        ropePanelJaringan = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelJaringanCardView);
    }

    private void initiateViewPanelPresensi() {
        // alt presensi
        panelPresensiCardView = rootView.findViewById(R.id.dashboard_panel_presensi);
        panelPresensiCardView.setVisibility(View.GONE);
        jamDatangAltView = rootView.findViewById(R.id.dashboard_presensi_datang_val_alt);
        jamPulangAltView = rootView.findViewById(R.id.dashboard_presensi_pulang_val_alt);
    }

    private void populatePanelPresensi() {
        jamDatangAltView.setText(jamDatang);
        jamPulangAltView.setText(jamPulang);

        switch (statusDatang) {
            case "Anda Tidak Absen Datang":
                jamDatangAltView.setTextColor(getResources().getColor(R.color.red));
                statusDatang = "Anda belum merekam kehadiran";
                break;
            case "Anda Hadir Tepat Waktu":
                jamDatangAltView.setTextColor(getResources().getColor(R.color.green));
                statusDatang = "Anda hadir tepat waktu";
                break;
            case "Anda Datang Terlambat (DT)":
                jamDatangAltView.setTextColor(getResources().getColor(R.color.orange));
                statusDatang = "Anda datang terlambat (DT)";
                break;
            default:
                jamDatangAltView.setTextColor(getResources().getColor(android.R.color.black));
                datangBackground.setVisibility(View.GONE);
                break;
        }
        switch (statusPulang) {
            case "Anda Belum Absen Pulang":
                jamPulangAltView.setTextColor(getResources().getColor(R.color.red));
                statusPulang = "Anda belum merekam kepulangan";
                break;
            case "Anda Tidak Absen Pulang":
                jamPulangAltView.setTextColor(getResources().getColor(R.color.red));
                statusPulang = "Anda tidak merekam kepulangan";
                break;
            case "Anda Pulang Cepat (PC)":
                jamPulangAltView.setTextColor(getResources().getColor(R.color.orange));
                statusPulang = "Anda pulang lebih cepat (PC)";
                break;
            case "Anda Pulang Tepat Waktu":
                jamPulangAltView.setTextColor(getResources().getColor(R.color.green));
                statusPulang = "Anda pulang tepat waktu";
                break;
            default:
                jamPulangAltView.setTextColor(getResources().getColor(android.R.color.black));
                datangBackground.setVisibility(View.GONE);
                break;
        }

        panelPresensiCardView.setVisibility(View.VISIBLE);
        ropePanelPresensi = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelPresensiCardView);
    }

    private void getJSONPresensi() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONPresensi();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(mContentUrl + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONPresensi() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            jsonObject = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
            hariTanggal = hari + ", " + jsonObject.getString(konfigurasi.TAG_ABSEN_TANGGAL);
            jamDatang = checkNull(jsonObject.getString(konfigurasi.TAG_ABSEN_DATANG));
            jamPulang = checkNull(jsonObject.getString(konfigurasi.TAG_ABSEN_PULANG));
            jamEfektif = checkNull(jsonObject.getString(konfigurasi.TAG_ABSEN_WAKTUKERJA));
            statusDatang = jsonObject.getString(konfigurasi.TAG_ABSEN_STATUSDATANG);
            statusPulang = jsonObject.getString(konfigurasi.TAG_ABSEN_STATUSPULANG);
            statusAbsen = jsonObject.getString(konfigurasi.TAG_ABSEN_WAKTUKERJA);

            if (jamDatang.equals("-")) {
                isSudahAbsenPagi = false;
            } else {
                isSudahAbsenPagi = true;
            }
            if (jamPulang.equals("-")) {
                isSudahAbsenSore = false;
            } else {
                isSudahAbsenSore = true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Gagal menarik data presensi", Toast.LENGTH_SHORT).show();
        }
        populatePanelPresensi();
    }

    private void initiateViewPanelTunjangan() {
        // mac address
        panelTunjanganCardView = rootView.findViewById(R.id.dashboard_panel_tunjangan);
        panelTunjanganCardView.setVisibility(View.GONE);
    }

    private void populatePanelTunjangan() {
        panelTunjanganCardView.setVisibility(View.VISIBLE);
        ropePanelTunjangan = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelTunjanganCardView);
    }

    private void initiateViewPanelCuti() {
        // mac address
        panelCutiCardView = rootView.findViewById(R.id.dashboard_panel_cuti);
        panelCutiCardView.setVisibility(View.GONE);
    }

    private void populatePanelCuti() {
        panelCutiCardView.setVisibility(View.VISIBLE);
        ropePanelCuti = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelCutiCardView);
    }

}
