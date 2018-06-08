package id.go.bpkp.mobilemapbpkp.monitoring;

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

/**
 * Created by ASUS on 22/01/2018.
 */

public class PegawaiSingkatAdapter extends RecyclerView.Adapter<PegawaiSingkatAdapter.PegawaiSingkatViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<PegawaiSingkat> pegawaiSingkatList;
    private String mUserToken;
    private YoYo.YoYoString ropePegawaiSingkat;

    public PegawaiSingkatAdapter(Context context,
                                 List<PegawaiSingkat> pegawaiSingkatList,
                                 RecyclerViewClickListener itemListener,
                                 String mUserToken) {
        this.context = context;
        this.pegawaiSingkatList = pegawaiSingkatList;
        this.itemListener = itemListener;
        this.mUserToken = mUserToken;
    }

    @Override
    public PegawaiSingkatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_pegawai, null);
        PegawaiSingkatViewHolder pegawaiSingkatViewHolder = new PegawaiSingkatViewHolder(view);
        return pegawaiSingkatViewHolder;
    }

    @Override
    public void onBindViewHolder(PegawaiSingkatViewHolder holder, int position) {
        PegawaiSingkat pegawaiSingkat = pegawaiSingkatList.get(position);

        Picasso.with(context).load(pegawaiSingkat.getFoto()).into(holder.profilePictureView);
        holder.namaView.setText(pegawaiSingkat.getNama());
        holder.nipBaruView.setText(pegawaiSingkat.getNipBaru());
        holder.unitView.setText(pegawaiSingkat.getUnit());
        holder.jabatanSingkatView.setText(pegawaiSingkat.getJabatanSingkat());

        ropePegawaiSingkat = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootview);
    }

    @Override
    public int getItemCount() {
        return pegawaiSingkatList.size();
    }

    class PegawaiSingkatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profilePictureView;
        TextView namaView, nipBaruView, jabatanSingkatView, unitView;
        LinearLayout rootview;

        public PegawaiSingkatViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.pegawai_singkat_layout);

            profilePictureView = itemView.findViewById(R.id.profic);
            namaView = itemView.findViewById(R.id.nama);
            nipBaruView = itemView.findViewById(R.id.nipbaru);
            jabatanSingkatView = itemView.findViewById(R.id.jabatan_singkat);
            unitView = itemView.findViewById(R.id.unit);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            int id = this.getLayoutPosition();

            PegawaiSingkat pegawaiSingkat = pegawaiSingkatList.get(id);
            String empNipbaru = pegawaiSingkat.getNipBaru();
            String empUsername = pegawaiSingkat.getNipBaruPisah();
            String empNiplama = pegawaiSingkat.getNipLama();
            String empFoto = PassedIntent.getFoto(empNiplama);

            Bundle bundle = new Bundle();
            bundle.putString(PassedIntent.INTENT_NIPBARU, empNipbaru);
            bundle.putString(PassedIntent.INTENT_FOTO, empFoto);
            bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
            bundle.putString(PassedIntent.INTENT_USERNAME, empUsername);
            bundle.putString(PassedIntent.INTENT_NIPLAMA, empNiplama);

            ProfilPegawaiPagerFragment fragment = new ProfilPegawaiPagerFragment();
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_fragment_area, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}