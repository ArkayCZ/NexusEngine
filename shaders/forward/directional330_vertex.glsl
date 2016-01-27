#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture_coordinate;
layout(location = 2) in vec3 normal;

out vec2 v_texture_coordinate;
out vec3 v_normal_vector;
out vec3 v_world_position;

uniform mat4 world_matrix;
uniform mat4 transformation_matrix;

void main() {
    v_world_position = (transformation_matrix * vec4(position, 1.0)).xyz;
    v_normal_vector = (transformation_matrix * vec4(normal, 0.0)).xyz;
    v_texture_coordinate = texture_coordinate;
    gl_Position = world_matrix * vec4(position, 1.0);
}
