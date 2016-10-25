
#ifdef GL_ES
precision mediump float;
#endif

uniform float u_usesDiffuseTexture;
uniform float u_usesAlphaTexture;
uniform float u_usesBumpTexture;
uniform float u_usesSpecularTexture;
uniform float u_usesNightLightDiffuseTexture;

uniform sampler2D u_diffuseTexture;
uniform sampler2D u_alphaTexture;
uniform sampler2D u_bumpTexture;
uniform sampler2D u_specularTexture;
uniform sampler2D u_nightLightDiffuseTexture;

uniform vec4 u_lightDiffuse;
uniform vec4 u_lightSpecular;
uniform vec4 u_lightAmbient;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform vec4 u_materialAmbient;
uniform float u_materialShininess;

uniform vec4 u_cameraLightDirection;

varying vec2 v_uv;
varying vec4 v_h;
varying vec4 v_s;
varying vec4 v_normal;
varying vec4 v_sCam;

void main()
{
    float spotAttenuation = max(0.0, (dot(-v_sCam, u_cameraLightDirection) / (length(v_sCam) * length(u_cameraLightDirection))));
    spotAttenuation = min(1.0, pow(spotAttenuation + 0.3, 50.0));
    float spotLambert = max(0.0, dot(v_sCam, v_normal)/(length(v_sCam) * length(v_normal)));
    float lambert = max(0.0, dot(v_s, v_normal)/(length(v_s) * length(v_normal)));
    float phong = max(0.0, dot(v_h, v_normal)/(length(v_h) * length(v_normal)));

    vec4 objectColor = u_lightAmbient*u_materialAmbient + u_lightDiffuse*u_materialDiffuse*lambert
                       + u_lightSpecular*u_materialSpecular*pow(phong, u_materialShininess);

    if(u_usesDiffuseTexture == 1.0)
    {
        //vec4 invertObjectColor = vec4(1 - objectColor.x,1 - objectColor.y,1-objectColor.z,objectColor.w);
        //gl_FragColor = objectColor * texture2D(u_diffuseTexture, v_uv) + invertObjectColor * texture2D(u_nightLightDiffuseTexture, v_uv);
        lambert += spotLambert * spotAttenuation;
        gl_FragColor = (pow(lambert, 0.7) * texture2D(u_diffuseTexture, v_uv))
                        + (1.0 - pow(lambert,0.35)) * texture2D(u_nightLightDiffuseTexture, v_uv);
    }
    if(u_usesAlphaTexture == 1.0)
    {
        //gl_FragColor = v_color * texture2D(u_alphaTexture, v_uv);
    }
    if(u_usesBumpTexture == 1.0)
    {
        //gl_FragColor = v_color * texture2D(u_bumpTexture, v_uv);
    }
    if(u_usesSpecularTexture == 1.0)
    {
        gl_FragColor += (pow(phong, u_materialShininess) * texture2D(u_specularTexture, v_uv));
    }
    if(u_usesDiffuseTexture == 0.0 && u_usesAlphaTexture == 0.0 && u_usesBumpTexture == 0.0 && u_usesSpecularTexture == 0.0)
    {
        gl_FragColor = objectColor;
    }
}