package id.go.bpkp.mobilemapbpkp.absen;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;

/**
 * Created by ASUS on 08/05/2018.
 */

public class AbsenAdapter extends RecyclerView.Adapter<AbsenAdapter.AbsenViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<Absen> absenList;

    public AbsenAdapter(
            Context context,
            List<Absen> absenList,
            RecyclerViewClickListener itemListener) {
        this.context = context;
        this.absenList = absenList;
        this.itemListener = itemListener;
    }

    @Override
    public AbsenAdapter.AbsenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_absen, parent, false);
        AbsenAdapter.AbsenViewHolder absenViewHolder = new AbsenAdapter.AbsenViewHolder(view);
        return absenViewHolder;
    }

    @Override
    public void onBindViewHolder(AbsenAdapter.AbsenViewHolder holder, int position) {
        Absen absen = absenList.get(position);

        String hari, tanggal, hariTanggal;
        hariTanggal = absen.getTanggalAbsen();
        int separator = hariTanggal.indexOf(",");
        hari = hariTanggal.substring(0, separator);
        tanggal = hariTanggal.substring(separator + 2);

        holder.hariView.setText(hari);
        holder.tanggalView.setText(tanggal);
        holder.jamDatangView.setText(absen.getJamDatang());
        holder.jamPulangView.setText(absen.getJamPulang());
        holder.statusDatangView.setText(absen.getStatusDatang());
        holder.statusPulangView.setText(absen.getStatusPulang());
        checkStatus(absen.getStatusDatang(), holder.statusBarDatang, holder.statusDatangDetail, holder.datangDetailIcon);
        checkStatus(absen.getStatusPulang(), holder.statusBarPulang, holder.statusPulangDetail, holder.pulangDetailIcon);
        holder.jamKerjaEfektifView.setText(absen.getJamEfektif());
    }

    @Override
    public int getItemCount() {
        return absenList.size();
    }

    private void checkStatus(String status, LinearLayout statusBarProgress, LinearLayout statusBarDetail, ImageView icon) {
        switch (status) {
            case "Anda Tidak Absen Datang":
                statusBarProgress.setBackgroundResource(R.color.red);
                statusBarDetail.setBackgroundResource(R.color.red);
                icon.setBackgroundResource(R.color.red);
                break;
            case "Anda Hadir Tepat Waktu":
                statusBarProgress.setBackgroundResource(R.color.green);
                statusBarDetail.setBackgroundResource(R.color.green);
                icon.setBackgroundResource(R.color.green);
                break;
            case "Anda Datang Terlambat (DT)":
                statusBarProgress.setBackgroundResource(R.color.orange);
                statusBarDetail.setBackgroundResource(R.color.orange);
                icon.setBackgroundResource(R.color.orange);
                break;
            case "Anda Belum Absen Pulang":
                statusBarProgress.setBackgroundResource(R.color.red);
                statusBarDetail.setBackgroundResource(R.color.red);
                icon.setBackgroundResource(R.color.red);
                break;
            case "Anda Tidak Absen Pulang":
                statusBarProgress.setBackgroundResource(R.color.red);
                statusBarDetail.setBackgroundResource(R.color.red);
                icon.setBackgroundResource(R.color.red);
                break;
            case "Anda Pulang Cepat (PC)":
                statusBarProgress.setBackgroundResource(R.color.orange);
                statusBarDetail.setBackgroundResource(R.color.orange);
                icon.setBackgroundResource(R.color.orange);
                break;
            case "Anda Pulang Tepat Waktu":
                statusBarProgress.setBackgroundResource(R.color.green);
                statusBarDetail.setBackgroundResource(R.color.green);
                icon.setBackgroundResource(R.color.green);
                break;

            default:
                statusBarProgress.setBackgroundResource(R.color.darkGrey);
                statusBarDetail.setVisibility(View.GONE);
                icon.setVisibility(View.GONE);
                break;

        }
    }

    class AbsenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView hariView, tanggalView, jamDatangView, jamPulangView, statusDatangView, statusPulangView, jamKerjaEfektifView;
        LinearLayout absenDetailView, statusBarDatang, statusBarPulang, statusDatangDetail, statusPulangDetail;
        ImageView datangDetailIcon, pulangDetailIcon;
        boolean isDetailShown = false;

        public AbsenViewHolder(View itemView) {
            super(itemView);

            hariView = itemView.findViewById(R.id.data_absen_hari);
            tanggalView = itemView.findViewById(R.id.data_absen_tanggal);
            statusBarDatang = itemView.findViewById(R.id.absen_status_bar_datang);
            statusBarPulang = itemView.findViewById(R.id.absen_status_bar_pulang);
            statusDatangDetail = itemView.findViewById(R.id.absen_status_datang_detail);
            statusPulangDetail = itemView.findViewById(R.id.absen_status_pulang_detail);
            datangDetailIcon = itemView.findViewById(R.id.absen_status_datang_detail_icon);
            pulangDetailIcon = itemView.findViewById(R.id.absen_status_pulang_detail_icon);
            jamDatangView = itemView.findViewById(R.id.data_absen_jam_datang);
            jamPulangView = itemView.findViewById(R.id.data_absen_jam_pulang);
            statusDatangView = itemView.findViewById(R.id.data_absen_status_datang);
            statusPulangView = itemView.findViewById(R.id.data_absen_status_pulang);
            jamKerjaEfektifView = itemView.findViewById(R.id.data_absen_jam_efektif);
            absenDetailView = itemView.findViewById(R.id.data_absen_detail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            if (isDetailShown) {
                absenDetailView.setVisibility(View.GONE);
                isDetailShown = false;
            } else {
                absenDetailView.setVisibility(View.VISIBLE);
                isDetailShown = true;
            }
        }
    }
}
