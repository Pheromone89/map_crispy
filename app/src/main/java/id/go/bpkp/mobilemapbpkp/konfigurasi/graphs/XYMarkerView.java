
package id.go.bpkp.mobilemapbpkp.konfigurasi.graphs;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

import id.go.bpkp.mobilemapbpkp.R;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class XYMarkerView extends MarkerView {

    private TextView tvContent, tvContent2;
    private IAxisValueFormatter xAxisValueFormatter, labelAxisValueFormatter;
    private String xLabelTitle;

    private DecimalFormat format;

    public XYMarkerView(Context context, IAxisValueFormatter xAxisValueFormatter, LabelAxisValueFormatter labelAxisValueFormatter, String title) {
        super(context, R.layout.custom_marker_view);

        this.xAxisValueFormatter = xAxisValueFormatter;
        this.labelAxisValueFormatter = labelAxisValueFormatter;
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent2 = (TextView) findViewById(R.id.tvContent2);
        format = new DecimalFormat("###");
        this.xLabelTitle = title;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(xLabelTitle + ": " + labelAxisValueFormatter.getFormattedValue(e.getX(), null));
        tvContent2.setText("Jumlah: " + format.format(e.getY()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
