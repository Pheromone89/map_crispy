package id.go.bpkp.mobilemapbpkp.izinkantor;

/**
 * Created by ASUS on 08/05/2018.
 */

public class IzinKantor {

    private int id;
    private String jenisIzin, tanggalMulai, tanggalSelesai, catatan, kodeProses, namaProses;

    public IzinKantor(int id, String jenisIzin, String tanggalMulai, String tanggalSelesai, String catatan, String kodeProses, String namaProses) {
        this.id = id;
        this.jenisIzin = jenisIzin;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.catatan = catatan;
        this.kodeProses = kodeProses;
        this.namaProses = namaProses;
    }

    public int getId() {
        return id;
    }

    public String getJenisIzin() {
        return jenisIzin;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getKodeProses() {
        return kodeProses;
    }

    public String getNamaProses() {
        return namaProses;
    }
}
