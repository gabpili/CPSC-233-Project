package gameobj;

import base.Flag;

public class MisslePickup extends Pickup {
    public static final double COOLDOWN = 10.0;

	/**
	 * basic constructor with name initialized as "Missile Pickup"
	 */
    public MisslePickup(double x, double y) {
        super(x, y, "Missle Pickup");

    }

	/**
	 * default constructor with x, y set as 0s
	 */
    public MisslePickup() {
        this(0, 0);

    }

	/**
	 * copy constructor
	 */
    public MisslePickup(MisslePickup toCopy) {
        super(toCopy);

    }

	/**
	 * resolves collision by disabling the pickup and the colliding car picking up the missile
	 */
    @Override
    public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException {
        if (getActive()) {
            addFlag(new Flag(Flag.HandlingMethod.TIMED_DISABLE, COOLDOWN));
            dObj.addFlag(new Flag(Flag.HandlingMethod.PICKUP_MISSLE));

        }
    }
}
