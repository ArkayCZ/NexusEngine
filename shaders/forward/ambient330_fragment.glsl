#version 330

in vec2 tex_coord;

out vec4 frag_color;

uniform vec3 ambient;
uniform sampler2D material_texture;

void main() {
    frag_color = texture2D(material_texture, tex_coord.xy) * vec4(ambient, 1.0);
}
