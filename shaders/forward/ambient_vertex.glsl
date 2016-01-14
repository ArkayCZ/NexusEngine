#version 120

attribute vec3 position;
attribute vec2 texture_coordinate;

varying vec2 tex_coord;

uniform mat4 world_matrix;

void main() {
    gl_Position = world_matrix * vec4(position, 1.0);
    tex_coord = texture_coordinate;
}
