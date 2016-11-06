package is.ru.graphics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;
import is.ru.graphics.shaders.EarthShader;
import is.ru.graphics.shapes.SphereGraphic;

/**
 * Created by Sverrir on 17.10.2016.
 */
public class Earth implements Animatable {

    Clouds clouds;
    Atmosphere atmosphere;
    EarthShader earthShader;

    Texture dayDiffuse;
    Texture nightDiffuse;
    Texture specularMap;
    Texture alphaTexture;
    private float diameter;

    public Earth(EarthShader earthShader, float diameter) {
        this.earthShader = earthShader;
        dayDiffuse = new Texture(Gdx.files.internal("textures/Albedo.jpg"));
        nightDiffuse = new Texture(Gdx.files.internal("textures/night_Lights_modified.png"));
        specularMap = new Texture(Gdx.files.internal("textures/Ocean_Mask.png"));
        this.alphaTexture = new Texture(Gdx.files.internal("textures/Clouds.png"));
        this.diameter = diameter;

        atmosphere = new Atmosphere();

        clouds = new Clouds();
    }

    @Override
    public void update(float deltatime) {

    }

    @Override
    public void draw() {
        Camera cam = Camera.getInstance();
        Point3D lightPosition = Sun.getInstance().getPosition();

        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addScale(diameter,diameter,diameter);

        atmosphere.draw();

        earthShader.useShader();
        earthShader.setViewMatrix(cam.getViewMatrix());
        earthShader.setProjectionMatrix(cam.getProjectionMatrix());
        earthShader.setLightPosition(lightPosition);
        earthShader.setCameraLightPosition(cam.eye);
        earthShader.setCameraLightDirection(cam.getLightDirection());
        earthShader.setCameraPosition(cam.eye);
        earthShader.setLightSpecular(0,0,0,0);

        earthShader.setMaterialShininess(150);
        earthShader.setDiffuseTexture(dayDiffuse);
        earthShader.setUsesNightLightDiffuseTex(nightDiffuse);
        earthShader.setSpecularMapTexture(specularMap);
        earthShader.setModelMatrix(ModelMatrix.main.getMatrix());
        SphereGraphic.drawSolidSphere(earthShader);

        clouds.draw();

        ModelMatrix.main.popMatrix();
    }
}
