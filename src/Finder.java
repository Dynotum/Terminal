import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Finder {

    private List<String> list = new ArrayList<>();
    private List<String> listF = new ArrayList<>();
    private String nameFile;
    private String[] getDisks = hasMoreDisks();
    private OS os = OS.getInstance();                //Initialized Operating System

    public Finder(String nameFile) {
        this.nameFile = nameFile;

        OSDisk();

        //TODO  - CREAR UN HILO POR CADA DISCO PARA LA BUSQUEDA.
        //        CREAR DATABASE PARA INDEX


//        localdisk.start();
//        diskD.start();
        // out.start();

/*        synchronized (localdisk) {
            try {
                localdisk.wait();
                //diskD.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String s : list) {
                System.out.println(s);
            }
        }*/
    }

/*    Thread localdisk = new Thread(() -> {
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
    });*/

    // find VBoxUSB.inf
    public void find(String fileToFind, File newFile) {

        File[] listFile = newFile.listFiles();

        if (listFile != null) {
            for (File file : listFile) {
                listF.add(file.getName()); //file.getParent()
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

    private String[] hasMoreDisks() {
        File[] paths;
        String[] pathName;

        // returns pathnames for files and directory
        paths = File.listRoots();
        pathName = new String[paths.length];

        for (int i = 0; i < paths.length; i++) {
            pathName[i] = paths[i].toString();
        }

        return pathName;
    }

    private void OSDisk(){
        System.out.printf("Searching in %s:\n",os.get_OS_NAME());
        if (os.get_OS_NAME().contains(os.getEnumOSName().windows.toString())) {
            if (getDisks.length > 1) {
                for (String disk : getDisks) {
                    System.out.println(disk);
                }
            } else {
                System.out.println(getDisks[0]);
            }
        } else if (os.get_OS_NAME().contains(os.getEnumOSName().linux.toString())) {
            System.out.println("Hey Linux!! " + getDisks[0]); //TODO
        } else {
            System.out.println("OS not supporting yet");
        }
    }
}
