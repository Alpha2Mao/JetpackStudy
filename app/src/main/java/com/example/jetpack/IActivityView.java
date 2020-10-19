package com.example.jetpack;

public interface IActivityView {
    void initViews();
    void initEvents();
    void beforeOnCreate();
    void afterOnCreate();
    void initImmersionBar();
}