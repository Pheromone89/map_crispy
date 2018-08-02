package id.go.bpkp.mobilemapbpkp.izinkantor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.cuti.BawahanLangsungCuti;
import id.go.bpkp.mobilemapbpkp.cuti.CutiPersetujuanPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;

/**
 * Created by ASUS on 15/02/2018.
 */

public class BawahanLangsungIzinKantorAdapter extends RecyclerView.Adapter<BawahanLangsungIzinKantorAdapter.BawahanLangsungIzinKantorViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<BawahanLangsungIzinKantor> pegawaiBawahanLangsungList;
    private String mUserToken;
    private String mNipLamaAtasan;
    private YoYo.YoYoString ropeBawahanLangsung;

    public BawahanLangsungIzinKantorAdapter(Context context,
                                            List<BawahanLangsungIzinKantor> pegawaiBawahanLangsungList,
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
    public BawahanLangsungIzinKantorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.item_list_pegawai_izin_kantor_bawahan_langsung, parent, false);
//        BawahanLangsungIzinKantorViewHolder bawahanLangsungIzinKantorViewHolder = new BawahanLangsungIzinKantorViewHolder(view);
//        return bawahanLangsungIzinKantorViewHolder;
        return null;
    }

    @Override
    public void onBindViewHolder(BawahanLangsungIzinKantorViewHolder holder, int position) {
        BawahanLangsungIzinKantor bawahanLangsungIzinKantor = pegawaiBawahanLangsungList.get(position);


//        String foto = PassedIntent.getFoto(context, bawahanLangsungIzinKantor.getNipLama());
//        String nama = bawahanLangsungIzinKantor.getNama();
//        String tanggal = bawahanLangsungIzinKantor.getTanggalAwal() + " s.d. " + bawahanLangsungIzinKantor.getTanggalAkhir();
//        String keterangan = bawahanLangsungIzinKantor.getJenisCuti() + " (" + bawahanLangsungIzinKantor.getJumlahHari() + " hari)";
//        Picasso.with(context).load(foto).into(holder.profilePictureView);
//        holder.namaView.setText(nama);
//        holder.tanggalView.setText(tanggal);
//        holder.keteranganView.setText(keterangan);

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

    class BawahanLangsungIzinKantorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profilePictureView;
        TextView namaView, tanggalView, keteranganView;
        LinearLayout rootview;

        public BawahanLangsungIzinKantorViewHolder(View itemView) {
            super(itemView);

//            rootview = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_layout);
//
//            profilePictureView = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_profic);
//            namaView = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_nama);
//            tanggalView = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_tanggal);
//            keteranganView = itemView.findViewById(R.id.pegawai_bawahan_langsung_cuti_alasan);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            int id = this.getLayoutPosition();

            BawahanLangsungIzinKantor bawahanLangsungIzinKantor = pegawaiBawahanLangsungList.get(id);
//            String empNipLama = bawahanLangsungIzinKantor.getNipLama();
//            String empNama = bawahanLangsungIzinKantor.getNama();
//            String empIdTransaksi = bawahanLangsungIzinKantor.getIdTransaksi();
//            String empJenisCuti = bawahanLangsungIzinKantor.getJenisCuti();
//            String empTanggalPengajuan = bawahanLangsungIzinKantor.getTanggalPengajuan();
//            String empTanggalAwal = bawahanLangsungIzinKantor.getTanggalAwal();
//            String empTanggalAkhir = bawahanLangsungIzinKantor.getTanggalAkhir();
//            String empJumlahHari = bawahanLangsungIzinKantor.getJumlahHari();
//            String empAlamat = bawahanLangsungIzinKantor.getAlamat();
//            String empAlasan = bawahanLangsungIzinKantor.getAlasan();
//            String empCatatan = bawahanLangsungIzinKantor.getCatatan();
//            String empPemrosesSebelumnya = bawahanLangsungIzinKantor.getPemrosesSebelumnya();
//            boolean empIsFinal = bawahanLangsungIzinKantor.isFinal();

            Bundle bundle = new Bundle();
            bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLamaAtasan);
            bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
//            bundle.putString("persetujuan_" + PassedIntent.INTENT_NIPLAMA, empNipLama);
//            bundle.putString("persetujuan_" + PassedIntent.INTENT_NAMA, empNama);
//            bundle.putString("persetujuan_" + "id_transaksi", empIdTransaksi);
//            bundle.putString("persetujuan_" + "jenis_cuti", empJenisCuti);
//            bundle.putString("persetujuan_" + "tanggal_pengajuan", empTanggalPengajuan);
//            bundle.putString("persetujuan_" + "tanggal_awal", empTanggalAwal);
//            bundle.putString("persetujuan_" + "tanggal_akhir", empTanggalAkhir);
//            bundle.putString("persetujuan_" + "jumlah_hari", empJumlahHari);
//            bundle.putString("persetujuan_" + "alasan", empAlasan);
//            bundle.putString("persetujuan_" + "alamat", empAlamat);
//            bundle.putString("persetujuan_" + "catatan", empCatatan);
//            bundle.putString("persetujuan_" + "pemroses_sebelumnya", empPemrosesSebelumnya);
//            bundle.putBoolean("persetujuan_" + "is_final", empIsFinal);

//            IzinKantorPersetujuanPegawaiFragment fragment = new IzinKantorPersetujuanPegawaiFragment();
//            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragment.setArguments(bundle);
//            fragmentTransaction.add(R.id.content_fragment_area, fragment);
//            fragmentTransaction.addToBackStack("fragment_daftar_persetujuan_izin_kantor");
//            fragmentTransaction.commit();

        }
    }
}
