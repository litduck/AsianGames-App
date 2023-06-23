package com.example.app.fragment;

import android.os.Bundle;
import android.view.Gravity;
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

import com.example.app.LoginOrRegisterActivity;
import com.example.app.R;
import com.example.app.util.GetSQLite;

import java.util.UUID;

public class RegisterFragment extends Fragment {
    private Button nowRegisterBtn;
    private Button returnLoginBtn;

    private TextView bannerText;

    private EditText registerUsername;
    private EditText registerPassword;

    RadioButton radioMale;
    RadioButton radioFemale;
    RadioGroup radioGroup;

    private LinearLayout loginLinearLayout;
    private LinearLayout registerLinearLayout;

    public LoginFragment loginFragment;
//    public RegisterFragment registerFragment = new RegisterFragment();

    private String sex;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        nowRegisterBtn = (Button) ((LoginOrRegisterActivity)getActivity()).findViewById(R.id.nowRegisterButton);
        returnLoginBtn = (Button) ((LoginOrRegisterActivity)getActivity()).findViewById(R.id.returnLoginButton);
        loginLinearLayout = (LinearLayout) ((LoginOrRegisterActivity)getActivity()).findViewById(R.id.startUILogin);
        registerLinearLayout = (LinearLayout) ((LoginOrRegisterActivity)getActivity()).findViewById(R.id.startUIRegister);
        bannerText = (TextView) ((LoginOrRegisterActivity)getActivity()).findViewById(R.id.startBanner);
        registerUsername = (EditText) view.findViewById(R.id.registerUsername);
        registerPassword = (EditText) view.findViewById(R.id.registerPassword);
        radioMale = (RadioButton) view.findViewById(R.id.registerRadioMale);
        radioFemale = (RadioButton) view.findViewById(R.id.registerRadioFemale);
        radioGroup = (RadioGroup) view.findViewById(R.id.registerRadioGroupSex);

        loginFragment = new LoginFragment();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) view.findViewById(radioGroup.getCheckedRadioButtonId());
                sex = rb.getText().toString();
            }
        });

        nowRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();
                GetSQLite getSQLite=new GetSQLite();

                String checkSql = "select * from users where username=?";
                String[] checkO = new String[]{name};
                boolean ifExist = getSQLite.ifUserExist(getActivity(), checkSql, checkO);
                if(ifExist){
                    registerUsername.setText("");
                    registerPassword.setText("");
                    Toast toast=Toast.makeText(getActivity(), "此账户名已被占用！", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    String account = CreateId();
                    String sql = "insert into users values(?,?,?,?)";
                    Object[] o = new Object[]{account,name,sex,password};
                    getSQLite.insertData(getActivity(), sql, o);
                    Toast toast=Toast.makeText(getActivity(), "注册成功！", Toast.LENGTH_SHORT);
                    toast.show();
                    bannerText.setText("登录界面");
                    ((LoginOrRegisterActivity)getActivity()).FragmentHideShow(loginFragment);
                    loginLinearLayout.setVisibility(View.VISIBLE);
                    registerLinearLayout.setVisibility(View.GONE);
                }

            }
        });

        returnLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((LoginOrRegisterActivity)getActivity()).FragmentHideShow(loginFragment);
                loginLinearLayout.setVisibility(View.VISIBLE);
                registerLinearLayout.setVisibility(View.GONE);
                bannerText.setText("登录界面");
            }
        });

        return view;
    }
    // 创建八位账号ID
    private String CreateId(){
        String[] c = new String[] { "a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z" };

        StringBuffer shortBuffer = new StringBuffer();
        System.out.println("原来生成的36位uuid");
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);

        uuid=uuid.replace("-", "");
        System.out.println("替换-后的32位uuid");
        System.out.println(uuid);

        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(c[x % 62]);
        }
        String str = shortBuffer.toString();

        System.out.println("生成的随机字符"+str);

        return str;
    }

}
