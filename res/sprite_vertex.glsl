#version 330

layout (location = 0) in vec4 position;

out vec4 color;

uniform mat4 projectionMatrix;

void main() {
    color = clamp(position, 0.0, 1.0);
    gl_Position = projectionMatrix * position;
}