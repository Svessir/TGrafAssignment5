package is.ru.graphics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.shaders.CloudShader;
import is.ru.graphics.shapes.SphereGraphic;

/**
 * Created by Kárii on 25.10.2016.
 */
public class Clouds implements Animatable{
    private float diameter;
    private Texture texture;
    CloudShader shader;

    public Clouds(CloudShader shader, float diameter){
        this.shader = shader;
        this.diameter = diameter;
        this.texture = new Texture(Gdx.files.internal("textures/Clouds.png"));

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addScale(diameter,diameter,diameter);
        shader.setMaterialDiffuse(1,0,0,1);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        //shader.setDiffuseTexture(texture);
        //shader.setAlphaTexture(texture);
        SphereGraphic.drawSolidSphere(shader);
        ModelMatrix.main.popMatrix();
    }
}
