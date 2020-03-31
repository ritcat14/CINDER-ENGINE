package cache;

import cache.types.Block;
import core.sout.LogType;
import core.sout.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class FileCache {

    private static Map<String, Block> fileMap;

    public static void init() {
        Logger.PRINT(LogType.INFO, "Initiating cache");
        fileMap = new HashMap<>();
    }

    public static void update() {
        Iterator<Map.Entry<String, Block>> iterator = fileMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Block> entry = iterator.next();
            entry.getValue().update();
            if (!entry.getValue().isAlive()) iterator.remove();
        }
    }

    public static Block getBlock(String fileName) {
        Iterator<Map.Entry<String, Block>> iterator = fileMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Block> entry = iterator.next();
            if (entry.getKey().equals(fileName)) return entry.getValue();
        }
        return null;
    }

    public static void addBlock(String fileName, Block block) {
        fileMap.put(fileName, block);
    }

}
