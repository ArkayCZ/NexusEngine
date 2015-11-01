#version 330

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 texCoord;

out vec2 textureCoord;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;

void main() {
    textureCoord = texCoord;
    gl_Position = projectionMatrix * position;
}