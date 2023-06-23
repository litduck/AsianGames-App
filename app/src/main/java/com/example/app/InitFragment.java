package com.example.app;

import com.example.app.fragment.Page1Fragment;
import com.example.app.fragment.Page2Fragment;
import com.example.app.fragment.Page3Fragment;
import com.example.app.fragment.Page4Fragment;
import com.example.app.fragment.Page5Fragment;

public class InitFragment {
    private Page1Fragment page1Fragment;
    private Page2Fragment page2Fragment;
    private Page3Fragment page3Fragment;
    private Page4Fragment page4Fragment;
    private Page5Fragment page5Fragment;

    public void init(){
        page1Fragment = new Page1Fragment();
        page2Fragment = new Page2Fragment();
        page3Fragment = new Page3Fragment();
        page4Fragment = new Page4Fragment();
        page5Fragment = new Page5Fragment();
    }

    public Page1Fragment getPage1(){
        return page1Fragment;
    }

    public Page2Fragment getPage2(){
        return page2Fragment;
    }

    public Page3Fragment getPage3(){
        return page3Fragment;
    }

    public Page4Fragment getPage4(){
        return page4Fragment;
    }

    public Page5Fragment getPage5(){
        return page5Fragment;
    }
}
