package core.graphics;

/*
 *  Main window class
 */

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private volatile long ID;
    private volatile double width, height;

    public Window(double width, double height) {
        setSize(width, height);
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    }

    public void init() {
        setID(glfwCreateWindow((int)getWidth(), (int)getHeight(), "CINDER-ENGINE", NULL, NULL));
        if (getID() == NULL) throw new RuntimeException("Failed to create GLFW window");
        // Key set up
        glfwWaitEvents();

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(getID(), pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(getID(),
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2);
        }
        glfwMakeContextCurrent(NULL);
        glfwMakeContextCurrent(getID());
        GL.createCapabilities();
        glfwSwapInterval(1);
        showWindow();
    }

    public void hideWindow() {
        glfwHideWindow(getID());
    }

    public void showWindow() {
        glfwShowWindow(getID());
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.0f, 0.3f, 0.8f, 0.0f);
        glfwSwapBuffers(getID());
    }

    private synchronized void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    private synchronized void setID(long ID) {
        this.ID = ID;
    }

    public synchronized long getID() {
        return ID;
    }

    public synchronized double getWidth() {
        return width;
    }

    public synchronized double getHeight() {
        return height;
    }

    public void cleanUp() {

    }

}
