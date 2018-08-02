package id.go.bpkp.mobilemapbpkp.izinkantor;

/**
 * Created by ASUS on 15/02/2018.
 */

public class BawahanLangsungIzinKantor {

    private int id;
    private String idTransaksi, nipLama, nama, jenisCuti, tanggalPengajuan, tanggalAwal, tanggalAkhir, jumlahHari, alasan, alamat, catatan, pemrosesSebelumnya;
    private boolean isFinal;

    public BawahanLangsungIzinKantor(int id, String idTransaksi, String nipLama, String nama, String jenisCuti, String tanggalPengajuan, String tanggalAwal, String tanggalAkhir, String jumlahHari, String alasan, String alamat, String catatan, String pemrosesSebelumnya, Boolean isFinal) {
        this.id = id;
        this.idTransaksi = idTransaksi;
        this.nipLama = nipLama;
        this.nama = nama;
        this.jenisCuti = jenisCuti;
        this.tanggalPengajuan = tanggalPengajuan;
        this.tanggalAwal = tanggalAwal;
        this.tanggalAkhir = tanggalAkhir;
        this.jumlahHari = jumlahHari;
        this.alasan = alasan;
        this.alamat = alamat;
        this.catatan = catatan;
        this.pemrosesSebelumnya = pemrosesSebelumnya;
        this.isFinal = isFinal;
    }
}