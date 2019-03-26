package gameobj;

import base.Flag;

public class MisslePickup extends Pickup {
    public static final double COOLDOWN = 10.0;

    public MisslePickup(double x, double y) {
        super(x, y, "Missle Pickup");

    }

    public MisslePickup() {
        this(0, 0);

    }

    public MisslePickup(MisslePickup toCopy) {
        super(toCopy);

    }

    @Override
    public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException {
        if (getActive()) {
            addFlag(new Flag(Flag.HandlingMethod.TIMED_DISABLE, COOLDOWN));
            dObj.addFlag(new Flag(Flag.HandlingMethod.PICKUP_MISSLE));

        }
    }
}
