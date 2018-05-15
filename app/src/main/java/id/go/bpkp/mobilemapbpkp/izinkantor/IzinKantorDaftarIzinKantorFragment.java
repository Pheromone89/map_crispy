package id.go.bpkp.mobilemapbpkp.izinkantor;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;

/**
 * Created by ASUS on 09/02/2018.
 */

public class IzinKantorDaftarIzinKantorFragment extends Fragment {

    private View
            rootView;
    private String
            mUserToken,
            mNipLama,
            mNipBaru,
            mNama,
            mFoto,
            tahunMin2Label,
            tahunMin1Label,
            hakCutiTMin2,
            hakCutiTMin1,
            hakCutiT,
            jumlahHakCuti,
            cutiTerpakai;
    private int
            mRoleIdInt,
            tahunBerjalan,
            tahunMin2,
            tahunMin1;
    private ImageView
            proficView;
    private TextView
            namaView,
            nipView,
            cutiT2LabelView,
            cutiT1LabelView,
            hakCutiTMin2View,
            hakCutiTMin1View,
            hakCutiTView,
            jumlahHakCutiView,
            cutiTerpakaiView;
    private CardView
            pengajuanCutiButton,
            daftarCutiButton;

    public IzinKantorDaftarIzinKantorFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuti_data_cuti, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_data);

        //bundle dari fragment sebelumnya
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        //nip lama
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        //role id
        mRoleIdInt = this.getArguments().getInt(PassedIntent.INTENT_ROLEIDINT);

        initiateView();
        populateView();

        // harus terakhir
        initiateSetOnClickMethod();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_data);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {

    }

    private void populateView() {

    }

    private void initiateSetOnClickMethod() {

    }
}