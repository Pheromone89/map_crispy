package id.go.bpkp.mobilemapbpkp.cuti;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.DatePicker;
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
import id.go.bpkp.mobilemapbpkp.konfigurasi.UserRole;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import pl.droidsonroids.gif.GifImageView;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTO;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTOURL;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISATASAN;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMA;
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

public class CutiPengajuanPegawaiFragment extends Fragment {

    LinearLayout
            cutiPengajuanPeringatanAtasanLangsungView,
            cutiPengajuanKonfirmasiView,
            messageSuccessView,
            messageFailView,
            cutiPengajuanProgressView,
            formPengajuan;
    GifImageView pengajuanProgressBar;
    EditText
            tanggalMulaiView,
            tanggalSelesaiView,
            alasanCutiMelahirkanEditText,
            alasanCutiEditText,
            alamatEditText,
            noHpEditText;
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
            mFotoUrl,
            mNoHp,
            mAtasanLangsung,
            mNipAtasanLangsung,
            mSaldoCuti,
            JSON_STRING;
    private int
            mRoleIdInt;
    private ImageView
            proficView;
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
            tidakPunyaAtasanLangsung,
            isAtasan;
    private YoYo.YoYoString ropeCutiPengajuan;

    public CutiPengajuanPegawaiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuti_pengajuan, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_pengajuan);

        //bundle dari fragment sebelumnya
        mFoto = this.getArguments().getString(INTENT_FOTO);
        mFotoUrl = this.getArguments().getString(INTENT_FOTOURL);
        mUserToken = this.getArguments().getString(INTENT_USERTOKEN);
        mNipLama = this.getArguments().getString(INTENT_NIPLAMA);
        mNipBaru = this.getArguments().getString(INTENT_NIPBARU);
        mNama = this.getArguments().getString(INTENT_NAMA);
        mNoHp = this.getArguments().getString(INTENT_NOHP);
        //role id
        mRoleIdInt = this.getArguments().getInt("role_id");
        // bool atasan
        isAtasan = this.getArguments().getBoolean(INTENT_ISATASAN);
        tidakPunyaAtasanLangsung = this.getArguments().getBoolean(INTENT_TIDAKPUNYAATASANLANGSUNG);
        mAtasanLangsung = this.getArguments().getString(INTENT_NAMAATASANLANGSUNG);
        mNipAtasanLangsung = this.getArguments().getString(INTENT_NIPATASANLANGSUNG);
        // saldo cuti
        mSaldoCuti = this.getArguments().getString("saldo_cuti");

        // date
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_pengajuan);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        datePickerMulai = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTanggalMulai();
            }
        };
        datePickerSelesai = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTanggalSelesai();
            }
        };
        // root
        rootLayout = rootView.findViewById(R.id.cuti_pengajuan_layout);
        rootLayout.setVisibility(View.GONE);
        // profile area
        namaView = rootView.findViewById(R.id.cuti_pengajuan_profil_nama);
        nipView = rootView.findViewById(R.id.cuti_pengajuan_profil_nip);
        atasanLangsungView = rootView.findViewById(R.id.cuti_pengajuan_profil_atasan_langsung_val);
        saldoCutiView = rootView.findViewById(R.id.cuti_pengajuan_profil_saldo_val);
        // data area
        formPengajuan = rootView.findViewById(R.id.cuti_pengajuan_form_pengajuan_cuti);
        tanggalMulaiView = rootView.findViewById(R.id.cuti_pengajuan_tanggal_mulai);
        tanggalSelesaiView = rootView.findViewById(R.id.cuti_pengajuan_tanggal_selesai);
        jenisCutiSpinner = rootView.findViewById(R.id.cuti_pengajuan_spinner_jenis);
        alasanCutiView = rootView.findViewById(R.id.pengajuan_cuti_alasan);
        alasanCutiEditText = rootView.findViewById(R.id.cuti_pengajuan_alasan);
        alasanCutiSakitView = rootView.findViewById(R.id.pengajuan_cuti_alasan_sakit);
        alasanCutiSakitSpinner = rootView.findViewById(R.id.cuti_pengajuan_spinner_alasan_sakit);
        alasanCutiMelahirkanView = rootView.findViewById(R.id.pengajuan_cuti_alasan_melahirkan);
        alasanCutiMelahirkanEditText = rootView.findViewById(R.id.cuti_pengajuan_alasan_melahirkan);
        alamatEditText = rootView.findViewById(R.id.cuti_pengajuan_alamat);
        noHpEditText = rootView.findViewById(R.id.cuti_pengajuan_no_hp);
        // warning fotm
        cutiPengajuanPeringatanAtasanLangsungView = rootView.findViewById(R.id.cuti_pengajuan_peringatan_atasan_langsung);
        cutiPengajuanKonfirmasiView = rootView.findViewById(R.id.cuti_pengajuan_konfirmasi);
        konfirmasiYesButton = rootView.findViewById(R.id.cuti_pengajuan_konfirmasi_ya);
        konfirmasiNoButton = rootView.findViewById(R.id.cuti_pengajuan_konfirmasi_tidak);
        // submit
        submitButton = rootView.findViewById(R.id.cuti_pengajuan_submit_button);
        cutiPengajuanProgressView = rootView.findViewById(R.id.cuti_pengajuan_progress);
        pengajuanProgressBar = rootView.findViewById(R.id.cuti_pengajuan_progress_bar);
        // message
        messageSuccessView = rootView.findViewById(R.id.cuti_pengajuan_message_success);
        messageFailView = rootView.findViewById(R.id.cuti_pengajuan_message_failed);
        messageOKButton = rootView.findViewById(R.id.cuti_pengajuan_message_success_button);
        messageFailButton = rootView.findViewById(R.id.cuti_pengajuan_message_fail_button);
        failOverheadMessage = rootView.findViewById(R.id.cuti_pengajuan_fail_overhead_message);
    }

    private void populateView() {
        namaView.setText(mNama);
        nipView.setText(mNipBaru);
        atasanLangsungView.setText(mAtasanLangsung);
        saldoCutiView.setText(mSaldoCuti);

        if (tidakPunyaAtasanLangsung) {
            cutiPengajuanPeringatanAtasanLangsungView.setVisibility(View.VISIBLE);
            atasanLangsungView.setText("atasan langsung belum diset");
//            cutiPengajuanFormView.setVisibility(View.GONE);
        } else if (!tidakPunyaAtasanLangsung) {
            cutiPengajuanPeringatanAtasanLangsungView.setVisibility(View.GONE);
            getJSONalamatHp();
//            cutiPengajuanFormView.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getActivity(), "error mencari atasan langsung", Toast.LENGTH_SHORT).show();
        }

        konfigurasi.fadeAnimation(true, rootLayout, konfigurasi.animationDurationShort);
    }

    private void populateViewAlamatHp() {
        alamatEditText.setText(alamat);
        noHpEditText.setText(noHp);
    }

    private void initiateSetOnClickMethod() {
        tanggalMulaiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        getActivity(),
                        datePickerMulai,
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
                        datePickerSelesai,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        jenisCutiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 4:
                        alasanCutiView.setVisibility(View.GONE);
                        alasanCutiSakitView.setVisibility(View.GONE);
                        alasanCutiMelahirkanView.setVisibility(View.VISIBLE);
//                        Toast.makeText(getActivity(), Integer.toString(i), Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        alasanCutiView.setVisibility(View.GONE);
                        alasanCutiSakitView.setVisibility(View.VISIBLE);
                        alasanCutiMelahirkanView.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(), Integer.toString(i), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        alasanCutiView.setVisibility(View.VISIBLE);
                        alasanCutiSakitView.setVisibility(View.GONE);
                        alasanCutiMelahirkanView.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(), Integer.toString(i), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cutiPengajuanKonfirmasiView.getVisibility() == View.GONE) {
                    konfigurasi.fadeAnimation(true, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                } else {
                    konfigurasi.fadeAnimation(false, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                }
            }
        });
        konfirmasiYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cutiPengajuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    konfigurasi.fadeAnimation(false, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                    cutiPengajuanProgressView.setVisibility(View.VISIBLE);
                } else {
                    konfigurasi.fadeAnimation(true, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                    cutiPengajuanProgressView.setVisibility(View.GONE);
                }
                final String kodeJenisCutiString = jenisCutiSpinner.getSelectedItem().toString();

                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkEmpty(kodeJenisCutiString);
                    }
                }, konfigurasi.animationDurationShort);
            }
        });
        konfirmasiNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cutiPengajuanKonfirmasiView.getVisibility() == View.VISIBLE) {
                    konfigurasi.fadeAnimation(false, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                } else {
                    konfigurasi.fadeAnimation(true, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                }
            }
        });
        messageOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // didisable request bu yani
//                Toast.makeText(getActivity(), "sukses", Toast.LENGTH_SHORT).show();

//                Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("fragment_dashboard_cuti");
//                final FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
//                ft.detach(fragment);
//                ft.attach(fragment);

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
                    bundle.putString(INTENT_NOHP, mNoHp);
                    bundle.putString(INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
                    bundle.putString(INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
                    bundle.putBoolean(INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);

                    fragment = new CutiDashboardAdminFragment();
                    fragment.setArguments(bundle);
//                    fragmentTag = getResources().getString(R.string.title_fragment_cuti_dashboard_pegawai);
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
                    bundle.putString(INTENT_NOHP, mNoHp);

                    fragment = new CutiDashboardPegawaiFragment();
                    fragment.setArguments(bundle);
//                    fragmentTag = getResources().getString(R.string.title_fragment_cuti_dashboard_pegawai);
                }

                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.content_fragment_area, fragment, fragmentTag);
                fragmentTransaction.add(R.id.content_fragment_area, fragment);
                fragmentManager.popBackStack("fragment_dashboard_cuti", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.commit();
            }
        });
        messageFailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSuccessView.setVisibility(View.GONE);
                messageFailView.setVisibility(View.GONE);
                konfigurasi.fadeAnimation(false, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
                cutiPengajuanProgressView.setVisibility(View.GONE);
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

    private void getJSONalamatHp() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pengajuanProgressBar.setVisibility(View.VISIBLE);
                formPengajuan.setVisibility(View.GONE);
//                loading = ProgressDialog.show(getActivity(),"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pengajuanProgressBar.setVisibility(View.GONE);
                formPengajuan.setVisibility(View.VISIBLE);
//                loading.dismiss();
                JSON_STRING = s;
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void parseJSON() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            alamat = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_ALAMAT));
            noHp = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NOHP));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Gagal mengambil alamat dan nomor telepon", Toast.LENGTH_SHORT).show();
        }
        populateViewAlamatHp();
    }

    private void getData() {
        String kodeJenisCutiSpinner = jenisCutiSpinner.getSelectedItem().toString();
        Date date = new Date();
        switch (kodeJenisCutiSpinner) {
            case konfigurasi.CUTI_TAHUNAN:
                kodeJenisCuti = konfigurasi.KODE_CUTI_TAHUNAN;
                alasan = alasanCutiEditText.getText().toString();
                tanggalMulai = tanggalMulaiView.getText().toString();
                tanggalSelesai = tanggalSelesaiView.getText().toString();
                tanggalPengajuan = dateFormat.format(date);
                break;
            case konfigurasi.CUTI_BESAR:
                kodeJenisCuti = konfigurasi.KODE_CUTI_BESAR;
                alasan = alasanCutiEditText.getText().toString();
                tanggalMulai = tanggalMulaiView.getText().toString();
                tanggalSelesai = tanggalSelesaiView.getText().toString();
                tanggalPengajuan = dateFormat.format(date);
                break;
            case konfigurasi.CUTI_DILUARTANGGUNGANNEGARA:
                kodeJenisCuti = konfigurasi.KODE_CUTI_DILUARTANGGUNGANNEGARA;
                alasan = alasanCutiEditText.getText().toString();
                tanggalMulai = tanggalMulaiView.getText().toString();
                tanggalSelesai = tanggalSelesaiView.getText().toString();
                tanggalPengajuan = dateFormat.format(date);
                break;
            case konfigurasi.CUTI_ALASANPENTING:
                kodeJenisCuti = konfigurasi.KODE_CUTI_ALASANPENTING;
                alasan = alasanCutiEditText.getText().toString();
                tanggalMulai = tanggalMulaiView.getText().toString();
                tanggalSelesai = tanggalSelesaiView.getText().toString();
                tanggalPengajuan = dateFormat.format(date);
                break;
            case konfigurasi.CUTI_MELAHIRKAN:
                kodeJenisCuti = konfigurasi.KODE_CUTI_MELAHIRKAN;
                alasan = alasanCutiMelahirkanEditText.getText().toString();
                tanggalMulai = tanggalMulaiView.getText().toString();
                tanggalSelesai = tanggalSelesaiView.getText().toString();
                tanggalPengajuan = dateFormat.format(date);
                break;
            case konfigurasi.CUTI_SAKIT:
                kodeJenisCuti = konfigurasi.KODE_CUTI_SAKIT;
                alasan = alasanCutiSakitSpinner.getSelectedItem().toString();
                tanggalMulai = tanggalMulaiView.getText().toString();
                tanggalSelesai = tanggalSelesaiView.getText().toString();
                tanggalPengajuan = dateFormat.format(date);
                break;
        }
    }

    private void postPengajuanCuti() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_PENGAJUANCUTI + mUserToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")) {
                                cutiPengajuanKonfirmasiView.setVisibility(View.GONE);
                                cutiPengajuanProgressView.setVisibility(View.GONE);
                                messageSuccessView.setVisibility(View.VISIBLE);
                            } else {
                                cutiPengajuanKonfirmasiView.setVisibility(View.GONE);
                                cutiPengajuanProgressView.setVisibility(View.GONE);
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
                        cutiPengajuanKonfirmasiView.setVisibility(View.GONE);
                        cutiPengajuanProgressView.setVisibility(View.GONE);
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
                switch (kodeJenisCuti) {
                    case konfigurasi.KODE_CUTI_TAHUNAN:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasan", alasan);
                        params.put("tgl_awal", tanggalMulai);
                        params.put("tgl_akhir", tanggalSelesai);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", alamat);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_BESAR:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasan", alasan);
                        params.put("tgl_awal", tanggalMulai);
                        params.put("tgl_akhir", tanggalSelesai);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", alamat);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_SAKIT:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasansakit", alasan);
                        params.put("tgl_awal1", tanggalMulai);
                        params.put("tgl_akhir1", tanggalSelesai);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", alamat);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_MELAHIRKAN:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasanbersalin", alasan);
                        params.put("tgl_awal", tanggalMulai);
                        params.put("tgl_akhir", tanggalSelesai);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", alamat);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_ALASANPENTING:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasanpenting", alasan);
                        params.put("tgl_awal", tanggalMulai);
                        params.put("tgl_akhir", tanggalSelesai);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", alamat);
                        params.put("notelp_pemohon", noHp);
                        break;
                    case konfigurasi.KODE_CUTI_DILUARTANGGUNGANNEGARA:
                        params.put("pegawai", mNipLama);
                        params.put("kd_jenis_cuti", Integer.toString(kodeJenisCuti));
                        params.put("alasan", alasan);
                        params.put("tgl_awal", tanggalMulai);
                        params.put("tgl_akhir", tanggalSelesai);
                        params.put("tgl_pengajuan", tanggalPengajuan);
                        params.put("alamat_pemohon", alamat);
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

//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        failOverheadMessage.setVisibility(View.GONE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//            konfigurasi.fadeAnimation(false, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
//            cutiPengajuanProgressView.setVisibility(View.VISIBLE);
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//            progressBar.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            konfigurasi.fadeAnimation(true, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
//            cutiPengajuanProgressView.setVisibility(View.GONE);
//            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//        }
//    }

    private void updateTanggalMulai() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tanggalMulaiView.setText(sdf.format(calendar.getTime()));
    }

    private void updateTanggalSelesai() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tanggalSelesaiView.setText(sdf.format(calendar.getTime()));
    }

    private void checkEmpty(String kodeJenisCutiString) {
        alamatEditText.setError(null);
        noHpEditText.setError(null);
        tanggalMulaiView.setError(null);
        tanggalSelesaiView.setError(null);
        switch (kodeJenisCutiString) {
            case konfigurasi.CUTI_MELAHIRKAN:
                alasanCutiMelahirkanEditText.setError(null);
                break;
            case konfigurasi.CUTI_SAKIT:
                break;
            default:
                alasanCutiEditText.setError(null);
                break;
        }
        alamat = alamatEditText.getText().toString();
        noHp = noHpEditText.getText().toString();
        tanggalMulai = tanggalMulaiView.getText().toString();
        tanggalSelesai = tanggalSelesaiView.getText().toString();
        switch (kodeJenisCutiString) {
            case konfigurasi.CUTI_MELAHIRKAN:
                alasan = alasanCutiMelahirkanEditText.getText().toString();
                break;
            case konfigurasi.CUTI_SAKIT:
                alasan = alasanCutiSakitSpinner.getSelectedItem().toString();
                break;
            default:
                alasan = alasanCutiEditText.getText().toString();
                break;
        }
        boolean cancel = false;
        if (TextUtils.isEmpty(alamat) || alamat.equals("")) {
            alamatEditText.setError("Kolom ini wajib diisi");
            cutiPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
        }
        if (TextUtils.isEmpty(noHp) || noHp.equals("")) {
            noHpEditText.setError("Kolom ini wajib diisi");
            cutiPengajuanKonfirmasiView.setVisibility(View.GONE);
            cancel = true;
        }
        boolean allDate = true;
        if (TextUtils.isEmpty(tanggalMulai) || tanggalMulai.equals("")) {
            tanggalMulaiView.setError("Kolom ini wajib diisi");
            cutiPengajuanKonfirmasiView.setVisibility(View.GONE);
            allDate = false;
            cancel = true;
        }
        if (TextUtils.isEmpty(tanggalSelesai) || tanggalSelesai.equals("")) {
            tanggalSelesaiView.setError("Kolom ini wajib diisi");
            cutiPengajuanKonfirmasiView.setVisibility(View.GONE);
            allDate = false;
            cancel = true;
        }

        if (allDate) {
            cancel = validasiTanggalCuti(tanggalMulai, tanggalSelesai);
        }

        switch (kodeJenisCutiString) {
            case konfigurasi.CUTI_MELAHIRKAN:
                if (TextUtils.isEmpty(alasan) || alasan.equals("")) {
                    alasanCutiMelahirkanEditText.setError("Kolom ini wajib diisi");
                    cutiPengajuanKonfirmasiView.setVisibility(View.GONE);
                    cancel = true;
                }
                break;
            case konfigurasi.CUTI_SAKIT:
                break;
            default:
                if (TextUtils.isEmpty(alasan) || alasan.equals("")) {
                    if (TextUtils.isEmpty(alasan) || alasan.equals("")) {
                        alasanCutiEditText.setError("Kolom ini wajib diisi");
                        cutiPengajuanKonfirmasiView.setVisibility(View.GONE);
                        cancel = true;
                    }
                }
                break;
        }
        if (cancel) {
            konfigurasi.fadeAnimation(false, cutiPengajuanKonfirmasiView, konfigurasi.animationDurationShort);
            cutiPengajuanProgressView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "form pengajuan cuti belum lengkap", Toast.LENGTH_SHORT).show();
        } else {
            getData();
            postPengajuanCuti();
        }
    }

    private boolean validasiTanggalCuti(String tanggalMulai, String tanggalSelesai) {

        String tanggalMulaiHari;
        String tanggalMulaiBulan;
        String tanggalMulaiTahun;
        String tanggalSelesaiHari;
        String tanggalSelesaiBulan;
        String tanggalSelesaiTahun;

        tanggalMulaiHari = tanggalMulai.substring(8, 10);
        tanggalMulaiBulan = tanggalMulai.substring(5, 7);
        tanggalMulaiTahun = tanggalMulai.substring(0, 4);
        int tanggalMulaiInt = Integer.parseInt(tanggalMulaiTahun + tanggalMulaiBulan + tanggalMulaiHari);

        tanggalSelesaiHari = tanggalSelesai.substring(8, 10);
        tanggalSelesaiBulan = tanggalSelesai.substring(5, 7);
        tanggalSelesaiTahun = tanggalSelesai.substring(0, 4);
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
