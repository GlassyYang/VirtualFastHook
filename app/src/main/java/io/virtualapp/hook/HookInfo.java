package io.virtualapp.hook;

public class HookInfo {
    public static String[][] HOOK_ITEMS = {{
            "1",
            "android.telephony.TelephonyManager", "getDeviceId", "()Ljava/lang/String;",
            "io.virtualapp.hook.HookDeviceId", "hook", "Ljava/lang/Object;",
            "io.virtualapp.hook.HookDeviceId", "forward", "Ljava/lang/Object;"
    }};
}
