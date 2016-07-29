package com.example.jiangrui.myapplication;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Android View相关工具类
 */
public class ViewUtil {

    // 金额输入框相关设置
    // 限制输入位数13位（含小数及小数点），限制小数点后两位
    public void setMoneyEditText(Activity myActivityReference, final EditText etInput) {
        etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        etInput.setSelection(etInput.getText().length());
        etInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        etInput.setText(s);
                        etInput.setSelection(s.length());
                    }
                }
                if (s.length() > 0 && s.charAt(0) == '.') {
                    etInput.setText("0" + s.toString());
                    etInput.setSelection(2);
                }
                if (s.length() > 1 && s.charAt(0) == '0' && s.charAt(1) != '.') {
                    etInput.setText(s.toString().subSequence(1, s.length()));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }
}
