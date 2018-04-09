package id.go.bpkp.mobilemapbpkp.cuti;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 15/02/2018.
 */

public class CutiDashboardAdminFragment extends Fragment {

    private View
            rootView;
    private String
            mUserToken,
            mNipLama,
            mNipBaru,
            mNama,
            mFoto,
            mAtasanLangsung,
            mNipAtasanLangsung;
    private int
            mRoleIdInt;
    private boolean
            tidakPunyaAtasanLangsung;
    private CardView
            daftarPegawaiCutiView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuti_dashboard_admin, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_dashboard_admin);

        //bundle dari fragment sebelumnya
        //URL foto
        mFoto = this.getArguments().getString(PassedIntent.INTENT_FOTO);
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        //nip lama
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        //nip baru
        mNipBaru = this.getArguments().getString(PassedIntent.INTENT_NIPBARU);
        //nama
        mNama = this.getArguments().getString(PassedIntent.INTENT_NAMA);
        //role id
        mRoleIdInt = this.getArguments().getInt(PassedIntent.INTENT_ROLEIDINT);

        initiateView();
        populateView();
        initiateSetOnClickMethod();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_dashboard_admin);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        daftarPegawaiCutiView = rootView.findViewById(R.id.dashboard_cuti_daftar_pegawai_button);
    }

    private void populateView() {

    }

    private void initiateSetOnClickMethod() {
        daftarPegawaiCutiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                String url;
                if (mRoleIdInt == 1 || mRoleIdInt == 2) {
                    url = konfigurasi.URL_GET_ALLSMALL;
                } else if (mRoleIdInt == 3) {
                    url = konfigurasi.URL_GET_ALLBYUNIT + mNipLama + "?api_token=";
                } else {
                    url = konfigurasi.URL_GET_ALLSMALL;
                }
                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
                bundle.putString(PassedIntent.INTENT_FRAGMENTCONTENT, url);
                bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);
                bundle.putInt(PassedIntent.INTENT_ROLEIDINT, mRoleIdInt);

                fragment = new CutiDaftarPegawaiFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentManager.popBackStack();
                fragmentTransaction.commit();
            }
        });
    }
}