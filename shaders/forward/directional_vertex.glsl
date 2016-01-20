#version 120

attribute vec3 position;
attribute vec2 texture_coordinate;
attribute vec3 normal;

varying vec3 normal_vector;
varying vec3 world_position;

uniform mat4 world_matrix;
uniform mat4 transformation_matrix;

void main() {
    world_position = (world_matrix * vec4(position, 1.0)).xyz;
    normal_vector = (world_matrix * vec4(normal, 0.0)).xyz;
    gl_Position = modelview_projected * vec4(position, 1.0);
}
