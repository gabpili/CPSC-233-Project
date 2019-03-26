package gameobj;

import base.Flag;

public class StaticObstacle extends BasicGameObject{
	/**
	 * Constructor takes in five arguments: x, y, name, half width and half height
	 * sets them using a constructor from the BasicGameObject class.
	 */
	public StaticObstacle(double x, double y, String name, double halfW, double halfH, double mass) {
		super(x, y, name, halfW, halfH, mass);

	}
	/**
	 * flags this obstacle to be destroyed and flags the colliding dObj to change speed by -5
	 */
	@Override
	public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException {
		if (!(dObj instanceof MissleProjectile)) {
			if (dObj.getMass() / 20 > getMass()) {
				super.addFlag(new Flag(Flag.HandlingMethod.DESTROY));
				dObj.addFlag(new Flag(Flag.HandlingMethod.SET_SPEED, dObj.getSpeed() * 0.4));

			}else {
				dObj.addFlag(new Flag(Flag.HandlingMethod.SET_SPEED, dObj.getSpeed() * -0.5));
			}
		}
	}
}
