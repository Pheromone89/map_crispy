package id.go.bpkp.mobilemapbpkp.izinkantor;

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
import android.widget.RelativeLayout;
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
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import pl.droidsonroids.gif.GifImageView;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTOURL;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ISATASAN;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NOHP;

/**
 * Created by ASUS on 09/02/2018.
 */

public class IzinKantorDashboardPegawaiFragment extends Fragment implements RecyclerViewClickListener {

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
            mFotoUrl,
            mNoHp,
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
            pengajuanIzinKantorButton,
            pengajuanGagalFpButton,
            daftarIzinKantorButton;
    private boolean
            tidakPunyaAtasanLangsung;
    private LinearLayout rootLayout;
    private RecyclerView izinKantorRecyclerView;
    private IzinKantorAdapter izinKantorAdapter;
    private List<IzinKantor>
            izinKantorList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CardView izinKantorCardView;
    private GifImageView rootProgressBar, persetujuanProgressBar;
    private LinearLayout messageNoTransaksi;
    private GifImageView listProgressBar;

    // persetujuan
    private boolean isAtasan;
    private CardView
            persetujuanIzinKantorButton;
    private RelativeLayout
            notifikasiIcon;
    // saved
    private TextView savedIndex, savedJson, notifikasi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_izin_kantor_dashboard_pegawai, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_dashboard_pegawai);

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
        mRoleIdInt = this.getArguments().getInt("role_id");
        // bool atasan
        isAtasan = this.getArguments().getBoolean(PassedIntent.INTENT_ISATASAN);
        // bool atasan
        tidakPunyaAtasanLangsung = this.getArguments().getBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG);
        // atasan langsung
        mAtasanLangsung = this.getArguments().getString(PassedIntent.INTENT_NAMAATASANLANGSUNG);
        // nip atasan langsung
        mNipAtasanLangsung = this.getArguments().getString(PassedIntent.INTENT_NIPATASANLANGSUNG);

        mFotoUrl = this.getArguments().getString(INTENT_FOTOURL);
        mNoHp = this.getArguments().getString(INTENT_NOHP);
        isAtasan = this.getArguments().getBoolean(INTENT_ISATASAN);

        izinKantorList = new ArrayList<>();

        initiateView();
        populateView();

        initiateViewListIzinKantor();
        getJSONListIzin();

        // harus terakhir
        initiateSetOnClickMethod();
        if (isAtasan) {
            persetujuanIzinKantorButton.setVisibility(View.VISIBLE);
            getJSONDaftarBawahanLangsung();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_dashboard_pegawai);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        rootLayout = rootView.findViewById(R.id.izin_kantor_pegawai_dashboard);
        rootProgressBar = rootView.findViewById(R.id.izin_kantor_pegawai_progress_bar);
        rootProgressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);
        // profic
        proficView = (ImageView) rootView.findViewById(R.id.dashboard_profic);
        namaView = (TextView) rootView.findViewById(R.id.dashboard_nama);
        nipView = (TextView) rootView.findViewById(R.id.dashboard_nip);
        //  button
        daftarIzinKantorButton = (CardView) rootView.findViewById(R.id.dashboard_izin_kantor_daftar_izin_kantor_button);
        pengajuanIzinKantorButton = (CardView) rootView.findViewById(R.id.dashboard_izin_kantor_pengajuan_izin_kantor_button);
        pengajuanGagalFpButton = (CardView) rootView.findViewById(R.id.dashboard_izin_kantor_pengajuan_gagal_fp_button);
        messageNoTransaksi = rootView.findViewById(R.id.message_tidak_ada_izin_kantor);
        listProgressBar = rootView.findViewById(R.id.list_progress_bar);

        // persetujuan
        persetujuanIzinKantorButton = rootView.findViewById(R.id.dashboard_izin_kantor_persetujuan_izin_kantor_button);
        persetujuanProgressBar = rootView.findViewById(R.id.izin_kantor_pegawai_dashboard_persetujuan_progress_bar);
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
        daftarIzinKantorButton.setOnClickListener(new View.OnClickListener() {
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
        pengajuanIzinKantorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSONAtasanLangsung(0);
            }
        });
        pengajuanGagalFpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSONAtasanLangsung(1);
            }
        });
        persetujuanIzinKantorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);

                IzinKantorDaftarPersetujuanFragment izinKantorDaftarPersetujuanFragment = new IzinKantorDaftarPersetujuanFragment();
                izinKantorDaftarPersetujuanFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, izinKantorDaftarPersetujuanFragment);
                fragmentTransaction.addToBackStack("fragment_dashboard_izin_kantor");
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
            bundle.putBoolean(INTENT_ISATASAN, isAtasan);
            bundle.putString(INTENT_FOTOURL, mFotoUrl);
            bundle.putString(INTENT_NOHP, mNoHp);
            bundle.putInt("role_id", mRoleIdInt);
            bundle.putString(PassedIntent.INTENT_FOTO, mFoto);
            bundle.putString(PassedIntent.INTENT_NAMAATASANLANGSUNG, mAtasanLangsung);
            bundle.putString(PassedIntent.INTENT_NIPATASANLANGSUNG, mNipAtasanLangsung);
            bundle.putBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, tidakPunyaAtasanLangsung);

            Fragment izinFragment = null;
            switch (kodeIzin) {
                case 0:
                    izinFragment = new IzinKantorPengajuanPegawaiFragment();
                    break;
                case 1:
                    izinFragment = new GagalFpPengajuanPegawaiFragment();
                    break;
            }
            izinFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content_fragment_area, izinFragment);
            fragmentTransaction.addToBackStack("fragment_dashboard_izin_kantor");
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

    // list izin kantor

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    private void initiateViewListIzinKantor() {
        izinKantorCardView = rootView.findViewById(R.id.izin_kantor_list_cardview);
        izinKantorCardView.setVisibility(View.GONE);
        izinKantorRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_izin_kantor);
        izinKantorRecyclerView.setHasFixedSize(true);
        izinKantorRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void populateViewListIzin() {
        izinKantorAdapter = new IzinKantorAdapter(getActivity(), izinKantorList, this);
        izinKantorRecyclerView.setAdapter(izinKantorAdapter);
        izinKantorRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });

        listProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, izinKantorCardView, konfigurasi.animationDurationShort);
        if (izinKantorList.size() == 0) {
            konfigurasi.fadeAnimation(true, messageNoTransaksi, konfigurasi.animationDurationShort);
        }
    }

    private void parseJSONListIzin() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {
                JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String jenisIzin = jo.getString("jenis_izin");
                    String tanggalMulai = jo.getString("tanggal_awal");
                    String tanggalSelesai = jo.getString("tanggal_akhir");
                    String kodeProses = jo.getString("kd_proses");
                    String namaProses = jo.getString("nama_proses");
                    String catatan = jo.getString("keterangan");

                    izinKantorList.add(
                            new IzinKantor(
                                    i,
                                    jenisIzin,
                                    tanggalMulai,
                                    tanggalSelesai,
                                    catatan,
                                    kodeProses,
                                    namaProses
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
        populateViewListIzin();
    }

    private void getJSONListIzin() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                izinKantorCardView.setVisibility(View.GONE);
                izinKantorRecyclerView.setVisibility(View.GONE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                izinKantorCardView.setVisibility(View.VISIBLE);
                izinKantorRecyclerView.setVisibility(View.VISIBLE);
                JSON_STRING = s;
                parseJSONListIzin();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DAFTARIZINKANTOR + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    // method terkait persetujuan

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
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSONDaftarBawahanLangsung();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP_IZINKANTORBAWAHANLANGSUNGCOUNT + mNipLama + "?api_token=" + mUserToken);

                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void setJSONDaftarBawahanLangsung() {
        notifikasiIcon = rootView.findViewById(R.id.izin_kantor_pegawai_notifikasi);
        notifikasiIcon.setVisibility(View.VISIBLE);
        notifikasi = rootView.findViewById(R.id.izin_kantor_pegawai_jumlah_notifikasi);
        notifikasi.setText(JSON_index);
    }

}