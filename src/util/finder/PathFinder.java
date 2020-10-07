package util.finder;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

public class PathFinder {

    public static void findByName(String name, File rootDirectory, List<String> paths)
            throws IllegalArgumentException {
        if (name == null | rootDirectory == null | paths == null) {
            throw new IllegalArgumentException("Null cannot be resolved into name or rootDirectory or paths");
        }
        if (!rootDirectory.isDirectory()) {
            throw new IllegalArgumentException(" Given rootDirectory is not a directory");
        }

        for (File file : rootDirectory.listFiles()) {
            if (file.isDirectory())
                findByName(name, file, paths);

            if (file.getName().contentEquals(name))
                paths.add(file.getAbsolutePath());
        }

    }

    public static Path findFromResources(String path){
        try {
            URL url = new PathFinder().getClass().getClassLoader().getResource(path);
            return Path.of(new File(url.getPath()).getPath());
        } catch(NullPointerException exp){
            System.out.println(exp.getMessage());
            System.out.println("Return empty path");
            return Path.of("");
        }
    }
}
