package id.go.bpkp.mobilemapbpkp.konfirmasipenugasan;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.izinkantor.IzinKantorDaftarIzinKantorFragment;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.monitoring.PegawaiSingkat;
import id.go.bpkp.mobilemapbpkp.monitoring.PegawaiSingkatAdapter;

/**
 * Created by ASUS on 09/02/2018.
 */

public class KonfirmasiPenugasanDashboardPegawaiFragment extends Fragment implements RecyclerViewClickListener {

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
            mNipAtasanLangsung;
    private int
            mRoleIdInt;
    private ImageView
            proficView;
    private TextView
            namaView,
            nipView;
    private CardView
            pengajuanKonfirmasiPenugasanButton,
            pengajuanGagalFpButton,
            daftarKonfirmasiPenugasanButton;
    private boolean
            tidakPunyaAtasanLangsung;
    private LinearLayout rootLayout;
    private RecyclerView konfirmasiPenugasanRecyclerView;
    private KonfirmasiPenugasanAdapter konfirmasiPenugasanAdapter;
    private List<KonfirmasiPenugasan>
            konfirmasiPenugasanList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CardView konfirmasiPenugasanCardView;
    private ProgressBar rootProgressBar;
    private LinearLayout messageNoTransaksi;
    private ProgressBar listProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_konfirmasi_penugasan_dashboard_pegawai, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_konfirmasi_penugasan);

        // setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

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

        initiateView();
        populateView();

        konfirmasiPenugasanList = new ArrayList<>();
        initiateViewListPenugasan();
        getJSONListPenugasan();

        // harus terakhir
        initiateSetOnClickMethod();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_dashboard_pegawai);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        rootLayout = rootView.findViewById(R.id.konfirmasi_penugasan_pegawai_dashboard);
        rootProgressBar = rootView.findViewById(R.id.konfirmasi_penugasan_pegawai_progress_bar);
        rootLayout.setVisibility(View.GONE);
        rootProgressBar.setVisibility(View.VISIBLE);
        // profic
        proficView = (ImageView) rootView.findViewById(R.id.dashboard_profic);
        namaView = (TextView) rootView.findViewById(R.id.dashboard_nama);
        nipView = (TextView) rootView.findViewById(R.id.dashboard_nip);
        //  button
        daftarKonfirmasiPenugasanButton = (CardView) rootView.findViewById(R.id.dashboard_konfirmasi_penugasan_daftar_konfirmasi_penugasan_button);
        pengajuanKonfirmasiPenugasanButton = (CardView) rootView.findViewById(R.id.dashboard_konfirmasi_penugasan_pengajuan_konfirmasi_penugasan_button);
        messageNoTransaksi = rootView.findViewById(R.id.message_tidak_ada_konfirmasi_penugasan);
        listProgressBar = rootView.findViewById(R.id.list_progress_bar);
    }

    private void populateView() {
        // profic
        Picasso.with(getActivity()).load(mFoto).into(proficView);
        namaView.setText(mNama);
        nipView.setText(mNipBaru);

        rootProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, rootLayout, konfigurasi.animationDurationShort);
    }

    private void initiateSetOnClickMethod() {
        daftarKonfirmasiPenugasanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);
//                bundle.putInt("role_id", mRoleIdInt);

                IzinKantorDaftarIzinKantorFragment izinKantorDaftarIzinKantorFragment = new IzinKantorDaftarIzinKantorFragment();
                izinKantorDaftarIzinKantorFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, izinKantorDaftarIzinKantorFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        pengajuanKonfirmasiPenugasanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // validasi atasan langsung dinonaktifkan dl
//                getJSONAtasanLangsung(0);

                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);
                bundle.putString(PassedIntent.INTENT_NIPBARU, mNipBaru);
                bundle.putString(PassedIntent.INTENT_NAMA, mNama);
                bundle.putString(PassedIntent.INTENT_FOTO, mFoto);
                bundle.putString(PassedIntent.INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
                bundle.putString(PassedIntent.INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
                bundle.putBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);

                KonfirmasiPenugasanPengajuanPegawaiFragment konfirmasiPenugasanPengajuanPegawaiFragment = new KonfirmasiPenugasanPengajuanPegawaiFragment();
                konfirmasiPenugasanPengajuanPegawaiFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, konfirmasiPenugasanPengajuanPegawaiFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void parseJSONAtasanLangsung(int kodeIzin) {
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
            bundle.putString(PassedIntent.INTENT_FOTO, mFoto);
            bundle.putString(PassedIntent.INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
            bundle.putString(PassedIntent.INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
            bundle.putBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);

            KonfirmasiPenugasanPengajuanPegawaiFragment konfirmasiPenugasanPengajuanPegawaiFragment = new KonfirmasiPenugasanPengajuanPegawaiFragment();
            konfirmasiPenugasanPengajuanPegawaiFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content_fragment_area, konfirmasiPenugasanPengajuanPegawaiFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "JSON exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void getJSONAtasanLangsung(final int kodeIzin) {
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
                parseJSONAtasanLangsung(kodeIzin);
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

    private String checkNull(String string) {
        if (string.equals("null")) {
            return "0";
        } else {
            return string;
        }
    }

    // list konfirmasi penugasan

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    private void initiateViewListPenugasan() {
        konfirmasiPenugasanCardView = rootView.findViewById(R.id.konfirmasi_penugasan_list_cardview);
        konfirmasiPenugasanCardView.setVisibility(View.GONE);
        konfirmasiPenugasanRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_konfirmasi_penugasan);
        konfirmasiPenugasanRecyclerView.setHasFixedSize(true);
        konfirmasiPenugasanRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        loadingProgressBar = rootView.findViewById(R.id.seluruh_pegawai_progress_bar);
    }

    private void populateViewListPenugasan() {
        konfirmasiPenugasanAdapter = new KonfirmasiPenugasanAdapter(getActivity(), konfirmasiPenugasanList, this);
        konfirmasiPenugasanRecyclerView.setAdapter(konfirmasiPenugasanAdapter);
        konfirmasiPenugasanRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });

        listProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, konfirmasiPenugasanCardView, konfigurasi.animationDurationShort);
        if (konfirmasiPenugasanList.size() == 0) {
            konfigurasi.fadeAnimation(true, messageNoTransaksi, konfigurasi.animationDurationShort);
        }
    }

    private void parseJSONListPenugasan() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {
                JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String jenisPenugasan = jo.getString("jenis_penugasan");
                    String tanggalMulai = jo.getString("tanggal_mulai");
                    String tanggalSelesai = jo.getString("tanggal_selesai");
                    String catatan = jo.getString("catatan");

                    konfirmasiPenugasanList.add(
                            new KonfirmasiPenugasan(
                                    i,
                                    jenisPenugasan,
                                    tanggalMulai,
                                    tanggalSelesai,
                                    catatan
                            )
                    );
                }
            } else {
//                Toast.makeText(getActivity(), jsonObject.getString(konfigurasi.TAG_JSON_ARRAY), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Terjadi kesalahan, silakan login kembali", Toast.LENGTH_SHORT).show();
            PassingIntent.signOut(getActivity(), mUserToken, sharedPreferences);
        }
        populateViewListPenugasan();
    }

    private void getJSONListPenugasan() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                konfirmasiPenugasanCardView.setVisibility(View.GONE);
                konfirmasiPenugasanRecyclerView.setVisibility(View.GONE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                konfirmasiPenugasanCardView.setVisibility(View.VISIBLE);
                konfirmasiPenugasanRecyclerView.setVisibility(View.VISIBLE);
                JSON_STRING = s;
                parseJSONListPenugasan();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DAFTARKONFIRMASIPENUGASAN + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}