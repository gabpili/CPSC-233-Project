import java.lang.Math;

public abstract class DynamicObject extends StaticObject {
    /**
     * Instance Variables 
     */
    private double speed;
    private double direction;

	/**
	 * Constructor takes in five arguments: x, y, name, half width and half height
	 * sets them using another constructor in this class.
	 */
    public DynamicObject(double x, double y, String name, double halfW, double halfH){
        this(x, y, name, halfW, halfH, 0, 0);
    }
    
	/**
	 * Constructor takes in seven arguments and uses methods within the class to set speed with given 
     * speed and direction with given direction as well as a constructor from the StaticObject class 
     * to set x, y, name, halfW and halfH. 
	 */
    public DynamicObject(double x, double y, String name, double halfW, double halfH, double speed, double direction){
        super(x, y, name, halfW, halfH);
        setSpeed(speed);
        setDirection(direction);
    }
    
	/**
     * Copy constructor 
	 */
    public DynamicObject(DynamicObject toCopy){
        super(toCopy);
        this.speed = toCopy.speed;
        this.direction = toCopy.direction;
    }

	/**
     * Set given speed with 'this' speed.
	 */
    public void setSpeed(double speed){
        this.speed = speed;
    }

    public void setDirection(double direction){
        while(direction < -Math.PI){
            direction += Math.PI * 2;
        }
        while(direction >= Math.PI){
            direction -= Math.PI * 2;
        }

        this.direction = direction;
    }

    public void setXYVelocity(double xvelocity, double yvelocity){
        setSpeed(Math.sqrt(xvelocity * xvelocity + yvelocity * yvelocity));
        double newDirection = Math.atan2(yvelocity, xvelocity);
        setDirection(newDirection);
    }

	/**
     * returns value of speed
	 */
    public double getSpeed(){
        return speed;
    }
    
	/**
     * returns value of direction
	 */
    public double getDirection(){
        return direction;
    }
    
	/**
     * returns the 'x' velocity using cosine formula
	 */
    public double getXVelocity(){
        return Math.cos(direction) * speed;
    }
    
	/**
     * returns the 'y' velocity using sine formula
	 */
    public double getYVelocity(){
        return Math.sin(direction) * speed;
    }

    public void tick(double time){
        setX(getX() + getXVelocity() * time);
        setY(getY() + getYVelocity() * time);
        tickExtended(time);
    }
    
	/**
     * declaration of an abstract method that takes in one argument of type double
     * which will be considered as 'time'.
	 */
    abstract void tickExtended(double time);

	/**
     * returns values into string format using the toString() method in StaticObject
     * and concatinating it with what was calculated for in this class such as speed and the 
     * degree of the direction. 
	 */
    public String toString(){
        return super.toString() + (int) speed +  "m/s " + (int) Math.toDegrees(direction) + "deg";
    }
}
