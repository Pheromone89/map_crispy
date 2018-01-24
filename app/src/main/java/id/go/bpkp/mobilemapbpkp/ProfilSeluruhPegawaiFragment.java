package id.go.bpkp.mobilemapbpkp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ASUS on 22/01/2018.
 */

public class ProfilSeluruhPegawaiFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView listView;
    private String JSON_STRING;
    View fragmentView;
    private String urlToken;

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
        urlToken = this.getArguments().getString("url_token");

        Toast.makeText(getActivity(), urlToken, Toast.LENGTH_SHORT).show();

        fragmentView = view;
        listView = (ListView) fragmentView.findViewById(R.id.list_seluruh_pegawai);
        listView.setOnItemClickListener(this);
        getJSON();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_seluruh_pegawai);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_seluruh_pegawai);
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            Toast.makeText(getActivity(), "JSON object dibuat ", Toast.LENGTH_SHORT).show();

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String nama = jo.getString(konfigurasi.TAG_NAMA);
                String niplama = jo.getString(konfigurasi.TAG_NIPLAMA);
                String nipbaru = jo.getString(konfigurasi.TAG_NIPBARU);
                String pangkat = jo.getString(konfigurasi.TAG_PANGKAT);
                String jabatansingkat = jo.getString(konfigurasi.TAG_JABATANSINGKAT);
                String unit = jo.getString(konfigurasi.TAG_UNIT);
                String pendidikan = jo.getString(konfigurasi.TAG_PENDIDIKANSINGKAT);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_NAMA,nama);
                employees.put(konfigurasi.TAG_NIPLAMA,niplama);
                employees.put(konfigurasi.TAG_NIPBARU,nipbaru);
                employees.put(konfigurasi.TAG_PANGKAT,pangkat);
                employees.put(konfigurasi.TAG_JABATANSINGKAT,jabatansingkat);
                employees.put(konfigurasi.TAG_UNIT,unit);
                employees.put(konfigurasi.TAG_PENDIDIKANSINGKAT,pendidikan);
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
        Toast.makeText(getActivity(), "Adapter berhasil diinisasi", Toast.LENGTH_SHORT).show();
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
                String s = rh.sendGetRequest(urlToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), PlaceholderActivity.class);

        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        Toast.makeText(getActivity(), "http://118.97.51.140:10001/map/public/foto/"+map.get(konfigurasi.EMP_NIPLAMA)+".gif", Toast.LENGTH_SHORT).show();

//        String empId = map.get(konfigurasi.TAG_ID);
//        String empName = map.get(konfigurasi.TAG_NAMA);
//        String empNipbaru = map.get(konfigurasi.TAG_NIPBARU);
//        String empNiplama = map.get(konfigurasi.TAG_NIPLAMA);
//        String empPangkat = map.get(konfigurasi.TAG_PANGKAT);
//        String empJabatan = map.get(konfigurasi.TAG_JABATAN);
//        String empUnit = map.get(konfigurasi.TAG_UNIT);
//        String empFoto = map.get("http://118.97.51.140:10001/map/public/foto/"+konfigurasi.TAG_NIPLAMA+".gif");
//        intent.putExtra(konfigurasi.EMP_ID,empId);
//        intent.putExtra(konfigurasi.EMP_NAMA,empName);
//        intent.putExtra(konfigurasi.EMP_NIPBARU,empNipbaru);
//        intent.putExtra(konfigurasi.EMP_NIPLAMA,empNiplama);
//        intent.putExtra(konfigurasi.EMP_PANGKAT,empPangkat);
//        intent.putExtra(konfigurasi.EMP_JABATAN,empJabatan);
//        intent.putExtra(konfigurasi.EMP_UNIT,empUnit);
//        intent.putExtra("http://118.97.51.140:10001/map/public/foto/"+konfigurasi.EMP_NIPLAMA+".gif",empFoto);

//        getActivity().startActivity(intent);
//        String[] allPegawaiData = {
//                empId,
//                empName,
//                empNipbaru,
//                empNiplama,
//                empPangkat,
//                empJabatan,
//                empUnit,
//                empFoto
//        };

////
////        Bundle dataPegawai = new Bundle();
////        dataPegawai.putStringArray("all data", new String[]{
////                empId,
////                empName,
////                empNipbaru,
////                empNiplama,
////                empPangkat,
////                empJabatan,
////                empUnit,
////                empFoto});
////        fragment.setArguments(dataPegawai);
////
//        PlaceholderActivity fragment = new ProfilPegawaiFragment();
////        Bundle args = new Bundle();
////        args.putStringArray("test", allPegawaiData);
////        fragment.setArguments(args);
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.content_fragment_area, fragment);
//        fragmentTransaction.commit();
    }
}
