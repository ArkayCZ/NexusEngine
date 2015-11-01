#version 330

out vec4 fragmentColor;

in vec4 color;

void main() {
    fragmentColor = color * cos(color.x);
}