package com.bavaria.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

/**
 * Created by archirayan1 on 6/6/2016.
 */
public class SettingsActivity extends Activity implements View.OnClickListener {
    TextView uName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        uName = (TextView) findViewById(R.id.tvUserName_SettingsActivity);
        if(Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_LOGGEDIN).equalsIgnoreCase("1")){
            Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_UNAME);
            uName.setText(Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_UNAME));
        }else{
            uName.setText("Login");
        }

        uName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SettingsActivity.this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
    }
}
