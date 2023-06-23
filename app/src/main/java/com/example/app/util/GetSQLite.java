package com.example.app.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.app.bean.AsianGames;
import com.example.app.bean.Medals;
import com.example.app.bean.Posts;
import com.example.app.bean.Sports;
import com.example.app.bean.User;

import java.util.ArrayList;
import java.util.List;

public class GetSQLite {

    public List<AsianGames> setAsianGamesList(FragmentActivity activity){
        String sql = "select game_name,session from summerGame";

        Cursor cursor = getData(activity, sql);
        List<AsianGames> list=new ArrayList<>();
        while(cursor.moveToNext()){
            AsianGames asianGames = new AsianGames();
            String gameName = cursor.getString(0);
            String session = cursor.getString(1);

            asianGames.setAsianGamesTitle(gameName);
            asianGames.setSession(session);
            list.add(asianGames);
        }
        return list;
    }

    public List<Sports> setSportsList(FragmentActivity activity, String sql,String account){
//        String sql = "select * from schedule";

        Cursor cursor = getData(activity, sql);
        Cursor flag = getData(activity,"select sport_id from collections where user_account='"+account+"'");
        System.out.println("account: "+account);
        List<String> col = new ArrayList<>();
        while(flag.moveToNext()){
            col.add(flag.getString(0));
            System.out.println("sportID: "+flag.getString(0));
        }

        List<Sports> list=new ArrayList<>();
        while(cursor.moveToNext()){
            Sports sports = new Sports();

            String id = cursor.getString(0);
            String sportName = cursor.getString(1);
            String type = cursor.getString(2);
            String date = cursor.getString(3);
            String gym = cursor.getString(4);
            String path = cursor.getString(5);
            String user_account;

            sports.setSportsName(sportName);
            sports.setSportType(type);
            sports.setSportsDate(date);
            sports.setSportsGym(gym);
            sports.setId(id);
            sports.setPath(path);

            for(int i=0;i< col.size();i++){
                if(col.get(i).equals(id)){
                    sports.setIfSelect(true);
                    break;
                }
                else{
                    sports.setIfSelect(false);
                }
            }

            list.add(sports);
        }
        return list;
    }

    public List<Medals> setMedalsList(FragmentActivity activity, String se){
        List<Medals> list=new ArrayList<>();

        String sql = "select * from medals where session=" + "'" + se + "'";
        Cursor cursor = getData(activity, sql);
        while(cursor.moveToNext()){
            Medals medals = new Medals();
            int rank = cursor.getInt(0);
            String country = cursor.getString(1);
            int golden = cursor.getInt(2);
            int silver = cursor.getInt(3);
            int bronze = cursor.getInt(4);
            int total = cursor.getInt(5);

            medals.setRank(rank);
            medals.setCountry(country);
            medals.setGolden(golden);
            medals.setSilver(silver);
            medals.setBronze(bronze);
            medals.setTotal(total);

            list.add(medals);
        }

        return list;
    }

    public List<Posts> setPostsList(FragmentActivity activity){
        List<Posts> list = new ArrayList<>();

        String sql = "select username,date,content from users natural join userPost";
        Cursor cursor = getData(activity, sql);

        while(cursor.moveToNext()){
            Posts posts = new Posts();

            String name = cursor.getString(0);
            String time = cursor.getString(1);
            String content = cursor.getString(2);

            posts.setPostUsername(name);
            posts.setPostTime(time);
            posts.setPostContent(content);

            list.add(posts);
        }

        return list;
    }


    public User getUser(Context context, String[] o){
        User user = new User();
        String sql = "select * from users where username=? and password=?";
        Cursor cursor = getData1(context, sql, o);
        if(cursor.moveToFirst() == true){
            String id;
            String name;
            String psw;
            String sex;

            id = cursor.getString(0);
            name = cursor.getString(1);
            sex = cursor.getString(2);
            psw = cursor.getString(3);

            System.out.println(name);

            user.setAccount(id);
            user.setUsername(name);
            user.setSex(sex);
            user.setPassword(psw);

        }

        return user;
    }

    public void insertData(FragmentActivity activity, String sql, Object[] o){
        OpenHelper openHelper=new OpenHelper(activity);
        SQLiteDatabase db=openHelper.getReadableDatabase();
        db.execSQL(sql,o);
    }

    public void deleteData(FragmentActivity activity, String sql, Object[] o){
        OpenHelper openHelper=new OpenHelper(activity);
        SQLiteDatabase db=openHelper.getReadableDatabase();
        db.execSQL(sql,o);
    }

    public void updateData(FragmentActivity activity, String sql, Object[] o){
        OpenHelper openHelper=new OpenHelper(activity);
        SQLiteDatabase db=openHelper.getReadableDatabase();
        db.execSQL(sql,o);
    }


    public boolean ifUserExist(Context context, String sql, String[] o){
        boolean ifExist = false;
        try {
            OpenHelper openHelper=new OpenHelper(context);
            SQLiteDatabase db=openHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql,o);
            System.out.println(sql);

            if (cursor.moveToFirst() == true)
            {
                ifExist = true;
            }
            else{
                ifExist = false;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ifExist;
    }

    public boolean ifNewUserExist(FragmentActivity activity, String sql, String[] o){
        boolean ifExist = false;
        try {
            OpenHelper openHelper=new OpenHelper(activity);
            SQLiteDatabase db=openHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql,o);
            System.out.println(sql);

            if (cursor.moveToFirst() == true)
            {
                ifExist = true;
            }
            else{
                ifExist = false;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ifExist;
    }

    public Cursor getData(FragmentActivity activity, String sql){
        OpenHelper openHelper=new OpenHelper(activity);
        SQLiteDatabase db=openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    public Cursor getData1(Context context, String sql, String[] o){
        OpenHelper openHelper=new OpenHelper(context);
        SQLiteDatabase db=openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,o);
        return cursor;
    }
}
