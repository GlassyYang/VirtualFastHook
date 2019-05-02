package pers.turing.technician.fasthook;

import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import pers.turing.technician.fasthook.HookInfo;

public class HookMethodManager {
    static private Boolean init = false;
    static List<HookInfo> hookInfoList = new LinkedList<>();

    public static void Init() {
        if (init) return;
        init = true;

        Method[] methods = HookMethodManager.class.getMethods(); // 反射得到目标类的所有方法
        for (Method method : methods) {
            HookInfo Anno = method.getAnnotation(HookInfo.class);
            if (Anno != null)
                hookInfoList.add(Anno);
        }
    }

    @HookInfo(beHookedClass = TelephonyManager.class,
            beHookedMethod = "getDeviceId",
            hookMethod = "hookDeviceId")
    public static String hookDeviceId(Object thiz) {
        Log.i("FAST", "TelephonyManager getDeviceId hooked");
        return "123456789012345";
    }



    public native static String forward(Object thiz);
}
