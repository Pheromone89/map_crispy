package id.go.bpkp.mobilemapbpkp.informasistrategis.indikator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import id.go.bpkp.mobilemapbpkp.konfigurasi.FragmentBundles;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 11/02/2018.
 */

public class IndikatorFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Bundle fragmentBundle;
    private Context mContext;

    public IndikatorFragmentPagerAdapter(FragmentManager fm, Context context, Bundle data) {
        super(fm);
        mContext = context;
        fragmentBundle = data;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle fragmentBundle = this.fragmentBundle;
        switch (position) {
            case 0:
                fragment = new IndikatorDistribusiPKPTFragment();
                fragmentBundle.putString(PassedIntent.INTENT_FRAGMENTCONTENT, null);
                fragment.setArguments(fragmentBundle);
                return fragment;
            case 1:
                fragment = new IndikatorFragment();
                fragmentBundle.putString(PassedIntent.INTENT_FRAGMENTCONTENT, konfigurasi.URL_GET_INDIKATORPEMDA);
                fragment.setArguments(fragmentBundle);
                return fragment;
            case 2:
                fragment = new IndikatorFragment();
                fragmentBundle.putString(PassedIntent.INTENT_FRAGMENTCONTENT, konfigurasi.URL_GET_INDIKATORKL);
                fragment.setArguments(fragmentBundle);
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Distribusi PKPT";
//                return mContext.getString(R.string.title_fragment_page_data_pokok);
            case 1:
                return "Indikator Pemda";
//                return mContext.getString(R.string.title_fragment_page_data_diklat);
            case 2:
                return "Indikator K/L";
//                return mContext.getString(R.string.title_fragment_page_data_unit);
            default:
                return null;
        }
    }
}
