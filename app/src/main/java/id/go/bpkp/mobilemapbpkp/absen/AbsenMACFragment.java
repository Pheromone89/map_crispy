package id.go.bpkp.mobilemapbpkp.absen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.model.LatLng;

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
import id.go.bpkp.mobilemapbpkp.konfigurasi.SavedInstances;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import pl.droidsonroids.gif.GifImageView;

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
            jamEfektifView,
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
    private LinearLayout rootLayout, tidakTerhubungLayout, sudahAbsenLayout, absenLayout, validitasProgressBarLayout;
    private GifImageView rootProgressBar;
    private YoYo.YoYoString ropeDashboardPegawai;
    private String macAdress, macMAP;
    private TextView statusText, konfirmasiLokasiText;
    private CardView statusCard, validasiButton, absenButton;
    private LatLng position;
    private boolean isChecked;
    private MacLocation location;

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
        isChecked = false;

        //bundle dari fragment sebelumnya
        mUserToken = SavedInstances.userToken;
        // nama
        mNama = SavedInstances.name;
        //nip lama tanpa spasi
        mNipLama = SavedInstances.nipLama;
        // IMEI
        mImei = SavedInstances.imei;
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
        position = getLatitudeLongitude();

        initiateView();
        getJSONAbsen();
        setOnClickMethod();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnClickMethod() {
        validasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                populateValidationResult(
//                        getLatitudeLongitude(),
//                        getMacAdress()
//                );
                isChecked = true;

                absenLayout.setVisibility(View.GONE);
                sudahAbsenLayout.setVisibility(View.GONE);
                tidakTerhubungLayout.setVisibility(View.GONE);
                validitasProgressBarLayout.setVisibility(View.VISIBLE);

                // send to API
                validitasProgressBarLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cekValiditas(getMacAdress());
                    }
                }, 500);
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbsenDataFragment absenDataFragment = new AbsenDataFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, absenDataFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        absenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "nice", Toast.LENGTH_SHORT).show();
//                postPresensi(getMacAdress());
            }
        });
        absenButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    absenButton.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    absenButton.setCardBackgroundColor(getResources().getColor(R.color.blueBasicDark));
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    absenButton.setCardBackgroundColor(getResources().getColor(R.color.blueBasicDark));
                }
                return false;
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        if (isChecked) {
            cekValiditas(getMacAdress());
        }
    }

    private void initiateView() {
        statusText = rootView.findViewById(R.id.dashboard_presensi_status);
        statusCard = rootView.findViewById(R.id.dashboard_presensi_status_card);

        rootLayout = rootView.findViewById(R.id.dashboard_presensi_mac_layout);
        rootLayout.setVisibility(View.GONE);

        rootProgressBar = rootView.findViewById(R.id.dashboard_presensi_mac_progress_bar);
        rootProgressBar.setVisibility(View.VISIBLE);

        panelHeader = rootView.findViewById(R.id.dashboard_presensi_mac_nama);
        historyButton = rootView.findViewById(R.id.absen_history_button);

        hariTanggalView = rootView.findViewById(R.id.dashboard_presensi_mac_hari_tanggal);
        jamEfektifView = rootView.findViewById(R.id.dashboard_presensi_mac_jam_efektif);

        datangCardView = rootView.findViewById(R.id.datang_card_view);
        pulangCardView = rootView.findViewById(R.id.pulang_card_view);
        jamDatangView = rootView.findViewById(R.id.dashboard_presensi_mac_jam_datang);
        jamPulangView = rootView.findViewById(R.id.dashboard_presensi_mac_jam_pulang);
        statusDatangView = rootView.findViewById(R.id.dashboard_presensi_mac_status_datang);
        statusPulangView = rootView.findViewById(R.id.dashboard_presensi_mac_status_pulang);

        validasiButton = rootView.findViewById(R.id.absen_validasi_button);
        validitasProgressBarLayout = rootView.findViewById(R.id.absen_validitas_progressbar);
        validitasProgressBarLayout.setVisibility(View.GONE);

        konfirmasiLokasiText = rootView.findViewById(R.id.absen_konfirmasi_lokasi);
        absenButton = rootView.findViewById(R.id.absen_execute);

        tidakTerhubungLayout = rootView.findViewById(R.id.absen_tidak_terhubung);
        sudahAbsenLayout = rootView.findViewById(R.id.absen_sudah_rekam);
        absenLayout = rootView.findViewById(R.id.absen_lakukan_absen);
    }

    private void populateView() {
//        String header = "Presensi " + mNama;
        panelHeader.setText(mNama);
        hariTanggalView.setText(hariTanggal);
        jamDatangView.setText(jamDatang);
        jamPulangView.setText(jamPulang);
        jamEfektifView.setText(jamEfektif);

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

        rootProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, rootLayout, konfigurasi.animationDurationShort);
    }

    private void populateValidationResult(LatLng position, final String macAdress) {
//        if (absenLayout.isShown() || sudahAbsenLayout.isShown() || tidakTerhubungLayout.isShown()){
        absenLayout.setVisibility(View.GONE);
        sudahAbsenLayout.setVisibility(View.GONE);
        tidakTerhubungLayout.setVisibility(View.GONE);
        validitasProgressBarLayout.setVisibility(View.VISIBLE);
//        }

        validitasProgressBarLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (macAdress.equals(macMAP)) {
                    validitasProgressBarLayout.setVisibility(View.GONE);
                    tidakTerhubungLayout.setVisibility(View.GONE);
                    sudahAbsenLayout.setVisibility(View.GONE);
                    absenLayout.setVisibility(View.VISIBLE);
                } else {
                    validitasProgressBarLayout.setVisibility(View.GONE);
                    tidakTerhubungLayout.setVisibility(View.VISIBLE);
                    sudahAbsenLayout.setVisibility(View.GONE);
                    absenLayout.setVisibility(View.GONE);
                }
            }
        }, 1500);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_absen);
        super.onCreateOptionsMenu(menu, inflater);
    }

    ///////////////////////////
    /// GET CURRENT PRESENCE //
    ///////////////////////////

    private void getJSONAbsen() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONAbsen();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ABSEN + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONAbsen() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
//            if (jsonObject.getString("success").equals("true")) {
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
            populateView();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "JSONException", Toast.LENGTH_SHORT).show();
//            PassingIntent.signOut(getActivity(), mUserToken, sharedPreferences);
        }
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

    private LatLng getLatitudeLongitude() {
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
            return new LatLng(userLat, userLong);
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
            return new LatLng(userLat, userLong);
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

    /////////////////////
    /// cek validitas ///
    /////////////////////

    private void cekValiditas(final String macAdress) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_POST_VALIDASIPRESENSI + mUserToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            validitasProgressBarLayout.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                location = new MacLocation(
                                        jsonObject.getJSONObject("result").getString("label"),
                                        jsonObject.getJSONObject("result").getString("lantai"),
                                        jsonObject.getJSONObject("result").getString("location"),
                                        jsonObject.getJSONObject("result").getString("keterangan")
                                );
                                setLayoutKonfirmasi(jsonObject.getJSONObject("result").getString("location"), jsonObject.getJSONObject("result").getString("lantai"));
                                konfigurasi.fadeAnimation(true, absenLayout, konfigurasi.animationDurationShort);
                            } else if (jsonObject.getString("success").equals("false")) {
                                String failedLoginMessage = jsonObject.getString("result");
                                konfigurasi.fadeAnimation(true, tidakTerhubungLayout, konfigurasi.animationDurationShort);
                                Snackbar.make(rootLayout, failedLoginMessage, Snackbar.LENGTH_LONG).setAction("Message", null).show();
//                                if (failedLoginMessage.equals("Your username or password incorrect!")) {
//                                    Snackbar.make(rootLayout, "Username atau kata sandi yang Anda masukkan salah", Snackbar.LENGTH_LONG).setAction("Message", null).show();
//                                } else if (failedLoginMessage.equals("Your password incorrect!")) {
//                                    Snackbar.make(rootLayout, "Kata sandi yang Anda masukkan salah", Snackbar.LENGTH_LONG).setAction("Message", null).show();
//                                } else {
//                                    Snackbar.make(rootLayout, "Gagal melakukan Login", Snackbar.LENGTH_LONG).setAction("Message", null).show();
//                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        validitasProgressBarLayout.setVisibility(View.GONE);
                        if (error instanceof AuthFailureError) {
                            Snackbar.make(rootLayout, "Gagal mengotentifikasi", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof ServerError) {
                            Snackbar.make(rootLayout, "Masalah pada server", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof TimeoutError) {
                            Snackbar.make(rootLayout, "Waktu koneksi habis", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof NetworkError) {
                            Snackbar.make(rootLayout, "Gagal menghubungkan dengan jaringan", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof ParseError) {
                            Snackbar.make(rootLayout, "Gagal parsing data", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("macaddress", macAdress);
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

    private void setLayoutKonfirmasi(String lokasi, String lantai) {
        String konfirmasiLokasi = "Apakah Anda yakin akan merekam kehadiran di ";
        konfirmasiLokasi = konfirmasiLokasi + "lantai " + lantai + " ";
        konfirmasiLokasi = konfirmasiLokasi + lokasi + "?";
        konfirmasiLokasiText.setText(konfirmasiLokasi);
    }

    //////////////////
    /// post absen ///
    //////////////////

    private void postPresensi(final String macAdress) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                konfigurasi.fadeAnimation(false, absenLayout, konfigurasi.animationDurationShort);
                            } else {
                                String failedLoginMessage = jsonObject.getString("message");
                                if (failedLoginMessage.equals("Your username or password incorrect!")) {
                                    Snackbar.make(rootLayout, "Username atau kata sandi yang Anda masukkan salah", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                                } else if (failedLoginMessage.equals("Your password incorrect!")) {
                                    Snackbar.make(rootLayout, "Kata sandi yang Anda masukkan salah", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                                } else {
                                    Snackbar.make(rootLayout, "Gagal melakukan Login", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                                }
                                konfigurasi.fadeAnimation(false, absenLayout, konfigurasi.animationDurationShort);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            Snackbar.make(rootLayout, "Gagal mengotentifikasi", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof ServerError) {
                            Snackbar.make(rootLayout, "Masalah pada server", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof TimeoutError) {
                            Snackbar.make(rootLayout, "Waktu koneksi habis", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof NetworkError) {
                            Snackbar.make(rootLayout, "Gagal menghubungkan dengan jaringan", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        } else if (error instanceof ParseError) {
                            Snackbar.make(rootLayout, "Gagal parsing data", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                        }
//                        konfigurasi.fadeAnimation(false, mProgressView, konfigurasi.animationDurationShort);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mac_Adress", macAdress);
                params.put("lat", position.latitude + "");
                params.put("long", position.longitude + "");
                params.put("niplama", mNipLama);
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

}