package engine.graphics;

/**
 * Created by vesel on 02.11.2015.
 */
public class Bitmap {

    private int mWidth;
    private int mHeight;
    private int[] mPixels;

    public Bitmap(int width, int height) {
        this(width, height, new int[width * height]);
    }

    public Bitmap(int width, int height, int[] pixels) {
        mWidth = width;
        mHeight = height;
        mPixels = pixels;
    }

    public int getPixel(int x, int y) {
        int pixelNumber = x + y * getWidth();
        if(pixelNumber < 0 || pixelNumber >= mPixels.length)
            return 0;
        return mPixels[x + y * getWidth()];
    }

    public void setPixels(int x, int y, int color) {
        mPixels[x + y * getWidth()] = color;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int[] getPixels() {
        return mPixels;
    }

    public void setPixels(int[] pixels) {
        mPixels = pixels;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < mWidth; x++) {
            for (int y = 0; y < mHeight; y++) {
                builder.append(getPixel(x, y) + ";");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public Bitmap flipX() {
        int[] temp = new int[getPixels().length];
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                temp[x + y * getWidth()] = mPixels[(getWidth() - x - 1) + y * getWidth()];
            }
        }

        mPixels = temp;
        return this;
    }
}
