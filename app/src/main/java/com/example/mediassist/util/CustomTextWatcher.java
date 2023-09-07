package com.example.mediassist.util;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public class CustomTextWatcher implements TextWatcher {

    private CheckForEmptyCallBack checkForEmptyCallBack;
    private EnteredTextCallBack enteredTextCallBack;
    private TextView errorText;


    public CustomTextWatcher(TextView error, CheckForEmptyCallBack checkForEmptyCallBack) {
        this.checkForEmptyCallBack = checkForEmptyCallBack;
        this.errorText = error;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            this.errorText.setVisibility(View.VISIBLE);
        } else {
            this.errorText.setVisibility(View.GONE);
        }
        this.checkForEmptyCallBack.checkForEmpty();

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


}