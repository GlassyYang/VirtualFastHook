package pers.turing.technician.fasthook;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.telephony.SmsMessage;
import android.util.Log;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.hardware.Camera;
import android.media.MediaRecorder;

public class HookMethodManager {
    // 摄像头
    @HookPrivacyInfo(beHookedClass = "android.hardware.Camera", beHookedMethod = "takePicture", forwardMethod = "forwardTakePicture")
    public void hookTakePicture(Object thiz, Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg) {
        // do nothing
    }

    public static native void forwardTakePicture(Object thiz, Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg);


    // 联网
    @HookPrivacyInfo(beHookedClass = "java.net.URL", beHookedMethod = "openConnection", forwardMethod = "forwardOpenConnection")
    public URLConnection hookOpenConnection(Object thiz) throws java.io.IOException {
        throw new IOException();
    }

    public static native URLConnection forwardOpenConnection(Object thiz);

    // 发送多份信息
    @HookPrivacyInfo(beHookedClass = "android.telephony.SmsManager", beHookedMethod = "sendMultipartTextMessage", forwardMethod = "forwardSendMultipartTextMessage")
    public void hookSendMultipartTextMessage(Object thiz, String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents) {
        // do nothing
    }

    public static native void forwardSendMultipartTextMessage(Object thiz, String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents);

    // 发送数据短信
    @HookPrivacyInfo(beHookedClass = "android.telephony.SmsManager", beHookedMethod = "sendDataMessage", forwardMethod = "forwardSendDataMessage")
    public void hooksendDataMessage(Object thiz, String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        // donothing
    }

    public static native void forwardSendDataMessage(Object thiz, String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent);


    // 从卡中读取消息 getAllMessagesFromIcc
    @HookPrivacyInfo(beHookedClass = "android.telephony.SmsManager", beHookedMethod = "getAllMessagesFromIcc", forwardMethod = "forwardGetAllMessagesFromIcc")
    public ArrayList<SmsMessage> hookGetAllMessagesFromIcc(Object thiz) {
        return new ArrayList<>();
    }

    public static native void forwardGetAllMessagesFromIcc(Object thiz);


    // 发送信息
    @HookPrivacyInfo(beHookedClass = "android.telephony.SmsManager", beHookedMethod = "sendTextMessage", forwardMethod = "forwardSendTextMessage")
    public void hookSendTextMessage(Object thiz, String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        // 啥也不干
    }

    public static native void forwardSendTextMessage(Object thiz, String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent);


    // 手机号
    @HookPrivacyInfo(beHookedClass = "android.telephony.TelephonyManager", beHookedMethod = "getLine1Number", forwardMethod = "forwardGetLine1Number")
    public String hookGetLine1Number(Object thiz) {
        return "18888888888";
    }

    public native static String forwardGetLine1Number(Object thiz);

    // IMEI
    @HookPrivacyInfo(beHookedClass = "android.telephony.TelephonyManager", beHookedMethod = "getDeviceId", forwardMethod = "forwardGetDeviceId")
    public static String hookGetDeviceId(Object thiz) {
        Log.i("FAST", "TelephonyManager getDeviceId hooked");
        return "123456789012345";
    }

    public native static String forwardGetDeviceId(Object thiz);

    // 任务列表
    @HookPrivacyInfo(beHookedClass = "android.app.ActivityManager", beHookedMethod = "getRunningTasks", forwardMethod = "forwardGetRunningTasks")
    public static List<ActivityManager.RunningTaskInfo> hookGetRunningTasks(Object thiz, int maxNum) {
        Log.i("FAST", "ActivityManager getRunningTasks hooked");
        return new LinkedList<>();
    }

    public native static List<ActivityManager.RunningTaskInfo> forwardGetRunningTasks(Object thiz);

    // 短信读取
    @HookPrivacyInfo(beHookedClass = "com.android.internal.telephony.gsm.SmsMessage$PduParser", beHookedMethod = "getUserDataUCS2", forwardMethod = "forwardGetUserDataUCS2")
    public static String hookGetSMS(Object thiz, int byteCount) {
        return "";
    }

    public static native String forwardGetUserDataUCS2(Object thiz, int byteCount);
}


