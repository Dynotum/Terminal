/*
SINGLETON class
*/
public class OS {
    private static OS osInstance = null;
    private enumOS enumOSName = null;
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    public enum enumOS {
        windows,
        linux,
        mac,
        solaris
    }

    private OS() {

        if (OS_NAME.contains("windows")) {
            enumOSName = enumOSName.windows;
        } else if (OS_NAME.contains("linux")) {
            enumOSName = enumOSName.linux;
        } else if (OS_NAME.contains("solaris")) {
            enumOSName = enumOSName.solaris;
        } else if (OS_NAME.contains("mac")) {
            enumOSName = enumOSName.mac;
        }
    }

    public static OS getInstance() {
        if (osInstance == null) {
            osInstance = new OS();
        }
        return osInstance;
    }

    public enumOS getEnumOSName() {
        return enumOSName;
    }

    public static String get_OS_NAME() {
        return OS_NAME;
    }


}
