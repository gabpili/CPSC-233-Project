import java.lang.Math;

public class Car extends DynamicObject{
	private double baseAcceleration;
	private double baseFriction;
	private double angularVelocity;

	public Car(double x, double y, String name, double direction, double baseAcceleration, double baseFriction){
		super(x,y,name,speed,direction);

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

	public void tick(double time){
		super.tick(time);
		super.setDirection(super.getDirection() + angularVelocity);
	}

}