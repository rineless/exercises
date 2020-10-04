package util.reader;

import java.nio.file.Path;
import java.util.*;

public abstract class Reader {
    abstract public List<String> receiveLinesAsList(Path path);
}
