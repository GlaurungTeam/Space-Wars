package helpers;

import entities.Constants;

import java.io.*;

public class Reader {

    public String readString(String path) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(Constants.PROJECT_PATH + path);

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line = bufferedReader.readLine();

            while(line != null){
                for (char ch :
                        line.toCharArray()) {
                    stringBuilder.append(ch);
                }

                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
