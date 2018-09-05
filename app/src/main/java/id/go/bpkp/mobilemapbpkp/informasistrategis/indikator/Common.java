package id.go.bpkp.mobilemapbpkp.informasistrategis.indikator;

public class Common {

    private int id;
    private String label, nilai;

    public Common(int id, String label, String nilai) {
        this.id = id;
        this.label = label;
        this.nilai = nilai;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getNilai() {
        return nilai;
    }
}
