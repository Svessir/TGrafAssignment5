package is.ru.graphics.motion;

import is.ru.graphics.math.Point3D;
import is.ru.graphics.math.Vector3D;

import java.util.ArrayList;

/**
 * Created by Kárii on 4.11.2016.
 */
public class BSplineMotion {
    ArrayList<BezierMotion> motions;
    private Point3D pStart;
    private Point3D pEnd;

    private float startTime;
    private float endTime;

    public BSplineMotion(ArrayList<Point3D> controlPoints, float startTime, float endTime){
        this.startTime = startTime;
        this.endTime = endTime;

        int motionCount = (controlPoints.size() / 2) - 1;
        float timePerMotion = (endTime - startTime) / (float) motionCount;

        motions = new ArrayList<BezierMotion>();

        if(controlPoints.size() < 4){
            motions = null;
            return;
        }
        Point3D P1 = controlPoints.get(0);
        Point3D P2 = controlPoints.get(1);
        Point3D P3 = controlPoints.get(2);
        Point3D P4 = controlPoints.get(3);


        BezierMotion motion = new BezierMotion(P1, P2, P3, P4, startTime, startTime + timePerMotion);
        motions.add(motion);

        pStart = P1;

        for(int i = 1; i < motionCount; i++){
            P1 = P4;
            P2 = P1;
            P2.add(Vector3D.difference(P4, P3));

            P3 = controlPoints.get((i+1)* 2);
            P4 = controlPoints.get((i+1)* 2 + 1);

            motion = new BezierMotion(P1, P2, P3, P4,
                    startTime + timePerMotion * i, startTime + timePerMotion * (i+1));
            motions.add(motion);
        }

        pEnd = P4;
    }

    public void getCurrentPoistion(float currentTime, Point3D out_postion){
        if(currentTime < startTime){
            out_postion.x = pStart.x;
            out_postion.y = pStart.y;
            out_postion.z = pStart.z;
        }
        else if(currentTime > endTime){
            out_postion.x = pEnd.x;
            out_postion.y = pEnd.y;
            out_postion.z = pEnd.z;
        }
        else {
            for(BezierMotion motion : motions){
                if(motion.getStartTime() < currentTime && currentTime < motion.getEndTime()){
                    motion.getCurrentPoistion(currentTime, out_postion);
                    break;
                }
            }
        }
    }
}
