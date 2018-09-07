package id.go.bpkp.mobilemapbpkp.hotspot;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import id.go.bpkp.mobilemapbpkp.monitoring.Jabatan;

/**
 * Created by ASUS on 20/03/2018.
 */

public class HotspotDetailAdapter extends RecyclerView.Adapter<HotspotDetailAdapter.HotspotDetailViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context mContext;
    private List<Hotspot> hotspotList;

    public HotspotDetailAdapter(Context mContext, List<Hotspot> hotspotList,
                                RecyclerViewClickListener itemListener) {
        this.mContext = mContext;
        this.hotspotList = hotspotList;
        this.itemListener = itemListener;
    }

    @Override
    public HotspotDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_hotspot_detail, parent, false);
        HotspotDetailViewHolder hotspotDetailViewHolder = new HotspotDetailViewHolder(view);
        return hotspotDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(HotspotDetailViewHolder holder, int position) {
        Hotspot hotspot = hotspotList.get(position);
        holder.ip.setText(hotspot.getIp());
        holder.datang.setText(hotspot.getDatang());
        holder.pulang.setText(hotspot.getPulang());

        YoYo.with(Techniques.FadeIn)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootview);
    }

    @Override
    public int getItemCount() {
        return hotspotList.size();
    }

    class HotspotDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView datang, pulang, ip;
        LinearLayout rootview;

        public HotspotDetailViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.hotspot_detail_layout);

            datang = itemView.findViewById(R.id.hotspot_detail_datang);
            pulang = itemView.findViewById(R.id.hotspot_detail_pulang);
            ip = itemView.findViewById(R.id.hotspot_detail_ip);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
