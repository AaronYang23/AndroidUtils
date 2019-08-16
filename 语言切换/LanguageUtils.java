package cc.sachsen.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import java.lang.ref.WeakReference;
import java.util.Locale;


/**
 * create by AaronYang on 2019-08-13.
 * email: 1390939057@qq.com
 * github: AaronYang23
 * describe: 语言切换工具
 */
public class LanguageUtils {


    public static final String LOCALE_AUTO = "auto"; //提供的lacale里面 国家代码没有
    public static final String LOCALE_CN = "cn";//Locale.SIMPLIFIED_CHINESE; //提供的lacale里面 国家代码没有
    public static final String LOCALE_EN = "en";


    //在切换语言获取Resource对象时候，8.0以下 activity和application都可以，8.0之后只能用activity，否则切换无效


    /**
     * desc: 获取系统语言
     */
    public static Locale getSystemLocale(){
        return Locale.getDefault();
    }


    /**
     * desc: 获取app当前的语言环境
     */
    public static Locale getAppLocale(WeakReference<Context> context) {

        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0之上
            locale = context.get().getResources().getConfiguration().getLocales().get(0);
        } else {
            //7.0以下
            Configuration configuration = context.get().getResources().getConfiguration();
            locale = configuration.locale;
        }
        return locale;
    }


    /**
     * desc: 设置App的语言
     */
    public static void setAppLocale(WeakReference<Context> context, Locale locale) {

        Configuration configuration = context.get().getResources().getConfiguration();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            //7.0以上
            configuration.setLocale(locale);
            context.get().createConfigurationContext(configuration);
        }else {
            //7.0以下
            configuration.locale = locale;
            context.get().getResources().updateConfiguration(configuration,null);
        }
    }


    /**
     * desc: 保存当前设置的语言
     */
    public static void saveLocale(WeakReference<Context> context, String localeType) {
        SharedPreferences preferences = context.get().getSharedPreferences("ayShare", Context.MODE_PRIVATE);
        preferences.edit().putString("localeType", localeType).apply();
    }

    /**
     * desc: 获取保存的语言字段
     */
    public static String getLocalLocale(WeakReference<Context> context) {
        return context.get().getSharedPreferences("ayShare", Context.MODE_PRIVATE).getString("localeType", "");
    }



}
