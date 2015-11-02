package engine.input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.HashMap;

/**
 * Created by vesel on 30.10.2015.
 */
public class Input {

    public static final int KEY_A = GLFW_KEY_A;
    public static final int KEY_B = GLFW_KEY_B;
    public static final int KEY_C = GLFW_KEY_C;
    public static final int KEY_D = GLFW_KEY_D;
    public static final int KEY_E = GLFW_KEY_E;
    public static final int KEY_F = GLFW_KEY_F;
    public static final int KEY_G = GLFW_KEY_G;
    public static final int KEY_H = GLFW_KEY_H;
    public static final int KEY_I = GLFW_KEY_I;
    public static final int KEY_J = GLFW_KEY_J;
    public static final int KEY_K = GLFW_KEY_K;
    public static final int KEY_L = GLFW_KEY_L;
    public static final int KEY_M = GLFW_KEY_M;
    public static final int KEY_N = GLFW_KEY_N;
    public static final int KEY_O = GLFW_KEY_O;
    public static final int KEY_P = GLFW_KEY_P;
    public static final int KEY_Q = GLFW_KEY_Q;
    public static final int KEY_R = GLFW_KEY_R;
    public static final int KEY_S = GLFW_KEY_S;
    public static final int KEY_T = GLFW_KEY_T;
    public static final int KEY_U = GLFW_KEY_U;
    public static final int KEY_V = GLFW_KEY_V;
    public static final int KEY_W = GLFW_KEY_W;
    public static final int KEY_X = GLFW_KEY_X;
    public static final int KEY_Y = GLFW_KEY_Y;
    public static final int KEY_Z = GLFW_KEY_Z;
    public static final int KEY_UP = GLFW_KEY_UP;
    public static final int KEY_DOWN = GLFW_KEY_DOWN;
    public static final int KEY_LEFT = GLFW_KEY_LEFT;
    public static final int KEY_RIGHT = GLFW_KEY_RIGHT;
    public static final int KEY_ESC = GLFW_KEY_ESCAPE;

    private HashMap<Integer, Boolean> mKeys;
    private boolean mRightMouseButton, mLeftMouseButton;

    private int mMouseX, mMouseY;
    private int mMouseLastX, mMouseLastY;
    private int mMouseDeltaX, mMouseDeltaY;

    private GLFWCursorPosCallback mCursorPosCallback;
    private GLFWKeyCallback mKeyCallback;
    private GLFWMouseButtonCallback mMouseButtonCallback;

    public Input() {
        mKeys = new HashMap<>();

        mCursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mMouseLastX = mMouseX;
                mMouseLastY = mMouseY;

                mMouseX = (int)xpos;
                mMouseY = (int)ypos;

                mMouseDeltaX = mMouseLastX - mMouseX;
                mMouseDeltaY = mMouseLastY - mMouseY;
            }
        };

        mKeyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                mKeys.put(key, action != GLFW_RELEASE);
            }
        };

        mMouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if(button == GLFW_MOUSE_BUTTON_1)
                    mLeftMouseButton = action != GLFW_RELEASE;
                if(button == GLFW_MOUSE_BUTTON_2)
                    mRightMouseButton = action != GLFW_RELEASE;
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

    public int getMouseDeltaX() {
        return mMouseDeltaX;
    }

    public void setMouseDeltaX(int mouseDeltaX) {
        mMouseDeltaX = mouseDeltaX;
    }

    public int getMouseDeltaY() {
        return mMouseDeltaY;
    }

    public void setMouseDeltaY(int mouseDeltaY) {
        mMouseDeltaY = mouseDeltaY;
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
