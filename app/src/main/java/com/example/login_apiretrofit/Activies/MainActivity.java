package com.example.login_apiretrofit.Activies;

import static com.example.login_apiretrofit.Activies.Splash_ScreenActivity.editor;
import static com.example.login_apiretrofit.Activies.Splash_ScreenActivity.preferences;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.login_apiretrofit.Fragment.Show_all_product;
import com.example.login_apiretrofit.Fragment.home_fragment;
import com.example.login_apiretrofit.Modals.ProductUser;
import com.example.login_apiretrofit.Modals.Retro_Instance_Class;
import com.example.login_apiretrofit.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Button add;
    EditText pname,pdecs,pprice;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView=findViewById(R.id.navigation);
        drawerLayout=findViewById(R.id.Drawer);
        toolbar=findViewById(R.id.toolbar);
        TextView nametext,emailtext;
        View headerView = navigationView.getHeaderView(0);

        nametext=headerView.findViewById(R.id.hader_name);
        emailtext=headerView.findViewById(R.id.hader_email);
        String uname,uemail;
        int uid;
        uid= preferences.getInt("uid",0);
        uname=preferences.getString("name","");
        uemail=preferences.getString("email","");
        Log.d("TTT", "onCreate: "+uname);
        Log.d("TTT", "onCreate: "+uemail);
        nametext.setText(""+uname);
        emailtext.setText(""+uemail);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.home_menu)
                {
                    addfragment(new home_fragment());
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                if (item.getItemId()==R.id.logout)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Logout");
                    builder.setMessage("Are you sure ?");
                    builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            editor.putBoolean("isLogin",false);
                            editor.commit();
                            finish();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                if (item.getItemId()==R.id.add_product)
                {
                    Dialog dialog=new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.dailog_layout);
                    add=dialog.findViewById(R.id.Add_button);
                    pname=dialog.findViewById(R.id.product_name);
                    pdecs=dialog.findViewById(R.id.product_desc);
                    pprice=dialog.findViewById(R.id.product_price);
                    imageView=dialog.findViewById(R.id.product_image);
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            String name,descri,price,image;
                            name=pname.getText().toString();
                            descri=pdecs.getText().toString();
                            price=pprice.getText().toString();
                            int r=new Random().nextInt(100);
                            image="img_"+r+".png";
                            Retro_Instance_Class.CallApi().PRODUCT_USER_CALL(String.valueOf(uid),name,descri,price,image).enqueue(new Callback<ProductUser>()
                            {
                                @Override
                                public void onResponse(Call<ProductUser> call, Response<ProductUser> response)
                                {
                                    Log.d("ttt", "onResponse: "+response.body().toString());
                                }

                                @Override
                                public void onFailure(Call<ProductUser> call, Throwable t)
                                {
                                    Log.e("ttt", "onFailure: "+t.getLocalizedMessage() );
                                }
                            });
                        }
                    });
                    dialog.show();
                }
                if (item.getItemId()==R.id.show_all)
                {
                    addfragment(new Show_all_product());
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                return true;
            }
        });
    }

    private void addfragment(Fragment fragment) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();

    }
}