package id.go.bpkp.mobilemapbpkp.hotspot;

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

import java.util.ArrayList;
import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.monitoring.PegawaiSingkat;
import id.go.bpkp.mobilemapbpkp.monitoring.ProfilPegawaiPagerFragment;

/**
 * Created by ASUS on 22/01/2018.
 */

public class HotspotAdapter extends RecyclerView.Adapter<HotspotAdapter.HotspotViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<Hotspot> hotspotList, hotspotListCopy;
    private String mUserToken;
    private YoYo.YoYoString ropeHotspot;

    public HotspotAdapter(Context context,
                          List<Hotspot> hotspotList,
                          RecyclerViewClickListener itemListener,
                          String mUserToken) {
        this.context = context;
        this.hotspotList = hotspotList;
        this.itemListener = itemListener;
        this.mUserToken = mUserToken;
    }

    @Override
    public HotspotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_hotspot, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new HotspotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotspotViewHolder holder, int position) {
        Hotspot hotspot = hotspotList.get(position);

        Picasso.with(context).load(hotspot.getFoto()).into(holder.profilePictureView);
        holder.namaView.setText(hotspot.getNama());
        String nip = hotspot.getNipbaru() + " / " + hotspot.getNiplama();
        holder.nipBaruView.setText(nip);
        holder.tanggalView.setText(hotspot.getTanggal());
        holder.datangView.setText(hotspot.getDatang());
        holder.pulangView.setText(hotspot.getPulang());
    }

    @Override
    public int getItemCount() {
        return hotspotList.size();
    }

    class HotspotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profilePictureView;
        TextView namaView, nipBaruView, jabatanSingkatView, unitView, datangView, pulangView, tanggalView;
        LinearLayout rootview;

        public HotspotViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.pegawai_singkat_layout);

            profilePictureView = itemView.findViewById(R.id.profic);
            namaView = itemView.findViewById(R.id.nama);
            nipBaruView = itemView.findViewById(R.id.nipbaru);
            jabatanSingkatView = itemView.findViewById(R.id.jabatan_singkat);
            unitView = itemView.findViewById(R.id.unit);
            datangView = itemView.findViewById(R.id.datang);
            pulangView = itemView.findViewById(R.id.pulang);
            tanggalView = itemView.findViewById(R.id.tanggal);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
//            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
//            int id = this.getLayoutPosition();
//
//            Hotspot hotspot = hotspotList.get(id);
//            String empNipbaru = hotspot.getNipbaru();
//            String empUsername = hotspot.getNipbaru();
//            String empNiplama = hotspot.getNiplama();
//            String empFoto = PassedIntent.getFoto(context, empNiplama);
//
//            Bundle bundle = new Bundle();
//            bundle.putString(PassedIntent.INTENT_NIPBARU, empNipbaru);
//            bundle.putString(PassedIntent.INTENT_FOTO, empFoto);
//            bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
//            bundle.putString(PassedIntent.INTENT_USERNAME, empUsername);
//            bundle.putString(PassedIntent.INTENT_NIPLAMA, empNiplama);
//
//            HotspotFragment fragment = new HotspotFragment();
//            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragment.setArguments(bundle);
//            fragmentTransaction.add(R.id.content_fragment_area, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();

        }
    }

    public void filter(String text) {
        hotspotListCopy = new ArrayList<>();
        hotspotListCopy.addAll(Hotspot.hotspotList);
        hotspotList.clear();
        if (text.isEmpty()) {
            hotspotList.addAll(Hotspot.hotspotList);
            hotspotListCopy.clear();
        } else {
            text = text.toLowerCase();
            for (Hotspot hotspot: hotspotListCopy) {
                if (hotspot.getNamanip().toLowerCase().contains(text)) {
                    hotspotList.add(hotspot);
                }
            }
            hotspotListCopy.clear();
        }
        notifyDataSetChanged();
    }
}