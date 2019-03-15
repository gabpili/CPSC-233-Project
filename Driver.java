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
	 * Calculation of speed is determined first by mulitpling 'base acceleration' of attachedCar
	 * by time and adding it to the 'speed' of attached car.
 	 */
	private void accelerate(double time) {
		attachedCar.setSpeed(attachedCar.getSpeed() + attachedCar.getBaseAcceleration() * time);
	}
	
	/**
	 * Condition statements check if the velocity is greater than zero or 
	 * if the velocity, v, is zero, then it's going the opposite direction.
	 * the velocity will then be the difference in 'base acceleration' 
	 * multiplied by either 1.5 or 0.3. The speed is then set as the value of v. 
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
 	 * Sets 'angular velocity' to radians 5π/8 radians. Other condition will set 
	 * to the opposite direction of -5π/8 radians.
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
 	 * Method will take in a list of Characters called 'input' and time.
	 * If char 'W' is entered, 'acceleration' method. If char 'S' is entered, 
	 * 'brake' method is called. If char 'A' is entered, 'turn' method is called 
	 * and the attachedCar will turn. If char 'D' is entered, 'turn' method is called 
	 * and a turn to the opposite side by degrees happens as well.
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
