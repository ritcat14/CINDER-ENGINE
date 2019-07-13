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

    public Window(double width, double height) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        ID = glfwCreateWindow((int)width, (int)height, "CINDER-ENGINE", NULL, NULL);
        if (ID == NULL) throw new RuntimeException("Failed to create GLFW window");

        // Key set up
        glfwSetKeyCallback(ID, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
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
        updateThread();
        nullThread();
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(ID, title);
    }

    public void nullThread() {
        glfwMakeContextCurrent(NULL);
    }

    public void updateThread() {
        glfwMakeContextCurrent(NULL);
        glfwMakeContextCurrent(ID);
        System.out.println(glfwGetCurrentContext());
        GL.createCapabilities();
        glfwSwapInterval(1);
        showWindow();
    }

    public void hideWindow() {
        glfwHideWindow(ID);
    }

    public void showWindow() {
        glfwShowWindow(ID);
    }

    public void render() {
        updateThread();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.0f, 0.3f, 0.8f, 0.0f);
        glfwSwapBuffers(ID);
        glfwPollEvents();
        nullThread();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(ID);
    }
}
