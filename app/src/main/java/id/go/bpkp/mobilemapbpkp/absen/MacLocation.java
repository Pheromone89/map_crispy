package id.go.bpkp.mobilemapbpkp.absen;

public class MacLocation {
    String label, lantai, location, keterangan;

    public MacLocation(String label, String lantai, String location, String keterangan) {

        this.label = label;
        this.lantai = lantai;
        this.location = location;
        this.keterangan = keterangan;
    }

    public String getLabel() {
        return label;
    }

    public String getLantai() {
        return lantai;
    }

    public String getLocation() {
        return location;
    }

    public String getKeterangan() {
        return keterangan;
    }
}
