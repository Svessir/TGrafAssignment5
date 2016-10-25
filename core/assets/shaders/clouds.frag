
#ifdef GL_ES
precision mediump float;
#endif

uniform vec4 u_lightDiffuse;
uniform vec4 u_lightSpecular;
uniform vec4 u_lightAmbient;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform vec4 u_materialAmbient;
uniform float u_materialShininess;

varying vec4 v_color;
varying vec4 v_s;
varying vec4 v_h;
varying vec4 v_normal;

void main()
{
    float lambert = max(0.0, dot(v_s, v_normal)/(length(v_s) * length(v_normal)));
    float phong = max(0.0, dot(v_h, v_normal)/(length(v_h) * length(v_normal)));

    vec4 objectColor = u_lightAmbient*u_materialAmbient + u_lightDiffuse*u_materialDiffuse*lambert
                           + u_lightSpecular*u_materialSpecular*pow(phong, u_materialShininess);

	gl_FragColor = objectColor;
}