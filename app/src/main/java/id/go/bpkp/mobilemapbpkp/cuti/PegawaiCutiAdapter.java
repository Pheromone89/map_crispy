package id.go.bpkp.mobilemapbpkp.cuti;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.monitoring.PegawaiSingkat;
import id.go.bpkp.mobilemapbpkp.monitoring.PegawaiSingkatAdapter;
import id.go.bpkp.mobilemapbpkp.monitoring.ProfilPegawaiPagerFragment;

/**
 * Created by ASUS on 15/02/2018.
 */

public class PegawaiCutiAdapter extends RecyclerView.Adapter<PegawaiCutiAdapter.PegawaiCutiViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<PegawaiCuti> pegawaiCutiList;
    private String mUserToken;
    private YoYo.YoYoString ropePegawaiCuti;

    public PegawaiCutiAdapter(Context context,
                              List<PegawaiCuti> pegawaiCutiList,
                              RecyclerViewClickListener itemListener,
                              String mUserToken) {
        this.context = context;
        this.pegawaiCutiList = pegawaiCutiList;
        this.itemListener = itemListener;
        this.mUserToken = mUserToken;
    }

    @Override
    public PegawaiCutiAdapter.PegawaiCutiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_pegawai_cuti, parent, false);
        PegawaiCutiAdapter.PegawaiCutiViewHolder pegawaiCutiViewHolder = new PegawaiCutiAdapter.PegawaiCutiViewHolder(view);
        return pegawaiCutiViewHolder;
    }

    @Override
    public void onBindViewHolder(PegawaiCutiAdapter.PegawaiCutiViewHolder holder, int position) {
        PegawaiCuti pegawaiCuti = pegawaiCutiList.get(position);

        Picasso.with(context).load(pegawaiCuti.getFoto()).into(holder.profilePictureView);
        holder.namaView.setText(pegawaiCuti.getNama());
        String nip = pegawaiCuti.getNipBaru() + " / " + pegawaiCuti.getNipLama();
        holder.nipBaruView.setText(nip);

        ropePegawaiCuti = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootview);
    }

    @Override
    public int getItemCount() {
        return pegawaiCutiList.size();
    }

    class PegawaiCutiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profilePictureView, addButton;
        TextView namaView, nipBaruView;
        LinearLayout rootview;

        public PegawaiCutiViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.pegawai_cuti_layout);

            profilePictureView = itemView.findViewById(R.id.pegawai_cuti_profic);
            namaView = itemView.findViewById(R.id.pegawai_cuti_nama);
            nipBaruView = itemView.findViewById(R.id.pegawai_cuti_nip_baru);
            addButton = itemView.findViewById(R.id.pegawai_cuti_plus_button);

            addButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            int id = this.getLayoutPosition();

            PegawaiCuti pegawaiCuti = pegawaiCutiList.get(id);
            String empNipbaru = pegawaiCuti.getNipBaru();
            String empUsername = pegawaiCuti.getNipBaruPisah();
            String empNiplama = pegawaiCuti.getNipLama();
            String empNama = pegawaiCuti.getNama();
            String empFoto = PassedIntent.getFoto(context, empNiplama);

            Bundle bundle = new Bundle();
            bundle.putString(PassedIntent.INTENT_NAMA, empNama);
            bundle.putString(PassedIntent.INTENT_NIPLAMA, empNiplama);
            bundle.putString(PassedIntent.INTENT_NIPBARU, empNipbaru);
            bundle.putString(PassedIntent.INTENT_FOTO, empFoto);
            bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
            bundle.putString(PassedIntent.INTENT_USERNAME, empUsername);
            bundle.putBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, true);
            bundle.putString(PassedIntent.INTENT_NAMAATASANLANGSUNG, "testValue");
            bundle.putString(PassedIntent.INTENT_NIPATASANLANGSUNG, "testNipValue");


            CutiDashboardPegawaiFragment fragment = new CutiDashboardPegawaiFragment();
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_fragment_area, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}
