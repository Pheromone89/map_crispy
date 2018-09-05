package id.go.bpkp.mobilemapbpkp.hotspot;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 11/02/2018.
 */

public class Hotspot{

    private int id, mData;
    private String
            niplama,
            tanggal,
            datang,
            pulang,
            nama,
            namanip,
            nipbaru,
            unit,
            kode_unit,
            acctid,
            foto;

    public static List<Hotspot> hotspotList = new ArrayList<>();

    public Hotspot(int id, String niplama, String tanggal, String datang, String pulang,
                   String nama, String namanip, String nipbaru, String unit, String kode_unit, String acctid, String foto) {
        this.id = id;
        this.niplama = niplama;
        this.tanggal = tanggal;
        this.datang = datang;
        this.pulang = pulang;
        this.nama = nama;
        this.namanip = namanip;
        this.nipbaru = nipbaru;
        this.unit = unit;
        this.kode_unit = kode_unit;
        this.acctid = acctid;
        this.foto = foto;
    }

    /**
     * recreate object from parcel
     */
    private Hotspot(Parcel in) {
        mData = in.readInt();
    }

    public int getId() {
        return id;
    }

    public String getNiplama() {
        return niplama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getDatang() {
        return datang;
    }

    public String getPulang() {
        return pulang;
    }

    public String getAcctid() {
        return acctid;
    }

    public String getNama() {
        return nama;
    }

    public String getNamanip() {
        return namanip;
    }

    public String getNipbaru() {
        return nipbaru;
    }

    public String getUnit() {
        return unit;
    }

    public String getKode_unit() {
        return kode_unit;
    }

    public String getFoto() {
        return foto;
    }
}
