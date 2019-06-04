package com.lody.virtual.server.pm;

import java.util.HashMap;
import java.util.Map;

public class HookPravicyManager {

    // 在Privacy info中定义
    //    public static int PRAVICY_Camera = 1;     摄像头权限
    //    public static int PRAVICY_Net = 2;        网络权限
    //    public static int PRAVICY_SMS = 4;        短信权限
    //    public static int PRAVICY_IMEI = 8;       手机号、IMEI等
    //    public static int PRAVICY_TASK = 16;       任务列表
    static final Map<String, Integer> mHookMethod = new HashMap<>();


    public static void put(String process, int pravicy) {
        synchronized (HookPravicyManager.class) {
            if (!mHookMethod.containsKey(process)) {
                mHookMethod.put(process, 0);
            }
            mHookMethod.put(process, pravicy);
        }
    }

    public static void remove(String packageName) {
        synchronized (HookPravicyManager.class) {
            mHookMethod.remove(packageName);
        }
    }

    public static void remove(String packageName, int pravicy) {
        synchronized (HookPravicyManager.class) {
            if (!mHookMethod.containsKey(packageName)) return;

            int p = mHookMethod.get(packageName);
            mHookMethod.put(packageName, p & (~pravicy));
        }
    }

    public static int get(String process) {
        synchronized (HookPravicyManager.class) {
            if (!mHookMethod.containsKey(process)) return 0;
            return mHookMethod.get(process);
        }
    }
}
