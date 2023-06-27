package com.example.login_apiretrofit.Activies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_apiretrofit.R;

public class Splash_ScreenActivity extends AppCompatActivity
{
    private Runnable runnable;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    boolean isLogin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferences=getSharedPreferences("myPref",MODE_PRIVATE);
        editor=preferences.edit();

        isLogin=preferences.getBoolean("isLogin",false);
        Log.e("EEE", "onCreate: "+isLogin );
        runnable = new Runnable() {
            @Override
            public void run() {
                if(isLogin) {
                    Intent intent = new Intent(Splash_ScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.e("EEE", "onCreate: "+isLogin );
                    Intent intent = new Intent(Splash_ScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        new Handler().postDelayed(runnable,5000);

    }

}