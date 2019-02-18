import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class Standard {

    private String line = null;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(22);
    private Finder finder;
    public OS os = OS.getInstance();

    Thread stdin = new Thread(() -> {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("> ");
            while ((line = br.readLine()) != null) {
                queue.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    Thread stdout = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String input = queue.take();

                switch (input.toLowerCase().trim().split(" ")[0]) {
                    case "exit":
                        System.out.println("Bye bye...");
                        System.exit(0);
                        break;
                    case "time":
                        System.out.println(LocalDateTime.now());
                        break;
                    case "":
                        break;
                    case "clear":
                        clear();
                        break;
                    case "find":
                        if (rules(input)) {
                            finder = new Finder(input.split(" ")[1]);
                        }
                        break;
                    case "ls":
                        if (rules(input)){
                            listFilesInDirectory(input.split(" ")[1]);
                        }
                        break;
                    default:
                        System.out.println("\'" + line + "\' it is not recognized as an internal command.");
                        break;
                }
                System.out.print("> ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    private int sizeParameters(String input) {
        return input.trim().split("\\s+").length;
    }

    private void listFilesInDirectory(String dir) throws IOException {
        if (!Files.isDirectory(Paths.get(dir))) {
            System.out.println("Cannot find the path specified.");
        } else {

            try {
                List<File> filesInFolder = Files.list(Paths.get(dir))
                        //.filter(Files::isRegularFile)
                        .sorted()
                        .map(Path::toFile)
                        .collect(Collectors.toList());

                for (File fineName : filesInFolder) {
                    System.out.println(fineName.toString().substring(dir.length()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean rules(String input) {
        int size = sizeParameters(input);
        boolean flag = true;
        if (size <= 1) {
            System.out.println("Error: Incomplete command line.");
            flag = false;
        } else if (size > 2) {
            System.out.println("Error: Too many arguments.");
            flag = false;
        }
        return flag;
    }

    public void clear() {
        //this clear screen,move cursor to the first row, first column
        final String CLEAR_CONSOLE_UNIX = "\033[H\033[2J";

        try {
            // WINDOWS only works on cmd or powershell
            if (os.get_OS_NAME().contains(os.getEnumOSName().windows.toString())) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.println(CLEAR_CONSOLE_UNIX);
            }
        } catch (IOException | InterruptedException e) {
            //ignored
        }
    }
}