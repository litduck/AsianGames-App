package com.example.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.app.LoginOrRegisterActivity;
import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.util.GetSQLite;

public class LoginFragment extends Fragment {
    private Button registerBtn;
    private TextView bannerText;

    private LinearLayout loginLinearLayout;
    private LinearLayout registerLinearLayout;

    public LoginFragment loginFragment;
    public RegisterFragment registerFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        registerBtn = (Button) ((LoginOrRegisterActivity)getActivity()).findViewById(R.id.registerButton);
        loginLinearLayout = (LinearLayout) ((LoginOrRegisterActivity)getActivity()).findViewById(R.id.startUILogin);
        registerLinearLayout = (LinearLayout) ((LoginOrRegisterActivity)getActivity()).findViewById(R.id.startUIRegister);
        bannerText = (TextView) ((LoginOrRegisterActivity)getActivity()).findViewById(R.id.startBanner);
        registerFragment = new RegisterFragment();
        loginFragment = new LoginFragment();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginOrRegisterActivity)getActivity()).FragmentHideShow(registerFragment);
                registerLinearLayout.setVisibility(View.VISIBLE);
                loginLinearLayout.setVisibility(View.GONE);
                bannerText.setText("注册界面");
            }
        });

        return view;
    }
}
