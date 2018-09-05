package id.go.bpkp.mobilemapbpkp.informasistrategis.realisasipkpt;

public class RealisasiPkpt {
    String labelPkpt, realisasiPkpt, totalPkpt, persentasiPkpt;

    public RealisasiPkpt(String labelPkpt, String realisasiPkpt, String totalPkpt, String persentasiPkpt) {
        this.labelPkpt = labelPkpt;
        this.realisasiPkpt = realisasiPkpt;
        this.totalPkpt = totalPkpt;
        this.persentasiPkpt = persentasiPkpt;
    }

    public String getLabelPkpt() {
        return labelPkpt;
    }

    public String getRealisasiPkpt() {
        return realisasiPkpt;
    }

    public String getTotalPkpt() {
        return totalPkpt;
    }

    public String getPersentasiPkpt() {
        return persentasiPkpt;
    }
}