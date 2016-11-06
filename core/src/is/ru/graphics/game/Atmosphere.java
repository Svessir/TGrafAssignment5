package is.ru.graphics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;
import is.ru.graphics.shaders.AtmosphereShader;
import is.ru.graphics.shapes.PlanetAtmosphereGraphic;
import is.ru.graphics.shapes.SphereGraphic;

/**
 * Created by Sverrir on 2.11.2016.
 */
public class Atmosphere implements Animatable {
    private AtmosphereShader atmosphereShader;
    private float scale = 1.01f;
    private Texture gradientTexture;

    public Atmosphere() {
        gradientTexture = new Texture(Gdx.files.internal("textures/AtmosphereGradient3.jpg"));
        atmosphereShader = new AtmosphereShader();
    }

    @Override
    public void update(float deltatime) {

    }

    @Override
    public void draw() {

        Camera cam = Camera.getInstance();
        Point3D lightPosition = Sun.getInstance().getPosition();

        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTransformation(getTransformation());
        float cameraDistance = Vector3D.difference(cam.eye, new Point3D(0,0,0)).length() - 1.0f;
        ModelMatrix.main.addTranslation(0,0, -1.0f * (1.0f/cameraDistance));
        scale = (float)Math.sin(Math.acos(1.0f/cameraDistance));
        ModelMatrix.main.addScale(scale,scale,scale);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glBlendFunc(Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA, Gdx.gl20.GL_SRC_ALPHA);

        atmosphereShader.useShader();
        atmosphereShader.setViewMatrix(cam.getViewMatrix());
        atmosphereShader.setProjectionMatrix(cam.getProjectionMatrix());
        atmosphereShader.setLightPosition(lightPosition);
        atmosphereShader.setCameraPosition(cam.eye);
        atmosphereShader.setGradientTexture(gradientTexture);
        atmosphereShader.setModelMatrix(ModelMatrix.main.getMatrix());
        PlanetAtmosphereGraphic.drawAtmosphere(atmosphereShader);

        Gdx.gl.glDisable(GL20.GL_BLEND);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        ModelMatrix.main.popMatrix();

    }

    private float[] getTransformation() {
        Vector3D n = Vector3D.difference(new Point3D(0,0,0), Camera.getInstance().eye);
        Vector3D up = new Vector3D(0,1,0);
        Vector3D u = up.cross(n);
        n.normalize();
        u.normalize();
        Vector3D v = n.cross(u);

        float[] pm = new float[16];

        pm[0] = u.x; pm[4] = v.x; pm[8] = n.x; pm[12] = 0f;
        pm[1] = u.y; pm[5] = v.y; pm[9] = n.y; pm[13] = 0f;
        pm[2] = u.z; pm[6] = v.z; pm[10] = n.z; pm[14] = 0f;
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

        return pm;
    }
}
