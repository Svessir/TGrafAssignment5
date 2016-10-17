
#ifdef GL_ES
precision mediump float;
#endif

attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_uv;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform float u_usesDiffuseTexture;
uniform float u_usesAlphaTexture;
uniform float u_usesBumpTexture;
uniform float u_usesSpecularTexture;

uniform sampler2D u_diffuseTexture;
uniform sampler2D u_alphaTexture;
uniform sampler2D u_bumpTexture;
uniform sampler2D u_specularTexture;

uniform vec4 u_color;

varying vec4 v_color;
varying vec2 v_uv;

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = vec4(a_normal.x, a_normal.y, a_normal.z, 0.0);
	normal = u_modelMatrix * normal;

	position = u_viewMatrix * position;
	normal = u_viewMatrix * normal;

	v_color = (dot(normal, vec4(0,0,1,0)) / length(normal)) * u_color;
	//v_color = max(0.0f, dot(normal, normalize(vec4(-position.x, -position.y, -position.z, 0))) / length(normal)) * u_color;
    v_uv = a_uv;

	gl_Position = u_projectionMatrix * position;
}