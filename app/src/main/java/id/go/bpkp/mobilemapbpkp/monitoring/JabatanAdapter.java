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
 * Created by ASUS on 20/03/2018.
 */

public class JabatanAdapter extends RecyclerView.Adapter<JabatanAdapter.JabatanViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context mContext;
    private List<Jabatan> jabatanList;
    private YoYo.YoYoString ropeJabatan;

    public JabatanAdapter(Context mContext, List<Jabatan> jabatanList,
                          RecyclerViewClickListener itemListener) {
        this.mContext = mContext;
        this.jabatanList = jabatanList;
        this.itemListener = itemListener;
    }

    @Override
    public JabatanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_jabatan, parent, false);
        JabatanViewHolder jabatanViewHolder = new JabatanViewHolder(view);
        return jabatanViewHolder;
    }

    @Override
    public void onBindViewHolder(JabatanViewHolder holder, int position) {
        Jabatan jabatan = jabatanList.get(position);
        holder.namaJabatan.setText(jabatan.getNamaJabatan());
        String tmtJabatan = "TMT: " + convertDate(jabatan.getTmtJabatan());
        String nomor = "Nomor SK: " + jabatan.getNomorSK();
        String tanggal = "Tanggal SK: " + convertDate(jabatan.getTanggalSK());
        holder.tmtJabatan.setText(tmtJabatan);
        holder.nomorSK.setText(nomor);
        holder.tanggalSK.setText(tanggal);

        ropeJabatan = YoYo.with(Techniques.FadeIn)
                .duration(500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootview);
    }

    @Override
    public int getItemCount() {
        return jabatanList.size();
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

    class JabatanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView namaJabatan, tmtJabatan, nomorSK, tanggalSK;
        LinearLayout dataJabatanDetailView;
        CardView rootview;
        boolean isDetailShown = false;

        public JabatanViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.data_jabatan_layout);

            namaJabatan = itemView.findViewById(R.id.data_jabatan_nama);
            tmtJabatan = itemView.findViewById(R.id.data_jabatan_tmt_jabatan);
            dataJabatanDetailView = itemView.findViewById(R.id.data_jabatan_detail);
            nomorSK = itemView.findViewById(R.id.data_jabatan_nomor);
            tanggalSK = itemView.findViewById(R.id.data_jabatan_tanggal_sk);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            if (isDetailShown) {
                dataJabatanDetailView.setVisibility(View.GONE);
                isDetailShown = false;
            } else {
                dataJabatanDetailView.setVisibility(View.VISIBLE);
                isDetailShown = true;
            }

        }
    }
}
