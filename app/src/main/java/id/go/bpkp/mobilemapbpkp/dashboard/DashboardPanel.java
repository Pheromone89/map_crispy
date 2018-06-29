package id.go.bpkp.mobilemapbpkp.dashboard;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.go.bpkp.mobilemapbpkp.R;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;

public class DashboardPanel {

    public static List<DashboardPanel> dashboardPanelList = new ArrayList<DashboardPanel>(Arrays.<DashboardPanel>asList(
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_ULTAH,
                    R.layout.i_dashboard_panel_ultah,
                    "dashboard_panel_ultah"
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_PROFIL,
                    R.layout.i_dashboard_panel_profil,
                    "dashboard_panel_profil",
                    "dashboard_profil_layout",
                    "dashboard_pegawai_profil_option"
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_PRESENSI,
                    R.layout.i_dashboard_panel_presensi,
                    "dashboard_panel_presensi",
                    "dashboard_presensi_layout",
                    "dashboard_pegawai_presensi_option"
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_CUTI,
                    R.layout.i_dashboard_panel_cuti,
                    "dashboard_panel_cuti",
                    "dashboard_cuti_layout",
                    "dashboard_pegawai_cuti_option"
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_JARINGAN,
                    R.layout.i_dashboard_panel_jaringan,
                    "dashboard_panel_jaringan",
                    "dashboard_jaringan_layout",
                    "dashboard_pegawai_jaringan_option"
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_TUNJANGAN,
                    R.layout.i_dashboard_panel_tunjangan,
                    "dashboard_panel_tunjangan",
                    "dashboard_tunjangan_layout",
                    "dashboard_pegawai_tunjangan_option"
            )
    ));
    int panelId;
    int panelLayout;
    String panelCardView;
    String panelBackground;
    String panelOption;

    public DashboardPanel(int panelId, int panelLayout, String panelCardView, String panelBackground, String panelOption) {
        this.panelId = panelId;
        this.panelLayout = panelLayout;
        this.panelCardView = panelCardView;
        this.panelBackground = panelBackground;
        this.panelOption = panelOption;
    }

    public DashboardPanel(int panelId, int panelLayout, String panelCardView) {
        this.panelId = panelId;
        this.panelLayout = panelLayout;
        this.panelCardView = panelCardView;
    }

    public int getPanelId() {
        return panelId;
    }

    public int getPanelLayout() {
        return panelLayout;
    }

    public String getPanelCardView() {
        return panelCardView;
    }

    public String getPanelBackground() {
        return panelBackground;
    }

    //////////////////////////
    // dashboard panel list //
    //////////////////////////

    public String getPanelOption() {
        return panelOption;
    }
}