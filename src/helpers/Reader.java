package helpers;

import java.io.FileNotFoundException;

public interface Reader {

    String read(String path) throws FileNotFoundException;
}