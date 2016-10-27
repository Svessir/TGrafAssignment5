
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

uniform float u_usesDiffuseTexture;
uniform float u_usesAlphaTexture;

uniform sampler2D u_diffuseTexture;
uniform sampler2D u_alphaTexture;

uniform vec4 u_cameraLightDirection;

varying vec4 v_color;
varying vec4 v_s;
varying vec4 v_h;
varying vec4 v_normal;
varying vec4 v_sCam;
varying vec2 v_uv;


void main()
{
    float spotAttenuation = max(0.0, (dot(-v_sCam, u_cameraLightDirection) / (length(v_sCam) * length(u_cameraLightDirection))));
    spotAttenuation = min(1.0, pow(spotAttenuation + 0.3, 50.0));
    float spotLambert = max(0.0, dot(v_sCam, v_normal)/(length(v_sCam) * length(v_normal)));

    float lambert = max(0.0, dot(v_s, v_normal)/(length(v_s) * length(v_normal)));
    float phong = max(0.0, dot(v_h, v_normal)/(length(v_h) * length(v_normal)));

    vec4 objectColor = u_lightAmbient*u_materialAmbient + u_lightDiffuse*u_materialDiffuse*lambert
                           + u_lightSpecular*u_materialSpecular*pow(phong, u_materialShininess);

    if(u_usesDiffuseTexture == 1.0) {
        gl_FragColor = objectColor + spotLambert * spotAttenuation;
        vec4 color = texture2D(u_diffuseTexture, v_uv);
        gl_FragColor.a = (color.x * color.y * color.z) * 2;
        return;
    }
    if(u_usesAlphaTexture == 1.0) {

    }

	gl_FragColor = objectColor;
}