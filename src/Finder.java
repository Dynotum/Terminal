import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Finder {

    private List<String> list = new ArrayList<>();
    private String nameFile;

    public Finder(String nameFile) {
        this.nameFile = nameFile;

        localdisk.start();
        diskD.start();
        // out.start();

        synchronized (localdisk) {
            try {
                localdisk.wait();
                //diskD.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String s : list) {
                System.out.println(s);
            }
        }
    }

    Thread localdisk = new Thread(() -> {
        find(nameFile, new File("C:\\"));
    });


    Thread diskD = new Thread(() -> {
        find(nameFile, new File("D:\\"));
    });

    Thread out = new Thread(() -> {
        try {
            localdisk.join();
            diskD.join();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    // find VBoxUSB.inf
    public void find(String fileToFind, File newFile) {

        File[] listFile = newFile.listFiles();

        if (listFile != null) {
            for (File file : listFile) {
                if (file.isDirectory()) {
                    find(fileToFind, file);
                } else if (fileToFind.equalsIgnoreCase(file.getName())) {
                    //System.out.println(file.getAbsolutePath());
                    list.add(file.getAbsolutePath());
                }
            }
        }
    }

    private boolean isDir(File newFile) {
        return newFile.isDirectory();
    }

    private File[] hasMoreDisks() {
        File[] paths;
        FileSystemView fsv = FileSystemView.getFileSystemView();

// returns pathnames for files and directory
        paths = File.listRoots();

// for each pathname in pathname array
/*        for (File path : paths) {
            // prints file and directory paths
            System.out.println("Drive Name: " + path);
            //System.out.println("Description: " + fsv.getSystemTypeDescription(path));
        }*/
        return paths;
    }
}
