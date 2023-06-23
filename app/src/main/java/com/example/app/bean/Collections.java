package com.example.app.bean;

import java.io.Serializable;

public class Collections implements Serializable {
    private String sportID;
    private String userAccount;

    public void setSportID(String sportID) {
        this.sportID = sportID;
    }

    public String getSportID() {
        return sportID;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public String getUserAccount() {
        return userAccount;
    }
}
