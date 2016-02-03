#version 120

attribute vec3 position;
attribute vec2 texture_coordinate;

varying vec2 v_texture_coord;

uniform mat4 projection_matrix;

void main()
{
    v_texture_coord = texture_coordinate;
    gl_Position = projection_matrix * vec4(position, 1.0);
}