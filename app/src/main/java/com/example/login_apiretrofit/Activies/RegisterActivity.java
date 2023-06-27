package com.example.login_apiretrofit.Activies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_apiretrofit.Modals.RegisterData;
import com.example.login_apiretrofit.Retro_Instance_Class;
import com.example.login_apiretrofit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity
{
    EditText name,email,password;
    Button reg_submit;
    String sname,semail,spassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.reg_name);
        email=findViewById(R.id.reg_email);
        password=findViewById(R.id.reg_password);
        reg_submit=findViewById(R.id.reg_submit);

       reg_submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(name.getText().toString().equals("")){
                   name.setError("empty field ",getResources().getDrawable(R.drawable.baseline_error_24));

               }
               if(email.getText().toString().equals("")){
                   email.setError("empty field ",getResources().getDrawable(R.drawable.baseline_error_24));

               }
               if(password.getText().toString().equals("")){
                   password.setError("empty field ",getResources().getDrawable(R.drawable.baseline_error_24));

               }
               sname=name.getText().toString();
               semail=email.getText().toString();
               spassword=password.getText().toString();

               Retro_Instance_Class.CallApi().REGISTER_USER_CALL(sname,semail,spassword).enqueue(new Callback<RegisterData>() {
                   @Override
                   public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                       Log.d("TAG", "onResponse: "+response.body().toString());

                       Log.d("con", "onResponse: Connection"+ response.body().getConnection());
                       if(response.body().getResult()==1){

                           Toast.makeText(RegisterActivity.this, "successfully register", Toast.LENGTH_LONG).show();
                           Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                           startActivity(intent);
                           finish();
                       } else if (response.body().getResult()==2) {
                           Toast.makeText(RegisterActivity.this, "Already register", Toast.LENGTH_LONG).show();

                       }else if(response.body().getResult()==0){
                           Toast.makeText(RegisterActivity.this, "somthing went wrong", Toast.LENGTH_LONG).show();

                       }
                   }

                   @Override
                   public void onFailure(Call<RegisterData> call, Throwable t)
                   {

                   }
               });

           }
       });
    }

}