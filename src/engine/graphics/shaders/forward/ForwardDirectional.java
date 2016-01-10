package engine.graphics.shaders.forward;

import engine.graphics.shaders.Shader;
import engine.utils.ContentLoader;

/**
 * Created by vesel on 10.01.2016.
 */
public class ForwardDirectional extends Shader {

    public ForwardDirectional() {
        super();

        this.attachProgram(ContentLoader.readFileAsString("shaders/forward/directional_fragment.glsl"), Shader.FRAG);
        this.attachProgram(ContentLoader.readFileAsString("shaders/forward/directional_vertex.glsl"), Shader.VERT);
        this.compile();
    }

}
