package id.go.bpkp.mobilemapbpkp.dashboard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.absen.AbsenBawahan;
import id.go.bpkp.mobilemapbpkp.absen.AbsenBawahanFragment;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDaftarPersetujuanFragment;
import id.go.bpkp.mobilemapbpkp.izinkantor.IzinKantorDaftarPersetujuanFragment;
import id.go.bpkp.mobilemapbpkp.konfigurasi.FragmentBundles;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.SavedInstances;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.monitoring.PegawaiSingkat;

import static id.go.bpkp.mobilemapbpkp.dashboard.DashboardPanel.dashboardPanelList;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTO;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPLAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERTOKEN;

/**
 * Created by ASUS on 26/01/2018.
 */

public class DashboardPegawaiFragment extends Fragment {

    public static String PACKAGE_NAME;

    protected static final float LOCSOUTH = -6.19225f;
    protected static final float LOCEAST = 106.87157f;
    protected static final float LOCNORTH = -6.19290f;
    protected static final float LOCWEST = 106.87018f;
    // 1^0 dalam kilometer
    protected static final float KONSTAN = 111322f;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isSudahAbsenPagi, isSudahAbsenSore;
    private View rootView;
    private TextView
            jamDatangAltView,
            jamDatangHotspotAltView,
            jamPulangAltView,
            jamPulangHotspotAltView,
            macAdressView,
            profilNamaView,
            profilNipView;
    private ImageView
            profilFotoView;
    private String
            JSON_STRING,
            mNama,
            mNipBaru,
            mNipLama,
            mUserToken,
            mFoto,
            mImei,
            mContentUrl;
    private boolean isHut, isAtasan;
    private String
            jamDatang,
            jamDatangHotspot,
            jamPulang,
            jamPulangHotspot,
            jamEfektifHotspot,
            statusDatang,
            statusDatangHotspot,
            statusPulang,
            statusPulangHotspot;
    private int
            mRoleId;
    private LinearLayout rootLayout;
    private ProgressBar rootProgressBar;
    private FloatingActionButton fab;
    private LinearLayout panelOption;
    // panel cardview
    private int currentSelectedPanel = 999, currentPanelShowing;
    private LinearLayout panelProfilCardView, panelJaringanCardView, panelPresensiCardView, panelHotspotCardView, panelTunjanganCardView, panelCutiCardView, panelNotifikasiAtasanCardView;
    private LinearLayout panelProfilBackground, panelJaringanBackground, panelPresensiBackground, panelHotspotBackground, panelTunjanganBackground, panelCutiBackground, panelNotifikasiAtasanBackground;
    // khusus ultah
    private LinearLayout panelUltahCardView;
    private ImageView fotoUltah;
    // end ultah
    private YoYo.YoYoString ropeAnimation;
    private LayoutInflater panelLayouInflater;
    private LinearLayout panelLinearLayout;
    private String hakCutiT, hakCutiTMin1, hakCutiTMin2, jumlahHakCuti, saldoCuti, cutiTerpakai;
    private TextView panelCutiSaldo, panelCutiHak, panelCutiTerpakai;
    Calendar c;
    int year;
    int prevMonth;
    String time;
    // panel tukin
    private String tukinBulan, tukinTahun, tukinGrade, tukinPersenPotongan, tukinDasar, tukinPotongan, tukinBersih, tukinPeriode;
    private TextView tukinGradeTextView, tukinDasarTextView, tukinPotonganTextView, tukinBersihTextView, tukinPeriodeTextView;
    // panel notif atasan
    private String jumlahProsesCuti, jumlahProsesIzinKantor;
    private TextView prosesPersetujuanCutiTextView, prosesPersetujuanIzinKantorTextView, presensiTextView;
    private int jumlahDatang, jumlahBawahan;
    // test backgourn array
    private LinearLayout[] background;
    private int[] panelDisabled;

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
        PACKAGE_NAME = getActivity().getApplicationContext().getPackageName();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        //bundle dari fragment sebelumnya
        //nip lama tanpa spasi
        mNipLama = SavedInstances.nipLama;
        //URL foto
        mFoto = PassedIntent.getFoto(getActivity(), mNipLama);
        //login token
        mUserToken = SavedInstances.userToken;
        // nama
        mNama = SavedInstances.name;
        //nip tanpa spasi
        mNipBaru = SavedInstances.nipBaru;
        //content url
        mContentUrl = konfigurasi.URL_GET_ABSEN;
        //role id
        mRoleId = SavedInstances.roleId;
        // HUT?
        isHut = SavedInstances.isHut.equals("true");
        // atasan
        isAtasan = SavedInstances.isAtasan.equals("true");

//        TextView textView = rootView.findViewById(R.id.debug);
//        textView.setText(
//                "list pegawai: " + PegawaiSingkat.pegawaiSingkatList.size() + "\n" +
//                        "list absen bawahan: " + AbsenBawahan.absenBawahanList.size()
//        );


        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        // bulan diindeks dari 0
        prevMonth = c.get(Calendar.MONTH) + 1;
        time = "/" + prevMonth + "/" + year;

        initiateView();
        populateView();

        // list background ke array
        background = new LinearLayout[dashboardPanelList.size()];
        for (int i = 0; i < dashboardPanelList.size(); i++) {
            background[i] = dashboardPanelList.get(i).getPanelBackground(getActivity(), rootView);
        }

        // panel didisable
        // list panel yg ingin didisable
        panelDisabled = new int[]{
                konfigurasi.DASHBOARD_PANEL_JARINGAN
        };

        // initiate panel dan option panel (tombol hide/show) dari setting lokal
        for (int i = 0; i < dashboardPanelList.size(); i++) {
            Boolean status = sharedPreferences.getBoolean("dashboard_panel_" + i, true);
            CardView c;
            LinearLayout progressBar;
            for (int x = 0; x < panelDisabled.length; x++) {
                if (dashboardPanelList.get(i).getPanelId() != panelDisabled[x]) {
                    c = dashboardPanelList.get(i).getPanelOption(getActivity(), rootView);
                    progressBar = dashboardPanelList.get(i).getPanelOptionProgressbar(getActivity(), rootView);
                    if (status) {
                        showPanel(dashboardPanelList.get(i).getPanelId());
                    }
                    if (c != null) {
                        c.setVisibility(View.VISIBLE);
                    }
                    setPanelOption(dashboardPanelList.get(i).getPanelId());
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        }

        // hide option panel non-atasan
        if (!isAtasan) {
            // masukkan panel khusus atasan di sini:
            int[] panelAtasan = new int[]{
                    konfigurasi.DASHBOARD_PANEL_NOTIFIKASIATASAN
            };
            for (int i = 0; i < panelAtasan.length; i++) {
                CardView c = dashboardPanelList.get(panelAtasan[i]).getPanelOption(getActivity(), rootView);
                c.setVisibility(View.GONE);
            }
        }
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
        panelLinearLayout = rootView.findViewById(R.id.dashboard_panel_linear_layout);
        rootProgressBar = rootView.findViewById(R.id.dashboard_pegawai_progress_bar);
        fab = rootView.findViewById(R.id.dashboard_pegawai_fab);
        // panel option
        panelOption = rootView.findViewById(R.id.dashboard_pegawai_panel_option);

        rootProgressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);
    }

    private void populateView() {
        rootLayout.setVisibility(View.VISIBLE);
        rootProgressBar.setVisibility(View.GONE);
        ropeAnimation = YoYo.with(Techniques.FadeIn)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(rootLayout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShown = panelOption.isShown();
                if (isShown) {
                    ropeAnimation = YoYo.with(Techniques.SlideOutDown)
                            .duration(konfigurasi.animationDurationShort)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(panelOption);
                    ropeAnimation = YoYo.with(Techniques.ZoomOut)
                            .duration(konfigurasi.animationDurationShort)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(fab);
                    panelOption.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            panelOption.setVisibility(View.GONE);
                            ropeAnimation = YoYo.with(Techniques.ZoomIn)
                                    .duration(konfigurasi.animationDurationShort)
                                    .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                                    .interpolate(new AccelerateDecelerateInterpolator())
                                    .playOn(fab);
                        }

                    }, konfigurasi.animationDurationShort);
                } else {
                    ropeAnimation = YoYo.with(Techniques.ZoomOut)
                            .duration(200)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(fab);
                    panelOption.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            panelOption.setVisibility(View.VISIBLE);
                            ropeAnimation = YoYo.with(Techniques.SlideInUp)
                                    .duration(konfigurasi.animationDurationShort)
                                    .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                                    .interpolate(new AccelerateDecelerateInterpolator())
                                    .playOn(panelOption);
                        }

                    }, 200);
                    panelOption.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ropeAnimation = YoYo.with(Techniques.ZoomIn)
                                    .duration(konfigurasi.animationDurationShort)
                                    .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                                    .interpolate(new AccelerateDecelerateInterpolator())
                                    .playOn(fab);
                        }

                    }, konfigurasi.animationDurationShort);
                }
            }
        });
    }

    private String checkNull(String string, String placeholder) {
        if (string.equals("null")) {
            return placeholder;
        } else {
            return string;
        }
    }

    private void putDelay(final View view) {
        view.setEnabled(false);

        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                });
            }
        }, konfigurasi.animationDurationShort);
    }

    private void setPanelOption(final int panelId) {
        final CardView option = dashboardPanelList.get(panelId).getPanelOption(getActivity(), rootView);
        final LinearLayout panelCardView = dashboardPanelList.get(panelId).getPanelCardView(getActivity(), rootView);

        if (option != null) {
            option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (panelCardView == null) {
                        showPanel(panelId);
                    } else if (panelCardView.isShown()) {
                        deletePanel(panelId);
                    } else {
                        showPanel(panelId);
                    }
                    putDelay(v);
                }
            });
        }
    }

    private void setHighlight(int panelId) {
        if (currentSelectedPanel < dashboardPanelList.size()) {
            LinearLayout background = dashboardPanelList.get(currentSelectedPanel).getPanelBackground(getActivity(), rootView);
            if (background != null) {
                background.setBackgroundResource(R.color.whiteAlternate);
            }
        }

        if (panelId != currentSelectedPanel) {
            switch (panelId) {
                case konfigurasi.DASHBOARD_PANEL_PROFIL:
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_PROFIL;
                    dashboardPanelList
                            .get(currentSelectedPanel)
                            .getPanelBackground(getActivity(), rootView)
                            .setBackgroundResource(R.color.whiteAlternateDark);
                    break;
                case konfigurasi.DASHBOARD_PANEL_JARINGAN:
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_JARINGAN;
                    dashboardPanelList
                            .get(currentSelectedPanel)
                            .getPanelBackground(getActivity(), rootView)
                            .setBackgroundResource(R.color.whiteAlternateDark);
                    break;
                case konfigurasi.DASHBOARD_PANEL_PRESENSI:
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_PRESENSI;
                    dashboardPanelList
                            .get(currentSelectedPanel)
                            .getPanelBackground(getActivity(), rootView)
                            .setBackgroundResource(R.color.whiteAlternateDark);
                    break;
                case konfigurasi.DASHBOARD_PANEL_TUNJANGAN:
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_TUNJANGAN;
                    dashboardPanelList
                            .get(currentSelectedPanel)
                            .getPanelBackground(getActivity(), rootView)
                            .setBackgroundResource(R.color.whiteAlternateDark);
                    break;
                case konfigurasi.DASHBOARD_PANEL_CUTI:
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_CUTI;
                    dashboardPanelList
                            .get(currentSelectedPanel)
                            .getPanelBackground(getActivity(), rootView)
                            .setBackgroundResource(R.color.whiteAlternateDark);
                    break;
                case konfigurasi.DASHBOARD_PANEL_NOTIFIKASIATASAN:
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_NOTIFIKASIATASAN;
                    dashboardPanelList
                            .get(currentSelectedPanel)
                            .getPanelBackground(getActivity(), rootView)
                            .setBackgroundResource(R.color.whiteAlternateDark);
                    break;
                case konfigurasi.DASHBOARD_PANEL_ULTAH:
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_ULTAH;
                    break;
                case konfigurasi.DASHBOARD_PANEL_HOTSPOT:
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_HOTSPOT;
                    dashboardPanelList
                            .get(currentSelectedPanel)
                            .getPanelBackground(getActivity(), rootView)
                            .setBackgroundResource(R.color.whiteAlternateDark);
                    break;
            }
        } else {
            currentSelectedPanel = 999;
        }
    }

    public String getMacId() {
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getBSSID();
    }

    private void showPanel(final int panelId) {
        panelLayouInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        panelLayouInflater = LayoutInflater.from(getActivity());

        switch (panelId) {
            case konfigurasi.DASHBOARD_PANEL_PROFIL:
                currentPanelShowing = konfigurasi.DASHBOARD_PANEL_PROFIL;
                dashboardPanelList
                        .get(currentPanelShowing)
                        .getPanelOption(getActivity(), rootView)
                        .setVisibility(View.VISIBLE);
                initiateViewPanelProfil();
                populatePanelProfil();
                break;
            case konfigurasi.DASHBOARD_PANEL_JARINGAN:
                currentPanelShowing = konfigurasi.DASHBOARD_PANEL_JARINGAN;
                dashboardPanelList
                        .get(currentPanelShowing)
                        .getPanelOption(getActivity(), rootView)
                        .setVisibility(View.VISIBLE);
                initiateViewPanelJaringan();
                populatePanelJaringan();
                break;
            case konfigurasi.DASHBOARD_PANEL_PRESENSI:
                currentPanelShowing = konfigurasi.DASHBOARD_PANEL_PRESENSI;
                dashboardPanelList
                        .get(currentPanelShowing)
                        .getPanelOption(getActivity(), rootView)
                        .setVisibility(View.VISIBLE);
                initiateViewPanelPresensi();
                getJSONPresensi();
                break;
            case konfigurasi.DASHBOARD_PANEL_TUNJANGAN:
                currentPanelShowing = konfigurasi.DASHBOARD_PANEL_TUNJANGAN;
                dashboardPanelList
                        .get(currentPanelShowing)
                        .getPanelOption(getActivity(), rootView)
                        .setVisibility(View.VISIBLE);
                initiateViewPanelTunjangan();
                getJSONTunjangan();
                break;
            case konfigurasi.DASHBOARD_PANEL_CUTI:
                currentPanelShowing = konfigurasi.DASHBOARD_PANEL_CUTI;
                dashboardPanelList
                        .get(currentPanelShowing)
                        .getPanelOption(getActivity(), rootView)
                        .setVisibility(View.VISIBLE);
                initiateViewPanelCuti();
                getJSONCuti();
                break;
            case konfigurasi.DASHBOARD_PANEL_ULTAH:
                currentPanelShowing = konfigurasi.DASHBOARD_PANEL_ULTAH;
                if (isHut) {
                    initiateViewPanelUltah();
                    populatePanelUltah();
                }
                break;
            case konfigurasi.DASHBOARD_PANEL_NOTIFIKASIATASAN:
                currentPanelShowing = konfigurasi.DASHBOARD_PANEL_NOTIFIKASIATASAN;
                if (isAtasan) {
                    dashboardPanelList
                            .get(currentPanelShowing)
                            .getPanelOption(getActivity(), rootView)
                            .setVisibility(View.VISIBLE);
                    initiateViewPanelNotifikasiAtasan();
                    getJSONNotifikasiAtasanCuti();
                    getJSONNotifikasiAtasanIzinKantor();
                    getJSONNotifikasiAtasanPresensi();
                }
                break;
            case konfigurasi.DASHBOARD_PANEL_HOTSPOT:
                currentPanelShowing = konfigurasi.DASHBOARD_PANEL_HOTSPOT;
                dashboardPanelList
                        .get(currentPanelShowing)
                        .getPanelOption(getActivity(), rootView)
                        .setVisibility(View.VISIBLE);
                initiateViewPanelHotspot();
                getJSONPresensiHotspot();
                break;
        }

        setPanelOption(dashboardPanelList.get(panelId).getPanelId());

        editor.putBoolean("dashboard_panel_" + panelId, true);
        editor.commit();
    }

    private void deletePanel(int panelId) {

        final LinearLayout panelProgressBar = dashboardPanelList.get(panelId).getPanelOptionProgressbar(getActivity(), rootView);
        final LinearLayout panelCardView = dashboardPanelList.get(panelId).getPanelCardView(getActivity(), rootView);
        final CardView optionPanel = dashboardPanelList.get(panelId).getPanelOption(getActivity(), rootView);
        panelProgressBar.setVisibility(View.VISIBLE);

        ropeAnimation = YoYo.with(Techniques.SlideOutRight)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelCardView);
        panelCardView.postDelayed(new Runnable() {
            @Override
            public void run() {
                panelLinearLayout.removeView(panelCardView);
                optionPanel.setCardBackgroundColor(getResources().getColor(R.color.blueBasic));
                panelProgressBar.setVisibility(View.GONE);
            }

        }, konfigurasi.animationDurationShort);
        setPanelOption(dashboardPanelList.get(panelId).getPanelId());
        // setting
        editor.putBoolean("dashboard_panel_" + panelId, false);
        editor.commit();
    }

    ////////////////////////////
    // dashboard panel profil //
    ////////////////////////////

    private void initiateViewPanelProfil() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_profil, null);
        panelLinearLayout.addView(v);

        // profil
        panelProfilBackground = dashboardPanelList.get(konfigurasi.DASHBOARD_PANEL_PROFIL).getPanelBackground(getActivity(), rootView);
        panelProfilCardView = rootView.findViewById(R.id.dashboard_panel_profil);
        panelProfilCardView.setVisibility(View.GONE);
        profilFotoView = rootView.findViewById(R.id.dashboard_profil_foto);
        profilNamaView = rootView.findViewById(R.id.dashboard_profil_nama_val);
        profilNipView = rootView.findViewById(R.id.dashboard_profil_nip_val);
    }

    private void populatePanelProfil() {
        Picasso.with(getActivity()).load(mFoto).into(profilFotoView);
        profilNamaView.setText(mNama);
        String nipInfo = mNipBaru + " / " + mNipLama;
        profilNipView.setText(nipInfo);
        panelProfilCardView.setVisibility(View.VISIBLE);
        ropeAnimation = YoYo.with(Techniques.SlideInRight)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelProfilCardView);

        panelProfilBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_PROFIL);
            }
        });

        //panel
        dashboardPanelList
                .get(konfigurasi.DASHBOARD_PANEL_PROFIL)
                .getPanelOption(getActivity(), rootView)
                .setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    //////////////////////////////
    // dashboard panel jaringan //
    //////////////////////////////

    private void initiateViewPanelJaringan() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_jaringan, null);
        panelLinearLayout.addView(v);

        // mac address
        panelJaringanBackground = dashboardPanelList.get(konfigurasi.DASHBOARD_PANEL_JARINGAN).getPanelBackground(getActivity(), rootView);
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
        ropeAnimation = YoYo.with(Techniques.SlideInRight)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelJaringanCardView);

        panelJaringanBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_JARINGAN);
            }
        });

        //panel
        dashboardPanelList
                .get(konfigurasi.DASHBOARD_PANEL_JARINGAN)
                .getPanelOption(getActivity(), rootView)
                .setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    //////////////////////////////
    // dashboard panel presensi //
    //////////////////////////////

    private void initiateViewPanelPresensi() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_presensi, null);
        panelLinearLayout.addView(v);

        // alt presensi
        panelPresensiBackground = dashboardPanelList.get(konfigurasi.DASHBOARD_PANEL_PRESENSI).getPanelBackground(getActivity(), rootView);
        panelPresensiCardView = rootView.findViewById(R.id.dashboard_panel_presensi);
        panelPresensiCardView.setVisibility(View.GONE);
        jamDatangAltView = rootView.findViewById(R.id.dashboard_presensi_datang_val_alt);
        jamPulangAltView = rootView.findViewById(R.id.dashboard_presensi_pulang_val_alt);
    }

    private void populatePanelPresensi() {
        jamDatangAltView.setText(jamDatang);
        jamPulangAltView.setText(jamPulang);

        panelPresensiBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_PRESENSI);
            }
        });

        switch (statusDatang) {
            case "Anda Tidak KonfirmasiPenugasan Datang":
                jamDatangAltView.setTextColor(getResources().getColor(R.color.red));
                break;
            case "Anda Hadir Tepat Waktu":
                jamDatangAltView.setTextColor(getResources().getColor(R.color.green));
                break;
            case "Anda Datang Terlambat (DT)":
                jamDatangAltView.setTextColor(getResources().getColor(R.color.orange));
                break;
            default:
                jamDatangAltView.setTextColor(getResources().getColor(R.color.blueBasicDark));
                break;
        }
        switch (statusPulang) {
            case "Anda Belum KonfirmasiPenugasan Pulang":
                jamPulangAltView.setTextColor(getResources().getColor(R.color.red));
                break;
            case "Anda Tidak KonfirmasiPenugasan Pulang":
                jamPulangAltView.setTextColor(getResources().getColor(R.color.red));
                break;
            case "Anda Pulang Cepat (PC)":
                jamPulangAltView.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "Anda Pulang Tepat Waktu":
                jamPulangAltView.setTextColor(getResources().getColor(R.color.green));
                break;
            default:
                jamPulangAltView.setTextColor(getResources().getColor(R.color.blueBasicDark));
                break;
        }

        panelPresensiCardView.setVisibility(View.VISIBLE);
        ropeAnimation = YoYo.with(Techniques.SlideInRight)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelPresensiCardView);

        //panel
        dashboardPanelList
                .get(konfigurasi.DASHBOARD_PANEL_PRESENSI)
                .getPanelOption(getActivity(), rootView)
                .setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void getJSONPresensi() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONPresensi();
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.GONE);
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
//            if (jsonObject.getString("success").equals("true")) {
                jsonObject = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
                jamDatang = checkNull(jsonObject.getString(konfigurasi.TAG_ABSEN_DATANG), "-");
                jamPulang = checkNull(jsonObject.getString(konfigurasi.TAG_ABSEN_PULANG), "-");
                statusDatang = jsonObject.getString(konfigurasi.TAG_ABSEN_STATUSDATANG);
                statusPulang = jsonObject.getString(konfigurasi.TAG_ABSEN_STATUSPULANG);

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
                populatePanelPresensi();
//            } else {
//                Toast.makeText(getActivity(), "Gagal menarik data presensi", Toast.LENGTH_SHORT).show();
//            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Gagal menarik data presensi", Toast.LENGTH_SHORT).show();
        }
    }

    ///////////////////////////////
    // dashboard panel tunjangan //
    ///////////////////////////////

    private void initiateViewPanelTunjangan() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_tunjangan, null);
        panelLinearLayout.addView(v);

        // mac address
        panelTunjanganBackground = dashboardPanelList.get(konfigurasi.DASHBOARD_PANEL_TUNJANGAN).getPanelBackground(getActivity(), rootView);
        panelTunjanganCardView = rootView.findViewById(R.id.dashboard_panel_tunjangan);
        panelTunjanganCardView.setVisibility(View.GONE);

        tukinGradeTextView = rootView.findViewById(R.id.dashboard_tunjangan_grade);
        tukinDasarTextView = rootView.findViewById(R.id.dashboard_tunjangan_dasar);
        tukinPotonganTextView = rootView.findViewById(R.id.dashboard_tunjangan_potongan);
        tukinBersihTextView = rootView.findViewById(R.id.dashboard_tunjangan_bersih);
        tukinPeriodeTextView = rootView.findViewById(R.id.dashboard_tunjangan_periode);

        ImageView tukinInfo = rootView.findViewById(R.id.dashboard_tunjangan_info);
        tukinInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Data Tunjangan Kinerja belum divalidasi", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populatePanelTunjangan() {
        tukinDasarTextView.setText(tukinDasar);
        String infoPotongan = "Pot. " + tukinPersenPotongan + " " + tukinPotongan;
        tukinPotonganTextView.setText(infoPotongan);
        tukinBersihTextView.setText(tukinBersih);
        tukinGradeTextView.setText(tukinGrade);
        String periode = "Tukin bulan " + tukinBulan + " " + tukinTahun;
        tukinPeriodeTextView.setText(periode);

        panelTunjanganCardView.setVisibility(View.VISIBLE);
        ropeAnimation = YoYo.with(Techniques.SlideInRight)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelTunjanganCardView);

        panelTunjanganBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_TUNJANGAN);
            }
        });

        //panel
        dashboardPanelList
                .get(konfigurasi.DASHBOARD_PANEL_TUNJANGAN)
                .getPanelOption(getActivity(), rootView)
                .setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void getJSONTunjangan() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONTunjangan();
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.GONE);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_TUKIN + mNipLama + time + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONTunjangan() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {
                jsonObject = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
                tukinBulan = checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_BULAN), "-");
                tukinTahun = checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_TAHUN), "-");
                tukinGrade = checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_GRADE), "-");
                tukinPersenPotongan = checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_PERSENPOTONGAN), "-");
                tukinDasar = "Rp" + checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_TUKINDASAR), "-");
                tukinPotongan = "(Rp" + checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_POTONGAN), "-") + ")";
                tukinBersih = "Rp" + checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_TUKINBERSIH), "-");
                populatePanelTunjangan();
            } else {
                Toast.makeText(getActivity(), "Gagal menarik data tunjangan kinerja", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Gagal menarik data tunjangan kinerja", Toast.LENGTH_SHORT).show();
        }
    }

    //////////////////////////
    // dashboard panel cuti //
    //////////////////////////

    private void initiateViewPanelCuti() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_cuti, null);
        panelLinearLayout.addView(v);

        // cuti
        panelCutiBackground = dashboardPanelList.get(konfigurasi.DASHBOARD_PANEL_CUTI).getPanelBackground(getActivity(), rootView);
        panelCutiCardView = rootView.findViewById(R.id.dashboard_panel_cuti);
        panelCutiCardView.setVisibility(View.GONE);
        panelCutiSaldo = rootView.findViewById(R.id.dashboard_cuti_saldo);
        panelCutiHak = rootView.findViewById(R.id.dashboard_cuti_hak);
        panelCutiTerpakai = rootView.findViewById(R.id.dashboard_cuti_terpakai);
    }

    private void populatePanelCuti() {
        panelCutiSaldo.setText(saldoCuti);
        if (!saldoCuti.equals("0")) {
            panelCutiSaldo.setTextColor(getResources().getColor(R.color.green));
        } else {
            panelCutiSaldo.setTextColor(getResources().getColor(R.color.red));
        }
        panelCutiHak.setText(jumlahHakCuti);
        panelCutiTerpakai.setText(cutiTerpakai);

        panelCutiCardView.setVisibility(View.VISIBLE);
        ropeAnimation = YoYo.with(Techniques.SlideInRight)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelCutiCardView);

        panelCutiBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_CUTI);
            }
        });

        //panel
        dashboardPanelList
                .get(konfigurasi.DASHBOARD_PANEL_CUTI)
                .getPanelOption(getActivity(), rootView)
                .setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void getJSONCuti() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONCuti();
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.GONE);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP_REKAPCUTI + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONCuti() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {
                jsonObject = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
                // hak cuti tahun ini
                hakCutiT = checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_SALDO0), "0");
                // hak cuti tahun -1
                hakCutiTMin1 = checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_SALDO1), "0");
                // hak cuti tahun -2
                hakCutiTMin2 = checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_SALDO2), "0");
                jumlahHakCuti = "" + (Integer.parseInt(hakCutiT) + Integer.parseInt(hakCutiTMin1) + Integer.parseInt(hakCutiTMin2));
                saldoCuti = (Integer.parseInt(checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_CUTI0), "0")) +
                        Integer.parseInt(checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_CUTI1), "0")) +
                        Integer.parseInt(checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_CUTI2), "0"))) + "";
                cutiTerpakai = (Integer.parseInt(jumlahHakCuti) - Integer.parseInt(saldoCuti)) + "";
                populatePanelCuti();
            } else {
                Toast.makeText(getActivity(), "Gagal menarik data cuti", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Gagal menarik data cuti", Toast.LENGTH_SHORT).show();
        }
    }

    ///////////////////////////
    // dashboard panel ultah //
    ///////////////////////////

    private void initiateViewPanelUltah() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_ultah, null);
        panelLinearLayout.addView(v);

        // panel ultah
        panelUltahCardView = rootView.findViewById(R.id.dashboard_panel_ultah);
        panelUltahCardView.setVisibility(View.GONE);
        fotoUltah = rootView.findViewById(R.id.dashboard_ultah_foto);
    }

    private void populatePanelUltah() {
        Picasso.with(getActivity()).load(mFoto).into(fotoUltah);
        panelUltahCardView.setVisibility(View.VISIBLE);
        ropeAnimation = YoYo.with(Techniques.SlideInRight)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelUltahCardView);
    }

    ///////////////////////////////////////
    // dashboard panel notifikasi atasan //
    ///////////////////////////////////////

    private void initiateViewPanelNotifikasiAtasan() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_notifikasi_atasan, null);
        panelLinearLayout.addView(v);

        // panel notif atasan
        panelNotifikasiAtasanBackground = dashboardPanelList.get(konfigurasi.DASHBOARD_PANEL_NOTIFIKASIATASAN).getPanelBackground(getActivity(), rootView);
        panelNotifikasiAtasanCardView = rootView.findViewById(R.id.dashboard_panel_notifikasi_atasan);
        panelNotifikasiAtasanCardView.setVisibility(View.GONE);

        // notifikasi
        prosesPersetujuanCutiTextView = rootView.findViewById(R.id.dashboard_notifikasi_cuti);
        prosesPersetujuanIzinKantorTextView = rootView.findViewById(R.id.dashboard_notifikasi_izin_kantor);
        presensiTextView = rootView.findViewById(R.id.dashboard_notifikasi_presensi);
        ImageView info = rootView.findViewById(R.id.dashboard_notifikasi_presensi_info);

        prosesPersetujuanCutiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
//                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);

                DashboardActivity.openedDrawer = "panel_dashboard_notif_cuti";

                CutiDaftarPersetujuanFragment cutiDaftarPersetujuanFragment = new CutiDaftarPersetujuanFragment();
//                cutiDaftarPersetujuanFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, cutiDaftarPersetujuanFragment);
                fragmentTransaction.commit();
            }
        });
        prosesPersetujuanIzinKantorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
//                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);

                DashboardActivity.openedDrawer = "panel_dashboard_notif_izin";

                IzinKantorDaftarPersetujuanFragment izinKantorDaftarPersetujuanFragment = new IzinKantorDaftarPersetujuanFragment();
//                izinKantorDaftarPersetujuanFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, izinKantorDaftarPersetujuanFragment);
                fragmentTransaction.commit();
            }
        });
        presensiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString(INTENT_USERTOKEN, mUserToken);
//                bundle.putString(INTENT_NAMA, mNama);
//                bundle.putString(INTENT_NIPLAMA, mNipLama);
//                bundle.putString(INTENT_NIPBARU, mNipBaru);
//                bundle.putString(INTENT_FOTO, mFoto);

                DashboardActivity.openedDrawer = "panel_dashboard_notif_presensi";

                AbsenBawahanFragment absenBawahanFragment = new AbsenBawahanFragment();
//                absenBawahanFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, absenBawahanFragment);
                fragmentTransaction.commit();
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "khusus untuk bawahan yang telah melakukan setting atasan langsung", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populatePanelNotifikasiAtasan() {
        prosesPersetujuanCutiTextView.setText(jumlahProsesCuti);

        panelNotifikasiAtasanCardView.setVisibility(View.VISIBLE);
        ropeAnimation = YoYo.with(Techniques.SlideInRight)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelNotifikasiAtasanCardView);

        panelNotifikasiAtasanBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_NOTIFIKASIATASAN);
            }
        });

        //panel
        dashboardPanelList
                .get(konfigurasi.DASHBOARD_PANEL_NOTIFIKASIATASAN)
                .getPanelOption(getActivity(), rootView)
                .setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void getJSONNotifikasiAtasanCuti() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONNotifikasiAtasanCuti();
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.GONE);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP_CUTIBAWAHANLANGSUNGCOUNT + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONNotifikasiAtasanCuti() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {
                String result = jsonObject.getString(konfigurasi.TAG_JSON_ARRAY);
                jumlahProsesCuti = result;
                if (!jsonObject.getString("success").equals("true")) {
                    jumlahProsesCuti = "" + 0;
                }
                populatePanelNotifikasiAtasan();
            } else {
                Toast.makeText(getActivity(), "Kesalahan mengambil data proses pengajuan cuti", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Kesalahan mengambil data proses pengajuan cuti", Toast.LENGTH_SHORT).show();
        }
    }

    private void getJSONNotifikasiAtasanPresensi() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONNotifikasiAtasanPresensi();
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.GONE);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ABSENBAWAHAN + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONNotifikasiAtasanPresensi() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {
                JSONArray result = jsonObject.getJSONArray("result");
                jumlahBawahan = jumlahDatang = 0;
                jumlahBawahan = result.length();
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    if (!jo.getString(konfigurasi.TAG_ABSEN_DATANG).equals("null")) {
                        jumlahDatang++;
                    }
                }
                populatePanelNotifikasiAtasanPresensi();
            } else {
                Toast.makeText(getActivity(), "Kesalahan mengambil data proses pengajuan cuti", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Kesalahan mengambil data proses pengajuan cuti", Toast.LENGTH_SHORT).show();
        }
    }

    private void populatePanelNotifikasiAtasanPresensi() {
        String notifikasiPresensi;
        if (jumlahDatang == 0) {
            notifikasiPresensi = "0%";
        } else {
            float persentasePresensi = ((1f * jumlahDatang) / (1f * jumlahBawahan)) * 100f;
            notifikasiPresensi = Float.toString(persentasePresensi);
            notifikasiPresensi = notifikasiPresensi.substring(0, 4) + "%";
        }
        presensiTextView.setText(notifikasiPresensi);
    }

    private void getJSONNotifikasiAtasanIzinKantor() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONNotifikasiAtasanIzinKantor();
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.GONE);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP_IZINKANTORBAWAHANLANGSUNGCOUNT + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONNotifikasiAtasanIzinKantor() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {
                String result = jsonObject.getString(konfigurasi.TAG_JSON_ARRAY);
                jumlahProsesIzinKantor = result;
                if (!jsonObject.getString("success").equals("true")) {
                    jumlahProsesIzinKantor = "" + 0;
                }
                populatePanelNotifikasiAtasanIzinKantor();
            } else {
                Toast.makeText(getActivity(), "Kesalahan mengambil data proses pengajuan izin kantor", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Kesalahan mengambil data proses pengajuan izin kantor", Toast.LENGTH_SHORT).show();
        }
    }

    private void populatePanelNotifikasiAtasanIzinKantor() {
        prosesPersetujuanIzinKantorTextView.setText(jumlahProsesIzinKantor);
    }

    //////////////////////////////////////////
    // dashboard panel presensi via hotspot //
    //////////////////////////////////////////

    private void initiateViewPanelHotspot() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_hotspot, null);
        panelLinearLayout.addView(v);

        // alt presensi
        panelHotspotBackground = dashboardPanelList.get(konfigurasi.DASHBOARD_PANEL_HOTSPOT).getPanelBackground(getActivity(), rootView);
        panelHotspotCardView = rootView.findViewById(R.id.dashboard_panel_hotspot);
        panelHotspotCardView.setVisibility(View.GONE);
        jamDatangHotspotAltView = rootView.findViewById(R.id.dashboard_hotspot_datang_val_alt);
        jamPulangHotspotAltView = rootView.findViewById(R.id.dashboard_hotspot_pulang_val_alt);
    }

    private void populatePanelPresensiHotspot() {
        jamDatangHotspotAltView.setText(jamDatangHotspot);
        jamPulangHotspotAltView.setText(jamPulangHotspot);

        panelHotspotBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_HOTSPOT);
            }
        });

        switch (statusDatangHotspot) {
            case "BELUM DATANG":
                jamDatangHotspotAltView.setTextColor(getResources().getColor(R.color.red));
                break;
            case "TEPAT WAKTU":
                jamDatangHotspotAltView.setTextColor(getResources().getColor(R.color.green));
                break;
            case "TERLAMBAT":
                jamDatangHotspotAltView.setTextColor(getResources().getColor(R.color.orange));
                break;
            default:
                jamDatangHotspotAltView.setTextColor(getResources().getColor(R.color.blueBasicDark));
                break;
        }
        switch (statusPulangHotspot) {
            case "BELUM PULANG":
                jamPulangHotspotAltView.setTextColor(getResources().getColor(R.color.red));
                break;
            case "PULANG CEPAT":
                jamPulangHotspotAltView.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "TEPAT WAKTU":
                jamPulangHotspotAltView.setTextColor(getResources().getColor(R.color.green));
                break;
            default:
                jamPulangHotspotAltView.setTextColor(getResources().getColor(R.color.blueBasicDark));
                break;
        }

        panelHotspotCardView.setVisibility(View.VISIBLE);
        ropeAnimation = YoYo.with(Techniques.SlideInRight)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelHotspotCardView);

        //panel
        dashboardPanelList
                .get(konfigurasi.DASHBOARD_PANEL_HOTSPOT)
                .getPanelOption(getActivity(), rootView)
                .setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void getJSONPresensiHotspot() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONPresensiHotspot();
                dashboardPanelList.get(currentPanelShowing).getPanelOptionProgressbar(getActivity(), rootView).setVisibility(View.GONE);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_HOTSPOT + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONPresensiHotspot() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
//            if (jsonObject.getString("success").equals("true")) {
            jsonObject = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
            jamDatangHotspot = checkNull(jsonObject.getString(konfigurasi.TAG_DATANG), "-");
            jamPulangHotspot = checkNull(jsonObject.getString(konfigurasi.TAG_PULANG), "-");
            statusDatangHotspot = jsonObject.getString(konfigurasi.TAG_HOTSPOT_STATUSDATANG);
            statusPulangHotspot = jsonObject.getString(konfigurasi.TAG_HOTSPOT_STATUSPULANG);

            if (jamDatangHotspot.equals(jamPulangHotspot)) {
                jamPulangHotspot = "-";
                isSudahAbsenPagi = true;
            }
//            if (jamPulangHotspot.equals("-")) {
//                isSudahAbsenSore = false;
//            } else {
//                isSudahAbsenSore = true;
//            }
            populatePanelPresensiHotspot();
//            } else {
//                Toast.makeText(getActivity(), "Gagal menarik data presensi", Toast.LENGTH_SHORT).show();
//            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Gagal menarik data presensi via hotspot", Toast.LENGTH_SHORT).show();
        }
    }
}