package gameobj;

import java.lang.Math;

import base.Vector;
import base.Flag;

public abstract class DynamicGameObject extends BasicGameObject {
    
    private Vector velocity = new Vector();
    private Vector direction = new Vector(1, 0);

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

    /**
     * full constructor with velocity as a vector of m/s components and direction as a normal vector
     */
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

    /**
     * sets magnitude of velocity in m/s
     */
    public void setSpeed(double speed) {
    	if (velocity.normSqr() == 0) {
    		velocity = new Vector(direction).multiply(speed);
    		
    	}else {
    		velocity = velocity.normalize().multiply(speed);

    	}
    }

    /**
     * sets magnitude and direction of velocity and does not change the object's direction
     */
    public void setVelocity(double speed, double direction) {
    	this.velocity = new Vector(direction).multiply(speed);

    }

    /**
     * sets velocity to given vector, components in m/s
     */
    public void setVelocity(Vector velocity) {
    	this.velocity = new Vector(velocity);

    }

    /**
     * sets direction to given direction in rads
     */
    public void setDirection(double direction) {
    	this.direction = new Vector(direction);

    }

    /**
     * sets direction to the normalized given vector
     */
    public void setDirection(Vector direction) {
    	this.direction = new Vector(direction).normalize();

    }

    /**
     * gets speed as a positive value in m/s
     */
    public double getSpeed() {
    	return velocity.norm();

    }

    /**
     * gets velocity as a vector, its components in m/s
     */
    public Vector getVelocity() {
    	return new Vector(velocity);

    }

    /**
     * gets direction as a normal vector
     */
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
