package graphics.deperecated;

import static org.lwjgl.opengl.GL11.*;
/**
 * Created by vesel on 30.10.2015.
 */
public class Transformation {

    private float mTranslationX, mTranslationY, mTranslationZ;
    private float mRotation;

    public void apply() {
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
        glRotatef(mRotation, 0, 0, 1);
        glTranslatef(mTranslationX, mTranslationY, mTranslationZ);
    }

    public void discard() {
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
    }

    public float getTranslationX() {
        return mTranslationX;
    }

    public void setTranslationX(float translationX) {
        this.mTranslationX = translationX;
    }

    public float getTranslationY() {
        return mTranslationY;
    }

    public void setTranslationY(float translationY) {
        this.mTranslationY = translationY;
    }

    public float getTranslationZ() {
        return mTranslationZ;
    }

    public void setTranslationZ(float translationZ) {
        this.mTranslationZ = translationZ;
    }

    public float getRotation() {
        return mRotation;
    }

    public void setRotation(float rotation) {
        this.mRotation = rotation;
    }
}
