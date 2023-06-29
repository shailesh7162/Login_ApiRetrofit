package com.example.login_apiretrofit.Activies;

import static com.example.login_apiretrofit.Activies.Splash_ScreenActivity.editor;
import static com.example.login_apiretrofit.Activies.Splash_ScreenActivity.preferences;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_apiretrofit.Modals.ProductCategories;
import com.example.login_apiretrofit.Modals.addProductData;
import com.example.login_apiretrofit.Modals.viewProductData;
import com.example.login_apiretrofit.R;
import com.example.login_apiretrofit.Retro_Instance_Class;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;

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

    ImageView  pimage;
    String imageget;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    int gellery = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        function();
        nameset();
        show_product_method();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        logout();


        floatingActionButton.setOnClickListener(v -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.add_product_dialog);

            EditText pname, pprice, pdisc;

            int uid;
            uid = preferences.getInt("userid", 0);
            Button add, cancel;
            pname = dialog.findViewById(R.id.apname);
            pprice = dialog.findViewById(R.id.apprice);
            pdisc = dialog.findViewById(R.id.apdis);
            pimage=dialog.findViewById(R.id.apimage);
            add = dialog.findViewById(R.id.add);
            cancel = dialog.findViewById(R.id.cancel);


            cancel.setOnClickListener(view -> {
                dialog.dismiss();
            });
            pimage.setOnClickListener(view -> {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            });


            add.setOnClickListener(v1 -> {
                String spname, spprice, spdisc;
                spdisc = pdisc.getText().toString();
                spprice = pprice.getText().toString();
                spname = pname.getText().toString();


                Bitmap bitmap = ((BitmapDrawable) pimage.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageinarayy = byteArrayOutputStream.toByteArray();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    imageget = Base64.getEncoder().encodeToString(imageinarayy);
                }
                Retro_Instance_Class.CallApi().PRODUCT_USER_CALL(uid, spname, spprice, spdisc,imageget)
                        .enqueue(new Callback<addProductData>() {
                            @Override
                            public void onResponse(Call<addProductData> call, Response<addProductData> response) {
                                if (response.body().getProductaddd() == 1) {
                                    Toast.makeText(MainActivity.this, "product Added", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<addProductData> call, Throwable t) {

                            }
                        });


                dialog.dismiss();
            });

            dialog.show();
        });


    }


    private void function() {
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.fab);
        View view = navigationView.getHeaderView(0);
        // imageView = view.findViewById(R.id.header_image);
        recyclerView=findViewById(R.id.recycler);
        progressBar=findViewById(R.id.ProgressBar);
    }
    private void nameset() {
        toolbar.setTitle("ECommerce app ");
        setSupportActionBar(toolbar);
        name = preferences.getString("name", "");
        email = preferences.getString("email", "");
        View view = navigationView.getHeaderView(0);
        header_name = view.findViewById(R.id.header_name);
        header_email = view.findViewById(R.id.header_email);
        header_name.setText(name);
        header_email.setText(email);
    }
    private void show_product_method(){
        int userid =preferences.getInt("userid",2);
        Retro_Instance_Class.CallApi().VIEW_PRODUCT_DATA_CALL(userid).enqueue(new Callback<viewProductData>() {
            @Override
            public void onResponse(Call<viewProductData> call, Response<viewProductData> response) {
                List<ProductCategories> Userdata;
                Userdata= response.body().getProductdata();
                if(Userdata!=null){
                    progressBar.setVisibility(View.GONE);
                }
                for (int i = 0; i < Userdata.size(); i++) {
                    Log.d("API", "onResponse: name="+Userdata.get(i).getPname());
                }

//
                if (response.isSuccessful()) {


                    if (response.body().getConnection() == 1) {
                        if (response.body().getResult() == 1) {
                            show_product_adapter adapter = new show_product_adapter(MainActivity.this, Userdata);
                            LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                            manager.setOrientation(RecyclerView.VERTICAL);
                            recyclerView.setLayoutManager(manager);

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<viewProductData> call, Throwable t) {

            }
        });

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
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) ;
            {
                Uri uri = result.getUri();
                pimage.setImageURI(uri);

            }
        }
    }
}