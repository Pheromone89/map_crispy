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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;

/**
 * Created by ASUS on 11/02/2018.
 */

public class ProfilPegawaiDataPokokFragment extends Fragment {

    private View
            rootView;
    private String
            JSON_STRING;
    private String
            mNipBaru,
            mFoto,
            mUserToken,
            mNipLama,
            mNoHp,
            mEmail,
            username;
    private TextView
            namaGelarProfilPictureView,
            nipProfilPictureView,
            pangkatProfilPictureView,
            namaPegawaiIndividuView,
            nipPegawaiIndividuView,
            pangkatPegawaiIndividuView,
            unitPegawaiIndividuView,
            jabatanProfilIndividuView,
            pendidikanProfilIndividuView,
            ttglProfilIndividuView,
            agamaProfilIndividuView,
            alamatProfilIndividuView,
            noHpProfilIndividuView,
            emailProfilIndividuView,
            sertpimProfilIndividuView,
            sertjfaProfilIndividuView,
            sertprofProfilIndividuView,
            pasanaganProfilIndividuView,
            jumlahanakProfilIndividuView,
            akreProfilIndividuView,
            toeflProfilIndividuView,
            lamaJabProfilIndividuView,
            lamaUnitProfilIndividuView,
            lamaKpProfilIndividuView,
            tmtPensiunProfilIndividuView;
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
            usia,
            agama,
            alamat,
            lamajab,
            lamaunit,
            lamakp,
            tmtpensiun,
            sertpim,
            sertjfa,
            sertprof,
            pasangan,
            jumlahanak,
            akre,
            toefl;
    private int akreInt, mRoleId;
    private LinearLayout hpLayout, emailLayout;
    private RelativeLayout fotoEditButton;
    private DateFormat dateFormat;
    private ProgressBar loadingProgressBar;
    private ScrollView dataPokokScrollView;
    private YoYo.YoYoString ropeProfilIndividu;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ProfilPegawaiDataPokokFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_pegawai_data_pokok, null);
    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        namaGelarProfilPictureView.setText(namaGelar);
//    }

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
        mFoto = this.getArguments().getString(PassedIntent.INTENT_FOTO);
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        //nip tanpa spasi
        username = this.getArguments().getString(PassedIntent.INTENT_USERNAME);
        //nip lama tanpa spasi
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        mNoHp = this.getArguments().getString(PassedIntent.INTENT_NOHP);
        mEmail = this.getArguments().getString(PassedIntent.INTENT_EMAIL);
        mRoleId = this.getArguments().getInt(PassedIntent.INTENT_ROLEIDINT);
        JSON_STRING = sharedPreferences.getString("saved_json_data_pokok", null);

        // date format
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        initiateView(rootView);
        if (JSON_STRING == null) {
            getJSON();
        } else {
            parseJSON();
        }
        setOnClick();
    }

    private void initiateView(View view){
        loadingProgressBar = rootView.findViewById(R.id.profil_individu_data_pokok_progress_bar);
        dataPokokScrollView = rootView.findViewById(R.id.profil_individu_data_pokok);
        dataPokokScrollView.setVisibility(View.GONE);
        proficPegawaiIndividuView = (ImageView) view.findViewById(R.id.profil_individu_profic);
        fotoEditButton = (RelativeLayout) view.findViewById(R.id.profil_individu_profic_edit);
        namaGelarProfilPictureView = (TextView) view.findViewById(R.id.profil_individu_proficnama);
        nipProfilPictureView = (TextView) view.findViewById(R.id.profil_individu_proficnip);
        namaPegawaiIndividuView = (TextView) view.findViewById(R.id.profil_individu_nama);
        nipPegawaiIndividuView = (TextView) view.findViewById(R.id.profil_individu_nip);
        pangkatPegawaiIndividuView = (TextView) view.findViewById(R.id.profil_individu_pangkat);
        unitPegawaiIndividuView = (TextView) view.findViewById(R.id.profil_individu_unit);
        pangkatProfilPictureView = (TextView) view.findViewById(R.id.profil_individu_proficpangkat);
        jabatanProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_jabatan);
        pendidikanProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_pendidikan);
        ttglProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_ttgl);
        agamaProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_agama);
        alamatProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_alamat);
        hpLayout = view.findViewById(R.id.profil_individu_hp_layout);
        noHpProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_no_hp);
        emailLayout = view.findViewById(R.id.profil_individu_email_layout);
        emailProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_email);
        lamaJabProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_lama_jab);
        lamaUnitProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_lama_unit);
        lamaKpProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_lama_kp);
        tmtPensiunProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_tmt_pensiun);
        sertpimProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_sertpim);
        sertjfaProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_sertjfa);
        sertprofProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_sertprof);
        pasanaganProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_pasangan);
        jumlahanakProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_jumlahanak);
        akreProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_akre);
        toeflProfilIndividuView = (TextView) view.findViewById(R.id.profil_individu_toefl);
    }
    private void populateView(){
        // saved json
        // profile picture area
        Picasso.with(getActivity()).load(mFoto).into(proficPegawaiIndividuView);
        namaGelarProfilPictureView.setText(namaGelar);
        String nip = mNipBaru + " / " + mNipLama;
        nipProfilPictureView.setText(nip);
        pangkatProfilPictureView.setText(pangkat);
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
        noHpProfilIndividuView.setText(mNoHp);
        emailProfilIndividuView.setText(mEmail);
        lamaJabProfilIndividuView.setText(lamajab);
        lamaUnitProfilIndividuView.setText(lamaunit);
        lamaKpProfilIndividuView.setText(lamakp);
//        tmtPensiunProfilIndividuView.setText(tmtpensiun);
        sertpimProfilIndividuView.setText(sertpim);
        sertjfaProfilIndividuView.setText(sertjfa);
        sertprofProfilIndividuView.setText(sertprof);
        pasanaganProfilIndividuView.setText(pasangan);
        jumlahanakProfilIndividuView.setText(jumlahanak);
        akreProfilIndividuView.setText(akre);
        toeflProfilIndividuView.setText(toefl);

        loadingProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, dataPokokScrollView, konfigurasi.animationDurationShort);
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loadingProgressBar.setVisibility(View.VISIBLE);
//                dataPokokScrollView.setVisibility(View.GONE);
//                loading = ProgressDialog.show(getActivity(),"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                editor.putString("saved_json_data_pokok", s);
                editor.apply();
                parseJSON();
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
    }

    private void parseJSON() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            namaLengkap = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NAMA));
            namaGelar = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NAMALENGKAP));
            mNipBaru = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NIPBARU));
            pangkat = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_PANGKAT));
            jabatan = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_JABATAN));
            unit = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_UNIT));
            pendidikan = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_PENDIDIKANSTRATASINGKAT));
            pendidikan = pendidikan + " " + checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_PENDIDIKANSINGKAT));
            pendidikan = pendidikan + " " + checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_PENDIDIKANJURUSAN));
            ttgl = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_TEMPATLAHIR));
            ttgl = ttgl + ", " + convertDate(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_TANGGALLAHIR));
            ttgl = ttgl + " / " + checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_USIA)) + " tahun";
            agama = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_AGAMA));
            alamat = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_ALAMAT));
            mNoHp = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NOHP));
            mEmail = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_EMAIL));
            lamajab = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_LAMATHJABATAN)) + " tahun "
                    + checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_LAMABLJABATAN)) + " bulan";
            lamaunit = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_LAMATHUNIT)) + " tahun "
                    + checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_LAMABLUNIT)) + " bulan";
            lamakp = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_LAMATHKP)) + " tahun "
                    + checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_LAMABLKP)) + " bulan";
            sertpim = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_DIKLATSTRUK));
            sertjfa = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_SERTJFA));
            sertprof = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_SERTPROFESI));
            akre = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_TOTALPAK));
            toefl = checkNull(jsonObject.getJSONObject("score").getString(konfigurasi.TAG_TOEFL));
            pasangan = checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_NAMAPASANGAN));
            pasangan = pasangan + " / " + checkNull(jsonObject.getJSONObject("result").getString(konfigurasi.TAG_UNITPASANGAN));
            jumlahanak = "DATA BELUM TERSEDIA";
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Terjadi kesalahan pada data pokok, silakan login kembali", Toast.LENGTH_SHORT).show();
            signOut();
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
                logoutIntent.putExtra(INTENT_NIPBARU, mNipBaru);
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

    private String convertDate(String tanggal) {
        if (tanggal.length() == 10) {
            String tanggalHari;
            String tanggalBulan;
            String tanggalTahun;

            tanggalHari = tanggal.substring(8, 10);
            tanggalBulan = tanggal.substring(5, 7);
            tanggalTahun = tanggal.substring(0, 4);
            return tanggalHari + "/" + tanggalBulan + "/" + tanggalTahun;
        } else if (tanggal.equals("null")) {
            return "-";
        } else {
            return tanggal;
        }
    }

    private void setOnClick() {
        fotoEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "upload foto baru", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
