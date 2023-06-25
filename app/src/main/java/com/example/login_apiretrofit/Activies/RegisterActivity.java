package com.example.login_apiretrofit.Activies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_apiretrofit.Modals.RegisterUser;
import com.example.login_apiretrofit.Modals.Retro_Instance_Class;
import com.example.login_apiretrofit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity
{
    EditText reg_name,reg_email,reg_password;
    Button reg_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_name=findViewById(R.id.reg_name);
        reg_email=findViewById(R.id.reg_email);
        reg_password=findViewById(R.id.reg_password);
        reg_submit=findViewById(R.id.reg_submit);

        reg_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (network()) {
                    String name, email, password;
                    name = reg_name.getText().toString();
                    email = reg_email.getText().toString();
                    password = reg_password.getText().toString();

                    if (reg_name.getText().toString().equals(" ")) {
                        reg_name.setError("Name can't empty");
                    } else if (reg_email.getText().toString().equals(" ")) {
                        reg_email.setError("Enter the Email");
                    } else if (reg_password.getText().toString().equals(" ")) {
                        reg_password.setError("Enter the password");
                    } else {
                        if (!password.equals(password)) {
                            reg_password.setError("please enter same password");
                        } else {
                            Retro_Instance_Class.CallApi().REGISTER_USER_CALL(name, email, password).enqueue(new Callback<RegisterUser>() {
                                @Override
                                public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                                    Log.d("TTT", "onResponse: " + response.body().toString());
                                    if (response.body().getConnection() == 1) {
                                        if (response.body().getResult() == 1) {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else if (response.body().getResult() == 2) {
                                            Toast.makeText(RegisterActivity.this, "Already Register", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "User not Register", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RegisterUser> call, Throwable t) {
                                    Log.e("TTT", "onFailure: " + t.getLocalizedMessage());
                                }
                            });
                        }
                    }
                } else {

                }

            }
            });
        }


    private boolean network()
    {
        ConnectivityManager manager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}