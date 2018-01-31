package id.go.bpkp.mobilemapbpkp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ASUS on 22/01/2018.
 */

public class ProfilSeluruhPegawaiFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView listView;
    private String JSON_STRING;
    View rootView;
    String mUserToken, mUrl, previousTitle;

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
        mUserToken = this.getArguments().getString("user_token");
        mUrl = this.getArguments().getString("fragment_url");

        rootView = view;
        listView = (ListView) rootView.findViewById(R.id.list_seluruh_pegawai);
        listView.setOnItemClickListener(this);
        getJSON();

        previousTitle = ((AppCompatActivity)getActivity()).getSupportActionBar().getTitle().toString();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_seluruh_pegawai);

        Toast.makeText(getActivity(), mUrl, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_seluruh_pegawai);
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
                String url = konfigurasi.URL_GET_ALLBYQUERY+query+"?api_token=";
                Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();

//                Intent g = new Intent(getActivity(), PlaceholderActivity.class);
//                g.putExtra("url", url);
//                startActivity(g);
//
                ProfilSeluruhPegawaiFragment fragment = new ProfilSeluruhPegawaiFragment();
                Bundle bundle = new Bundle();
                bundle.putString("user_token", mUserToken);
                bundle.putString("fragment_url", url);
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

    private void showEmployee(){
        JSONObject jsonObject = null;
        Toast.makeText(getActivity(), "json", Toast.LENGTH_SHORT).show();
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        Toast.makeText(getActivity(), "arraylist", Toast.LENGTH_SHORT).show();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String nama = jo.getString(konfigurasi.TAG_NAMA);
                String niplama = jo.getString(konfigurasi.TAG_NIPLAMA);
                String nipbaru = jo.getString(konfigurasi.TAG_NIPBARU);
                String nippisah = jo.getString(konfigurasi.TAG_NIPBARUGABUNG);
                String jabatansingkat = jo.getString(konfigurasi.TAG_JABATANSINGKAT);
                String unit = jo.getString(konfigurasi.TAG_UNIT);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_NAMA,nama);
                employees.put(konfigurasi.TAG_NIPLAMA,niplama);
                employees.put(konfigurasi.TAG_NIPBARU,nipbaru);
                employees.put(konfigurasi.TAG_NIPBARUGABUNG,nippisah);
                employees.put(konfigurasi.TAG_JABATANSINGKAT,jabatansingkat);
                employees.put(konfigurasi.TAG_UNIT,unit);
                employees.put("foto", "http://118.97.51.140:10001/map/public/foto/"+niplama+".gif");
                list.add(employees);
            }
            Toast.makeText(getActivity(), "empoloyee object telah dibuat", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "error exception", Toast.LENGTH_SHORT).show();
        }
        ListAdapter adapter = new PegawaiAdapter(
                getActivity(),
                list,
                R.layout.item_list_pegawai,
                new String[]{
                        konfigurasi.TAG_NAMA,
                        konfigurasi.TAG_NIPBARU,
                        konfigurasi.TAG_JABATANSINGKAT,
                        konfigurasi.TAG_UNIT,
                        "foto"
                },
                new int[]{
                        R.id.nama,
                        R.id.nipbaru,
                        R.id.jabatan_singkat,
                        R.id.unit,
                        R.id.profic
                });
        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        Toast.makeText(getActivity(), "http://118.97.51.140:10001/map/public/foto/"+map.get(konfigurasi.EMP_NIPLAMA)+".gif", Toast.LENGTH_SHORT).show();

        String empNipbaru = map.get(konfigurasi.TAG_NIPBARU);
        String empUsername = map.get(konfigurasi.TAG_NIPBARUGABUNG);
        String empNiplama = map.get(konfigurasi.TAG_NIPLAMA);
        String empFoto ="http://118.97.51.140:10001/map/public/foto/"+map.get(konfigurasi.EMP_NIPLAMA)+".gif";

        Bundle bundle = new Bundle();
        bundle.putString("nip_baru", empNipbaru);
        bundle.putString("foto", empFoto);
        bundle.putString("user_token", mUserToken);
        bundle.putString("username", empUsername);
        bundle.putString("nip_lama", empNiplama);

        ProfilPegawaiIndividuFragment fragment = new ProfilPegawaiIndividuFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.content_fragment_area, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}