package id.go.bpkp.mobilemapbpkp.monitoring;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import id.go.bpkp.mobilemapbpkp.R;

/**
 * Created by ASUS on 11/02/2018.
 */

public class ProfilPegawaiFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Bundle fragmentBundle;
    private Context mContext;

    public ProfilPegawaiFragmentPagerAdapter(FragmentManager fm, Context context, Bundle data) {
        super(fm);
        mContext = context;
        fragmentBundle = data;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ProfilPegawaiDataPokokFragment();
                fragment.setArguments(this.fragmentBundle);
                return fragment;
            case 1:
                fragment = new ProfilPegawaiDataDiklatFragment();
                fragment.setArguments(this.fragmentBundle);
                return fragment;
            case 2:
                fragment = new ProfilPegawaiDataUnitFragment();
                fragment.setArguments(this.fragmentBundle);
                return fragment;
            case 3:
                fragment = new ProfilPegawaiDataJabatanFragment();
                fragment.setArguments(this.fragmentBundle);
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_fragment_page_data_pokok);
            case 1:
                return mContext.getString(R.string.title_fragment_page_data_diklat);
            case 2:
                return mContext.getString(R.string.title_fragment_page_data_unit);
            case 3:
                return mContext.getString(R.string.title_fragment_page_data_jabatan);
            default:
                return null;
        }
    }
}
