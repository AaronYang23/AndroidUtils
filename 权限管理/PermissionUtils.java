package com.seeknature.audio.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.seeknature.audio.Constants;
import com.seeknature.audio.R;


/**
 * Created by  AaronYang on 2017/10/17.
 * QQ：1390939057
 * Description:   6.0(23)以上 动态权限管理
 */

public class PermissionUtils {
    public static int PERMISSON_REQUESTCODE = 10;

    //// TODO: 2017/10/17 如何减少内存泄露 使得工具类不包含context的引用

    /**
     * desc: 检查权限
     */
    public static boolean checkPermisson(Context context, String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { //手机版本小于6.0的直接通过   23
            return true;
        }
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
            return false;
        }
        return false;
    }


    /**
     * desc: 检查一组权限
     */
    public static boolean checkManyPermissons(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { //手机版本小于6.0的直接通过   23
            return true;
        }
        for (String s : permissions) {//有一个不通过就不通过
            if (ContextCompat.checkSelfPermission(context, s) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


    /**
     * desc: 请求单一权限
     */
    public static void requestSinglePermission(Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            //提示为什么用户为什么要此权限，首次请求默认为false，第一次请求被拒绝后值为true，同意为false（同意后不进入请求）
            //   所以true要提示用户为什么要此权限   false是申请权限（用于第一次）
            //这个坑就是 ：  第一次请求权限 shouldshow值是false ，第二次及其以后shouldshow值是false值都为true ,所以弹出框会矛盾，
            // 处理方法是回调中再次判断如果没获得权限 弹出自己的提示对话框  可以选择进入应用权限设置
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Log.i("shouldShow", "-true");  //true为第一次拒绝  大部分手机拒绝后不再弹出授权框,
                getRequestPermissionDialog(activity).show();//这里操作是手动打开本应用权限管理页面让用户自己去打开
            } else {
                Log.i("shouldShow", "-false");
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        }
    }

    /**
     * desc: 请求一组权限
     */
    public static void requestManyPremisson(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }


    /**
     * desc: 提示框
     */
    public static AlertDialog getRequestPermissionDialog(final Context context) {
        final AlertDialog myDialog = new AlertDialog.Builder(context).create();
        myDialog.show();
        myDialog.setCancelable(false);
        try {
            myDialog.getWindow().setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_tip_permission, null));
            WindowManager.LayoutParams params =
                    myDialog.getWindow().getAttributes();
            myDialog.getWindow().setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView tip = myDialog.getWindow().findViewById(R.id.Dialog_text);
        TextView Dialog_ok = myDialog.getWindow().findViewById(R.id.Dialog_ok);
        TextView Dialog_cancel = myDialog.getWindow().findViewById(R.id.Dialog_cancel);
        tip.setText("当前应用缺少必要权限。\n请点击\"设置\"-\"权限\"-打开所需权限。\n最后点击两次返回按钮，即可返回。");
        Dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAppDetailSetting(context);
                myDialog.dismiss();
            }
        });
        Dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        return myDialog;
    }

    //有的手机打不开应用设置
//    private static void toAppDetailSetting(Context context) {
//        //测试对象为小米 魅族 oppo/vivo  华为 三星
//        Intent intent = new Intent();
//        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//        intent.setData(uri);
//        try {
//            context.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 因为考虑到比如小米miui8和小米miui8之前的设置页面不一样会报错找不到类，如果其他手机也是这样要判断的内容太多
//            // 所以统一提示不做跳转让用户去手机管家手动设置，如果需要跳转则需要找到对应手机平台的手机管家页面
//            Toast.makeText(context, "跳转应用权限管理失败，请手动打开\"手机管家\"-\"权限管理\"-\"应用权限管理\"打开所需权限", Toast.LENGTH_LONG).show();
//        }
//    }

    /**
     * desc: 跳转到应用的设置
     */
    private static void toAppDetailSetting(Context context) {
        //这里进行权限被拒绝的处理，就跳转到本应用的程序管理器
        Toast.makeText(context, "请在权限管理中开启相关权限", Toast.LENGTH_SHORT).show();
        Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");

        String pkg = "com.android.settings";
        String cls = "com.android.settings.applications.InstalledAppDetails";

        i.setComponent(new ComponentName(pkg, cls));
        i.setData(Uri.parse("package:" + Constants.PACKAGE_NAME));
        context.startActivity(i);
    }


//    /**
//     * 代码记录：
//     * desc: activity or  fragment中回调处理
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        LogUtil.i("grantResults:" + grantResults);
//        LogUtil.i("grantResults  length:" + grantResults.length);
//        switch (requestCode) {
//            case this.PERMISSON_REQUESTCODE:

//                for (int i = 0; i < grantResults.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        LogUtil.i(grantResults[i] + "权限通过");
//                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                        PermissionUtils.getRequestPermissionDialog(this).show();
//                        break;
//                    }
//                }

//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
////                    goPage();   todoyourwork
//                } else {
//                    // Permission Denied 权限被拒绝操作
////                    Toast.makeText(Splash_Ac.this, "提示：请在设置中打开应用需要的权限", Toast.LENGTH_SHORT).show();
////                    getRequestPermissionDialog(activity).show();
//                }
//                break;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

}
