package io.virtualapp.hook;

import android.util.Log;

public class HookDeviceId {
    public static String hook(Object thiz) {
        Log.i("FAST", "TelephonyManager getDeviceId hooked");
        return "123456789012345";
    }

    public native static String forward(Object thiz);
}
