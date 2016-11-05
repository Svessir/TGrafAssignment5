package is.ru.graphics.shaders;

import com.badlogic.gdx.Gdx;

/**
 * Created by Sverrir on 5.11.2016.
 */
public class SkyBoxShader extends AbstractShader {

    public SkyBoxShader() {
        super("shaders/skyboxShader.vert", "shaders/skyboxShader.frag");
    }

    @Override
    public boolean usesNormals() {
        return false;
    }
}
