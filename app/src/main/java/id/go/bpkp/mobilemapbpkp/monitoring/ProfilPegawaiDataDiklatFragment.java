package id.go.bpkp.mobilemapbpkp.monitoring;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.dashboard.DashboardActivity;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.SavedInstances;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;
import pl.droidsonroids.gif.GifImageView;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;

/**
 * Created by ASUS on 11/02/2018.
 */

public class ProfilPegawaiDataDiklatFragment extends Fragment implements RecyclerViewClickListener {

    private String
            JSON_STRING,
            kodeJenisDiklat,
            tanggalSertifikat,
            nomorSertifikat,
            kompetensi;
    private String
            mUserToken,
            mNipLama;
    private View
            rootView;
    private RecyclerView
            dataDiklatRecyclerView;
    private DiklatAdapter
            diklatAdapter;
    private ArrayList<Diklat>
            diklatList;
    private GifImageView loadingProgressBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LinearLayout dataView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_pegawai_data_diklat, null);
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
        editor = sharedPreferences.edit();

        //inisiasi rootView
        rootView = (View) view;

        //bundle dari fragment sebelumnya
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        mUserToken = SavedInstances.userToken;
        JSON_STRING = sharedPreferences.getString("saved_json_data_diklat", null);

        diklatList = new ArrayList<>();

        initiateView();
        if (JSON_STRING == null) {
            getJSON();
        } else {
            parseJSON();
        }
    }

    private void initiateView() {
        dataView = rootView.findViewById(R.id.profil_pegawai_data_diklat);
        dataView.setVisibility(View.GONE);

        loadingProgressBar = rootView.findViewById(R.id.profil_individu_data_diklat_progress_bar);
        dataDiklatRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_data_diklat);
        dataDiklatRecyclerView.setHasFixedSize(true);
        dataDiklatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void populateView() {
        diklatAdapter = new DiklatAdapter(getActivity(), diklatList, this);
        dataDiklatRecyclerView.setAdapter(diklatAdapter);

        dataView.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, dataDiklatRecyclerView, konfigurasi.animationDurationShort);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                editor.putString("saved_json_data_diklat", s);
                editor.apply();
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP_DIKLAT + mNipLama + "?api_token=" + mUserToken);
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
            if (jsonObject.getString("success").equals("true")) {
                JSONArray result = jsonObject.getJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    kodeJenisDiklat = jo.getString(konfigurasi.TAG_DIKLAT_KODEJENISDIKLAT);
                    tanggalSertifikat = jo.getString(konfigurasi.TAG_DIKLAT_TANGGALSERTIFIKAT);
                    nomorSertifikat = jo.getString(konfigurasi.TAG_DIKLAT_NOMORSERTIFIKAT);
                    kompetensi = jo.getString(konfigurasi.TAG_DIKLAT_KOMPETENSI);

                    diklatList.add(
                            new Diklat(
                                    i,
                                    kodeJenisDiklat,
                                    nomorSertifikat,
                                    tanggalSertifikat,
                                    kompetensi
                            )
                    );
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Terjadi kesalahan, silakan login kembali", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Diklat Error", Toast.LENGTH_SHORT).show();
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