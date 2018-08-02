package id.go.bpkp.mobilemapbpkp.cuti;

public class Cuti {

    private int id;
    private String jenisCuti, tanggalMulai, tanggalSelesai, alasan, jumlahHari, kodeProses, namaProses;

    public Cuti(int id, String jenisCuti, String tanggalMulai, String tanggalSelesai, String alasan, String jumlahHari, String kodeProses, String namaProses) {
        this.id = id;
        this.jenisCuti = jenisCuti;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.alasan = alasan;
        this.jumlahHari = jumlahHari;
        this.kodeProses = kodeProses;
        this.namaProses = namaProses;
    }

    public int getId() {
        return id;
    }

    public String getJenisCuti() {
        return jenisCuti;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public String getAlasan() {
        return alasan;
    }

    public String getJumlahHari() {
        return jumlahHari;
    }

    public String getKodeProses() {
        return kodeProses;
    }

    public String getNamaProses() {
        return namaProses;
    }
}
