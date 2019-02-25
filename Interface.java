import java.util.ArrayList;
import java.lang.Math;

public class Interface{
	private Car attachedCar;

	public Interface(Car car){
		attachedCar = car;
	}

	private void accelerate(double time){
		double speed = attachedCar.getSpeed();

		if(speed > 0){
			double a = attachedCar.getBaseAcceleration() * time;
		}else{
			double a = attachedCar.getBaseAcceleration() * time * -1;
		}

		attachedCar.setSpeed(speed + a);
	}

	private void brake(double time){
		double speed = attachedCar.getSpeed(); 
		
		attachedCar.setSpeed(speed - speed * getBaseFriction() * time);
	}

	private void turn(boolean clockwise, double time){
		if(clockwise){
			attachedCar.setAngularVelocity(Math.toRadians(10));
		}else{
			attachedCar.setAngularVelocity(Math.toRadians(-10));
		}
	}

	public void takeInput(ArrayList<Character> input, double time){
		for(char c: input){
			switch(c){
				case('w'):
					accelerate(time);
					break;
				case('s'):
					brake(time);
					break;
				case('a'):
					turn(false, time);
					break;
				case('d'):
					turn(true, time);
					break;
			}
		}
	}
}