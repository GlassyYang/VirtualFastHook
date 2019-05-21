package pers.turing.technician.fasthook;

import static pers.turing.technician.fasthook.FastHookManager.MODE_REWRITE;
import static pers.turing.technician.fasthook.FastHookManager.MODE_REPLACE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface HookPrivacyInfo {
    static int MODE_HOOK = 1;
    static int MODE_CALLBACK = 2;

    static int PRAVICY_Camera = 0;
    static int PRAVICY_Net = 1;
    static int PRAVICY_SMS = 2;
    static int PRAVICY_IMEI = 3;
    static int PRAVICY_TASK = 4;

    int hook() default MODE_HOOK;

    int mode() default MODE_REWRITE;

    int pravicy();

    String beHookedClass();

    String beHookedMethod();

    String forwardMethod() default "";
}
