package id.go.bpkp.mobilemapbpkp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfDownloadActivity extends AppCompatActivity {

    String downloadLink;
    String nipBaru;
    String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent downloadIntent = getIntent();
        downloadLink = downloadIntent.getStringExtra("download_link");
        nipBaru = downloadIntent.getStringExtra("nip_baru");
        nama = downloadIntent.getStringExtra("nama");

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "pdf");
        folder.mkdir();
        File file = new File(folder, nama+".pdf");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Downloader.DownloadFile(downloadLink, file);
        Toast.makeText(this, "DRH downloaded", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, downloadLink, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, extStorageDirectory, Toast.LENGTH_SHORT).show();
//        showPdf();
    }
    public void showPdf()
    {
        File file = new File(Environment.getExternalStorageDirectory()+"/Mypdf/"+nama+".pdf");
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }
}
