#version 120

uniform vec3 camera_position;

uniform float specular_exponent;
uniform float specular_intensity;

uniform vec3 light_color;
uniform float light_intensity;
uniform vec3 light_direction;

void main() {

}

vec4 calculate_light(vec3 direction, vec3 normal, BaseLight light) {
    float attenuation = dot(normal, -direction);

    vec4 attenuation_color = vec4(0, 0, 0, 0);
    vec4 specular_color = vec4(0, 0, 0, 0);
    if(attenuation > 0) {
        attenuation_color = vec4(base.color, 1.0) * base.intensity * attenuation;

        vec3 camera_direction = normalize(camera_positon - )
    }
}

vec4 calculate_directional_light() {

}


