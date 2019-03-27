package gameobj;

import base.Flag;

public class SpeedboostPickup extends Pickup {
    public static final double COOLDOWN = 5.0;
    public static final double SPEED = 20.0;

	/**
	 * basic constructor with name initialized as "Speedboost Pickup"
	 */
    public SpeedboostPickup(double x, double y) {
        super(x, y, "Speedboost Pickup");

    }

	/**
	 * default constructor with x, y initialized as 0s
	 */
    public SpeedboostPickup() {
        this(0, 0);

    }

	/**
	 * copy constructor
	 */
    public SpeedboostPickup(SpeedboostPickup toCopy) {
        super(toCopy);

    }

	/**
	 * resolves collision by disabling the pickup and the colliding car picking up the speed boost
	 */
    @Override
    public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException {
    	if (getActive()) {
	        addFlag(new Flag(Flag.HandlingMethod.TIMED_DISABLE, COOLDOWN));
	        dObj.addFlag(new Flag(Flag.HandlingMethod.PICKUP_SPEEDBOOST));

    	}
    }
}
