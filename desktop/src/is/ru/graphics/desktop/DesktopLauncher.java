package is.ru.graphics.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import is.ru.graphics.game.EarthProject;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 4000;
		config.height = 4000;
		new LwjglApplication(new EarthProject(), config);
	}
}
