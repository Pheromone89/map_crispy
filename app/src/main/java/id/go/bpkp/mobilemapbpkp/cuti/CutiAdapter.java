package id.go.bpkp.mobilemapbpkp.cuti;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
import id.go.bpkp.mobilemapbpkp.monitoring.Diklat;

/**
 * Created by ASUS on 11/02/2018.
 */

public class CutiAdapter extends RecyclerView.Adapter<CutiAdapter.CutiViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context mContext;
    private List<Cuti> cutiList;
    YoYo.YoYoString ropeDataCuti;

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

        final String jenisCuti = cuti.getJenisCuti();
        final String tanggalMulai = cuti.getTanggalMulai();
        final String tanggalSelesai = cuti.getTanggalSelesai();
        final String alasan = cuti.getAlasan();
        final String jumlahHari = cuti.getJumlahHari();
        final String kodeProses = cuti.getKodeProses();
        final String namaProses = cuti.getNamaProses();

        String title = jenisCuti + " (" + jumlahHari + " hari)";

        holder.jenisCutiView.setText(title);
        if (tanggalSelesai.equals("null")) {
            holder.tanggalMulaiView.setText(tanggalMulai);
        } else {
            String tanggal = tanggalMulai + " s.d. " + tanggalSelesai;
            holder.tanggalMulaiView.setText(tanggal);
        }

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, namaProses, Toast.LENGTH_SHORT).show();
            }
        });

        switch (kodeProses) {
            case "0":
                // diajukan
                holder.icon.setCardBackgroundColor(mContext.getResources().getColor(R.color.blueBasicDark));
                holder.iconImage.setImageResource(R.drawable.ic_hourglass_hollow);
                break;
            case "1":
                // perseetujuan atlas
                holder.icon.setCardBackgroundColor(mContext.getResources().getColor(R.color.green));
                holder.iconImage.setImageResource(R.drawable.ic_hourglass_hollow);
                break;
            case "2":
                // persetujuan atlas 2
                holder.icon.setCardBackgroundColor(mContext.getResources().getColor(R.color.green));
                holder.iconImage.setImageResource(R.drawable.ic_hourglass_hollow);
                break;
            case "3":
                // peretujuan pejabat berwenang
                holder.icon.setCardBackgroundColor(mContext.getResources().getColor(R.color.green));
                holder.iconImage.setImageResource(R.drawable.ic_check);
                break;
            case "4":
                // final
                holder.icon.setCardBackgroundColor(mContext.getResources().getColor(R.color.green));
                holder.iconImage.setImageResource(R.drawable.ic_check);
                break;
            case "5":
                // ditolak
                holder.icon.setCardBackgroundColor(mContext.getResources().getColor(R.color.red));
                holder.iconImage.setImageResource(R.drawable.ic_close_alt);
                break;
            case "6":
                // dokumen kurang
                holder.icon.setCardBackgroundColor(mContext.getResources().getColor(R.color.orange));
                holder.iconImage.setImageResource(R.drawable.ic_menu_izin_cuti);
                break;
            case "7":
                // ditangguhkan
                holder.icon.setCardBackgroundColor(mContext.getResources().getColor(R.color.orange));
                holder.iconImage.setImageResource(R.drawable.ic_ditangguhkan);
                break;
            case "8":
                // ditunda
                holder.icon.setCardBackgroundColor(mContext.getResources().getColor(R.color.orange));
                holder.iconImage.setImageResource(R.drawable.ic_ditangguhkan);
                break;
        }


        ropeDataCuti = YoYo.with(Techniques.FadeIn)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootView);
    }

    @Override
    public int getItemCount() {
        return cutiList.size();
    }

    class CutiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout rootView;
        TextView jenisCutiView, tanggalMulaiView;
        CardView icon;
        ImageView iconImage;

        public CutiViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.cuti_layout);

            icon = itemView.findViewById(R.id.cuti_icon);
            iconImage = itemView.findViewById(R.id.cuti_icon_image);
            jenisCutiView = itemView.findViewById(R.id.cuti_jenis_cuti);
            tanggalMulaiView = itemView.findViewById(R.id.cuti_tanggal_mulai);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}














