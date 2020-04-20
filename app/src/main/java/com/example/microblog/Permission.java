package com.example.microblog;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class Permission {

    Activity activity;


    public Permission(Activity activity) {
        this.activity = activity;
    }

    public static final String TAG = "xing";
    public static final int REQUEST_CODE_PRESSION_PHONE_STATE = 1;

    public boolean checkPerssion() {
        //版本号的判断

        Log.d(activity.getClass().getName(),"当前版本号：" + Build.VERSION.SDK_INT+"");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return false;
        } else {


            //权限有三种状态（1、允许  2、提示  3、禁止）
            int permission = ActivityCompat.checkSelfPermission(activity.getApplication(), Manifest.permission.READ_PHONE_STATE);
            // 2、提示  3、禁止
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "click: -------sdk>23---------------------没有获得权限");
                //如果设置中权限是禁止的咋返回false;如果是提示咋返回的是true
                boolean is = ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.READ_PHONE_STATE);
                Log.i(TAG, "click: ------is----------------------" + is);
                if (is) {
                    Log.i(TAG, "click: ------提示----------------------");

                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            REQUEST_CODE_PRESSION_PHONE_STATE);
                } else {
                    Log.i(TAG, "click: ------禁止----------------------");
                    gotoHuaweiPermission();
                }

            } else {
                //1、允许
                getDeviceId();
            }
        }

        return true;
    }

    private String getDeviceId() {
//        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        Toast.makeText(this, "" + manager.getDeviceId(), Toast.LENGTH_LONG).show();
        return "sadsdasdsadsa";//manager.getDeviceId();
    }


    //始终允许
// --permissions--------android.permission.READ_PHONE_STATE
//    : --------grantResults-------0
//禁止  和 始终禁止
// --permissions--------android.permission.READ_PHONE_STATE
//    : --------grantResults-------1
   // @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     //   super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PRESSION_PHONE_STATE:
                if (grantResults[0] == 0) {
                    getDeviceId();
                } else {

                }
                break;
            default:
                break;

        }
    }

    // 华为的权限管理页面

    private void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
         //   startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "gotoHuaweiPermission: ------------------");
        }

    }


    //  跳转到系统的设置界面
    //获取应用详情页面intent

    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        return localIntent;
    }


    // 跳转到魅族的权限管理系统

    private void gotoMeizuPermission() {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoHuaweiPermission();
        }
    }


    //跳转到miui的权限管理页面

    private void gotoMiuiPermission() {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", activity.getPackageName());
        try {
            activity.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            gotoMeizuPermission();
        }
    }

}
