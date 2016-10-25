package is.ru.graphics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import is.ru.graphics.math.Matrix;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;
import is.ru.graphics.shaders.EarthShader;
import is.ru.graphics.shapes.SphereGraphic;
import is.ru.graphics.sounds.Sounds;

public class EarthProject extends ApplicationAdapter implements InputProcessor {

	private EarthShader shader;
	//private CloudShader cloudShader;
	private Camera cam;
	private Animatable earth;
	private Animatable clouds;
	private Sounds backgroundSounds;
	private float earthDiameter = 2.0f;
	private float angle;

	@Override
	public void create() {
		shader = new EarthShader();
		//cloudShader = new CloudShader();
		backgroundSounds = new Sounds();
		try {
			backgroundSounds.playSound();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SphereGraphic.create();

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());


		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		cam = new Camera();
		cam.PerspctiveProjection3D(90, 16f/9f, 0.001f, 100);
		//cam.OrthographicProjection3D(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight(), 0.01f, 100);
		cam.Look3D(new Point3D(2, 2, 2), new Point3D(0, 0, 0), new Vector3D(0, 1, 0));
		shader.setViewMatrix(cam.getViewMatrix());
		shader.setProjectionMatrix(cam.getProjectionMatrix());

		earth = new Earth(shader,earthDiameter);
		//clouds = new Clouds(cloudShader, earthDiameter*1.2f);
	}

	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		cam.update(deltaTime);
		angle += 180.0f * deltaTime;

	}

	private void display() {
		Gdx.gl.glViewport(0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		float s = (float)Math.sin((angle / 2.0) * Math.PI / 180.0);
		float c = (float)Math.cos((angle / 2.0) * Math.PI / 180.0);
		shader.setViewMatrix(cam.getViewMatrix());
		shader.setProjectionMatrix(cam.getProjectionMatrix());
		shader.setLightPosition(new Point3D(-200 * s, 100, -100 * c));
		shader.setCameraLightPosition(new Point3D(cam.eye.x, cam.eye.y, cam.eye.z));
		shader.setCameraLightDirection(new Point3D(0,0,-1));
		shader.setCameraPosition(cam.eye);
		shader.setLightAmbient(0f,0f,0f,1);
		shader.setLightDiffuse(1,1,1,1);
		shader.setLightSpecular(0,0,0,0);
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		ModelMatrix.main.loadIdentityMatrix();

		earth.draw();


	}

	@Override
	public void render() {
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}