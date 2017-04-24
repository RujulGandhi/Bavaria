package com.bavaria.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by archirayan4 on 5/17/2016.
 */
public class VideoWeb extends AppCompatActivity {
    ImageView icBackImg;
    TextView proNameTxt, descTxt;
    String vid, proName, proDesc;
    WebView vwVideo;
    final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoweb);
        Bundle extras = getIntent().getExtras();
        vid = extras.getString("video");
        proName = extras.getString("pro_name");
        proDesc = extras.getString("pro_small_desc");

        vwVideo = (WebView) findViewById(R.id.wvVideo_VideoWeb);
        WebSettings settings = vwVideo.getSettings();
        settings.setJavaScriptEnabled(true);
        proNameTxt = (TextView) findViewById(R.id.activity_videoweb_pro_name_Txt);
        descTxt = (TextView) findViewById(R.id.activity_videoweb_desc_Txt);
        icBackImg = (ImageView) findViewById(R.id.activity_videoweb_icBack_Img);
        icBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoWeb.this, VideoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
            }
        });

        vwVideo.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setTitle("Loading...");
                activity.setProgress(progress * 100);
                if (progress == 100)
                    activity.setTitle(proName);
            }
        });

        vwVideo.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        proNameTxt.setText("" + proName);
        descTxt.setText("" + proDesc);
//        vwVideo.loadUrl("" + vid);
        vwVideo.loadUrl(vid);

    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(vwVideo, (Object[]) null);

        } catch (ClassNotFoundException cnfe) {
            Log.e("EXCEPTION", "" + cnfe);
        } catch (NoSuchMethodException nsme) {
            Log.e("EXCEPTION", "" + nsme);
        } catch (InvocationTargetException ite) {
            Log.e("EXCEPTION", "" + ite);
        } catch (IllegalAccessException iae) {
            Log.e("EXCEPTION", "" + iae);
        }
    }
}
