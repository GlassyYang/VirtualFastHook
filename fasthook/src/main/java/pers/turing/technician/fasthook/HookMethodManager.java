package pers.turing.technician.fasthook;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.telephony.SmsMessage;
import android.util.Half;
import android.util.Log;
import android.hardware.Camera;
import android.util.TimeUtils;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class HookMethodManager {
    public static String TAG = "HookMethodManager";

    //private static SharedPreferences HOOK_LIST;
//    private static Map<String, boolean[]> HOOK_LIST;
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db;
    private static HookMethodManager manager = null;
    private static String SERVICE_ACTION = "io.virtualapp..action.HOOK_PRIVILEGE_SERVICE";
    // 在Privacy info中定义
    //    public static int PRAVICY_Camera = 0;     摄像头权限
    //    public static int PRAVICY_Net = 1;        网络权限
    //    public static int PRAVICY_SMS = 2;        短信权限
    //    public static int PRAVICY_IMEI = 3;       手机号、IMEI等
    //    public static int PRAVICY_TASK = 4;       任务列表

    private Context context;

    // 单例模式
    public static HookMethodManager Instance(Context context) {
        if (manager == null) {
            manager = new HookMethodManager();
            manager.context = context;
            dbHelper = new DatabaseHelper(context, "privilege",null, 1);
            db = dbHelper.getWritableDatabase();
            Log.d(TAG, "Instance: init");
        }
        return manager;
    }


    // 注册要hook的包名和要关闭的权限
    public void register_hook_method(String app, int PARVICY){
        String priStr = Integer.toString(PARVICY);
        ContentValues values = new ContentValues();
        Cursor cursor = queryPrivacy(app, priStr);
        values.put("authorized", true);
        if(cursor.getCount() != 0){
            db.update("privilege", values, "app_name=? and pril_name=?", new String[]{app, priStr});
        }else{
            values.put("app_name", app);
            values.put("pril_name", priStr);
            db.insert("privilege",null, values);
        }
    }

    public void deleteApp(String app){
        db.delete("privilege", "app_name=?", new String[]{app});
    }

    public void registerApp(String app){
        for(int i = 0; i < 5; i++){
            register_hook_method(app, i);
        }
    }

    // 取消注册要hook的包名和要关闭的权限
    public void unregister_hook_method(String app, int PARVICY) {
        String priStr = Integer.toString(PARVICY);
        ContentValues value = new ContentValues();
        value.put("authorized", false);
        Cursor cursor = queryPrivacy(app, priStr);
        if(cursor.getCount() != 0){
            db.update("privilege", value, "app_name=? and pril_name=?", new String[]{app, priStr});
        }else{
            value.put("app_name", app);
            value.put("pril_name", priStr);
            db.insert("privilege", null, value);
        }
    }

    private Cursor queryPrivacy(String app, String privacy){
        return db.query("privilege", null, "app_name=? and pril_name=?", new String[]{app, privacy}, null, null, null, null);
    }

    private Cursor queryApp(String app){
        return db.query("privilege", null, "app_name=?", new String[]{app}, null, null, null, null);
    }

    public boolean[] get_hook_method_service(String app) {
        boolean[] res = new boolean[5];
        Arrays.fill(res, true);
        Cursor cursor = queryApp(app);
        while(cursor.moveToNext()){
            int privacy = cursor.getInt(cursor.getColumnIndex("pril_name"));
            res[privacy] = cursor.getString(cursor.getColumnIndex("authorized")).equals("1");
        }
        return res;
    }

    public boolean[] get_hook_method(String app){
        final boolean[] ans = new boolean[5];
        Arrays.fill(ans, true);
//        final String appName = app;
//        final boolean[] finished = new boolean[1];
//        finished[0] = false;
//        Log.d(TAG, "get_hook_method: 开始进行进程间通信");
//        @SuppressLint("HandlerLeak") final Messenger clientMessager = new Messenger(new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                Bundle data = msg.getData();
//                ans[0] = data.getBoolean("pri_0");
//                ans[1] = data.getBoolean("pri_1");
//                ans[2] = data.getBoolean("pri_2");
//                ans[3] = data.getBoolean("pri_3");
//                ans[4] = data.getBoolean("pri_4");
//                finished[0] = true;
//            }
//        });
//
//        Log.d(TAG, "get_hook_method: 开始进行进程间通信2");
//        ServiceConnection conn = new ServiceConnection(){
//
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                Messenger  serviceMessenger = new Messenger(service);
//                Message msg = Message.obtain();
//                Bundle sent = new Bundle();
//                sent.putString("app_name", appName);
//                msg.replyTo = clientMessager;
//                try{
//                    serviceMessenger.send(msg);
//                }catch (RemoteException e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//            }
//        };
//        Intent intent = new Intent();
//        intent.setAction(SERVICE_ACTION);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//
//        Log.d(TAG, "get_hook_method: 开始进行进程间通信3");
//        PackageManager pm = context.getPackageManager();
//        //我们先通过一个隐式的Intent获取可能会被启动的Service的信息
//        ResolveInfo info = pm.resolveService(intent, 0);
//
//        if(info != null) {
//            String packageName = info.serviceInfo.packageName;
//            String serviceNmae = info.serviceInfo.name;
//            ComponentName componentName = new ComponentName(packageName, serviceNmae);
//            intent.setComponent(componentName);
//            try {
//                Log.i("DemoLog", "客户端调用bindService方法");
//                context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("DemoLog", e.getMessage());
//            }
//        }
//        //等待通信完成
//        while(!finished[0]){
//
//        }
//        Log.d(TAG, "get_hook_method: 到最后的了");
//        context.unbindService(conn);
        return ans;
    }

    // 摄像头
    @HookPrivacyInfo(beHookedClass = "android.hardware.Camera", beHookedMethod = "takePicture", forwardMethod = "forwardTakePicture", pravicy = HookPrivacyInfo.PRAVICY_Camera)

    public void hookTakePicture(Object thiz, Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg) {
        // do nothing
    }

    public static native void forwardTakePicture(Object thiz, Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg);


    // 联网
    @HookPrivacyInfo(beHookedClass = "java.net.URL", beHookedMethod = "openConnection", forwardMethod = "forwardOpenConnection", pravicy = HookPrivacyInfo.PRAVICY_Net)
    public URLConnection hookOpenConnection(Object thiz) throws java.io.IOException {
        throw new IOException();
    }

    public static native URLConnection forwardOpenConnection(Object thiz);

    // 发送多份信息
    @HookPrivacyInfo(beHookedClass = "android.telephony.SmsManager", beHookedMethod = "sendMultipartTextMessage", forwardMethod = "forwardSendMultipartTextMessage", pravicy = HookPrivacyInfo.PRAVICY_SMS)
    public void hookSendMultipartTextMessage(Object thiz, String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents) {
        // do nothing
    }

    public static native void forwardSendMultipartTextMessage(Object thiz, String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents);

    // 发送数据短信
    @HookPrivacyInfo(beHookedClass = "android.telephony.SmsManager", beHookedMethod = "sendDataMessage", forwardMethod = "forwardSendDataMessage", pravicy = HookPrivacyInfo.PRAVICY_SMS)
    public void hooksendDataMessage(Object thiz, String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        // donothing
    }

    public static native void forwardSendDataMessage(Object thiz, String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent);


    // 从卡中读取消息 getAllMessagesFromIcc
    @HookPrivacyInfo(beHookedClass = "android.telephony.SmsManager", beHookedMethod = "getAllMessagesFromIcc", forwardMethod = "forwardGetAllMessagesFromIcc", pravicy = HookPrivacyInfo.PRAVICY_SMS)
    public ArrayList<SmsMessage> hookGetAllMessagesFromIcc(Object thiz) {
        return new ArrayList<>();
    }

    public static native void forwardGetAllMessagesFromIcc(Object thiz);


    // 发送信息
    @HookPrivacyInfo(beHookedClass = "android.telephony.SmsManager", beHookedMethod = "sendTextMessage", forwardMethod = "forwardSendTextMessage", pravicy = HookPrivacyInfo.PRAVICY_SMS)
    public void hookSendTextMessage(Object thiz, String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        // 啥也不干
    }

    public static native void forwardSendTextMessage(Object thiz, String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent);


    // 短信读取 FIXME
//    @HookPrivacyInfo(beHookedClass = "android.content.ContentResolver", beHookedMethod = "query", forwardMethod = "forwardQuery", pravicy = HookPrivacyInfo.PRAVICY_SMS)
//    Cursor HookQuery(Object thiz, final Uri uri, String[] projection, Bundle queryArgs, CancellationSignal cancellationSignal) {
//        Log.d(TAG, "Query Hooked");
//        if (Objects.equals(uri.getHost(), "SMS")) {
//            Log.d(TAG, "Query SMS");
//            return forwardQuery(thiz, Uri.parse("HOOK://HOOK"), projection, queryArgs, cancellationSignal);
//        }
//        return forwardQuery(thiz, uri, projection, queryArgs, cancellationSignal);
//    }
//
//    public static native Cursor forwardQuery(Object thiz, final Uri uri, String[] projection, Bundle queryArgs, CancellationSignal cancellationSignal);
//
//    @HookPrivacyInfo(beHookedClass = "android.content.ContentResolver", beHookedMethod = "query", forwardMethod = "forwardQuery", pravicy = HookPrivacyInfo.PRAVICY_SMS)
//    Cursor HookQuery(Object thiz, Uri uri, String[] arg1, String arg2, String[] arg3, String arg4, CancellationSignal arg5) {
//        Log.d(TAG, "Query Hooked");
//        if (Objects.equals(uri.getHost(), "SMS")) {
//            Log.d(TAG, "Query SMS");
//            return forwardQuery(thiz, Uri.parse("HOOK://HOOK"), arg1, arg2, arg3, arg4, arg5);
//        }
//        return forwardQuery(thiz, uri, arg1, arg2, arg3, arg4, arg5);
//    }
//
//    public static native Cursor forwardQuery(Object thiz, Uri uri, String[] arg1, String arg2, String[] arg3, String arg4, CancellationSignal arg5);


    @HookPrivacyInfo(beHookedClass = "com.android.internal.telephony.gsm.SmsMessage$PduParser", beHookedMethod = "getUserDataUCS2", forwardMethod = "forwardGetUserDataUCS2", pravicy = HookPrivacyInfo.PRAVICY_SMS)
    public static String hookGetSMS(Object thiz, int byteCount) {
        return "";
    }

    public static native String forwardGetUserDataUCS2(Object thiz, int byteCount);

    // 手机号
    @HookPrivacyInfo(beHookedClass = "android.telephony.TelephonyManager", beHookedMethod = "getLine1Number", forwardMethod = "forwardGetLine1Number", pravicy = HookPrivacyInfo.PRAVICY_IMEI)
    public String hookGetLine1Number(Object thiz) {
        return "18888888888";
    }

    public native static String forwardGetLine1Number(Object thiz);

    // IMEI
    @HookPrivacyInfo(beHookedClass = "android.telephony.TelephonyManager", beHookedMethod = "getDeviceId", forwardMethod = "forwardGetDeviceId", pravicy = HookPrivacyInfo.PRAVICY_IMEI)
    public static String hookGetDeviceId(Object thiz) {
        Log.i("FAST", "TelephonyManager getDeviceId hooked");
        return "123456789012345";
    }

    public native static String forwardGetDeviceId(Object thiz);

    // 任务列表
    @HookPrivacyInfo(beHookedClass = "android.app.ActivityManager", beHookedMethod = "getRunningTasks", forwardMethod = "forwardGetRunningTasks", pravicy = HookPrivacyInfo.PRAVICY_TASK)
    public static List<ActivityManager.RunningTaskInfo> hookGetRunningTasks(Object thiz, int maxNum) {
        Log.i("FAST", "ActivityManager getRunningTasks hooked");
        return new LinkedList<>();
    }

    public native static List<ActivityManager.RunningTaskInfo> forwardGetRunningTasks(Object thiz, int maxNum);
}


