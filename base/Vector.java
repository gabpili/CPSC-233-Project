package base;

import java.lang.Math;

/**
 * an immutable data type of a 2D vector
 * contains various operations needed for vector math
 */
public final class Vector {
    
    private final double i;
    private final double j;

    /**
     * constructor of vector components
     */
    public Vector(double i, double j) {
        this.i = i;
        this.j = j;

    }

    /**
     * constructor of direction, with length of 0
     */
    public Vector(double theta) {
    	this.i = Math.cos(theta);
    	this.j = Math.sin(theta);

    }

    /**
     * default constructor for a zero vector
     */
    public Vector() {
    	this(0, 0);

    }

    /**
     * copy constructor
     */
    public Vector(Vector vec) {
    	this.i = vec.i;
    	this.j = vec.j;

    }

    /**
     * gets i component
     */
    public double getI() {
    	return i;

    }

    /**
     * gets j component
     */
    public double getJ() {
    	return j;

    }

    /**
     * returns the norm
     */
    public double norm() {
    	return Math.sqrt(i * i + j * j);

    }

    /**
     * returns the norm squared
     */
    public double normSqr() {
    	return i * i + j * j;

    }

    /**
     * calculates the direction from i axis in rads
     */
    public double theta() {
    	return Math.atan2(j, i);

    }

    /**
     * calculates the norm of this - vec
     */
    public double distance(Vector vec) {
    	return Math.sqrt(distanceSqr(vec));

    }

    /**
     * calculates square of the norm of this - vec
     */
    public double distanceSqr(Vector vec) {
    	return Math.pow(this.i - vec.i, 2) + Math.pow(this.j - vec.j, 2);

    }

    /**
     * calculates the angle between this and vec
     */
    public double includedAngle(Vector vec) {
    	double angle = Math.acos(dot(vec) / (norm() * vec.norm()));
    	if (!((Double) angle).isNaN()) {
    		return angle;
    	}else {
    		return 0;
    	}

    }

    /**
     * calculates the dot product of this and vec
     */
    public double dot(Vector vec) {
    	return i * vec.i + j * vec.j;

    }

    /**
     * returns vec added to this vector
     */
    public Vector add(Vector vec) {
    	return new Vector(this.i + vec.i, this.j + vec.j);

    }

    /**
     * returns a vector with components of vec and this multiplied
     */
    public Vector multiply(Vector vec) {
    	return new Vector(this.i * vec.i, this.j * vec.j);

    }

    /**
     * returns this vector multiplied by a scalar
     */
    public Vector multiply(double c) {
    	return new Vector(i * c, j * c);

    }

    /**
     * returns the negation
     */
    public Vector negate() {
    	return new Vector(-i, -j);

    }

    /**
     * returns the normalized vector
     */
    public Vector normalize() {
    	double r = norm();
    	if (r != 0) {
    		return new Vector(i / r, j / r);

    	}else {
    		return new Vector(i, j);

    	}

    }

    /**
     * returns vec projected onto this
     */
    public Vector projectionOf(Vector vec) {
    	return multiply(dot(vec) / normSqr());
    }

    /**
     * returns this rotated CW (assuming j axis points down)
     */
    public Vector rotateOrthogonalCW() {
    	return new Vector(-j, i);

    }

    /**
     * returns this rotated CCW (assuming j axis points down)
     */
    public Vector rotateOrthogonalCCW() {
    	return new Vector(j, -i);

    }

    /**
     * returns this rotated by given rads
     */
    public Vector rotate(double theta) {
    	return new Vector(
    		i * Math.cos(theta) - j * Math.sin(theta),
    		i * Math.sin(theta) + j * Math.cos(theta));

    }

    /**
     * returns true if vec holds same components
     */
    public boolean equals(Vector vec) {
    	return this.i == vec.i && this.j == vec.j;
    }

    /**
     * returns true of vec is orthogonal
     */
    public boolean orthogonalTo(Vector vec) {
    	return ((Double) dot(vec)).equals(0);
    }

    /**
     * returns components in a string
     */
    public String toString() {
    	return i + " " + j;
    }
}
