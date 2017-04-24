package com.bavaria.group;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class ShareActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView backImg;
    Button btMail, btTwitter, btPlayStore, btFb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        init();
    }

    private void init() {
        backImg = (ImageView) findViewById(R.id.activity_share_back_Img);
        btMail = (Button) findViewById(R.id.activity_share_mail_Btn);
        btTwitter = (Button) findViewById(R.id.btTwitter_ShareActivity);
        btPlayStore = (Button) findViewById(R.id.btPlayStore_ShareActivity);
        btFb = (Button) findViewById(R.id.btFB_ShareActivity);

        btMail.setOnClickListener(this);
        backImg.setOnClickListener(this);
        btTwitter.setOnClickListener(this);
        btPlayStore.setOnClickListener(this);
        btFb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_share_back_Img:
                Intent intent = new Intent(ShareActivity.this, InformationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                break;

            case R.id.activity_share_mail_Btn:
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse(""));
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Bavaria Group");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "hi, i Downloaded the Bavaria-app.\nwant to see for yourself? \ndownload the app at\nhttp://apps.appmachine.com/bavaria");
                startActivity(sendIntent);
                break;

            case R.id.btTwitter_ShareActivity:
                promptForShare();
                break;

            case R.id.btPlayStore_ShareActivity:
                promptForShare();
                break;

            case R.id.btFB_ShareActivity:
                promptForShare();
                break;

        }


    }

    private void promptForShare() {

        AlertDialog.Builder upgradeAlert = new AlertDialog.Builder(ShareActivity.this);
        upgradeAlert.setTitle("Share");
        upgradeAlert.setMessage("http://apps.appmachine.com/bavaria");
        upgradeAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //set progress dialog and start the in app purchase process
                dialog.cancel();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        upgradeAlert.show();
    }

}
