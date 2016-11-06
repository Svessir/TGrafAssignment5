package is.ru.graphics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import is.ru.graphics.motion.BSplineMotion;
import is.ru.graphics.motion.BezierMotion;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;
import is.ru.graphics.shaders.CloudShader;
import is.ru.graphics.shaders.EarthShader;
import is.ru.graphics.shapes.SphereGraphic;

import java.util.ArrayList;

public class EarthProject extends ApplicationAdapter implements InputProcessor {

	private EarthShader earthShader;
	private CloudShader cloudShader;

	private Camera cam;

	private Animatable earth;
	private Animatable clouds;

	private BSplineMotion motion;
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

	private float timeElapsed = 0.0f;
	private float rotationSpeed = 0.5f;
	private float roundMotionSize = 4.0f;

	private Animatable sun;
	private float earthDiameter = 2.0f;
	private float angle;

	private ArrayList<Point3D> controlPoints;

	@Override
	public void create() {
		earthShader = new EarthShader();
		cloudShader = new CloudShader();

		/*backgroundSounds = new Sounds();
		try {
			backgroundSounds.playSound();
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		SphereGraphic.create();

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		earthShader.setModelMatrix(ModelMatrix.main.getMatrix());
		//cloudShader.setModelMatrix(ModelMatrix.main.getMatrix());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		/*bezierMotion = new BezierMotion(new Point3D(-4.0f, 0.0f, 0.0f), new Point3D(-3.5f, 0.0f, 2.0f),
								  new Point3D(-3.0f, 0.0f, 2.6f), new Point3D(-2.5f, 0.0f, 3.1f), 3.0f, 10.0f);*/

		bezierMotion = new BezierMotion(new Point3D(-4.0f, -2.0f, 0.0f), new Point3D(-2.0f, -1.5f, -4.0f),
				new Point3D(2.0f, 0.0f, -4.0f), new Point3D(4.0f, 1.5f, 0.0f), 3.0f, 15.0f);
		bezierMotion2 = new BezierMotion(new Point3D(4.0f, 1.5f, 0.0f), new Point3D(3.0f, 2.0f, 0.0f),
				new Point3D(2.0f, 6.0f, 0.0f), new Point3D(1.0f, 4.0f, 0.0f), 20.0f, 32.0f);
		/*bezierMotion3 = new BezierMotion(new Point3D(0.0f, 4.0f, 0.0f), new Point3D(0.0f, 6.0f, 1.0f),
				new Point3D(0.0f, 2.0f, 3.0f), new Point3D(0.0f, 0.0f, 4.0f), 27.0f, 39.0f);
		bezierMotion4 = new BezierMotion(new Point3D(0.0f, 0.0f, 4.0f), new Point3D(-4.0f, 0.0f, 2.0f),
				new Point3D(-4.0f, 0.0f, -2.0f), new Point3D(0.0f, 0.0f, -4.0f), 39.0f, 51.0f);*/


		modelPosition = new Point3D();

		cam = Camera.getInstance();
		sun = Sun.getInstance();
		cam.PerspctiveProjection3D(90, 16f/9f, 0.1f, 100);
		//cam.OrthographicProjection3D(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight(), 0.01f, 100);
		cam.Look3D(new Point3D(2, 2, 2), new Point3D(0, 0, 0), new Vector3D(0, 1, 0));
		earthShader.setViewMatrix(cam.getViewMatrix());
		earthShader.setProjectionMatrix(cam.getProjectionMatrix());
		cloudShader.setViewMatrix(cam.getViewMatrix());
		cloudShader.setProjectionMatrix(cam.getProjectionMatrix());

		earth = new Earth(earthShader,earthDiameter);
		clouds = new Clouds(cloudShader, earthDiameter*1.01f);
	}

	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		cam.update(deltaTime);
		if(firstFrame){
			currentTime = 0.0f;
			firstFrame = false;
		}
		else {
			currentTime += Gdx.graphics.getRawDeltaTime();
		}

		angle += 20f * deltaTime;
		/*timeElapsed += deltaTime;
		cam.Look3D(new Point3D(-(float)Math.cos(timeElapsed * rotationSpeed) * roundMotionSize, 0.0f, -(float) Math.sin(timeElapsed * rotationSpeed) * roundMotionSize),
				new Point3D(0,(float)Math.sin(timeElapsed * rotationSpeed) * (roundMotionSize/4),0), new Vector3D(0,1,0));
		System.out.println(cam);*/
		sun.update(deltaTime);
		if(firstBezier){
			bezierMotion.getCurrentPoistion(currentTime, modelPosition);
			cam.Look3D(new Point3D(modelPosition.x, modelPosition.y, modelPosition.z),
					new Point3D(0,0,0), new Vector3D(0,1,0));
			if(currentTime > 15.0f){
				secondBezier = true;
				firstBezier = false;
			}
		}
		if(secondBezier){
			bezierMotion2.getCurrentPoistion(currentTime, modelPosition);
			cam.Look3D(new Point3D(modelPosition.x, modelPosition.y, modelPosition.z),
					new Point3D(0,0,0), new Vector3D(0,1,0));
			if(currentTime > 32.0f){
				thirdBezier = true;
				secondBezier = false;
			}
		}
		/*if(thirdBezier){
			bezierMotion3.getCurrentPoistion(currentTime, modelPosition);
			cam.Look3D(new Point3D(modelPosition.x, modelPosition.y, modelPosition.z),
					new Point3D(0,0,0), new Vector3D(0,1,0));
			if(currentTime > 26.0f){
				forthBezier = true;
				thirdBezier = false;
			}
		}
		if(forthBezier){
			bezierMotion4.getCurrentPoistion(currentTime, modelPosition);
			cam.Look3D(new Point3D(modelPosition.x, modelPosition.y, modelPosition.z),
					new Point3D(0,0,0), new Vector3D(0,1,0));
		}*/
		System.out.println(cam);
	}

	private void display() {
		Gdx.gl.glViewport(0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		ModelMatrix.main.loadIdentityMatrix();

		earth.draw();

		clouds.draw();


	}

	@Override
	public void render() {
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}

	/*
	* eye = (cos(elapsedTime * incrementFactor) * sizeFactor , y , sin(elapsedTime * incrementFactor) * sizeFactor);
	* center = (0, sin(elapsedTime * incrementFactor) * sizeFactor , 0);
	* lookAt(eye, center, (0,1,0));
	*
	* */


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