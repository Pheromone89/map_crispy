package id.go.bpkp.mobilemapbpkp.monitoring;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 11/02/2018.
 */

public class Diklat implements Parcelable {

    public static final Parcelable.Creator<Diklat> CREATOR
            = new Parcelable.Creator<Diklat>() {
        public Diklat createFromParcel(Parcel in) {
            return new Diklat(in);
        }

        public Diklat[] newArray(int size) {
            return new Diklat[size];
        }
    };
    private int id, mData;
    private String
            namaDiklat,
            nomorDiklat,
            tanggalDiklat,
            jenisKompetensi;

    public Diklat(int id, String namaDiklat, String nomorDiklat, String tanggalDiklat, String jenisKompetensi) {
        this.id = id;
        this.namaDiklat = namaDiklat;
        this.nomorDiklat = nomorDiklat;
        this.tanggalDiklat = tanggalDiklat;
        this.jenisKompetensi = jenisKompetensi;
    }

    /**
     * recreate object from parcel
     */
    private Diklat(Parcel in) {
        mData = in.readInt();
    }

    public String getJenisKompetensi() {
        return jenisKompetensi;
    }

    public int getId() {
        return id;
    }

    public String getNamaDiklat() {
        return namaDiklat;
    }

    public String getNomorDiklat() {
        return nomorDiklat;
    }

    public String getTanggalDiklat() {
        return tanggalDiklat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mData);
    }
}
