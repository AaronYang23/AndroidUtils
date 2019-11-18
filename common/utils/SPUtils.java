package cc.sachsen.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.SimpleArrayMap;

import com.google.gson.Gson;

/**
 * <pre>
 *     author: zhongyun
 *     time  : 2016/06/13
 *     desc  : utils about shared preference
 * </pre>
 */
@SuppressLint("ApplySharedPref")
public final class SPUtils {

    private static final String DEFAULT_NAME = "config";

    private static final SimpleArrayMap<String, SPUtils> SP_MAP = new SimpleArrayMap<>();

    private static final Gson sGson = new Gson();

    private SharedPreferences mSharedPref;

    private SharedPreferences.Editor mEditor;

    public static SPUtils getInstance() {
        return getInstance(DEFAULT_NAME, Context.MODE_PRIVATE);
    }

    public static SPUtils getInstance(String spName) {
        return getInstance(spName, Context.MODE_PRIVATE);
    }

    public static SPUtils getInstance(String spName, final int mode) {
        if (spName == null || spName.trim().isEmpty())
            spName = DEFAULT_NAME;

        SPUtils spUtils = SP_MAP.get(spName);
        if (spUtils == null) {
            spUtils = new SPUtils(spName, mode);
            SP_MAP.put(spName, spUtils);
        }

        return spUtils;
    }

    private SPUtils(final String spName, final int mode) {
        mSharedPref = Utils.getApp().getSharedPreferences(spName, mode);
        mEditor = mSharedPref.edit();
    }

    public SPUtils put(String key, Object value) {
        if (value instanceof String) {
            mEditor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            mEditor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            mEditor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            mEditor.putLong(key, (Long) value);
        } else {
            mEditor.putString(key, sGson.toJson(value));
        }

        mEditor.apply();

        return this;
    }

    public <T> T get(String key, T defValue) {
        Object result = null;

        if (defValue == null || defValue instanceof String) {
            result = mSharedPref.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            result = mSharedPref.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            result = mSharedPref.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            result = mSharedPref.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            result = mSharedPref.getLong(key, (Long) defValue);
        }

        return (T) result;
    }

    public <T> T get(String key, Class<T> clazz) {
        try {
            return sGson.fromJson(mSharedPref.getString(key, null), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean commit() {
        return mEditor.commit();
    }

    public SPUtils remove(String key) {
        mEditor.remove(key).apply();
        return this;
    }

    public boolean clear() {
        return mEditor.clear().commit();
    }

}
