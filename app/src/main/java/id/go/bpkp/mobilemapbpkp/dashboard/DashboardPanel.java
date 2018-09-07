package id.go.bpkp.mobilemapbpkp.dashboard;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.View;
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
                    "ultah",
                    R.layout.i_dashboard_panel_ultah
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_PROFIL,
                    "profil",
                    R.layout.i_dashboard_panel_profil
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_PRESENSI,
                    "presensi",
                    R.layout.i_dashboard_panel_presensi
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_CUTI,
                    "cuti",
                    R.layout.i_dashboard_panel_cuti
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_JARINGAN,
                    "jaringan",
                    R.layout.i_dashboard_panel_jaringan
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_TUNJANGAN,
                    "tunjangan",
                    R.layout.i_dashboard_panel_tunjangan
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_NOTIFIKASIATASAN,
                    "notifikasi_atasan",
                    R.layout.i_dashboard_panel_notifikasi_atasan
            ),
            new DashboardPanel(
                    konfigurasi.DASHBOARD_PANEL_HOTSPOT,
                    "hotspot",
                    R.layout.i_dashboard_panel_hotspot
            )
    ));
    int panelId;
    int panelLayout;
    String panelName;

    public DashboardPanel(int panelId, String panelName, int panelLayout) {
        this.panelId = panelId;
        this.panelName = panelName;
        this.panelLayout = panelLayout;
    }

    public int getPanelId() {
        return panelId;
    }

    public int getPanelLayout() {
        return panelLayout;
    }

    public static List<DashboardPanel> getDashboardPanelList() {
        return dashboardPanelList;
    }

    public LinearLayout getPanelCardView(Context context, View rootView) {
        String panelCardIdString = "dashboard_panel_" + panelName;
        int panelCardIdInt = context.getResources().getIdentifier(panelCardIdString, "id", DashboardPegawaiFragment.PACKAGE_NAME);
        LinearLayout panelCard = rootView.findViewById(panelCardIdInt);
        return panelCard;
    }

    public LinearLayout getPanelBackground(Context context, View rootView) {
        String backgroundIdString = "dashboard_pegawai_" + panelName + "_background";
        int backgroundIdInt = context.getResources().getIdentifier(backgroundIdString, "id", DashboardPegawaiFragment.PACKAGE_NAME);
        LinearLayout background = rootView.findViewById(backgroundIdInt);
        return background;
    }

    public CardView getPanelOption(Context context, View rootView) {
        String optionIdString = "dashboard_pegawai_" + panelName + "_option";
        int optionIdInt = context.getResources().getIdentifier(optionIdString, "id", DashboardPegawaiFragment.PACKAGE_NAME);
        CardView option = rootView.findViewById(optionIdInt);
        return option;
    }

    public LinearLayout getPanelOptionProgressbar(Context context, View rootView) {
        String progressBarIdString = "dashboard_pegawai_" + panelName + "_option_progressbar";
        int progressBarIdInt = context.getResources().getIdentifier(progressBarIdString, "id", DashboardPegawaiFragment.PACKAGE_NAME);
        LinearLayout progressBarView = rootView.findViewById(progressBarIdInt);
        return progressBarView;
    }
}