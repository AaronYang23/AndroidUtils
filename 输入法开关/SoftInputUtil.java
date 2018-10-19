package com.seeknature.audio.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * create by AaronYang on 2018/9/19.
 * email: 1390939057@qq.com
 * github: AaronYang23
 * describe: 输入法工具
 */
public class SoftInputUtil {

    /**
     * desc: 关闭输入法/虚拟键盘
     */
    private void closeSoftInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive())
            imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
    }

    /**
     * desc: 弹出输入法/虚拟键盘
     */
    private void showSoftInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

}
