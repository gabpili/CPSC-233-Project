package base;

import java.lang.Math;

public final class Vector {
    private final double i;
    private final double j;

    public Vector(double i, double j) {
        this.i = i;
        this.j = j;

    }

    public Vector(double theta) {
    	this.i = Math.cos(theta);
    	this.j = Math.sin(theta);

    }

    public Vector() {
    	this(0, 0);
    	
    }

    public Vector(Vector vec) {
    	this.i = vec.i;
    	this.j = vec.j;

    }

    public double getI() {
    	return i;

    }

    public double getJ() {
    	return j;

    }

    public double norm() {
    	return Math.sqrt(i * i + j * j);

    }

    public double normSqr() {
    	return i * i + j * j;

    }

    public double theta() {
    	return Math.atan2(j, i);

    }

    public double distance(Vector vec) {
    	return Math.sqrt(distanceSqr(vec));

    }

    public double distanceSqr(Vector vec) {
    	return Math.pow(this.i - vec.i, 2) + Math.pow(this.j - vec.j, 2);

    }

    public double includedAngle(Vector vec) {
    	double angle = Math.acos(dot(vec) / (norm() * vec.norm()));
    	if (!((Double) angle).isNaN()) {
    		return angle;
    	}else {
    		return 0;
    	}
    	
    }

    public double dot(Vector vec) {
    	return i * vec.i + j * vec.j;

    }

    public Vector add(Vector vec) {
    	return new Vector(this.i + vec.i, this.j + vec.j);

    }

    public Vector multiply(Vector vec) {
    	return new Vector(this.i * vec.i, this.j * vec.j);

    }

    public Vector multiply(double c) {
    	return new Vector(i * c, j * c);

    }

    public Vector negate() {
    	return new Vector(-i, -j);

    }

    public Vector normalize() {
    	double r = norm();
    	if (r != 0) {
    		return new Vector(i / r, j / r);

    	}else {
    		return new Vector(i, j);
    		
    	}

    }

    public Vector projectionOf(Vector vec) {
    	return multiply(dot(vec) / normSqr());
    }

    public Vector rotateOrthogonalCW() {
    	return new Vector(-j, i);

    }

    public Vector rotateOrthogonalCCW() {
    	return new Vector(j, -i);

    }

    public Vector rotate(double theta) {
    	return new Vector(
    		i * Math.cos(theta) - j * Math.sin(theta), 
    		i * Math.sin(theta) + j * Math.cos(theta));

    }

    public boolean equals(Vector vec) {
    	return this.i == vec.i && this.j == vec.j;
    }

    public boolean orthogonalTo(Vector vec) {
    	return ((Double) dot(vec)).equals(0);
    }

    public String toString() {
    	return i + " " + j;
    }
}
