package id.go.bpkp.mobilemapbpkp.informasistrategis.indikator;

public class DistribusiPKPT {

    private int id;
    private String fokus, label, jumlah, persentase;

    public DistribusiPKPT(int id, String fokus, String label, String jumlah, String persentase) {
        this.id = id;
        this.fokus = fokus;
        this.label = label;
        this.jumlah = jumlah;
        this.persentase = persentase;
    }

    public int getId() {
        return id;
    }

    public String getFokus() {
        return fokus;
    }

    public String getLabel() {
        return label;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getPersentase() {
        return persentase;
    }
}
