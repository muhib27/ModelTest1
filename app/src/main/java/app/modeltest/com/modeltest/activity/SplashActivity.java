package app.modeltest.com.modeltest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import app.modeltest.com.modeltest.R;
import app.modeltest.com.modeltest.utils.AppSharedPreference;

public class SplashActivity extends AppCompatActivity {

    public static final String keyReqLogin = "reqLogin";
    public static final String keyReqRegistration = "reqRegistration";
    public static final String keyReqBasicInfo = "reqBasicInfo";
    public static final String keyBasicInfoList = "basicInfoList";
    public static final String keyChangeCalender = "changeCalender";
    public static final String keyPeriodInfoList = "periodInfoList";
    public static final String keyUserMoodList = "userMoodList";
    public static final String keyUserList = "users";
    ImageView imageView;

    @Override
    protected void onStart() {
        super.onStart();

        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                boolean isFirstTime = AppSharedPreference.getUsingFirstTime();
                Intent intent;
                if (isFirstTime) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.logo);


    }

}

