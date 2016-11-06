package is.ru.graphics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import is.ru.graphics.motion.BezierMotion;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;
import is.ru.graphics.shaders.EarthShader;
import is.ru.graphics.shapes.PlanetAtmosphereGraphic;
import is.ru.graphics.shapes.SphereGraphic;

public class EarthProject extends ApplicationAdapter implements InputProcessor {

	private EarthShader earthShader;
	private Camera cam;

	private Animatable earth;

	private BezierMotion bezierMotion;
	private BezierMotion bezierMotion2;
	private BezierMotion bezierMotion3;
	private BezierMotion bezierMotion4;
	private boolean firstBezier = true;
	private boolean secondBezier = false;
	private boolean thirdBezier = false;
	private boolean forthBezier = false;

	private float currentTime;
	private boolean firstFrame = true;
	private Point3D modelPosition;

	private Animatable sun;
	private float earthDiameter = 2.0f;

	@Override
	public void create() {
		earthShader = new EarthShader();

		SphereGraphic.create();
		PlanetAtmosphereGraphic.create(1.1f);		// Also try 1.2f

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		earthShader.setModelMatrix(ModelMatrix.main.getMatrix());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		bezierMotion = new BezierMotion(new Point3D(-4.0f, -2.0f, 0.0f), new Point3D(-2.0f, -0.5f, -4.0f),
				new Point3D(2.0f, 0.5f, -4.0f), new Point3D(4.0f, 3.0f, 0.0f), 1.0f, 20.0f);
		bezierMotion2 = new BezierMotion(new Point3D(4.0f, 3.0f, 0.0f), new Point3D(2.0f, 0.5f, 4.0f),
				new Point3D(-2.0f, -0.5f, 4.0f), new Point3D(-4.0f, -2.0f, 0.0f), 20.0f, 40.0f);
		bezierMotion3 = new BezierMotion(new Point3D(-4.0f, -2.0f, 0.0f), new Point3D(-2.0f, -0.5f, -4.0f),
				new Point3D(2.0f, 0.5f, -4.0f), new Point3D(4.0f, 2.0f, 0.0f), 40.0f, 60.0f);
		bezierMotion4 = new BezierMotion(new Point3D(4.0f, 2.0f, 0.0f), new Point3D(-10.0f, 5.0f, 15.0f),
				new Point3D(5.0f, 10.0f, -15.0f), new Point3D(4.0f, 0.0f, -3.0f), 60.0f, 80.0f);


		modelPosition = new Point3D();

		cam = Camera.getInstance();
		sun = Sun.getInstance();
		cam.PerspctiveProjection3D(90, 16f/9f, 0.1f, 100);
		cam.Look3D(new Point3D(2f, 2f, 2f), new Point3D(0f, 0f, 0f), new Vector3D(0f, 1f, 0f));
		earthShader.setViewMatrix(cam.getViewMatrix());
		earthShader.setProjectionMatrix(cam.getProjectionMatrix());

		earth = new Earth(earthShader,earthDiameter);
	}

	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		if(firstFrame){
			currentTime = 0.0f;
			firstFrame = false;
		}
		else {
			currentTime += Gdx.graphics.getRawDeltaTime();
		}
		sun.update(deltaTime);
		if(firstBezier){
			bezierMotion.getCurrentPoistion(currentTime, modelPosition);
			cam.Look3D(new Point3D(modelPosition.x, modelPosition.y, modelPosition.z),
					new Point3D(0f,0f,0f), new Vector3D(0f,1f,0f));
			if(currentTime > 20.0f){
				secondBezier = true;
				firstBezier = false;
			}
		}
		else if(secondBezier){
			bezierMotion2.getCurrentPoistion(currentTime, modelPosition);
			cam.Look3D(new Point3D(modelPosition.x, modelPosition.y, modelPosition.z),
					new Point3D(0,0,0), new Vector3D(0,1,0));
			if(currentTime > 40.0f){
				thirdBezier = true;
				secondBezier = false;
			}
		}
		else if(thirdBezier){
			bezierMotion3.getCurrentPoistion(currentTime, modelPosition);
			cam.Look3D(new Point3D(modelPosition.x, modelPosition.y, modelPosition.z),
					new Point3D(0,0,0), new Vector3D(0,1,0));
			if(currentTime > 60.0f){
				forthBezier = true;
				thirdBezier = false;
			}
		}
		else if(forthBezier){
			bezierMotion4.getCurrentPoistion(currentTime, modelPosition);
			cam.Look3D(new Point3D(modelPosition.x, modelPosition.y, modelPosition.z),
					new Point3D(0,0,0), new Vector3D(0,1,0));
			if(currentTime > 80.0){
				forthBezier = false;
			}
		}
		else{
			cam.update(deltaTime);
		}
	}

	private void display() {
		Gdx.gl.glViewport(0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		ModelMatrix.main.loadIdentityMatrix();
		//cam.draw();
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