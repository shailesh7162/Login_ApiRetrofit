package com.example.login_apiretrofit.Activies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.login_apiretrofit.Modals.LoginUser;
import com.example.login_apiretrofit.Modals.Retro_Instance_Class;
import com.example.login_apiretrofit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    EditText editemail,editpassword;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editemail=findViewById(R.id.editemail);
        editpassword=findViewById(R.id.editePassword);
        btnlogin=findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editemail.getText().toString();
                String password = editpassword.getText().toString();
                Log.d("EEE", "onClick: email="+email+"/tPass="+password);

                Retro_Instance_Class.CallApi().LOGIN_USER_CALL(email,password).enqueue(new Callback<LoginUser>() {
                    @Override
                    public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {

                    }

                    @Override
                    public void onFailure(Call<LoginUser> call, Throwable t) {

                    }
                });
            }
        });
    }
}