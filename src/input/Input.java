package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.HashMap;

/**
 * Created by vesel on 30.10.2015.
 */
public class Input {

    private HashMap<Integer, Boolean> mKeys;
    private boolean mRightMouseButton, mLeftMouseButton;

    private int mMouseX, mMouseY;

    private GLFWCursorPosCallback mCursorPosCallback;
    private GLFWKeyCallback mKeyCallback;
    private GLFWMouseButtonCallback mMouseButtonCallback;

    public Input() {
        mKeys = new HashMap<>();

        mCursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mMouseX = (int)xpos;
                mMouseY = (int)ypos;
            }
        };

        mKeyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                mKeys.put(key, action != GLFW.GLFW_RELEASE);
            }
        };

        mMouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if(button == GLFW.GLFW_MOUSE_BUTTON_1)
                    mLeftMouseButton = action != GLFW.GLFW_RELEASE;
                if(button == GLFW.GLFW_MOUSE_BUTTON_2)
                    mRightMouseButton = action != GLFW.GLFW_RELEASE;
            }
        };
    }

    public boolean isKeyDown(int key) {
        if(mKeys.containsKey(key))
            return mKeys.get(key);
        else return false;
    }

    public int getMouseY() {
        return mMouseY;
    }

    public int getMouseX() {
        return mMouseX;
    }

    public boolean isLeftMouseButton() {
        return mLeftMouseButton;
    }

    public boolean isRightMouseButton() {
        return mRightMouseButton;
    }

    public GLFWCursorPosCallback getCursorPosCallback() {
        return mCursorPosCallback;
    }

    public GLFWKeyCallback getKeyCallback() {
        return mKeyCallback;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mMouseButtonCallback;
    }
}
