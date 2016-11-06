
#ifdef GL_ES
precision mediump float;
#endif

uniform vec4 u_lightDiffuse;
uniform vec4 u_lightAmbient;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialAmbient;

uniform float u_usesAlphaTexture;

uniform sampler2D u_alphaTexture;

uniform vec4 u_cameraLightDirection;

varying vec4 v_s;
varying vec4 v_h;
varying vec4 v_normal;
varying vec4 v_sCam;
varying vec2 v_uv;


void main()
{
    // Calculate the spot light strength on the object
    float spotAttenuation = max(0.0, (dot(-v_sCam, u_cameraLightDirection) / (length(v_sCam) * length(u_cameraLightDirection))));
    spotAttenuation = min(1.0, pow(spotAttenuation + 0.3, 50.0));
    float spotLambert = max(0.0, dot(v_sCam, v_normal)/(length(v_sCam) * length(v_normal)));

    float lambert = max(0.0, dot(v_s, v_normal)/(length(v_s) * length(v_normal)));

    // calculate the object color with the two lights
    gl_FragColor = u_lightAmbient*u_materialAmbient + u_lightDiffuse*u_materialDiffuse*(lambert + spotLambert * spotAttenuation);

    if(u_usesAlphaTexture == 1.0) {
        vec4 color = texture2D(u_alphaTexture, v_uv);

        /**
         use the color(black/white) read from the alpha texture to determine the alpha.
         If further from black then higher alpha it has. The multiplication factor is
         just configured with a visual preferance.
        */
        gl_FragColor.a = (color.x * color.y * color.z) * 2.0;
    }
}