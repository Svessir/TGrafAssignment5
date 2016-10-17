package is.ru.graphics.shaders;

import com.badlogic.gdx.graphics.Texture;

import java.nio.FloatBuffer;

/**
 * Created by Sverrir on 17.10.2016.
 */
public interface Shader {
    void setColor (float r, float g, float b, float a);

    int getVertexPointer();

    int getNormalPointer();

    int getUvPointer();

    void setModelMatrix(FloatBuffer matrix);

    void setViewMatrix(FloatBuffer matrix);

    void setProjectionMatrix(FloatBuffer matrix);

    boolean usesTexture();

    void setDiffuseTexture(Texture tex);

    void setAlphaTexture(Texture tex);

    void setBumpMapTexture(Texture tex);

    void setSpecularMapTexture(Texture tex);

}
