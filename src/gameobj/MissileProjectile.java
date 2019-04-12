package gameobj;

import base.Flag;
import base.Manifold;

public class MissileProjectile extends DynamicGameObject {

	/**
	 * full constructor with halfW initialized as 0.2m and halfH initialized as 0.8m
	 */
    public MissileProjectile(double x, double y, double speed, double direction) {
        super(x, y, direction, "Missile", 0.2, 0.8, 0, speed);

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
    public void resolveCollision(DynamicGameObject dObj, Manifold manifold) throws IllegalArgumentException {
        addFlag(new Flag(Flag.HandlingMethod.DESTROY));
        addFlag(new Flag(Flag.HandlingMethod.EXPOLSION, new double[]{
        	15, 500
        }));

    }
}
