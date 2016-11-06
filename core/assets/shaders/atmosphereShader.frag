
#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_gradientTexture;
varying vec2 v_uv;


void main()
{
    vec2 uv = v_uv;
	gl_FragColor = texture2D(u_gradientTexture, uv);

	// configure the alpha so that it gradients with higher y value on the texture.
	gl_FragColor.a = pow((1 - uv.y), 0.4);
}