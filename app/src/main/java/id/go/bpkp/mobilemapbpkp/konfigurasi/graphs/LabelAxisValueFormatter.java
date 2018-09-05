package id.go.bpkp.mobilemapbpkp.konfigurasi.graphs;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LabelAxisValueFormatter implements IAxisValueFormatter {

    private DecimalFormat mFormat;
    private ArrayList<String> arrayList;

    public LabelAxisValueFormatter(ArrayList<String> arrayList) {
        mFormat = new DecimalFormat("###,###,###,##0");
        this.arrayList = arrayList;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return arrayList.get((int) value);
    }
}
