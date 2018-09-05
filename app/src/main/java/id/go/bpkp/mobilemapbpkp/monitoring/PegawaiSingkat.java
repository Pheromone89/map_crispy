package id.go.bpkp.mobilemapbpkp.monitoring;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 11/02/2018.
 */

public class PegawaiSingkat {

    private int id;
    private String nama, nipLama, nipBaru, nipBaruPisah, jabatanSingkat, unit, foto;

    public static List<PegawaiSingkat> pegawaiSingkatList = new ArrayList<>();

    public PegawaiSingkat(int id, String nama, String nipLama, String nipBaru, String nipBaruPisah, String jabatanSingkat, String unit, String foto) {
        this.id = id;
        this.nama = nama;
        this.nipLama = nipLama;
        this.nipBaru = nipBaru;
        this.nipBaruPisah = nipBaruPisah;
        this.jabatanSingkat = jabatanSingkat;
        this.unit = unit;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNipLama() {
        return nipLama;
    }

    public String getNipBaru() {
        return nipBaru;
    }

    public String getNipBaruPisah() {
        return nipBaruPisah;
    }

    public String getJabatanSingkat() {
        return jabatanSingkat;
    }

    public String getUnit() {
        return unit;
    }

    public String getFoto() {
        return foto;
    }

    public String getAllDetail() {
        return id + " " + nama + " " + nipLama + " " + nipBaru + " " + jabatanSingkat + " " + unit;
    }
}
