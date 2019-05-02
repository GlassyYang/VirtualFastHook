package pers.turing.technician.fasthook;

import static pers.turing.technician.fasthook.FastHookManager.MODE_REWRITE;
import static pers.turing.technician.fasthook.FastHookManager.MODE_REPLACE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface HookInfo {
    int mode() default MODE_REWRITE;

    Class beHookedClass();

    String beHookedMethod();

    String beHookedMethodSig() default "";

    String hookMethod();

    String hookMethodSig() default "Ljava/lang/Object;";

    String forwardMethod() default "forward";

    String forwardMethodSig() default "Ljava/lang/Object;";
}
