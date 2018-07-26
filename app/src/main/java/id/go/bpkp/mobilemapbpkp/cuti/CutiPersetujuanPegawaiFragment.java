package id.go.bpkp.mobilemapbpkp.cuti;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPLAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERTOKEN;

/**
 * Created by ASUS on 09/02/2018.
 */

public class CutiPersetujuanPegawaiFragment extends Fragment {

    LinearLayout
            cutiPersetujuanKonfirmasiView,
            messageSuccessView,
            messageFailView,
            cutiPersetujuanProgressView,
            formPersetujuan;
    ProgressBar persetujuanProgressBar;
    ProgressBar
            progressBar;
    int
            kodeJenisCuti;
    String
            tanggalMulai,
            tanggalSelesai,
            tanggalPengajuan,
            noHp,
            alamat,
            alasan;
    CardView
            submitButton,
            setujuButton,
            tolakButton,
            teruskanButton,
            konfirmasiYesButton,
            konfirmasiNoButton,
            messageOKButton,
            messageFailButton,
            failOverheadMessage;
    DateFormat dateFormat;
    DatePickerDialog.OnDateSetListener datePickerMulai, datePickerSelesai;
    Calendar calendar = Calendar.getInstance();
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
            mSaldoCuti,
            JSON_STRING;
    private int
            mRoleIdInt;
    private ImageView
            fotoView;
    private TextView
            namaView,
            nipView,
            atasanLangsungView,
            saldoCutiView;
    private LinearLayout
            alasanCutiView,
            alasanCutiSakitView,
            alasanCutiMelahirkanView,
            rootLayout;
    private Spinner
            jenisCutiSpinner,
            alasanCutiSakitSpinner;
    private boolean
            tidakPunyaAtasanLangsung;
    private YoYo.YoYoString ropeCutiPengajuan;
    // data persetujuan bawahan
    private String idTransaksi, fotoBawahan, namaBawahan, nipLamaBawahan, jenisCutiBawahan, tanggalPengajuanBawahan, tanggalAwalBawahan, tanggalAkhirBawahan, jumlahHariBawahan, alasanBawahan, alamatBawahan, catatanBawahan, pemrosesSebelumnya;
    private TextView jenisCutiView, tanggalMulaiView, tanggalSelesaiView, alasanView, alamatView, catatanView, catatanLabelView;
    private long animationDuration = 500;
    private boolean isFinal;
    private int isAtasanSetuju;
    private EditText catatanEditText;
    private String catatan;
    private LinearLayout catatanLayout, setujuLayout, teruskanLayout;
    private TextView successText;

    public CutiPersetujuanPegawaiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuti_persetujuan, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_persetujuan);

        //bundle dari fragment sebelumnya
        mNipLama = this.getArguments().getString(INTENT_NIPLAMA);
        mUserToken = this.getArguments().getString(INTENT_USERTOKEN);
        // data cuti yg akan disetujui
        idTransaksi = this.getArguments().getString("persetujuan_" + "id_transaksi");
        nipLamaBawahan = this.getArguments().getString("persetujuan_" + PassedIntent.INTENT_NIPLAMA);
        fotoBawahan = PassedIntent.getFoto(nipLamaBawahan);
        namaBawahan = this.getArguments().getString("persetujuan_" + PassedIntent.INTENT_NAMA);
        jenisCutiBawahan = this.getArguments().getString("persetujuan_" + "jenis_cuti");
        tanggalPengajuanBawahan = this.getArguments().getString("persetujuan_" + "tanggal_pengajuan");
        tanggalAwalBawahan = this.getArguments().getString("persetujuan_" + "tanggal_awal");
        tanggalAkhirBawahan = this.getArguments().getString("persetujuan_" + "tanggal_akhir");
        jumlahHariBawahan = this.getArguments().getString("persetujuan_" + "jumlah_hari");
        alasanBawahan = this.getArguments().getString("persetujuan_" + "alasan");
        alamatBawahan = this.getArguments().getString("persetujuan_" + "alamat");
        catatanBawahan = this.getArguments().getString("persetujuan_" + "catatan");
        pemrosesSebelumnya = this.getArguments().getString("persetujuan_" + "pemroses_sebelumnya");
        isFinal = this.getArguments().getBoolean("persetujuan_" + "is_final");

        initiateView();
        populateView();
        initiateSetOnClickMethod();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_persetujuan);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        // root
        rootLayout = rootView.findViewById(R.id.cuti_persetujuan_layout);
        rootLayout.setVisibility(View.GONE);
        // profile area
        fotoView = rootView.findViewById(R.id.cuti_persetujuan_profic_foto);
        namaView = rootView.findViewById(R.id.cuti_persetujuan_profil_nama);
        nipView = rootView.findViewById(R.id.cuti_persetujuan_profil_nip);
        // data area
        formPersetujuan = rootView.findViewById(R.id.cuti_persetujuan_form_persetujuan_cuti);
        jenisCutiView = rootView.findViewById(R.id.cuti_persetujuan_jenis_cuti);
        tanggalMulaiView = rootView.findViewById(R.id.cuti_persetujuan_tanggal_mulai);
        tanggalSelesaiView = rootView.findViewById(R.id.cuti_persetujuan_tanggal_selesai);
        alasanView = rootView.findViewById(R.id.cuti_persetujuan_alasan);
        alamatView = rootView.findViewById(R.id.cuti_persetujuan_alamat);
        catatanLayout = rootView.findViewById(R.id.cuti_persetujuan_catatan_layout);
        catatanLabelView = rootView.findViewById(R.id.cuti_persetujuan_catatan_label);
        catatanView = rootView.findViewById(R.id.cuti_persetujuan_catatan);
        catatanEditText = rootView.findViewById(R.id.cuti_persetujuan_catatan_edittext);
        // warning form
        cutiPersetujuanKonfirmasiView = rootView.findViewById(R.id.cuti_persetujuan_konfirmasi);
        konfirmasiYesButton = rootView.findViewById(R.id.cuti_persetujuan_konfirmasi_ya);
        konfirmasiNoButton = rootView.findViewById(R.id.cuti_persetujuan_konfirmasi_tidak);
        // submit
        setujuLayout = rootView.findViewById(R.id.cuti_persetujuan_setuju_layout);
        setujuButton = rootView.findViewById(R.id.cuti_persetujuan_setuju_button);
        tolakButton = rootView.findViewById(R.id.cuti_persetujuan_tolak_button);
        teruskanLayout = rootView.findViewById(R.id.cuti_persetujuan_teruskan_layout);
        teruskanButton = rootView.findViewById(R.id.cuti_persetujuan_teruskan_button);
        cutiPersetujuanProgressView = rootView.findViewById(R.id.cuti_persetujuan_progress);
        persetujuanProgressBar = rootView.findViewById(R.id.cuti_persetujuan_progress_bar);
        // message
        messageSuccessView = rootView.findViewById(R.id.cuti_persetujuan_message_success);
        successText = rootView.findViewById(R.id.cuti_persetujuan_message_success_text);
        messageFailView = rootView.findViewById(R.id.cuti_persetujuan_message_failed);
        messageOKButton = rootView.findViewById(R.id.cuti_persetujuan_message_success_button);
        messageFailButton = rootView.findViewById(R.id.cuti_persetujuan_message_fail_button);
        failOverheadMessage = rootView.findViewById(R.id.cuti_persetujuan_fail_overhead_message);
    }

    private void populateView() {
        Picasso
                .with(getActivity())
                .load(fotoBawahan)
                .into(fotoView);
        namaView.setText(namaBawahan);
        jenisCutiView.setText(jenisCutiBawahan);
        tanggalMulaiView.setText(tanggalAkhirBawahan);
        tanggalSelesaiView.setText(tanggalAkhirBawahan);
        alasanView.setText(alasanBawahan);
        alamatView.setText(alamatBawahan);
        // catatan pemroses sebelumnya
        if (pemrosesSebelumnya.equals("null")) {
            catatanLayout.setVisibility(View.GONE);
        } else {
            String catatanLabel = "Reviu sebelumnya oleh " + pemrosesSebelumnya + ":";
            catatanLabelView.setText(catatanLabel);
        }
        catatanView.setText(catatanBawahan);
        // teruskan / tolak & setuju
        if (isFinal) {
            setujuLayout.setVisibility(View.VISIBLE);
            teruskanLayout.setVisibility(View.GONE);
        } else {
            setujuLayout.setVisibility(View.GONE);
            teruskanLayout.setVisibility(View.VISIBLE);
        }

        rootLayout.setVisibility(View.VISIBLE);
        persetujuanProgressBar.setVisibility(View.GONE);
        formPersetujuan.setVisibility(View.VISIBLE);
        ropeCutiPengajuan = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(rootLayout);
    }

    private void initiateSetOnClickMethod() {

        setujuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAtasanSetuju = 1;
                if (cutiPersetujuanKonfirmasiView.getVisibility() == View.GONE) {
                    konfigurasi.fadeAnimation(true, cutiPersetujuanKonfirmasiView, animationDuration);
                } else {
                    konfigurasi.fadeAnimation(false, cutiPersetujuanKonfirmasiView, animationDuration);
                }
            }
        });
        tolakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAtasanSetuju = 0;
                if (cutiPersetujuanKonfirmasiView.getVisibility() == View.GONE) {
                    konfigurasi.fadeAnimation(true, cutiPersetujuanKonfirmasiView, animationDuration);
                } else {
                    konfigurasi.fadeAnimation(false, cutiPersetujuanKonfirmasiView, animationDuration);
                }

            }
        });
        teruskanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAtasanSetuju = 3;
                if (cutiPersetujuanKonfirmasiView.getVisibility() == View.GONE) {
                    konfigurasi.fadeAnimation(true, cutiPersetujuanKonfirmasiView, animationDuration);
                } else {
                    konfigurasi.fadeAnimation(false, cutiPersetujuanKonfirmasiView, animationDuration);
                }
            }
        });
        konfirmasiYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cutiPersetujuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    cutiPersetujuanProgressView.setVisibility(View.VISIBLE);
                    konfigurasi.fadeAnimation(false, cutiPersetujuanKonfirmasiView, animationDuration);
                } else {
                    cutiPersetujuanProgressView.setVisibility(View.GONE);
                    konfigurasi.fadeAnimation(true, cutiPersetujuanKonfirmasiView, animationDuration);
                }

                catatan = catatanEditText.getText().toString();
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        postPengajuanCuti();
                    }
                }, animationDuration);
            }
        });
        konfirmasiNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cutiPersetujuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    konfigurasi.fadeAnimation(false, cutiPersetujuanKonfirmasiView, animationDuration);
                } else {
                    konfigurasi.fadeAnimation(true, cutiPersetujuanKonfirmasiView, animationDuration);
                }
            }
        });
        messageOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "sukses", Toast.LENGTH_SHORT).show();

                FragmentManager fm = getActivity().getFragmentManager();
                fm.popBackStack("fragment_daftar_persetujuan_cuti", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                getActivity().getFragmentManager().popBackStack();
            }
        });
        messageFailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSuccessView.setVisibility(View.GONE);
                messageFailView.setVisibility(View.GONE);
                konfigurasi.fadeAnimation(false, cutiPersetujuanKonfirmasiView, animationDuration);
                cutiPersetujuanProgressView.setVisibility(View.GONE);
                konfigurasi.fadeAnimation(true, failOverheadMessage, animationDuration);
            }
        });
    }

    private void postPengajuanCuti() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_PERSETUJUANCUTI + mUserToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                cutiPersetujuanKonfirmasiView.setVisibility(View.GONE);
                                cutiPersetujuanProgressView.setVisibility(View.GONE);
                                successText.setText(jsonObject.getString("result"));
                                konfigurasi.fadeAnimation(true, messageSuccessView, animationDuration);
                            } else {
                                cutiPersetujuanKonfirmasiView.setVisibility(View.GONE);
                                cutiPersetujuanProgressView.setVisibility(View.GONE);
                                konfigurasi.fadeAnimation(true, messageFailView, animationDuration);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cutiPersetujuanKonfirmasiView.setVisibility(View.GONE);
                        cutiPersetujuanProgressView.setVisibility(View.GONE);
                        konfigurasi.fadeAnimation(true, messageFailView, animationDuration);
//                        messageFailView.setVisibility(View.VISIBLE);
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
                params.put("id", idTransaksi);
                params.put("catatan", catatan);
                params.put("status", isAtasanSetuju + "");

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
            konfigurasi.fadeAnimation(false, cutiPersetujuanKonfirmasiView, animationDuration);
            cutiPersetujuanProgressView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            konfigurasi.fadeAnimation(true, cutiPersetujuanKonfirmasiView, animationDuration);
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            cutiPersetujuanKonfirmasiView.setVisibility(View.VISIBLE);
        }
    }
}
