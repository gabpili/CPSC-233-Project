package gameobj;

import java.lang.Math;

import base.Flag;
import base.Manifold;
import base.Vector;

public abstract class DynamicGameObject extends BasicGameObject {
    // value to change position and angle given time
    private Vector velocity = new Vector();
    private double angularVelocity = 0;

    // values to change velocity and angular velocity given time
    private Vector netForce = new Vector();
    private double netAngularAcceleration = 0;
    private final double inertiaMoment;

	/**
	 * constructor that excludes direction
	 */
    public DynamicGameObject(double x, double y, String name, double halfW, double halfH, double mass) {
        this(x, y, 0, name, halfW, halfH, mass, 0);

    }

	/**
	 * full constructor with direction in radians and speed in m/s
	 */
    public DynamicGameObject(double x, double y, double direction, String name, double halfW, double halfH, double mass,
    	double speed) {
        super(x, y, direction, name, halfW, halfH, mass);
        setSpeed(speed);
        this.inertiaMoment = mass * (Math.pow(halfW * 2, 2) + Math.pow(halfH * 2, 2)) / 12;

    }

    /**
     * full constructor with direction and velocity as vectors
     */
    public DynamicGameObject(double x, double y, Vector direction, String name, double halfW, double halfH, double mass,
    	Vector velocity) {
    	super(x, y, direction, name, halfW, halfH, mass);
    	setVelocity(velocity);
        this.inertiaMoment = mass * (Math.pow(halfW * 2, 2) + Math.pow(halfH * 2, 2)) / 12;

    }

	/**
     * copy constructor
	 */
    public DynamicGameObject(DynamicGameObject toCopy) {
        super(toCopy);
        this.velocity = new Vector(toCopy.velocity);
        this.inertiaMoment = toCopy.inertiaMoment;

    }

    /**
     * Setter method sets magnitude of velocity to given speed
     * does not change the direction of velocity
     */
    public void setSpeed(double speed) {
    	if (velocity.normSqr() == 0) {
    		velocity = getDirection().multiply(speed);

    	}else {
    		velocity = velocity.normalize().multiply(speed);

    	}
    }

    /**
     * Setter method sets magnitude of velocity and direction of the object
     * does not change the direction of velocity
     */
    public void setVelocity(double speed, double direction) {
    	this.velocity = new Vector(direction).multiply(speed);

    }

    /**
     * Setter method sets velocity as vector
     */
    public void setVelocity(Vector velocity) {
    	this.velocity = new Vector(velocity);

    }

    /**
     * Setter method sets angular velocity in radians/s
     */
    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;

    }

    /**
     * Setter method sets net force as vector
     */
    public void setNetForce(Vector netForce) {
        this.netForce = netForce;

    }

    /**
     * Setter method sets net angular acceleration as radians/s^2
     */
    public void setNetAngularAcceleration(double netAngularAcceleration) {
        this.netAngularAcceleration = netAngularAcceleration;

    }

    /**
     * Getter method returns the value of velocity normal in m/s
     */
    public double getSpeed() {
    	return velocity.norm();

    }

    /**
     * Getter method returns the velocity vector of components in m/s
     */
    public Vector getVelocity() {
    	return new Vector(velocity);

    }

    /**
     * Getter method returns angularVelocity in radians/s
     */
    public double getAngularVelocity() {
        return angularVelocity;

    }

    /**
     * Getter method returns netForce vector of components in N
     */
    public Vector getNetForce() {
        return netForce;

    }

    /**
     * Getter method returns netAngularAcceleration in radians/s^2
     */
    public double getNetAngularAcceleration() {
        return netAngularAcceleration;

    }

    /**
     * Getter method returns the value of netAngularAcceleration
     */
    public double getInertiaMoment() {
        return inertiaMoment;

    }

    /**
     * adds given force vector to the netForce
     */
    public void addForce(Vector force) {
        netForce = netForce.add(force);

    }

    /**
     * adds given torque as Nm to the netAngularAcceleration
     */
    public void addTorque(double force, double radius) {
        netAngularAcceleration += force / getMass() / radius;

    }

    /**
     * adds given angularAcceleration to the netAngularAcceleration
     */
    public void addAngularAcceleration(double angularAcceleration) {
        netAngularAcceleration += angularAcceleration;

    }

    /**
     * change physical values given time
     */
    public void tick(double time) {
        // change angular velocity and direction
        setAngularVelocity(getAngularVelocity() + getNetAngularAcceleration() * time);
        setDirection(getDirection().rotate(angularVelocity * time));

        // change velocity using netForce
        velocity = velocity.add(netForce.multiply(time / getMass()));

        // change position using velocity
        setX(getX() + velocity.getI() * time);
        setY(getY() + velocity.getJ() * time);

        // reset values
        setNetAngularAcceleration(0);
        netForce = new Vector();

    }

	/**
     * returns values into string format using the toString() method in StaticObject
     * and concatinating it with what was calculated for in this class such as speed and the
     * degree of the direction.
	 */
    public String toString() {
        return super.toString() + (int) velocity.norm() +  "m/s ";

    }
}
