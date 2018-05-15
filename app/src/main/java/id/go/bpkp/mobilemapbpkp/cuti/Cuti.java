package id.go.bpkp.mobilemapbpkp.cuti;

public class Cuti {

    private int id;
    private String
            namaDiklat,
            nomorDiklat,
            tanggalDiklat,
            jenisKompetensi;

    public Cuti(int id, String namaDiklat, String nomorDiklat, String tanggalDiklat, String jenisKompetensi) {
        this.id = id;
        this.namaDiklat = namaDiklat;
        this.nomorDiklat = nomorDiklat;
        this.tanggalDiklat = tanggalDiklat;
        this.jenisKompetensi = jenisKompetensi;
    }

    public int getId() {
        return id;
    }

    public String getNamaDiklat() {
        return namaDiklat;
    }

    public String getNomorDiklat() {
        return nomorDiklat;
    }

    public String getTanggalDiklat() {
        return tanggalDiklat;
    }

    public String getJenisKompetensi() {
        return jenisKompetensi;
    }
}
