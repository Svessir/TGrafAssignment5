package is.ru.graphics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.shaders.EarthShader;
import is.ru.graphics.shapes.SphereGraphic;

/**
 * Created by Sverrir on 17.10.2016.
 */
public class Earth implements Animatable {

    EarthShader shader;
    Texture dayDiffuse;
    Texture nightDiffuse;
    Texture specularMap;
    Texture alphaTexture;
    private float diameter;

    public Earth(EarthShader shader, float diameter) {
        this.shader = shader;
        dayDiffuse = new Texture(Gdx.files.internal("textures/Albedo.jpg"));
        nightDiffuse = new Texture(Gdx.files.internal("textures/night_Lights_modified.png"));
        specularMap = new Texture(Gdx.files.internal("textures/Ocean_Mask.png"));
        this.alphaTexture = new Texture(Gdx.files.internal("textures/Clouds.png"));
        this.diameter = diameter;
    }

    @Override
    public void update(float deltatime) {

    }

    @Override
    public void draw() {
        Camera cam = Camera.getInstance();
        Point3D lightPosition = Sun.getInstance().getPosition();
        shader.useShader();
        shader.setViewMatrix(cam.getViewMatrix());
        shader.setProjectionMatrix(cam.getProjectionMatrix());
        shader.setLightPosition(lightPosition);
        shader.setCameraLightPosition(cam.eye);
        shader.setCameraLightDirection(cam.getLightDirection());
        shader.setCameraPosition(cam.eye);
        shader.setLightAmbient(0f,0f,0f,1);
        shader.setLightDiffuse(0,0,0,1);
        shader.setLightSpecular(0,0,0,0);

        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addScale(diameter,diameter,diameter);
        shader.setMaterialAmbient(0.5f,0.5f,0.5f,1);
        shader.setMaterialSpecular(1,1,1,1);
        shader.setMaterialDiffuse(1,1,1,1);
        shader.setMaterialShininess(150);
        shader.setDiffuseTexture(dayDiffuse);
        shader.setUsesNightLightDiffuseTex(nightDiffuse);
        shader.setSpecularMapTexture(specularMap);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        SphereGraphic.drawSolidSphere(shader);
        ModelMatrix.main.popMatrix();
    }
}
