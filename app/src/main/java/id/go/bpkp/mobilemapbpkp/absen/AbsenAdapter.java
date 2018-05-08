package id.go.bpkp.mobilemapbpkp.absen;

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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.RecyclerViewClickListener;
import id.go.bpkp.mobilemapbpkp.cuti.CutiDashboardPegawaiFragment;
import id.go.bpkp.mobilemapbpkp.cuti.PegawaiCuti;
import id.go.bpkp.mobilemapbpkp.cuti.PegawaiCutiAdapter;
import id.go.bpkp.mobilemapbpkp.konfigurasi.PassedIntent;

/**
 * Created by ASUS on 08/05/2018.
 */

public class AbsenAdapter extends RecyclerView.Adapter<AbsenAdapter.AbsenViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<Absen> absenList;

    public AbsenAdapter(
            Context context,
            List<Absen> absenList,
            RecyclerViewClickListener itemListener) {
        this.context = context;
        this.absenList = absenList;
        this.itemListener = itemListener;
    }

    @Override
    public AbsenAdapter.AbsenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_absen, parent, false);
        AbsenAdapter.AbsenViewHolder absenViewHolder = new AbsenAdapter.AbsenViewHolder(view);
        return absenViewHolder;
    }

    @Override
    public void onBindViewHolder(AbsenAdapter.AbsenViewHolder holder, int position) {
        Absen absen = absenList.get(position);

//        Picasso.with(context).load(pegawaiCuti.getFoto()).into(holder.profilePictureView);
//        holder.namaView.setText(pegawaiCuti.getNama());
//        holder.nipBaruView.setText(pegawaiCuti.getNipBaru());
    }

    @Override
    public int getItemCount() {
        return absenList.size();
    }

    class AbsenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        ImageView profilePictureView, addButton;
//        TextView namaView, nipBaruView;

        public AbsenViewHolder(View itemView) {
            super(itemView);

//            profilePictureView = itemView.findViewById(R.id.pegawai_cuti_profic);
//            namaView = itemView.findViewById(R.id.pegawai_cuti_nama);
//            nipBaruView = itemView.findViewById(R.id.pegawai_cuti_nip_baru);
//            addButton = itemView.findViewById(R.id.pegawai_cuti_plus_button);
//
//            addButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            int id = this.getLayoutPosition();

            Absen absen = absenList.get(id);
//            String empNipbaru = pegawaiCuti.getNipBaru();
//            String empUsername = pegawaiCuti.getNipBaruPisah();
//            String empNiplama = pegawaiCuti.getNipLama();
//            String empNama = pegawaiCuti.getNama();
//            String empFoto = PassedIntent.getFoto(empNiplama);

            Bundle bundle = new Bundle();
//            bundle.putString(PassedIntent.INTENT_NAMA, empNama);
//            bundle.putString(PassedIntent.INTENT_NIPLAMA, empNiplama);
//            bundle.putString(PassedIntent.INTENT_NIPBARU, empNipbaru);
//            bundle.putString(PassedIntent.INTENT_FOTO, empFoto);
//            bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
//            bundle.putString(PassedIntent.INTENT_USERNAME, empUsername);
//            bundle.putBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, true);
//            bundle.putString(PassedIntent.INTENT_NAMAATASANLANGSUNG, "testValue");
//            bundle.putString(PassedIntent.INTENT_NIPATASANLANGSUNG, "testNipValue");


            AbsenDataFragment fragment = new AbsenDataFragment();
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_fragment_area, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
