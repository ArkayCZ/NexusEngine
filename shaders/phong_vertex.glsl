#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;

out vec2 textureCoord;
out vec3 normalVector;
out vec3 worldPosition;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;

void main() {
    textureCoord = texCoord;
    worldPosition = (worldMatrix * vec4(position, 1.0)).xyz;
    normalVector = (worldMatrix * vec4(normal, 0.0)).xyz;
    gl_Position = projectionMatrix * vec4(position, 1.0);
}