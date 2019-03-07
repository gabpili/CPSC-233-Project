import java.lang.Math;

public class Car extends DynamicObject{
	private double baseAcceleration;
	private double baseFriction;
	private double angularVelocity = 0;

	public Car(double x, double y, String name, double halfW, double halfH, double direction, double baseAcceleration, double baseFriction){
		super(x, y, name, halfW, halfH, 0, direction);
		this.baseAcceleration = baseAcceleration;
		this.baseFriction = baseFriction;
	}

	public Car(Car toCopy){
		super(toCopy);
		this.baseAcceleration = toCopy.baseAcceleration;
		this.baseFriction = toCopy.baseFriction;
		this.angularVelocity = toCopy.angularVelocity;
	}

	public void setAngularVelocity(double angularVelocity){
		this.angularVelocity = angularVelocity;
	}

	public double getAngularVelocity(){
		return angularVelocity;
	}

	public double getBaseAcceleration(){
		return baseAcceleration;
	}

	public double getBaseFriction(){
		return baseFriction;
	}

	public void tickExtended(double time){
		super.setSpeed(super.getSpeed() - super.getSpeed() * 0.2 * time);
		super.setDirection(super.getDirection() + angularVelocity * time);
	}

@Override
			public void resolveCollision(DynamicObject dObj){
			}
}
