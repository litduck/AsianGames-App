package com.example.app.bean;

import java.io.Serializable;

public class Sports implements Serializable {
    public String sportsName;
    public String sportType;
    public String sportsDate;
    public String sportsGym;

    public String id;
    public String path;

    private boolean ifSelect;


    // 获取赛事名字
    public void setSportsName(String sportsName){
        this.sportsName = sportsName;
    }
    public String getSportsName(){
        return sportsName;
    }

    public void setSportType(String sportType){
        this.sportType = sportType;
    }
    public String getSportType(){
        return sportType;
    }

    // 获取比赛日期
    public void setSportsDate(String sportsDate){
        this.sportsDate = sportsDate;
    }
    public String getSportsDate(){
        return sportsDate;
    }

    // 获取赛事场馆
    public void setSportsGym(String sportsGym){
        this.sportsGym = sportsGym;
    }
    public String getSportsGym(){
        return sportsGym;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }

    public void setIfSelect(boolean ifSelect){
        this.ifSelect = ifSelect;
    }
    public boolean getIfSelect(){return ifSelect;}

    public void setPath(String path){this.path = path;}
    public String getPath(){return path;}
    public String toString() {
        return "Sport's name: " + sportsName + ", Sport's date: " + sportsDate + ", Sport's gym: " + sportsGym;
    }
}
