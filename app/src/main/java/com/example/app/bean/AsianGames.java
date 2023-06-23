package com.example.app.bean;

import java.io.Serializable;

public class AsianGames implements Serializable {
    private String AsianGamesTitle;
    private String session;

    public void setAsianGamesTitle(String AsianGamesTitle){
        this.AsianGamesTitle = AsianGamesTitle;
    }
    public String getAsianGamesTitle(){
        return AsianGamesTitle;
    }

    public void setSession(String session){
        this.session = session;
    }
    public String getSession(){
        return session;
    }



    public String toString() {
        return "Asian Games' name: " + AsianGamesTitle;
    }

}
