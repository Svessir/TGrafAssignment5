
#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_gradientTexture;

uniform vec4 u_cameraLightDirection;

varying vec4 v_s;
varying vec4 v_h;
varying vec2 v_uv;


void main()
{
    vec2 uv = v_uv;
	gl_FragColor = texture2D(u_gradientTexture, uv);
	gl_FragColor.a = pow((1 - uv.y), 0.4) + clamp((1 - length(gl_FragColor)),0, 1);
}