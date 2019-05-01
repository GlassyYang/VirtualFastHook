package io.virtualapp.hook;

import android.telephony.TelephonyManager;

public class HookInfo {
    public static String[][] HOOK_ITEMS = {{
            "1",
            TelephonyManager.class.getName(), "getDeviceId", "",
            "io.virtualapp.hook.HookDeviceId", "hook", "Ljava/lang/Object;",
            "io.virtualapp.hook.HookDeviceId", "forward", "Ljava/lang/Object;"
    }};
}