package core.sout;

import static core.sout.LogType.ERROR;
import static core.sout.LogType.FILE;

public abstract class Logger {

    public static void PRINT_ERROR(Exception e, String sout) {
        System.err.println();
    }

    public static void PRINT(LogType logType, String sout) {
        if (logType == ERROR || logType == FILE) System.err.println("[" + logType.name() + "]: " + sout);
        else System.out.println("[" + logType.name() + "]: " + sout);
    }

}
