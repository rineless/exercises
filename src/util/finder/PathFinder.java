package util.finder;

import java.io.File;
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
}
