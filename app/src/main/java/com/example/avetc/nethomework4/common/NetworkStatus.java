package com.example.avetc.nethomework4.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.example.avetc.nethomework4.App;


public class NetworkStatus {
    private static final String TAG = "NetworkStatus";

    public enum Status {
        WIFI,
        MOBILE,
        ETHERNET,
        OFFLINE
    }

    private static boolean isAirplane() {
        return Settings.Global.getInt(App.getInstance().getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

//    public static Status getStatus() {
//        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if (null != activeNetwork) {
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
//                return Status.WIFI;
//            }
//
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET) {
//                return Status.ETHERNET;
//            }
//
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                return Status.MOBILE;
//            }
//        }
//
//        return Status.OFFLINE;
//    }
    private static Status currentStatus = Status.OFFLINE;
    private static Status getStatus() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
            {
                currentStatus = Status.WIFI;
            }

            if(activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET)
            {
                currentStatus = Status.ETHERNET;
            }

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                currentStatus = Status.MOBILE;
            }
        }
        else
        {
            currentStatus = Status.OFFLINE;
        }

        return currentStatus;
    }



    public static boolean isOnline() {
        return getStatus().equals(Status.WIFI) || getStatus().equals(Status.MOBILE) || getStatus().equals(Status.ETHERNET);
    }

    public static boolean isWifi() {
        return getStatus().equals(Status.WIFI);
    }

    public static boolean isEthernet() {
        return getStatus().equals(Status.ETHERNET);
    }

    public static boolean isMobile() {
        return getStatus().equals(Status.MOBILE);
    }

    public static boolean isOffline() {
        return getStatus().equals(Status.OFFLINE);
    }
}
