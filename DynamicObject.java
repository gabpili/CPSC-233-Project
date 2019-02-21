import java.lang.Math;

public class DynamicObject extends staticObject {

    /* Instance Variables */
    private double speed;
    private double direction;


    /*constructors*/

    public DynamicObject(double x, double y, String name){
        super(x);
        super(y);
        super(name);
        this.setSpeed(speed);
        this.setDirection(direction);
  }

    public DynamicObject(double x, double y, String name, double speed, double direction){
          super(x, y, name);
          setSpeed(speed);
          setDirection(direction);
    }

    public DynamicObject(DynamicObject toCopy){
          super(toCopy);
          this.speed = toCopy.speed;
          this.direction = toCopy.direction;
    }

    /* setters */

    public void setSpeed(double spd){
      this.speed = spd;
    }
    
    public void setDirection(double direct){
      this.direction = direct;
    }
    
    public void setXYVelocity(double xvelocity, double yvelocity){
        setSpeed(Math.sqrt(xvelocity * xvelocity + yvelocity * yvelocity);
        double newDirection = Math.atan2(yvelocity, xvelocity);
        if (newDirection >= 0){
          setDirection(newDirection);
        }
        else {
          setDirection(Math.PI * 2 + newDirection);  
        }
    }

    /*getters */
    public double getSpeed(){
        return speed;
    }

    public double getDirection(){
        return direction;
    }

    public double getXVelocity(){
        return Math.cos(direction) * speed;
    }
    public double getYVelocity(){
        return Math.sin(direction) * speed;
    }

    /* Methods */
    public void tick(double time){
        setX(getX() + getXVelocity() * time);
        setY(getY() + getYVelocity() * time);
    }

    public String toString(){
        return super.toString() + " " + speed +  " " + direction;
    }
}
