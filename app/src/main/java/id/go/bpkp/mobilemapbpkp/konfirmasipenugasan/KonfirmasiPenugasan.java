package id.go.bpkp.mobilemapbpkp.konfirmasipenugasan;

/**
 * Created by ASUS on 08/05/2018.
 */

public class KonfirmasiPenugasan {

    private int id;
    private String jenisPenugasan, tanggalMulai, tanggalSelesai, catatan;

    public KonfirmasiPenugasan(int id, String jenisPenugasan, String tanggalMulai, String tanggalSelesai, String catatan) {
        this.id = id;
        this.jenisPenugasan = jenisPenugasan;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.catatan = catatan;
    }

    public int getId() {
        return id;
    }

    public String getJenisPenugasan() {
        return jenisPenugasan;
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
}
