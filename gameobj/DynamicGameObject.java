package gameobj;

import java.lang.Math;

import base.Vector;
import base.Flag;

public abstract class DynamicGameObject extends BasicGameObject {
    // Instance Variables
    private Vector velocity = new Vector();
    private Vector direction =  new Vector(1, 0);

	/**
	 * Constructor takes in five arguments: x, y, name, half width and half height
	 * sets them using another constructor in this class.
	 */
    public DynamicGameObject(double x, double y, String name, double halfW, double halfH, double mass) {
        this(x, y, name, halfW, halfH, mass, 0, 0);

    }
    
	/**
	 * Constructor takes in seven arguments and uses methods within the class to set speed with given 
     * speed and direction with given direction as well as a constructor from the StaticObject class 
     * to set x, y, name, halfW and halfH. 
	 */
    public DynamicGameObject(double x, double y, String name, double halfW, double halfH, double mass, 
    	double speed, double direction) {
        super(x, y, name, halfW, halfH, mass);
        setDirection(direction);
        setSpeed(speed);

    }

    public DynamicGameObject(double x, double y, String name, double halfW, double halfH, double mass, 
    	Vector velocity, Vector direction) {
    	super(x, y, name, halfW, halfH, mass);
    	setVelocity(velocity);
    	setDirection(direction);

    }
    
	/**
     * copy constructor 
	 */
    public DynamicGameObject(DynamicGameObject toCopy) {
        super(toCopy);
        this.velocity = new Vector(toCopy.velocity);
        this.direction = new Vector(toCopy.direction);

    }

    public void setSpeed(double speed) {
    	if (velocity.normSqr() == 0) {
    		velocity = new Vector(direction).multiply(speed);
    		
    	}else {
    		velocity = velocity.normalize().multiply(speed);

    	}
    }

    public void setVelocity(double speed, double direction) {
    	this.velocity = new Vector(direction).multiply(speed);

    }

    public void setVelocity(Vector velocity) {
    	this.velocity = new Vector(velocity);

    }

    public void setDirection(double direction) {
    	this.direction = new Vector(direction);

    }

    public void setDirection(Vector direction) {
    	this.direction = new Vector(direction);

    }

    public double getSpeed() {
    	return velocity.norm();

    }

    public Vector getVelocity() {
    	return new Vector(velocity);

    }

    public Vector getDirection() {
    	return new Vector(direction);

    }

    /**
     * change position based on velocity components and time given
     */
    public void tick(double time) {
        setX(getX() + velocity.getI() * time);
        setY(getY() + velocity.getJ() * time);

    }

	/**
     * returns values into string format using the toString() method in StaticObject
     * and concatinating it with what was calculated for in this class such as speed and the 
     * degree of the direction. 
	 */
    public String toString() {
        return super.toString() + (int) velocity.norm() +  "m/s " + (int) Math.toDegrees(direction.theta()) + "deg";
        
    }
}
