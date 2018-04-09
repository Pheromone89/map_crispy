package id.go.bpkp.mobilemapbpkp.cuti;

/**
 * Created by ASUS on 15/02/2018.
 */

public class PegawaiCuti {

    private int id;
    private String nama, nipLama, nipBaru, nipBaruPisah, foto;

    public PegawaiCuti(int id, String nama, String nipLama, String nipBaru, String nipBaruPisah, String foto) {
        this.id = id;
        this.nama = nama;
        this.nipLama = nipLama;
        this.nipBaru = nipBaru;
        this.nipBaruPisah = nipBaruPisah;
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

    public String getFoto() {
        return foto;
    }
}