package com.example.login_apiretrofit;

import com.example.login_apiretrofit.Modals.LoginUser;
import com.example.login_apiretrofit.Modals.ProductUser;
import com.example.login_apiretrofit.Modals.RegisterUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI
{
    @FormUrlEncoded
    @POST("Register.php")
    Call<RegisterUser> REGISTER_USER_CALL(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginUser> LOGIN_USER_CALL(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("addProduct.php")
    Call<ProductUser> PRODUCT_USER_CALL(@Field("userid") String usrid, @Field("pname") String pname, @Field("pprize") String pprize, @Field("pdes") String pdes, @Field("productimage") String productimage);


}
