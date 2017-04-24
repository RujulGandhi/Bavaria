package com.bavaria.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

/**
 * Created by archirayan1 on 3/11/2016.
 */
public class PhotoVideoActivity extends Activity implements View.OnClickListener {
    LinearLayout llPhoto, llVideo, llCatalogue;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_video);
        inIt();
    }

    private void inIt() {
        llPhoto = (LinearLayout) findViewById(R.id.llPhoto_PhotoVideoActivity);
        llVideo = (LinearLayout) findViewById(R.id.llVideo_PhotoVideoActivity);
        llCatalogue = (LinearLayout) findViewById(R.id.llCatalog_PhotoVideoActivity);
        ivBack = (ImageView) findViewById(R.id.ivBack_PhotoVideoActivity);
        llPhoto.setOnClickListener(this);
        llVideo.setOnClickListener(this);
        llCatalogue.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.llPhoto_PhotoVideoActivity:
                Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_PHOTO_OR_VIDEO,"0");
                intent = new Intent(getApplicationContext(), PhotoVideoListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.llCatalog_PhotoVideoActivity:
                Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_PHOTO_OR_VIDEO,"1");
                intent = new Intent(getApplicationContext(), PhotoVideoListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.llVideo_PhotoVideoActivity:
                intent = new Intent(getApplicationContext(), VideoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

                break;

            case R.id.ivBack_PhotoVideoActivity:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
                finish();

                break;
        }


    }
}
