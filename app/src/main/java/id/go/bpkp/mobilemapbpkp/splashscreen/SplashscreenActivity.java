package id.go.bpkp.mobilemapbpkp.splashscreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import id.go.bpkp.mobilemapbpkp.login.LoginActivity;
import id.go.bpkp.mobilemapbpkp.R;

public class SplashscreenActivity extends AppCompatActivity {
    public static final String USERNAME = "id.go.bpkp.mobilemapbpkp.extra.USERNAME";
    //Set waktu lama splashscreen
    private static int splashInterval = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        ImageView splashBottom, splashLogo;
        Animation downtoup, uptodown;
        TextView splashTitle, versionNameView;

        String versionName = "";
        try {
            versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
            versionName = "Mobile MAP BPKP versi " + versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionNameView = (TextView) findViewById(R.id.version_name);
        versionNameView.setText(versionName);

        splashLogo = (ImageView) findViewById(R.id.splash_logo) ;
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        splashLogo.setAnimation(downtoup);
        versionNameView.setAnimation(downtoup);

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                PackageManager.PERMISSION_GRANTED);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent loginIntent = new Intent(SplashscreenActivity.this,LoginActivity.class);
                loginIntent.putExtra("username", "");
                startActivity(loginIntent);
                this.finish();
            }

            private void finish() {
                // TODO Auto-generated method stub
            }
        }, splashInterval);
    }
}
