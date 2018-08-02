package id.go.bpkp.mobilemapbpkp.izinkantor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

/**
 * Created by ASUS on 08/05/2018.
 */

public class IzinKantorAdapter extends RecyclerView.Adapter<IzinKantorAdapter.IzinKantorViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<IzinKantor> izinKantorList;
    private YoYo.YoYoString ropeDataIzinKantor;

    public IzinKantorAdapter(
            Context context,
            List<IzinKantor> izinKantorList,
            RecyclerViewClickListener itemListener) {
        this.context = context;
        this.izinKantorList = izinKantorList;
        this.itemListener = itemListener;
    }

    @Override
    public IzinKantorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_izin_kantor, parent, false);
        IzinKantorViewHolder izinKantorViewHolder = new IzinKantorViewHolder(view);
        return izinKantorViewHolder;
    }

    @Override
    public void onBindViewHolder(IzinKantorViewHolder holder, int position) {
        IzinKantor izinKantor = izinKantorList.get(position);

        final String jenisIzin = izinKantor.getJenisIzin();
        String tanggalMulai = izinKantor.getTanggalMulai();
        String tanggalSelesai = izinKantor.getTanggalSelesai();
        String kodeProses = izinKantor.getKodeProses();
        final String namaProses = izinKantor.getNamaProses();
        String catatan = izinKantor.getCatatan();

        holder.jenisIzinView.setText(jenisIzin);
        if (tanggalSelesai.equals("null")) {
            holder.tanggalMulaiView.setText(tanggalMulai);
        } else {
            String tanggal = tanggalMulai + " s.d. " + tanggalSelesai;
            holder.tanggalMulaiView.setText(tanggal);
        }

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, namaProses, Toast.LENGTH_SHORT).show();
            }
        });

        switch (kodeProses) {
            case "0":
                holder.icon.setCardBackgroundColor(context.getResources().getColor(R.color.blueBasicDark));
                holder.iconImage.setImageResource(R.drawable.ic_hourglass_hollow);
                break;
            case "1":
                holder.icon.setCardBackgroundColor(context.getResources().getColor(R.color.green));
                holder.iconImage.setImageResource(R.drawable.ic_hourglass_hollow);
                break;
            case "2":
                holder.icon.setCardBackgroundColor(context.getResources().getColor(R.color.green));
                holder.iconImage.setImageResource(R.drawable.ic_hourglass_hollow);
                break;
            case "3":
                holder.icon.setCardBackgroundColor(context.getResources().getColor(R.color.green));
                holder.iconImage.setImageResource(R.drawable.ic_check);
                break;
            case "5":
                holder.icon.setCardBackgroundColor(context.getResources().getColor(R.color.red));
                holder.iconImage.setImageResource(R.drawable.ic_close_alt);
                break;
        }

        ropeDataIzinKantor = YoYo.with(Techniques.FadeIn)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootView);
    }

    @Override
    public int getItemCount() {
        return izinKantorList.size();
    }

    class IzinKantorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout rootView;
        TextView jenisIzinView, tanggalMulaiView;
        CardView icon;
        ImageView iconImage;

        public IzinKantorViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.izin_kantor_layout);

            icon = itemView.findViewById(R.id.izin_kantor_icon);
            iconImage = itemView.findViewById(R.id.izin_kantor_icon_image);
            jenisIzinView = itemView.findViewById(R.id.izin_kantor_jenis_izin);
            tanggalMulaiView = itemView.findViewById(R.id.izin_kantor_tanggal_mulai);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}
