package graphics;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by vesel on 01.11.2015.
 * The class provides some handy utilities for working with OpenGL graphics.
 */
public class RenderUtils {

    private RenderUtils() {}

    /**
     * Sets the support of GL_TEXTURE_2D on and off.
     * @param enabled
     */
    public static void enableTextures(boolean enabled) {
        if(enabled)
            glEnable(GL_TEXTURE_2D);
        else glDisable(GL_TEXTURE_2D);
    }

    /**
     * Gets OpenGL version.
     * @return Supported OpenGL Version.
     */
    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

}
