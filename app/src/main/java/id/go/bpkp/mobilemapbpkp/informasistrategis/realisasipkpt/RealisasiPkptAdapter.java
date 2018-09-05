package id.go.bpkp.mobilemapbpkp.informasistrategis.realisasipkpt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 11/02/2018.
 */

public class RealisasiPkptAdapter extends RecyclerView.Adapter<RealisasiPkptAdapter.RealisasiPkptViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<RealisasiPkpt> realisasiPkptList;
    private YoYo.YoYoString ropeDataRealisasiPkpt;

    public RealisasiPkptAdapter(
            Context context,
            List<RealisasiPkpt> realisasiPkptList,
            RecyclerViewClickListener itemListener) {
        this.context = context;
        this.realisasiPkptList = realisasiPkptList;
        this.itemListener = itemListener;
    }

    @Override
    public RealisasiPkptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_realisasi_pkpt, parent, false);
        RealisasiPkptViewHolder realisasiPkptViewHolder = new RealisasiPkptViewHolder(view);
        return realisasiPkptViewHolder;
    }

    @Override
    public void onBindViewHolder(RealisasiPkptViewHolder holder, int position) {
        RealisasiPkpt realisasiPkpt = realisasiPkptList.get(position);

        String label = realisasiPkpt.getLabelPkpt();
        String realisasi = realisasiPkpt.getRealisasiPkpt();
        String total = realisasiPkpt.getTotalPkpt();
        String persentase = realisasiPkpt.getPersentasiPkpt();

        String detail = "( " + realisasi + " / " + total + " )";

        holder.labelView.setText(label);
        holder.detailView.setText(detail);
        holder.persentaseView.setText(persentase);

        if (position + 1 == realisasiPkptList.size()) {
            holder.divider.setVisibility(View.GONE);
        }

        ropeDataRealisasiPkpt = YoYo.with(Techniques.FadeIn)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootView);
    }

    @Override
    public int getItemCount() {
        return realisasiPkptList.size();
    }

    class RealisasiPkptViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout rootView, divider;
        TextView labelView, detailView, persentaseView;

        public RealisasiPkptViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.informasi_strategis_pkpt_list_layout);
            divider = itemView.findViewById(R.id.informasi_strategis_pkpt_list_divider);

            labelView = itemView.findViewById(R.id.informasi_strategis_pkpt_list_label);
            detailView = itemView.findViewById(R.id.informasi_strategis_pkpt_list_detail);
            persentaseView = itemView.findViewById(R.id.informasi_strategis_pkpt_list_persentase);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}