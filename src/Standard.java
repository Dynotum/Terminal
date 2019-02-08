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
import java.util.stream.Stream;

public class Standard {

    private String line = null;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(22);

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
                    case "find":
                        if (rules(input)){
                            try (Stream<Path> stream = Files.find(Paths.get(input.split(" ")[1]), 5,
                                    (path, attr) -> path.getFileName().toString().equals("ts_events.log") )) {//rolenuser.sql
                                //System.out.println(stream.findAny().isPresent());
                                stream.forEach(System.out::println);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e){
                                e.printStackTrace();
                                System.out.println(e.toString());
                            }
                        }

                            //System.out.println(input.toLowerCase().trim().split(" ")[1]);
                        break;
                    case "ls":
                        if (rules(input))
                            listFilesInDirectory(input.split(" ")[1]);
                        break;
                    default:
                        System.out.println("\'" + line + "\' it is not recognized as an internal command.");
                        break;
                }
//                try (Stream<Path> paths = Files.list(Paths.get("C:\\Users\\csedano\\Documents"))) {
//                    paths
//                            .filter(Files::isRegularFile)
//
//                            .sorted()
////                            .collect(Collectors.toList());
//                            .forEach(System.out::println);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//                if (input.startsWith("scan")) {
//                    //scan path
//                    System.out.println("scanning " + input.split(" ")[1]);
//                }


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
}
