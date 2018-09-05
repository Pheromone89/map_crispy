package id.go.bpkp.mobilemapbpkp.informasistrategis.indikator;

import android.app.Fragment;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.cuti.PegawaiCutiAdapter;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.graphs.Graphs;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by ASUS on 11/02/2018.
 */

public class IndikatorDistribusiPKPTFragment extends Fragment implements RecyclerViewClickListener {

    private View
            rootView;
    private String
            JSON_STRING;
    private String
            mUserToken;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // jumlah PP
    private CardView chartLayoutJumlahPP;
    private PieChart chartViewJumlahPP;
    private LinearLayout legendLayoutJumlahPP;
    private CardView judulLayoutJumlahPP;
    private TextView judulViewJumlahPP;
    private GifImageView chartProgressBarJumlahPP;
    private String chartTitleJumlahPP;
    private ArrayList<String>
            jumlahPPLabel,
            jumlahPPFokus,
            jumlahPPPersentase,
            jumlahPPNilai;
    private ScrollView rootLayout;
    private GifImageView rootProgressBar;
    private LinearLayout dataLinearLayout;
    private LayoutInflater panelLayouInflater;
    private List<DistribusiPKPT> distribusiPKPTList;
    private RecyclerView legendRecyclerView;
    private DistribusiPKPTAdapter
            legendAdapter;

    public IndikatorDistribusiPKPTFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informasi_strategis_indikator_distribusi_pkpt, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ngambil judul dan ngeset judul fragment
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_informasi_strategis);

        // set up setting
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        //inisiasi rootView
        rootView = (View) view;

        //bundle dari fragment sebelumnya
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);

        distribusiPKPTList = new ArrayList<>();

        initiateView();
        populateView();

        // jumlah pp
        initiatePieChartJumlahPP();
        getJsonJumlahPP();
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
        rootLayout = rootView.findViewById(R.id.informasi_strategis_indikator_layout);
        rootProgressBar = rootView.findViewById(R.id.informasi_strategis_indikator_progress_bar);
        rootLayout.setVisibility(View.GONE);
        rootProgressBar.setVisibility(View.VISIBLE);

        dataLinearLayout = rootView.findViewById(R.id.informasi_strategis_indikator_linear_layout);
    }

    private void populateView() {
        rootProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, rootLayout, konfigurasi.animationDurationShort);
    }

    private void initiateSetOnClickMethod() {

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    /////////////////////////////
    //// pie chart jumlah PP ////
    /////////////////////////////

    private void initiatePieChartJumlahPP() {
        chartLayoutJumlahPP = rootView.findViewById(R.id.informasi_strategis_piechart_layout);
        chartViewJumlahPP = rootView.findViewById(R.id.informasi_strategis_piechart_chart);
        judulLayoutJumlahPP = rootView.findViewById(R.id.informasi_strategis_piechart_judul_layout);
        judulViewJumlahPP = rootView.findViewById(R.id.informasi_strategis_piechart_judul);
        chartProgressBarJumlahPP = rootView.findViewById(R.id.informasi_strategis_piechart_progress_bar);
        judulLayoutJumlahPP.setVisibility(View.GONE);
        chartLayoutJumlahPP.setVisibility(View.VISIBLE);
        chartViewJumlahPP.setVisibility(View.GONE);
        chartProgressBarJumlahPP.setVisibility(View.VISIBLE);

        // legend
        legendLayoutJumlahPP = rootView.findViewById(R.id.informasi_strategis_piechart_legend);
        legendLayoutJumlahPP.setVisibility(View.GONE);
        legendRecyclerView = (RecyclerView) rootView.findViewById(R.id.informasi_strategis_piechart_legend_recycler_view);
        legendRecyclerView.setHasFixedSize(true);
        legendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        jumlahPPLabel = new ArrayList<>();
        jumlahPPNilai = new ArrayList<>();
    }

    private void populateViewPieChartJumlahPP() {
        for (int i = 0; i < distribusiPKPTList.size(); i++) {
            String label =
                    distribusiPKPTList.get(i).getFokus()
                            + System.lineSeparator()
                            + distribusiPKPTList.get(i).getPersentase();
            jumlahPPLabel.add(label);
            jumlahPPNilai.add(distribusiPKPTList.get(i).getJumlah());
        }

        judulViewJumlahPP.setText(chartTitleJumlahPP);
        Graphs.createPieChart(getActivity(), jumlahPPNilai, jumlahPPLabel, chartViewJumlahPP);

        legendAdapter = new DistribusiPKPTAdapter(getActivity(), distribusiPKPTList, this);
        legendRecyclerView.setAdapter(legendAdapter);
        legendLayoutJumlahPP.setVisibility(View.VISIBLE);

        judulLayoutJumlahPP.setVisibility(View.VISIBLE);
        chartViewJumlahPP.setVisibility(View.VISIBLE);
        chartProgressBarJumlahPP.setVisibility(View.GONE);
    }

    private void parseJsonJumlahPP() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {
                JSONArray jsonArray = jsonObject.getJSONArray("fokuspengawasan");
                chartTitleJumlahPP = jsonObject.getString("judul");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String label = jo.getString("label");
                    String fokus = jo.getString("fokus");
                    String jumlah = jo.getString("jumlah");
                    String persentase = jo.getString("persentase");

                    distribusiPKPTList.add(
                            new DistribusiPKPT(
                                    i,
                                    fokus,
                                    label,
                                    jumlah,
                                    persentase
                            )
                    );
                }
                populateViewPieChartJumlahPP();
            } else {
                Toast.makeText(getActivity(), "gagal menarik data PP", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            PassingIntent.signOut(getActivity(), mUserToken, sharedPreferences);
            Toast.makeText(getActivity(), "gagal menarik data PP", Toast.LENGTH_SHORT).show();
        }
    }

    private void getJsonJumlahPP() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJsonJumlahPP();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_JUMLAHPP + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
