package is.ru.graphics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.shaders.EarthShader;
import is.ru.graphics.shaders.Shader;
import is.ru.graphics.shapes.SphereGraphic;

/**
 * Created by Sverrir on 17.10.2016.
 */
public class Earth implements Animatable {

    EarthShader shader;
    Texture dayDiffuse;
    Texture nightDiffuse;
    Texture specularMap;

    public Earth(EarthShader shader) {
        this.shader = shader;
        dayDiffuse = new Texture(Gdx.files.internal("textures/Albedo.jpg"));
        nightDiffuse = new Texture(Gdx.files.internal("textures/night_lights_modified.png"));
        specularMap = new Texture(Gdx.files.internal("textures/Ocean_Mask.png"));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addScale(2,2,2);
        shader.setMaterialAmbient(0.5f,0.5f,0.5f,1);
        shader.setMaterialSpecular(0,0,0,0);
        shader.setMaterialDiffuse(1,1,1,1);
        shader.setMaterialShininess(0);
        shader.setDiffuseTexture(dayDiffuse);
        shader.setUsesNightLightDiffuseTex(nightDiffuse);
        shader.setSpecularMapTexture(specularMap);
        shader.setModelMatrix(ModelMatrix.main.getMatrix());
        SphereGraphic.drawSolidSphere(shader);
        ModelMatrix.main.popMatrix();
    }
}
