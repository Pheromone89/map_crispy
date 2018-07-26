package id.go.bpkp.mobilemapbpkp.konfigurasi;

import android.content.SharedPreferences;

/**
 * Created by ASUS on 12/03/2018.
 */

public class SettingPrefs {

    public static final String SETTING_BELUMSETNOHP = "belum_Set_no_hp";

    // dashboard panel
    public static final String DASHBOARDPANEL_PROFIL = "dashboard_panel_" + konfigurasi.DASHBOARD_PANEL_PROFIL;
    public static final String DASHBOARDPANEL_JARINGAN = "dashboard_panel_" + konfigurasi.DASHBOARD_PANEL_JARINGAN;
    public static final String DASHBOARDPANEL_PRESENSI = "dashboard_panel_" + konfigurasi.DASHBOARD_PANEL_PRESENSI;
    public static final String DASHBOARDPANEL_TUNJANGAN = "dashboard_panel_" + konfigurasi.DASHBOARD_PANEL_TUNJANGAN;
    public static final String DASHBOARDPANEL_CUTI = "dashboard_panel_" + konfigurasi.DASHBOARD_PANEL_CUTI;
    public static final String DASHBOARDPANEL_ULTAH = "dashboard_panel_" + konfigurasi.DASHBOARD_PANEL_ULTAH;
    // saved json profil pegawai
    public static final String SAVEDJSON_DATAPOKOK = "saved_json_data_pokok";
    public static final String SAVEDJSON_DATADIKLAT = "saved_json_data_diklat";
    public static final String SAVEDJSON_DATAJABATAN = "saved_json_data_jabatan";
    public static final String SAVEDJSON_DATAUNIT = "saved_json_data_unit";

    public static void clearProfil(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SAVEDJSON_DATAPOKOK, null);
        editor.putString(SAVEDJSON_DATADIKLAT, null);
        editor.putString(SAVEDJSON_DATAJABATAN, null);
        editor.putString(SAVEDJSON_DATAUNIT, null);
        editor.apply();
    }
}
