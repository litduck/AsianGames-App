package com.example.app.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.adapter.AsianGamesAdapter;
import com.example.app.adapter.PostsAdapter;
import com.example.app.bean.Posts;
import com.example.app.util.GetSQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Page4Fragment extends Fragment {
    private RecyclerView postsRecyclerView;
    private List<Posts> postsList;
    private TextView countDate;

    private EditText postText;
    private Button postBtn;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page4, container, false);

        postsRecyclerView = (RecyclerView)view.findViewById(R.id.postRecyclerView);
        postText = (EditText) view.findViewById(R.id.postText);
        postBtn = (Button) view.findViewById(R.id.postButton);
        countDate = (TextView) view.findViewById(R.id.countDate);

        GetSQLite getSQLite=new GetSQLite();
        ChangePosts(getSQLite);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = postText.getText().toString();
                String time = getTime();
                SharedPreferences pref= getActivity().getSharedPreferences("user", MODE_PRIVATE);
                String account = pref.getString("Account","");

                String sql = "insert into userPost values(?,?,?)";
                Object[] o = new Object[]{account,time,text};
                getSQLite.insertData(getActivity(), sql, o);

                ChangePosts(getSQLite);

                postText.setText("");

            }
        });
        try {
            countDate.setText("距离杭州亚运会开始还有"+CountDate()+"天");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return view;
    }

    public void ChangePosts(GetSQLite getSQLite){
        postsList = getSQLite.setPostsList(this.getActivity());
        for(int i=0;i<postsList.size();i++){
            System.out.println(postsList.get(i).getPostUsername());
        }

        PostsAdapter postsAdapter = new PostsAdapter(this.getActivity(), postsList);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
//        //设置布局
        postsRecyclerView.setLayoutManager(manager);
//        //设置动画
        postsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        postsRecyclerView.setAdapter(postsAdapter);
    }

    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        String time = sdf.format(date);

        return time;
    }
    private String CountDate() throws ParseException {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        dft.applyPattern("yyyy-MM-dd");
        Date date = new Date();// 获取当前时间
        String time = dft.format(date);

        Date star = dft.parse(time);//开始时间
        Date endDay=dft.parse("2023-09-23");//结束时间
        Date nextDay=star;
        int i=0;
        while(nextDay.before(endDay)){//当明天不在结束时间之前是终止循环
            Calendar cld = Calendar.getInstance();
            cld.setTime(star);
            cld.add(Calendar.DATE, 1);
            star = cld.getTime();
                //获得下一天日期字符串
            nextDay = star;
            i++;
        }
        String date1 = i+"";
        return date1;

    }
}
