import java.io.File;


public class Finder {

    public void find(String nameFile, File newFile) {

        File[] listFile = newFile.listFiles();

        if (listFile != null) {
            for (File file : listFile) {
                //System.out.println(file.toString());
                if (file.isDirectory()) {
                    find(nameFile, file);
                } else if (nameFile.equalsIgnoreCase(file.getName())) {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }

    public boolean isDir(File newFile) {
        return newFile.isDirectory();
    }
}
