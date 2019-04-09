package base;

import java.util.ArrayList;
import java.lang.Math;

import gameobj.Car;
import gameobj.Pickup;
import gameobj.MisslePickup;
import gameobj.SpeedboostPickup;

public class Driver {
	/**
 	* Instance vairables
 	*/
	private Car attachedCar;
	private int section = 0;
	private int lap = 1;
	private Pickup item = null;

	/**
 	 * Construtor that sets given value of 'car' to attachedCar.
 	 */
	public Driver(Car car) {
		attachedCar = car;

	}

	/**
 	 * Set method sets given value of section to "this.section".
 	 */
	public void setSection(int section) {
	    this.section = section;

	}

	/**
 	 * Set method sets given value of lap to "this.lap".
 	 */
	public void setLap(int lap) {
		this.lap = lap;

	}

	public void setItem(Pickup item) {
		this.item = item;

	}

	public void setAttachedCar(Car attachedCar) {
		this.attachedCar = attachedCar;

	}

	/**
 	 * Returns the value of 'attachedCar'.
 	 */
	public Car getAttachedCar() {
		return attachedCar;

	}

	/**
 	 * Returns the value of 'section'.
 	 */
	public int getSection() {
		return section;

	}

	/**
 	 * Returns the value of 'lap'.
 	 */
	public int getLap() {
		return lap;

	}

	/**
	 * Calculation of speed is determined first by mulitpling 'base acceleration' of attachedCar
	 * by time and adding it to the 'speed' of attached car.
 	 */
	private void accelerate(double time) {
		attachedCar.accelerate();

	}

	/**
	 * Condition statements check if the velocity is greater than zero or
	 * if the velocity, v, is zero, then it's going the opposite direction.
	 * the velocity will then be the difference in 'base acceleration'
	 * multiplied by either 1.5 or 0.3. The speed is then set as the value of v.
 	 */
	private void brake(double time) {
		attachedCar.brake();

	}

	/**
	 * Sets 'angular velocity' to radians 5π/8 radians. Other condition will set
	 * to the opposite direction of -5π/8 radians.
	 */
	private void turn(int direction, double time) {
		if (direction == 0) {
			direction = (int)(-Math.signum(attachedCar.getTurnAngle()));
			double toTurn = direction * 1.5;

			if ((attachedCar.getTurnAngle() + toTurn * time) * -direction < 0) {
				attachedCar.setTurnAngularVelocity(attachedCar.getTurnAngularVelocity() - attachedCar.getTurnAngle() / time);

			}else {
				attachedCar.setTurnAngularVelocity(attachedCar.getTurnAngularVelocity() + toTurn);

			}
		}else {
			attachedCar.setTurnAngularVelocity(attachedCar.getTurnAngularVelocity() + direction * 1.5);

		}
	}

	/**
 	 * Method will take in a list of Characters called 'input' and time.
	 * If char 'W' is entered, 'acceleration' method. If char 'S' is entered,
	 * 'brake' method is called. If char 'A' is entered, 'turn' method is called
	 * and the attachedCar will turn. If char 'D' is entered, 'turn' method is called
	 * and a turn to the opposite side by degrees happens as well.
 	 */
	public void takeInput(ArrayList<Character> input, double time) {
		boolean turning = false;
		for (char c: input) {
			switch (c) {
				case ('W'):
					attachedCar.accelerate();
					break;
				case ('S'):
					attachedCar.brake();
					break;
				case ('A'):
					turn(-1, time);
					turning = true;
					break;
				case ('D'):
					turn(1, time);
					turning = true;
					break;
				case ('F'):
					useItem();
					break;
				case ('Z'):
					attachedCar.addForce(attachedCar.getDirection().rotateOrthogonalCCW().multiply(100000));
					break;
				case ('C'):
					attachedCar.addForce(attachedCar.getDirection().rotateOrthogonalCW().multiply(100000));
					break;
				case ('Q'):
					attachedCar.addAngularAcceleration(-50);
					break;
				case ('E'):
					attachedCar.addAngularAcceleration(50);
					break;

			}
		}

		if (!turning) {
			turn(0, time);

		}
	}

	public void useItem() {
		if(item instanceof MisslePickup) {
			Vector vec = attachedCar.getDirection().multiply(attachedCar.getHalfH() + 1.6);
			double[] values = new double[]{
				attachedCar.getX() + vec.getI(),
				attachedCar.getY() + vec.getJ(),
				attachedCar.getDirection().theta()
			};

			attachedCar.addFlag(new Flag(Flag.HandlingMethod.SPAWN_MISSLE, values));

			setItem(null);

		}
		else if(item instanceof SpeedboostPickup) {
			attachedCar.addForce(attachedCar.getDirection().multiply(SpeedboostPickup.SPEED * attachedCar.getMass() * 100));

			setItem(null);

		}
	}
}
