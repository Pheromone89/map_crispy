package id.go.bpkp.mobilemapbpkp.absen;

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

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;

/**
 * Created by ASUS on 11/02/2018.
 */

public class AbsenBawahanFragment extends Fragment implements RecyclerViewClickListener {

    private String
            JSON_STRING,
            jamDatang,
            jamPulang,
            jamEfektif,
            statusDatang,
            statusPulang,
            tanggalAbsen,
            hariAbsen;
    private String
            mNipBaru,
            mFoto,
            mUserToken,
            mNipLama,
            username;
    private View
            rootView;
    private RecyclerView
            dataAbsenBawahanRecyclerView;
    private AbsenBawahanAdapter
            absenBawahanAdapter;
    private ArrayList<AbsenBawahan>
            absenBawahanList;
    private ProgressBar loadingProgressBar;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_absen_bawahan, null);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ngambil judul dan ngeset judul fragment
        setHasOptionsMenu(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //inisiasi rootView
        rootView = (View) view;

        //bundle dari fragment sebelumnya
        //URL foto
        mFoto = this.getArguments().getString(PassedIntent.INTENT_FOTO);
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        //nip tanpa spasi
        username = this.getArguments().getString(PassedIntent.INTENT_USERNAME);
        //nip lama tanpa spasi
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);

        absenBawahanList = new ArrayList<>();

        initiateView();
        getJSON();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_data_absen);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        loadingProgressBar = rootView.findViewById(R.id.absen_bawahan_progress_bar);
        dataAbsenBawahanRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_data_absen_bawahan);
        dataAbsenBawahanRecyclerView.setHasFixedSize(true);
        dataAbsenBawahanRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void populateView() {
        absenBawahanAdapter = new AbsenBawahanAdapter(getActivity(), absenBawahanList, this);
        dataAbsenBawahanRecyclerView.setAdapter(absenBawahanAdapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingProgressBar.setVisibility(View.VISIBLE);
                dataAbsenBawahanRecyclerView.setVisibility(View.GONE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loadingProgressBar.setVisibility(View.GONE);
                konfigurasi.fadeAnimation(true, dataAbsenBawahanRecyclerView, 500);
//                dataAbsenBawahanRecyclerView.setVisibility(View.VISIBLE);
                JSON_STRING = s;
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ABSENBAWAHAN + mNipLama + "?api_token=" + mUserToken);
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
                    String nipLama = jo.getString(konfigurasi.TAG_NIPLAMA);
                    String nama = jo.getString(konfigurasi.TAG_NAMA);
                    String jamDatang = checkNull(jo.getString(konfigurasi.TAG_ABSEN_DATANG));
                    String jamPulang = checkNull(jo.getString(konfigurasi.TAG_ABSEN_PULANG));
                    String kodeDatang = jo.getString("kd_datang");
                    String kodePulang = jo.getString("kd_pulang");

                    absenBawahanList.add(
                            new AbsenBawahan(
                                    i,
                                    nipLama,
                                    nama,
                                    jamDatang,
                                    kodeDatang,
                                    jamPulang,
                                    kodePulang
                            )
                    );
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Terjadi kesalahan, silakan login kembali", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "KonfirmasiPenugasan Error", Toast.LENGTH_SHORT).show();
            PassingIntent.signOut(getActivity(), mUserToken, sharedPreferences);
        }
        populateView();
    }

//    public void signOut() {
//        getJSONSignout();
//    }
//
//    private void parseJSONSignout() {
//        JSONObject jsonObject = null;
//        Intent logoutIntent = null;
//        try {
//            jsonObject = new JSONObject(JSON_STRING);
//            String message = jsonObject.getString("success");
//            if (message.equals("true")) {
//                SharedPreferences prefs = getActivity().getSharedPreferences(Activity.class.getSimpleName(), Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.remove(INTENT_NIPBARU);
//                editor.apply();
//                logoutIntent = new Intent(getActivity(), LoginActivity.class);
//                logoutIntent.putExtra(INTENT_NIPBARU, mNipBaru);
//                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(logoutIntent);
//                getActivity().finish();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        startActivity(logoutIntent);
//        getActivity().finish();
//    }
//
//    private void getJSONSignout() {
//        class GetJSON extends AsyncTask<Void, Void, String> {
//
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(getActivity(), null, null, false, false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                JSON_STRING = s;
//                parseJSONSignout();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                RequestHandler rh = new RequestHandler();
//                String s = rh.sendGetRequest(konfigurasi.URL_LOGOUT + mUserToken);
//                return s;
//            }
//        }
//        GetJSON gj = new GetJSON();
//        gj.execute();
//    }

    private String checkNull(String string) {
        if (string.equals("null") || string.equals("NULL")) {
            return "-";
        } else {
            return string;
        }
    }
}