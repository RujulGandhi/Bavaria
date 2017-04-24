package com.bavaria.group;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by archirayan4 on 5/5/2016.
 */
public class ContactUsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {


    private GoogleMap mMap, mapZoom;
    TextView liveChatTxt, emailTxt, websiteTxt;
    ImageView ivBack, ivFb, ivTwitter, ivInstagram, ivYouTube, ivClose;
    Button btnAppStoteApple,btnPlayStore,btnSharelocation;
    private FrameLayout flMapZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SupportMapFragment mapFragmentZoom = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapZoom);
        mapFragmentZoom.getMapAsync(this);

    }

    private void init() {
        liveChatTxt = (TextView) findViewById(R.id.activity_contactup_live_chat_Txt);
        emailTxt = (TextView) findViewById(R.id.activity_contactup_email_Txt);
        websiteTxt = (TextView) findViewById(R.id.activity_contactup_website_Txt);
        ivBack = (ImageView) findViewById(R.id.ivBack_ContactUsActivity);
        ivFb = (ImageView) findViewById(R.id.ivFacebook_AboutusActivity);
        ivTwitter = (ImageView) findViewById(R.id.ivTwiiter_AboutusActivity);
        ivInstagram = (ImageView) findViewById(R.id.ivInstagram_AboutusActivity);
        ivYouTube = (ImageView) findViewById(R.id.ivYouTube_AboutusActivity);
        ivClose = (ImageView) findViewById(R.id.ivCloseContactus);
        flMapZoom = (FrameLayout) findViewById(R.id.flMapView);
        btnAppStoteApple = (Button) findViewById(R.id.btnappstoreapple);
        btnPlayStore = (Button) findViewById(R.id.btngoogleplaystore);
        btnSharelocation = (Button) findViewById(R.id.btn_sharelocation);


        emailTxt.setOnClickListener(this);
        liveChatTxt.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivYouTube.setOnClickListener(this);
        ivFb.setOnClickListener(this);
        ivInstagram.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        btnAppStoteApple.setOnClickListener(this);
        btnPlayStore.setOnClickListener(this);
        btnSharelocation.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mapZoom = map;
        LatLng sydney = new LatLng(29.375859, 47.977405);
       //archi location LatLng sydney = new LatLng(23.0178047, 72.5549591);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Kuwait")
                .snippet("Bavaria Group")
                .position(sydney));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                flMapZoom.setVisibility(View.VISIBLE);
                showNewMapWithZoom();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId())
        {
            case R.id.ivCloseContactus:
                flMapZoom.setVisibility(View.GONE);
                break;
            case R.id.ivBack_ContactUsActivity:
                intent = new Intent(ContactUsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.activity_contactup_email_Txt:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("plain/text");
                intent.setData(Uri.parse("Support@bavariagroup.net"));
                intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Support@bavariagroup.net"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "A Question from Bavaria");
                //   sendIntent.putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from my demo app :-)");
                startActivity(intent);
                break;

            case R.id.activity_contactup_live_chat_Txt:
                intent = new Intent(ContactUsActivity.this, LiveChatActivity.class);
                startActivity(intent);
                break;

            case R.id.activity_contactup_website_Txt:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bavariagroup.net"));
                startActivity(intent);
                break;

            case R.id.ivFacebook_AboutusActivity:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bavari.grop"));
                startActivity(intent);
                break;

            case R.id.ivTwiiter_AboutusActivity:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/bavariakuwait"));
                startActivity(intent);
                break;

            case R.id.ivInstagram_AboutusActivity:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/bavariagroup/?hl=en"));
                startActivity(intent);
                break;

            case R.id.ivYouTube_AboutusActivity:
//                https://www.youtube.com/watch?v=T6ielvSc7DU&list=UUrcfXe9vn1VW58Y-Dy0Y7Sw
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=T6ielvSc7DU&list=UUrcfXe9vn1VW58Y-Dy0Y7Sw"));
                startActivity(intent);
                break;

            case R.id.btnappstoreapple:

             //   Toast.makeText(ContactUsActivity.this, "hiiapple", Toast.LENGTH_SHORT).show();

                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Bavaria iPhone App: https://itunes.apple.com/us/app/bavaria/id1013155091?mt=8");
                intent.setType("text/plain");
                startActivity(intent);
                break;


            case R.id.btngoogleplaystore:
               // Toast.makeText(ContactUsActivity.this, "hiiabroid", Toast.LENGTH_SHORT).show();
                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Bavaria Android App: https://play.google.com/store/apps/details?id=com.bavaria.group&hl=en");
                intent.setType("text/plain");
                startActivity(intent);
                break;


            case R.id.btn_sharelocation:

                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Bavaria's location: https://www.google.co.in/maps?q=loc:29.3758593,47.977405");
                intent.setType("text/plain");
                startActivity(intent);
                break;


        }
    }

    private void showNewMapWithZoom() {
        LatLng sydney = new LatLng(29.375859, 47.977405);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mapZoom.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
        mapZoom.addMarker(new MarkerOptions()
                .title("Kuwait")
                .snippet("Bavaria Group")
                .position(sydney));

    }
}
