package util.writer;

import org.junit.jupiter.api.*;
import util.finder.PathFinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FileWriterTest {
    Path file = PathFinder.findFromResources("util/writer/file.txt");

    @BeforeEach
    public void fillFileWithSomeLines() throws IOException { //TODO exceptions
        List<String> lines = new LinkedList<>();
        lines.add("Hey!");
        lines.add("You");
        lines.add("are");
        lines.add("awesome ;)");
        Files.write(file, lines);
    }

    @AfterEach
    public void clearFile() throws IOException { //TODO exceptions
        Files.write(file, "".getBytes());
    }

    @Test
    @DisplayName("Line should be rewritten in file")
    public void shouldRewriteLine() throws IOException { //TODO exception
        FileWriter writer = new FileWriter();
        String expected = "Hey!\n"+"We\n"+"are\n"+"awesome ;)\n";

        writer.rewriteLine("We", 1, file);
        String actual = Files.lines(file).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected rewritten line in file");
    }
}
