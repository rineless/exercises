package util.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader {
    private BufferedReader reader;

    public ConsoleReader(){
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine(){
        try {
            return reader.readLine(); //TODO get more information about .lines()
        } catch (IOException e) {
            System.out.println("Cannot read line from console. Returned empty line");
            return "";
        }
    }

}
