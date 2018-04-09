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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;

/**
 * Created by ASUS on 11/02/2018.
 */

public class DiklatAdapter extends RecyclerView.Adapter<DiklatAdapter.DiklatViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context mContext;
    private List<Diklat> diklatList;

    public DiklatAdapter(Context mContext, List<Diklat> diklatList,
                         RecyclerViewClickListener itemListener) {
        this.mContext = mContext;
        this.diklatList = diklatList;
        this.itemListener = itemListener;
    }

    @Override
    public DiklatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_diklat, parent, false);
        DiklatViewHolder diklatViewHolder = new DiklatViewHolder(view);
        return diklatViewHolder;
    }

    @Override
    public void onBindViewHolder(DiklatViewHolder holder, int position) {
        Diklat diklat = diklatList.get(position);

        String nomor = "Nomor sertifikat: " + diklat.getNomorDiklat();
        String tanggal = "Tgl Sert.: " + convertDate(diklat.getTanggalDiklat());
        String kompetensi = "Kompetensi: " + diklat.getJenisKompetensi();

        holder.namaView.setText(diklat.getNamaDiklat());
        holder.nomorView.setText(nomor);
        holder.tanggalView.setText(tanggal);
        holder.kompetensiView.setText(kompetensi);
    }

    @Override
    public int getItemCount() {
        return diklatList.size();
    }

    private String convertDate(String tanggal) {
        if (tanggal.length() == 10) {
            String tanggalHari;
            String tanggalBulan;
            String tanggalTahun;

            tanggalHari = tanggal.substring(8, 10);
            tanggalBulan = tanggal.substring(5, 7);
            tanggalTahun = tanggal.substring(0, 4);
            return tanggalHari + "/" + tanggalBulan + "/" + tanggalTahun;
        } else if (tanggal.equals("null")) {
            return "-";
        } else {
            return tanggal;
        }
    }

    class DiklatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView namaView, nomorView, tanggalView, kompetensiView;
        LinearLayout dataDiklatDetailView;
        boolean isDetailShown = false;

        public DiklatViewHolder(View itemView) {
            super(itemView);

            namaView = itemView.findViewById(R.id.data_diklat_nama);
            dataDiklatDetailView = itemView.findViewById(R.id.data_diklat_detail);
            nomorView = itemView.findViewById(R.id.data_diklat_nomor);
            tanggalView = itemView.findViewById(R.id.data_diklat_tanggal);
            kompetensiView = itemView.findViewById(R.id.data_diklat_jenis_kompetensi);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            if (isDetailShown) {
                dataDiklatDetailView.setVisibility(View.GONE);
                isDetailShown = false;
            } else {
                dataDiklatDetailView.setVisibility(View.VISIBLE);
                isDetailShown = true;
            }

        }
    }
}














