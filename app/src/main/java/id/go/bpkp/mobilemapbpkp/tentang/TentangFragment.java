package id.go.bpkp.mobilemapbpkp.tentang;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.util.Calendar;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDaftarCutiFragment;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDaftarPersetujuanFragment;
import id.go.bpkp.mobilemapbpkp.cuti.CutiPengajuanPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 09/02/2018.
 */

public class TentangFragment extends Fragment {

    private View
            rootView;
    private LinearLayout rootLayout;
    private ProgressBar rootProgressBar;
    // tentang
    private TextView judulView, judulUpdateView;
    private CardView updateButton, backButton;
    private RelativeLayout tentangUmumLayout;
    private LinearLayout tentangFiturBaruLayout;
    private ListView updateListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tentang, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_dashboard_pegawai);

        initiateView();
        populateView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_cuti_dashboard_pegawai);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        // root
        rootLayout = rootView.findViewById(R.id.tentang_layout);
        rootProgressBar = rootView.findViewById(R.id.tentang_progress_bar);
        rootLayout.setVisibility(View.GONE);
        rootProgressBar.setVisibility(View.VISIBLE);
        // layout
        tentangUmumLayout = rootView.findViewById(R.id.tentang_umum);
        tentangFiturBaruLayout = rootView.findViewById(R.id.tentang_fitur_baru);
        judulUpdateView = rootView.findViewById(R.id.tentang_title_update);
        updateListView = rootView.findViewById(R.id.tentang_list_update);
        // data
        judulView = rootView.findViewById(R.id.tentang_judul);
        // button
        updateButton = rootView.findViewById(R.id.tentang_update_button);
        backButton = rootLayout.findViewById(R.id.tentang_back_button);
    }

    private void populateView() {
        // profic
        String judulUpdate;
        String judul = getResources().getString(R.string.tentang_versi);
        String versi = null;
        String[] updateList = new String[]{
                "Penambahan fitur konfirmasi kehadiran (fingerprint).",
                "Penambahan fitur panel monitoring untuk akun atasan.",
                "Penambahan fitur monitoring presensi untuk akun atasan.",
                "Penambahan fitur persetujuan cuti untuk akun atasan.",
                "Penambahan fitur \"about MAP MOBILE\".",
                "Perbaikan \"bug\".",
                "Perbaikan tampilan."
        };
        try {
            versi = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        judulUpdate = "Yang baru di MAP Mobile\nversi " + versi + " - Juli 2018";
        judul = judul + " " + versi;
        judulView.setText(judul);
        judulUpdateView.setText(judulUpdate);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.item_update,
                R.id.tentang_item_update_konten,
                updateList
        );
        updateListView.setAdapter(arrayAdapter);

        rootProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, rootLayout, 1500);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tentangFiturBaruLayout.isShown()) {
                    tentangFiturBaruLayout.setVisibility(View.GONE);
                    tentangUmumLayout.setVisibility(View.VISIBLE);
                } else {
                    tentangFiturBaruLayout.setVisibility(View.VISIBLE);
                    tentangUmumLayout.setVisibility(View.GONE);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tentangFiturBaruLayout.isShown()) {
                    tentangFiturBaruLayout.setVisibility(View.GONE);
                    tentangUmumLayout.setVisibility(View.VISIBLE);
                } else {
                    tentangFiturBaruLayout.setVisibility(View.VISIBLE);
                    tentangUmumLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}