package id.go.bpkp.mobilemapbpkp.splashscreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import id.go.bpkp.mobilemapbpkp.konfigurasi.PassingIntent;
import id.go.bpkp.mobilemapbpkp.konfigurasi.konfigurasi;
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

        ImageView splashBottom;
        CardView splashLogo;
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

        splashLogo = findViewById(R.id.splash_logo);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        splashLogo.setAnimation(downtoup);
//        splashLogo.setVisibility(View.GONE);
//        konfigurasi.fadeAnimation(true, splashLogo, 1500);

//        YoYo.YoYoString ropeAnimation;
//        ropeAnimation = YoYo.with(Techniques.FadeIn)
//                .duration(2000)
//                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
//                .interpolate(new AccelerateDecelerateInterpolator())
//                .playOn(splashLogo);

        versionNameView.setAnimation(downtoup);

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                PackageManager.PERMISSION_GRANTED);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(SplashscreenActivity.this,LoginActivity.class);
                loginIntent.putExtra("username", "");
                startActivity(loginIntent);
                this.finish();
            }

            private void finish() {
            }
        }, splashInterval);
    }
}
