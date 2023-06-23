
package com.example.login_apiretrofit.Modals;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginUser {

    @SerializedName("connection")
    @Expose
    private Integer connection;
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("userdata")
    @Expose
    private Userdata userdata;

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Userdata getUserdata() {
        return userdata;
    }

    public void setUserdata(Userdata userdata) {
        this.userdata = userdata;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "connection=" + connection +
                ", result=" + result +
                ", userdata=" + userdata +
                '}';
    }
}
