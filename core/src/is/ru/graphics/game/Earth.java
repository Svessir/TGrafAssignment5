package is.ru.graphics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.shaders.Shader;
import is.ru.graphics.shapes.SphereGraphic;

/**
 * Created by Sverrir on 17.10.2016.
 */
public class Earth implements Animatable {

    Shader shader;
    Texture dayDiffuse;

    public Earth(Shader shader) {
        this.shader = shader;
        dayDiffuse = new Texture(Gdx.files.internal("textures/Albedo.jpg"));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addScale(2,2,2);
        shader.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        shader.setDiffuseTexture(dayDiffuse);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        SphereGraphic.drawSolidSphere(shader);
        ModelMatrix.main.popMatrix();
    }
}
