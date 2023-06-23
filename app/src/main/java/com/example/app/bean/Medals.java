package com.example.app.bean;

import java.io.Serializable;

public class Medals implements Serializable {
    private int rank;
    private String country;
    private int golden;
    private int silver;
    private int bronze;
    private int total;

    public void setRank(int sportsName){
        this.rank = rank;
    }
    public int getRank(){
        return rank;
    }

    public void setGolden(int golden){
        this.golden = golden;
    }
    public int getGolden(){
        return golden;
    }

    public void setSilver(int silver){
        this.silver = silver;
    }
    public int getSilver(){
        return silver;
    }

    public void setBronze(int bronze){
        this.bronze = bronze;
    }
    public int getBronze(){
        return bronze;
    }

    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return total;
    }

    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return country;
    }
}
