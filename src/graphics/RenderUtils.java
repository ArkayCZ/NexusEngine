package graphics;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by vesel on 01.11.2015.
 */
public class RenderUtils {

    private RenderUtils() {}

    public static void enableTextures(boolean enabled) {
        if(enabled)
            glEnable(GL_TEXTURE_2D);
        else glDisable(GL_TEXTURE_2D);
    }

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

}
