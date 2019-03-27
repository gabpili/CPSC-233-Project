package gameobj;

import java.lang.Math;

import base.Vector;

public class Car extends DynamicGameObject{	
	// values to change velocity and angle given time
	private Vector netForce = new Vector();
	private Vector brakeForce = new Vector();
	private double netTorque = 0;
	private double angularAcceleration = 0;
	private double angularVelocity = 0;

	// basic forward/backward
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
	private final double wheelLoad;
	private final double corneringStiffness;
	private final double slipAngleThreshhold = 0.35;
	
	/**
	 * full constructor
	 */
	public Car(double x, double y, String name, double halfW, double halfH, double mass, double direction, 
		double engine, double brake, double drag, double rollingResistance, 
		double frontToAxle, double backToAxle, double turnLimit) {
		super(x, y, name, halfW, halfH, mass, 0, direction);

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

		this.wheelLoad = getMass() / 2 * 9.81;
		this.corneringStiffness = wheelLoad * 2.2 / slipAngleThreshhold;

	}

	public Car(String name, double halfW, double halfH, double mass, 
		double engine, double brake, double drag, double rollingResistance, 
		double frontToAxle, double backToAxle, double turnLimit) {
		this(0, 0, name, halfW, halfH, mass, 0, engine, brake, drag, rollingResistance,
			frontToAxle, backToAxle, turnLimit);

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
		this.wheelLoad = toCopy.wheelLoad;
		this.corneringStiffness = toCopy.corneringStiffness;

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
	 * adds given force as a vector to the netForce
	 */
	public void addForce(Vector force) {
		netForce = netForce.add(force);

	}

	/**
	 * adds given torque as newton metres to the netTorque
	 */
	public void addTorque(double torque) {
		netTorque += torque;

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
	private double calculatePacejkaForce(double slipAngle) {
		if (Math.abs(slipAngle) < slipAngleThreshhold) {
			return corneringStiffness * slipAngle;

		}else {
			return wheelLoad * 2.2 * Math.signum(slipAngle) + 1 / slipAngle; 

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
		angularVelocity = v.norm() * Math.sin(turnAngle) / axleDistance;

		// change angular velocity and direction
		angularVelocity += netTorque / getMass() * time;
		setDirection(d.rotate(angularVelocity * time));

		// reset angular values
		netTorque = 0;
		angularAcceleration = 0;
		turnAngularVelocity = 0;

		// set variables of changed values
		d = getDirection();
		vParallel = d.projectionOf(v);

		/* cornering/lateral force and torque
		approximates front wheels as one wheel and back wheels as one wheel
		resource: http://www.asawicki.info/Mirror/Car%20Physics%20for%20Games/Car%20Physics%20for%20Games.html
			  by: Marco Monster */
		if (v.norm() != 0) {
			Vector tangentialVelocity = dLateral.multiply(-angularVelocity * centreToFrontAxle);

			// calculating slip angle
			double slipAngleFront = d.includedAngle(v.add(tangentialVelocity)) 
				* Math.signum(dLateral.dot(vLateral.add(tangentialVelocity))) 
				+ turnAngle * Math.signum(d.dot(vParallel));
			double slipAngleBack = d.includedAngle(v.add(tangentialVelocity.negate())) 
				* Math.signum(dLateral.dot(vLateral.add(tangentialVelocity.negate())));

			// calculating lateral forces of each axle from slip angles
			double forceLateralFront = calculatePacejkaForce(slipAngleFront) * Math.cos(turnAngle);
			double forceLateralBack = calculatePacejkaForce(slipAngleBack);

			// net lateral force as a vector
			Vector fLateral = dLateral.multiply(forceLateralFront + forceLateralBack);

			// finally adds to netForce and netTorque
			addForce(fLateral);
			netTorque += forceLateralFront / Math.pow(centreToFrontAxle, 2)
				- forceLateralBack / Math.pow(centreToBackAxle, 2);

		}

		// drag (-drag * v^2 + -rollingResistance * v)
		addForce(v.multiply(-drag * v.norm() - rollingResistance));

		// braking
		if (brakeForce.norm() / getMass() * time > vParallel.norm()) {
			brakeForce = vParallel.negate().multiply(getMass() / time);

		}
		addForce(brakeForce);

		// change velocity using netForce
		setVelocity(v.add(netForce.multiply(time / getMass())));

		// change position and reset forces
		super.tick(time);
		netForce = new Vector();
		brakeForce = new Vector();

	}

	/**
	 * responds to collision by adding flags to objects
 	 * each physical object type responds differently
	 */
	@Override
	public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException {

	}
	
     /**
     * returns values into string format using the toString() method
     * linking it with what was calculated in this class such as velocity of the car.
     */
	@Override
	public String toString() {
		return getName() + " " +  (int) (getVelocity().norm() * 3.6) + "km/h ";
	}
}
