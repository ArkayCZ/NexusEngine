#version 120

attribute vec3 position;
attribute vec2 texture_coordinate;

varying vec2 v_texture_coord;

uniform mat4 transformation_matrix;

void main() {
    gl_Position = transformation_matrix * vec4(position, 1);
    v_texture_coord = texture_coordinate;
}
