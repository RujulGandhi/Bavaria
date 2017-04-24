package com.bavaria.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bavaria.group.Util.Utils;

/**
 * Created by archirayan1 on 3/4/2016.
 */
public class MyAccountActivity extends Activity implements View.OnClickListener {
    //    https://bavariagroup.net/en/index.php/en/component/users/?view=login
    private WebView wvLoadMyAccount;
    private ProgressBar progress;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        wvLoadMyAccount = (WebView) findViewById(R.id.wvLoadMyAccountUi);
        wvLoadMyAccount.setWebChromeClient(new MyWebViewClient());
        progress = (ProgressBar) findViewById(R.id.progressBar_MyAccountActivity);
        progress.setMax(100);

        ivBack = (ImageView) findViewById(R.id.ivBack_MyAccountActivity);
        ivBack.setOnClickListener(this);


        if (Utils.isOnline(getApplicationContext())) {
            wvLoadMyAccount.getSettings().setJavaScriptEnabled(true); // enable javascript
            wvLoadMyAccount.loadUrl("https://bavariagroup.net/en/index.php/en/component/users/?view=login");
            MyAccountActivity.this.progress.setProgress(0);
        }else{
            Toast.makeText(MyAccountActivity.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack_MyAccountActivity:
                Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out,R.anim.nothing);
                finish();
                break;
        }

    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            MyAccountActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }
}
