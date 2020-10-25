package com.example.jetpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.jetpack.R;

import android.graphics.Bitmap;
import android.os.Bundle;

import butterknife.BindView;

public class MineActivity extends BaseActivity {

    @BindView(R.id.app_name)
    AppCompatTextView mAppName;

    @BindView(R.id.version_name)
    AppCompatTextView mVersionName;

    @BindView(R.id.version_code)
    AppCompatTextView mVersionCode;

    @BindView(R.id.package_name)
    AppCompatTextView mPackageName;

    @BindView(R.id.app_image)
    AppCompatImageView mImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    public void initViews() {
        String appName = AppUtils.getAppName(this);
        String versionName = AppUtils.getVersionName(this);
        String versionCode = String.valueOf(AppUtils.getVersionCode(this));
        String packageName = AppUtils.getPackageName(this);
        mAppName.setText(appName);
        mVersionCode.setText(versionCode);
        mPackageName.setText(packageName);
        String appVersionName = "app_v" + versionName + "_" + (BuildConfig.DEBUG ? 1 : 0) + "_" +
                (BuildConfig.DEBUG ? "debug" : "release");
        mVersionName.setText(appVersionName);
        Bitmap bitmap = AppUtils.getBitmap(this);
        mImageView.setImageBitmap(bitmap);

    }
}