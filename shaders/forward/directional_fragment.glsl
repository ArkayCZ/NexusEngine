#version 120

varying vec2 v_texture_coordinate;
varying vec3 v_normal_vector;
varying vec3 v_world_position;

uniform vec3 camera_position;

uniform float specular_exponent;
uniform float specular_intensity;

uniform vec3 light_color;
uniform float light_intensity;
uniform vec3 light_direction;

uniform sampler2D material_texture;

vec4 calculate_light(vec3 direction, vec3 normal, vec3 light_color, float intensity) {
    float attenuation = dot(normal, -direction);

    vec4 attenuation_color = vec4(0, 0, 0, 0);
    vec4 specular_color = vec4(0, 0, 0, 0);
    if(attenuation > 0) {
        attenuation_color = vec4(light_color, 1.0) * intensity * attenuation;

        vec3 camera_direction = normalize(camera_position - v_world_position);
        vec3 reflection_direction = normalize(reflect(direction, normal));

        float specular_comparation = dot(camera_direction, reflection_direction);
        specular_comparation = pow(specular_comparation, specular_exponent);

        if(specular_comparation > 0)
            specular_color = vec4(light_color, 1.0) * specular_intensity * specular_comparation;
    }

    return attenuation_color + specular_color;
}

vec4 calculate_directional_light(vec3 direction, vec3 normal, vec3 color, float intensity) {
    return calculate_light(-direction, normal, color, intensity);
}


void main() {
    gl_FragColor = texture2D(material_texture, v_texture_coordinate) * calculate_directional_light(light_direction, v_normal_vector, light_color, light_intensity);
}
