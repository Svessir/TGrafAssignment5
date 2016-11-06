
#ifdef GL_ES
precision mediump float;
#endif

uniform float u_usesDiffuseTexture;
uniform float u_usesSpecularTexture;
uniform float u_usesNightLightDiffuseTexture;

uniform sampler2D u_diffuseTexture;
uniform sampler2D u_specularTexture;
uniform sampler2D u_nightLightDiffuseTexture;

uniform vec4 u_lightSpecular;

uniform vec4 u_materialSpecular;
uniform float u_materialShininess;

uniform vec4 u_cameraLightDirection;

varying vec2 v_uv;
varying vec4 v_h;
varying vec4 v_s;
varying vec4 v_normal;
varying vec4 v_sCam;

void main()
{
    // Caluclate the spotlight strength
    float spotAttenuation = max(0.0, (dot(-v_sCam, u_cameraLightDirection) / (length(v_sCam) * length(u_cameraLightDirection))));
    spotAttenuation = min(1.0, pow(spotAttenuation + 0.3, 50.0));
    float spotLambert = max(0.0, dot(v_sCam, v_normal)/(length(v_sCam) * length(v_normal)));

    // Calculate the lighting vectors for the sun.
    float lambert = max(0.0, dot(v_s, v_normal)/(length(v_s) * length(v_normal)));
    float phong = max(0.0, dot(v_h, v_normal)/(length(v_h) * length(v_normal)));

    if(u_usesDiffuseTexture == 1.0)
    {
        // Add the spotlight strength to the overall lighting strength.
        lambert += spotLambert * spotAttenuation;

        /*
         Lerp between light and day texture according to lambert (diffuse light strength).
         The multiplication factors and exponents are configured by visual preference as their
         purpose is just to configure how clear cut the day night shift is.
        */
        gl_FragColor = (pow(lambert, 0.7) * texture2D(u_diffuseTexture, v_uv))
                        + (1.0 - pow(lambert,0.35)) * (texture2D(u_nightLightDiffuseTexture, v_uv) * 0.5);
    }
    if(u_usesSpecularTexture == 1.0)
    {
        /**
         Add the specularity light color to the fragment. The color read from the specularity texture
         is multiplied with the specularity so that where there is sea in the texture then the specularity
         is high, else on terrain their is no specularity.
        */
        gl_FragColor += (pow(phong, u_materialShininess) * texture2D(u_specularTexture, v_uv));
    }
}