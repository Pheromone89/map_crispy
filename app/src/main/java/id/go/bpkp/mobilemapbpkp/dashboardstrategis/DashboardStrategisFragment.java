package id.go.bpkp.mobilemapbpkp.dashboardstrategis;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.informasistrategis.indikator.IndikatorPagerFragment;
import id.go.bpkp.mobilemapbpkp.informasistrategis.realisasipkpt.RealisasiPkpt;
import id.go.bpkp.mobilemapbpkp.informasistrategis.realisasipkpt.RealisasiPkptAdapter;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by ASUS on 09/02/2018.
 */

public class DashboardStrategisFragment extends Fragment implements RecyclerViewClickListener {

    private View
            rootView;
    private String
            JSON_STRING,
            mUserToken;
    private LinearLayout rootLayout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private GifImageView rootProgressBar;
    private CardView pkptMenuButton, fpMenuButton, indikatorMenuButton;
    // list pkpt
    private String judul;
    private GifImageView pkptListProgressBar;
    private CardView pkptListCardview;
    private RecyclerView pkptRecyclerView;
    private RealisasiPkptAdapter
            realisasiPkptAdapter;
    private List<RealisasiPkpt>
            realisasiPkptList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_strategis, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_informasi_strategis);

        // setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        //bundle dari fragment sebelumnya
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);

        initiateView();
        populateView();

        // harus terakhir
        initiateSetOnClickMethod();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_informasi_strategis);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        rootLayout = rootView.findViewById(R.id.informasi_strategis_dashboard_layout);
        rootProgressBar = rootView.findViewById(R.id.informasi_strategis_dashboard_progress_bar);
        rootLayout.setVisibility(View.GONE);
        rootProgressBar.setVisibility(View.VISIBLE);
        // menu button
        pkptMenuButton = rootView.findViewById(R.id.informasi_strategis_menu_pkpt);
        fpMenuButton = rootView.findViewById(R.id.informasi_strategis_menu_fp);
        indikatorMenuButton = rootView.findViewById(R.id.informasi_strategis_menu_indikator);
    }

    private void populateView() {
        rootProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, rootLayout, konfigurasi.animationDurationShort);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    private void initiateSetOnClickMethod() {
        pkptMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "menu ini belum diimplementasikan", Toast.LENGTH_SHORT).show();
            }
        });
        fpMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "menu ini belum diimplementasikan", Toast.LENGTH_SHORT).show();
            }
        });
        indikatorMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                Bundle bundle = new Bundle();
                bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);

                IndikatorPagerFragment indikatorPagerFragment = new IndikatorPagerFragment();
                indikatorPagerFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, indikatorPagerFragment);
                fragmentTransaction.addToBackStack("fragment_dashboard_informasi_strategis");
                fragmentTransaction.commit();
            }
        });
    }

    private String checkNull(String string) {
        if (string.equals("null")) {
            return "0";
        } else {
            return string;
        }
    }
}