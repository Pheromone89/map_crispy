package id.go.bpkp.mobilemapbpkp.hotspot;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.SavedInstances;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;
import id.go.bpkp.mobilemapbpkp.monitoring.PegawaiSingkat;
import id.go.bpkp.mobilemapbpkp.monitoring.PegawaiSingkatAdapter;
import pl.droidsonroids.gif.GifImageView;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;

/**
 * Created by ASUS on 22/01/2018.
 */

public class HotspotFragment extends Fragment implements RecyclerViewClickListener {

    private String
            JSON_STRING;
    private View
            rootView;
    private String
            mUserToken,
            mNipLama,
            previousTitle;
    private int
            mRoleId;
    // buat recyclerView
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
    private RecyclerView
            hotspotRecyclerView;
    private HotspotAdapter
            hotspotAdapter;
    private List<Hotspot>
            hotspotList;
    private GifImageView loadingProgressBar;

    public HotspotFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotspot, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mUserToken = SavedInstances.userToken;
        mNipLama = SavedInstances.nipLama;
        rootView = view;

        hotspotList = new ArrayList<>();

        initiateView();
        getJSON();

        previousTitle = ((AppCompatActivity)getActivity()).getSupportActionBar().getTitle().toString();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_hotspot);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_hotspot);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    private void initiateView() {
        hotspotRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_hotspot);
        hotspotRecyclerView.setHasFixedSize(true);
        hotspotRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadingProgressBar = rootView.findViewById(R.id.hotspot_progress_bar);
    }

    private void populateView() {
        Hotspot.hotspotList.addAll(hotspotList);
        hotspotAdapter = new HotspotAdapter(
                getActivity(),
                hotspotList,
                this,
                mUserToken
        );
        hotspotRecyclerView.setAdapter(hotspotAdapter);
        hotspotRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_hotspot);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(true);
        }
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                hotspotAdapter.filter(newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                hotspotAdapter.filter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        super.onCreateOptionsMenu(menu, inflater);
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
            for(int i = 0; i<result.length(); i++){
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
            try {
                jsonObject = new JSONObject(JSON_STRING);
                String message = jsonObject.getString("success");
                if (message.equals("false")) {
                    Snackbar.make(rootView, "Pegawai tidak ditemukan", Snackbar.LENGTH_LONG).setAction("Message", null).show();
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
                Toast.makeText(getActivity(), "Terjadi kesalahan, silakan login kembali", Toast.LENGTH_SHORT).show();
                signOut();
            }
            getActivity().getFragmentManager().popBackStack();
        }
        populateView();
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingProgressBar.setVisibility(View.VISIBLE);
                hotspotRecyclerView.setVisibility(View.GONE);
//                loading = ProgressDialog.show(getActivity(),null,null,false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loadingProgressBar.setVisibility(View.GONE);
                hotspotRecyclerView.setVisibility(View.VISIBLE);
//                loading.dismiss();
                JSON_STRING = s;
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_HOTSPOTALL+mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
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