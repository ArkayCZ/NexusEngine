#version 330

in vec2 v_texture_coordinate;
in vec3 v_normal_vector;
in vec3 v_world_position;

uniform vec3 camera_position;

uniform float specular_exponent;
uniform float specular_intensity;

uniform vec3 light_attenuation;
uniform vec3 light_color;
uniform vec3 light_position;
uniform float light_range;
uniform float light_intensity;
uniform vec3 light_direction;
uniform float light_length;

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

vec4 calculate_point_light(vec3 color, float intensity, vec3 attenuation, vec3 position, float range, vec3 normal) {
    vec3 direction = v_world_position - position;
    float distance = length(direction);

    if(distance > range)
        return vec4(0, 0, 0, 0);

    direction = normalize(direction);

    vec4 light_c = calculate_light(direction, normal, color, intensity);

    float attenuation_c = attenuation.z + attenuation.y * distance + attenuation.x * distance * distance + 0.0001;

    return light_c / attenuation_c;
}

vec4 calculate_spot_light(vec3 color, float intensity, vec3 attenuation,
    vec3 position, float range, vec3 normal, vec3 direction, float length) {

    vec3 direction_l = normalize(v_world_position - position);

    float treshold = dot(direction_l, direction);

    vec4 spot_color = vec4(0, 0, 0, 0);

    if(treshold > length) {
        spot_color = calculate_point_light(color, intensity, attenuation, position, range, normal);
    }

    return spot_color;
}

void main() {
    gl_FragColor = texture2D(material_texture, v_texture_coordinate) *
        calculate_spot_light(light_color, light_intensity,
        light_attenuation, light_position, light_range, v_normal_vector, light_direction, light_length);
}