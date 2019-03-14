import java.util.ArrayList;
import java.lang.Math;


public class Driver {
	/**
 	* Instance vairables
 	*/
	private Car attachedCar;
	private int section = 0;
	private int lap = 1;

	/**
 	 * Construtor that sets given value of 'car' to attachedCar.
 	 */
	public Driver(Car car) {
		attachedCar = car;
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
 	 * Returns the value of 'attachedCar'.
 	 */
	public Car getAttachedCar() {
		return attachedCar;
	}
	
	/**
 	 * 
 	 */
	private void accelerate(double time) {
		attachedCar.setSpeed(attachedCar.getSpeed() + attachedCar.getBaseAcceleration() * time);
	}
	
	/**
 	 * 
 	 */
	private void brake(double time) {
		double v = attachedCar.getSpeed();
		
		if (v > 0) {
			v -= attachedCar.getBaseAcceleration() * time * 1.5;
		}
		
		else {
			v -= attachedCar.getBaseAcceleration() * time * 0.3;
		}

		attachedCar.setSpeed(v);
	}
	
	/**
 	 *
 	 */
	private void turn(boolean clockwise, double time) {
		if (clockwise) {
			attachedCar.setAngularVelocity(Math.toRadians(50));
		}
		
		else {
			attachedCar.setAngularVelocity(Math.toRadians(-50));
		}
	}
	
	/**
 	 * 
 	 */
	public void takeInput(ArrayList<Character> input, double time) {
		attachedCar.setAngularVelocity(0);
		for (char c: input) {
			switch (c) {
				case ('W'):
					accelerate(time);
					break;
				case ('S'):
					brake(time);
					break;
				case ('A'):
					turn(false, time);
					break;
				case ('D'):
					turn(true, time);
					break;
			}
		}
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
}
