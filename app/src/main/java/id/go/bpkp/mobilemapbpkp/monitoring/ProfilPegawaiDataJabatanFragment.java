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
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;

/**
 * Created by ASUS on 11/02/2018.
 */

public class ProfilPegawaiDataJabatanFragment extends Fragment implements RecyclerViewClickListener {

    private String
            JSON_STRING,
            namaJabatan,
            tmtJabatan,
            nomorSKJabatan,
            tanggalSKJabatan;
    private String
            mFoto,
            username,
            mUserToken,
            mNipLama;
    private View
            rootView;
    private RecyclerView
            dataJabatanRecyclerView;
    private JabatanAdapter
            jabatanAdapter;
    private ArrayList<Jabatan>
            jabatanList;
    private ProgressBar loadingProgressBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_pegawai_data_jabatan, null);
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
        //URL foto
        mFoto = this.getArguments().getString("foto");
        //login token
        mUserToken = this.getArguments().getString("user_token");
        //nip tanpa spasi
        username = this.getArguments().getString("username");
        //nip lama tanpa spasi
        mNipLama = this.getArguments().getString("nip_lama");
        JSON_STRING = sharedPreferences.getString("saved_json_data_jabatan", null);

        jabatanList = new ArrayList<>();

        initiateView();
        if (JSON_STRING == null) {
            getJSON();
        } else {
            parseJSON();
        }
    }

    private void initiateView() {
        loadingProgressBar = rootView.findViewById(R.id.profil_individu_data_jabatan_progress_bar);
        dataJabatanRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_data_jabatan);
        dataJabatanRecyclerView.setHasFixedSize(true);
        dataJabatanRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void populateView() {
        jabatanAdapter = new JabatanAdapter(getActivity(), jabatanList, this);
        dataJabatanRecyclerView.setAdapter(jabatanAdapter);

        loadingProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, dataJabatanRecyclerView, konfigurasi.animationDurationShort);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loadingProgressBar.setVisibility(View.VISIBLE);
//                dataJabatanRecyclerView.setVisibility(View.GONE);
//                loading = ProgressDialog.show(getActivity(),"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                JSON_STRING = s;
                editor.putString("saved_json_data_jabatan", s);
                editor.apply();
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP_JABATAN + mNipLama + "?api_token=" + mUserToken);
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
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                namaJabatan = jo.getString("s_nmjabdetail");
                tmtJabatan = jo.getString("dari");
                nomorSKJabatan = jo.getString("s_no_sk");
                tanggalSKJabatan = jo.getString("d_tgl_sk");

                jabatanList.add(
                        new Jabatan(
                                i,
                                namaJabatan,
                                tmtJabatan,
                                nomorSKJabatan,
                                tanggalSKJabatan
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Terjadi kesalahan, silakan login kembali", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Jabatan Error", Toast.LENGTH_SHORT).show();
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
