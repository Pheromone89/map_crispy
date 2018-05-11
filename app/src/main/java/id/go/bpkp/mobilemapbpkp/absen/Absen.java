package id.go.bpkp.mobilemapbpkp.absen;

/**
 * Created by ASUS on 08/05/2018.
 */

public class Absen {

    private int id;
    private String tanggalAbsen, jamDatang, statusDatang, jamPulang, statusPulang, jamEfektif;

    public Absen(int id, String tanggalAbsen, String jamDatang, String statusDatang, String jamPulang, String statusPulang, String jamEfektif) {
        this.id = id;
        this.tanggalAbsen = tanggalAbsen;
        this.jamDatang = jamDatang;
        this.statusDatang = statusDatang;
        this.jamPulang = jamPulang;
        this.statusPulang = statusPulang;
        this.jamEfektif = jamEfektif;
    }

    public int getId() {
        return id;
    }

    public String getTanggalAbsen() {
        return tanggalAbsen;
    }

    public String getJamDatang() {
        return jamDatang;
    }

    public String getStatusDatang() {
        return statusDatang;
    }

    public String getJamPulang() {
        return jamPulang;
    }

    public String getStatusPulang() {
        return statusPulang;
    }

    public String getJamEfektif() {
        return jamEfektif;
    }
}
