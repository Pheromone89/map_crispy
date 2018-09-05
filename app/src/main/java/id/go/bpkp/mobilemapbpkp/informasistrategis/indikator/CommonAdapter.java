package id.go.bpkp.mobilemapbpkp.informasistrategis.indikator;

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

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.CommonViewHolder> {

    private static RecyclerViewClickListener itemListener;
    YoYo.YoYoString ropeDataCommon;
    private Context mContext;
    private List<Common> commonList;

    public CommonAdapter(Context mContext, List<Common> commonList,
                         RecyclerViewClickListener itemListener) {
        this.mContext = mContext;
        this.commonList = commonList;
        this.itemListener = itemListener;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_indikator_common, parent, false);
        CommonViewHolder commonViewHolder = new CommonViewHolder(view);
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        Common common = commonList.get(position);

        final String label = common.getLabel();
        final String value = common.getNilai();

        holder.labelView.setText(label);
        holder.valueView.setText(value);

        ropeDataCommon = YoYo.with(Techniques.FadeIn)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootView);
    }

    @Override
    public int getItemCount() {
        return commonList.size();
    }

    class CommonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout rootView;
        TextView labelView, valueView;

        public CommonViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.indikator_common_layout);

            labelView = itemView.findViewById(R.id.indikator_common_label);
            valueView = itemView.findViewById(R.id.indikator_common_value);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}














