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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.SettingPrefs;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

import static id.go.bpkp.mobilemapbpkp.dashboard.DashboardPanel.dashboardPanelList;

/**
 * Created by ASUS on 26/01/2018.
 */

public class DashboardPegawaiFragment extends Fragment {

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
            profilNipLamaView,
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
    private boolean isHut;
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
    private FloatingActionButton fab;
    private ScrollView panelOption;
    // panel cardview
    private int currentSelectedPanel = 999;
    private boolean profilIsShown, presensiIsShown, jaringanIsShown, cutiIsShown, tunjanganIsShown, ultahIsShown = false;
    private boolean profileIsClicked, presensiIsClicked, jaringanIsClicked, cutiIsClicked, tunjanganIsClicked, ultahIsClicked = false;
    private LinearLayout panelProfilCardView, panelJaringanCardView, panelPresensiCardView, panelTunjanganCardView, panelCutiCardView;
    private LinearLayout panelProfilBackground, panelJaringanBackground, panelPresensiBackground, panelTunjanganBackground, panelCutiBackground;
    private ImageView panelProfilCloseButton, panelJaringanCloseButton, panelPresensiCloseButton, panelTunjanganCloseButton, panelCutiCloseButton;
    private CardView profilOptionCardView, jaringanOptionCardView, presensiOptionCardView, tunjanganOptionCardView, cutiOptionCardView;
    // khusus ultah
    private LinearLayout panelUltahCardView;
    private ImageView fotoUltah;
    // end ultah
    private YoYo.YoYoString ropePanelEnable;
    private YoYo.YoYoString ropePanelDisable;
    private YoYo.YoYoString ropeCloseButton;
    private LayoutInflater panelLayouInflater;
    private LinearLayout panelLinearLayout;
    private String hakCutiT, hakCutiTMin1, hakCutiTMin2, jumlahHakCuti, saldoCuti, cutiTerpakai;
    private TextView panelCutiSaldo, panelCutiHak, panelCutiTerpakai;
    private long animationDurationLong = 1500;
    private long animationDurationShort = 500;
    // panel tukin
    private String tukinBulan, tukinTahun, tukinGrade, tukinPersenPotongan, tukinDasar, tukinPotongan, tukinBersih;
    private TextView tukinGradeTextView, tukinDasarTextView, tukinPotonganTextView, tukinBersihTextView;

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
        // HUT?
        isHut = this.getArguments().getBoolean(PassedIntent.INTENT_ISHUT, false);

        // panel setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        initiateView();
        populateView();

        for (int i = 0; i < dashboardPanelList.size(); i++) {
            Boolean status = sharedPreferences.getBoolean("dashboard_panel_" + i, true);
            if (status == true) {
                showPanel(
                        dashboardPanelList.get(i).getPanelId()
                );
            }
            setPanelOption(
                    dashboardPanelList.get(i).getPanelId(),
                    dashboardPanelList.get(i).getPanelOption(),
                    dashboardPanelList.get(i).getPanelCardView()
            );
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
        profilOptionCardView = rootView.findViewById(R.id.dashboard_pegawai_profil_option);
        presensiOptionCardView = rootView.findViewById(R.id.dashboard_pegawai_presensi_option);
        jaringanOptionCardView = rootView.findViewById(R.id.dashboard_pegawai_jaringan_option);
        cutiOptionCardView = rootView.findViewById(R.id.dashboard_pegawai_cuti_option);
        tunjanganOptionCardView = rootView.findViewById(R.id.dashboard_pegawai_tunjangan_option);

        rootProgressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);
    }

    private void populateView() {
        rootLayout.setVisibility(View.VISIBLE);
        rootProgressBar.setVisibility(View.GONE);
        ropeDashboardPegawai = YoYo.with(Techniques.FadeIn)
                .duration(animationDurationLong)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(rootLayout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShown = panelOption.isShown();
                if (isShown) {
                    ropePanelDisable = YoYo.with(Techniques.SlideOutDown)
                            .duration(animationDurationShort)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(panelOption);
                    panelOption.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            panelOption.setVisibility(View.GONE);
                        }

                    }, animationDurationShort);
                } else {
                    panelOption.setVisibility(View.VISIBLE);
                    ropePanelEnable = YoYo.with(Techniques.SlideInUp)
                            .duration(animationDurationShort)
                            .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .playOn(panelOption);
                }
            }
        });
    }

    private String checkNull(String string) {
        if (string.equals("null")) {
            return "-";
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
        }, animationDurationShort);
    }

    private void setPanelOption(final int panelId, final String option, final String panelCardView) {
        if (option != null) {
            int panelOptionInt = this.getResources().getIdentifier(option, "id", getActivity().getPackageName());
            int panelCardViewInt = this.getResources().getIdentifier(panelCardView, "id", getActivity().getPackageName());
            final CardView cardViewOption = rootView.findViewById(panelOptionInt);
            final LinearLayout cardViewPanel = rootView.findViewById(panelCardViewInt);

            cardViewOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cardViewPanel == null) {
                        showPanel(panelId);
                    } else if (cardViewPanel.isShown()) {
                        deletePanel(panelId, cardViewPanel, ropePanelDisable, cardViewOption);
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
            String panelBackgroundString = dashboardPanelList.get(currentSelectedPanel).getPanelBackground();
            if (panelBackgroundString != null) {
                int panelBackgroundInt = this.getResources().getIdentifier(panelBackgroundString, "id", getActivity().getPackageName());
                LinearLayout background = rootView.findViewById(panelBackgroundInt);
                background.setBackgroundResource(R.color.whiteAlternate);
            }
        }

        if (panelId != currentSelectedPanel) {
            switch (panelId) {
                case konfigurasi.DASHBOARD_PANEL_PROFIL:
                    panelProfilBackground.setBackgroundResource(R.color.whiteAlternateDark);
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_PROFIL;
                    break;
                case konfigurasi.DASHBOARD_PANEL_JARINGAN:
                    panelJaringanBackground.setBackgroundResource(R.color.whiteAlternateDark);
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_JARINGAN;
                    break;
                case konfigurasi.DASHBOARD_PANEL_PRESENSI:
                    panelPresensiBackground.setBackgroundResource(R.color.whiteAlternateDark);
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_PRESENSI;
                    break;
                case konfigurasi.DASHBOARD_PANEL_TUNJANGAN:
                    panelTunjanganBackground.setBackgroundResource(R.color.whiteAlternateDark);
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_TUNJANGAN;
                    break;
                case konfigurasi.DASHBOARD_PANEL_CUTI:
                    panelCutiBackground.setBackgroundResource(R.color.whiteAlternateDark);
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_CUTI;
                    break;
                case konfigurasi.DASHBOARD_PANEL_ULTAH:
                    currentSelectedPanel = konfigurasi.DASHBOARD_PANEL_ULTAH;
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

    private void showPanel(int panelId) {
        panelLayouInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        panelLayouInflater = LayoutInflater.from(getActivity());

        switch (panelId) {
            case konfigurasi.DASHBOARD_PANEL_PROFIL:
                initiateViewPanelProfil();
                populatePanelProfil();
                profilIsShown = true;
                profileIsClicked = false;
                break;
            case konfigurasi.DASHBOARD_PANEL_JARINGAN:
//                initiateViewPanelJaringan();
//                populatePanelJaringan();
//                jaringanIsShown = true;
//                jaringanIsClicked = false;
                break;
            case konfigurasi.DASHBOARD_PANEL_PRESENSI:
                initiateViewPanelPresensi();
                getJSONPresensi();
                presensiIsShown = true;
                presensiIsClicked = false;
                break;
            case konfigurasi.DASHBOARD_PANEL_TUNJANGAN:
                initiateViewPanelTunjangan();
                getJSONTunjangan();
                tunjanganIsShown = true;
                tunjanganIsClicked = false;
                break;
            case konfigurasi.DASHBOARD_PANEL_CUTI:
                initiateViewPanelCuti();
                getJSONCuti();
                cutiIsShown = true;
                cutiIsClicked = false;
                break;
            case konfigurasi.DASHBOARD_PANEL_ULTAH:
                if (isHut) {
                    initiateViewPanelUltah();
                    populatePanelUltah();
                    cutiIsShown = true;
                    cutiIsClicked = false;
                }
                break;
        }

        setPanelOption(
                dashboardPanelList.get(panelId).getPanelId(),
                dashboardPanelList.get(panelId).getPanelOption(),
                dashboardPanelList.get(panelId).getPanelCardView()
        );

        editor.putBoolean("dashboard_panel_" + panelId, true);
        editor.commit();
    }

    private void animateCloseButton(final ImageView closeButton, YoYo.YoYoString closeRope) {
        boolean isShown = closeButton.isShown();
        if (isShown) {
            closeRope = YoYo.with(Techniques.SlideOutRight)
                    .duration(animationDurationShort)
                    .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                    .interpolate(new AccelerateDecelerateInterpolator())
                    .playOn(closeButton);
            closeButton.postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeButton.setVisibility(View.GONE);
                }

            }, animationDurationShort);
        } else {
            closeButton.setVisibility(View.VISIBLE);
            closeRope = YoYo.with(Techniques.SlideInRight)
                    .duration(animationDurationShort)
                    .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                    .interpolate(new AccelerateDecelerateInterpolator())
                    .playOn(closeButton);
        }
    }

    private void deletePanel(int panelId, final LinearLayout panelCardView, YoYo.YoYoString ropeClose, final CardView optionPanel) {
        ropeClose = YoYo.with(Techniques.SlideOutRight)
                .duration(animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelCardView);
        panelCardView.postDelayed(new Runnable() {
            @Override
            public void run() {
                panelLinearLayout.removeView(panelCardView);
                optionPanel.setCardBackgroundColor(getResources().getColor(R.color.blueBasic));
            }

        }, animationDurationShort);
        setPanelOption(
                dashboardPanelList.get(panelId).getPanelId(),
                dashboardPanelList.get(panelId).getPanelOption(),
                dashboardPanelList.get(panelId).getPanelCardView()
        );
        // setting
        editor.putBoolean("dashboard_panel_" + panelId, false);
        editor.commit();
    }

    private void initiateViewPanelProfil() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_profil, null);
        panelLinearLayout.addView(v);

        // profil
        panelProfilBackground = rootView.findViewById(R.id.dashboard_profil_layout);
        panelProfilCardView = rootView.findViewById(R.id.dashboard_panel_profil);
        panelProfilCardView.setVisibility(View.GONE);
        profilFotoView = rootView.findViewById(R.id.dashboard_profil_foto);
        profilNamaView = rootView.findViewById(R.id.dashboard_profil_nama_val);
        profilNipView = rootView.findViewById(R.id.dashboard_profil_nip_val);
        panelProfilCloseButton = rootView.findViewById(R.id.dashboard_panel_profil_close);
    }

    private void populatePanelProfil() {
        Picasso.with(getActivity()).load(mFoto).into(profilFotoView);
        profilNamaView.setText(mNama);
        String nipInfo = mNipBaru + " / " + mNipLama;
        profilNipView.setText(nipInfo);
        panelProfilCardView.setVisibility(View.VISIBLE);
        ropePanelEnable = YoYo.with(Techniques.SlideInRight)
                .duration(animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelProfilCardView);

        panelProfilCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deletePanel(konfigurasi.DASHBOARD_PANEL_PROFIL, panelProfilCardView, ropePanelDisable, profilOptionCardView);
            }
        });

        panelProfilBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_PROFIL);
            }
        });

        //panel
        profilOptionCardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void initiateViewPanelJaringan() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_jaringan, null);
        panelLinearLayout.addView(v);

        // mac address
        panelJaringanBackground = rootView.findViewById(R.id.dashboard_jaringan_layout);
        panelJaringanCardView = rootView.findViewById(R.id.dashboard_panel_jaringan);
        panelJaringanCardView.setVisibility(View.GONE);
        macAdressView = rootView.findViewById(R.id.dashboard_presensi_mac_address_val);
        panelJaringanCloseButton = rootView.findViewById(R.id.dashboard_panel_jaringan_close);
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
        ropePanelEnable = YoYo.with(Techniques.SlideInRight)
                .duration(animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelJaringanCardView);

        panelJaringanCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deletePanel(konfigurasi.DASHBOARD_PANEL_JARINGAN, panelJaringanCardView, ropePanelDisable, jaringanOptionCardView);
            }
        });

        panelJaringanBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_JARINGAN);
            }
        });

        //panel
        jaringanOptionCardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void initiateViewPanelPresensi() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_presensi, null);
        panelLinearLayout.addView(v);

        // alt presensi
        panelPresensiBackground = rootView.findViewById(R.id.dashboard_presensi_layout);
        panelPresensiCardView = rootView.findViewById(R.id.dashboard_panel_presensi);
        panelPresensiCardView.setVisibility(View.GONE);
        jamDatangAltView = rootView.findViewById(R.id.dashboard_presensi_datang_val_alt);
        jamPulangAltView = rootView.findViewById(R.id.dashboard_presensi_pulang_val_alt);
        panelPresensiCloseButton = rootView.findViewById(R.id.dashboard_panel_presensi_close);
    }

    private void populatePanelPresensi() {
        jamDatangAltView.setText(jamDatang);
        jamPulangAltView.setText(jamPulang);

        panelPresensiCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deletePanel(konfigurasi.DASHBOARD_PANEL_PRESENSI, panelPresensiCardView, ropePanelDisable, presensiOptionCardView);
            }
        });

        panelPresensiBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_PRESENSI);
            }
        });

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
        ropePanelEnable = YoYo.with(Techniques.SlideInRight)
                .duration(animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelPresensiCardView);

        //panel
        presensiOptionCardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
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
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_tunjangan, null);
        panelLinearLayout.addView(v);

        // mac address
        panelTunjanganBackground = rootView.findViewById(R.id.dashboard_tunjangan_layout);
        panelTunjanganCardView = rootView.findViewById(R.id.dashboard_panel_tunjangan);
        panelTunjanganCardView.setVisibility(View.GONE);
        panelTunjanganCloseButton = rootView.findViewById(R.id.dashboard_panel_tunjangan_close);

        tukinGradeTextView = rootView.findViewById(R.id.dashboard_tunjangan_grade);
        tukinDasarTextView = rootView.findViewById(R.id.dashboard_tunjangan_dasar);
        tukinPotonganTextView = rootView.findViewById(R.id.dashboard_tunjangan_potongan);
        tukinBersihTextView = rootView.findViewById(R.id.dashboard_tunjangan_bersih);

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

        panelTunjanganCardView.setVisibility(View.VISIBLE);
        ropePanelEnable = YoYo.with(Techniques.SlideInRight)
                .duration(animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelTunjanganCardView);

        panelTunjanganCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deletePanel(konfigurasi.DASHBOARD_PANEL_TUNJANGAN, panelTunjanganCardView, ropePanelDisable, tunjanganOptionCardView);
            }
        });

        panelTunjanganBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_TUNJANGAN);
            }
        });

        //panel
        tunjanganOptionCardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void getJSONTunjangan() {
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
                parseJSONTunjangan();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int prevMonth = c.get(Calendar.MONTH) - 1;
                String time = "/" + prevMonth + "/" + year;
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
                tukinBulan = checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_BULAN));
                tukinTahun = checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_TAHUN));
                tukinGrade = checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_GRADE));
                tukinPersenPotongan = checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_PERSENPOTONGAN));
                tukinDasar = "Rp" + checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_TUKINDASAR));
                tukinPotongan = "(Rp" + checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_POTONGAN)) + ")";
                tukinBersih = "Rp" + checkNull(jsonObject.getString(konfigurasi.TAG_TUNJANGAN_TUKINBERSIH));
            } else {
                Toast.makeText(getActivity(), "Gagal menarik data tunjangan kinerja", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Gagal menarik data tunjangan kinerja", Toast.LENGTH_SHORT).show();
        }
        populatePanelTunjangan();
    }

    private void initiateViewPanelCuti() {
        final View v = panelLayouInflater.inflate(R.layout.i_dashboard_panel_cuti, null);
        panelLinearLayout.addView(v);

        // cuti
        panelCutiBackground = rootView.findViewById(R.id.dashboard_cuti_layout);
        panelCutiCardView = rootView.findViewById(R.id.dashboard_panel_cuti);
        panelCutiCardView.setVisibility(View.GONE);
        panelCutiSaldo = rootView.findViewById(R.id.dashboard_cuti_saldo);
        panelCutiHak = rootView.findViewById(R.id.dashboard_cuti_hak);
        panelCutiTerpakai = rootView.findViewById(R.id.dashboard_cuti_terpakai);
        panelCutiCloseButton = rootView.findViewById(R.id.dashboard_panel_cuti_close);
    }

    private void populatePanelCuti() {
        panelCutiSaldo.setText(saldoCuti);
        panelCutiHak.setText(jumlahHakCuti);
        panelCutiTerpakai.setText(cutiTerpakai);

        panelCutiCardView.setVisibility(View.VISIBLE);
        ropePanelEnable = YoYo.with(Techniques.SlideInRight)
                .duration(animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelCutiCardView);

        panelCutiCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deletePanel(konfigurasi.DASHBOARD_PANEL_CUTI, panelCutiCardView, ropePanelDisable, cutiOptionCardView);
            }
        });

        panelCutiBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(konfigurasi.DASHBOARD_PANEL_CUTI);
            }
        });

        //panel
        cutiOptionCardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void getJSONCuti() {
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
                parseJSONCuti();
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
            jsonObject = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
            // hak cuti tahun ini
            hakCutiT = checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_SALDO0));
            // hak cuti tahun -1
            hakCutiTMin1 = checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_SALDO1));
            // hak cuti tahun -2
            hakCutiTMin2 = checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_SALDO2));
            jumlahHakCuti = "" + (Integer.parseInt(hakCutiT) + Integer.parseInt(hakCutiTMin1) + Integer.parseInt(hakCutiTMin2));
            saldoCuti = (Integer.parseInt(checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_CUTI0))) +
                    Integer.parseInt(checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_CUTI1))) +
                    Integer.parseInt(checkNull(jsonObject.getString(konfigurasi.TAG_CUTI_CUTI2)))) + "";
            cutiTerpakai = (Integer.parseInt(jumlahHakCuti) - Integer.parseInt(saldoCuti)) + "";
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "JSON exception", Toast.LENGTH_SHORT).show();
        }
        populatePanelCuti();
    }

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
        ropePanelEnable = YoYo.with(Techniques.SlideInRight)
                .duration(animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(panelUltahCardView);
    }
}