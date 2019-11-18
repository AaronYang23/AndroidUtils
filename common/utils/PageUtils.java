package cc.sachsen.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * @author Z.Yun
 */
public class PageUtils {

    private static final String TAG = PageUtils.class.getSimpleName();

    private PageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 检测通知是否打开
     * @param context
     * @return
     */
    public static boolean isNotificationsEnabled(Context context) {
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    /**
     * 前往设置通知页面，如果失败就进入设置页面
     * @param activity
     */
    public static void goNotificationSetting(Activity activity) {
        ApplicationInfo appInfo = activity.getApplicationInfo();
        String pkg = activity.getApplicationContext().getPackageName();
        boolean isXiaoMi = Build.MANUFACTURER.toLowerCase().contains("xiaomi");
        int uid = appInfo.uid;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isXiaoMi) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, pkg);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid);
                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.putExtra("app_package", pkg);
                intent.putExtra("app_uid", uid);
                activity.startActivity(intent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + pkg));
                activity.startActivity(intent);
            } else {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
            Log.e(TAG, "goNotificationSetting:", e);
        }
    }
}
