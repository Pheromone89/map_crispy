package id.go.bpkp.mobilemapbpkp.cuti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 09/02/2018.
 */

public class CutiDashboardPegawaiFragment extends Fragment {

    private View
            rootView;
    private String
            JSON_STRING,
            JSON_index,
            JSON_BawahanLangsung,
            mUserToken,
            mNipLama,
            mNipBaru,
            mNama,
            mFoto,
            mAtasanLangsung,
            mNipAtasanLangsung,
            tahunLabel,
            tahunMin2Label,
            tahunMin1Label,
            hakCutiTMin2,
            hakCutiTMin1,
            hakCutiT,
            jumlahHakCuti,
            cutiTerpakai,
            saldoCuti,
            jabatan;
    private int
            mRoleIdInt,
            tahunBerjalan,
            tahunMin2,
            tahunMin1;
    private ImageView
            proficView;
    private TextView
            namaView,
            nipView,
            jabatanView,
            cutiTLabelView,
            cutiT2LabelView,
            cutiT1LabelView,
            hakCutiTMin2View,
            hakCutiTMin1View,
            hakCutiTView,
            jumlahHakCutiView,
            cutiTerpakaiView,
            saldoCutiView;
    private CardView
            pengajuanCutiButton,
            daftarCutiButton;
    private boolean
            tidakPunyaAtasanLangsung, isAtasan;
    private YoYo.YoYoString ropeCutiDashboard;
    private LinearLayout rootLayout;
    private ProgressBar rootProgressBar, pengajuanProgressBar, persetujuanProgressBar;
    private TextView pengajuanLabel;
    // persetujuan
    private CardView
            persetujuanCutiButton;
    private RelativeLayout
            notifikasiIcon;
    // saved
    private TextView savedIndex, savedJson, notifikasi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuti_dashboard_pegawai, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_dashboard_pegawai);

        //bundle dari fragment sebelumnya
        //URL foto
        mFoto = this.getArguments().getString(PassedIntent.INTENT_FOTO);
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        //nip lama UNTUK PEGAWAI, BUKAN ADMIN
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        //nip baru
        mNipBaru = this.getArguments().getString(PassedIntent.INTENT_NIPBARU);
        //nama
        mNama = this.getArguments().getString(PassedIntent.INTENT_NAMA);
        //role id
//        mRoleIdInt = this.getArguments().getInt("role_id");
        // bool atasan
        isAtasan = this.getArguments().getBoolean(PassedIntent.INTENT_ISATASAN);
        tidakPunyaAtasanLangsung = this.getArguments().getBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG);
        // atasan langsung
        mAtasanLangsung = this.getArguments().getString(PassedIntent.INTENT_NAMAATASANLANGSUNG);
        // nip atasan langsung
        mNipAtasanLangsung = this.getArguments().getString(PassedIntent.INTENT_NIPATASANLANGSUNG);


        tahunBerjalan = Calendar.getInstance().get(Calendar.YEAR);
        tahunMin2 = tahunBerjalan - 2;
        tahunMin2Label = "" + tahunMin2;
        tahunMin1 = tahunBerjalan - 1;
        tahunMin1Label = "" + tahunMin1;
        tahunLabel = "" + tahunBerjalan;

        initiateView();

        // harus terakhir
        getJSONDashboardCuti();
        initiateSetOnClickMethod();
        if (isAtasan) {
            persetujuanCutiButton.setVisibility(View.VISIBLE);
            getJSONDaftarBawahanLangsung();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_dashboard_pegawai);
        super.onCreateOptionsMenu(menu, inflater);
        if (isAtasan) {
            getJSONDaftarBawahanLangsung();
        }
    }

    private void initiateView() {
        // root
        rootLayout = rootView.findViewById(R.id.cuti_pegawai_dashboard);
        rootProgressBar = rootView.findViewById(R.id.cuti_pegawai_progress_bar);
        rootLayout.setVisibility(View.GONE);
        rootProgressBar.setVisibility(View.VISIBLE);
        // profic
        proficView = (ImageView) rootView.findViewById(R.id.dashboard_cuti_profic);
        namaView = (TextView) rootView.findViewById(R.id.dashboard_cuti_nama);
        nipView = (TextView) rootView.findViewById(R.id.dashboard_cuti_nip);
        jabatanView = (TextView) rootView.findViewById(R.id.dashboard_cuti_jabatan);
        // data
        cutiTLabelView = (TextView) rootView.findViewById(R.id.dashboard_cuti_t_label);
        cutiT2LabelView = (TextView) rootView.findViewById(R.id.dashboard_cuti_t_2_label);
        cutiT1LabelView = (TextView) rootView.findViewById(R.id.dashboard_cuti_t_1_label);
        hakCutiTMin2View = (TextView) rootView.findViewById(R.id.dashboard_cuti_t_2_val);
        hakCutiTMin1View = (TextView) rootView.findViewById(R.id.dashboard_cuti_t_1_val);
        hakCutiTView = (TextView) rootView.findViewById(R.id.dashboard_cuti_t_val);
        jumlahHakCutiView = (TextView) rootView.findViewById(R.id.dashboard_cuti_jumlah_val);
        cutiTerpakaiView = (TextView) rootView.findViewById(R.id.dashboard_cuti_terpakai_val);
        saldoCutiView = (TextView) rootView.findViewById(R.id.dashboard_cuti_sisa_val);
        //  button
        daftarCutiButton = (CardView) rootView.findViewById(R.id.dashboard_cuti_daftar_cuti_button);
        pengajuanCutiButton = (CardView) rootView.findViewById(R.id.dashboard_cuti_pengajuan_cuti_button);
        pengajuanProgressBar = rootView.findViewById(R.id.cuti_pegawai_dashboard_pengajuan_progress_bar);
        pengajuanLabel = rootLayout.findViewById(R.id.cuti_pegawai_dashboard_pengajuan_label);
        // persetujuan
        persetujuanCutiButton = rootView.findViewById(R.id.dashboard_cuti_persetujuan_cuti_button);
        persetujuanProgressBar = rootView.findViewById(R.id.cuti_pegawai_dashboard_persetujuan_progress_bar);
    }

    private void populateView() {
        // profic
        Picasso
                .with(getActivity())
                .load(mFoto)
                .into(proficView);
        namaView.setText(mNama);
        nipView.setText(mNipBaru);
        jabatanView.setText(jabatan);
        // data
        cutiTLabelView.setText(tahunLabel);
        cutiT2LabelView.setText(tahunMin2Label);
        cutiT1LabelView.setText(tahunMin1Label);
        hakCutiTMin2 = hakCutiTMin2 + " hari";
        hakCutiTMin1 = hakCutiTMin1 + " hari";
        hakCutiT = hakCutiT + " hari";
        jumlahHakCuti = jumlahHakCuti + " hari";
        cutiTerpakai = cutiTerpakai + " hari";
        saldoCuti = saldoCuti + " hari";
        hakCutiTMin2View.setText(hakCutiTMin2);
        hakCutiTMin1View.setText(hakCutiTMin1);
        hakCutiTView.setText(hakCutiT);
        jumlahHakCutiView.setText(jumlahHakCuti);
        cutiTerpakaiView.setText(cutiTerpakai);
        saldoCutiView.setText(saldoCuti);

        rootLayout.setVisibility(View.VISIBLE);
        rootProgressBar.setVisibility(View.GONE);
        ropeCutiDashboard = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(rootLayout);
    }

    private void initiateSetOnClickMethod() {
        daftarCutiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);
//                bundle.putInt("role_id", mRoleIdInt);

                CutiDaftarCutiFragment cutiDaftarCutiFragment = new CutiDaftarCutiFragment();
                cutiDaftarCutiFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, cutiDaftarCutiFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        pengajuanCutiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSONAtasanLangsung();
            }
        });
        persetujuanCutiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);

                CutiDaftarPersetujuanFragment cutiDaftarPersetujuanFragment = new CutiDaftarPersetujuanFragment();
                cutiDaftarPersetujuanFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, cutiDaftarPersetujuanFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void parseJSONAtasanLangsung() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("result").equals("Data atasan tidak ditemukan.")) {
                tidakPunyaAtasanLangsung = true;
            } else {
                JSONObject result = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
                mAtasanLangsung = result.getString("nama_lengkap");
                mNipAtasanLangsung = result.getString("s_nip");
                tidakPunyaAtasanLangsung = false;
            }

            Bundle bundle = new Bundle();
            bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
            bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);
            bundle.putString(PassedIntent.INTENT_NIPBARU, mNipBaru);
            bundle.putString(PassedIntent.INTENT_NAMA, mNama);
//            bundle.putInt("role_id", mRoleIdInt);
            bundle.putString(PassedIntent.INTENT_FOTO, mFoto);
            bundle.putString(PassedIntent.INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
            bundle.putString(PassedIntent.INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
            bundle.putBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);
            bundle.putString("saldo_cuti", saldoCuti);

            CutiPengajuanPegawaiFragment cutiPengajuanPegawaiFragment = new CutiPengajuanPegawaiFragment();
            cutiPengajuanPegawaiFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content_fragment_area, cutiPengajuanPegawaiFragment);
            fragmentTransaction.addToBackStack("fragment_dashboard_cuti");
            fragmentTransaction.commit();
            pengajuanLabel.setVisibility(View.VISIBLE);
            pengajuanProgressBar.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "JSON exception", Toast.LENGTH_SHORT).show();
            pengajuanLabel.setVisibility(View.VISIBLE);
            pengajuanProgressBar.setVisibility(View.GONE);
        }
    }

    private void getJSONAtasanLangsung() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pengajuanLabel.setVisibility(View.INVISIBLE);
                pengajuanProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
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

    private void getJSONDashboardCuti() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONDashboardCuti();
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

    private void parseJSONDashboardCuti() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            jsonObject = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
            // jabatan
            jabatan = checkNull(jsonObject.getJSONObject(konfigurasi.TAG_CUTI_PEGAWAI).getString(konfigurasi.TAG_JABATANSINGKAT));
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
        populateView();
    }

    private String checkNull(String string) {
        if (string.equals("null")) {
            return "0";
        } else {
            return string;
        }
    }

    private void parseJSONDaftarBawahanLangsung() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            String result = jsonObject.getString(konfigurasi.TAG_JSON_ARRAY);
            JSON_index = result;
            if (!jsonObject.getString("success").equals("true")) {
                JSON_index = "" + 0;
            }
            setJSONDaftarBawahanLangsung();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "JSON exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void getJSONDaftarBawahanLangsung() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                rootProgressBar.setVisibility(View.VISIBLE);
//                rootLayout.setVisibility(View.GONE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                rootProgressBar.setVisibility(View.GONE);
//                rootLayout.setVisibility(View.VISIBLE);
                JSON_STRING = s;
                parseJSONDaftarBawahanLangsung();
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

    private void setJSONDaftarBawahanLangsung() {
        notifikasiIcon = rootView.findViewById(R.id.cuti_pegawai_notifikasi);
        notifikasiIcon.setVisibility(View.VISIBLE);
        notifikasi = rootView.findViewById(R.id.cuti_pegawai_jumlah_notifikasi);
        notifikasi.setText(JSON_index);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}