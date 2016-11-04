package is.ru.graphics.math;

/**
 * Created by Kárii on 3.11.2016.
 */
public class BezierMotion {
    private Point3D P1;
    private Point3D P2;
    private Point3D P3;
    private Point3D P4;
    private float startTime;
    private float endTime;

    public BezierMotion(Point3D P1, Point3D P2, Point3D P3, Point3D P4, float startTime, float endTime){
        this.P1 = P1;
        this.P2 = P2;
        this.P3 = P3;
        this.P4 = P4;
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
            out_postion.x = P4.x;
            out_postion.y = P4.y;
            out_postion.z = P4.z;
        }
        else {
            float t = (currentTime - startTime)/ (endTime - startTime);
            out_postion.x = (1.0f - t)*(1.0f - t)*(1.0f - t) * P1.x + 3*(1.0f - t) * (1.0f - t)*t* P2.x + 3*(1.0f - t)*t*t*P3.x + t*t*t*P4.x;
            out_postion.y = (1.0f - t)*(1.0f - t)*(1.0f - t) * P1.y + 3*(1.0f - t) * (1.0f - t)*t* P2.y + 3*(1.0f - t)*t*t*P3.y + t*t*t*P4.y;
            out_postion.z = (1.0f - t)*(1.0f - t)*(1.0f - t) * P1.z + 3*(1.0f - t) * (1.0f - t)*t* P2.z + 3*(1.0f - t)*t*t*P3.z + t*t*t*P4.z;
        }
    }
}
