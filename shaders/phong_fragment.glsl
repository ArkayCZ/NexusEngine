#version 330

const int MAX_POINT_LIGHTS = 4;
const int MAX_SPOT_LIGHTS = 4;

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

struct Attenuation {
    float absolute;
    float linear;
    float quadratical;
};

struct PointLight {
    BaseLight baseLight;
    Attenuation attenuation;
    vec3 position;
    float range;
};

struct SpotLight {
    PointLight pointLight;
    vec3 direction;
    float length;
};

uniform sampler2D texture;
uniform vec3 ambientLight;
uniform vec3 baseColor;
uniform vec3 cameraPosition;

uniform float specularExponent;
uniform float specularIntensity;

uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];

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

vec4 calculatePointLight(PointLight light, vec3 normal) {
    vec3 lightDirection = worldPosition - light.position;
    float distance = length(lightDirection);

    if(distance > light.range)
        return vec4(0, 0, 0, 0);

    lightDirection = normalize(lightDirection);

    vec4 resultColor = calculateLight(lightDirection, normal, light.baseLight);

    float attenuation = light.attenuation.absolute + light.attenuation.linear * distance +
                        light.attenuation.quadratical * distance * distance;

    if(attenuation == 0)
        attenuation += 0.0001;

    return resultColor / attenuation;
}

vec4 calculateSpotLight(SpotLight light, vec3 normal) {
    vec3 direction = normalize(worldPosition - light.pointLight.position);

    float lightCoefficient = dot(direction, light.direction);

    vec4 spotColor = vec4(0, 0, 0, 0);

    if(lightCoefficient > light.length) {
        spotColor = calculatePointLight(light.pointLight, normal) * (1.0 - (1.0 - lightCoefficient) / (1.0 - light.length));
    }

    return spotColor;
}

void main() {
    vec4 totalLight = vec4(ambientLight, 1);
    vec4 textureColor = texture2D(texture, textureCoord.xy);

    vec4 color = vec4(baseColor, 1);

    if(textureColor != vec4(0, 0, 0, 0))
        color *= textureColor;

    vec3 normal = normalize(normalVector);

    totalLight += calculateDirectionalLight(directionalLight, normal);

    for(int i = 0; i < MAX_POINT_LIGHTS; i++) {
        if(pointLights[i].baseLight.intensity > 0)
            totalLight += calculatePointLight(pointLights[i], normal);
    }

    for(int i = 0; i < MAX_POINT_LIGHTS; i++) {
            if(spotLights[i].pointLight.baseLight.intensity > 0)
                totalLight += calculateSpotLight(spotLights[i], normal);
    }

    fragmentColor = color * totalLight;
}
