package id.go.bpkp.mobilemapbpkp.dashboard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import id.go.bpkp.mobilemapbpkp.monitoring.ProfilSeluruhPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_DASHBOARDCONTENT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_EMAIL;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FOTO;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPBARU;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NIPLAMA;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_NOHP;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_ROLEIDINT;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG;
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_USERTOKEN;

/**
 * Created by ASUS on 22/01/2018.
 */

public class DashboardAdminFragment extends Fragment {

    private View
            rootView;
    private TextView
            jumlahStrukturalView,
            jumlahKorwasView,
            jumlahAuditorView,
            jumlahJftLainView,
            jumlahJfUmumView,
            jumlahDpilView,
            jumlahTotalPegawaiView;
    private String
            mFoto,
            mNipLama,
            mNipBaru,
            username,
            JSON_STRING,
            mUserToken,
            mDashboardContent,
            jumlahStruktural,
            jumlahKorwas,
            jumlahAuditor,
            jumlahJftLain,
            jumlahJfUmum,
            jumlahDpil,
            jumlahTotalPegawai;
    private int
            mRoleId;
    private ArrayList<String>
            strataLabelArrayList,
            strataDataArrayList,
            jenisKelaminLabelArrayList,
            jenisKelaminDataArrayList,
            usiaLabelArrayList,
            usiaDataArrayList,
            golonganLabelArrayList,
            golonganDataArrayList,
            jfaLabelArrayList,
            jfaDataArrayList,
            jabatanLabelArrayList,
            jabatanDataArrayList;
    private PieChart
            strataChartView,
            jenisKelaminChartView;
    private BarChart
            usiaChartView,
            golonganChartView,
            jfaChartView,
            jabatanChartView;
    private CardView
            jumlahTotalPegawaiCardView;

    public DashboardAdminFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_admin, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_dashboard_admin);

        //bundle dari fragment sebelumnya
        //URL foto
        mFoto = this.getArguments().getString(INTENT_FOTO);
        //login token
        mUserToken = this.getArguments().getString(INTENT_USERTOKEN);
        //nip tanpa spasi
        mNipBaru = this.getArguments().getString(INTENT_NIPBARU);
        //nip lama tanpa spasi
        mNipLama = this.getArguments().getString(INTENT_NIPLAMA);
        //content url
        mDashboardContent = this.getArguments().getString(INTENT_DASHBOARDCONTENT);
        //role id
        mRoleId = this.getArguments().getInt(INTENT_ROLEIDINT);

        initiatePanelView();
        getJSON();
        initiateSetOnClickMethod();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_dashboard_admin);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(getActivity(),"Mengisi Beranda","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(mDashboardContent + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showEmployee() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            // 6 panel
            jumlahStruktural = jsonObject.getJSONObject("umum").getString("count_struk");
            jumlahKorwas = jsonObject.getJSONObject("umum").getString("count_korwas");
            jumlahAuditor = jsonObject.getJSONObject("umum").getString("count_jfa");
            jumlahJftLain = jsonObject.getJSONObject("umum").getString("count_jft");
            jumlahJfUmum = jsonObject.getJSONObject("umum").getString("count_jafum");
            jumlahDpil = jsonObject.getJSONObject("umum").getString("count_dpil");
            // mid panel
            jumlahTotalPegawai = jsonObject.getJSONObject("umum").getString("total");
            populatePanelView();
            // bikin chart strata
            JSONArray strata = jsonObject.getJSONArray("strata");
            strataDataArrayList = new ArrayList<String>();
            strataLabelArrayList = new ArrayList<String>();
            for (int i = 0; i < strata.length(); i++) {
                JSONObject jo = strata.getJSONObject(i);
                strataDataArrayList.add(jo.getString("jum"));
                strataLabelArrayList.add(jo.getString("s_nama_strata_skt"));
            }
            createPieChart(strataDataArrayList, strataLabelArrayList, strataChartView);
            // bikin chart jenis kelamin
            JSONArray jenisKelamin = jsonObject.getJSONArray("jenis_kelamin");
            jenisKelaminDataArrayList = new ArrayList<String>();
            jenisKelaminLabelArrayList = new ArrayList<String>();
            for (int i = 0; i < jenisKelamin.length(); i++) {
                JSONObject jo = jenisKelamin.getJSONObject(i);
                jenisKelaminDataArrayList.add(jo.getString("jum"));
                jenisKelaminLabelArrayList.add(jo.getString("jenis_kelamin"));
            }
            createPieChart(jenisKelaminDataArrayList, jenisKelaminLabelArrayList, jenisKelaminChartView);
            // bikin chart usia
            JSONArray usia = jsonObject.getJSONArray("usia");
            usiaDataArrayList = new ArrayList<String>();
            usiaLabelArrayList = new ArrayList<String>();
            for (int i = 0; i < usia.length(); i++) {
                JSONObject jo = usia.getJSONObject(i);
                usiaDataArrayList.add(jo.getString("jum"));
                usiaLabelArrayList.add(jo.getString("usia"));
            }
            createBarChart(usiaDataArrayList, usiaLabelArrayList, usiaChartView, "Usia");
            // bikin chart gol
            JSONArray gol = jsonObject.getJSONArray("golongan");
            golonganDataArrayList = new ArrayList<String>();
            golonganLabelArrayList = new ArrayList<String>();
            for (int i = 0; i < gol.length(); i++) {
                JSONObject jo = gol.getJSONObject(i);
                golonganDataArrayList.add(jo.getString("jum"));
                golonganLabelArrayList.add(jo.getString("golruang"));
            }
            createBarChart(golonganDataArrayList, golonganLabelArrayList, golonganChartView, "Golongan");
            // bikin chart detil jfa
            JSONArray jfa = jsonObject.getJSONArray("jfa_chart");
            jfaDataArrayList = new ArrayList<String>();
            jfaLabelArrayList = new ArrayList<String>();
            for (int i = 0; i < jfa.length(); i++) {
                JSONObject jo = jfa.getJSONObject(i);
                jfaDataArrayList.add(jo.getString("jum"));
                jfaLabelArrayList.add(jo.getString("jab"));
            }
            createBarChart(jfaDataArrayList, jfaLabelArrayList, jfaChartView, "JFA");
            // bikin chart jabatan
            JSONArray jab = jsonObject.getJSONArray("jab_chart");
            jabatanDataArrayList = new ArrayList<String>();
            jabatanLabelArrayList = new ArrayList<String>();
            for (int i = 0; i < jab.length(); i++) {
                JSONObject jo = jab.getJSONObject(i);
                jabatanDataArrayList.add(jo.getString("jum"));
                jabatanLabelArrayList.add(jo.getString("jab"));
            }
            createBarChart(jabatanDataArrayList, jabatanLabelArrayList, jabatanChartView, "Jabatan");

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "JSONException", Toast.LENGTH_SHORT).show();
        }
    }

    private void createPieChart(ArrayList<String> stringDataArray, ArrayList<String> stringLabelArray, PieChart chartView) {
        chartView.setUsePercentValues(true);
        chartView.getDescription().setEnabled(false);
        chartView.setExtraOffsets(5, 10, 5, 5);

        chartView.setDragDecelerationFrictionCoef(0.95f);

        chartView.setDrawHoleEnabled(true);
        chartView.setHoleColor(getResources().getColor(R.color.transparent));

        chartView.setTransparentCircleColor(Color.WHITE);
        chartView.setTransparentCircleAlpha(110);

        chartView.setHoleRadius(58f);
        chartView.setTransparentCircleRadius(61f);

        chartView.setDrawCenterText(true);

        chartView.setRotationAngle(0);
        chartView.setRotationEnabled(true);
        chartView.setHighlightPerTapEnabled(true);

        setPieData(
                stringDataArray.size(),
                stringDataArray,
                stringLabelArray,
                chartView);

        chartView.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // chartView.spin(2000, 0, 360);

        Legend l = chartView.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        chartView.setEntryLabelColor(Color.BLACK);
        chartView.setEntryLabelTextSize(12f);
    }

    private void setPieData(int count, ArrayList<String> stringDataArray, ArrayList stringLabelArray, PieChart chartView) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry(
                            (float) Integer.parseInt(stringDataArray.get(i)),
                            stringLabelArray.get(i).toString().toUpperCase()
                    )
            );
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(0f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            colors.add(Color.argb(
                    255,
                    rnd.nextInt(256),
                    256 - (rnd.nextInt(200)),
                    256 - (rnd.nextInt(200))));
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface();
        chartView.setData(data);

        // undo all highlights
        chartView.highlightValues(null);

        chartView.invalidate();
    }

    private void createBarChart(ArrayList<String> stringDataArray, ArrayList<String> stringLabelArray, BarChart chartView, String title) {
//        chartView.setOnChartValueSelectedListener(this);

        chartView.setDrawBarShadow(false);
        chartView.setDrawValueAboveBar(true);

        chartView.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chartView.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chartView.setPinchZoom(false);

        chartView.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);
//
//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chartView);

        XAxis xAxis = chartView.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        LabelAxisValueFormatter labelFormatter = new LabelAxisValueFormatter(stringLabelArray);
//        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(stringDataArray.size());
        xAxis.setValueFormatter(labelFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = chartView.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chartView.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.isLegendCustom();
        l.setEnabled(false);

        XYMarkerView mv = new XYMarkerView(getActivity(), custom, labelFormatter, title);
        mv.setChartView(chartView); // For bounds control
        chartView.setMarker(mv); // Set the marker to the chart

        chartView.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        setBarData(stringDataArray.size(), stringDataArray, chartView);
    }

    private void setBarData(int count, ArrayList<String> stringDataArray, BarChart chartView) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(
                            (float) i,
                            (float) Integer.parseInt(stringDataArray.get(i))
                    )
            );
        }

        BarDataSet set1;

        if (chartView.getData() != null && chartView.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chartView.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            chartView.getData().notifyDataChanged();
            chartView.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10);
            IValueFormatter valueFormatter = new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    String valueStr = (String) Integer.toString((int) value);
                    return valueStr;
                }
            };
            data.setValueFormatter(valueFormatter);
            data.setBarWidth(0.9f);
            chartView.setData(data);
        }
    }

    private void initiatePanelView() {
        // 6 panel
        jumlahStrukturalView = (TextView) rootView.findViewById(R.id.panel_jumlah_struktural);
        jumlahKorwasView = (TextView) rootView.findViewById(R.id.panel_jumlah_korwas);
        jumlahAuditorView = (TextView) rootView.findViewById(R.id.panel_jumlah_auditor);
        jumlahJftLainView = (TextView) rootView.findViewById(R.id.panel_jumlah_jft_lain);
        jumlahJfUmumView = (TextView) rootView.findViewById(R.id.panel_jumlah_jf_umum);
        jumlahDpilView = (TextView) rootView.findViewById(R.id.panel_jumlah_dpil);
        // mid panel
        jumlahTotalPegawaiCardView = (CardView) rootView.findViewById(R.id.card_jumlah_total_pegawai);
        jumlahTotalPegawaiView = (TextView) rootView.findViewById(R.id.panel_jumlah_total_pegawai);
        // PieChart
        strataChartView = (PieChart) rootView.findViewById(R.id.strata_chart);
        jenisKelaminChartView = (PieChart) rootView.findViewById(R.id.jenis_kelamin_chart);
        usiaChartView = (BarChart) rootView.findViewById(R.id.usia_chart);
        golonganChartView = (BarChart) rootView.findViewById(R.id.golongan_chart);
        jfaChartView = (BarChart) rootView.findViewById(R.id.jfa_chart);
        jabatanChartView = (BarChart) rootView.findViewById(R.id.jabatan_chart);
    }

    private void populatePanelView() {
        // 6 panel
        jumlahStrukturalView.setText(jumlahStruktural);
        jumlahKorwasView.setText(jumlahKorwas);
        jumlahAuditorView.setText(jumlahAuditor);
        jumlahJftLainView.setText(jumlahJftLain);
        jumlahJfUmumView.setText(jumlahJfUmum);
        jumlahDpilView.setText(jumlahDpil);
        // mid panel
        jumlahTotalPegawaiView.setText(jumlahTotalPegawai);
    }

    private void initiateSetOnClickMethod() {
        jumlahTotalPegawaiCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url;
                Fragment fragment = null;
                if (mRoleId == 1 || mRoleId == 2) {
                    url = konfigurasi.URL_GET_ALLSMALL;
                } else if (mRoleId == 3) {
                    url = konfigurasi.URL_GET_ALLBYUNIT + mNipLama + "?api_token=";
                } else {
                    url = konfigurasi.URL_GET_ALLSMALL;
                }
                Bundle bundle = new Bundle();
                bundle.putString("user_token", mUserToken);
                bundle.putString("fragment_url", url);
                bundle.putString("nip_lama", mNipLama);
                bundle.putInt("role_id", mRoleId);

                fragment = new ProfilSeluruhPegawaiFragment();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_fragment_area, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
