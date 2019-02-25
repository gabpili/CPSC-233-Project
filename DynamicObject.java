import java.lang.Math;

public abstract class DynamicObject extends StaticObject {
    /* Instance Variables */
    private double speed;
    private double direction;


    /*constructors*/
    public DynamicObject(double x, double y, String name){
        this(x, y, name, 0, 0);
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
        tickExtended(time);
    }

    abstract void tickExtended(double time);

    public String toString(){
        return super.toString() + " " + (int) speed +  " " + (int) Math.toDegrees(direction);
    }
}
