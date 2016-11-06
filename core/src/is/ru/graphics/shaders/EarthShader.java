package is.ru.graphics.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Sverrir on 17.10.2016.
 */
public class EarthShader extends AbstractShader {

    protected boolean usesNightLightDiffuseTexture = false;
    protected int usesNightLightDiffuseTexLoc;
    protected int nightLightDiffuseTextureLoc;

    public EarthShader() {
        super("shaders/earthShader.vert", "shaders/earthShader.frag");

        usesNightLightDiffuseTexLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesNightLightDiffuseTexture");
        nightLightDiffuseTextureLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_nightLightDiffuseTexture");
    }

    public void setUsesNightLightDiffuseTex(Texture tex) {
        usesNightLightDiffuseTexture = tex != null ? true : false;
        bindTexture(tex, 4, nightLightDiffuseTextureLoc, usesNightLightDiffuseTexLoc);
    }
}
