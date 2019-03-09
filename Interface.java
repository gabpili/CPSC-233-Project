import java.util.ArrayList;
import java.lang.Math;

public class Interface{
	private Car attachedCar;

	public Interface(Car car){
		attachedCar = car;
	}

	private void accelerate(double time){
		attachedCar.setSpeed(attachedCar.getSpeed() + attachedCar.getBaseAcceleration() * time);
	}

	private void brake(double time){
		double v = attachedCar.getSpeed();

		if(v > 0){
			v -= attachedCar.getBaseAcceleration() * time * 1.5;
		}else{
			v += attachedCar.getBaseAcceleration() * time * 0.6;
		}

		attachedCar.setSpeed(v);
	}

	private void turn(boolean clockwise, double time){
		if(clockwise){
			attachedCar.setAngularVelocity(Math.toRadians(50));
		}else{
			attachedCar.setAngularVelocity(Math.toRadians(-50));
		}
	}

	public void takeInput(ArrayList<Character> input, double time){
		attachedCar.setAngularVelocity(0);
		for(char c: input){
			switch(c){
				case('W'):
					accelerate(time);
					break;
				case('S'):
					brake(time);
					break;
				case('A'):
					turn(false, time);
					break;
				case('D'):
					turn(true, time);
					break;
			}
		}
	}
}
