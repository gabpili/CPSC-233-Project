package gameobj;

import base.Flag;

public class MissileProjectile extends DynamicGameObject {

	/**
	 * full constructor with halfW initialized as 0.2m and halfH initialized as 0.8m
	 */
    public MissileProjectile(double x, double y, double speed, double direction) {
        super(x, y, "Missile", 0.2, 0.8, 0, speed, direction);

    }

	/**
	 * constructor with speed initialized as 30m/s
	 */
    public MissileProjectile(double x, double y, double direction) {
        this(x, y, 30, direction);

    }

	/**
	 * copy constructor
	 */
    public MissileProjectile(MissileProjectile toCopy) {
        super(toCopy);

    }

	/**
	 * resolves collision by destroying itself and causing an explosion
	 */
    @Override
    public void resolveCollision(DynamicGameObject o) throws IllegalArgumentException {
        addFlag(new Flag(Flag.HandlingMethod.DESTROY));
        addFlag(new Flag(Flag.HandlingMethod.EXPOLSION, new double[]{
        	15, 500
        }));

    }
}
