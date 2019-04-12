package gameobj;

import java.lang.Math;

import base.Manifold;
import base.Vector;

public class Car extends DynamicGameObject{

	// basic forward/backward
    private Vector brakeForce = new Vector();
	private final double engine;
	private final double brake;
	private final double drag; //0.4257
	private final double rollingResistance;

	// axle positions
	private final double frontToAxle;
	private final double backToAxle;
	private	final double centreToFrontAxle;
	private	final double centreToBackAxle;
	private final double axleDistance;

	// front wheel turning values
	private double turnAngularVelocity = 0;
	private double turnAngle = 0;
	private final double turnLimit;

	// high speed turning values; Pacejka tire model dependants
	private final double axleLoadFront;
	private final double axleLoadBack;
	private final double slipAngleThreshhold = 0.35;
	private final double corneringStiffnessFront;
	private final double corneringStiffnessBack;

	/**
	 * full constructor
	 */
	public Car(double x, double y, String name, double halfW, double halfH, double mass, double direction,
		double engine, double brake, double drag, double rollingResistance,
		double frontToAxle, double backToAxle,
		double turnLimit,
		double corneringStiffnessFront, double corneringStiffnessBack) {
		super(x, y, direction, name, halfW, halfH, mass, 0);

		this.engine = Math.abs(engine);
		this.brake = Math.abs(brake);
		this.drag = Math.abs(drag);
		this.rollingResistance = Math.abs(rollingResistance);

		this.frontToAxle = Math.abs(frontToAxle);
		this.backToAxle = Math.abs(backToAxle);
		this.centreToFrontAxle = halfH - frontToAxle;
		this.centreToBackAxle = halfH - backToAxle;
		this.axleDistance = centreToFrontAxle + centreToBackAxle;

		this.turnLimit = Math.abs(turnLimit);

		this.axleLoadFront = (getMass() * 9.81) * backToAxle / (frontToAxle + backToAxle);
		this.axleLoadBack = (getMass() * 9.81) * frontToAxle / (frontToAxle + backToAxle);
		this.corneringStiffnessFront = corneringStiffnessFront;
		this.corneringStiffnessBack = corneringStiffnessBack;

	}

    /**
     * constructor that excludes position
     */
	public Car(String name, double halfW, double halfH, double mass, 
		double engine, double brake, double drag, double rollingResistance,
		double frontToAxle, double backToAxle,
		double turnLimit,
		double corneringStiffnessFront, double corneringStiffnessBack) {
		this(0, 0, name, halfW, halfH, mass, 0, engine, brake, drag, rollingResistance,
			frontToAxle, backToAxle,
			turnLimit,
			corneringStiffnessFront, corneringStiffnessBack);

	}

	/**
	 * Copy Constructor
	 */
	public Car(Car toCopy) {
		super(toCopy);
		this.engine = toCopy.engine;
		this.brake = toCopy.brake;
		this.drag = toCopy.drag;
		this.rollingResistance = toCopy.rollingResistance;
		this.frontToAxle = toCopy.frontToAxle;
		this.backToAxle = toCopy.backToAxle;
		this.centreToFrontAxle = toCopy.centreToFrontAxle;
		this.centreToBackAxle = toCopy.centreToBackAxle;
		this.axleDistance = toCopy.axleDistance;
		this.turnLimit = toCopy.turnLimit;
		this.axleLoadFront = toCopy.axleLoadFront;
		this.axleLoadBack = toCopy.axleLoadBack;
		this.corneringStiffnessFront = toCopy.corneringStiffnessFront;
		this.corneringStiffnessBack = toCopy.corneringStiffnessBack;

	}

	/**
	 * sets angular velocity of the front wheels in rads/sec
	 */
	public void setTurnAngularVelocity(double omega) {
		this.turnAngularVelocity = omega;

	}

	/**
	 * sets angle of front wheels within the limits of turnLimit
	 */
	private void setFrontWheelAngle(double angle) {
		if (Math.abs(angle) < turnLimit) {
			turnAngle = angle;

		}else {
			turnAngle = turnLimit * Math.signum(angle);

		}
	}

	/**
	 * gets angular velocity of the front wheels in rads/sec
	 */
	public double getTurnAngularVelocity() {
		return turnAngularVelocity;

	}

	/**
	 * gets angle of the front wheels in rads
	 */
	public double getTurnAngle() {
		return turnAngle;

	}

	/**
	 * adds the force of the engine in the forward direction
	 */
	public void accelerate() {
		addForce(getDirection().multiply(engine));

	}

	/**
	 * sets the special brakeForce in the opposite direction of travel
	 */
	public void brake() {
		brakeForce = getDirection().projectionOf(getVelocity()).normalize().multiply(-brake);

	}

	/**
	 * simplified implementation of Pacejka's tire formula
	 * returns lateral force for a wheel given its slipAngle
	 * resource: https://en.wikipedia.org/wiki/Hans_B._Pacejka#The_Pacejka_%22Magic_Formula%22_tire_models
	 */
	private double calculatePacejkaForce(double axleLoad, double corneringStiffness, double slipAngle) {
		if (Math.abs(slipAngle) < slipAngleThreshhold + 0.01) {
			return slipAngle * corneringStiffness * axleLoad;

		}else {
			return slipAngleThreshhold * corneringStiffness * axleLoad * Math.signum(slipAngle);

		}

	}

	/**
	 * calculates forces and torque from velocity, direction, front wheel angle and mass
	 * calculates changes of velocity and angular velocity from forces and torque
	 */
	@Override
	public void tick(double time) {
		Vector v = getVelocity();
		Vector d = getDirection();
		Vector vParallel = d.projectionOf(v);
		Vector vLateral = vParallel.add(v.negate());
		Vector dLateral = d.rotateOrthogonalCW();

		// wheel turning (angularVelocity += ||vParallel|| * sin(delta)/L)
		setFrontWheelAngle(turnAngle + turnAngularVelocity * time);
		//angularVelocity += vParallel.norm() * Math.sin(turnAngle) / axleDistance;
		System.out.println(turnAngle);
		turnAngularVelocity = 0;

		// set variables of changed values
		d = getDirection();
		vParallel = d.projectionOf(v);

		/* cornering/lateral forces and torque -------------------------------------------------------------------
		approximates front wheels as one wheel and back wheels as one wheel
		resource: http://www.asawicki.info/Mirror/Car%20Physics%20for%20Games/Car%20Physics%20for%20Games.html
			  by: Marco Monster */
		double tangentialVelocityFront = getAngularVelocity() * centreToFrontAxle;
		double tangentialVelocityBack = -getAngularVelocity() * centreToBackAxle;

		// calculating slip angle
		double slipAngleFront;
		double slipAngleBack;
		if (v.dot(d) == 0) {
			slipAngleFront = Math.PI * Math.signum(v.dot(dLateral) + tangentialVelocityFront) - turnAngle * Math.signum(v.dot(d));
			slipAngleBack = Math.PI * Math.signum(v.dot(dLateral) + tangentialVelocityBack);

		}else {
			slipAngleFront = Math.atan((v.dot(dLateral) + tangentialVelocityFront) / Math.abs(v.dot(d))) - turnAngle * Math.signum(v.dot(d));
			slipAngleBack = Math.atan((v.dot(dLateral) + tangentialVelocityBack) / Math.abs(v.dot(d)));

		}

		// calculating lateral forces of each axle from slip angles
		double forceLateralFront = -calculatePacejkaForce(axleLoadFront, corneringStiffnessFront, slipAngleFront);
		double forceLateralBack = -calculatePacejkaForce(axleLoadBack, corneringStiffnessBack, slipAngleBack);

		// finally adds to net force and net torque
		addForce(dLateral.multiply(forceLateralFront).rotate(turnAngle).add(dLateral.multiply(forceLateralBack)));
		addAngularAcceleration((forceLateralFront * Math.cos(turnAngle) * centreToFrontAxle - forceLateralBack * centreToBackAxle) / getInertiaMoment());
		// ------------------------------------------------------------------------------------------------------

		// drag (-drag * v^2 + -rollingResistance * v)
		addForce(v.multiply(-drag * v.norm() - rollingResistance));

		// braking
		if (brakeForce.norm() / getMass() * time > vParallel.norm()) {
			brakeForce = vParallel.negate().multiply(getMass() / time);

		}
		addForce(brakeForce);

		// change position and reset forces
		super.tick(time);
        brakeForce = new Vector();

	}

	/**
	 * responds to collision by adding flags to objects
 	 * each physical object type responds differently
	 */
	@Override
	public void resolveCollision(DynamicGameObject dObj, Manifold manifold) throws IllegalArgumentException {

	}

     /**
     * returns values into string format using the toString() method
     * linking it with what was calculated in this class such as velocity of the car.
     */
	@Override
	public String toString() {
		return getName() + " " +  (int) (getVelocity().norm() * 3.6) + "km/h " + getNetForce();
	}
}
