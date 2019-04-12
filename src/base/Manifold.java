package base;

import java.lang.Math;

public final class Manifold {

	private final Vector pointOfContact;
    private final Vector pointOfPenetration;
	private final double penetrationDepth;
	private final Vector collisionNormal;
	private final Vector relativeVelocity; // relative to 
	public static final double staticFrictionCoefficient = 0.74; // steel on steel
	public static final double dynamicFrictionCoefficient = 0.57; // source: CRC Handbook of Physical Quantities.

	public Manifold(Vector pointC, Vector pointP, double depth, Vector normal, Vector vel) {
		this.pointOfContact = pointC;
        this.pointOfPenetration = pointP;
		this.penetrationDepth = depth;
		this.collisionNormal = normal;
		this.relativeVelocity = vel;

	}

	public Vector getPointOfContact() {
		return pointOfContact;

	}

    public Vector getPointOfPenetration() {
        return pointOfContact;

    }

	public double getPenetrationDepth() {
		return penetrationDepth;

	}

	public Vector getCollisionNormal() {
		return collisionNormal;

	}

	public Vector getRelativeVelocity() {
		return relativeVelocity;

	}

    @Override
    public String toString() {
        return "C: " + pointOfContact + " P: " + pointOfPenetration 
            + " depth: " + (int) penetrationDepth + " Normal: "  + collisionNormal.theta()
            + " relative velocity: " + (int) relativeVelocity.norm();

    }
}