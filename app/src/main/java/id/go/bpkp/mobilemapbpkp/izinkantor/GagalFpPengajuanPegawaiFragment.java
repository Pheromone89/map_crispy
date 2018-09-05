package id.go.bpkp.mobilemapbpkp.izinkantor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDashboardAdminFragment;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDashboardPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.konfigurasi.UserRole;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTO;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTOURL;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISATASAN;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPLAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NOHP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ROLEIDINT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERTOKEN;

/**
 * Created by ASUS on 09/02/2018.
 */

public class GagalFpPengajuanPegawaiFragment extends Fragment {

    LinearLayout
            gagalFpPengajuanPeringatanAtasanLangsungView,
            gagalFpPengajuanKonfirmasiView,
            messageSuccessView,
            messageFailView,
            gagalFpPengajuanProgressView,
            formPengajuan;
    ProgressBar pengajuanProgressBar;
    EditText
            tanggalGagalView,
            tanggalPengajuanView,
            lokasiEditText,
            jamGagalView;
    ProgressBar
            progressBar;
    int
            kodeJenisCuti,
            mRoleIdInt;
    String
            tanggalGagal,
            tanggalPermohonan,
            jamGagal,
            lokasi,
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
            mFotoUrl,
            mNoHp,
            mAtasanLangsung,
            mNipAtasanLangsung,
            JSON_STRING;
    private TextView
            namaView,
            nipView,
            atasanLangsungView;
    private boolean
            tidakPunyaAtasanLangsung,
            isAtasan;
    private LinearLayout rootLayout;

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
        mRoleIdInt = this.getArguments().getInt("role_id");
        // bool atasan
        tidakPunyaAtasanLangsung = this.getArguments().getBoolean(INTENT_TIDAKPUNYAATASANLANGSUNG);
        mAtasanLangsung = this.getArguments().getString(INTENT_NAMAATASANLANGSUNG);
        mNipAtasanLangsung = this.getArguments().getString(INTENT_NIPATASANLANGSUNG);

        mFotoUrl = this.getArguments().getString(INTENT_FOTOURL);
        isAtasan = this.getArguments().getBoolean(INTENT_ISATASAN);

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_gagal_fp_pengajuan);
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
                updateTanggal(tanggalGagalView);
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
        // root
        rootLayout = rootView.findViewById(R.id.gagal_fp_pengajuan_layout);
        rootLayout.setVisibility(View.GONE);
        // profile area
        namaView = rootView.findViewById(R.id.gagal_fp_pengajuan_profil_nama);
        nipView = rootView.findViewById(R.id.gagal_fp_pengajuan_profil_nip);
        atasanLangsungView = rootView.findViewById(R.id.gagal_fp_pengajuan_profil_atasan_langsung_val);
        // data area
        formPengajuan = rootView.findViewById(R.id.gagal_fp_pengajuan_form_pengajuan_gagal_fp);
        tanggalPengajuanView = rootView.findViewById(R.id.gagal_fp_pengajuan_tanggal_pengajuan);
        jamGagalView = rootView.findViewById(R.id.gagal_fp_pengajuan_jam_izin);
        tanggalGagalView = rootView.findViewById(R.id.gagal_fp_pengajuan_tanggal_izin);
        lokasiEditText = rootView.findViewById(R.id.gagal_fp_pengajuan_lokasi);
        // warning form
        gagalFpPengajuanPeringatanAtasanLangsungView = rootView.findViewById(R.id.gagal_fp_pengajuan_peringatan_atasan_langsung);
        gagalFpPengajuanKonfirmasiView = rootView.findViewById(R.id.gagal_fp_pengajuan_konfirmasi);
        konfirmasiYesButton = rootView.findViewById(R.id.gagal_fp_pengajuan_konfirmasi_ya);
        konfirmasiNoButton = rootView.findViewById(R.id.gagal_fp_pengajuan_konfirmasi_tidak);
        // submit
        submitButton = rootView.findViewById(R.id.gagal_fp_pengajuan_submit_button);
        gagalFpPengajuanProgressView = rootView.findViewById(R.id.gagal_fp_pengajuan_progress);
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
                jamGagalView.setText(hourFormat.format(calendar.getTime()));
            }
        };
    }

    private void populateView() {
        namaView.setText(mNama);
        nipView.setText(mNipBaru);
        atasanLangsungView.setText(mAtasanLangsung);

        if (tidakPunyaAtasanLangsung) {
            gagalFpPengajuanPeringatanAtasanLangsungView.setVisibility(View.VISIBLE);
            atasanLangsungView.setText("atasan langsung belum diset");
        } else if (!tidakPunyaAtasanLangsung) {
            gagalFpPengajuanPeringatanAtasanLangsungView.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), "error mencari atasan langsung", Toast.LENGTH_SHORT).show();
        }

        konfigurasi.fadeAnimation(true, rootLayout, konfigurasi.animationDurationShort);
    }

    private void initiateSetOnClickMethod() {
        tanggalGagalView.setOnClickListener(new View.OnClickListener() {
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
        jamGagalView.setOnClickListener(new View.OnClickListener() {
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
                if (gagalFpPengajuanKonfirmasiView.getVisibility() == View.GONE) {
                    konfigurasi.fadeAnimation(true, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                } else {
                    konfigurasi.fadeAnimation(false, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                }
            }
        });
        konfirmasiYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gagalFpPengajuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    konfigurasi.fadeAnimation(false, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                    gagalFpPengajuanProgressView.setVisibility(View.VISIBLE);
                } else {
                    konfigurasi.fadeAnimation(true, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                    gagalFpPengajuanProgressView.setVisibility(View.GONE);
                }
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkEmpty();
                    }
                }, konfigurasi.animationDurationShort);
            }
        });
        konfirmasiNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gagalFpPengajuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    konfigurasi.fadeAnimation(false, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                } else {
                    konfigurasi.fadeAnimation(true, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                }
            }
        });
        messageOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // didisable request bu yani
//                Toast.makeText(getActivity(), "sukses", Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().popBackStack();

                Fragment fragment;
                String fragmentTag;
                if (mRoleIdInt == UserRole.USER_ROLE_SUPERADMIN ||
                        mRoleIdInt == UserRole.USER_ROLE_ADMINUNIT ||
                        mRoleIdInt == UserRole.USER_ROLE_ADMINPUSAT) {
                    // admin
                    Bundle bundle = new Bundle();
                    bundle.putString(INTENT_USERTOKEN, mUserToken);
                    bundle.putString(INTENT_NIPLAMA, mNipLama);
                    bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);
                    bundle.putString(INTENT_NAMA, mNama);
                    bundle.putString(INTENT_FOTOURL, mFotoUrl);
                    bundle.putString(INTENT_FOTO, mFoto);
                    bundle.putString(INTENT_NIPBARU, mNipBaru);
                    bundle.putString(INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
                    bundle.putString(INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
                    bundle.putBoolean(INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);

                    fragment = new IzinKantorDashboardAdminFragment();
                    fragment.setArguments(bundle);
                    fragmentTag = getResources().getString(R.string.title_fragment_izin_kantor_dashboard_pegawai);
                } else {
                    // pegawai
                    Bundle bundle = new Bundle();
                    bundle.putString(INTENT_USERTOKEN, mUserToken);
                    bundle.putString(INTENT_NIPLAMA, mNipLama);
                    bundle.putInt(INTENT_ROLEIDINT, mRoleIdInt);
                    bundle.putBoolean(INTENT_ISATASAN, isAtasan);
                    bundle.putString(INTENT_NAMA, mNama);
                    bundle.putString(INTENT_FOTOURL, mFotoUrl);
                    bundle.putString(INTENT_FOTO, mFoto);
                    bundle.putString(INTENT_NIPBARU, mNipBaru);

                    fragment = new IzinKantorDashboardPegawaiFragment();
                    fragment.setArguments(bundle);
                    fragmentTag = getResources().getString(R.string.title_fragment_izin_kantor_dashboard_pegawai);
                }

                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.content_fragment_area, fragment, fragmentTag);
                fragmentTransaction.add(R.id.content_fragment_area, fragment);
                fragmentManager.popBackStack("fragment_dashboard_izin_kantor", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.commit();

            }
        });
        messageFailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSuccessView.setVisibility(View.GONE);
                messageFailView.setVisibility(View.GONE);
                konfigurasi.fadeAnimation(false, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                gagalFpPengajuanProgressView.setVisibility(View.GONE);
                konfigurasi.fadeAnimation(true, failOverheadMessage, konfigurasi.animationDurationShort);
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
        tanggalPengajuan = tanggalPengajuanView.getText().toString();
        jamGagal = jamGagalView.getText().toString();
        tanggalGagal = tanggalGagalView.getText().toString();
        lokasi = lokasiEditText.getText().toString();
//        int kodeJenisIzinKantorSpinner = jenisIzinSpinner.getSelectedItemPosition();
    }

    private void postPengajuanCuti() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                konfigurasi.URL_PENGAJUANIZINKANTOR + mUserToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                gagalFpPengajuanKonfirmasiView.setVisibility(View.GONE);
                                gagalFpPengajuanProgressView.setVisibility(View.GONE);
                                messageSuccessView.setVisibility(View.VISIBLE);
                            } else {
                                gagalFpPengajuanKonfirmasiView.setVisibility(View.GONE);
                                gagalFpPengajuanProgressView.setVisibility(View.GONE);
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
                        gagalFpPengajuanKonfirmasiView.setVisibility(View.GONE);
                        gagalFpPengajuanProgressView.setVisibility(View.GONE);
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
//                        showProgress(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // parameter fingerprint
                params.put("pegawai", mNipLama);
                params.put("kd_kat_alasan", "3");
                params.put("kd_jenisizin", "4");
                params.put("kd_alasan", "8");
                params.put("lokasi_fprint", lokasi);
                params.put("waktu_fprint", jamGagal);
                params.put("tgl_fprint", tanggalGagal);
                params.put("tgl_srt_fprint", tanggalPengajuan);

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

//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        failOverheadMessage.setVisibility(View.GONE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//            konfigurasi.fadeAnimation(false, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
//            gagalFpPengajuanProgressView.setVisibility(View.VISIBLE);
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//            progressBar.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            konfigurasi.fadeAnimation(true, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//            gagalFpPengajuanKonfirmasiView.setVisibility(View.VISIBLE);
//        }
//    }

    private void updateTanggal(EditText v) {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        v.setText(sdf.format(calendar.getTime()));
    }

    private void checkEmpty() {
        lokasiEditText.setError(null);
        tanggalGagalView.setError(null);
        tanggalPengajuanView.setError(null);
        jamGagalView.setError(null);
        lokasi = lokasiEditText.getText().toString();
        tanggalGagal = tanggalGagalView.getText().toString();
        tanggalPengajuan = tanggalPengajuanView.getText().toString();
        jamGagal = jamGagalView.getText().toString();

        boolean cancel = false;
        if (TextUtils.isEmpty(lokasi) || lokasi.equals("")) {
            lokasiEditText.setError("Kolom ini wajib diisi");
//            lokasiEditText.setError("lokasi");
            gagalFpPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
        }
        if (TextUtils.isEmpty(tanggalGagal) || tanggalGagal.equals("")) {
            tanggalGagalView.setError("Kolom ini wajib diisi");
//            tanggalGagalView.setError("tanggal gagal");
            gagalFpPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
        }
        if (TextUtils.isEmpty(tanggalPengajuan) || tanggalPengajuan.equals("")) {
            tanggalPengajuanView.setError("Kolom ini wajib diisi");
//            tanggalPengajuanView.setError("tanggal pengajuan");
            gagalFpPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
        }
        if (TextUtils.isEmpty(jamGagal) || jamGagalView.equals("")) {
            jamGagalView.setError("Kolom ini wajib diisi");
//            jamGagalView.setError("jam gagal");
            gagalFpPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
        }

        if (cancel) {
            konfigurasi.fadeAnimation(false, gagalFpPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
            gagalFpPengajuanProgressView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "form pengajuan izin kantor belum lengkap", Toast.LENGTH_SHORT).show();
        } else {
            getData();
            postPengajuanCuti();
        }
    }
}
