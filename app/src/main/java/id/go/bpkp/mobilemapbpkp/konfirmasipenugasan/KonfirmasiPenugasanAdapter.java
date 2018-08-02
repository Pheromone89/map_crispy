package id.go.bpkp.mobilemapbpkp.konfirmasipenugasan;

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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 08/05/2018.
 */

public class KonfirmasiPenugasanAdapter extends RecyclerView.Adapter<KonfirmasiPenugasanAdapter.KonfirmasiPenugasanViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<KonfirmasiPenugasan> konfirmasiPenugasanList;
    private YoYo.YoYoString ropeDataKonfirmasiPenugasan;

    public KonfirmasiPenugasanAdapter(
            Context context,
            List<KonfirmasiPenugasan> konfirmasiPenugasanList,
            RecyclerViewClickListener itemListener) {
        this.context = context;
        this.konfirmasiPenugasanList = konfirmasiPenugasanList;
        this.itemListener = itemListener;
    }

    @Override
    public KonfirmasiPenugasanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_konfirmasi_penugasan, parent, false);
        KonfirmasiPenugasanViewHolder konfirmasiPenugasanViewHolder = new KonfirmasiPenugasanViewHolder(view);
        return konfirmasiPenugasanViewHolder;
    }

    @Override
    public void onBindViewHolder(KonfirmasiPenugasanViewHolder holder, int position) {
        KonfirmasiPenugasan konfirmasiPenugasan = konfirmasiPenugasanList.get(position);

        String jenisPenugasan = konfirmasiPenugasan.getJenisPenugasan();
        String tanggalMulai = konfirmasiPenugasan.getTanggalMulai();
        String tanggalSelesai = konfirmasiPenugasan.getTanggalSelesai();
        String catatan = konfirmasiPenugasan.getCatatan();

        holder.jenisPenugasanView.setText(jenisPenugasan);
        holder.tanggalMulaiView.setText(tanggalMulai);
        holder.tanggalSelesaiView.setText(tanggalSelesai);

        ropeDataKonfirmasiPenugasan = YoYo.with(Techniques.FadeIn)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootView);
    }

    @Override
    public int getItemCount() {
        return konfirmasiPenugasanList.size();
    }

    class KonfirmasiPenugasanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout rootView;
        TextView jenisPenugasanView, tanggalMulaiView, tanggalSelesaiView;

        public KonfirmasiPenugasanViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.konfirmasi_penugasan_layout);

            jenisPenugasanView = itemView.findViewById(R.id.konfirmasi_penugasan_jenis_penugasan);
            tanggalMulaiView = itemView.findViewById(R.id.konfirmasi_penugasan_tanggal_mulai);
            tanggalSelesaiView = itemView.findViewById(R.id.konfirmasi_penugasan_tanggal_selesai);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}
