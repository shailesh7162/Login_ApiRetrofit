package com.example.login_apiretrofit.Activies;

import static com.example.login_apiretrofit.Activies.Splash_ScreenActivity.editor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_apiretrofit.Modals.LoginUser;
import com.example.login_apiretrofit.Modals.Retro_Instance_Class;
import com.example.login_apiretrofit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity
{
   EditText email,password;
   Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        loginbtn=findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semail,spassword;
                 semail = email.getText().toString();
                 spassword = password.getText().toString();

                Log.d("TTT", "onClick: email="+email+"/tPass="+password);

                Retro_Instance_Class.CallApi().LOGIN_USER_CALL(semail,spassword).enqueue(new Callback<LoginUser>() {

                    @Override
                    public void onResponse(Call<LoginUser> call, Response<LoginUser> response)
                    {
                        if(response.body().getResult()==1)
                        {
                            editor.putBoolean("isLogin",true);
                            editor.putString("name",response.body().getUserdata().getName());
                            editor.putString("email",response.body().getUserdata().getEmail());
                            editor.putInt("usrid", response.body().getUserdata().getId());
                            editor.apply();
                            editor.commit();

                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "account not found ", Toast.LENGTH_LONG).show();
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

    }
}