package id.go.bpkp.mobilemapbpkp.monitoring;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.SettingPrefs;

/**
 * Created by ASUS on 11/02/2018.
 */

public class ProfilPegawaiPagerFragment extends Fragment implements RecyclerViewClickListener {

    private String
            mFoto,
            mUserToken,
            mNipLama,
            mNoHp,
            mEmail,
            mNipBaru,
            username;
    private Bundle
            fragmentBundle;
    private int
            mRoledId;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // set up setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        //bundle dari fragment sebelumnya
        //URL foto
        mFoto = this.getArguments().getString(PassedIntent.INTENT_FOTO);
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        //nip tanpa spasi
        username = this.getArguments().getString(PassedIntent.INTENT_USERNAME);
        //nip lama tanpa spasi
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        // nohp
        mNoHp = this.getArguments().getString(PassedIntent.INTENT_NOHP);
        mEmail = this.getArguments().getString(PassedIntent.INTENT_EMAIL);
        mRoledId = this.getArguments().getInt(PassedIntent.INTENT_ROLEIDINT);

        fragmentBundle = new Bundle();
        fragmentBundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
        fragmentBundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLama);
        fragmentBundle.putString(PassedIntent.INTENT_NIPBARU, mNipBaru);
        fragmentBundle.putString(PassedIntent.INTENT_FOTO, mFoto);
        fragmentBundle.putString(PassedIntent.INTENT_NOHP, mNoHp);
        fragmentBundle.putString(PassedIntent.INTENT_EMAIL, mEmail);
        fragmentBundle.putInt(PassedIntent.INTENT_ROLEIDINT, mRoledId);

        View result = inflater.inflate(R.layout.pager_profil_pegawai, container, false);
        ViewPager profilPegawaiViewPager = (ViewPager) result.findViewById(R.id.pager_profil_pegawai);
        profilPegawaiViewPager.setAdapter(buildAdapter());

        TabLayout profilPegawaiTabLayout = (TabLayout) result.findViewById(R.id.pager_tab_profil_pegawai);
        profilPegawaiTabLayout.setupWithViewPager(profilPegawaiViewPager);

        return (result);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ngambil judul dan ngeset judul fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_pegawai);
        setHasOptionsMenu(true);
    }

    private PagerAdapter buildAdapter() {
        ProfilPegawaiFragmentPagerAdapter profilPegawaiFragmentPagerAdapter = new ProfilPegawaiFragmentPagerAdapter(
                getChildFragmentManager(),
                getActivity(),
                fragmentBundle
        );

        return profilPegawaiFragmentPagerAdapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_pegawai);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SettingPrefs.clearProfil(sharedPreferences);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SettingPrefs.clearProfil(sharedPreferences);
    }
}
