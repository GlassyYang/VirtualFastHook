package mirror.android.content.pm;

import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

/**
 * @author alongwy
 * @date 2019/3/24.
 */

public class PackageParserP28 {
    public static Class<?> TYPE = RefClass.load(PackageParserP28.class, "android.content.pm.PackageParser");
    @MethodReflectParams({"android.content.pm.PackageParser$Package", "boolean"})
    public static RefStaticMethod<Void> collectCertificates;
}
