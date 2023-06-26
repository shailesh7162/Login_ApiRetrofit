package com.example.login_apiretrofit.Activies;

import static com.example.login_apiretrofit.Activies.Splash_ScreenActivity.editor;
import static com.example.login_apiretrofit.Activies.Splash_ScreenActivity.preferences;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.login_apiretrofit.Modals.ProductUser;
import com.example.login_apiretrofit.Modals.Retro_Instance_Class;
import com.example.login_apiretrofit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FloatingActionButton floatingActionButton;
    TextView header_name, header_email;
    String name, email;
    Toolbar toolbar;
    Button button;

    ImageView imageView;
    int gellery=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();//reffrence
        setname(); //toolbar set in drawer user name and email set


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        logout();

        button.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,gellery);
        });


        floatingActionButton.setOnClickListener(v -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.add_product_dialog);

            EditText pname, pprice, pdisc, pimage;

            int uid;
            uid = preferences.getInt("userid", 0);
            Button add, cancel;
            pname = dialog.findViewById(R.id.apname);
            pprice = dialog.findViewById(R.id.apprice);
            pdisc = dialog.findViewById(R.id.apdis);
            pimage = dialog.findViewById(R.id.apimage);
            add = dialog.findViewById(R.id.add);
            cancel = dialog.findViewById(R.id.cancel);


            cancel.setOnClickListener(view -> {
                dialog.dismiss();
            });


            add.setOnClickListener(v1 -> {
                String spname, spprice, spdisc, spimage;
                spdisc = pdisc.getText().toString();
                spprice = pprice.getText().toString();
                spimage = pimage.getText().toString();
                spname = pname.getText().toString();
                Retro_Instance_Class.CallApi().PRODUCT_USER_CALL(String.valueOf(uid), spname, spprice, spdisc, spimage)
                        .enqueue(new Callback<ProductUser>() {
                            @Override
                            public void onResponse(Call<ProductUser> call, Response<ProductUser> response) {

                            }

                            @Override
                            public void onFailure(Call<ProductUser> call, Throwable t) {

                            }
                        });


                dialog.dismiss();
            });

            dialog.show();
        });


    }


    private void init() {
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.fab);
        button = findViewById(R.id.btnimg);
        imageView = findViewById(R.id.H_img);
    }
    private void setname() {
        toolbar.setTitle("E_Commerce app ");
        setSupportActionBar(toolbar);
        name = preferences.getString("name", "");
        email = preferences.getString("email", "");
        View view = navigationView.getHeaderView(0);
        header_name = view.findViewById(R.id.header_name);
        header_email = view.findViewById(R.id.header_email);
        header_name.setText(name);
        header_email.setText(email);
    }
    private void logout()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_logout) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("LOGOUT ?");
                    builder.setMessage("Are You Sure You Want To Logout ?");
                    builder.setPositiveButton("yes", (dialogInterface, i) -> {
                        editor.putBoolean("isLogin", false);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                        drawerLayout.close();
                        startActivity(intent);
                    });

                    builder.setNegativeButton("no", (dialogInterface, i) -> {

                        drawerLayout.close();
                    });
                    builder.show();
                } else if (item.getItemId() == R.id.menu_myprofile) {
                    drawerLayout.close();
                }
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK);
        {
            if(requestCode==gellery){
                imageView.setImageURI(data.getData());
            }
        }
    }
}