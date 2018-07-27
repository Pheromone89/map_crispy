package id.go.bpkp.mobilemapbpkp.monitoring;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.ProgressBar;
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
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;

/**
 * Created by ASUS on 22/01/2018.
 */

public class ProfilSeluruhPegawaiFragment extends Fragment implements RecyclerViewClickListener {

    private String
            JSON_STRING;
    private View
            rootView;
    private String
            mUserToken,
            mUrl,
            mNipLama,
            previousTitle;
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
            pegawaiSingkatRecyclerView;
    private PegawaiSingkatAdapter
            pegawaiSingkatAdapter;
    private List<PegawaiSingkat>
            pegawaiSingkatList;
    private ProgressBar loadingProgressBar;

    public ProfilSeluruhPegawaiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_seluruh_pegawai, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        mUrl = this.getArguments().getString(PassedIntent.INTENT_FRAGMENTCONTENT);
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        mRoleId = this.getArguments().getInt(PassedIntent.INTENT_ROLEIDINT);
        rootView = view;

        pegawaiSingkatList = new ArrayList<>();

        initiateView();
        getJSON();

        previousTitle = ((AppCompatActivity)getActivity()).getSupportActionBar().getTitle().toString();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_seluruh_pegawai);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_seluruh_pegawai);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    private void initiateView() {
        pegawaiSingkatRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_seluruh_pegawai);
        pegawaiSingkatRecyclerView.setHasFixedSize(true);
        pegawaiSingkatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadingProgressBar = rootView.findViewById(R.id.seluruh_pegawai_progress_bar);
    }

    private void populateView() {
        pegawaiSingkatAdapter = new PegawaiSingkatAdapter(getActivity(), pegawaiSingkatList, this, mUserToken);
        pegawaiSingkatRecyclerView.setAdapter(pegawaiSingkatAdapter);
        pegawaiSingkatRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL) {
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_seluruh_pegawai);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(true);
        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                String url = konfigurasi.URL_GET_ALLBYQUERY + (query.trim()) + "?api_token=";
                ProfilSeluruhPegawaiFragment fragment = new ProfilSeluruhPegawaiFragment();
                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_FRAGMENTCONTENT, url);
                bundle.putInt(PassedIntent.INTENT_ROLEIDINT, mRoleId);
                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                return false;
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
                nipBaru = jo.getString(konfigurasi.TAG_NIPBARU);
                nipBaruPisah = jo.getString(konfigurasi.TAG_NIPBARUGABUNG);
                jabatanSingkat = jo.getString(konfigurasi.TAG_JABATANSINGKAT);
                unit = jo.getString(konfigurasi.TAG_UNIT);
                foto = PassedIntent.getFoto(getActivity(), nipLama);

                pegawaiSingkatList.add(
                        new PegawaiSingkat(
                                i,
                                nama,
                                nipLama,
                                nipBaru,
                                nipBaruPisah,
                                jabatanSingkat,
                                unit,
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
                pegawaiSingkatRecyclerView.setVisibility(View.GONE);
//                loading = ProgressDialog.show(getActivity(),null,null,false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loadingProgressBar.setVisibility(View.GONE);
                pegawaiSingkatRecyclerView.setVisibility(View.VISIBLE);
//                loading.dismiss();
                JSON_STRING = s;
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(mUrl+mUserToken);
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