package engine.graphics.window; /**
 * Created by vesel on 30.10.2015.
 */

import engine.graphics.MatrixTransformation;
import engine.input.Input;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL;
import engine.utils.Log;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL32.*;

public class Window {

    private long mID;
    private int mWidth, mHeight;

    public static int WIDTH, HEIGHT;

    private boolean mShown = false;

    private GLFWWindowFocusCallback mFocusCallback;
    private Input mInput;

    public Window(int width, int height, final String title) {
        mInput = new Input();
        mWidth = width;
        mHeight = height;

        WIDTH = width;
        HEIGHT = height;

        if(glfwInit() != GL_TRUE)
            System.err.println("Failed to init GLFW!");

        mID = glfwCreateWindow(width, height, title, NULL, NULL);
        if(mID == NULL)
            System.err.println("Failed to create engine.graphics.window.Window!");

        glfwMakeContextCurrent(mID);

        mInput.setCursorPos(mID, Window.WIDTH / 2, Window.HEIGHT / 2);

        glfwSetCursorPosCallback(mID, mInput.getCursorPosCallback());
        glfwSetMouseButtonCallback(mID, mInput.getMouseButtonCallback());
        glfwSetKeyCallback(mID, mInput.getKeyCallback());
    }

    public void setResizable(boolean value) {
        if(mShown) return;
        glfwWindowHint(GLFW_RESIZABLE, value ? GL_TRUE : GL_FALSE);
    }

    /**
     * Shows the window. Must be called after setting size and changing size is not possible after showing the window.
     * @param showCursor Wheter the cursor should be visible in the window.
     */
    public void show(boolean showCursor) {
        mShown = true;
        glfwMakeContextCurrent(mID);
        if(!showCursor)
            glfwSetInputMode(mID, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        glfwShowWindow(mID);
    }

    public void centerWindow() {
        ByteBuffer displayMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int widthMargin = (GLFWvidmode.width(displayMode) - mWidth) / 2;
        int heightMargin = (GLFWvidmode.height(displayMode) - mHeight) / 2;

        setPosition(widthMargin, heightMargin);
    }

    public void initGL() {
        GL.createCapabilities();

        Log.i(glGetString(GL_VERSION));

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        //glEnable(GL_FRAMEBUFFER_SRGB);
    }

    public void initGL(float viewportWidth, float viewportHeight) {
        GL.createCapabilities();


        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        //glOrtho(viewportWidth / -2.0f, viewportWidth / 2.0f, viewportHeight / -2.0f, viewportHeight / 2.0f, -1.0f, 1.0f);
        glOrtho(0.0f, viewportWidth, 0.0f, viewportHeight, -1.0f, 1.0f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
    }

    public void initProjection(float fov, float near, float far) {
        MatrixTransformation.setProjection(fov, mWidth, mHeight, near, far);
    }

    public void update() {
        glfwSwapBuffers(mID);
        glfwPollEvents();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(mID) == GL_TRUE;
    }

    public void setPosition(int x, int y) {
        glfwSetWindowPos(mID, x, y);
    }

    public void setClearColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    public Input getInputStatus() {
        return mInput;
    }

    public void destroy() {
        glfwDestroyWindow(mID);
        glfwTerminate();
    }

}
