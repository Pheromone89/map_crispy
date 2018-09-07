package id.go.bpkp.mobilemapbpkp.hotspot;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.SavedInstances;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;
import id.go.bpkp.mobilemapbpkp.monitoring.Jabatan;
import id.go.bpkp.mobilemapbpkp.monitoring.JabatanAdapter;
import pl.droidsonroids.gif.GifImageView;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;

/**
 * Created by ASUS on 11/02/2018.
 */

public class HotspotDetailFragment extends Fragment implements RecyclerViewClickListener {

    private String
            JSON_STRING;
    private String
            nama,
            datang,
            pulang,
            namanip,
            kodeUnit,
            tanggal,
            nipLama,
            nipBaru,
            ip,
            unit,
            acctId,
            foto;
    private String
            mUserToken,
            mTanggal,
            mNipLama;
    private String namaProfil, nipProfil, jamEfektif, fotoProfil;
    private View
            rootView;
    private TextView namaView, nipView, jamEfektifView;
    private RecyclerView
            hotspotDetailRecyclerView;
    private HotspotDetailAdapter
            hotspotDetailAdapter;
    private ArrayList<Hotspot>
            hotspotList;
    private ImageView fotoView;
    private GifImageView loadingProgressBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RelativeLayout dataView;
    private CardView detailListView;
    private CardView detailJamEfektifView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotspot_detail, null);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ngambil judul dan ngeset judul fragment
        setHasOptionsMenu(true);

        // set up setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //inisiasi rootView
        rootView = (View) view;

        //bundle dari fragment sebelumnya
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        mTanggal = this.getArguments().getString(PassedIntent.INTENT_TANGGAL);
        mUserToken = SavedInstances.userToken;

        hotspotList = new ArrayList<>();

        initiateView();
        getJSON();
    }

    private void initiateView() {
        dataView = rootView.findViewById(R.id.hotspot_detail);
        detailListView = rootView.findViewById(R.id.hotspot_detail_list_cardview);
        detailJamEfektifView = rootView.findViewById(R.id.hotspot_detail_jam_efektif_panel);
        dataView = rootView.findViewById(R.id.hotspot_detail);
        namaView = rootView.findViewById(R.id.dashboard_nama);
        nipView = rootView.findViewById(R.id.dashboard_nip);
        fotoView = rootView.findViewById(R.id.dashboard_profic);
        jamEfektifView = rootView.findViewById(R.id.jam_efektif_value);
        dataView.setVisibility(View.GONE);
        detailListView.setVisibility(View.GONE);
        detailJamEfektifView.setVisibility(View.GONE);

        loadingProgressBar = rootView.findViewById(R.id.list_progress_bar);
        hotspotDetailRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_hotspot_detail);
        hotspotDetailRecyclerView.setHasFixedSize(true);
        hotspotDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void populateView() {
        hotspotDetailAdapter = new HotspotDetailAdapter(getActivity(), hotspotList, this);
        namaView.setText(namaProfil);
        nipView.setText(nipProfil);
        jamEfektifView.setText(jamEfektif);
        Picasso.with(getActivity()).load(fotoProfil).into(fotoView);
        hotspotDetailRecyclerView.setAdapter(hotspotDetailAdapter);

        dataView.setVisibility(View.VISIBLE);
        detailListView.setVisibility(View.VISIBLE);
        detailJamEfektifView.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, hotspotDetailRecyclerView, konfigurasi.animationDurationShort);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String url = konfigurasi.URL_GET_HOTSPOTDETAIL + mNipLama + "/" + mTanggal + "?api_token=" + mUserToken;
//                String s = rh.sendGetRequest(konfigurasi.URL_GET_HOTSPOTDETAIL + mNipLama + "/" + mTanggal + "?api_token=" + mUserToken);
                String s = rh.sendGetRequest(url);
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
            JSONArray result = jsonObject.getJSONArray("result");
            namaProfil = jsonObject.getString("nama");
            nipProfil = jsonObject.getString("nipbaru");
            jamEfektif = jsonObject.getString("jam_efektif");
            fotoProfil = PassedIntent.getFoto(getActivity(), mNipLama);
            String tanggal = mTanggal;
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                nama = jo.getString(konfigurasi.TAG_NAMA);
                nipLama = jo.getString(konfigurasi.TAG_NIPLAMA);
                tanggal = jo.getString(konfigurasi.TAG_TANGGAL_HOTSPOT_PANJANG);
                datang = jo.getString(konfigurasi.TAG_DATANG);
                pulang = jo.getString(konfigurasi.TAG_PULANG);
                namanip = jo.getString(konfigurasi.TAG_NAMANIP);
                nipBaru = jo.getString(konfigurasi.TAG_NIPBARU);
                unit = jo.getString(konfigurasi.TAG_NAMAUNIT);
                kodeUnit = jo.getString(konfigurasi.TAG_KODE_UNIT);
                acctId = jo.getString(konfigurasi.TAG_ACCTID);
                ip = jo.getString(konfigurasi.TAG_IP);
                foto = PassedIntent.getFoto(getActivity(), nipLama);

                hotspotList.add(
                        new Hotspot(
                                i,
                                nipLama,
                                tanggal,
                                datang,
                                pulang,
                                nama,
                                namanip,
                                nipBaru,
                                unit,
                                kodeUnit,
                                acctId,
                                ip,
                                foto
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Terjadi kesalahan, silakan login kembali", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Detail Hotspot Error", Toast.LENGTH_SHORT).show();
            signOut();
        }
        populateView();
    }

    public void signOut() {
        getJSONSignout();
    }

    private void parseJSONSignout() {
        JSONObject jsonObject = null;
        Intent logoutIntent = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            String message = jsonObject.getString("success");
            if (message.equals("true")) {
                SharedPreferences prefs = getActivity().getSharedPreferences(Activity.class.getSimpleName(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(INTENT_NIPBARU);
                editor.apply();
                logoutIntent = new Intent(getActivity(), LoginActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logoutIntent);
                getActivity().finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(logoutIntent);
        getActivity().finish();
    }

    private void getJSONSignout() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), null, null, false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                parseJSONSignout();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_LOGOUT + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
