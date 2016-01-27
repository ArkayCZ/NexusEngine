#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture_coordinate;

out vec2 tex_coord;

uniform mat4 world_matrix;

void main() {
    gl_Position = world_matrix * vec4(position, 1.0);
    tex_coord = texture_coordinate;
}
