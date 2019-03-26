package gameobj;

import base.Flag;

public class SpeedboostPickup extends Pickup {
    public static final double COOLDOWN = 5.0;
    public static final double SPEED = 20.0;

    public SpeedboostPickup(double x, double y) {
        super(x, y, "Speedboost Pickup");

    }

    public SpeedboostPickup() {
        this(0, 0);

    }

    public SpeedboostPickup(SpeedboostPickup toCopy) {
        super(toCopy);

    }

    @Override
    public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException {
    	if (getActive()) {
	        addFlag(new Flag(Flag.HandlingMethod.TIMED_DISABLE, COOLDOWN));
	        dObj.addFlag(new Flag(Flag.HandlingMethod.PICKUP_SPEEDBOOST));

    	}
    }
}
