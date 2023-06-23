package com.example.app.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.LoginOrRegisterActivity;
import com.example.app.R;
import com.example.app.adapter.CollectionsAdapter;
import com.example.app.bean.Sports;
import com.example.app.util.GetSQLite;

import java.util.List;

public class Page5Fragment extends Fragment {
    private TextView ownUsername;
    private TextView ownAccount;
    private TextView ownSex;
    private RecyclerView collectionsRecyclerView;
//    private List<Collections> collectionsList;
    private List<Sports> sportsList;
    private TextView tv1;
    private String user_account;
    private boolean ifFirst = true;
    private Button exitBtn;
    private Button changeBtn;
    private EditText changeName;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private RadioGroup radioGroup;
    private String sex;
    private SharedPreferences pref;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page5, container, false);

        //初始化控件
        ownUsername = (TextView) view.findViewById(R.id.ownUsername);
        ownAccount = (TextView) view.findViewById(R.id.ownAccount);
        ownSex = (TextView) view.findViewById(R.id.ownSex);
        collectionsRecyclerView = (RecyclerView) view.findViewById(R.id.myStar);
        exitBtn = (Button) view.findViewById(R.id.exitButton);
        changeBtn= (Button) view.findViewById(R.id.changeButton);


        Bundle bundle = getArguments();
        String account = bundle.getString("account");
        String name = bundle.getString("username");
        String password = bundle.getString("password");
        sex = bundle.getString("sex");

        pref = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Account",account);
        editor.putString("Name",name);
        editor.putString("Password",password);
        editor.putString("Sex",sex);
        editor.apply();

        showText(pref);
        user_account = pref.getString("Account","");

        changeData(user_account);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(pref,editor);
            }
        });


        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getActivity(), LoginOrRegisterActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onResume() {

        super.onResume();
        if(!ifFirst){
            changeData(user_account);
            showText(pref);
        }
        ifFirst = false;
    }

    private void changeData(String user_account){
        GetSQLite getSQLite=new GetSQLite();
        String sql = "select * from schedule natural join collections where user_account='"+user_account+"'";
        sportsList = getSQLite.setSportsList(this.getActivity(), sql, user_account);

        CollectionsAdapter collectionsAdapter = new CollectionsAdapter(this.getActivity(),sportsList);
        collectionsAdapter.setOnItemClickListener(new CollectionsAdapter.OnItemClickListener() {
            @Override
            public void onCollectedButton(View view, int position, String id) {
                String sql1 = "delete from collections where sport_id=? and user_account=?";
                Object[] o = new Object[]{id,user_account};
                getSQLite.deleteData(getActivity(),sql1,o);
                changeData(user_account);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        //设置布局
        collectionsRecyclerView.setLayoutManager(manager);
        //设置动画
        collectionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        collectionsRecyclerView.setAdapter(collectionsAdapter);
    }

    private void showText(SharedPreferences pref){
        ownUsername.setText(pref.getString("Name",""));
        ownAccount.setText(pref.getString("Account",""));
        ownSex.setText(pref.getString("Sex",""));
    }

    public void showDialog(SharedPreferences pref, SharedPreferences.Editor editor){
        AlertDialog.Builder builder=new AlertDialog.Builder(this.getContext());
        builder.setTitle("个人基本信息填写");
        View view=this.getLayoutInflater().inflate(R.layout.dialog_personal_message, new LinearLayout(this.getActivity()));

        changeName = (EditText) view.findViewById(R.id.editUsername);
        radioMale = (RadioButton) view.findViewById(R.id.editRadioMale);
        radioFemale = (RadioButton) view.findViewById(R.id.editRadioFemale);
        radioGroup = (RadioGroup) view.findViewById(R.id.editRadioGroupSex);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) view.findViewById(radioGroup.getCheckedRadioButtonId());
                sex = rb.getText().toString();
            }
        });

        builder.setView(view);
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = changeName.getText().toString();
                editor.putString("Name",name);
                editor.putString("Sex", sex);
                editor.commit();
                String user_account = pref.getString("Account","");
                String sql = "update users set username=?,sex=? where user_account=?";
                Object[] o = new Object[]{name,sex,user_account};
                GetSQLite getSQLite = new GetSQLite();
                getSQLite.updateData(getActivity(),sql,o);
                showText(pref);
                Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }
}
