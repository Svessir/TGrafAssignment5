package is.ru.graphics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import is.ru.graphics.math.ModelMatrix;
import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;
import is.ru.graphics.shaders.SimpleShader;
import is.ru.graphics.shapes.SphereGraphic;

public class EarthProject extends ApplicationAdapter {

	private SimpleShader shader;
	private Camera cam;
	@Override
	public void create() {

		shader = new SimpleShader("shaders/simple3D.vert" , "shaders/simple3D.frag");


		//COLOR IS SET HERE
		//shader.setColor(0.7f, 0.2f, 0, 1);

		SphereGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());


		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		cam = new Camera();
		//cam.perspectiveProjection(90, 16 / 9, 0.07f, 100);
		cam.look(new Point3D(15, 5, 5), new Point3D(0, 0, 0), new Vector3D(0, 1, 0));

		shader.setViewMatrix(cam.getViewMatrix());
		shader.setProjectionMatrix(cam.getProjectionMatrix());
	}

	private void update() {
	}

	private void display() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		shader.setViewMatrix(cam.getViewMatrix());
		shader.setProjectionMatrix(cam.getProjectionMatrix());

		ModelMatrix.main.pushMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		shader.setColor(0,1,0,1);
		SphereGraphic.drawSolidSphere();
		ModelMatrix.main.popMatrix();
	}

	@Override
	public void render() {
		update();
		display();
	}
}