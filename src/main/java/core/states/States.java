package core.states;

import java.util.ArrayList;
import java.util.List;

public abstract class States {

    private static List<String> states = new ArrayList<>();

    public static void addString(String name) {
        states.add(name.toUpperCase());
    }

    public static boolean isState(String name) {
        return states.contains(name.toUpperCase());
    }

}
