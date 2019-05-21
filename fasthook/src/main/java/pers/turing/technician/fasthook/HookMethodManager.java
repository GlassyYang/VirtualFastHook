package pers.turing.technician.fasthook;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.telephony.SmsMessage;
import android.util.Log;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.hardware.Camera;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;

public class HookMethodManager {
    private static Map<String, boolean[]> HOOK_LIST;
    private static HookMethodManager manager = null;

    public static HookMethodManager Instance() {
        if (manager == null) {
            manager = new HookMethodManager();
            HOOK_LIST = new HashMap<>();
        }
        return manager;
    }

    public void register_hook_method(String app, int PARVICY) {
        if (!HOOK_LIST.containsKey(app)) {
            boolean[] value = new boolean[5];
            for (boolean b : value) b = false;
            HOOK_LIST.put(app, value);
        }
        boolean[] array = HOOK_LIST.get(app);
        assert array != null;
        array[PARVICY] = true;
    }

    public void unregister_hook_method(String app, int PARVICY) {
        if (!HOOK_LIST.containsKey(app)) return;
        boolean[] array = HOOK_LIST.get(app);
        assert array != null;
        array[PARVICY] = false;
    }

    boolean[] get_hook_method(String app) {
        if (!HOOK_LIST.containsKey(app)) return null;
        return HOOK_LIST.get(app);
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


    // 发送信息
    @HookPrivacyInfo(beHookedClass = "android.content.ContentResolver", beHookedMethod = "query", forwardMethod = "forwardQuery", pravicy = HookPrivacyInfo.PRAVICY_SMS)
    Cursor HookQuery(Object thiz, final Uri uri, String[] projection, Bundle queryArgs, CancellationSignal cancellationSignal) {
        try {
            Log.d(TAG, "HookQuery: " + uri.toString());
            if (Objects.equals(uri.getHost(), "SMS"))
                return null;
            return forwardQuery(thiz, uri, projection, queryArgs, cancellationSignal);
        } catch (Exception e) {
            return null;
        }
    }

    public static native Cursor forwardQuery(Object thiz, final Uri uri, String[] projection, Bundle queryArgs, CancellationSignal cancellationSignal);

    @HookPrivacyInfo(beHookedClass = "android.content.ContentResolver", beHookedMethod = "query", forwardMethod = "forwardQuery", pravicy = HookPrivacyInfo.PRAVICY_SMS)
    Cursor HookQuery(Object thiz, final Uri uri, String[] projection, String arg1, String[] queryArgs, String arg2, CancellationSignal cancellationSignal) {
        try {
            Log.d(TAG, "HookQuery: " + uri.toString());
            if (Objects.equals(uri.getHost(), "SMS"))
                return null;
            return forwardQuery(thiz, uri, projection, arg1, queryArgs, arg2, cancellationSignal);
        } catch (Exception e) {
            return null;
        }
    }

    // android.net.Uri,
    // java.lang.String[],
    // java.lang.String,
    // java.lang.String[],
    // java.lang.String,
    // android.os.CancellationSignal
    public static native Cursor forwardQuery(Object thiz, final Uri uri, String[] projection, String arg1, String[] queryArgs, String arg2, CancellationSignal cancellationSignal);

    // 短信读取
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


