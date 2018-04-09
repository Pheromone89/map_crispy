package id.go.bpkp.mobilemapbpkp.monitoring;

/**
 * Created by ASUS on 12/02/2018.
 */

public class Unit {

    private int id;
    private String
            nomorSK,
            tmtSK,
            kotaUnit,
            namaUnit,
            tmtUnit;

    public Unit(int id, String nomorSK, String tmtSK, String kotaUnit, String namaUnit, String tmtUnit) {
        this.id = id;
        this.nomorSK = nomorSK;
        this.tmtSK = tmtSK;
        this.kotaUnit = kotaUnit;
        this.namaUnit = namaUnit;
        this.tmtUnit = tmtUnit;
    }

    public int getId() {
        return id;
    }

    public String getNomorSK() {
        return nomorSK;
    }

    public String getTmtSK() {
        return tmtSK;
    }

    public String getKotaUnit() {
        return kotaUnit;
    }

    public String getNamaUnit() {
        return namaUnit;
    }

    public String getTmtUnit() {
        return tmtUnit;
    }
}
