package engine.graphics.font;

import engine.utils.Log;
import engine.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vesel on 30.01.2016.
 */
public class FontSpecification {

    private String mFilePath;
    private List<String> mLines;

    public static final int ID_INDEX = 1;
    public static final int X_INDEX = 2;
    public static final int Y_INDEX = 3;
    public static final int WIDTH_INDEX = 4;
    public static final int HEIGHT_INDEX = 5;
    public static final int X_OFFSET_INDEX = 6;
    public static final int Y_OFFSET_INDEX = 7;
    public static final int ADVANCE_INDEX = 8;

    private HashMap<Character, Integer> mXMap;
    private HashMap<Character, Integer> mYMap;
    private HashMap<Character, Integer> mWidthMap;
    private HashMap<Character, Integer> mHeightMap;
    private HashMap<Character, Integer> mXOffsetMap;
    private HashMap<Character, Integer> mYOffsetMap;
    private HashMap<Character, Integer> mAdvanceMap;

    public FontSpecification(String path) {
        this(new ArrayList<>());
        mFilePath = path;
    }

    public FontSpecification(List<String> lines) {
        mLines = lines;

        mXMap = new HashMap<>();
        mYMap = new HashMap<>();
        mWidthMap = new HashMap<>();
        mHeightMap = new HashMap<>();
        mXOffsetMap = new HashMap<>();
        mYOffsetMap = new HashMap<>();
        mAdvanceMap = new HashMap<>();

        parseFile(getLines());
    }

    private void parseFile(List<String> lines) {
        for(String line : lines) {
            /* Splits the data and removed empty strings */
            String[] data = line.split(" ");
            data = Utils.removeEmptyStrings(data);

            /* Skip line if it's not a char record */
            if (!data[0].equals("char")) continue;
            /* Get the id of the character */
            int id = getValue(data[ID_INDEX]);

            /* Save all of the data in the maps */
            mXMap.put((char) id, getValue(data[X_INDEX]));
            mYMap.put((char) id, getValue(data[Y_INDEX]));
            mWidthMap.put((char) id, getValue(data[WIDTH_INDEX]));
            mHeightMap.put((char) id, getValue(data[HEIGHT_INDEX]));
            mXOffsetMap.put((char) id, getValue(data[X_OFFSET_INDEX]));
            mYOffsetMap.put((char) id, getValue(data[Y_OFFSET_INDEX]));
            mAdvanceMap.put((char) id, getValue(data[ADVANCE_INDEX]));
        }

        Log.i("Font '" + mFilePath + "' has been parsed successfully");
    }

    /**
     * Helper function, parses the integer behind the equiv sign.
     * @param data String to be parsed.
     * @return Integer value behind the equiv sign.
     */
    private int getValue(String data) {
        return Integer.parseInt(data.split("=")[1]);
    }

    /**
     * Retruns the entire parameter line for a character.
     * @param c The character bound to the line.
     * @return
     */
    public String getCharLine(char c) {
        int characterNumber = (int)c;

        for (String line: mLines) {
            if(line.contains("char id=" + characterNumber))
                return line;
        }

        Log.e("Failed to find character '" + characterNumber + "' in the selected font-spec.");
        return null;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public List<String> getLines() {
        return mLines;
    }

    public void setLines(List<String> lines) {
        mLines = lines;
    }

    public void setFilePath(String path) {
        mFilePath = path;
    }

    public int getX(char c) {
        return mXMap.get(c);
    }

    public int getY(char c) {
        return mYMap.get(c);
    }

    public int getHeight(char c) {
        return mHeightMap.get(c);
    }

    public int getWidth(char c) {
        return mWidthMap.get(c);
    }

    public int getAdvance(char c) {
        return mAdvanceMap.get(c);
    }

    public int getXOffset(char c) {
        return mXOffsetMap.get(c);
    }

    public int getYOffset(char c) {
        return mYOffsetMap.get(c);
    }

}
