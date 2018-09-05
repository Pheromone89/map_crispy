package id.go.bpkp.mobilemapbpkp.izinkantor;

/**
 * Created by ASUS on 15/02/2018.
 */

public class BawahanLangsungIzinKantor {

    private int id;
    private String kdKatAlasan, nipLama, nama, jenisIzin, tanggalPengajuan, tanggalAwal, tanggalAkhir, keterangan, jamIzin, pemrosesSebelumnya, catatan;
    private boolean isFinal;

    public BawahanLangsungIzinKantor(int id, String kdKatAlasan, String nipLama, String nama, String jenisIzin, String tanggalPengajuan, String tanggalAwal, String tanggalAkhir, String keterangan, String jamIzin, String pemrosesSebelumnya, String catatan, boolean isFinal) {
        this.id = id;
        this.kdKatAlasan = kdKatAlasan;
        this.nipLama = nipLama;
        this.nama = nama;
        this.jenisIzin = jenisIzin;
        this.tanggalPengajuan = tanggalPengajuan;
        this.tanggalAwal = tanggalAwal;
        this.tanggalAkhir = tanggalAkhir;
        this.keterangan = keterangan;
        this.jamIzin = jamIzin;
        this.pemrosesSebelumnya = pemrosesSebelumnya;
        this.catatan = catatan;
        this.isFinal = isFinal;
    }

    public int getId() {
        return id;
    }

    public String getKdKatAlasan() {
        return kdKatAlasan;
    }

    public String getNipLama() {
        return nipLama;
    }

    public String getNama() {
        return nama;
    }

    public String getJenisIzin() {
        return jenisIzin;
    }

    public String getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public String getTanggalAwal() {
        return tanggalAwal;
    }

    public String getTanggalAkhir() {
        return tanggalAkhir;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getJamIzin() {
        return jamIzin;
    }

    public String getPemrosesSebelumnya() {
        return pemrosesSebelumnya;
    }

    public String getCatatan() {
        return catatan;
    }

    public boolean isFinal() {
        return isFinal;
    }
}