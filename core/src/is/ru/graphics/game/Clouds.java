package is.ru.graphics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;
import is.ru.graphics.shaders.CloudShader;
import is.ru.graphics.shapes.SphereGraphic;

/**
 * Created by Kárii on 25.10.2016.
 */
public class Clouds implements Animatable{
    private float diameter;
    private Texture texture;
    private float scale = 1.01f;
    CloudShader cloudShader;

    public Clouds(){
        cloudShader = new CloudShader();
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
        Vector3D lightColor = Sun.getInstance().getColor();

        cloudShader.useShader();
        cloudShader.setViewMatrix(cam.getViewMatrix());
        cloudShader.setProjectionMatrix(cam.getProjectionMatrix());
        cloudShader.setLightPosition(lightPosition);
        cloudShader.setCameraLightDirection(cam.getLightDirection());
        cloudShader.setCameraLightPosition(cam.eye);
        cloudShader.setCameraPosition(cam.eye);
        cloudShader.setLightAmbient(0f,0f,0f,1);
        cloudShader.setLightDiffuse(lightColor.x,lightColor.y,lightColor.z,1);
        cloudShader.setLightSpecular(0,0,0,1);

        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addScale(scale,scale,scale);
        cloudShader.setMaterialDiffuse(1,1,1,1); // WHITE skies;
        cloudShader.setModelMatrix(ModelMatrix.main.getMatrix());
        cloudShader.setAlphaTexture(texture);
        SphereGraphic.drawSolidSphere(cloudShader);
        ModelMatrix.main.popMatrix();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
