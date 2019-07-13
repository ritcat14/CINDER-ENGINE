package core.graphics.gl;

import files.FileReader;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.glCreateProgram;

public abstract class Shader {

    private final int ID;
    protected String[] attributes;
    private List<Integer> shaders = new ArrayList<>();
    private boolean bound = false;

    public Shader(String fileName) {
        ID = glCreateProgram();
        if (ID == 0) throw new RuntimeException("Cannot create shader");
        readAttributes(fileName);
    }

    protected void readAttributes(String fileName) {
        String[] fileData = FileReader.readFile(fileName);
        int count = 0;
        for (String s : fileData) {
            String[] parts = s.split(" ");
            if (parts[0].equals("//")) attributes = new String[Integer.parseInt(parts[0])];
            else if (parts[0].equals("in")) attributes[count++] = parts[2].substring(0, parts[2].length() - 2);
        }
        for (String s : attributes) {
            System.out.println(s);
        }
    }

}
