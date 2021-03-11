package com.meo52hz.callskype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;

    private boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        listenToCall();

    }

    private void init() {

        mButton = findViewById(R.id.btnCall);

    }

    /**
     * Determine whether the Skype for Android client is installed on this device.
     */
    public boolean isSkypeClientInstalled(Context myContext) {
        PackageManager myPackageMgr = myContext.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        }
        catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    /**
     * Initiate the actions encoded in the specified URI.
     */
    public void initiateSkypeUri(Context myContext, String mySkypeUri) {

        // Make sure the Skype for Android client is installed.
        if (!isSkypeClientInstalled(myContext)) {
            goToMarket(myContext);
            return;
        }

        Intent skype_intent = new Intent("android.intent.action.VIEW");
        skype_intent.setClassName("com.skype.raider", "com.skype4life.MainActivity");
        skype_intent.setData(Uri.parse(mySkypeUri));
        startActivity(skype_intent);

//        // Create the Intent from our Skype URI.
//        Uri skypeUri = Uri.parse(mySkypeUri);
//        Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);
//
//        // Restrict the Intent to being handled by the Skype for Android client only.
//        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype4life.MainActivity"));
//        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Initiate the Intent. It should never fail because you've already established the
//        // presence of its handler (although there is an extremely minute window where that
//        // handler can go away).
//
//        myContext.startActivity(myIntent);

        return;
    }

    /**
     * Install the Skype client through the market: URI scheme.
     */
    public void goToMarket(Context myContext) {
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);

        return;
    }

    private void listenToCall(){

        mButton.setOnClickListener(v -> {

            initiateSkypeUri(this, "skype:0328431864");

        });

    }
}