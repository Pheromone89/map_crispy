package id.go.bpkp.mobilemapbpkp.konfirmasipenugasan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTO;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPLAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERTOKEN;

/**
 * Created by ASUS on 09/02/2018.
 */

public class KonfirmasiPenugasanPengajuanPegawaiFragment extends Fragment {

    LinearLayout
            rootLayout,
            konfirmasiPenugasanPengajuanPeringatanAtasanLangsungView,
            konfirmasiPenugasanPengajuanKonfirmasiView,
            messageSuccessView,
            messageFailView,
            konfirmasiPenugasanPengajuanProgressView,
            formPengajuan;
    ProgressBar pengajuanProgressBar;
    Spinner
            jenisPenugasanView;
    EditText
            tanggalGagalView,
            tanggalPengajuanView,
            tanggalMulaiView,
            tanggalSelesaiView,
            keteranganView,
            lokasiEditText,
            jamGagalView;
    ProgressBar
            progressBar;
    int
            kodeJenisCuti;
    String
            tanggalGagal,
            tanggalPermohonan,
            jamGagal,
            lokasi,
            keterangan,
            tanggalPengajuan,
            noHp,
            alasan,
            tanggalMulai,
            tanggalSelesai,
            jenisPenugasan;
    CardView
            submitButton,
            konfirmasiYesButton,
            konfirmasiNoButton,
            messageOKButton,
            messageFailButton,
            failOverheadMessage;
    DateFormat
            dateFormat, hourFormat;
    DatePickerDialog.OnDateSetListener
            datePickerPengajuan,
            datePickerPermohonanAwal,
            datePickerPermohonanAkhir;
    Calendar
            calendar = Calendar.getInstance();
    private View
            rootView;
    private String
            mUserToken,
            mNipLama,
            mNipBaru,
            mNama,
            mFoto,
            mAtasanLangsung,
            mNipAtasanLangsung,
            JSON_STRING;
    private TextView
            namaView,
            nipView,
            atasanLangsungView;
    private boolean
            tidakPunyaAtasanLangsung;
    private long animationDuration = 500;
    private List<JenisPenugasan> jenisPenugasanList;
    private TextView resultGagalView;

    public KonfirmasiPenugasanPengajuanPegawaiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_konfirmasi_penugasan_pengajuan, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_pengajuan);

        //bundle dari fragment sebelumnya
        mFoto = this.getArguments().getString(INTENT_FOTO);
        mUserToken = this.getArguments().getString(INTENT_USERTOKEN);
        mNipLama = this.getArguments().getString(INTENT_NIPLAMA);
        mNipBaru = this.getArguments().getString(INTENT_NIPBARU);
        mNama = this.getArguments().getString(INTENT_NAMA);
        //role id
//        mRoleIdInt = this.getArguments().getInt("role_id");
        // bool atasan
        tidakPunyaAtasanLangsung = this.getArguments().getBoolean(INTENT_TIDAKPUNYAATASANLANGSUNG);
        mAtasanLangsung = this.getArguments().getString(INTENT_NAMAATASANLANGSUNG);
        mNipAtasanLangsung = this.getArguments().getString(INTENT_NIPATASANLANGSUNG);

        // date
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        // jenis penugasan
        jenisPenugasanList = new ArrayList<>(Arrays.<JenisPenugasan>asList());

        initiateView();
        populateView();
        getJSONJenisPenugasan();
        // dinonaktifkan dulu karena fitur ini blm memerlukan validasi atasan langsung
//        if (mAtasanLangsung == null) {
//            getJSONAtasanLangsung();
//        } else {
//            populateView();
//        }
        initiateSetOnClickMethod();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_gagal_fp_pengajuan);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        datePickerPengajuan = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTanggal(tanggalPengajuanView);
            }
        };
        datePickerPermohonanAwal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTanggal(tanggalMulaiView);
            }
        };
        datePickerPermohonanAkhir = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTanggal(tanggalSelesaiView);
            }
        };
        rootLayout = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_layout);
        rootLayout.setVisibility(View.GONE);
        // profile area
        namaView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_profil_nama);
        nipView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_profil_nip);
        atasanLangsungView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_profil_atasan_langsung_val);
        // data area
        formPengajuan = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_form_pengajuan_konfirmasi_penugasan);
        tanggalPengajuanView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_tanggal_pengajuan);
        tanggalMulaiView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_tanggal_awal);
        tanggalSelesaiView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_tanggal_akhir);
        jenisPenugasanView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_spinner_jenis);
        keteranganView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_keterangan);
        // warning form
        konfirmasiPenugasanPengajuanPeringatanAtasanLangsungView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_peringatan_atasan_langsung);
        konfirmasiPenugasanPengajuanKonfirmasiView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_konfirmasi);
        konfirmasiYesButton = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_konfirmasi_ya);
        konfirmasiNoButton = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_konfirmasi_tidak);
        // submit
        submitButton = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_submit_button);
        konfirmasiPenugasanPengajuanProgressView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_progress);
        pengajuanProgressBar = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_progress_bar);
        // message
        messageSuccessView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_message_success);
        messageFailView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_message_failed);
        resultGagalView = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_message_failed_result);
        messageOKButton = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_message_success_button);
        messageFailButton = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_message_fail_button);
        failOverheadMessage = rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_fail_overhead_message);
    }

    private void populateView() {
        namaView.setText(mNama);
        nipView.setText(mNipBaru);

        // fitur atasan langsung dinonaktifkan
//        atasanLangsungView.setText(mAtasanLangsung);

//        if (tidakPunyaAtasanLangsung) {
//            konfirmasiPenugasanPengajuanPeringatanAtasanLangsungView.setVisibility(View.VISIBLE);
//            atasanLangsungView.setText("atasan langsung belum diset");
//        } else if (!tidakPunyaAtasanLangsung) {
//            konfirmasiPenugasanPengajuanPeringatanAtasanLangsungView.setVisibility(View.GONE);
//        } else {
//            Toast.makeText(getActivity(), "error mencari atasan langsung", Toast.LENGTH_SHORT).show();
//        }

        konfirmasiPenugasanPengajuanPeringatanAtasanLangsungView.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, rootLayout, animationDuration);

    }

    private void initiateSetOnClickMethod() {
        tanggalPengajuanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getActivity(),
                        datePickerPengajuan,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tanggalMulaiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getActivity(),
                        datePickerPermohonanAwal,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tanggalSelesaiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getActivity(),
                        datePickerPermohonanAkhir,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (konfirmasiPenugasanPengajuanKonfirmasiView.getVisibility() == View.GONE) {
                    konfigurasi.fadeAnimation(true, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
                } else {
                    konfigurasi.fadeAnimation(false, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
                }
            }
        });
        konfirmasiYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (konfirmasiPenugasanPengajuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    konfigurasi.fadeAnimation(false, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
                    konfirmasiPenugasanPengajuanProgressView.setVisibility(View.VISIBLE);
                } else {
                    konfigurasi.fadeAnimation(true, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
                    konfirmasiPenugasanPengajuanProgressView.setVisibility(View.GONE);
                }
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkEmpty();
                    }
                }, animationDuration);
            }
        });
        konfirmasiNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (konfirmasiPenugasanPengajuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    konfigurasi.fadeAnimation(false, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
                } else {
                    konfigurasi.fadeAnimation(true, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
                }
            }
        });
        messageOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // didisable request bu yani
//                Toast.makeText(getActivity(), "sukses", Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().popBackStack();
            }
        });
        messageFailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSuccessView.setVisibility(View.GONE);
                messageFailView.setVisibility(View.GONE);
                konfigurasi.fadeAnimation(false, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
                konfirmasiPenugasanPengajuanProgressView.setVisibility(View.GONE);
                konfigurasi.fadeAnimation(true, failOverheadMessage, animationDuration);
            }
        });
    }

    private void getJSONAtasanLangsung() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                parseJSONAtasanLangsung();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP_ATASAN1 + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONAtasanLangsung() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            tidakPunyaAtasanLangsung = (jsonObject.getString("success").equals("false"));
            if (!tidakPunyaAtasanLangsung) {
                mAtasanLangsung = checkNull(jsonObject.getJSONObject("result").getString("nama_lengkap"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Terjadi kesalahan, silakan login kembali", Toast.LENGTH_SHORT).show();
//            signOut();
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

    private void postPengajuanKonfirmasiPenugasan() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                konfigurasi.URL_PENGAJUANKONFIRMASIPENUGASAN + mUserToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                konfirmasiPenugasanPengajuanKonfirmasiView.setVisibility(View.GONE);
                                konfirmasiPenugasanPengajuanProgressView.setVisibility(View.GONE);
                                messageSuccessView.setVisibility(View.VISIBLE);
                            } else if (jsonObject.getString("success").equals("false")) {
                                String pesanGagal = jsonObject.getString("result");
                                resultGagalView.setText(pesanGagal);

                                konfirmasiPenugasanPengajuanKonfirmasiView.setVisibility(View.GONE);
                                konfirmasiPenugasanPengajuanProgressView.setVisibility(View.GONE);
                                messageFailView.setVisibility(View.VISIBLE);

                            } else {
                                konfirmasiPenugasanPengajuanKonfirmasiView.setVisibility(View.GONE);
                                konfirmasiPenugasanPengajuanProgressView.setVisibility(View.GONE);
                                messageFailView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        konfirmasiPenugasanPengajuanKonfirmasiView.setVisibility(View.GONE);
                        konfirmasiPenugasanPengajuanProgressView.setVisibility(View.GONE);
                        messageFailView.setVisibility(View.VISIBLE);
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
                        showProgress(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // parameter konfirmasi penugasan
                params.put("niplama", mNipLama);
                params.put("tanggal_mulai", tanggalMulai);
                params.put("tanggal_selesai", tanggalSelesai);
                params.put("jenis_penugasan", jenisPenugasan);
                params.put("catatan", keterangan);

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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        failOverheadMessage.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            konfigurasi.fadeAnimation(false, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
            konfirmasiPenugasanPengajuanProgressView.setVisibility(View.VISIBLE);
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//            progressBar.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
        } else {
            konfigurasi.fadeAnimation(true, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            konfirmasiPenugasanPengajuanKonfirmasiView.setVisibility(View.VISIBLE);
        }
    }

    private void updateTanggal(EditText v) {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        v.setText(sdf.format(calendar.getTime()));
    }

    private void getData() {
        tanggalPengajuan = tanggalPengajuanView.getText().toString();
        tanggalMulai = tanggalMulaiView.getText().toString();
        tanggalSelesai = tanggalSelesaiView.getText().toString();
        keterangan = keteranganView.getText().toString();

        // test jenis penugasan
//        Toast.makeText(getActivity(), jenisPenugasan, Toast.LENGTH_SHORT).show();
    }

    private void checkEmpty() {
        getData();
        tanggalPengajuanView.setError(null);
        tanggalMulaiView.setError(null);
        tanggalSelesaiView.setError(null);

        boolean cancel = false;
        boolean allDate = true;
        if (TextUtils.isEmpty(tanggalPengajuan) || tanggalPengajuan.equals("")) {
            tanggalPengajuanView.setError("Kolom ini wajib diisi");
            konfirmasiPenugasanPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
        }
        if (TextUtils.isEmpty(tanggalMulai) || tanggalMulai.equals("")) {
            tanggalMulaiView.setError("Kolom ini wajib diisi");
            konfirmasiPenugasanPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
            allDate = false;
        }
        if (TextUtils.isEmpty(tanggalSelesai) || tanggalSelesai.equals("")) {
            tanggalSelesaiView.setError("Kolom ini wajib diisi");
            konfirmasiPenugasanPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
            allDate = false;
        }

        // cek tanggal awal n akhir
        if (allDate) {
            cancel = validasiTanggalKonfirmasi(tanggalMulai, tanggalSelesai);
        }

        if (cancel) {
            konfigurasi.fadeAnimation(false, konfirmasiPenugasanPengajuanKonfirmasiView, animationDuration);
            konfirmasiPenugasanPengajuanProgressView.setVisibility(View.GONE);
            if (!allDate) {
                Toast.makeText(getActivity(), "form pengajuan izin kantor belum lengkap", Toast.LENGTH_SHORT).show();
            }
        } else {
            getData();
            postPengajuanKonfirmasiPenugasan();
        }
    }

    private boolean validasiTanggalKonfirmasi(String tanggalAwal, String tanggalAkhir) {

        String tanggalMulaiHari;
        String tanggalMulaiBulan;
        String tanggalMulaiTahun;
        String tanggalSelesaiHari;
        String tanggalSelesaiBulan;
        String tanggalSelesaiTahun;

        tanggalMulaiHari = tanggalAwal.substring(8, 10);
        tanggalMulaiBulan = tanggalAwal.substring(5, 7);
        tanggalMulaiTahun = tanggalAwal.substring(0, 4);
        int tanggalMulaiInt = Integer.parseInt(tanggalMulaiTahun + tanggalMulaiBulan + tanggalMulaiHari);

        tanggalSelesaiHari = tanggalAkhir.substring(8, 10);
        tanggalSelesaiBulan = tanggalAkhir.substring(5, 7);
        tanggalSelesaiTahun = tanggalAkhir.substring(0, 4);
        int tanggalSelesaiInt = Integer.parseInt(tanggalSelesaiTahun + tanggalSelesaiBulan + tanggalSelesaiHari);

        boolean status;

        if (tanggalSelesaiInt >= tanggalMulaiInt) {
            status = false;
        } else {
            Toast.makeText(getActivity(), "tanggal tidak valid", Toast.LENGTH_SHORT).show();
            tanggalMulaiView.setError("tanggal tidak valid");
            tanggalSelesaiView.setError("tanggal tidak valid");
            status = true;
        }
        return status;
    }


    private void getJSONJenisPenugasan() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(getActivity(), "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                JSON_STRING = s;
                parseJSONJenisPenugasan();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_JENISPENUGASAN + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSONJenisPenugasan() {
        List<String> spinnerArray = new ArrayList<String>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {
                JSONArray result = jsonObject.getJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String id = jo.getString("id");
                    String jenisPenugasan = jo.getString("jenis_penugasan");
                    jenisPenugasanList.add(
                            new JenisPenugasan(
                                    Integer.parseInt(id),
                                    jenisPenugasan
                            )
                    );
                    spinnerArray.add(jenisPenugasan);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Kesalahan mengambil data jenis penugasan", Toast.LENGTH_SHORT).show();
            jenisPenugasanList.add(
                    new JenisPenugasan(
                            99999,
                            "-"
                    )
            );
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) rootView.findViewById(R.id.konfirmasi_penugasan_pengajuan_spinner_jenis);
        sItems.setAdapter(adapter);

        jenisPenugasanView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (jenisPenugasanList.size() != 0) {
                    jenisPenugasan = Integer.toString(jenisPenugasanList.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    class JenisPenugasan {
        int id;
        String jenisPenugasan;

        public JenisPenugasan(int id, String jenisPenugasan) {
            this.id = id;
            this.jenisPenugasan = jenisPenugasan;
        }

        public int getId() {
            return id;
        }

        public String getJenisPenugasan() {
            return jenisPenugasan;
        }
    }

}
