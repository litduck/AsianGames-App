package com.example.app.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.adapter.SportsAdapter;
import com.example.app.bean.Sports;
import com.example.app.util.GetSQLite;

import java.util.List;

public class Page3Fragment extends Fragment {
    private List<Sports> sportsList;
    private Spinner typeSpinner, dateSpinner, gymSpinner;
    private boolean ifFirst = true;
    private String[] types = new String[]{"所有","游泳", "射箭", "田径", "羽毛球", "棒垒球", "篮球", "拳击", "皮划艇",
            "板球", "自行车", "体育舞蹈", "龙舟", "马术", "击剑", "足球", "高尔夫球", "体操", "体操", "手球", "曲棍球",
            "柔道", "卡巴迪", "武道", "智力项目", "现代五项", "轮滑", "赛艇", "橄榄球", "帆船", "藤球", "射击", "攀岩",
            "壁球", "乒乓球", "跆拳道", "网球", "铁人三项", "排球", "举重", "摔跤", "武术",
    };

    private String[] dates = new String[]{"所有","2023/9/23", "2023/9/24", "2023/9/25", "2023/9/26", "2023/9/27", "2023/9/28",
            "2023/9/29", "2023/9/30", "2023/10/1", "2023/10/2", "2023/10/3", "2023/10/4", "2023/10/5", "2023/10/6", "2023/10/7"
    };

    private String[] gyms = new String[]{"所有",
            "杭州奥体中心游泳馆", "淳安界首体育中心游泳赛场", "黄龙体育中心游泳馆", "富阳银湖体育中心射箭场", "杭州奥体中心体育场", "滨江体育馆",
            "绍兴棒（垒）球体育文化中心", "德清地理信息小镇篮球场", "杭州奥体中心体育馆(W/M)", "浙江大学(紫金港校区)体育馆(M)", "绍兴奥体中心体育馆(W)",
            "富阳体育中心体育馆(W)", "杭州体育馆", "富阳水上中心", "浙江工业大学板球场", "淳安界首体育中心小轮车赛场", "淳安界首体育中心山地自行车赛场",
            "淳安界首体育中心公路自行车赛场", "淳安界首体育中心自行车馆", "拱墅运河体育公园体育馆", "温州龙舟运动中心", "桐庐马术中心", "杭州电子科技大学体育馆",
            "黄龙体育中心体育场(W/M)", "临平体育中心体育场(W/M)", "上城体育中心体育场(W/M)", "萧山体育中心体育场(W/M)", "金华体育中心体育场(W/M)",
            "浙江师范大学东体育场(W/M)", "温州奥体中心体育场(W/M)", "温州体育中心体育场(W/M)", "西湖国际高尔夫球场", "黄龙体育中心体育馆",
            "黄龙体育中心体育馆", "浙江师范大学(萧山校区)体育馆(W)", "浙江工商大学文体中心(W/M)", "拱墅运河体育公园体育场", "茉山临浦体育馆",
            "萧山瓜沥文化体育中心", "萧山临浦体育馆", "临平体育中心体育", "杭州棋院(智力大厦)棋类馆", "中国杭州电竞中心", "富阳银湖体育中心", "钱塘轮滑中心",
            "杭州师范大学(仓前校区)体育场", "宁波象山亚帆中心", "金华体育中心体育馆", "绍兴柯桥羊山攀岩中心", "杭州奥体中心国博壁球馆", "临安体育文化会展中心",
            "杭州奥体中心网球中心", "淳安界首体育中心铁人三项赛场", "宁波半边山沙滩排球中心", "杭州师范大学(仓前校区)体育馆(W/M)", "临平体育中心体育馆(W)",
            "德清体育中心体育馆(M)", "中国轻纺城体育中心体育馆(M)", "萧山体育中心体育馆", "萧山瓜沥文化体育中心"
    };
    private int typeId = 2;
    private int dateId = 2;
    private int gymId = 2;
    private RecyclerView sportsRecyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, container, false);
        sportsRecyclerView = (RecyclerView)view.findViewById(R.id.SportsRecyclerView);

//      Spinner
        typeSpinner = (Spinner) view.findViewById(R.id.typeSpinner);
        dateSpinner = (Spinner) view.findViewById(R.id.dateSpinner);
        gymSpinner = (Spinner) view.findViewById(R.id.gymSpinner);

        changeData("select * from schedule");

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(
                this.getActivity(), R.layout.spinner_item_type, types);
        typeSpinner.setAdapter(yearAdapter);//年份列表
        typeSpinner.setSelection(0);//默认显示项

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(
                this.getActivity(), R.layout.spinner_item_date, dates);
        dateSpinner.setAdapter(dateAdapter);//年份列表
        dateSpinner.setSelection(0);//默认显示项

        ArrayAdapter<String> gymAdapter = new ArrayAdapter<String>(
                this.getActivity(), R.layout.spinner_item_gym, gyms);
        gymSpinner.setAdapter(gymAdapter);//年份列表
        gymSpinner.setSelection(0);//默认显示项

        MyItemSelectedListener myItemSelectedListener = new MyItemSelectedListener();//实例化事件监听器
        typeSpinner.setOnItemSelectedListener(myItemSelectedListener);
        dateSpinner.setOnItemSelectedListener(myItemSelectedListener);
        gymSpinner.setOnItemSelectedListener(myItemSelectedListener);

        return view;

    }

    public void onResume() {

        super.onResume();
        if(!ifFirst){
            changeData("select * from schedule");
            typeSpinner.setSelection(0);
            dateSpinner.setSelection(0);
            gymSpinner.setSelection(0);
        }
        ifFirst = false;
    }

    private void changeData(String sql){
        SharedPreferences pref = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        String account = pref.getString("Account","");

        GetSQLite getSQLite=new GetSQLite();
        sportsList = getSQLite.setSportsList(this.getActivity(), sql, account);
        SportsAdapter sportsAdapter = new SportsAdapter(this.getActivity(), sportsList);

        sportsAdapter.setOnItemClickListener(new SportsAdapter.OnItemClickListener() {
            @Override
            public void onButtonClicked(View view, String id, ImageButton collectBtn, ImageButton collectedBtn) {
                collectBtn.setVisibility(View.GONE);
                collectedBtn.setVisibility(View.VISIBLE);

                String sql1 = "insert into collections values(?,?)";
                Object[] o = new Object[]{account,id};
                getSQLite.insertData(getActivity(),sql1,o);

                Toast toast=Toast.makeText(getActivity(), "收藏成功！", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onCollectedButton(View view, String id, ImageButton collectBtn, ImageButton collectedBtn) {
                collectedBtn.setVisibility(View.GONE);
                collectBtn.setVisibility(View.VISIBLE);

                String sql2 = "delete from collections where sport_id=? and user_account=?";
                Object[] o = new Object[]{id,account};
                getSQLite.deleteData(getActivity(),sql2,o);
                Toast toast=Toast.makeText(getActivity(), "取消收藏！", Toast.LENGTH_SHORT);
                toast.show();
            }

        });
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        //设置布局
        sportsRecyclerView.setLayoutManager(manager);
        //设置动画
        sportsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sportsRecyclerView.setAdapter(sportsAdapter);
    }

    private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String type = typeSpinner.getSelectedItem().toString();
            String date = dateSpinner.getSelectedItem().toString();
            String gym = gymSpinner.getSelectedItem().toString();
            String sql = getSql(type, date, gym);

            System.out.println(sql);
            switch (parent.getId()) {
                case R.id.typeSpinner:
                    typeId = position;
                    changeData(sql);
                    break;
                case R.id.dateSpinner:
                    dateId = position;
                    changeData(sql);
                    break;
                case R.id.gymSpinner:
                    gymId = position;
                    changeData(sql);
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private String getSql(String type, String date, String gym){
        String sql = "select * from schedule";
        String all = "所有";
        if(type==all && date==all && gym==all){
            sql = "select * from schedule";
        }
        else if (type!=all && date==all && gym==all) {
            sql = "select * from schedule where type=" + "'" + type + "'";
        }
        else if (type==all && date!=all && gym==all) {
            sql = "select * from schedule where date=" + "'" + date + "'";
        }
        else if (type==all && date==all && gym!=all) {
            sql = "select * from schedule where gym=" + "'" + gym + "'";
        }
        else if (type!=all && date!=all && gym==all) {
            sql = "select * from schedule where type=" + "'" + type + "' and date=" + "'" + date + "'";
        }
        else if (type!=all && date==all && gym!=all) {
            sql = "select * from schedule where type=" + "'" + type + "' and gym=" + "'" + gym + "'";
        }
        else if (type==all && date!=all && gym!=all) {
            sql = "select * from schedule where date=" + "'" + date + "' and gym=" + "'" + gym + "'";
        }
        else{
            sql = "select * from schedule where type=" + "'" + type + "' and date=" + "'" + date + "' and gym=";
        }
        return sql;

    }
}
