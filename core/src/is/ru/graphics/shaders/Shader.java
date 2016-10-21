package is.ru.graphics.shaders;

import com.badlogic.gdx.graphics.Texture;
import is.ru.graphics.math.Point3D;

import java.nio.FloatBuffer;

/**
 * Created by Sverrir on 17.10.2016.
 */
public interface Shader {

    int getVertexPointer();

    int getNormalPointer();

    int getUvPointer();

    void setModelMatrix(FloatBuffer matrix);

    void setViewMatrix(FloatBuffer matrix);

    void setProjectionMatrix(FloatBuffer matrix);

    boolean usesTexture();
}
