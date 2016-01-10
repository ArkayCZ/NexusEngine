#version 120

attribute vec3 position;
attribute vec2 texture_coordinate;

varying vec2 tex_coord;

uniform mat4 modelview_projected;

void main() {
    tex_coord = texture_coordinate;
    gl_Position = modelview_projected * vec4(position, 1.0);
}
