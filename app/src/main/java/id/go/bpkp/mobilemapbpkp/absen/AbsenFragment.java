package id.go.bpkp.mobilemapbpkp.absen;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RequestHandler;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDaftarCutiFragment;
import id.go.bpkp.mobilemapbpkp.cuti.CutiPengajuanPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 11/04/2018.
 */

public class AbsenFragment extends Fragment {


    protected static final float LOCSOUTH = -6.19225f;
    protected static final float LOCEAST = 106.87157f;
    protected static final float LOCNORTH = -6.19290f;
    protected static final float LOCWEST = 106.87018f;
    // 1^0 dalam kilometer
    protected static final float KONSTAN = 111322f;

    boolean coarseLoc, fineLoc, isLatitudeOnPosition, isLongitudeOnPosition;
    float userLat, userLong, devlatfloat, devlongfloat;
    Button refreshButton;

    private View
            rootView;
    private String
            JSON_STRING,
            mUserToken,
            mNipLama,
            mNipBaru,
            mNama,
            mFoto,
            mAtasanLangsung,
            mNipAtasanLangsung;
    private int
            mRoleIdInt;
    private ImageView
            proficView,
            fingerIcon;
    private TextView
            namaView,
            nipView,
            jabatanView,
            latitudeView,
            longitudeView, east, west, north, south;
    private CardView
            absenButton, warningView;
    private boolean
            tidakPunyaAtasanLangsung;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_absen, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView = view;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_absen);

        //bundle dari fragment sebelumnya
        //URL foto
        mFoto = this.getArguments().getString(PassedIntent.INTENT_FOTO);
        //login token
        mUserToken = this.getArguments().getString(PassedIntent.INTENT_USERTOKEN);
        //nip lama UNTUK PEGAWAI, BUKAN ADMIN
        mNipLama = this.getArguments().getString(PassedIntent.INTENT_NIPLAMA);
        //nip baru
        mNipBaru = this.getArguments().getString(PassedIntent.INTENT_NIPBARU);
        //nama
        mNama = this.getArguments().getString(PassedIntent.INTENT_NAMA);
        //role id
//        mRoleIdInt = this.getArguments().getInt("role_id");
        // bool atasan
        tidakPunyaAtasanLangsung = this.getArguments().getBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG);
        // atasan langsung
        mAtasanLangsung = this.getArguments().getString(PassedIntent.INTENT_NAMAATASANLANGSUNG);
        // nip atasan langsung
        mNipAtasanLangsung = this.getArguments().getString(PassedIntent.INTENT_NIPATASANLANGSUNG);

        initiateView();
        populateView();
        // harus terakhir
        getLatitudeLongitude();
        initiateSetOnClickMethod();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLatitudeLongitude();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_absen);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initiateView() {
        // profic
        proficView = (ImageView) rootView.findViewById(R.id.dashboard_cuti_profic);
        namaView = (TextView) rootView.findViewById(R.id.dashboard_cuti_nama);
        nipView = (TextView) rootView.findViewById(R.id.dashboard_cuti_nip);
        jabatanView = (TextView) rootView.findViewById(R.id.dashboard_cuti_jabatan);
        // data
        absenButton = (CardView) rootView.findViewById(R.id.absen_button);
        fingerIcon = rootView.findViewById(R.id.absen_finger_icon);
        warningView = (CardView) rootView.findViewById(R.id.absen_warning);
        // debug
        latitudeView = rootView.findViewById(R.id.tvlat);
        longitudeView = rootView.findViewById(R.id.tvlong);
        west = rootView.findViewById(R.id.west);
        west.setText(Float.toString(LOCWEST));
        east = rootView.findViewById(R.id.east);
        east.setText(Float.toString(LOCEAST));
        north = rootView.findViewById(R.id.north);
        north.setText(Float.toString(LOCNORTH));
        south = rootView.findViewById(R.id.south);
        south.setText(Float.toString(LOCSOUTH));

        refreshButton = rootView.findViewById(R.id.absen_refresh);
    }

    private void populateView() {
        // profic
        Picasso
                .with(getActivity())
                .load(mFoto)
                .into(proficView);
        namaView.setText(mNama);
        nipView.setText(mNipBaru);
        jabatanView.setVisibility(View.GONE);
    }

    private void initiateSetOnClickMethod() {
        absenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        absenButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    fingerIcon.setImageResource(R.drawable.ic_menu_absen_green);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    fingerIcon.setImageResource(R.drawable.ic_menu_absen_white);
                }
                return false;
            }
        });
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllValue();
                getLatitudeLongitude();
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

    private void hitungDeviasi(float latitude, float longitude) {
        devlatfloat = (latitude - ((LOCNORTH + LOCSOUTH) / 2)) * KONSTAN;
        devlongfloat = (longitude - ((LOCWEST + LOCEAST) / 2)) * KONSTAN;
    }

    private void getLatitudeLongitude() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            coarseLoc = true;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else {
            fineLoc = true;
        }
        if (coarseLoc && fineLoc) {
            SingleShotLocationProvider.requestSingleUpdate(getActivity(),
                    new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                            userLat = location.latitude;
                            userLong = location.longitude;
                            latitudeView.setText(Float.toString(userLat));
                            longitudeView.setText(Float.toString(userLong));
                            if (isValidForAbsen(userLat, userLong)) {
                                absenButton.setVisibility(View.VISIBLE);
                                warningView.setVisibility(View.GONE);
                            } else {
                                absenButton.setVisibility(View.GONE);
                                warningView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } else {
            userLat = 0f;
            userLong = 0f;
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
            Toast.makeText(getActivity(), "tolong izinkan MAP BPKP untuk mengakses GPS anda", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidForAbsen(float latitude, float longitude) {
        if (latitude <= LOCNORTH || latitude >= LOCSOUTH) {
            isLatitudeOnPosition = false;
        } else {
            isLatitudeOnPosition = true;
        }
        if (longitude >= LOCEAST || longitude <= LOCWEST) {
            isLongitudeOnPosition = false;
        } else {
            isLongitudeOnPosition = true;
        }
        if (isLatitudeOnPosition && isLongitudeOnPosition) {
            return true;
        } else {
            return false;
        }
    }

    private void setAllValue() {
        userLat = 0f;
        userLong = 0f;
        latitudeView.setText("-");
        longitudeView.setText("-");
    }
}
