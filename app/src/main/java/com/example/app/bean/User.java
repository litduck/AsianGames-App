package com.example.app.bean;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String account;
    private String sex;

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public void setAccount(String account){
        this.account = account;
    }
    public String getAccount(){
        return account;
    }

    public void setSex(String sex){
        this.sex = sex;
    }
    public String getSex(){
        return sex;
    }
}
