#version 120

varying vec2 v_texture_coord;

uniform sampler2D material_texture;

void main() {
    gl_FragColor = texture2D(material_texture, v_texture_coord);
}