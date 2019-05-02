package pers.turing.technician.fasthook;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class HookMethodManager {
    public native static String forwardGetDeviceId(Object thiz);

    @HookPrivacyInfo(beHookedClass = "android.telephony.TelephonyManager", beHookedMethod = "getDeviceId", forwardMethod = "forwardGetDeviceId")
    public static String hookGetDeviceId(Object thiz) {
        Log.i("FAST", "TelephonyManager getDeviceId hooked");
        return "123456789012345";
    }

    public native static List<ActivityManager.RunningTaskInfo> forwardGetRunningTasks(Object thiz);

    @HookPrivacyInfo(beHookedClass = "android.app.ActivityManager", beHookedMethod = "getRunningTasks", forwardMethod = "forwardGetRunningTasks")
    public static List<ActivityManager.RunningTaskInfo> hookGetRunningTasks(Object thiz, int maxNum) {
        Log.i("FAST", "ActivityManager getRunningTasks hooked");
        return new LinkedList<>();
    }

    @HookPrivacyInfo(beHookedClass = "android.content.ContentResolver", beHookedMethod = "query")
    public static Cursor hookGetContentResolver(Object thiz, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @HookPrivacyInfo(beHookedClass = "android.content.ContentResolver", beHookedMethod = "query")
    public static Cursor hookGetContentResolver(Object thiz, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        return null;
    }

    @HookPrivacyInfo(beHookedClass = "android.content.ContentResolver", beHookedMethod = "query")
    public static Cursor hookGetContentResolver(Object thiz, Uri uri, String[] projection, Bundle queryArgs, CancellationSignal cancellationSignal) {
        return null;
    }

    @HookPrivacyInfo(beHookedClass = "android.telephony.SmsManager", beHookedMethod = "sendTextMessage", forwardMethod = "forwardSmsManager")
    public static void hookSmsManager(Object thiz, String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        forwardSmsManager(thiz, destinationAddress, scAddress, text, sentIntent, deliveryIntent);
    }

    public static native void forwardSmsManager(Object thiz, String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent);
}


