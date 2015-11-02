package utils;

import static org.lwjgl.opengl.GL11.*;

import graphics.Mesh;
import graphics.Texture;
import graphics.Vertex;
import math.Vector3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vesel on 30.10.2015.
 */
public class ContentLoader {

    public static String readFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            reader.close();
        } catch(Exception e) {

        }

        return builder.toString();
    }

    public static Mesh loadMesh(String path) {
        int lineNumber = 0;
        if(!path.split("\\.")[path.split("\\.").length - 1].equals("obj")) {
            Log.e("Failed to load Mesh! " + path);
            Log.e("Check the files extension and internal structure!");
            return null;
        } else {
            ArrayList<Vertex> vertices = new ArrayList<>();
            ArrayList<Integer> indices = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                String line;
                while((line = reader.readLine()) != null) {
                    String[] data = line.split(" ");
                    ArrayList<String> checkedData = new ArrayList<>();
                    for(int i = 0; i < data.length; i++) {
                        if(!data[i].equals(" ") || !data[i].equals(""))
                            checkedData.add(data[i]);
                    }
                    data = new String[checkedData.size()];
                    checkedData.toArray(data);

                    if(data.length == 0 || data[0].equals("#")) continue;
                    if(data[0].equals("v"))
                        vertices.add(new Vertex(new Vector3(Float.parseFloat(data[1]),
                                Float.parseFloat(data[2]), Float.parseFloat(data[3]))));
                    if(data[0].equals("f")) {
                        indices.add(Integer.parseInt(data[1].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(data[2].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(data[3].split("/")[0]) - 1);

                        if(data.length > 4) {
                            indices.add(Integer.parseInt(data[1].split("/")[0]) - 1);
                            indices.add(Integer.parseInt(data[3].split("/")[0]) - 1);
                            indices.add(Integer.parseInt(data[4].split("/")[0]) - 1);
                        }
                    }
                    lineNumber++;
                }
                reader.close();
            } catch(Exception e) {
                Log.e("Error while loading mesh at line " + lineNumber);
                Log.e(e.getMessage());
            }

            Vertex[] vertexArray = new Vertex[vertices.size()];
            vertices.toArray(vertexArray);
            Integer[] integers = new Integer[indices.size()];
            indices.toArray(integers);
            int[] indexArray = Utils.createIntArray(integers);

            Mesh mesh = new Mesh();
            mesh.addVertices(vertexArray, indexArray);

            return mesh;
        }
    }

    /**
     * Creates a texture object by loading in the pixels of the image using ImageIO and bitshifting. Then sends the texture
     * to OpenGL and gets its ID upon which it creates a new Texture object.
     * @param path Path to the image to be mapped and sent to the GPU.
     * @return Texture object containing information about the texture.
     */
    public static Texture loadTexture(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(image == null) {
            Log.e("Failed to load texture '" + path + "'!");
            return new Texture(-1, 0, 0);
        }

        int width = image.getWidth();
        int height= image.getHeight();

        int[] originalPixels = new int[width * height];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), originalPixels, 0, image.getWidth());

        int[] pixels = new int[width * height];
        for (int i = 0; i < pixels.length; i++) {
            int a = (originalPixels[i] & 0xFF000000) >> 24;
            int r = (originalPixels[i] & 0xFF0000) >> 16;
            int g = (originalPixels[i] & 0xFF00) >> 8;
            int b = (originalPixels[i] & 0xFF);

            pixels[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, Utils.createIntBuffer(pixels));
        Texture.unbind();

        return new Texture(textureID, width, height);
    }

}
