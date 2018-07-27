package id.go.bpkp.mobilemapbpkp.absen;

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
import com.squareup.picasso.Picasso;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;

/**
 * Created by ASUS on 08/05/2018.
 */

public class AbsenBawahanAdapter extends RecyclerView.Adapter<AbsenBawahanAdapter.AbsenBawahanViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<AbsenBawahan> absenBawahanList;

    private String statusDatang, statusPulang;
    private YoYo.YoYoString ropeDataAbsen;

    public AbsenBawahanAdapter(
            Context context,
            List<AbsenBawahan> absenBawahanList,
            RecyclerViewClickListener itemListener) {
        this.context = context;
        this.absenBawahanList = absenBawahanList;
        this.itemListener = itemListener;
    }

    @Override
    public AbsenBawahanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_absen_bawahan, parent, false);
        AbsenBawahanViewHolder absenBawahanViewHolder = new AbsenBawahanViewHolder(view);
        return absenBawahanViewHolder;
    }

    @Override
    public void onBindViewHolder(AbsenBawahanViewHolder holder, int position) {
        AbsenBawahan absenBawahan = absenBawahanList.get(position);

        String fotoUrl, nama, jamDatang, jamPulang, kodeDatang, kodePulang;
        fotoUrl = PassedIntent.getFoto(context, absenBawahan.getNipLama());
        nama = absenBawahan.getNama();
        jamDatang = absenBawahan.getJamDatang();
        jamPulang = absenBawahan.getJamPulang();
        kodeDatang = absenBawahan.getKodeDatang();
        kodePulang = absenBawahan.getKodePulang();

        Picasso.with(context).load(fotoUrl).into(holder.proficView);
        holder.namaView.setText(nama);
        holder.jamDatangView.setText(jamDatang);
        holder.jamPulangView.setText(jamPulang);

        switch (kodeDatang) {
            case "b":
                holder.jamDatangView.setTextColor(context.getResources().getColor(android.R.color.black));
//                holder.datangIcon.setBackgroundResource(android.R.color.black);
                break;
            case "r":
                holder.jamDatangView.setTextColor(context.getResources().getColor(R.color.red));
//                holder.datangIcon.setBackgroundResource(R.color.red);
                break;
            case "g":
                holder.jamDatangView.setTextColor(context.getResources().getColor(R.color.green));
//                holder.datangIcon.setBackgroundResource(R.color.green);
                break;
            case "o":
                holder.jamDatangView.setTextColor(context.getResources().getColor(R.color.orange));
//                holder.datangIcon.setBackgroundResource(R.color.orange);
                break;
        }
        switch (kodePulang) {
            case "b":
                holder.jamPulangView.setTextColor(context.getResources().getColor(android.R.color.black));
//                holder.pulangIcon.setBackgroundResource(android.R.color.black);
                break;
            case "r":
                holder.jamPulangView.setTextColor(context.getResources().getColor(R.color.red));
//                holder.pulangIcon.setBackgroundResource(R.color.red);
                break;
            case "g":
                holder.jamPulangView.setTextColor(context.getResources().getColor(R.color.green));
//                holder.pulangIcon.setBackgroundResource(R.color.green);
                break;
            case "o":
                holder.jamPulangView.setTextColor(context.getResources().getColor(R.color.orange));
//                holder.pulangIcon.setBackgroundResource(R.color.orange);
                break;
        }

//        ropeDataAbsen = YoYo.with(Techniques.FadeIn)
//                .duration(500)
//                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
//                .interpolate(new AccelerateDecelerateInterpolator())
//                .playOn(holder.rootView);
    }

    @Override
    public int getItemCount() {
        return absenBawahanList.size();
    }

    class AbsenBawahanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView rootView;
        ImageView proficView;
        TextView namaView, jamDatangView, jamPulangView;
        ImageView datangIcon, pulangIcon;

        public AbsenBawahanViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.absen_bawahan_data_layout);

            proficView = itemView.findViewById(R.id.data_absen_bawahan_profic);
            namaView = itemView.findViewById(R.id.data_absen_bawahan_nama);
            jamDatangView = itemView.findViewById(R.id.data_absen_bawahan_jam_datang);
            jamPulangView = itemView.findViewById(R.id.data_absen_bawahan_jam_pulang);
//            datangIcon = itemView.findViewById(R.id.data_absen_bawahan_datang_icon);
//            pulangIcon = itemView.findViewById(R.id.data_absen_bawahan_pulang_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
//            if (isDetailShown) {
//                absenDetailView.setVisibility(View.GONE);
//                isDetailShown = false;
//            } else {
//                absenDetailView.setVisibility(View.VISIBLE);
//                isDetailShown = true;
//            }
        }
    }
}
