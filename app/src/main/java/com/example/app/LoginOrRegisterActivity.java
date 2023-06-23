package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.app.bean.User;
import com.example.app.fragment.LoginFragment;
import com.example.app.fragment.Page5Fragment;
import com.example.app.fragment.RegisterFragment;
import com.example.app.util.GetSQLite;
import com.example.app.util.MyDatabaseHelper;

import java.io.IOException;

public class LoginOrRegisterActivity extends AppCompatActivity {
    private Button loginBtn;
    public TextView bannerText;
    private EditText loginUsername;
    private EditText loginPassword;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment currentFragment;

    public LoginFragment loginFragment = new LoginFragment();
    public RegisterFragment registerFragment = new RegisterFragment();

    private LinearLayout loginLinearLayout;
    private LinearLayout registerLinearLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题
        setContentView(R.layout.activity_login_or_register);

        openDatabase();

        InitFragment initFragment = new InitFragment();
        initFragment.init();
        Page5Fragment page5Fragment = initFragment.getPage5();

        //初始化fragments
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.loginOrRegisterFragment, loginFragment);
        transaction.show(loginFragment);
        transaction.hide(registerFragment);
        transaction.commit();
        currentFragment=loginFragment;

        loginLinearLayout = (LinearLayout) findViewById(R.id.startUILogin);
        registerLinearLayout = (LinearLayout) findViewById(R.id.startUIRegister);
        bannerText = (TextView) findViewById(R.id.startBanner);
        loginBtn = (Button) findViewById(R.id.loginButton);


        loginBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUsername = (EditText) manager.findFragmentById(R.id.loginOrRegisterFragment).getView().findViewById(R.id.loginUsername);
                loginPassword = (EditText) manager.findFragmentById(R.id.loginOrRegisterFragment).getView().findViewById(R.id.loginPassword);

                String name = loginUsername.getText().toString();

                // 获取用户是否存在的布尔值
                String password = loginPassword.getText().toString();
                String sql = "select * from users where username=? and password=?";
                String[] o = new String[]{name,password};
                GetSQLite getSQLite=new GetSQLite();
                boolean ifExist = getSQLite.ifUserExist(LoginOrRegisterActivity.this, sql, o);

                // 判断用户是否存在
                if(ifExist){
                    Intent intent = new Intent(LoginOrRegisterActivity.this, MainActivity.class);
                    String[] s = new String[]{name,password};
                    User user = getSQLite.getUser(LoginOrRegisterActivity.this, s);

                    // 数据传递
                    intent.putExtra("account",user.getAccount());
                    intent.putExtra("username",name);
                    intent.putExtra("password",password);
                    intent.putExtra("sex",user.getSex());

                    startActivity(intent);
                }
                else{
                    Toast toast=Toast.makeText(LoginOrRegisterActivity.this, "用户名或密码不正确！", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }));
    }

// fragments之间的切换
    public void FragmentHideShow(Fragment fg) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        if (!fg.isAdded()) {
            transaction.hide(currentFragment);
            transaction.add(R.id.loginOrRegisterFragment, fg);
            transaction.show(fg);
        } else {
            transaction.hide(currentFragment);
            transaction.show(fg);
        }
        currentFragment = fg;
        transaction.commit();
    }

    public void openDatabase(){
        MyDatabaseHelper myHelper = new MyDatabaseHelper(LoginOrRegisterActivity.this);
        try {
            myHelper.CopyDBFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
