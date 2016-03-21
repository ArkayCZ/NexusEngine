package engine.graphics.window;
/**
 * Created by vesel on 30.10.2015.
 * Represents a GLFW window within the engine.
 */

import engine.graphics.Transform;
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

public class Window
{

    private long mID;
    private int mWidth, mHeight;

    public static int WIDTH, HEIGHT;

    private boolean mShown = false;

    private Input mInput;

    /**
     * Creates a new window according to the parameters.
     *
     * @param width  Width of the window.
     * @param height Height of the window.
     * @param title  Title of the window.
     */
    public Window(int width, int height, final String title)
    {
        mInput = new Input(this);
        mWidth = width;
        mHeight = height;

        WIDTH = width;
        HEIGHT = height;

        if (glfwInit() != GL_TRUE)
        {
            System.err.println("Failed to onInit GLFW!");
        }

        mID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (mID == NULL)
        {
            Log.e("Failed to create engine.graphics.window.Window!");
            System.exit(0);
        }

        glfwMakeContextCurrent(mID);

        mInput.setCursorPos(mID, Window.WIDTH / 2, Window.HEIGHT / 2);

        glfwSetCursorPosCallback(mID, mInput.getCursorPosCallback());
        glfwSetMouseButtonCallback(mID, mInput.getMouseButtonCallback());
        glfwSetKeyCallback(mID, mInput.getKeyCallback());
        glfwSwapInterval(0);
    }

    public void setResizable(boolean value)
    {
        if (mShown)
        {
            return;
        }
        glfwWindowHint(GLFW_RESIZABLE, value ? GL_TRUE : GL_FALSE);
    }

    /**
     * Shows the window. Must be called after setting size and changing size is not possible after showing the window.
     *
     * @param showCursor Wheter the cursor should be visible in the window.
     */
    public void show(boolean showCursor)
    {
        mShown = true;
        mInput.init();
        glfwMakeContextCurrent(mID);
        if (!showCursor)
        {
            glfwSetInputMode(mID, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        }
        glfwShowWindow(mID);
    }

    /**
     * Positions the window in the center of the screen.
     */
    public void centerWindow()
    {
        ByteBuffer displayMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int widthMargin = (GLFWvidmode.width(displayMode) - mWidth) / 2;
        int heightMargin = (GLFWvidmode.height(displayMode) - mHeight) / 2;

        setPosition(widthMargin, heightMargin);
    }

    /**
     * Initializes OpenGL.
     */
    public void initGL()
    {
        /* Register context withing LWJGL */
        GL.createCapabilities();

        /* Print OpenGL version */
        Log.i(glGetString(GL_VERSION));

        /* Culling */
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        /* Enables depth testing */
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        /* Enables blending */
        glEnable(GL_BLEND);
    }

    /**
     * Polls events and swaps buffers.
     */
    public void update()
    {
        glfwSwapBuffers(mID);
        glfwPollEvents();
    }

    /**
     * Clears the windows color buffer bit and depth buffer bit.
     */
    public void clear()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Returns true if the window should close.
     *
     * @return Boolean value representing whether the window should close.
     */
    public boolean shouldClose()
    {
        return glfwWindowShouldClose(mID) == GL_TRUE;
    }

    /**
     * Sets the position of the window.
     *
     * @param x X position of the window.
     * @param y Y position of the window.
     */
    public void setPosition(int x, int y)
    {
        glfwSetWindowPos(mID, x, y);
    }

    /**
     * Gets the current input status for the window.
     *
     * @return Input object representing the input status of the window.
     */
    public Input getInputStatus()
    {
        return mInput;
    }

    /**
     * Gets the GLFW id of the window.
     *
     * @return ID of the window.
     */
    public long getID()
    {
        return mID;
    }

    public int getWidth()
    {
        return mWidth;
    }

    public int getHeight()
    {
        return mHeight;
    }

    public void destroy()
    {
        glfwDestroyWindow(mID);
        glfwTerminate();
    }

}
