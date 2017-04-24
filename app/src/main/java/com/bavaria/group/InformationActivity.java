package com.bavaria.group;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by archirayan4 on 5/9/2016.
 */
public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView backImg;
    Button supportBtn, shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        init();
    }

    private void init() {
        backImg = (ImageView) findViewById(R.id.activity_information_back_Img);
        supportBtn = (Button) findViewById(R.id.activity_information_support_Btn);
        shareBtn = (Button) findViewById(R.id.activity_information_share_Btn);
        shareBtn.setOnClickListener(this);
        supportBtn.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.activity_information_support_Btn:
                String s = "Debug-infos:";
                s += "\n OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
                s += "\n OS API Level: " + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.VERSION.SDK_INT + ")";
                s += "\n Device: " + android.os.Build.DEVICE;
                s += "\n Model (and Product): " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("plain/text");
                intent.setData(Uri.parse("Support@bavariagroup.net"));
                intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Support@bavariagroup.net"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Bavaria Group");
                intent.putExtra(Intent.EXTRA_TEXT, "Description of the problem : \n\n\n\n\n [Please describe the issue your are encountering here...] \n\nAdditional information about the app for this problem :\n\n\n\n" + s + "\n\n\nSent from my Android");
                startActivity(intent);
                break;

            case R.id.activity_information_share_Btn:
                intent = new Intent(InformationActivity.this, ShareActivity.class);
                startActivity(intent);
                break;

            case R.id.activity_information_back_Img:
                intent = new Intent(InformationActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                break;
        }
    }
}
