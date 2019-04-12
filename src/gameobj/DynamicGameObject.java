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
	 * Constructor takes in five arguments: x, y, name, half width and half height
	 * sets them using another constructor in this class.
	 */
    public DynamicGameObject(double x, double y, String name, double halfW, double halfH, double mass) {
        this(x, y, 0, name, halfW, halfH, mass, 0);

    }

	/**
	 * Constructor takes in seven arguments and uses methods within the class to set speed with given
     * speed and direction with given direction as well as a constructor from the StaticObject class
     * to set x, y, name, halfW and halfH.
	 */
    public DynamicGameObject(double x, double y, double direction, String name, double halfW, double halfH, double mass,
    	double speed) {
        super(x, y, direction, name, halfW, halfH, mass);
        setSpeed(speed);
        this.inertiaMoment = mass * (Math.pow(halfW * 2, 2) + Math.pow(halfH * 2, 2)) / 12;

    }

    /**
     * Constructor takes seven arguments and uses type vector when given a direction and velocity
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
     * Setter method sets given 'speed' to variable 'velocity'
     */
    public void setSpeed(double speed) {
    	if (velocity.normSqr() == 0) {
    		velocity = getDirection().multiply(speed);

    	}else {
    		velocity = velocity.normalize().multiply(speed);

    	}
    }

    /**
     * Setter method sets given 'speed' and give 'direction' to the variable 'velocity'
     */
    public void setVelocity(double speed, double direction) {
    	this.velocity = new Vector(direction).multiply(speed);

    }

    /**
     * Setter method sets given 'velocity' to the variable 'velocity'
     */
    public void setVelocity(Vector velocity) {
    	this.velocity = new Vector(velocity);

    }

    /**
     * Setter method sets given 'angularVelocity' to the variable 'angularVelocity'
     */
    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;

    }

    /**
     * Setter method sets given 'netForce' to the variable 'netForce'
     */
    public void setNetForce(Vector netForce) {
        this.netForce = netForce;

    }

    /**
     * Setter method sets given 'netAngularAcceleration' to the variable 'netAngularAcceleration'
     */
    public void setNetAngularAcceleration(double netAngularAcceleration) {
        this.netAngularAcceleration = netAngularAcceleration;

    }

    /**
     * Getter method returns the value of velocity normal
     */
    public double getSpeed() {
    	return velocity.norm();

    }

    /**
     * Getter method returns the value of velocity
     */
    public Vector getVelocity() {
    	return new Vector(velocity);

    }

    /**
     * Getter method returns the value of angularVelocity
     */
    public double getAngularVelocity() {
        return angularVelocity;

    }

    /**
     * Getter method returns the value of netForce
     */
    public Vector getNetForce() {
        return netForce;

    }

    /**
     * Getter method returns the value of netAngularAcceleration
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
     * adds given force as a vector to the netForce
     */
    public void addForce(Vector force) {
        netForce = netForce.add(force);

    }

    /**
     * adds given torque as newton metres to the netAngularAcceleration
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
     * change position based on velocity components and time given
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
