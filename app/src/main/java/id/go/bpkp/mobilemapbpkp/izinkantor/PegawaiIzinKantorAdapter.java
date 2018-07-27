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

public class PegawaiIzinKantorAdapter extends RecyclerView.Adapter<PegawaiIzinKantorAdapter.PegawaiIzinKantorViewHolder> {

    private static RecyclerViewClickListener itemListener;
    private Context context;
    private List<PegawaiIzinKantor> pegawaiIzinKantorList;
    private String mUserToken;
    private YoYo.YoYoString ropePegawaiIzinKantor;

    public PegawaiIzinKantorAdapter(Context context,
                                    List<PegawaiIzinKantor> pegawaiIzinKantorList,
                                    RecyclerViewClickListener itemListener,
                                    String mUserToken) {
        this.context = context;
        this.pegawaiIzinKantorList = pegawaiIzinKantorList;
        this.itemListener = itemListener;
        this.mUserToken = mUserToken;
    }

    @Override
    public PegawaiIzinKantorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_pegawai_izin_kantor, parent, false);
        PegawaiIzinKantorViewHolder pegawaiIzinKantorViewHolder = new PegawaiIzinKantorViewHolder(view);
        return pegawaiIzinKantorViewHolder;
    }

    @Override
    public void onBindViewHolder(PegawaiIzinKantorViewHolder holder, int position) {
        PegawaiIzinKantor pegawaiIzinKantor = pegawaiIzinKantorList.get(position);

        Picasso.with(context).load(pegawaiIzinKantor.getFoto()).into(holder.profilePictureView);
        holder.namaView.setText(pegawaiIzinKantor.getNama());
        String nip = pegawaiIzinKantor.getNipBaru() + " / " + pegawaiIzinKantor.getNipLama();
        holder.nipBaruView.setText(nip);

        ropePegawaiIzinKantor = YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(holder.rootview);
    }

    @Override
    public int getItemCount() {
        return pegawaiIzinKantorList.size();
    }

    class PegawaiIzinKantorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profilePictureView, addButton;
        TextView namaView, nipBaruView;
        LinearLayout rootview;

        public PegawaiIzinKantorViewHolder(View itemView) {
            super(itemView);

            rootview = itemView.findViewById(R.id.pegawai_izin_kantor_layout);

            profilePictureView = itemView.findViewById(R.id.pegawai_izin_kantor_profic);
            namaView = itemView.findViewById(R.id.pegawai_izin_kantor_nama);
            nipBaruView = itemView.findViewById(R.id.pegawai_izin_kantor_nip_baru);
            addButton = itemView.findViewById(R.id.pegawai_izin_kantor_plus_button);

            addButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
            int id = this.getLayoutPosition();

            PegawaiIzinKantor pegawaiIzinKantor = pegawaiIzinKantorList.get(id);
            String empNipbaru = pegawaiIzinKantor.getNipBaru();
            String empUsername = pegawaiIzinKantor.getNipBaruPisah();
            String empNiplama = pegawaiIzinKantor.getNipLama();
            String empNama = pegawaiIzinKantor.getNama();
            String empFoto = PassedIntent.getFoto(context, empNiplama);

            Bundle bundle = new Bundle();
            bundle.putString(PassedIntent.INTENT_NAMA, empNama);
            bundle.putString(PassedIntent.INTENT_NIPLAMA, empNiplama);
            bundle.putString(PassedIntent.INTENT_NIPBARU, empNipbaru);
            bundle.putString(PassedIntent.INTENT_FOTO, empFoto);
            bundle.putString(PassedIntent.INTENT_USERTOKEN, mUserToken);
            bundle.putString(PassedIntent.INTENT_USERNAME, empUsername);
            bundle.putBoolean(PassedIntent.INTENT_TIDAKPUNYAATASANLANGSUNG, true);
            bundle.putString(PassedIntent.INTENT_NAMAATASANLANGSUNG, "testValue");
            bundle.putString(PassedIntent.INTENT_NIPATASANLANGSUNG, "testNipValue");


            IzinKantorDashboardPegawaiFragment fragment = new IzinKantorDashboardPegawaiFragment();
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_fragment_area, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}
