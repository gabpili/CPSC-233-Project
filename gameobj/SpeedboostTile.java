package gameobj;

import base.Flag;

public class SpeedboostTile extends DynamicGameObject{

    /**
     * full constructor with halfW initialized to 1.5m and halfH initialized to 2m
     */
    public SpeedboostTile(double x, double y, double direction){
        super(x, y, "Speedboost", 1.5, 2, 0, 0, direction);
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
    public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException {
        dObj.addFlag(new Flag(Flag.HandlingMethod.ADD_SPEED, 2));

    }
}
