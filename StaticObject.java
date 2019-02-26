import java.lang.Math;

public abstract class StaticObject{
  private double x;
  private double y;
  private String name;

  public StaticObject(double x, double y, String name){
    setX(x);
    setY(y);
    this.name = name;
    
  }
  
  public StaticObject(StaticObject copy){
    double x = copy.x;
    double y = copy.y;
    String name = copy.name;
  }
  
  public void setX(double x){
    this.x = x;

  }

  public void setY(double y){
    this.y = y;

  }

  public double getX(){
    return x;

  }

  public double getY(){
    return y;

  }

  public String getName(){
    return name;
    
  }

  public double distance(StaticObject d){
    double dx = d.x - this.x;
    double dy = d.y - this.y;
    return Math.sqrt((dx * dx) + (dy * dy));
    
  }

  public double directionFrom(StaticObject d){
    double dx = this.x - d.x;
    double dy = this.y - d.y;
    return Math.atan2(dy, dx);
    
  }

  public String toString(){
    return name + " x:" + (int) x + "m y:" + (int) y +"m ";
    
  }
}
