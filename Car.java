import java.lang.Math;

public class Car extends DynamicGameObject{
	/**
	* Instance variables
	*/
	private double baseAcceleration;
	private double baseFriction;
	private double angularVelocity = 0;
	
	/**
	* Constructor takes in x, y, direction, halfW, halfH, baseAcceleration 
	* and baseFriction of type doubles. Also a name of type String. Uses a constructor within the class 
	* to initialize the given values.
	*/
	public Car(double x, double y, String name, double direction, double halfW, double halfH, double baseAcceleration, double baseFriction){
		super(x, y, name, halfW, halfH, 0, direction);
		this.baseAcceleration = baseAcceleration;
		this.baseFriction = baseFriction;
	}
	/**
	* Copy Contructor.
	*/
	public Car(Car toCopy){
		super(toCopy);
		this.baseAcceleration = toCopy.baseAcceleration;
		this.baseFriction = toCopy.baseFriction;
		this.angularVelocity = toCopy.angularVelocity;
	}
	/**
	* Setter Method.
	* Sets a given value to angular velocity.
	*/
	public void setAngularVelocity(double angularVelocity){
		this.angularVelocity = angularVelocity;
	}
	/**
	* Getter Method.
	* Returns the value of angularVelocity.
	*/
	public double getAngularVelocity(){
		return angularVelocity;
	}
	/**
	* Getter Method.
	* Return the value of baseAcceleration.
	*/
	public double getBaseAcceleration(){
		return baseAcceleration;
	}
	/**
	* Getter Method.
	* Return the value of baseFriction.
	*/
	public double getBaseFriction(){
		return baseFriction;
	}
	/**
	*
	*/
	public void tickExtended(double time){
		super.setSpeed(super.getSpeed() - super.getSpeed() * baseFriction * time);
		super.setDirection(super.getDirection() + angularVelocity * time);
	}
	/*
	* Overides the resolveCollision method in DynamicGameObject.
	*/
	@Override
	public void resolveCollision(DynamicGameObject dObj) {

	}

}
