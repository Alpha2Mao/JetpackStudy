package com.example.jetpack;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLocationListener implements LifecycleObserver {
    private static final String TAG = MyLocationListener.class.getSimpleName();

    public MyLocationListener(Activity activity, OnLocationChangedListener onLocationChangedListener){
        initLocationManager();
    }


    public interface OnLocationChangedListener{
        void onChanged(double latitude, double longitude);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startGetLocation(){
        Log.d(TAG, "startGetLocation()");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopGetLocation(){
        Log.d(TAG, "stopGetLocation()");

    }


    private void initLocationManager(){
        Log.d(TAG, "initLocationManager()");
    }
}
