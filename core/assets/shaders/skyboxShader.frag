
#ifdef GL_ES
precision mediump float;
#endif

uniform float u_usesDiffuseTexture;

uniform sampler2D u_diffuseTexture;
varying vec2 v_uv;


void main()
{
    if(u_usesDiffuseTexture == 1.0f)
	    gl_FragColor = texture2D(u_diffuseTexture, v_uv);
	else
	    gl_FragColor = vec4(1,0,0,1);
}