package gameobj;

import java.lang.Math;
import java.util.ArrayList;

import base.Flag;
import base.Manifold;
import base.Vector;

public abstract class BasicGameObject {
	private double x;
	private double y;
    private Vector direction;
	private String name;
	private double halfW;
	private double halfH;
	private double maxR;
	private double mass;
	private ArrayList<Flag> flags = new ArrayList<Flag>();

    /**
     * Constructor takes in five arguments: x, y, name, half width and half height
     * sets them using another constructor in this class.
     */
    public BasicGameObject(double x, double y, String name, double halfW, double halfH, double mass) {
        this(x, y, 0, name, halfW, halfH, mass);

    }
    
    /**
     * Constructor takes in seven arguments and uses methods within the class to set speed with given 
     * speed and direction with given direction as well as a constructor from the StaticObject class 
     * to set x, y, name, halfW and halfH. 
     */
    public BasicGameObject(double x, double y, double direction, String name, double halfW, double halfH, double mass) {
        this(x, y, new Vector(direction), name, halfW, halfH, mass);

    }

	/**
 	 * Constructor that takes 5 arguments and sets the given values to the following
	 * variables. maximum raidus (maxR) is also initialized under this constructor.
 	 */
	public BasicGameObject(double x, double y, Vector direction, String name, double halfW, double halfH, double mass) {
		setX(x);
		setY(y);
        setDirection(direction);
		this.name = name;
		this.halfW = Math.abs(halfW);
		this.halfH = Math.abs(halfH);
		this.maxR = Math.sqrt(halfW * halfW + halfH * halfH);
		this.mass = Math.abs(mass);

	}

	/**
 	 * Copy constructor
 	 */
	public BasicGameObject(BasicGameObject copy) {
		this.x = copy.x;
		this.y = copy.y;
        this.direction = copy.direction;
		this.name = copy.name;
		this.halfW = copy.halfW;
		this.halfH = copy.halfH;
		this.maxR = copy.maxR;
		this.mass = copy.mass;

	}

	/**
 	 * Setter method sets given 'x' to the variable 'x'.
 	 */
	public void setX(double x) {
		this.x = x;

	}

	/**
 	 * Setter method sets given 'y' to the variable 'y'.
 	 */
	public void setY(double y) {
		this.y = y;

	}

    public void setDirection(double direction) {
        this.direction = new Vector(direction);

    }

    public void setDirection(Vector direction) {
        this.direction = new Vector(direction).normalize();

    }

	/**
 	 * Getter method returns the value of x.
 	 */
	public double getX() {
		return x;

	}

	/**
 	 * Getter method returns the value of y.
 	 */
	public double getY() {
		return y;

	}

    public Vector getDirection() {
        return direction;

    }

	/**
 	 * Getter method returns the name.
 	 */
	public String getName() {
		return name;

	}

	/**
 	 * Getter method returns halfW (half width).
 	 */
	public double getHalfW() {
		return halfW;

	}

	/**
 	 * Getter method returns halfH (half height).
 	 */
	public double getHalfH() {
		return halfH;

	}

	/**
 	 * Getter method returns the value of maxR (maximum radius).
 	 */
	public double getMaxR() {
		return maxR;

	}

	public double getMass() {
		return mass;

	}

	/**
 	 * Method adds Flag f to list of flags.
 	 */
	public void addFlag(Flag f) {
		flags.add(new Flag(f));

	}

	/**
	 * removes Flag f from list of flags
	 */
	public void removeFlag(Flag f) {
		ArrayList<Flag> copy = new ArrayList<Flag>(flags);
		for (Flag x: copy){
			if (x.equals(f)){
				flags.remove(x);

			}
		}
	}

	/**
 	 * Getter method returns list of flags
 	 */
	public ArrayList<Flag> getFlags() {
		ArrayList<Flag> copy = new ArrayList<Flag>();
		for (Flag x: flags){
			copy.add(new Flag(x));

		}
		return copy;

	}

	/**
 	 * returns distance between this object and other object d
 	 */
	public double distance(BasicGameObject d) {
		double dx = d.x - this.x;
		double dy = d.y - this.y;
		return Math.sqrt((dx * dx) + (dy * dy));

	}
	
	/**
 	 * Method returns the angle pointing from given object 'd' to 'this' object
 	 */
	public double directionFrom(BasicGameObject d) {
		double dx = this.x - d.x;
		double dy = this.y - d.y;
		return Math.atan2(dy, dx);

	}

	/**
 	 * respond to collision by adding flags to objects
 	 * each physical object type responds differently
 	 */
	public abstract void resolveCollision(DynamicGameObject dObj, Manifold manifold) throws IllegalArgumentException;

	/**
 	 * Method puts values in String format.
	 *
	 * example output: "Magic School Bus x: 1m y: 1m "
	 * type casting is used to convert x and y (which are doubles)
	 * as integers.
 	 */
	public String toString() {
		return name + " x:" + (int) x + "m y:" + (int) y +"m " + (int) Math.toDegrees(direction.theta()) + "deg ";

	}
}
