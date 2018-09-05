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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.cuti.BawahanLangsungCuti;
import id.go.bpkp.mobilemapbpkp.cuti.CutiPersetujuanPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

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
        View view = inflater.inflate(R.layout.item_list_pegawai_izin_kantor_bawahan_langsung, parent, false);
        BawahanLangsungIzinKantorViewHolder bawahanLangsungIzinKantorViewHolder = new BawahanLangsungIzinKantorViewHolder(view);
        return bawahanLangsungIzinKantorViewHolder;
    }

    @Override
    public void onBindViewHolder(BawahanLangsungIzinKantorViewHolder holder, int position) {
        BawahanLangsungIzinKantor bawahanLangsungIzinKantor = pegawaiBawahanLangsungList.get(position);


        String foto = PassedIntent.getFoto(context, bawahanLangsungIzinKantor.getNipLama());
        String nama = bawahanLangsungIzinKantor.getNama();
        String jenisIzin = bawahanLangsungIzinKantor.getJenisIzin();
        String tanggalAwal = bawahanLangsungIzinKantor.getTanggalAwal();
        String tanggalAkhir = bawahanLangsungIzinKantor.getTanggalAkhir();
        String tanggal = null;
        String jamIzin = bawahanLangsungIzinKantor.getJamIzin();
        if (jamIzin.equals("null")) {
            jamIzin = "";
        } else {
            jamIzin = " pukul: " + jamIzin;
        }
        switch (jenisIzin) {
            case "Datang Terlambat":
                tanggal = tanggalAwal + jamIzin;
                break;
            case "Pulang Cepat":
                tanggal = tanggalAwal + jamIzin;
                break;
            case "Absensi Fingerprint":
                tanggal = tanggalAwal;
                break;
            case "Tidak Masuk Kantor":
                tanggal = tanggalAwal + " s.d. " + tanggalAkhir;
                break;
        }
        String keterangan = bawahanLangsungIzinKantor.getKeterangan();
        if (keterangan.equals("null")) {
            keterangan = "-";
        }
        Picasso.with(context).load(foto).into(holder.profilePictureView);
        holder.namaView.setText(nama);
        holder.jenisIzinView.setText(jenisIzin);
        holder.tanggalView.setText(tanggal);
        holder.keteranganView.setText(keterangan);

        ropeBawahanLangsung = YoYo.with(Techniques.FadeIn)
                .duration(konfigurasi.animationDurationShort)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootview);
    }

    @Override
    public int getItemCount() {
        return pegawaiBawahanLangsungList.size();
    }

    class BawahanLangsungIzinKantorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profilePictureView;
        TextView namaView, tanggalView, keteranganView, jenisIzinView;
        LinearLayout rootview;


        public BawahanLangsungIzinKantorViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.pegawai_bawahan_langsung_izin_kantor_layout);
//
            profilePictureView = itemView.findViewById(R.id.pegawai_bawahan_langsung_izin_kantor_profic);
            jenisIzinView = itemView.findViewById(R.id.pegawai_bawahan_langsung_izin_kantor_jenis_izin);
            namaView = itemView.findViewById(R.id.pegawai_bawahan_langsung_izin_kantor_nama);
            tanggalView = itemView.findViewById(R.id.pegawai_bawahan_langsung_izin_kantor_tanggal);
            keteranganView = itemView.findViewById(R.id.pegawai_bawahan_langsung_izin_kantor_alasan);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            int id = this.getLayoutPosition();

            BawahanLangsungIzinKantor bawahanLangsungIzinKantor = pegawaiBawahanLangsungList.get(id);
            int empIdTransaksi = bawahanLangsungIzinKantor.getId();
            String empkdKatAlasan = bawahanLangsungIzinKantor.getKdKatAlasan();
            String empnipLama = bawahanLangsungIzinKantor.getNipLama();
            String empnama = bawahanLangsungIzinKantor.getNama();
            String empjenisIzin = bawahanLangsungIzinKantor.getJenisIzin();
            String emptanggalPengajuan = bawahanLangsungIzinKantor.getTanggalPengajuan();
            String emptanggalAwal = bawahanLangsungIzinKantor.getTanggalAwal();
            String emptanggalAkhir = bawahanLangsungIzinKantor.getTanggalAkhir();
            String empketerangan = bawahanLangsungIzinKantor.getKeterangan();
            String empjamIzin = bawahanLangsungIzinKantor.getJamIzin();
            String empPemrosesSebelumnya = bawahanLangsungIzinKantor.getPemrosesSebelumnya();
            String empCatatan = bawahanLangsungIzinKantor.getCatatan();
            boolean empIsFinal = bawahanLangsungIzinKantor.isFinal();

            Bundle bundle = new Bundle();
            bundle.putString(PassedIntent.INTENT_NIPLAMA, mNipLamaAtasan);
            bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
            bundle.putInt("persetujuan_" + "id_transaksi", empIdTransaksi);
            bundle.putString("persetujuan_" + "kd_kat_alasan", empkdKatAlasan);
            bundle.putString("persetujuan_" + "nip_lama", empnipLama);
            bundle.putString("persetujuan_" + "nama", empnama);
            bundle.putString("persetujuan_" + "jenis_izin", empjenisIzin);
            bundle.putString("persetujuan_" + "tanggal_pengajuan", emptanggalPengajuan);
            bundle.putString("persetujuan_" + "tanggal_awal", emptanggalAwal);
            bundle.putString("persetujuan_" + "tanggal_akhir", emptanggalAkhir);
            bundle.putString("persetujuan_" + "keterangan", empketerangan);
            bundle.putString("persetujuan_" + "jam_izin", empjamIzin);
            bundle.putString("persetujuan_" + "pemroses_sebelumnya", empPemrosesSebelumnya);
            bundle.putString("persetujuan_" + "catatan", empCatatan);
            bundle.putBoolean("persetujuan_" + "is_final", empIsFinal);

            IzinKantorPersetujuanPegawaiFragment fragment = new IzinKantorPersetujuanPegawaiFragment();
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_fragment_area, fragment);
            fragmentTransaction.addToBackStack("fragment_daftar_persetujuan_izin_kantor");
            fragmentTransaction.commit();

        }
    }
}
