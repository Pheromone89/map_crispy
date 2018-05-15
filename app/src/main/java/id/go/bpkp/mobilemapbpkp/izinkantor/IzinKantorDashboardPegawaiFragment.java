package id.go.bpkp.mobilemapbpkp.izinkantor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class IzinKantorDashboardPegawaiFragment extends Fragment {

    private View
            rootView;
    private String
            JSON_STRING,
            mUserToken,
            mNipLama,
            mNipBaru,
            mNama,
            mFoto,
            mAtasanLangsung,
            mNipAtasanLangsung,
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
            tidakPunyaAtasanLangsung;

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
        tidakPunyaAtasanLangsung = this.getArguments().getBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG);
        // atasan langsung
        mAtasanLangsung = this.getArguments().getString(PassedIntent.INTENT_NAMAATASANLANGSUNG);
        // nip atasan langsung
        mNipAtasanLangsung = this.getArguments().getString(PassedIntent.INTENT_NIPATASANLANGSUNG);


        tahunBerjalan = Calendar.getInstance().get(Calendar.YEAR);
        tahunMin2 = tahunBerjalan - 2;
        tahunMin2Label = "Hak Cuti Tahun Lalu (" + tahunMin2 + ")";
        tahunMin1 = tahunBerjalan - 1;
        tahunMin1Label = "Hak Cuti Tahun Lalu (" + tahunMin1 + ")";

        initiateView();

        // harus terakhir
        getJSONDashboardCuti();
        initiateSetOnClickMethod();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_dashboard_pegawai);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        // profic
        proficView = (ImageView) rootView.findViewById(R.id.dashboard_cuti_profic);
        namaView = (TextView) rootView.findViewById(R.id.dashboard_cuti_nama);
        nipView = (TextView) rootView.findViewById(R.id.dashboard_cuti_nip);
        jabatanView = (TextView) rootView.findViewById(R.id.dashboard_cuti_jabatan);
        // data
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
    }

    private void initiateSetOnClickMethod() {
        daftarCutiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);
//                bundle.putInt("role_id", mRoleIdInt);

                IzinKantorDaftarIzinKantorFragment cutiDaftarCutiFragment = new IzinKantorDaftarIzinKantorFragment();
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

            IzinKantorPengajuanPegawaiFragment cutiPengajuanPegawaiFragment = new IzinKantorPengajuanPegawaiFragment();
            cutiPengajuanPegawaiFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content_fragment_area, cutiPengajuanPegawaiFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "JSON exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void getJSONAtasanLangsung() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), null, "mohon tunggu", false, false);
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

    private void getJSONDashboardCuti() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), null, "mohon tunggu", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
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
}
