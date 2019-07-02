package cache;

import cache.types.StringArrayBlock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class FileCache {

    private static int BLOCK_LIFE = 60;
    private static Map<String, StringArrayBlock> fileMap;

    public static void init() {
        fileMap = new HashMap<>();
    }

    public static void update() {
        Iterator<Map.Entry<String, StringArrayBlock>> iterator = fileMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, StringArrayBlock> entry = iterator.next();
            entry.getValue().update();
            if (!entry.getValue().isAlive()) iterator.remove();
        }
    }

    public static String[] getFile(String fileName) {
        Iterator<Map.Entry<String, StringArrayBlock>> iterator = fileMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, StringArrayBlock> entry = iterator.next();
            if (entry.getKey().equals(fileName)) return entry.getValue().getData();
        }
        return null;
    }

    public static void addFile(String fileName, String[] fileData) {
        fileMap.put(fileName, new StringArrayBlock(fileData, BLOCK_LIFE));
    }

}
