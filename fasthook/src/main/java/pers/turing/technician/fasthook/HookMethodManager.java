package pers.turing.technician.fasthook;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class HookMethodManager {
    @HookInfo(beHookedClass = "android.telephony.TelephonyManager",
            beHookedMethod = "getDeviceId", forwardMethod = "forwardGetDeviceId")
    public static String hookGetDeviceId(Object thiz) {
        Log.i("FAST", "TelephonyManager getDeviceId hooked");
        return "123456789012345";
    }

    public native static String forwardGetDeviceId(Object thiz);

    @HookInfo(beHookedClass = "android.app.ActivityManager",
            beHookedMethod = "getRunningTasks", forwardMethod = "forwardGetRunningTasks")
    public static List<ActivityManager.RunningTaskInfo> hookGetRunningTasks(Object thiz, int maxNum) {
        Log.i("FAST", "ActivityManager getRunningTasks hooked");
        return new LinkedList<>();
    }

    public native static List<ActivityManager.RunningTaskInfo> forwardGetRunningTasks(Object thiz, int maxNum);

    @HookInfo(beHookedClass = "android.content.ContentResolver",
            beHookedMethod = "query", forwardMethod = "forwardQuery")
    public static Cursor hookGetContentResolver(Object thiz, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (scheme != null && host != null && scheme.equalsIgnoreCase("content") && host.equalsIgnoreCase("sms")) {
            uri = Uri.parse("x" + uri.toString());
        }
        if (thiz instanceof ContentResolver) {
            return ((ContentResolver) thiz).query(uri, projection, selection, selectionArgs, sortOrder);
        }
        return null;
    }

    @HookInfo(beHookedClass = "android.content.ContentResolver",
            beHookedMethod = "query", forwardMethod = "forwardQuery")
    public static Cursor hookGetContentResolver(Object thiz, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (scheme != null && host != null && scheme.equalsIgnoreCase("content") && host.equalsIgnoreCase("sms")) {
            uri = Uri.parse("x" + uri.toString());
        }
        if (thiz instanceof ContentResolver) {
            return ((ContentResolver) thiz).query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
        }
        return null;
    }

    @HookInfo(beHookedClass = "android.content.ContentResolver",
            beHookedMethod = "query", forwardMethod = "forwardQuery")
    public static Cursor hookGetContentResolver(Object thiz, Uri uri, String[] projection, Bundle queryArgs, CancellationSignal cancellationSignal) {
        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (scheme != null && host != null && scheme.equalsIgnoreCase("content") && host.equalsIgnoreCase("sms")) {
            uri = Uri.parse("x" + uri.toString());
        }
        if (thiz instanceof ContentResolver) {
            return ((ContentResolver) thiz).query(uri, projection, queryArgs, cancellationSignal);
        }
        return null;
    }

}
