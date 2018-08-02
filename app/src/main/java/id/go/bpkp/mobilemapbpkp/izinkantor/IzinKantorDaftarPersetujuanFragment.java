package id.go.bpkp.mobilemapbpkp.izinkantor;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.cuti.BawahanLangsungCuti;
import id.go.bpkp.mobilemapbpkp.cuti.BawahanLangsungCutiAdapter;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 15/02/2018.
 */

public class IzinKantorDaftarPersetujuanFragment extends Fragment implements RecyclerViewClickListener {

    private String
            JSON_STRING;
    private View
            rootView;
    private String
            mUserToken,
            mUrl,
            mNipLama;
    private int
            mRoleId;
    // buat recyclerView
    private String
            nama,
            nipLama,
            nipBaru,
            nipBaruPisah,
            jabatanSingkat,
            unit,
            foto;
    private RecyclerView
            pegawaiBawahanLangsungRecyclerView;
    private BawahanLangsungIzinKantorAdapter
            pegawaiBawahanLangsungAdapter;
    private List<BawahanLangsungIzinKantor>
            pegawaiBawahanLangsungList;
    private ProgressBar loadingProgressBar;

    public IzinKantorDaftarPersetujuanFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_izin_kantor_bawahan_langsung, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        rootView = view;

        pegawaiBawahanLangsungList = new ArrayList<>();

        initiateView();
        getJSON();

//        previousTitle = ((AppCompatActivity)getActivity()).getSupportActionBar().getTitle().toString();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_persetujuan);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_persetujuan);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_persetujuan);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    private void initiateView() {
        loadingProgressBar = rootView.findViewById(R.id.izin_kantor_bawahan_langsung_progress_bar);
        pegawaiBawahanLangsungRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_izin_kantor_bawahan_langsung);
        pegawaiBawahanLangsungRecyclerView.setHasFixedSize(true);
        pegawaiBawahanLangsungRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void populateView() {
        pegawaiBawahanLangsungAdapter = new BawahanLangsungIzinKantorAdapter(getActivity(), pegawaiBawahanLangsungList, this, mNipLama, mUserToken);
        pegawaiBawahanLangsungRecyclerView.setAdapter(pegawaiBawahanLangsungAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void parseJSON() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String idTransaksi = jo.getString("id_transaksi");
                String nipLama = jo.getString(konfigurasi.TAG_NIPLAMA);
                String nama = jo.getString(konfigurasi.TAG_NAMA);
                String jenisCuti = jo.getString("jenis_cuti");
                String tanggalPengajuan = jo.getString("tanggal_pengajuan");
                String tanggalAwal = jo.getString("tanggal_awal");
                String tanggalAkhir = jo.getString("tanggal_akhir");
                String jumlahHari = jo.getString("jumlah_hari");
                String alasan = jo.getString("alasan");
                String alamat = jo.getString("alamat");
                String catatan = jo.getString("catatan");
                String pemrosesSebelumnya = jo.getString("pemroses_sebelumnya");
                boolean isFinal = jo.getString("is_final").equals("true");

                pegawaiBawahanLangsungList.add(
                        new BawahanLangsungIzinKantor(
                                i,
                                idTransaksi,
                                nipLama,
                                nama,
                                jenisCuti,
                                tanggalPengajuan,
                                tanggalAwal,
                                tanggalAkhir,
                                jumlahHari,
                                alasan,
                                alamat,
                                catatan,
                                pemrosesSebelumnya,
                                isFinal
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                jsonObject = new JSONObject(JSON_STRING);
                String message = jsonObject.getString("success");
                if (message.equals("false")) {
                    Snackbar.make(rootView, "Tidak ada pengajuan dalam proses", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                    getActivity().getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
                Toast.makeText(getActivity(), "JSON exception", Toast.LENGTH_SHORT).show();
            }
        }
        populateView();
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingProgressBar.setVisibility(View.VISIBLE);
                pegawaiBawahanLangsungRecyclerView.setVisibility(View.GONE);
//                loading = ProgressDialog.show(getActivity(),null,null,false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loadingProgressBar.setVisibility(View.GONE);
                pegawaiBawahanLangsungRecyclerView.setVisibility(View.VISIBLE);
//                loading.dismiss();
                JSON_STRING = s;
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP_IZINKANTORBAWAHANLANGSUNG + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
