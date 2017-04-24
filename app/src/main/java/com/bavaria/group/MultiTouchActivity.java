package com.bavaria.group;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bavaria.group.Util.TouchImageView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by archirayan1 on 6/2/2016.
 */
public class MultiTouchActivity extends Activity {
    private String image;
    private TouchImageView ivTouch;
    private LinearLayout ll;
    private ImageView ivback,ivshare;
    public  Uri bmpUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_multitouch);
        ivback = (ImageView) findViewById(R.id.ivBack_MultitouchActivity);
        ivshare = (ImageView) findViewById(R.id.ivShare_MultitouchActivity);
        ll = (LinearLayout) findViewById(R.id.linearlyout);
        ivTouch = (TouchImageView) findViewById(R.id.ivTouchImage);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            image = extra.getString("img");
            Log.e("%%%%%%%%", "" + image);
        }
        Glide.with(MultiTouchActivity.this).load(image).placeholder(R.drawable.default_img).into(ivTouch);
        ivTouch.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                bmpUri = getLocalBitmapUri(ivTouch);
                RectF rect = ivTouch.getZoomedRect();
                float currentZoom = ivTouch.getCurrentZoom();
                boolean isZoomed = ivTouch.isZoomed();
                //Log.e("sfsdfdsf", ""+currentZoom+","+isZoomed);
                //Do whater ever stuff u want
            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MultiTouchActivity.this, "CLICK", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        ivshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  Uri bmpUri = getLocalBitmapUri(imageView);
                if (bmpUri != null) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.setType("*/*");
                    startActivity(Intent.createChooser(shareIntent, "Share Image"));
                } else {
                    Toast.makeText(MultiTouchActivity.this, "Image not loaded yet.", Toast.LENGTH_SHORT).show();
                }


                Bitmap bitmap = captureView(ll);
                String pathofBmp = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap,null, null);
                Uri bmpUri = Uri.parse(pathofBmp);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));


            }
        });

//        ivTouch.setMaxZoom(4f);

    }

    public Uri getLocalBitmapUri(ImageView ivTouch) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = ivTouch.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) ivTouch.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Hello", "Hello@@");
        }
        return bmpUri;
    }


    public static Bitmap captureView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
//        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }
}
