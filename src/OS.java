public class OS {
    private OSs osName = null;

    public OSs getOsName() {
        return osName;
    }

    public enum OSs {
        windows,
        linux,
        mac,
        solaris
    }

    public OS() {
        String os = System.getProperty("os.name").toLowerCase();

        if (getOsName() == null) {
            if (os.contains("windows")) {
                osName = OSs.windows;
            } else if (os.contains("linux")) {
                osName = OSs.linux;
            } else if (os.contains("solaris")) {
                osName = OSs.solaris;
            } else if (os.contains("mac")) {
                osName = OSs.mac;
            }
        }
    }



}
