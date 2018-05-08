package id.go.bpkp.mobilemapbpkp.monitoring;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.login.LoginActivity;

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
import static id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent.INTENT_FRAGMENTCONTENT;

/**
 * Created by ASUS on 07/03/2018.
 */

public class PencarianPegawaiFragment extends Fragment {

    private View
            rootView;
    private String
            mFoto,
            mNipLama,
            mNipBaru,
            username,
            mUserToken,
            mDashboardContent;
    private int
            mRoleId;
    private EditText cariPegawaiQuery;
    private CardView searchButton;

    public PencarianPegawaiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pencarian_pegawai, null);
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

        initiateView();
        initiateSetOnClickMethod();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_profil_seluruh_pegawai);
    }

    private void initiateView() {
        cariPegawaiQuery = rootView.findViewById(R.id.phone_verification_phone_input);
        searchButton = rootView.findViewById(R.id.phone_verification_search_button);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchMenuItem = menu.getItem(0);
        searchMenuItem.setVisible(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_dashboard_admin);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initiateSetOnClickMethod() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cariPegawaiQuery.setError(null);
                String query = cariPegawaiQuery.getText().toString().trim();
                boolean cancel = false;
                if (TextUtils.isEmpty(query) || query.equals("")) {
                    cariPegawaiQuery.setError("Kolom ini wajib diisi");
                    cancel = true;
                }
                if (cancel) {
                } else {
                    String searchQuery = cariPegawaiQuery.getText().toString();
                    if (!searchQuery.equals("")) {
                        Fragment fragment;
                        String url;
                        url = konfigurasi.URL_GET_ALLBYQUERY + searchQuery + "?api_token=";
                        Bundle bundle = new Bundle();
                        bundle.putString(INTENT_USERTOKEN, mUserToken);
                        bundle.putString(INTENT_FRAGMENTCONTENT, url);
                        bundle.putString(INTENT_NIPLAMA, mNipLama);
                        bundle.putInt(INTENT_ROLEIDINT, mRoleId);

                        fragment = new ProfilSeluruhPegawaiFragment();
                        fragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.content_fragment_area, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        View view = getActivity().getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }
            }
        });
    }
}