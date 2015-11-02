#version 330

in vec2 textureCoord;
in vec3 normalVector;
in vec3 worldPosition;

out vec4 fragmentColor;

struct BaseLight {
    vec3 color;
    float intensity;
};

struct DirectionalLight {
    BaseLight baseLight;
    vec3 direction;
};

uniform sampler2D texture;
uniform vec3 ambientLight;
uniform vec3 baseColor;
uniform vec3 cameraPosition;

uniform float specularExponent;
uniform float specularIntensity;

uniform DirectionalLight directionalLight;

vec4 calculateLight(vec3 direction, vec3 normal, BaseLight base) {
    float attenuation = dot(normal, -direction);

    vec4 attenuationColor = vec4(0, 0, 0, 0);
    vec4 specularColor = vec4(0, 0, 0, 0);
    if(attenuation > 0) {
        attenuationColor = vec4(base.color, 1.0) * base.intensity * attenuation;

        vec3 cameraDirection = normalize(cameraPosition - worldPosition);
        vec3 reflectionDirection = normalize(reflect(direction, normal));

        float specularComparationResult = dot(cameraDirection, reflectionDirection);
        specularComparationResult = pow(specularComparationResult, specularExponent);

        if(specularComparationResult > 0)
            specularColor = vec4(base.color, 1.0) * specularIntensity * specularComparationResult;
    }

    return attenuationColor + specularColor;
}

vec4 calculateDirectionalLight(DirectionalLight dirLight, vec3 normal) {
    return calculateLight(-dirLight.direction, normal, dirLight.baseLight);
}

void main() {
    vec4 totalLight = vec4(ambientLight, 1);
    vec4 textureColor = texture2D(texture, textureCoord.xy);

    vec4 color = vec4(baseColor, 1);

    if(textureColor != vec4(0, 0, 0, 0))
        color *= textureColor;

    vec3 normal = normalize(normalVector);

    totalLight += calculateDirectionalLight(directionalLight, normal);

    fragmentColor = color * totalLight;
}
