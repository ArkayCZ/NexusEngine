#version 330

in vec2 textureCoord;

uniform sampler2D texture;
uniform vec3 color = vec3(1.0);

void main() {
    gl_FragColor = texture2D(texture, textureCoord.xy) * vec4(color, 1.0);
}
