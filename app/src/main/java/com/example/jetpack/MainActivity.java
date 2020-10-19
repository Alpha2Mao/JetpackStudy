package com.example.jetpack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MyLocationListener myLocationListener;


    @Override
    public void initViews() {
        myLocationListener = new MyLocationListener(this, new MyLocationListener.OnLocationChangedListener() {
            @Override
            public void onChanged(double latitude, double longitude) {
                Log.i(TAG, "The latitude and longitude is " + latitude + " * " + longitude);
            }
        });
        getLifecycle().addObserver(myLocationListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}