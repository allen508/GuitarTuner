package com.allen508.strumKit;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Mobile Ads SDK.
        //MobileAds.initialize(this, new OnInitializationCompleteListener() {
        //    @Override
        //    public void onInitializationComplete(InitializationStatus initializationStatus) {}
        //});

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        //MobileAds.setRequestConfiguration(
        //        new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        //                .build());


    }
}
