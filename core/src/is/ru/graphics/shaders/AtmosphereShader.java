package is.ru.graphics.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Sverrir on 2.11.2016.
 */
public class AtmosphereShader extends AbstractShader {

    private int gradientTextureLoc;
    private boolean usesGradientTexture;

    public AtmosphereShader() {
        super("shaders/atmosphereShader.vert", "shaders/atmosphereShader.frag");
        gradientTextureLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_gradientTexture");
    }

    public void setGradientTexture(Texture gradientTexture) {
        usesGradientTexture = gradientTexture != null;
        bindTexture(gradientTexture, 4, gradientTextureLoc);
    }

    @Override
    public boolean usesTexture() {
        return super.usesTexture() || usesGradientTexture;
    }

    protected void bindTexture(Texture tex, int layer, int textureLoc) {
        tex.bind(layer);
        Gdx.gl.glUniform1i(textureLoc, layer);

        Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
    }
}
