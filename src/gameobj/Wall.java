package gameobj;

import java.lang.Math;

import base.Flag;
import base.Manifold;
import base.Vector;

public class Wall extends BasicGameObject{

	private double startX;
	private double startY;
	private double endX;
	private double endY;

	/**
	 * Constructor consists arguments: x, y, name, x2 and y2
	 * values are set using a constructor in the BasicGameObject
	 * and setter methods within this Wall class.
	 */
	public Wall(double startX, double startY, String name, double endX, double endY) {
		super(startX, startY, name, 0, Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)) / 2, 0);
		setStartX(startX);
		setStartY(startY);
		setEndX(endX);
		setEndY(endY);
	}

    /**
	 * Copy Constructor
	 */
	public Wall(Wall copy) {

		super(copy);
		startX = copy.startX;
		startY = copy.startY;
		endX = copy.endX;
		endY = copy.endY;
	}

    /**
     * Sets given value of startX to this.startX
     */
	public void setStartX(double startX) {
		this.startX = startX;
	}

    /**
     * Sets given value of startY to this.startY
     */
	public void setStartY(double startY) {
		this.startY = startY;
	}

    /**
     * Sets methods sets given value of endX to this.endX
     */
	public void setEndX(double endX) {
		this.endX = endX;
	}

    /**
     * Sets methods sets given value of endY to this.endY
     */
    public void setEndY(double endY) {
		this.endY = endY;
    }

    /**
     * Returns value of startX
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Returns value of startY
     */
	public double getStartY() {
		return startY;
	}

    /**
     * Getter methods returns value of endX
     */
    public double getEndX() {
        return endX;
    }

    /**
     * Getter methods returns value of endY
     */
	public double getEndY() {
		return endY;
	}

	/**
	 *
	 */
	@Override
	public double distance(BasicGameObject o) {
        Vector wallStart = new Vector(startX, startY);
        Vector wallEnd = new Vector(endX, endY);
        Vector wallLength = wallEnd.add(wallStart.negate());

        Vector pointA = new Vector(o.getX(), o.getY());

        Vector wallStartToClosestPoint = wallLength.projectionOf(pointA.add(wallStart.negate()));
        Vector closestPoint = wallStart.add(wallStartToClosestPoint);

        if (Double.valueOf(wallLength.norm()).equals(
            Double.valueOf(closestPoint.add(wallStart.negate()).norm() + closestPoint.add(wallEnd.negate()).norm()))) {
            // Case : point A projected onto wall is between endpoints
            return pointA.add(closestPoint.negate()).norm();

        }else {
            // Case : point A projected onto wall is beyond endpoints
            Vector endpoint;
            if (pointA.add(wallStart.negate()).norm() < pointA.add(wallEnd.negate()).norm()) {
                endpoint = wallStart;

            }else {
                endpoint = wallEnd;

            }

            return pointA.add(endpoint.negate()).norm();

        }
	}

    /**
	 * The method resolveCollision is overidden from BasicGameObject
     * argument name is dObj of type DynamicGameObject
     */
	@Override
	public void resolveCollision(DynamicGameObject dObj, Manifold manifold) throws IllegalArgumentException {
		/* get nearby walls of car, then,
		if car has hit corner then find point of collision from nearest wall point, bouncing or rotating back
		if a wall is close enough to force a pivot then rotate*/
        Vector transform = manifold.getCollisionNormal().multiply(manifold.getPenetrationDepth());

        dObj.setX(dObj.getX() + transform.getI());
        dObj.setY(dObj.getY() + transform.getJ());

        dObj.setVelocity(dObj.getVelocity().add(manifold.getCollisionNormal().projectionOf(dObj.getVelocity()).negate()));

	}
}
