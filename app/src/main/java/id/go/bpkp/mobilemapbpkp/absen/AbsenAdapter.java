package id.go.bpkp.mobilemapbpkp.absen;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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

    private String statusDatang, statusPulang;
    private YoYo.YoYoString ropeDataAbsen;

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
        checkStatus(absen.getStatusDatang(), holder.statusBarDatang, holder.statusDatangDetail, holder.datangDetailIcon, holder.datangBuffer);
        checkStatus(absen.getStatusPulang(), holder.statusBarPulang, holder.statusPulangDetail, holder.pulangDetailIcon, holder.pulangBuffer);
        holder.jamKerjaEfektifView.setText(absen.getJamEfektif());
        holder.statusDatangView.setText(statusDatang);
        holder.statusPulangView.setText(statusPulang);

        ropeDataAbsen = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootview);
    }

    @Override
    public int getItemCount() {
        return absenList.size();
    }

    private void checkStatus(String status, LinearLayout statusBarProgress, LinearLayout statusBarDetail, ImageView icon, LinearLayout buffer) {
        switch (status) {
            case "Anda Tidak KonfirmasiPenugasan Datang":
                statusBarProgress.setBackgroundResource(R.color.red);
                statusBarDetail.setBackgroundResource(R.color.red);
                icon.setBackgroundResource(R.color.red);
                buffer.setBackgroundResource(R.color.red);
                statusDatang = "Anda belum merekam kehadiran";
                break;
            case "Anda Hadir Tepat Waktu":
                statusBarProgress.setBackgroundResource(R.color.green);
                statusBarDetail.setBackgroundResource(R.color.green);
                icon.setBackgroundResource(R.color.green);
                buffer.setBackgroundResource(R.color.green);
                statusDatang = "Anda hadir tepat waktu";
                break;
            case "Anda Datang Terlambat (DT)":
                statusBarProgress.setBackgroundResource(R.color.orange);
                statusBarDetail.setBackgroundResource(R.color.orange);
                icon.setBackgroundResource(R.color.orange);
                buffer.setBackgroundResource(R.color.orange);
                statusDatang = "Anda datang terlambat (DT)";
                break;
            case "Anda Belum KonfirmasiPenugasan Pulang":
                statusBarProgress.setBackgroundResource(R.color.red);
                statusBarDetail.setBackgroundResource(R.color.red);
                icon.setBackgroundResource(R.color.red);
                buffer.setBackgroundResource(R.color.red);
                statusPulang = "Anda belum merekam kepulangan";
                break;
            case "Anda Tidak KonfirmasiPenugasan Pulang":
                statusBarProgress.setBackgroundResource(R.color.red);
                statusBarDetail.setBackgroundResource(R.color.red);
                icon.setBackgroundResource(R.color.red);
                buffer.setBackgroundResource(R.color.red);
                statusPulang = "Anda tidak merekam kelupangan";
                break;
            case "Anda Pulang Cepat (PC)":
                statusBarProgress.setBackgroundResource(R.color.orange);
                statusBarDetail.setBackgroundResource(R.color.orange);
                icon.setBackgroundResource(R.color.orange);
                buffer.setBackgroundResource(R.color.orange);
                statusPulang = "Anda pulang lebih cepat (PC)";
                break;
            case "Anda Pulang Tepat Waktu":
                statusBarProgress.setBackgroundResource(R.color.green);
                statusBarDetail.setBackgroundResource(R.color.green);
                icon.setBackgroundResource(R.color.green);
                buffer.setBackgroundResource(R.color.green);
                statusPulang = "Anda pulang tepat waktu";
                break;

            default:
                statusBarProgress.setBackgroundResource(R.color.darkGrey);
                statusBarDetail.setVisibility(View.GONE);
                icon.setVisibility(View.GONE);
                buffer.setVisibility(View.GONE);
                break;

        }
    }

    class AbsenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView hariView, tanggalView, jamDatangView, jamPulangView, statusDatangView, statusPulangView, jamKerjaEfektifView;
        LinearLayout absenDetailView, statusBarDatang, statusBarPulang, statusDatangDetail, statusPulangDetail, datangBuffer, pulangBuffer;
        ImageView datangDetailIcon, pulangDetailIcon;
        CardView rootview;
        boolean isDetailShown = false;

        public AbsenViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.absen_data_layout);

            hariView = itemView.findViewById(R.id.data_absen_hari);
            tanggalView = itemView.findViewById(R.id.data_absen_tanggal);
            statusBarDatang = itemView.findViewById(R.id.absen_status_bar_datang);
            statusBarPulang = itemView.findViewById(R.id.absen_status_bar_pulang);
            statusDatangDetail = itemView.findViewById(R.id.absen_status_datang_detail);
            statusPulangDetail = itemView.findViewById(R.id.absen_status_pulang_detail);
            datangBuffer = itemView.findViewById(R.id.absen_status_datang_buffer);
            pulangBuffer = itemView.findViewById(R.id.absen_status_pulang_buffer);
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
