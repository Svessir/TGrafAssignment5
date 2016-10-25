package is.ru.graphics.game;

import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.shaders.AbstractShader;
import is.ru.graphics.shaders.CloudShader;
import is.ru.graphics.shaders.EarthShader;
import is.ru.graphics.shaders.Shader;
import is.ru.graphics.shapes.SphereGraphic;
import sun.security.provider.SHA;

/**
 * Created by Kárii on 25.10.2016.
 */
public class Clouds implements Animatable{
    private float diameter;
    CloudShader shader;

    public Clouds(CloudShader shader, float diameter){
        this.shader = shader;
        this.diameter = diameter;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(5,0,5);
        ModelMatrix.main.addScale(diameter,diameter,diameter);
        shader.setMaterialAmbient(0.5f,0.5f,0.5f,1);
        shader.setMaterialSpecular(1,1,1,1);
        shader.setMaterialDiffuse(1,1,1,1);
        shader.setMaterialShininess(150);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        SphereGraphic.drawSolidSphere(shader,1);
        ModelMatrix.main.popMatrix();
    }
}
