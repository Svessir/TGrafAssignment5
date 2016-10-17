
#ifdef GL_ES
precision mediump float;
#endif

uniform float u_usesDiffuseTexture;
uniform float u_usesAlphaTexture;
uniform float u_usesBumpTexture;
uniform float u_usesSpecularTexture;

uniform sampler2D u_diffuseTexture;
uniform sampler2D u_alphaTexture;
uniform sampler2D u_bumpTexture;
uniform sampler2D u_specularTexture;

varying vec4 v_color;
varying vec2 v_uv;

void main()
{
    if(u_usesDiffuseTexture == 1.0)
    {
        gl_FragColor = v_color * texture2D(u_diffuseTexture, v_uv);
    }
    else if(u_usesAlphaTexture == 1.0)
    {
        gl_FragColor = v_color * texture2D(u_alphaTexture, v_uv);
    }
    else if(u_usesBumpTexture == 1.0)
    {
        gl_FragColor = v_color * texture2D(u_bumpTexture, v_uv);
    }
    else if(u_usesSpecularTexture == 1.0)
    {
        gl_FragColor = v_color * texture2D(u_specularTexture, v_uv);
    }
    else
    {
        gl_FragColor = v_color;
    }
}