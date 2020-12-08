package util.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.finder.PathFinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleReader {
    private final Logger logger = LogManager.getLogger(ConsoleReader.class);
    private BufferedReader reader;

    private final ResourceBundle properties = ResourceBundle.getBundle("properties.valuesForProg"
            , Locale.getDefault());

    public ConsoleReader(){
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine(){
        try {
            return reader.readLine();
        } catch (IOException e) {
            logger.warn(properties.getString("consoleReader.cannot_read_line")
                    + properties.getString("consoleReader.return_empty_line"));
            return "";
        }
    }

    public void close(){
        try {
            reader.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
