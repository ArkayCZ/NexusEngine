#version 120

precision highp float;

attribute vec3 position;
attribute vec2 texture_coordinate;
attribute vec3 normal;

varying vec2 v_texture_coordinate;
varying vec3 v_normal_vector;
varying vec3 v_world_position;

uniform mat4 world_matrix;
uniform mat4 transformation_matrix;

void main() {
    v_texture_coordinate = texture_coordinate;
    v_normal_vector = (transformation_matrix * vec4(normal, 0.0)).xyz;
    v_world_position = (transformation_matrix * vec4(position, 1.0)).xyz;
    gl_Position = world_matrix * vec4(position, 1.0);
}
