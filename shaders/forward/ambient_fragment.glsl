#version 120

varying tex_coord;

uniform vec3 ambient;
uniform sampler2D texture;

void main() {
    gl_FragColor = texture2DD(texture, tex_coord.xy) * vec4(ambient, 1.0);
}
