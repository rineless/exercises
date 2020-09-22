package util.finder;

import java.io.File;
import java.util.List;

public class PathFinder {

    public static void findByName(String name, File rootDirectory, List<String> paths){
        if(name != null | rootDirectory != null | paths != null) {
            if(rootDirectory.isDirectory()) {

                for (File file : rootDirectory.listFiles()) {
                    if (file.isDirectory())
                        findByName(name, file, paths);

                    if (file.getName().contentEquals(name))
                        paths.add(file.getAbsolutePath());
                }
            }
        }

        throw new IllegalArgumentException("Null cannot be resolved into name or rootDirectory or paths" +
                "; Or given rootDirectory is not a directory");
    }
}
