package engine.graphics;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by vesel on 01.11.2015.
 */
public class Texture {

    private int mID;
    private int mWidth, mHeight;

    public Texture(int id, int width, int height) {
        mWidth = width;
        mHeight = height;
        mID = id;
    }

    public Texture(int id) {
        mID = id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, mID);
    }

    public static void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getID() {
        return mID;
    }
}
