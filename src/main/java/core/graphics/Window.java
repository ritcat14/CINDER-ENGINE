package core.graphics;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/*
 *  Main window class
 */

public class Window {

    private long ID;

    public Window(double width, double height) {
        GLFWErrorCallback.createThrow().set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_RELEASE_BEHAVIOR, GLFW_RELEASE_BEHAVIOR_FLUSH);

        ID = glfwCreateWindow((int) width, (int) height, "", NULL, NULL);
        if (ID == NULL) throw new RuntimeException("Failed to create GLFW window");

        // Key set up
        glfwSetKeyCallback(ID, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(ID, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(ID,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2);
        }
        setThread();
        glfwSwapInterval(1);
        showWindow();
        nullThread();
    }

    public synchronized void setThread() {
        glfwMakeContextCurrent(getID());
        GL.createCapabilities();
    }

    public synchronized void nullThread() {
        glfwMakeContextCurrent(NULL);
    }

    public void hideWindow() {
        glfwHideWindow(ID);
    }

    public void showWindow() {
        glfwShowWindow(ID);
    }

    public synchronized void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.0f, 0.3f, 0.8f, 0.0f);
        glfwSwapBuffers(ID);
    }

    public synchronized void update() {
        glfwPollEvents();
    }

    public synchronized boolean shouldClose() {
        return glfwWindowShouldClose(ID);
    }

    public long getID() {
        return ID;
    }


}
