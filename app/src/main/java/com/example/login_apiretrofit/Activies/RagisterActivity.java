package com.example.login_apiretrofit.Activies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.login_apiretrofit.R;

public class RagisterActivity extends AppCompatActivity
{
    EditText reg_name,reg_email,reg_password;
    Button reg_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister);
        reg_name=findViewById(R.id.reg_name);
        reg_email=findViewById(R.id.reg_email);
        reg_password=findViewById(R.id.reg_password);
        reg_submit=findViewById(R.id.reg_submit);

        reg_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (network())
                {

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