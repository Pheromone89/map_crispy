package id.go.bpkp.mobilemapbpkp.monitoring;

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

/**
 * Created by ASUS on 11/02/2018.
 */

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context mContext;
    private List<Unit> unitList;
    private YoYo.YoYoString ropeUnit;

    public UnitAdapter(Context mContext, List<Unit> unitList,
                       RecyclerViewClickListener itemListener) {
        this.mContext = mContext;
        this.unitList = unitList;
        this.itemListener = itemListener;
    }

    @Override
    public UnitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_unit, parent, false);
        UnitViewHolder unitViewHolder = new UnitViewHolder(view);
        return unitViewHolder;
    }

    @Override
    public void onBindViewHolder(UnitViewHolder holder, int position) {
        Unit unit = unitList.get(position);
        String nomor = "Nomor SK: " + unit.getNomorSK();
        String tanggal = "Tanggal SK: " + convertDate(unit.getTmtSK());
        String tmt = "TMT: " + convertDate(unit.getTmtUnit());
        holder.namaView.setText(unit.getNamaUnit());
        holder.nomorView.setText(nomor);
        holder.tanggalView.setText(tanggal);
        holder.tmtView.setText(tmt);

        ropeUnit = YoYo.with(Techniques.FadeIn)
                .duration(500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootview);
    }

    @Override
    public int getItemCount() {
        return unitList.size();
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

    class UnitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView namaView, nomorView, tanggalView, tmtView;
        LinearLayout detailView;
        CardView rootview;
        boolean isDetailShown = false;

        public UnitViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.data_unit_layout);

            namaView = itemView.findViewById(R.id.data_unit_nama);
            tmtView = itemView.findViewById(R.id.data_unit_tmt_unit);
            detailView = itemView.findViewById(R.id.data_unit_detail);
            nomorView = itemView.findViewById(R.id.data_unit_nomor);
            tanggalView = itemView.findViewById(R.id.data_unit_tanggal);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            if (isDetailShown) {
                detailView.setVisibility(View.GONE);
                isDetailShown = false;
            } else {
                detailView.setVisibility(View.VISIBLE);
                isDetailShown = true;
            }
        }
    }
}














