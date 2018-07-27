package id.go.bpkp.mobilemapbpkp.izinkantor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
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
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 15/02/2018.
 */

public class IzinKantorDaftarPegawaiFragment extends Fragment implements RecyclerViewClickListener {

    private String
            JSON_STRING;
    private View
            rootView;
    private String
            mUserToken,
            mUrl,
            mNipLama,
            mFotoUrl,
            mFoto;
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
            pegawaiIzinKantorRecyclerView;
    private PegawaiIzinKantorAdapter
            pegawaiIzinKantorAdapter;
    private List<PegawaiIzinKantor>
            pegawaiIzinKantorList;
    private ProgressBar loadingProgressBar;

    public IzinKantorDaftarPegawaiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_izin_kantor_seluruh_pegawai, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        mUrl = this.getArguments().getString(PassedIntent.INTENT_FRAGMENTCONTENT);
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        mRoleId = this.getArguments().getInt(PassedIntent.INTENT_ROLEIDINT);
        mFotoUrl = this.getArguments().getString(PassedIntent.INTENT_FOTOURL);
        mFoto = this.getArguments().getString(PassedIntent.INTENT_FOTO);
        rootView = view;

        pegawaiIzinKantorList = new ArrayList<>();

        initiateView();
        getJSON();

//        previousTitle = ((AppCompatActivity)getActivity()).getSupportActionBar().getTitle().toString();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_seluruh_pegawai);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_seluruh_pegawai);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    private void initiateView() {
        loadingProgressBar = rootView.findViewById(R.id.izin_kantor_seluruh_pegawai_progress_bar);
        pegawaiIzinKantorRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_izin_kantor_seluruh_pegawai);
        pegawaiIzinKantorRecyclerView.setHasFixedSize(true);
        pegawaiIzinKantorRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void populateView() {
        pegawaiIzinKantorAdapter = new PegawaiIzinKantorAdapter(getActivity(), pegawaiIzinKantorList, this, mUserToken);
        pegawaiIzinKantorRecyclerView.setAdapter(pegawaiIzinKantorAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_izin_kantor_seluruh_pegawai);

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
                String url = konfigurasi.URL_GET_ALLBYQUERY + query + "?api_token=";
                IzinKantorDaftarPegawaiFragment fragment = new IzinKantorDaftarPegawaiFragment();
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
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                nama = jo.getString(konfigurasi.TAG_NAMA);
                nipLama = jo.getString(konfigurasi.TAG_NIPLAMA);
                nipBaru = jo.getString(konfigurasi.TAG_NIPBARU);
                nipBaruPisah = jo.getString(konfigurasi.TAG_NIPBARUGABUNG);
                foto = PassedIntent.getFoto(getActivity(), nipLama);
//                foto = mFotoUrl + nipLama;

                pegawaiIzinKantorList.add(
                        new PegawaiIzinKantor(
                                i,
                                nama,
                                nipLama,
                                nipBaru,
                                nipBaruPisah,
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
                pegawaiIzinKantorRecyclerView.setVisibility(View.GONE);
//                loading = ProgressDialog.show(getActivity(),null,null,false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loadingProgressBar.setVisibility(View.GONE);
                pegawaiIzinKantorRecyclerView.setVisibility(View.VISIBLE);
//                loading.dismiss();
                JSON_STRING = s;
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(mUrl + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
