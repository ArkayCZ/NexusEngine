#version 120

varying vec2 texCoord0;

uniform vec3 color;
uniform sampler2D sampler;

void main()
{
	gl_FragColor = texture2D(sampler, texCoord0.xy) * vec4(color, 1);
	if(gl_FragColor.w < 0.8)
	    discard;
}
