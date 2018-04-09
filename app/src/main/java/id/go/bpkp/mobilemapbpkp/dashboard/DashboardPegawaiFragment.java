package id.go.bpkp.mobilemapbpkp.dashboard;

import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 26/01/2018.
 */

public class DashboardPegawaiFragment extends Fragment {

    private View rootView;
    private TextView
            jamDatangView,
            jamPulangView,
            hariTanggalView,
            statusMessageView,
            statusDatangView,
            statusPulangView;
    private ImageView
            statusIcon;
    private CardView
            datangCardView,
            pulangCardView,
            statusCardView;
    private String
            JSON_STRING;
    private String
            mNama,
            mNipBaru,
            mNipLama,
            mUserToken,
            mFoto,
            mContentUrl;
    private String
            jamDatang,
            jamPulang,
            statusMessage,
            hariTanggal,
            statusDatang,
            statusPulang,
            statusAbsen;
    private ArrayList<StatusKehadiran>
            statusMessageArrayList;
    private int
            mRoleId,
            skenarioDatang,
            skenarioPulang;
    private SimpleDateFormat
            format;
    private StatusKehadiran
            statusKehadiran;

    public DashboardPegawaiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_pegawai, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_dashboard_pegawai);

        //bundle dari fragment sebelumnya
        //URL foto
        mFoto = this.getArguments().getString(PassedIntent.INTENT_FOTO);
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        //nip tanpa spasi
        mNipBaru = this.getArguments().getString(PassedIntent.INTENT_NIPBARU);
        //nip lama tanpa spasi
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        //content url
        mContentUrl = konfigurasi.URL_GET_ABSEN;
        //role id
        mRoleId = this.getArguments().getInt(PassedIntent.INTENT_ROLEIDINT);

        format = new SimpleDateFormat("HH:mm:ss");

        initiateView();
        initiateStatusKehadiran();
        getJSON();
    }

    private void initiateStatusKehadiran() {
        String[] statusMessageArray = getResources().getStringArray(R.array.absensi_status_message);
        // skenario 0, sebelum jam 8, pegawai belum absen
        statusMessageArrayList = new ArrayList<>();
        statusMessageArrayList.add(new StatusKehadiran(
                statusMessageArray[0],
                getResources().getColor(R.color.green)
        ));
        // skenario 1, pegawai absen sebelum jam 8
        statusMessageArrayList.add(new StatusKehadiran(
                statusMessageArray[3],
                getResources().getColor(R.color.green)
        ));
        // skenario 2, lewat jam 8, pegawai blm absen
        statusMessageArrayList.add(new StatusKehadiran(
                statusMessageArray[1],
                getResources().getColor(R.color.orange)
        ));
        // skenario 3, lewat jam 8, pegawai absen >jam 8
        statusMessageArrayList.add(new StatusKehadiran(
                statusMessageArray[2],
                getResources().getColor(R.color.red)
        ));
        // skenario 4, belum jam pulang, pegawai blm absen
        statusMessageArrayList.add(new StatusKehadiran(
                statusMessageArray[4],
                getResources().getColor(R.color.green)
        ));
        // skenario 5, lewat jam pulang, pegawai blm absen
        statusMessageArrayList.add(new StatusKehadiran(
                statusMessageArray[5],
                getResources().getColor(R.color.orange)
        ));
        // skenario 6, belum jam pulang, pegawai sudah absen
        statusMessageArrayList.add(new StatusKehadiran(
                statusMessageArray[7],
                getResources().getColor(R.color.red)
        ));
        // skenario 7, lewat jam pulang, pegawai sudah absen
        statusMessageArrayList.add(new StatusKehadiran(
                statusMessageArray[6],
                getResources().getColor(R.color.green)
        ));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_dashboard_pegawai);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getSkenario() {
        // get current time
        Date waktuSekarang = Calendar.getInstance().getTime();
        int hari = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        // minggu = 1
        try {
            Date datangTimestamp = format.parse(jamDatang);
            Date pulangTimestamp = format.parse(jamPulang);

            long jamDatangPegawai = datangTimestamp.getTime();
            long jamPulangTimestamp = pulangTimestamp.getTime();
            long batasJamDatang = 28800000;
            long batasJamPulang;
            if (hari == 6) {
                batasJamPulang = 61200000;
            } else {
                batasJamPulang = 59400000;
            }
//            // masukkin list skenario di sini sama situationalnya
//            if ( waktuSekarang.getTime() <= batasJamDatang && jamDatangPegawai == /*hari ini 00:00*/) {
//
//            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        skenarioDatang = ;
//        skenarioPulang = ;
    }

    private void initiateView() {
        jamDatangView = rootView.findViewById(R.id.dashboard_pegawai_jam_datang);
        jamPulangView = rootView.findViewById(R.id.dashboard_pegawai_jam_pulang);
        statusDatangView = rootView.findViewById(R.id.dashboard_pegawai_status_kedatangan);
        statusPulangView = rootView.findViewById(R.id.dashboard_pegawai_status_kepulangan);
        hariTanggalView = rootView.findViewById(R.id.dashboard_pegawai_hari_tanggal);
        statusMessageView = rootView.findViewById(R.id.dashboard_pegawai_status_message);

        statusIcon = rootView.findViewById(R.id.dashboard_pegawai_status_icon);

        datangCardView = rootView.findViewById(R.id.dashboard_pegawai_datang_card);
        pulangCardView = rootView.findViewById(R.id.dashboard_pegawai_pulang_card);
        statusCardView = rootView.findViewById(R.id.dashboard_pegawai_status_card);
    }

    private void populateView() {
        jamDatangView.setText(jamDatang);
        jamPulangView.setText(jamPulang);

        statusDatangView.setText(statusDatang);
        statusPulangView.setText(statusPulang);

        statusMessageView.setText(statusAbsen);

        hariTanggalView.setText(hariTanggal);

        // skenario 0, sebelum jam 8, pegawai belum absen
        // skenario 1, pegawai absen sebelum jam 8
        // skenario 2, lewat jam 8, pegawai blm absen
        // skenario 3, lewat jam 8, pegawai absen >jam 8
        // skenario 4, belum jam pulang, pegawai blm absen
        // skenario 5, lewat jam pulang, pegawai blm absen
        // skenario 6, belum jam pulang, pegawai sudah absen
        // skenario 7, lewat jam pulang, pegawai sudah absen

//        getSkenario();
        switch (skenarioDatang) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

        switch (skenarioPulang) {
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(getActivity(),null,"Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                JSON_STRING = s;
                parseJSON();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(mContentUrl + mNipLama + "?api_token=" + mUserToken);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void parseJSON() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            jsonObject = jsonObject.getJSONObject(konfigurasi.TAG_JSON_ARRAY);
            jamDatang = jsonObject.getString(konfigurasi.TAG_ABSEN_DATANG);
            jamPulang = jsonObject.getString(konfigurasi.TAG_ABSEN_PULANG);
            hariTanggal = jsonObject.getString(konfigurasi.TAG_ABSEN_TANGGAL);
            statusDatang = jsonObject.getString(konfigurasi.TAG_ABSEN_STATUSDATANG);
            statusPulang = jsonObject.getString(konfigurasi.TAG_ABSEN_STATUSPULANG);
            statusAbsen = jsonObject.getString(konfigurasi.TAG_ABSEN_WAKTUKERJA);

            //formatting
//            Date datangTimestamp = format.parse(jamDatang);
//            Date pulangTimestamp = format.parse(jamPulang);
//            Date hariTanggalTimestamp = format.parse(hariTanggal);

//            jamDatang = getDate(datangTimestamp.getTime(), "HH:mm:ss");
//            jamPulang = getDate(pulangTimestamp.getTime(), "HH:mm:ss");
//            hariTanggal = getDate(hariTanggalTimestamp.getTime(), "EEE, dd MMMM yyyy");
//            statusMessage = "data belum tersedia";
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "JSONException", Toast.LENGTH_SHORT).show();
        }
        populateView();
    }

    private class StatusKehadiran {
        String status;
        int colorCode;

        public StatusKehadiran(String status, int colorCode) {
            this.status = status;
            this.colorCode = colorCode;
        }

        public String getStatus() {
            return status;
        }

        public int getColorCode() {
            return colorCode;
        }
    }
//    private String checkNull (String string){
//        if (string.equals("null")){
//            return "-";
//        } else {
//            return string;
//        }
//    }
}
