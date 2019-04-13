package gameobj;

import base.Flag;
import base.Manifold;
import base.Vector;

public class StaticObstacle extends BasicGameObject{

	/**
	 * Constructor takes in five arguments: x, y, name, half width and half height
	 * sets them using a constructor from the BasicGameObject class.
	 */
	public StaticObstacle(double x, double y, String name, double halfW, double halfH, double mass) {
		super(x, y, name, halfW, halfH, mass);

	}
	
	/**
	 * moves dObj to not be colliding and destroys this obstacle if dObj had enough speed and mass
	 */
	@Override
	public void resolveCollision(DynamicGameObject dObj, Manifold manifold) throws IllegalArgumentException {
        Vector transform = manifold.getCollisionNormal().multiply(manifold.getPenetrationDepth());

        dObj.setX(dObj.getX() + transform.getI());
        dObj.setY(dObj.getY() + transform.getJ());

		if (Math.abs(dObj.getVelocity().dot(manifold.getCollisionNormal())) * dObj.getMass() / 80 > getMass()) {
			super.addFlag(new Flag(Flag.HandlingMethod.DESTROY));
			dObj.addForce(manifold.getCollisionNormal().multiply(dObj.getSpeed() * dObj.getMass() * 40));

		}else {
			dObj.addForce(manifold.getCollisionNormal().multiply(dObj.getSpeed() * dObj.getMass() * 20));

		}
	}
}
