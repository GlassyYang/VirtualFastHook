package pers.turing.technician.fasthook;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class HookMethodManager {
    @HookInfo(beHookedClass = "android.telephony.TelephonyManager",
            beHookedMethod = "getDeviceId")
    public static String hookDeviceId(Object thiz) {
        Log.i("FAST", "TelephonyManager getDeviceId hooked");
        return "123456789012345";
    }

    @HookInfo(beHookedClass = "android.app.ActivityManager",
            beHookedMethod = "getRunningTasks",
            beHookedMethodSig = "I")
    public static List<ActivityManager.RunningTaskInfo> hookRunningTasks(Object thiz) {
        Log.i("FAST", "ActivityManager getRunningTasks hooked");
        return new LinkedList<>();
    }

    public native static String forward(Object thiz);

    public native static String forward(Object thiz, Context context);
}
