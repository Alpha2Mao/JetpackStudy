package com.example.jetpack;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements IActivityView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        afterOnCreate();
        if (getLayoutId() != 0){
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }
        initViews();
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void beforeOnCreate() {

    }

    @Override
    public void afterOnCreate() {

    }

    @Override
    public void initViews() {

    }

    protected abstract int getLayoutId();
}
