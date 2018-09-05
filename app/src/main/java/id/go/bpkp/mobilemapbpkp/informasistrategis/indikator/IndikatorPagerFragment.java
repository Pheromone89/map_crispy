package id.go.bpkp.mobilemapbpkp.informasistrategis.indikator;

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

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;

/**
 * Created by ASUS on 11/02/2018.
 */

public class IndikatorPagerFragment extends Fragment implements RecyclerViewClickListener {

    private String
            mUserToken;
    private Bundle
            fragmentBundle;
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
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);

        fragmentBundle = new Bundle();
        fragmentBundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);

        View result = inflater.inflate(R.layout.pager_indikator, container, false);
        ViewPager indikatorViewPager = (ViewPager) result.findViewById(R.id.pager_indikator);
        indikatorViewPager.setAdapter(buildAdapter());

        TabLayout indikatorTabLayout = (TabLayout) result.findViewById(R.id.pager_tab_indikator);
        indikatorTabLayout.setupWithViewPager(indikatorViewPager);

        return (result);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ngambil judul dan ngeset judul fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_informasi_strategis);
        setHasOptionsMenu(true);
    }

    private PagerAdapter buildAdapter() {
        IndikatorFragmentPagerAdapter indikatorFragmentPagerAdapter = new IndikatorFragmentPagerAdapter(
                getChildFragmentManager(),
                getActivity(),
                fragmentBundle
        );

        return indikatorFragmentPagerAdapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_informasi_strategis);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
