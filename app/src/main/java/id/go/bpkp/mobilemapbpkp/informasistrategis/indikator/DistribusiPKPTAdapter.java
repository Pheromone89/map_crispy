package id.go.bpkp.mobilemapbpkp.informasistrategis.indikator;

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
import id.go.bpkp.mobilemapbpkp.cuti.Cuti;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 11/02/2018.
 */

public class DistribusiPKPTAdapter extends RecyclerView.Adapter<DistribusiPKPTAdapter.DistribusiPKPTViewHolder> {

    private static RecyclerViewClickListener itemListener;
    YoYo.YoYoString ropeDataDistribusiPKPT;
    private Context mContext;
    private List<DistribusiPKPT> distribusiPKPTList;

    public DistribusiPKPTAdapter(Context mContext, List<DistribusiPKPT> distribusiPKPTList,
                                 RecyclerViewClickListener itemListener) {
        this.mContext = mContext;
        this.distribusiPKPTList = distribusiPKPTList;
        this.itemListener = itemListener;
    }

    @Override
    public DistribusiPKPTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_indikator_distribusi_pkpt, parent, false);
        DistribusiPKPTViewHolder distribusiPKPTViewHolder = new DistribusiPKPTViewHolder(view);
        return distribusiPKPTViewHolder;
    }

    @Override
    public void onBindViewHolder(DistribusiPKPTViewHolder holder, int position) {
        DistribusiPKPT distribusiPKPT = distribusiPKPTList.get(position);

        final String fokus = distribusiPKPT.getFokus();
        final String label = distribusiPKPT.getLabel();
        final String jumlah = distribusiPKPT.getJumlah();
        final String persentase = distribusiPKPT.getPersentase();

        String judul = fokus + " (" + jumlah + ")";

        holder.fokusView.setText(judul);
        holder.labelView.setText(label);
        holder.persentaseView.setText(persentase);

        ropeDataDistribusiPKPT = YoYo.with(Techniques.FadeIn)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootView);
    }

    @Override
    public int getItemCount() {
        return distribusiPKPTList.size();
    }

    class DistribusiPKPTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout rootView;
        TextView fokusView, labelView, persentaseView;

        public DistribusiPKPTViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.indikator_distribusi_pkpt_layout);

            fokusView = itemView.findViewById(R.id.indikator_distribusi_pkpt_fokus);
            labelView = itemView.findViewById(R.id.indikator_distribusi_pkpt_label);
            persentaseView = itemView.findViewById(R.id.indikator_distribusi_pkpt_persentase);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}














