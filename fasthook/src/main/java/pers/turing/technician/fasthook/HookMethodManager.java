package pers.turing.technician.fasthook;

import android.app.ActivityManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class HookMethodManager {
    @HookInfo(beHookedClass = TelephonyManager.class,
            beHookedMethod = "getDeviceId")
    public static String hookDeviceId(Object thiz) {
        Log.i("FAST", "TelephonyManager getDeviceId hooked");
        return "123456789012345";
    }

    @HookInfo(beHookedClass = ActivityManager.class,
            beHookedMethod = "getRunningTasks",
            beHookedMethodSig = "I")
    public static List<ActivityManager.RunningTaskInfo> hookRunningTasks(Object thiz) {
        Log.i("FAST", "TelephonyManager getDeviceId hooked");
        return new LinkedList<>();
    }

    public native static String forward(Object thiz);
}
