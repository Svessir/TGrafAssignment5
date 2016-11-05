package is.ru.graphics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;
import is.ru.graphics.shaders.EarthShader;
import is.ru.graphics.shapes.PlanetAtmosphereGraphic;
import is.ru.graphics.shapes.SphereGraphic;
import is.ru.graphics.sounds.Sounds;

public class EarthProject extends ApplicationAdapter implements InputProcessor {

	private EarthShader earthShader;
	private Camera cam;
	private Animatable earth;
	private Animatable sun;
	private Sounds backgroundSounds;
	private float earthDiameter = 2.0f;

	@Override
	public void create() {
		earthShader = new EarthShader();

		/*backgroundSounds = new Sounds();
		try {
			backgroundSounds.playSound();
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		SphereGraphic.create();
		PlanetAtmosphereGraphic.create();

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		earthShader.setModelMatrix(ModelMatrix.main.getMatrix());
		//cloudShader.setModelMatrix(ModelMatrix.main.getMatrix());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		cam = Camera.getInstance();
		sun = Sun.getInstance();
		cam.PerspctiveProjection3D(90, 16f/9f, 0.1f, 100);
		//cam.OrthographicProjection3D(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight(), 0.1f, 100);
		cam.Look3D(new Point3D(2, 2, 2), new Point3D(0, 0, 0), new Vector3D(0, 1, 0));
		earthShader.setViewMatrix(cam.getViewMatrix());
		earthShader.setProjectionMatrix(cam.getProjectionMatrix());

		earth = new Earth(earthShader,earthDiameter);
	}

	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		cam.update(deltaTime);
		sun.update(deltaTime);

	}

	private void display() {
		Gdx.gl.glViewport(0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		ModelMatrix.main.loadIdentityMatrix();
		cam.draw();
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