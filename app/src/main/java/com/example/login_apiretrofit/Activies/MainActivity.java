package com.example.login_apiretrofit.Activies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.login_apiretrofit.Modals.LoginUser;
import com.example.login_apiretrofit.Modals.Retro_Instance_Class;
import com.example.login_apiretrofit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
   EditText login_email,login_password;
   Button loginbtn;
   TextView login_sign,login_continus;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_email=findViewById(R.id.login_email);
        login_password=findViewById(R.id.login_password);
        loginbtn=findViewById(R.id.loginbtn);
        login_sign=findViewById(R.id.login_sign);
        login_continus=findViewById(R.id.login_continus);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                Log.d("TTT", "onClick: email="+email+"/tPass="+password);

                Retro_Instance_Class.CallApi().LOGIN_USER_CALL(email,password).enqueue(new Callback<LoginUser>() {
                    private SharedPreferences.Editor editor;

                    @Override
                    public void onResponse(Call<LoginUser> call, Response<LoginUser> response)
                    {
                        if(response.body().getResult()==1)
                        {
                            Log.d("TTT", "onResponse: " + response.body().getUserdata().getId());
                            Log.d("TTT", "onResponse: " + response.body().getResult());

                            editor.putBoolean("isLogin",true);
                            editor.putString("name",response.body().getUserdata().getName());
                            editor.putString("email",response.body().getUserdata().getEmail());
                            editor.putInt("uid", Integer.parseInt(response.body().getUserdata().getId()));
                            editor.commit();

                            Intent intent=new Intent(MainActivity.this, Ecommerce_activity.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginUser> call, Throwable t)
                    {
                        Log.e("TTT", "onFailure: "+t.getLocalizedMessage());
                    }
                });
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, RagisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}