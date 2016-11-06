package is.ru.graphics.motion;

import is.ru.graphics.math.Point3D;

/**
 * Created by Kárii on 3.11.2016.
 */
public class LinearMotion {
    private Point3D P1;
    private Point3D P2;
    private float startTime;
    private float endTime;

    public LinearMotion(Point3D P1, Point3D P2, float startTime, float endTime){
        this.P1 = P1;
        this.P2 = P2;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void getCurrentPoistion(float currentTime, Point3D out_postion){
        if(currentTime < startTime){
            out_postion.x = P1.x;
            out_postion.y = P1.y;
            out_postion.z = P1.z;
        }
        else if(currentTime > endTime){
            out_postion.x = P2.x;
            out_postion.y = P2.y;
            out_postion.z = P2.z;
        }
        else {
            float t = (currentTime - startTime)/ (endTime - startTime);
            out_postion.x = (1.0f - t) * P1.x + t * P2.x;
            out_postion.y = (1.0f - t) * P1.y + t * P2.y;
            out_postion.z = (1.0f - t) * P1.z + t * P2.z;

        }
    }
}
