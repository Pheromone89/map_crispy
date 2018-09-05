package id.go.bpkp.mobilemapbpkp.informasistrategis;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.graphs.Graphs;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by ASUS on 09/02/2018.
 */

public class InformasiStrategisIndikatorFragment extends Fragment {

    private View
            rootView;
    private String
            JSON_STRING_JUMLAHPP,
            JSON_STRING_INDIKATOR_PEMDA,
            JSON_STRING_INDIKATOR_KL,
            mUserToken;
    private ScrollView rootLayout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private GifImageView rootProgressBar;
    private LinearLayout dataLinearLayout;
    private LayoutInflater panelLayouInflater;
    // general layout
    private CardView chartLayout;
    private PieChart chartView;
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
    private TextView
            fokus1View, fokus1JumlahView, fokus1LabelView, fokus1PersentaseView,
            fokus2View, fokus2JumlahView, fokus2LabelView, fokus2PersentaseView,
            fokus3View, fokus3JumlahView, fokus3LabelView, fokus3PersentaseView,
            fokus4View, fokus4JumlahView, fokus4LabelView, fokus4PersentaseView;
    // indikator Pemda
    private CardView chartLayoutIndikatorPemda;
    private PieChart chartViewIndikatorPemda;
    private CardView judulLayoutIndikatorPemda;
    private TextView judulViewIndikatorPemda;
    private GifImageView chartProgressBarIndikatorPemda;
    private String chartTitleIndikatorPemda;
    private PieChart chartView1, chartView2, chartView3;
    private ArrayList<String> kapipLabel, kapipNilai, spipLabel, spipNilai, opiniLabel, opiniNilai;
    // indikator KL
    private CardView chartLayoutIndikatorKL;
    private PieChart chartViewIndikatorKL;
    private CardView judulLayoutIndikatorKL;
    private TextView judulViewIndikatorKL;
    private GifImageView chartProgressBarIndikatorKL;
    private String chartTitleIndikatorKL;
    private PieChart chartViewKL1, chartViewKL2, chartViewKL3;
    private ArrayList<String> kapipKLLabel, kapipKLNilai, spipKLLabel, spipKLNilai, opiniKLLabel, opiniKLNilai;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informasi_strategis_indikator_distribusi_pkpt, null);
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

        // jumlah pp
        initiatePieChartJumlahPP();
        getJsonJumlahPP();
        // indikator pemda
        initiatePieChartIndikatorPemda();
        getJsonIndikatorPemda();
        // indikator kl
        initiatePieChartIndikatorKL();
        getJsonIndikatorKL();

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
        panelLayouInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        panelLayouInflater = LayoutInflater.from(getActivity());

        rootProgressBar.setVisibility(View.GONE);
        konfigurasi.fadeAnimation(true, rootLayout, konfigurasi.animationDurationShort);
    }

    private void initiateSetOnClickMethod() {

    }

    /////////////////////////////
    //// pie chart jumlah PP ////
    /////////////////////////////

    private void initiatePieChartJumlahPP() {
        final View v = panelLayouInflater.inflate(R.layout.i_informasi_strategis_pie_chart_jumlah_pp, null);
        dataLinearLayout.addView(v);

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
//        fokus1View = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_1_fokus);
//        fokus1LabelView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_1_label);
//        fokus1JumlahView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_1_jumlah);
//        fokus1PersentaseView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_1_persentase);
//        fokus2View = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_2_fokus);
//        fokus2LabelView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_2_label);
//        fokus2JumlahView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_2_jumlah);
//        fokus2PersentaseView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_2_persentase);
//        fokus3View = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_3_fokus);
//        fokus3LabelView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_3_label);
//        fokus3JumlahView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_3_jumlah);
//        fokus3PersentaseView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_3_persentase);
//        fokus4View = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_4_fokus);
//        fokus4LabelView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_4_label);
//        fokus4JumlahView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_4_jumlah);
//        fokus4PersentaseView = rootView.findViewById(R.id.informasi_strategis_piechart_fokus_4_persentase);

        jumlahPPLabel = new ArrayList<>();
        jumlahPPNilai = new ArrayList<>();
        jumlahPPPersentase = new ArrayList<>();
        jumlahPPFokus = new ArrayList<>();
    }

    private void populateViewPieChartJumlahPP() {
        judulViewJumlahPP.setText(chartTitleJumlahPP);
        Graphs.createPieChart(getActivity(), jumlahPPNilai, jumlahPPFokus, chartViewJumlahPP);

        // legend
        legendLayoutJumlahPP.setVisibility(View.VISIBLE);
        fokus1View.setText(jumlahPPFokus.get(0));
        fokus1LabelView.setText(jumlahPPLabel.get(0));
        fokus1JumlahView.setText(jumlahPPNilai.get(0));
        fokus1PersentaseView.setText(jumlahPPPersentase.get(0));
        fokus2View.setText(jumlahPPFokus.get(1));
        fokus2LabelView.setText(jumlahPPLabel.get(1));
        fokus2JumlahView.setText(jumlahPPNilai.get(1));
        fokus2PersentaseView.setText(jumlahPPPersentase.get(1));
        fokus3View.setText(jumlahPPFokus.get(2));
        fokus3LabelView.setText(jumlahPPLabel.get(2));
        fokus3JumlahView.setText(jumlahPPNilai.get(2));
        fokus3PersentaseView.setText(jumlahPPPersentase.get(2));
        fokus4View.setText(jumlahPPFokus.get(3));
        fokus4LabelView.setText(jumlahPPLabel.get(3));
        fokus4JumlahView.setText(jumlahPPNilai.get(3));
        fokus4PersentaseView.setText(jumlahPPPersentase.get(3));

        judulLayoutJumlahPP.setVisibility(View.VISIBLE);
        chartViewJumlahPP.setVisibility(View.VISIBLE);
        chartProgressBarJumlahPP.setVisibility(View.GONE);
    }

    private void parseJsonJumlahPP() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING_JUMLAHPP);
            if (jsonObject.getString("success").equals("true")) {
                JSONArray jsonArray = jsonObject.getJSONArray("fokuspengawasan");
                chartTitleJumlahPP = jsonObject.getString("judul");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String label = jo.getString("label");
                    String fokus = jo.getString("fokus");
                    String jumlah = jo.getString("jumlah");
                    String persentase = jo.getString("persentase");

                    jumlahPPLabel.add(label);
                    jumlahPPNilai.add(jumlah);
                    jumlahPPPersentase.add(persentase);
                    jumlahPPFokus.add(fokus);
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
                JSON_STRING_JUMLAHPP = s;
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

    ///////////////////////////////////
    //// pie chart indikator pemda ////
    ///////////////////////////////////

    private void initiatePieChartIndikatorPemda() {
        final View v = panelLayouInflater.inflate(R.layout.i_informasi_strategis_pie_chart_indikator_pemda, null);
        dataLinearLayout.addView(v);

        chartLayoutIndikatorPemda = rootView.findViewById(R.id.informasi_strategis_piechart_layout_3);
        chartView1 = rootView.findViewById(R.id.informasi_strategis_piechart_chart_1);
        chartView2 = rootView.findViewById(R.id.informasi_strategis_piechart_chart_2);
        chartView3 = rootView.findViewById(R.id.informasi_strategis_piechart_chart_3);
        judulLayoutIndikatorPemda = rootView.findViewById(R.id.informasi_strategis_piechart_judul_layout_3);
        judulViewIndikatorPemda = rootView.findViewById(R.id.informasi_strategis_piechart_judul_3);
        chartProgressBarIndikatorPemda = rootView.findViewById(R.id.informasi_strategis_piechart_progress_bar_3);
        judulLayoutIndikatorPemda.setVisibility(View.GONE);
        chartLayoutIndikatorPemda.setVisibility(View.VISIBLE);
        chartView1.setVisibility(View.GONE);
        chartView2.setVisibility(View.GONE);
        chartView3.setVisibility(View.GONE);
        chartProgressBarIndikatorPemda.setVisibility(View.VISIBLE);

        kapipLabel = new ArrayList<>();
        kapipNilai = new ArrayList<>();
        spipNilai = new ArrayList<>();
        spipLabel = new ArrayList<>();
        opiniLabel = new ArrayList<>();
        opiniNilai = new ArrayList<>();
    }

    private void populateViewPieChartIndikatorPemda() {
        judulViewIndikatorPemda.setText(chartTitleIndikatorPemda);
        Graphs.createPieChart(getActivity(), spipNilai, spipLabel, chartView1);
        Graphs.createPieChart(getActivity(), kapipNilai, kapipLabel, chartView2);
        Graphs.createPieChart(getActivity(), opiniNilai, opiniLabel, chartView3);

        judulLayoutIndikatorPemda.setVisibility(View.VISIBLE);
        chartView1.setVisibility(View.VISIBLE);
        chartView2.setVisibility(View.VISIBLE);
        chartView3.setVisibility(View.VISIBLE);

        chartProgressBarIndikatorPemda.setVisibility(View.GONE);
    }

    private void parseJsonIndikatorPemda() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING_INDIKATOR_PEMDA);
            if (jsonObject.getString("success").equals("true")) {
                chartTitleIndikatorPemda = jsonObject.getString("judul");

                JSONObject spip = jsonObject.getJSONArray("spip").getJSONObject(0);
                String spipLabel0 = spip.getString("label_0");
                String spipLabel1 = spip.getString("label_1");
                String spipLabel2 = spip.getString("label_2");
                String spipLabel3 = spip.getString("label_3");
                String spipNilai0 = spip.getString("status_0");
                String spipNilai1 = spip.getString("status_1");
                String spipNilai2 = spip.getString("status_2");
                String spipNilai3 = spip.getString("status_3");

                spipLabel.add(spipLabel0);
                spipLabel.add(spipLabel1);
                spipLabel.add(spipLabel2);
                spipLabel.add(spipLabel3);
                spipNilai.add(spipNilai0);
                spipNilai.add(spipNilai1);
                spipNilai.add(spipNilai2);
                spipNilai.add(spipNilai3);

                JSONObject kapip = jsonObject.getJSONArray("kapip").getJSONObject(0);
                String kapipLabel0 = kapip.getString("label_0");
                String kapipLabel1 = kapip.getString("label_1");
                String kapipLabel2 = kapip.getString("label_2");
                String kapipLabel3 = kapip.getString("label_3");
                String kapipNilai0 = kapip.getString("status_0");
                String kapipNilai1 = kapip.getString("status_1");
                String kapipNilai2 = kapip.getString("status_2");
                String kapipNilai3 = kapip.getString("status_3");

                kapipLabel.add(kapipLabel0);
                kapipLabel.add(kapipLabel1);
                kapipLabel.add(kapipLabel2);
                kapipLabel.add(kapipLabel3);
                kapipNilai.add(kapipNilai0);
                kapipNilai.add(kapipNilai1);
                kapipNilai.add(kapipNilai2);
                kapipNilai.add(kapipNilai3);

                JSONObject opini = jsonObject.getJSONArray("opini").getJSONObject(0);
                String opiniLabel0 = opini.getString("label_0");
                String opiniLabel1 = opini.getString("label_1");
                String opiniLabel2 = opini.getString("label_2");
                String opiniLabel3 = opini.getString("label_3");
                String opiniNilai0 = opini.getString("status_0");
                String opiniNilai1 = opini.getString("status_1");
                String opiniNilai2 = opini.getString("status_2");
                String opiniNilai3 = opini.getString("status_3");

                opiniLabel.add(opiniLabel0);
                opiniLabel.add(opiniLabel1);
                opiniLabel.add(opiniLabel2);
                opiniLabel.add(opiniLabel3);
                opiniNilai.add(opiniNilai0);
                opiniNilai.add(opiniNilai1);
                opiniNilai.add(opiniNilai2);
                opiniNilai.add(opiniNilai3);

                populateViewPieChartIndikatorPemda();
            } else {
                Toast.makeText(getActivity(), "gagal menarik data indikator pemda", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            PassingIntent.signOut(getActivity(), mUserToken, sharedPreferences);
            Toast.makeText(getActivity(), "gagal menarik data indikator pemda", Toast.LENGTH_SHORT).show();
        }
    }

    private void getJsonIndikatorPemda() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING_INDIKATOR_PEMDA = s;
                parseJsonIndikatorPemda();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_INDIKATORPEMDA + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    /////////////////////////////////
    //// pie chart indikator K/L ////
    /////////////////////////////////

    private void initiatePieChartIndikatorKL() {
        final View v = panelLayouInflater.inflate(R.layout.i_informasi_strategis_pie_chart_indikator_kl, null);
        dataLinearLayout.addView(v);

        chartLayoutIndikatorKL = rootView.findViewById(R.id.informasi_strategis_piechart_layout_3);
        chartViewKL1 = rootView.findViewById(R.id.informasi_strategis_piechart_chart_1);
        chartViewKL2 = rootView.findViewById(R.id.informasi_strategis_piechart_chart_2);
        chartViewKL3 = rootView.findViewById(R.id.informasi_strategis_piechart_chart_3);
        judulLayoutIndikatorKL = rootView.findViewById(R.id.informasi_strategis_piechart_judul_layout_3);
        judulViewIndikatorKL = rootView.findViewById(R.id.informasi_strategis_piechart_judul_3);
        chartProgressBarIndikatorKL = rootView.findViewById(R.id.informasi_strategis_piechart_progress_bar_3);
        judulLayoutIndikatorKL.setVisibility(View.GONE);
        chartLayoutIndikatorKL.setVisibility(View.VISIBLE);
        chartViewKL1.setVisibility(View.GONE);
        chartViewKL2.setVisibility(View.GONE);
        chartViewKL3.setVisibility(View.GONE);
        chartProgressBarIndikatorKL.setVisibility(View.VISIBLE);

        kapipKLLabel = new ArrayList<>();
        kapipKLNilai = new ArrayList<>();
        spipKLNilai = new ArrayList<>();
        spipKLLabel = new ArrayList<>();
        opiniKLLabel = new ArrayList<>();
        opiniKLNilai = new ArrayList<>();
    }

    private void populateViewPieChartIndikatorKL() {
        Toast.makeText(getActivity(), chartTitleIndikatorKL, Toast.LENGTH_SHORT).show();
        judulViewIndikatorKL.setText(chartTitleIndikatorKL);
        Graphs.createPieChart(getActivity(), spipKLNilai, spipKLLabel, chartViewKL1);
        Graphs.createPieChart(getActivity(), kapipKLNilai, kapipKLLabel, chartViewKL2);
        Graphs.createPieChart(getActivity(), opiniKLNilai, opiniKLLabel, chartViewKL3);

        judulLayoutIndikatorKL.setVisibility(View.VISIBLE);
        chartViewKL1.setVisibility(View.VISIBLE);
        chartViewKL2.setVisibility(View.VISIBLE);
        chartViewKL3.setVisibility(View.VISIBLE);

        chartProgressBarIndikatorKL.setVisibility(View.GONE);
    }

    private void parseJsonIndikatorKL() {
        JSONObject jsonObject = null;

        Toast.makeText(getActivity(), "reached here", Toast.LENGTH_SHORT).show();
        try {
            jsonObject = new JSONObject(JSON_STRING_INDIKATOR_KL);
            if (jsonObject.getString("success").equals("true")) {
                chartTitleIndikatorKL = jsonObject.getString("judul");


                JSONObject spip = jsonObject.getJSONObject("spip");
                JSONArray spipArray = spip.getJSONArray("data");

                for (int i = 0; i < spipArray.length(); i++) {
                    JSONObject jo = spipArray.getJSONObject(i);
                    String label = jo.getString("label");
                    String status = jo.getString("status");
                    spipKLLabel.add(label);
                    spipKLNilai.add(status);
                }

                Toast.makeText(getActivity(), "reached SPIP", Toast.LENGTH_SHORT).show();

                JSONObject kapip = jsonObject.getJSONObject("kapip");
                JSONArray kapipArray = kapip.getJSONArray("data");

                for (int i = 0; i < kapipArray.length(); i++) {
                    JSONObject jo = kapipArray.getJSONObject(i);
                    String label = jo.getString("label");
                    String status = jo.getString("status");
                    kapipKLLabel.add(label);
                    kapipKLNilai.add(status);
                }

                Toast.makeText(getActivity(), "reached KAPIP", Toast.LENGTH_SHORT).show();

                JSONObject opini = jsonObject.getJSONObject("opini");
                JSONArray opiniArray = opini.getJSONArray("data");

                for (int i = 0; i < opiniArray.length(); i++) {
                    JSONObject jo = opiniArray.getJSONObject(i);
                    String label = jo.getString("label");
                    String status = jo.getString("status");
                    opiniKLLabel.add(label);
                    opiniKLNilai.add(status);
                }

                Toast.makeText(getActivity(), "reached OPEENEE", Toast.LENGTH_SHORT).show();

                populateViewPieChartIndikatorKL();
            } else {
                Toast.makeText(getActivity(), "gagal menarik data indikator KL", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            PassingIntent.signOut(getActivity(), mUserToken, sharedPreferences);
            Toast.makeText(getActivity(), "gagal menarik data indikator KL", Toast.LENGTH_SHORT).show();
        }
    }

    private void getJsonIndikatorKL() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING_INDIKATOR_KL = s;
                parseJsonIndikatorKL();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_INDIKATORKL + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}