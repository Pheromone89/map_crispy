package id.go.bpkp.mobilemapbpkp.absen;

import java.util.ArrayList;
import java.util.List;

import id.go.bpkp.mobilemapbpkp.monitoring.PegawaiSingkat;

/**
 * Created by ASUS on 08/05/2018.
 */

public class AbsenBawahan {

    private int id;
    private String nipLama, nama, jamDatang, kodeDatang, jamPulang, kodePulang;

    public static List<AbsenBawahan> absenBawahanList = new ArrayList<>();

    public AbsenBawahan(int id, String nipLama, String nama, String jamDatang, String kodeDatang, String jamPulang, String kodePulang) {
        this.id = id;
        this.nipLama = nipLama;
        this.nama = nama;
        this.jamDatang = jamDatang;
        this.kodeDatang = kodeDatang;
        this.jamPulang = jamPulang;
        this.kodePulang = kodePulang;
    }

    public int getId() {
        return id;
    }

    public String getNipLama() {
        return nipLama;
    }

    public String getNama() {
        return nama;
    }

    public String getJamDatang() {
        return jamDatang;
    }

    public String getKodeDatang() {
        return kodeDatang;
    }

    public String getJamPulang() {
        return jamPulang;
    }

    public String getKodePulang() {
        return kodePulang;
    }
}