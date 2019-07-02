package files;

import cache.FileCache;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class FileReader {

    public static String[] readFile(String fileName) {
        String[] fileData = FileCache.getFile(fileName);
        if (fileData != null && fileData.length > 0) return fileData;
        else if (fileData.length <= 0) {
            // Output error that file is empty
        } else {
            BufferedReader reader;
            InputStreamReader inputStreamReader;
            int counter = 0;
            String file;
            try {
                reader = new BufferedReader(inputStreamReader = new InputStreamReader(
                        ClassLoader.class.getResourceAsStream(fileName)));
                while ((file = reader.readLine()) != null) {
                    if (counter == 0) fileData = new String[Integer.valueOf(file)];
                    else fileData[counter - 1] = file;
                    counter++;
                }
                inputStreamReader.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        FileCache.addFile(fileName, fileData);
        return fileData;
    }

}
