package gameobj;

import base.Flag;
import base.Manifold;

public class SpeedboostTile extends BasicGameObject{

    /**
     * full constructor with halfW initialized to 1.5m and halfH initialized to 2m
     */
    public SpeedboostTile(double x, double y, double direction){
        super(x, y, direction, "Speedboost", 1.5, 2, 0);
    }

    /**
     * copy constructor
     */
    public SpeedboostTile(SpeedboostTile toCopy){
        super(toCopy);
    }

    /**
     * resolve collison by adding speed to the car 
     */
    @Override
    public void resolveCollision(DynamicGameObject dObj, Manifold manifold) throws IllegalArgumentException {
        dObj.addFlag(new Flag(Flag.HandlingMethod.ADD_SPEED, 2));

    }
}
