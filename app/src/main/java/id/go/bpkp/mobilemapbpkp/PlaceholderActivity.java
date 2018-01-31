package id.go.bpkp.mobilemapbpkp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceholderActivity extends AppCompatActivity {

    String url;
    String val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        Intent i = getIntent();
        url = i.getStringExtra("url");
        val = i.getStringExtra("val");

        TextView textView = (TextView) findViewById(R.id.namatest);
        textView.setText(val);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlaceholderActivity.this, url, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
