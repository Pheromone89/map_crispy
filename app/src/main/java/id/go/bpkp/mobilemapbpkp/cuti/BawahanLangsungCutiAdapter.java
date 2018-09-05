package id.go.bpkp.mobilemapbpkp.cuti;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
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

/**
 * Created by ASUS on 15/02/2018.
 */

public class BawahanLangsungCutiAdapter extends RecyclerView.Adapter<BawahanLangsungCutiAdapter.BawahanLangsungCutiViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<BawahanLangsungCuti> pegawaiBawahanLangsungList;
    private String mUserToken;
    private String mNipLamaAtasan;
    private YoYo.YoYoString ropeBawahanLangsung;

    public BawahanLangsungCutiAdapter(Context context,
                                      List<BawahanLangsungCuti> pegawaiBawahanLangsungList,
                                      RecyclerViewClickListener itemListener,
                                      String mNipLamaAtasan,
                                      String mUserToken) {
        this.context = context;
        this.pegawaiBawahanLangsungList = pegawaiBawahanLangsungList;
        this.itemListener = itemListener;
        this.mUserToken = mUserToken;
        this.mNipLamaAtasan = mNipLamaAtasan;
    }

    @Override
    public BawahanLangsungCutiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_pegawai_cuti_bawahan_langsung, parent, false);
        BawahanLangsungCutiViewHolder bawahanLangsungCutiViewHolder = new BawahanLangsungCutiViewHolder(view);
        return bawahanLangsungCutiViewHolder;
    }

    @Override
    public void onBindViewHolder(BawahanLangsungCutiViewHolder holder, int position) {
        BawahanLangsungCuti bawahanLangsungCuti = pegawaiBawahanLangsungList.get(position);


        String foto = PassedIntent.getFoto(context, bawahanLangsungCuti.getNipLama());
        String nama = bawahanLangsungCuti.getNama();
        String tanggal = bawahanLangsungCuti.getTanggalAwal() + " s.d. " + bawahanLangsungCuti.getTanggalAkhir();
        String keterangan = bawahanLangsungCuti.getJenisCuti() + " (" + bawahanLangsungCuti.getJumlahHari() + " hari)";
        Picasso.with(context).load(foto).into(holder.profilePictureView);
        holder.namaView.setText(nama);
        holder.tanggalView.setText(tanggal);
        holder.keteranganView.setText(keterangan);

//        ropeBawahanLangsung = YoYo.with(Techniques.FadeIn)
//                .duration(konfigurasi.animationDurationShort)
//                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
//                .interpolate(new AccelerateDecelerateInterpolator())
//                .playOn(holder.rootview);
    }

    @Override
    public int getItemCount() {
        return pegawaiBawahanLangsungList.size();
    }

    class BawahanLangsungCutiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profilePictureView;
        TextView namaView, tanggalView, keteranganView;
        LinearLayout rootview;

        public BawahanLangsungCutiViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_layout);

            profilePictureView = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_profic);
            namaView = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_nama);
            tanggalView = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_tanggal);
            keteranganView = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_alasan);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            int id = this.getLayoutPosition();

            BawahanLangsungCuti bawahanLangsungCuti = pegawaiBawahanLangsungList.get(id);
            String empNipLama = bawahanLangsungCuti.getNipLama();
            String empNama = bawahanLangsungCuti.getNama();
            String empIdTransaksi = bawahanLangsungCuti.getIdTransaksi();
            String empJenisCuti = bawahanLangsungCuti.getJenisCuti();
            String empTanggalPengajuan = bawahanLangsungCuti.getTanggalPengajuan();
            String empTanggalAwal = bawahanLangsungCuti.getTanggalAwal();
            String empTanggalAkhir = bawahanLangsungCuti.getTanggalAkhir();
            String empJumlahHari = bawahanLangsungCuti.getJumlahHari();
            String empAlamat = bawahanLangsungCuti.getAlamat();
            String empAlasan = bawahanLangsungCuti.getAlasan();
            String empCatatan = bawahanLangsungCuti.getCatatan();
            String empPemrosesSebelumnya = bawahanLangsungCuti.getPemrosesSebelumnya();
            String emptanggalPemrosesSebelumnya = bawahanLangsungCuti.getTanggalPemrosesSebelumnya();
            boolean empIsFinal = bawahanLangsungCuti.isFinal();

            Bundle bundle = new Bundle();
            bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLamaAtasan);
            bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
            bundle.putString("persetujuan_" + PassedIntent.INTENT_NIPLAMA, empNipLama);
            bundle.putString("persetujuan_" + PassedIntent.INTENT_NAMA, empNama);
            bundle.putString("persetujuan_" + "id_transaksi", empIdTransaksi);
            bundle.putString("persetujuan_" + "jenis_cuti", empJenisCuti);
            bundle.putString("persetujuan_" + "tanggal_pengajuan", empTanggalPengajuan);
            bundle.putString("persetujuan_" + "tanggal_awal", empTanggalAwal);
            bundle.putString("persetujuan_" + "tanggal_akhir", empTanggalAkhir);
            bundle.putString("persetujuan_" + "jumlah_hari", empJumlahHari);
            bundle.putString("persetujuan_" + "alasan", empAlasan);
            bundle.putString("persetujuan_" + "alamat", empAlamat);
            bundle.putString("persetujuan_" + "catatan", empCatatan);
            bundle.putString("persetujuan_" + "pemroses_sebelumnya", empPemrosesSebelumnya);
            bundle.putString("persetujuan_" + "tanggal_pemroses_sebelumnya", emptanggalPemrosesSebelumnya);
            bundle.putBoolean("persetujuan_" + "is_final", empIsFinal);

            CutiPersetujuanPegawaiFragment fragment = new CutiPersetujuanPegawaiFragment();
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_fragment_area, fragment);
            fragmentTransaction.addToBackStack("fragment_daftar_persetujuan_cuti");
            fragmentTransaction.commit();

        }
    }
}
