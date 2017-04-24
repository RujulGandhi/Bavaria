package com.bavaria.group.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

/**
 * Created by archirayan1 on 3/11/2016.
 */
public class Utils {
    public static boolean isOnline(Context context) {
        if (context != null) {
            final ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mgr != null) {

                final NetworkInfo mobileInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                final NetworkInfo wifiInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (wifiInfo != null && wifiInfo.isAvailable() && wifiInfo.isAvailable() && wifiInfo.isConnected()) {

                    final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    final WifiInfo wifiInfoStatus = wifiManager.getConnectionInfo();
                    final SupplicantState supState = wifiInfoStatus.getSupplicantState();

                    if (String.valueOf(supState).equalsIgnoreCase("COMPLETED") || String.valueOf(supState).equalsIgnoreCase("ASSOCIATED")) {
                        // WiFi is connected
                        return true;
                    }
                }

                if (mobileInfo != null && mobileInfo.isAvailable() && mobileInfo.isConnected()) {
                    // Mobile Network is connected
                    return true;
                }
            }
        }
        return false;
    }

    public static void WriteSharePrefrence(Context context, String key,
                                           String value) {
        @SuppressWarnings("static-access")
//        SharedPreferences write_Data = context.getSharedPreferences(
//                Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
//        Editor editor = write_Data.edit();
//        editor.putString(key, values);
//        editor.apply();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String ReadSharePrefrence(Context context, String key) {
//        SharedPreferences read_data = context.getSharedPreferences(
//                Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
//
//        return read_data.getString(key, "");

        String data;
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        data = preferences.getString(key, "");
        editor.commit();
        return data;
    }

    public static void showAlertBox(Context context,String msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Alert");

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
