#version 120

varying vec2 tex_coord;

uniform vec3 ambient;
uniform sampler2D material_texture;

void main() {
    gl_FragColor = texture2D(material_texture, tex_coord.xy) * vec4(ambient, 1.0);
}
