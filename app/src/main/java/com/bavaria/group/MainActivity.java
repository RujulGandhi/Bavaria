package com.bavaria.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bavaria.group.Adapter.MainScreenAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;


public class MainActivity extends Activity implements View.OnClickListener {
    LinearLayout contactUsLinearLayout, llMaintenance, llPayOnline, llAboutUs, llProjects, llPhotoVideo;
    FrameLayout flLoginBottom, flMain;
    TextView tvInfo, tvLogin, tvSettings;
    MainScreenAdapter adapter;
    Button mContactBtn, mLoginBtn, btContactus;
    Animation slide_down, slide_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        Toolbar toolBar = (Toolbar) findViewById(R.id.activity_main_toolbar);
//        setSupportActionBar(toolBar);
//        getSupportActionBar().setTitle("");
//        List<Fragment> fragments = getFragments();
//        adapter = new MainScreenAdapter(getSupportFragmentManager(), fragments);
//        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
//        pager.setAdapter(adapter);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(MainActivity.this, R.color.black)));
    }

    private void init() {
        btContactus = (Button) findViewById(R.id.btContactus);
        mLoginBtn = (Button) findViewById(R.id.activity_main_login_Btn);
        llMaintenance = (LinearLayout) findViewById(R.id.llMaintenance_MainActivity);
        llPayOnline = (LinearLayout) findViewById(R.id.llPayOnline_MainActivity);
        llAboutUs = (LinearLayout) findViewById(R.id.llAboutUs_MainActivity);
        llProjects = (LinearLayout) findViewById(R.id.llProjects_MainActivity);
        llPhotoVideo = (LinearLayout) findViewById(R.id.llPhotoVideo_MainActivity);
        flLoginBottom = (FrameLayout) findViewById(R.id.flLogin_MainActivity);
        flMain = (FrameLayout) findViewById(R.id.flMain_MainActivity);

        tvInfo = (TextView) findViewById(R.id.tvInformation_MainActivity);
        tvSettings = (TextView) findViewById(R.id.tvSettings_MainActivity);
        tvLogin = (TextView) findViewById(R.id.tvLogin_MainActivity);

        mLoginBtn.setOnClickListener(this);
        btContactus.setOnClickListener(this);
        llMaintenance.setOnClickListener(this);
        llPayOnline.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llProjects.setOnClickListener(this);
        llPhotoVideo.setOnClickListener(this);
        tvInfo.setOnClickListener(this);
        tvSettings.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        //Load animation
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

        flMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        if (Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_LOGGEDIN).equalsIgnoreCase("1")) {
            tvLogin.setText("My Account");
        } else {
            tvLogin.setText("Login");
        }

    }


    @Override
    public void onClick(View v) {
        Intent in;
        switch (v.getId()) {
            case R.id.btContactus:
                in = new Intent(getApplicationContext(), ContactUsActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.llMaintenance_MainActivity:
                in = new Intent(getApplicationContext(), MaintenanceActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.llPayOnline_MainActivity:
                in = new Intent(getApplicationContext(), PayOnlineActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.llAboutUs_MainActivity:
                in = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.llProjects_MainActivity:
                in = new Intent(getApplicationContext(), ProjectListaActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.llPhotoVideo_MainActivity:
                in = new Intent(getApplicationContext(), PhotoVideoActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;


            case R.id.tvInformation_MainActivity:
                flLoginBottom.startAnimation(slide_down);
                flLoginBottom.setVisibility(View.GONE);
                flMain.setVisibility(View.GONE);
                in = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.tvSettings_MainActivity:
                flLoginBottom.startAnimation(slide_down);
                flLoginBottom.setVisibility(View.GONE);
                flMain.setVisibility(View.GONE);
                in = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.tvLogin_MainActivity:
                flLoginBottom.startAnimation(slide_down);
                flLoginBottom.setVisibility(View.GONE);
                flMain.setVisibility(View.GONE);
                in = new Intent(MainActivity.this, Login.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                break;

            case R.id.activity_main_login_Btn:
                if (flLoginBottom.getVisibility() == View.GONE) {
                    flLoginBottom.setVisibility(View.VISIBLE);
                    flLoginBottom.startAnimation(slide_up);
                    flMain.setVisibility(View.VISIBLE);
                } else {
                    flLoginBottom.startAnimation(slide_down);
                    flLoginBottom.setVisibility(View.GONE);
                    flMain.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
