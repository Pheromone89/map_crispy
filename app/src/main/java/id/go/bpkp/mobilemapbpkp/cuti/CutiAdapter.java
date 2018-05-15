package id.go.bpkp.mobilemapbpkp.cuti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.monitoring.Diklat;

/**
 * Created by ASUS on 11/02/2018.
 */

public class CutiAdapter extends RecyclerView.Adapter<CutiAdapter.CutiViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context mContext;
    private List<Cuti> cutiList;

    public CutiAdapter(Context mContext, List<Cuti> cutiList,
                       RecyclerViewClickListener itemListener) {
        this.mContext = mContext;
        this.cutiList = cutiList;
        this.itemListener = itemListener;
    }

    @Override
    public CutiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_cuti, parent, false);
        CutiViewHolder cutiViewHolder = new CutiViewHolder(view);
        return cutiViewHolder;
    }

    @Override
    public void onBindViewHolder(CutiViewHolder holder, int position) {
        Cuti cuti = cutiList.get(position);

//        String nomor = "Nomor sertifikat: " + cuti.getNomorDiklat();
//        String tanggal = "Tgl Sert.: " + convertDate(cuti.getTanggalDiklat());
//        String kompetensi = "Kompetensi: " + cuti.getJenisKompetensi();
//
//        holder.namaView.setText(cuti.getNamaDiklat());
//        holder.nomorView.setText(nomor);
//        holder.tanggalView.setText(tanggal);
//        holder.kompetensiView.setText(kompetensi);
    }

    @Override
    public int getItemCount() {
        return cutiList.size();
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

    class CutiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView namaView, nomorView, tanggalView, kompetensiView;
        LinearLayout dataDiklatDetailView;
        boolean isDetailShown = false;

        public CutiViewHolder(View itemView) {
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














