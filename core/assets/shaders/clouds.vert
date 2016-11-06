
#ifdef GL_ES
precision mediump float;
#endif

attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_uv;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform vec4 u_cameraPosition;
uniform vec4 u_lightPosition;
uniform vec4 u_cameraLightPosition;

varying vec4 v_s;
varying vec4 v_h;
varying vec4 v_normal;
varying vec4 v_sCam;
varying vec2 v_uv;

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = vec4(a_normal.x, a_normal.y, a_normal.z, 0.0);
	normal = u_modelMatrix * normal;

    vec4 v = normalize(u_cameraPosition - position);
    v_s = normalize(u_lightPosition - position);
    v_h = normalize(v_s + v);
    v_uv = a_uv;

    v_sCam = normalize(u_cameraLightPosition - position);

	position = u_viewMatrix * position;
	//normal = u_viewMatrix * normal;
    v_normal = normal;

	gl_Position = u_projectionMatrix * position;
}