package pers.turing.technician.fasthook;

import static pers.turing.technician.fasthook.FastHookManager.MODE_REWRITE;
import static pers.turing.technician.fasthook.FastHookManager.MODE_REPLACE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface HookPrivacyInfo {
    static int MODE_HOOK = 1;
    static int MODE_CALLBACK = 2;

    int hook() default MODE_HOOK;

    int mode() default MODE_REWRITE;

    String beHookedClass();

    String beHookedMethod();

    String forwardMethod() default "";
}
