package files;

import cache.FileCache;
import cache.types.StringArrayBlock;
import core.CinderEngine;
import core.sout.LogType;
import core.sout.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public abstract class FileReader {

    public static String[] readFile(String fileName) {
        StringArrayBlock loadedBlock = (StringArrayBlock)FileCache.getBlock(fileName);
        String[] fileData = null;
        if (loadedBlock != null) {
            fileData = ((StringArrayBlock) FileCache.getBlock(fileName)).getData();
            if (fileData != null && fileData.length > 0) return fileData;
            else if (fileData.length <= 0) {
                Logger.PRINT(LogType.FILE, "Empty file, removing from cache.");
                loadedBlock.kill();
            }
        } else {
            BufferedReader reader;
            InputStreamReader inputStreamReader;
            int counter = 0;
            String file;
            try {
                reader = new BufferedReader(inputStreamReader = new InputStreamReader(
                        Objects.requireNonNull(CinderEngine.class.getClassLoader().getResourceAsStream(fileName))));
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
        FileCache.addBlock(fileName, new StringArrayBlock(fileData));
        return fileData;
    }

}
