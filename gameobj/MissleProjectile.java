package gameobj;

import base.Flag;

public class MissleProjectile extends DynamicGameObject {

    public MissleProjectile(double x, double y, double direction) {
        this(x, y, 30, direction);

    }

    public MissleProjectile(double x, double y, double speed, double direction) {
        super(x, y, "Missle", 0.2, 0.8, 0, speed, direction);

    }

    public MissleProjectile(MissleProjectile toCopy) {
        super(toCopy);

    }

    @Override
    public void resolveCollision(DynamicGameObject o) throws IllegalArgumentException {
        addFlag(new Flag(Flag.HandlingMethod.DESTROY));
        addFlag(new Flag(Flag.HandlingMethod.EXPOLSION, new double[]{
        	4, 500
        }));

    }
}
