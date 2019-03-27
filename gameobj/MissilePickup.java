package gameobj;

import base.Flag;

public class MissilePickup extends Pickup {
    public static final double COOLDOWN = 10.0;

	/**
	 * basic constructor with name initialized as "Missile Pickup"
	 */
    public MissilePickup(double x, double y) {
        super(x, y, "Missile Pickup");

    }

	/**
	 * default constructor with x, y set as 0s
	 */
    public MissilePickup() {
        this(0, 0);

    }

	/**
	 * copy constructor
	 */
    public MissilePickup(MissilePickup toCopy) {
        super(toCopy);

    }

	/**
	 * resolves collision by disabling the pickup and the colliding car picking up the missile
	 */
    @Override
    public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException {
        if (getActive()) {
            addFlag(new Flag(Flag.HandlingMethod.TIMED_DISABLE, COOLDOWN));
            dObj.addFlag(new Flag(Flag.HandlingMethod.PICKUP_MISSILE));

        }
    }
}
