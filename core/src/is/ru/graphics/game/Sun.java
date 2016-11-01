package is.ru.graphics.game;

import is.ru.graphics.math.Point3D;

/**
 * Created by Sverrir on 1.11.2016.
 */
public class Sun implements Animatable {

    private Point3D position = new Point3D(200,0,-150);

    private static Sun instance = new Sun();

    private float angle = 0;

    public static Sun getInstance() {
        return instance;
    }

    private Sun() {}

    @Override
    public void update(float deltatime) {
        float s = (float)Math.sin((angle / 2.0) * Math.PI / 180.0);
        float c = (float)Math.cos((angle / 2.0) * Math.PI / 180.0);

        angle += 50f * deltatime;

        if(angle > 360.0f)
            angle -= 360.0f;

        position.x += s;
        position.y += c;
    }

    @Override
    public void draw() {

    }

    public Point3D getPosition() {
        return position;
    }
}
