package is.ru.graphics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
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
    public void update(float deltatime) {

    }

    @Override
    public void draw() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);

        Camera cam = Camera.getInstance();
        Point3D lightPosition = Sun.getInstance().getPosition();

        shader.useShader();
        shader.setViewMatrix(cam.getViewMatrix());
        shader.setProjectionMatrix(cam.getProjectionMatrix());
        shader.setLightPosition(lightPosition);
        shader.setCameraLightDirection(cam.getLightDirection());
        shader.setCameraLightPosition(cam.eye);
        shader.setCameraPosition(cam.eye);
        shader.setLightAmbient(0f,0f,0f,1);
        shader.setLightDiffuse(1,1,1,1);
        shader.setLightSpecular(0,0,0,1);

        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addScale(diameter,diameter,diameter);
        shader.setMaterialDiffuse(1,1,1,1);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        shader.setAlphaTexture(texture);
        SphereGraphic.drawSolidSphere(shader);
        ModelMatrix.main.popMatrix();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
