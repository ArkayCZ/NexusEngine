package engine.utils;

import static org.lwjgl.opengl.GL11.*;

import engine.graphics.Bitmap;
import engine.graphics.Mesh;
import engine.graphics.Texture;
import engine.graphics.Vertex;
import engine.graphics.font.FontSheet;
import engine.graphics.font.FontSpecification;
import engine.math.Vector3;
import engine.utils.meshes.IndexedModel;
import engine.utils.meshes.ModelOBJ;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vesel on 30.10.2015.
 * Provides static methods for loading different forms of content into the engine.
 */
public class ContentLoader
{

    /**
     * Loads a string using BufferedReader.
     *
     * @param path Path to the file to read.
     * @return String containing contents of the file
     */
    public static String loadString(String path)
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e)
        {
            Log.e("Failed to load String resource!");
        }

        return builder.toString();
    }

    /**
     * Loads an OBJ mesh without texture coords and normals.
     *
     * @param path Path to the OBJ.
     * @return Mesh representing the model.
     */
    public static Mesh loadMesh(String path)
    {
        int lineNumber = 0;
        if (!path.split("\\.")[path.split("\\.").length - 1].equals("obj"))
        {
            Log.e("Failed to load Mesh! " + path);
            Log.e("Check the files extension and internal structure!");
            return null;
        }
        else
        {
            ArrayList<Vertex> vertices = new ArrayList<>();
            ArrayList<Integer> indices = new ArrayList<>();
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    String[] data = line.split(" ");
                    ArrayList<String> checkedData = new ArrayList<>();
                    //noinspection ForLoopReplaceableByForEach
                    for (int i = 0; i < data.length; i++)
                    {
                        if (!data[i].equals(" ") || !data[i].equals(""))
                        {
                            checkedData.add(data[i]);
                        }
                    }
                    data = new String[checkedData.size()];
                    checkedData.toArray(data);

                    if (data.length == 0 || data[0].equals("#"))
                    {
                        continue;
                    }
                    if (data[0].equals("v"))
                    {
                        vertices.add(new Vertex(new Vector3(Float.parseFloat(data[1]),
                                Float.parseFloat(data[2]), Float.parseFloat(data[3]))));
                    }
                    if (data[0].equals("f"))
                    {
                        indices.add(Integer.parseInt(data[1].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(data[2].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(data[3].split("/")[0]) - 1);

                        if (data.length > 4)
                        {
                            indices.add(Integer.parseInt(data[1].split("/")[0]) - 1);
                            indices.add(Integer.parseInt(data[3].split("/")[0]) - 1);
                            indices.add(Integer.parseInt(data[4].split("/")[0]) - 1);
                        }
                    }
                    lineNumber++;
                }
                reader.close();
            } catch (Exception e)
            {
                Log.e("Error while loading mesh at line " + lineNumber);
                Log.e(e.getMessage());
            }

            Vertex[] vertexArray = new Vertex[vertices.size()];
            vertices.toArray(vertexArray);
            Integer[] integers = new Integer[indices.size()];
            indices.toArray(integers);
            int[] indexArray = Utils.createIntArray(integers);

            Mesh mesh = new Mesh();
            mesh.addVertices(vertexArray, indexArray, true);

            return mesh;
        }
    }

    /**
     * Loads an OBJ model using IndexedModel. Will replace the regular LoadMesh method.
     *
     * @param path Path to the OBJ model
     * @return Mesh representing the OBJ model.
     */
    public static Mesh loadMeshIndexed(String path)
    {
        ModelOBJ model = new ModelOBJ(path);
        IndexedModel indexed = model.createIndexedModel();

        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < indexed.getPositions().size(); i++)
        {
            vertices.add(new Vertex(indexed.getPositions().get(i),
                    indexed.getTextureCoordinates().get(i), indexed.getNormals().get(i)));
        }

        Vertex[] vertexArray = new Vertex[vertices.size()];
        vertices.toArray(vertexArray);

        Integer[] indexArray = new Integer[indexed.getIndices().size()];
        indexed.getIndices().toArray(indexArray);

        Mesh mesh = new Mesh();
        if (!indexed.containsNormals())
        {
            mesh.addVertices(vertexArray, Utils.createIntArray(indexArray), true);
        }
        else
        {
            mesh.addVertices(vertexArray, Utils.createIntArray(indexArray), false);
        }

        return mesh;
    }

    /**
     * Creates a texture object by loading in the pixels of the image using ImageIO and bitshifting. Then sends the texture
     * to OpenGL and gets its ID upon which it creates a new Texture object.
     *
     * @param path Path to the image to be mapped and sent to the GPU.
     * @return Texture object containing information about the texture.
     */
    public static Texture loadTexture(String path)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(path));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        if (image == null)
        {
            Log.e("Failed to load texture '" + path + "'!");
            return new Texture(-1, 0, 0);
        }

        int width = image.getWidth();
        int height = image.getHeight();

        int[] originalPixels = new int[width * height];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), originalPixels, 0, image.getWidth());

        int[] pixels = new int[width * height];
        for (int i = 0; i < pixels.length; i++)
        {
            int a = (originalPixels[i] & 0xFF000000) >> 24;
            int r = (originalPixels[i] & 0xFF0000) >> 16;
            int g = (originalPixels[i] & 0xFF00) >> 8;
            int b = (originalPixels[i] & 0xFF);

            pixels[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, Utils.createIntBuffer(pixels));
        Texture.unbind();

        return new Texture(textureID, width, height);
    }

    /**
     * Loads a bitmap.
     *
     * @param path Path to the bitmap file.
     * @return Bitmap with the contents of the file.
     */
    public static Bitmap loadBitmap(String path)
    {
        int width = 0, height = 0;
        int[] pixels = new int[0];

        try
        {
            BufferedImage image = ImageIO.read(new File(path));

            width = image.getWidth();
            height = image.getHeight();

            pixels = new int[width * height];

            image.getRGB(0, 0, width, height, pixels, 0, width);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return new Bitmap(width, height, pixels);
    }

    public static FontSheet loadFontSheet(String path)
    {
        /* Already contains a file loading constructor, this has to eventually be moved to this class. */
        FontSheet sheet = new FontSheet(ContentLoader.loadTexture(path));
        return sheet;
    }

    public static FontSpecification loadFontSpecification(String path)
    {
        List<String> lines = new ArrayList<>();

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            String line;
            while ((line = reader.readLine()) != null)
            {
                lines.add(line);
            }

        } catch (IOException e)
        {
            Log.e("Failed to load font file '" + path + "'!");
        }

        FontSpecification spec = new FontSpecification(lines);

        return spec;
    }

}
