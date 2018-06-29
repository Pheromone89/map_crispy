package id.go.bpkp.mobilemapbpkp.izinkantor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

public class GagalFpPengajuanPegawaiFragment extends Fragment {

    LinearLayout
            izinKantorPengajuanPeringatanAtasanLangsungView,
            izinKantorPengajuanKonfirmasiView,
            messageSuccessView,
            messageFailView,
            izinKantorPengajuanProgressView,
            formPengajuan;
    ProgressBar pengajuanProgressBar;
    EditText
            keteranganIzinKantorView,
            tanggaIzinView,
            tanggalPengajuanView,
            tanggalPengajuanAwalView,
            tanggalPengajuanAkhirView,
            lokasiEditText,
            jamIzinView;
    ProgressBar
            progressBar;
    int
            kodeJenisCuti;
    String
            tanggalIzin,
            tanggalPermohonan,
            keterangan,
            tanggalPengajuan,
            noHp,
            alasan;
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
            datePickerIzin,
            datePickerPermohonan,
            datePickerPermohonanAwal,
            datePickerPermohonanAkhir;
    TimePickerDialog.OnTimeSetListener
            timePicker;
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

    public GagalFpPengajuanPegawaiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gagal_fp_pengajuan, null);
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
        hourFormat = new SimpleDateFormat("HH:mm");

        initiateView();
        if (mAtasanLangsung == null) {
            getJSONAtasanLangsung();
        } else {
            populateView();
        }
        initiateSetOnClickMethod();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_pengajuan);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        datePickerIzin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTanggal(tanggaIzinView);
            }
        };
        datePickerPermohonan = new DatePickerDialog.OnDateSetListener() {
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
                updateTanggal(tanggalPengajuanAwalView);
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
                updateTanggal(tanggalPengajuanAkhirView);
            }
        };
        // profile area
        namaView = rootView.findViewById(R.id.gagal_fp_pengajuan_profil_nama);
        nipView = rootView.findViewById(R.id.gagal_fp_pengajuan_profil_nip);
        atasanLangsungView = rootView.findViewById(R.id.gagal_fp_pengajuan_profil_atasan_langsung_val);
        // data area
        formPengajuan = rootView.findViewById(R.id.gagal_fp_pengajuan_form_pengajuan_gagal_fp);
        tanggalPengajuanView = rootView.findViewById(R.id.gagal_fp_pengajuan_tanggal_pengajuan);
        jamIzinView = rootView.findViewById(R.id.gagal_fp_pengajuan_jam_izin);
        tanggaIzinView = rootView.findViewById(R.id.gagal_fp_pengajuan_tanggal_izin);
        lokasiEditText = rootView.findViewById(R.id.gagal_fp_pengajuan_lokasi);
        // warning form
        izinKantorPengajuanPeringatanAtasanLangsungView = rootView.findViewById(R.id.gagal_fp_pengajuan_peringatan_atasan_langsung);
        izinKantorPengajuanKonfirmasiView = rootView.findViewById(R.id.gagal_fp_pengajuan_konfirmasi);
        konfirmasiYesButton = rootView.findViewById(R.id.gagal_fp_pengajuan_konfirmasi_ya);
        konfirmasiNoButton = rootView.findViewById(R.id.gagal_fp_pengajuan_konfirmasi_tidak);
        // submit
        submitButton = rootView.findViewById(R.id.gagal_fp_pengajuan_submit_button);
        izinKantorPengajuanProgressView = rootView.findViewById(R.id.gagal_fp_pengajuan_progress);
        pengajuanProgressBar = rootView.findViewById(R.id.gagal_fp_pengajuan_progress_bar);
        // message
        messageSuccessView = rootView.findViewById(R.id.gagal_fp_pengajuan_message_success);
        messageFailView = rootView.findViewById(R.id.gagal_fp_pengajuan_message_failed);
        messageOKButton = rootView.findViewById(R.id.gagal_fp_pengajuan_message_success_button);
        messageFailButton = rootView.findViewById(R.id.gagal_fp_pengajuan_message_fail_button);
        failOverheadMessage = rootView.findViewById(R.id.gagal_fp_pengajuan_fail_overhead_message);
        // jam picker
        timePicker = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                jamIzinView.setText(hourFormat.format(calendar.getTime()));
            }
        };
    }

    private void populateView() {
        namaView.setText(mNama);
        nipView.setText(mNipBaru);
        atasanLangsungView.setText(mAtasanLangsung);

        if (tidakPunyaAtasanLangsung) {
            izinKantorPengajuanPeringatanAtasanLangsungView.setVisibility(View.VISIBLE);
            atasanLangsungView.setText("atasan langsung belum diset");
//            cutiPengajuanFormView.setVisibility(View.GONE);
        } else if (!tidakPunyaAtasanLangsung) {
            izinKantorPengajuanPeringatanAtasanLangsungView.setVisibility(View.GONE);
//            cutiPengajuanFormView.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getActivity(), "error mencari atasan langsung", Toast.LENGTH_SHORT).show();
        }

    }

    private void initiateSetOnClickMethod() {
        tanggaIzinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getActivity(),
                        datePickerIzin,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tanggalPengajuanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getActivity(),
                        datePickerPermohonan,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        jamIzinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),
                        timePicker,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (izinKantorPengajuanKonfirmasiView.getVisibility() == View.GONE) {
                    izinKantorPengajuanKonfirmasiView.setVisibility(View.VISIBLE);
                } else {
                    izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
                }
            }
        });
        konfirmasiYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (izinKantorPengajuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    izinKantorPengajuanProgressView.setVisibility(View.VISIBLE);
                    izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
                } else {
                    izinKantorPengajuanProgressView.setVisibility(View.GONE);
                    izinKantorPengajuanKonfirmasiView.setVisibility(View.VISIBLE);
                }
                checkEmpty();
            }
        });
        konfirmasiNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (izinKantorPengajuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
                } else {
                    izinKantorPengajuanKonfirmasiView.setVisibility(View.VISIBLE);
                }
            }
        });
        messageOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "sukses", Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().popBackStack();
            }
        });
        messageFailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSuccessView.setVisibility(View.GONE);
                messageFailView.setVisibility(View.GONE);
                izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
                izinKantorPengajuanProgressView.setVisibility(View.GONE);
                failOverheadMessage.setVisibility(View.VISIBLE);
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

    private void getData() {
//        int kodeJenisIzinKantorSpinner = jenisIzinSpinner.getSelectedItemPosition();
//        Date date = new Date();
//        switch (kodeJenisIzinKantorSpinner) {
//            case 0:
//                Toast.makeText(getActivity(), "jenis izin: " + jenisIzinSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "tanggal izin: " + tanggaIzinView.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "tanggal permohonan: " + tanggalPengajuanView.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "jenis alasan: " + jenisAlasanSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "alasan: " + jenisAlasanPribadiSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                break;
//            case 1:
//                Toast.makeText(getActivity(), "jenis izin: " + jenisIzinSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "tanggal izin: " + tanggaIzinView.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "tanggal permohonan: " + tanggalPengajuanView.getText(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "jenis alasan: " + jenisAlasanSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "alasan: " + jenisAlasanPenugasanSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                break;
//        }
    }

    private void postPengajuanCuti() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_PENGAJUANCUTI + mUserToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
                                izinKantorPengajuanProgressView.setVisibility(View.GONE);
                                messageSuccessView.setVisibility(View.VISIBLE);
                            } else {
                                izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
                                izinKantorPengajuanProgressView.setVisibility(View.GONE);
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
                        izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
                        izinKantorPengajuanProgressView.setVisibility(View.GONE);
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
                switch (kodeJenisCuti) {
                    case konfigurasi.KODE_CUTI_TAHUNAN:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasan", alasan);
                        params.put("tgl_awal", tanggalIzin);
                        params.put("tgl_akhir", tanggalPermohonan);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", keterangan);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_BESAR:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasan", alasan);
                        params.put("tgl_awal", tanggalIzin);
                        params.put("tgl_akhir", tanggalPermohonan);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", keterangan);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_SAKIT:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasansakit", alasan);
                        params.put("tgl_awal1", tanggalIzin);
                        params.put("tgl_akhir1", tanggalPermohonan);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", keterangan);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_MELAHIRKAN:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasanbersalin", alasan);
                        params.put("tgl_awal", tanggalIzin);
                        params.put("tgl_akhir", tanggalPermohonan);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", keterangan);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_ALASANPENTING:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasanpenting", alasan);
                        params.put("tgl_awal", tanggalIzin);
                        params.put("tgl_akhir", tanggalPermohonan);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", keterangan);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_DILUARTANGGUNGANNEGARA:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasan", alasan);
                        params.put("tgl_awal", tanggalIzin);
                        params.put("tgl_akhir", tanggalPermohonan);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", keterangan);
                        params.put("notelp_pemohon", noHp);
                        break;
                }
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
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        failOverheadMessage.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
            izinKantorPengajuanProgressView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            izinKantorPengajuanProgressView.setVisibility(View.GONE);
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            izinKantorPengajuanKonfirmasiView.setVisibility(View.VISIBLE);
        }
    }

    private void updateTanggal(EditText v) {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        v.setText(sdf.format(calendar.getTime()));
    }

    private void checkEmpty() {
        keteranganIzinKantorView.setError(null);
        tanggaIzinView.setError(null);
        tanggalPengajuanView.setError(null);
        tanggalIzin = tanggaIzinView.getText().toString();
        tanggalPermohonan = tanggalPengajuanView.getText().toString();

        boolean cancel = false;
        if (TextUtils.isEmpty(keterangan) || keterangan.equals("")) {
            keteranganIzinKantorView.setError("Kolom ini wajib diisi");
            izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
        }
        boolean allDate = true;
        if (TextUtils.isEmpty(tanggalIzin) || tanggalIzin.equals("")) {
            tanggaIzinView.setError("Kolom ini wajib diisi");
            izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
            allDate = false;
            cancel = true;
        }
        if (TextUtils.isEmpty(tanggalPermohonan) || tanggalPermohonan.equals("")) {
            tanggalPengajuanView.setError("Kolom ini wajib diisi");
            izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
            allDate = false;
            cancel = true;
        }

        if (allDate) {
            cancel = validasiTanggalIzin(tanggalIzin, tanggalPermohonan);
        }

        if (cancel) {
            izinKantorPengajuanKonfirmasiView.setVisibility(View.GONE);
            izinKantorPengajuanProgressView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "form pengajuan izin kantor belum lengkap", Toast.LENGTH_SHORT).show();
        } else {
            getData();
//            postPengajuanCuti();
        }
    }

    private boolean validasiTanggalIzin(String tanggalIzin, String tanggalPermohonan) {

        String tanggalMulaiHari;
        String tanggalMulaiBulan;
        String tanggalMulaiTahun;
        String tanggalSelesaiHari;
        String tanggalSelesaiBulan;
        String tanggalSelesaiTahun;

        tanggalMulaiHari = tanggalIzin.substring(8, 10);
        tanggalMulaiBulan = tanggalIzin.substring(5, 7);
        tanggalMulaiTahun = tanggalIzin.substring(0, 4);
        int tanggalMulaiInt = Integer.parseInt(tanggalMulaiTahun + tanggalMulaiBulan + tanggalMulaiHari);

        tanggalSelesaiHari = tanggalPermohonan.substring(8, 10);
        tanggalSelesaiBulan = tanggalPermohonan.substring(5, 7);
        tanggalSelesaiTahun = tanggalPermohonan.substring(0, 4);
        int tanggalSelesaiInt = Integer.parseInt(tanggalSelesaiTahun + tanggalSelesaiBulan + tanggalSelesaiHari);

        boolean status;

        if (tanggalSelesaiInt >= tanggalMulaiInt) {
            status = false;
        } else {
            Toast.makeText(getActivity(), "tanggal tidak valid", Toast.LENGTH_SHORT).show();
            status = true;
        }

        return status;
    }
}
