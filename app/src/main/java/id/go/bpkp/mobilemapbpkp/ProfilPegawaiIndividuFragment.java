package id.go.bpkp.mobilemapbpkp;

import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by ASUS on 24/01/2018.
 */

public class ProfilPegawaiIndividuFragment extends Fragment {

    private View rootView;
    private String
            JSON_STRING,
            previousTitle;
    private String
            mNipBaru,
            mFoto,
            mUserToken,
            mNipLama,
            username;
    private TextView
            namaGelarProfilPictureView,
            nipProfilPictureView,
            namaPegawaiIndividuView,
            nipPegawaiIndividuView,
            pangkatPegawaiIndividuView,
            unitPegawaiIndividuView,
            jabatanProfilIndividuView,
            pendidikanProfilIndividuView,
            ttglProfilIndividuView,
            agamaProfilIndividuView,
            alamatProfilIndividuView,
            sertpimProfilIndividuView,
            sertjfaProfilIndividuView,
            sertprofProfilIndividuView,
            pasanaganProfilIndividuView,
            jumlahanakProfilIndividuView,
            akreProfilIndividuView;
    private ImageView
            proficPegawaiIndividuView;

    private String
            namaLengkap,
            namaGelar,
            pangkat,
            jabatan,
            unit,
            pendidikan,
            ttgl,
            agama,
            alamat,
            sertpim,
            sertjfa,
            sertprof,
            pasangan,
            jumlahanak,
            akre;
    private int akreInt;

    public ProfilPegawaiIndividuFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_pegawai_individu, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ngambil judul dan ngeset judul fragment
        previousTitle = ((AppCompatActivity)getActivity()).getSupportActionBar().getTitle().toString();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_pegawai_individu);
        setHasOptionsMenu(true);

        //inisiasi rootView
        rootView = (View) view;
        previousTitle = ((AppCompatActivity)getActivity()).getSupportActionBar().getTitle().toString();

        //bundle dari fragment sebelumnya
        //URL foto
        mFoto = this.getArguments().getString("foto");
        //login token
        mUserToken = this.getArguments().getString("user_token");
        //nip tanpa spasi
        username = this.getArguments().getString("username");
        //nip lama tanpa spasi
        mNipLama = this.getArguments().getString("nip_lama");

        initiateView(rootView);
        getJSON();

//        Intent pdfDownload = new Intent(getActivity(), PdfDownloadActivity.class);
//        pdfDownload.putExtra("download_link", "http://118.97.51.140:10001/map/api/drh/"+mNipLama+"?api_token="+mUserToken);
//        pdfDownload.putExtra("nip_baru", username);
//        pdfDownload.putExtra("nama", username);
//        startActivity(pdfDownload);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_pegawai_individu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView(View view){
        namaPegawaiIndividuView = (TextView) view.findViewById(R.id.profil_individu_nama);
        nipPegawaiIndividuView = (TextView) view.findViewById(R.id.profil_individu_nip);
        pangkatPegawaiIndividuView = (TextView) view.findViewById(R.id.profil_individu_pangkat);
        unitPegawaiIndividuView = (TextView) view.findViewById(R.id.profil_individu_unit);
        proficPegawaiIndividuView = (ImageView) view.findViewById(R.id.profil_individu_profic);
        namaGelarProfilPictureView = (TextView) view.findViewById(R.id.profil_individu_proficnama);
        nipProfilPictureView = (TextView) view.findViewById(R.id.profil_individu_proficnip);
        jabatanProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_jabatan);
        pendidikanProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_pendidikan);
        ttglProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_ttgl);
        agamaProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_agama);
        alamatProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_alamat);
        sertpimProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_sertpim);
        sertjfaProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_sertjfa);
        sertprofProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_sertprof);
        pasanaganProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_pasangan);
        jumlahanakProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_jumlahanak);
        akreProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_akre);
    }
    private void populateView(){
        // profile picture area
        pangkatPegawaiIndividuView.setText(mFoto);
        Picasso.with(getActivity()).load(mFoto).into(proficPegawaiIndividuView);
        namaGelarProfilPictureView.setText(namaGelar);
        nipProfilPictureView.setText(mNipBaru);
        // profile data
        namaPegawaiIndividuView.setText(namaLengkap);
        nipPegawaiIndividuView.setText(mNipBaru);
        pangkatPegawaiIndividuView.setText(pangkat);
        unitPegawaiIndividuView.setText(unit);
        jabatanProfilIndividuView.setText(jabatan);
        pendidikanProfilIndividuView.setText(pendidikan);
        ttglProfilIndividuView.setText(ttgl);
        agamaProfilIndividuView.setText(agama);
        alamatProfilIndividuView.setText(alamat);
        sertpimProfilIndividuView.setText(sertpim);
        sertjfaProfilIndividuView.setText(sertjfa);
        sertprofProfilIndividuView.setText(sertprof);
        pasanaganProfilIndividuView.setText(pasangan);
        jumlahanakProfilIndividuView.setText(jumlahanak);
        akreProfilIndividuView.setText(akre);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_EMP+mNipLama+"?api_token="+mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
        Toast.makeText(getActivity(), "JSON get'd", Toast.LENGTH_SHORT).show();
    }
    private void showEmployee(){
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            namaLengkap = jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NAMA);
            namaGelar = jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NAMALENGKAP);
            mNipBaru = jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NIPBARU);
            pangkat = jsonObject.getJSONObject("result").getString(konfigurasi.TAG_PANGKAT);
            jabatan = jsonObject.getJSONObject("result").getString(konfigurasi.TAG_JABATAN);
            unit = jsonObject.getJSONObject("result").getString(konfigurasi.TAG_UNIT);
            pendidikan = jsonObject.getJSONObject("result").getString(konfigurasi.TAG_PENDIDIKANSTRATASINGKAT);
            pendidikan = pendidikan + " " + jsonObject.getJSONObject("result").getString(konfigurasi.TAG_PENDIDIKANSINGKAT);
            pendidikan = pendidikan + " " + jsonObject.getJSONObject("result").getString(konfigurasi.TAG_PENDIDIKANJURUSAN);
            ttgl = jsonObject.getJSONObject("result").getString(konfigurasi.TAG_TEMPATLAHIR);
            ttgl = ttgl + ", " +jsonObject.getJSONObject("result").getString(konfigurasi.TAG_TANGGALLAHIR);
            agama = jsonObject.getJSONObject("result").getString(konfigurasi.TAG_AGAMA);
            alamat = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_ALAMAT));
            sertpim = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_DIKLATSTRUK));
            sertjfa = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_SERTJFA));
            sertprof = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_SERTPROFESI));
            akre = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_TOTALPAK));
//            akreInt = Integer.parseInt(akre)*100;
            pasangan = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NAMAPASANGAN));
            pasangan = pasangan + " / " + checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_UNITPASANGAN));
            jumlahanak = "DATA BELUM TERSEDIA";
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "JSONException", Toast.LENGTH_SHORT).show();
        }
        populateView();
    }

    private String checkNull (String string){
        if (string.equals("null")){
            return "-";
        } else {
            return string;
        }
    }
}
