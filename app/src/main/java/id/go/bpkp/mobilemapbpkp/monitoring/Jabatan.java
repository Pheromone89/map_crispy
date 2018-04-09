package id.go.bpkp.mobilemapbpkp.monitoring;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 20/03/2018.
 */

public class Jabatan implements Parcelable {

    public static final Parcelable.Creator<Jabatan> CREATOR
            = new Parcelable.Creator<Jabatan>() {
        public Jabatan createFromParcel(Parcel in) {
            return new Jabatan(in);
        }

        public Jabatan[] newArray(int size) {
            return new Jabatan[size];
        }
    };
    private int id, mData;
    private String
            namaJabatan,
            tmtJabatan,
            nomorSK,
            tanggalSK;

    public Jabatan(int id, String namaJabatan, String tmtJabatan, String nomorSK, String tanggalSK) {
        this.id = id;
        this.namaJabatan = namaJabatan;
        this.tmtJabatan = tmtJabatan;
        this.nomorSK = nomorSK;
        this.tanggalSK = tanggalSK;
    }

    /**
     * recreate object from parcel
     */
    private Jabatan(Parcel in) {
        mData = in.readInt();
    }

    public int getId() {
        return id;
    }

    public int getmData() {
        return mData;
    }

    public String getNamaJabatan() {
        return namaJabatan;
    }

    public String getTmtJabatan() {
        return tmtJabatan;
    }

    public String getNomorSK() {
        return nomorSK;
    }

    public String getTanggalSK() {
        return tanggalSK;
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
