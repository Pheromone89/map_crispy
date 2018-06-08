package id.go.bpkp.mobilemapbpkp.absen;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDaftarCutiFragment;
import id.go.bpkp.mobilemapbpkp.cuti.CutiPengajuanPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 11/04/2018.
 */

public class AbsenFragment extends Fragment {

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

    public AbsenFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_presensi_dashboard, null);
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

        initiateView();
        getJSON();
        setOnClickMethod();
        revealLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLocation();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_absen);
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void initiateView() {
        rootLayout = rootView.findViewById(R.id.dashboard_presensi_layout);
        rootProgressBar = rootView.findViewById(R.id.dashboard_presensi_progress_bar);
        rootProgressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);

        panelHeader = rootView.findViewById(R.id.absen_panel_header);
        jamDatangView = rootView.findViewById(R.id.dashboard_pegawai_jam_datang);
        jamPulangView = rootView.findViewById(R.id.dashboard_pegawai_jam_pulang);
        statusDatangView = rootView.findViewById(R.id.dashboard_pegawai_status_kedatangan);
        statusPulangView = rootView.findViewById(R.id.dashboard_pegawai_status_kepulangan);
        hariTanggalView = rootView.findViewById(R.id.dashboard_pegawai_hari_tanggal);
        statusMessageView = rootView.findViewById(R.id.dashboard_pegawai_status_message);
        infoButton = rootView.findViewById(R.id.absen_info_button);

        absenCardView = rootView.findViewById(R.id.dashboard_pegawai_absen);
        historyButton = rootView.findViewById(R.id.absen_history_button);
        absenWarningCardView = rootView.findViewById(R.id.dashboard_pegawai_absen_warning);
        refreshLocationButton = rootView.findViewById(R.id.absen_refresh_location);
        absenLoadingCardView = rootView.findViewById(R.id.dashboard_pegawai_absen_loading);
        absenInactiveCardView = rootView.findViewById(R.id.dashboard_pegawai_absen_inactive);
        fingerIcon = rootView.findViewById(R.id.dashboard_pegawai_finger_icon);

        datangCardView = rootView.findViewById(R.id.dashboard_pegawai_datang_card);
        pulangCardView = rootView.findViewById(R.id.dashboard_pegawai_pulang_card);
        statusCardView = rootView.findViewById(R.id.dashboard_pegawai_status_card);
    }

    private void populateView() {
        String header = "Presensi - " + mNama;
        panelHeader.setText(header);

        jamDatangView.setText(jamDatang);
        jamPulangView.setText(jamPulang);

        switch (statusDatang) {
            case "Anda Tidak Absen Datang":
                datangCardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                statusDatang = "Anda belum merekam kehadiran";
                break;
            case "Anda Hadir Tepat Waktu":
                datangCardView.setCardBackgroundColor(getResources().getColor(R.color.green));
                statusDatang = "Anda hadir tepat waktu";
                break;
            case "Anda Datang Terlambat (DT)":
                datangCardView.setCardBackgroundColor(getResources().getColor(R.color.orange));
                statusDatang = "Anda datang terlambat (DT)";
                break;
        }
        switch (statusPulang) {
            case "Anda Belum Absen Pulang":
                pulangCardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                statusPulang = "Anda belum merekam kepulangan";
                break;
            case "Anda Tidak Absen Pulang":
                pulangCardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                statusPulang = "Anda tidak merekam kepulangan";
                break;
            case "Anda Pulang Cepat (PC)":
                pulangCardView.setCardBackgroundColor(getResources().getColor(R.color.orange));
                statusPulang = "Anda pulang lebih cepat (PC)";
                break;
            case "Anda Pulang Tepat Waktu":
                pulangCardView.setCardBackgroundColor(getResources().getColor(R.color.green));
                statusPulang = "Anda pulang tepat waktu";
                break;
        }

        statusDatangView.setText(statusDatang);
        statusPulangView.setText(statusPulang);

        statusMessageView.setText(statusAbsen);
        hariTanggalView.setText(hariTanggal);

        if (currentHour < 12) {
            if (isSudahAbsenPagi) {
                absenInactiveCardView.setVisibility(View.VISIBLE);
                absenLoadingCardView.setVisibility(View.GONE);
                absenCardView.setVisibility(View.GONE);
                absenWarningCardView.setVisibility(View.GONE);
            }
        } else {
            if (isSudahAbsenSore) {
                absenInactiveCardView.setVisibility(View.VISIBLE);
                absenLoadingCardView.setVisibility(View.GONE);
                absenCardView.setVisibility(View.GONE);
                absenWarningCardView.setVisibility(View.GONE);
            }
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(getActivity(),null,"Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                JSON_STRING = s;
                parseJSON();
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

    private String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void parseJSON() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            jsonObject = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
            hariTanggal = hari + ", " + jsonObject.getString(konfigurasi.TAG_ABSEN_TANGGAL);
            jamDatang = checkNull(jsonObject.getString(konfigurasi.TAG_ABSEN_DATANG));
            jamPulang = checkNull(jsonObject.getString(konfigurasi.TAG_ABSEN_PULANG));
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
            Toast.makeText(getActivity(), "JSONException", Toast.LENGTH_SHORT).show();
        }
        populateView();
    }

    private String checkNull(String string) {
        if (string.equals("null")) {
            return "-";
        } else {
            return string;
        }
    }

    private void setOnClickMethod() {
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);

                AbsenDataFragment absenDataFragment = new AbsenDataFragment();
                absenDataFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, absenDataFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Data akan ter-update kurang lebih 15 menit setelah pegawai merekam kehadiran", Toast.LENGTH_SHORT).show();
            }
        });
        absenCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Fitur masih dalam pengembangan", Toast.LENGTH_SHORT).show();
                getLatitudeLongitude();
                setFingerCardView(true, userLat, userLong);
                if (isValidForAbsen(userLat, userLong)) {
                    setFingerCardView(true, userLat, userLong);
                    postDataAbsensi();
                } else {
                    Toast.makeText(getActivity(), "Anda telah keluar dari area absensi", Toast.LENGTH_SHORT).show();
                    setFingerCardView(true, 0f, 0f);
                    getLatitudeLongitude();
                }
            }
        });
        absenCardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    absenCardView.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    absenCardView.setCardBackgroundColor(getResources().getColor(R.color.blueBasicDark));
                }
                return false;
            }
        });
        absenWarningCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLocation();
                Toast.makeText(getActivity(), "Anda tidak dapat merekam kehadiran di luar area yang ditetapkan", Toast.LENGTH_SHORT).show();
            }
        });
        absenInactiveCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentHour < 12) {
                    Toast.makeText(getActivity(), "Kehadiran Pagi sudah direkam", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Kepulangan sudah direkam", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLocation();
            }
        });
    }

    private void postDataAbsensi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_POST_ABSEN + mUserToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                Toast.makeText(getActivity(), "Simulasi rekam kehadiran berhasil", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Kehadiran gagal terekam", Toast.LENGTH_SHORT).show();
                            }
                            setFingerCardView(false, userLat, userLong);
                            getJSON();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            Snackbar.make(rootView, "Gagal mengotentifikasi", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof ServerError) {
                            Snackbar.make(rootView, "Masalah pada server", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof TimeoutError) {
                            Snackbar.make(rootView, "Waktu koneksi habis", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof NetworkError) {
                            Snackbar.make(rootView, "Gagal menghubungkan dengan jaringan", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof ParseError) {
                            Snackbar.make(rootView, "Gagal parsing data", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("niplama", mNipLama);
                params.put("lat", Float.toString(userLat));
                params.put("long", Float.toString(userLong));
                params.put("imei", mImei);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    private void hitungDeviasi(float latitude, float longitude) {
        devlatfloat = (latitude - ((LOCNORTH + LOCSOUTH) / 2)) * KONSTAN;
        devlongfloat = (longitude - ((LOCWEST + LOCEAST) / 2)) * KONSTAN;
    }

    private void getLatitudeLongitude() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            coarseLoc = true;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
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
                            setFingerCardView(false, userLat, userLong);
                        }
                    });
        } else {
            userLat = 0f;
            userLong = 0f;
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
            setFingerCardView(false, userLat, userLong);
            Toast.makeText(getActivity(), "tolong izinkan MAP BPKP untuk mengakses GPS anda", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidForAbsen(float latitude, float longitude) {
        if (latitude <= LOCNORTH || latitude >= LOCSOUTH) {
            isLatitudeOnPosition = false;
        } else {
            isLatitudeOnPosition = true;
        }
        if (longitude >= LOCEAST || longitude <= LOCWEST) {
            isLongitudeOnPosition = false;
        } else {
            isLongitudeOnPosition = true;
        }
        if (isLatitudeOnPosition && isLongitudeOnPosition) {
            return true;
        } else {
            return false;
        }
    }

    private void setFingerCardView(boolean status, float userLat, float userLong) {
        if (status) {
            absenCardView.setVisibility(View.GONE);
            absenWarningCardView.setVisibility(View.GONE);
            absenLoadingCardView.setVisibility(View.VISIBLE);
        } else {
            if (isValidForAbsen(userLat, userLong)) {
                absenLoadingCardView.setVisibility(View.GONE);
                absenCardView.setVisibility(View.VISIBLE);
                absenWarningCardView.setVisibility(View.GONE);
            } else {
                absenLoadingCardView.setVisibility(View.GONE);
                absenCardView.setVisibility(View.GONE);
                absenWarningCardView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void refreshLocation() {
        setFingerCardView(true, 0f, 0f);
        getLatitudeLongitude();
        if (currentHour < 12) {
            if (isSudahAbsenPagi) {
                absenInactiveCardView.setVisibility(View.VISIBLE);
                absenLoadingCardView.setVisibility(View.GONE);
                absenCardView.setVisibility(View.GONE);
                absenWarningCardView.setVisibility(View.GONE);
            }
        } else {
            if (isSudahAbsenSore) {
                absenInactiveCardView.setVisibility(View.VISIBLE);
                absenLoadingCardView.setVisibility(View.GONE);
                absenCardView.setVisibility(View.GONE);
                absenWarningCardView.setVisibility(View.GONE);
            }
        }
    }

    private void revealLayout() {
        rootLayout.setVisibility(View.VISIBLE);
        rootProgressBar.setVisibility(View.GONE);
        ropeDashboardPegawai = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(rootLayout);
    }
}
