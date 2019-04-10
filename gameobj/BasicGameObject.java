package gameobj;

import java.lang.Math;
import java.util.ArrayList;

import base.Flag;

public abstract class BasicGameObject {
	private double x;
	private double y;
	private String name;
	private double halfW;
	private double halfH;
	private double maxR;
	private double mass;
	private ArrayList<Flag> flags = new ArrayList<Flag>();

	/**
 	 * Constructor that takes 5 arguments and sets the given values to the following
	 * variables. maximum raidus (maxR) is also initialized under this constructor.
 	 */
	public BasicGameObject(double x, double y, String name, double halfW, double halfH, double mass) {
		setX(x);
		setY(y);
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
	
	/**
	 * Getter method returns the mass.
	 */
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
	public abstract void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException;

	/**
 	 * Method puts values in String format.
	 *
	 * example output: "Magic School Bus x: 1m y: 1m "
	 * type casting is used to convert x and y (which are doubles)
	 * as integers.
 	 */
	public String toString() {
		return name + " x:" + (int) x + "m y:" + (int) y +"m ";

	}
}
