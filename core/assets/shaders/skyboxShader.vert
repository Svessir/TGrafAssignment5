
#ifdef GL_ES
precision mediump float;
#endif

attribute vec3 a_position;
attribute vec2 a_uv;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

varying vec2 v_uv;

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;
    position = u_viewMatrix * position;
    v_uv = a_uv;
	gl_Position = u_projectionMatrix * position;
}