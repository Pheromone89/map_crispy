package id.go.bpkp.mobilemapbpkp.cuti;

/**
 * Created by ASUS on 15/02/2018.
 */

public class BawahanLangsungCuti {

    private int id;
    private String idTransaksi, nipLama, nama, jenisCuti, tanggalPengajuan, tanggalAwal, tanggalAkhir, jumlahHari, alasan, alamat, catatan, pemrosesSebelumnya;
    private boolean isFinal;

    public BawahanLangsungCuti(int id, String idTransaksi, String nipLama, String nama, String jenisCuti, String tanggalPengajuan, String tanggalAwal, String tanggalAkhir, String jumlahHari, String alasan, String alamat, String catatan, String pemrosesSebelumnya, Boolean isFinal) {
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

    public int getId() {
        return id;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getNipLama() {
        return nipLama;
    }

    public String getNama() {
        return nama;
    }

    public String getJenisCuti() {
        return jenisCuti;
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

    public String getJumlahHari() {
        return jumlahHari;
    }

    public String getAlasan() {
        return alasan;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getPemrosesSebelumnya() {
        return pemrosesSebelumnya;
    }

    public boolean isFinal() {
        return isFinal;
    }
}