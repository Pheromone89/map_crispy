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
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.graphs.Graphs;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by ASUS on 11/02/2018.
 */

public class IndikatorFragment extends Fragment implements RecyclerViewClickListener {

    private View
            rootView;
    private String
            JSON_STRING;
    private String
            mUserToken;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // indikator pemda
    private ScrollView chartLayout;
    private PieChart chartView1, chartView2, chartView3;
    private LinearLayout legendLayout1, legendLayout2, legendLayout3;
    private CardView judulLayout1, judulLayout2, judulLayout3;
    private TextView judulView1, judulView2, judulView3;
    private GifImageView chartProgressBar;
    private String chartTitle1, chartTitle2, chartTitle3;
    private ArrayList<String>
            indikatorSPIPLabel,
            indikatorSPIPNilai,
            indikatorKAPIPLabel,
            indikatorKAPIPNilai,
            indikatorOPINILabel,
            indikatorOPININilai;
    private ScrollView rootLayout;
    private GifImageView rootProgressBar;
    private LinearLayout dataLinearLayout;
    private LayoutInflater panelLayouInflater;
    private List<Common> spipList, kapipList, opiniList;
    private RecyclerView legend1RecyclerView, legend2RecyclerView, legend3RecyclerView;
    private CommonAdapter
            legend1Adapter, legend2Adapter, legend3Adapter;
    private String mUrl;

    public IndikatorFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informasi_strategis_indikator, null);
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
        mUrl = this.getArguments().getString(PassedIntent.INTENT_FRAGMENTCONTENT);

        spipList = new ArrayList<>();
        kapipList = new ArrayList<>();
        opiniList = new ArrayList<>();

        initiateView();
        populateView();

        // jumlah pp
        initiatePieChartIndikator();
        getJsonIndikator();
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
    //// pie chart indikator ////
    /////////////////////////////

    private void initiatePieChartIndikator() {
        chartLayout = rootView.findViewById(R.id.informasi_strategis_indikator_layout);
        chartView1 = rootView.findViewById(R.id.informasi_strategis_piechart_chart1);
        chartView2 = rootView.findViewById(R.id.informasi_strategis_piechart_chart2);
        chartView3 = rootView.findViewById(R.id.informasi_strategis_piechart_chart3);
        judulLayout1 = rootView.findViewById(R.id.informasi_strategis_piechart1_judul_layout);
        judulLayout2 = rootView.findViewById(R.id.informasi_strategis_piechart2_judul_layout);
        judulLayout3 = rootView.findViewById(R.id.informasi_strategis_piechart3_judul_layout);
        judulView1 = rootView.findViewById(R.id.informasi_strategis_piechart1_judul);
        judulView2 = rootView.findViewById(R.id.informasi_strategis_piechart2_judul);
        judulView3 = rootView.findViewById(R.id.informasi_strategis_piechart3_judul);
        chartProgressBar = rootView.findViewById(R.id.informasi_strategis_piechart_progress_bar);
        judulLayout1.setVisibility(View.GONE);
        judulLayout2.setVisibility(View.GONE);
        judulLayout3.setVisibility(View.GONE);
        chartLayout.setVisibility(View.VISIBLE);
        chartView1.setVisibility(View.GONE);
        chartView2.setVisibility(View.GONE);
        chartView3.setVisibility(View.GONE);
        chartProgressBar.setVisibility(View.VISIBLE);

//         legend
        legendLayout1 = rootView.findViewById(R.id.informasi_strategis_piechart_legend1);
        legendLayout1.setVisibility(View.GONE);
        legendLayout2 = rootView.findViewById(R.id.informasi_strategis_piechart_legend2);
        legendLayout2.setVisibility(View.GONE);
        legendLayout3 = rootView.findViewById(R.id.informasi_strategis_piechart_legend3);
        legendLayout3.setVisibility(View.GONE);
        legend1RecyclerView = (RecyclerView) rootView.findViewById(R.id.informasi_strategis_piechart_legend1_recycler_view);
        legend2RecyclerView = (RecyclerView) rootView.findViewById(R.id.informasi_strategis_piechart_legend2_recycler_view);
        legend3RecyclerView = (RecyclerView) rootView.findViewById(R.id.informasi_strategis_piechart_legend3_recycler_view);
        legend1RecyclerView.setHasFixedSize(true);
        legend1RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        legend2RecyclerView.setHasFixedSize(true);
        legend2RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        legend3RecyclerView.setHasFixedSize(true);
        legend3RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        indikatorSPIPLabel = new ArrayList<>();
        indikatorSPIPNilai = new ArrayList<>();
        indikatorKAPIPLabel = new ArrayList<>();
        indikatorKAPIPNilai = new ArrayList<>();
        indikatorOPINILabel = new ArrayList<>();
        indikatorOPININilai = new ArrayList<>();
    }

    private void populateViewPieChartIndikatorPemda() {
        Graphs.createPieChart(getActivity(), indikatorSPIPNilai, indikatorSPIPLabel, chartView1);
        Graphs.createPieChart(getActivity(), indikatorKAPIPNilai, indikatorKAPIPLabel, chartView2);
        Graphs.createPieChart(getActivity(), indikatorOPININilai, indikatorOPINILabel, chartView3);

        legend1Adapter = new CommonAdapter(getActivity(), spipList, this);
        legend1RecyclerView.setAdapter(legend1Adapter);
        legendLayout1.setVisibility(View.VISIBLE);

        legend2Adapter = new CommonAdapter(getActivity(), kapipList, this);
        legend2RecyclerView.setAdapter(legend2Adapter);
        legendLayout2.setVisibility(View.VISIBLE);

        legend3Adapter = new CommonAdapter(getActivity(), opiniList, this);
        legend3RecyclerView.setAdapter(legend3Adapter);
        legendLayout3.setVisibility(View.VISIBLE);

        judulLayout1.setVisibility(View.VISIBLE);
        judulLayout2.setVisibility(View.VISIBLE);
        judulLayout3.setVisibility(View.VISIBLE);
        chartView1.setVisibility(View.VISIBLE);
        chartView2.setVisibility(View.VISIBLE);
        chartView3.setVisibility(View.VISIBLE);
        chartProgressBar.setVisibility(View.GONE);
    }

    private void parseJsonIndikatorPemda() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            if (jsonObject.getString("success").equals("true")) {

                putData(jsonObject, "spip", judulView1, spipList, indikatorSPIPLabel, indikatorSPIPNilai);
                putData(jsonObject, "kapip", judulView2, kapipList, indikatorKAPIPLabel, indikatorKAPIPNilai);
                putData(jsonObject, "opini", judulView3, opiniList, indikatorOPINILabel, indikatorOPININilai);

                populateViewPieChartIndikatorPemda();
            } else {
                Toast.makeText(getActivity(), "gagal menarik data indikator", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            PassingIntent.signOut(getActivity(), mUserToken, sharedPreferences);
            Toast.makeText(getActivity(), "gagal menarik data indikator", Toast.LENGTH_SHORT).show();
        }
    }

    private void getJsonIndikator() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                parseJsonIndikatorPemda();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(mUrl + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void putData(JSONObject jsonObject, String jsonTag, TextView titleView, List<Common> commonList, ArrayList<String> labelList, ArrayList<String> nilaiList) {
        JSONArray array = null;
        String judul, tahun;
        try {
            judul = jsonObject.getJSONObject(jsonTag).getString("label");
            tahun = jsonObject.getJSONObject(jsonTag).getString("tahun");
            String title = judul + " - " + tahun;
            Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
            array = jsonObject.getJSONObject(jsonTag).getJSONArray("data");
            titleView.setText(title);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jo = array.getJSONObject(i);
                String label = jo.getString("label");
                String nilai = jo.getString("status");
                commonList.add(new Common(i, label, nilai));
                labelList.add(label);
                nilaiList.add(nilai);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            PassingIntent.signOut(getActivity(), mUserToken, sharedPreferences);
            Toast.makeText(getActivity(), "gagal menarik data indikator", Toast.LENGTH_SHORT).show();
        }
    }
}