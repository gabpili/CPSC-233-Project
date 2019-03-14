import java.lang.Math;

public class Wall extends BasicGameObject{
	/**
	* Instance Variables 
	*/
	private double x2;
	private double y2;

	/* Constructor
	* Constructor consists arguments: x, y, name, x2 and y2
	* x, y and name variables are extended from the BasicGameObject class using 'super' keyword
	* takes the square root of  the exponent power of (x2 - x) and adds it to exponent power of (y2-y) and divides the result by 2
	*/ 
	public Wall(double x, double y, String name, double x2, double y2){
		super(x, y, name, 0, Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2)) / 2);
		setX2(x2);
		setY2(y2);
	}
        /* Copy Constructor */
	public Wall(Wall copy){
		super(copy);
		double x2 = copy.x2;
		double y2 = copy.y2;
	}

       /* setter methods
       * sets x2 and y2 
       */
	public void setX2(double x2){
		this.x2 = x2;
	}
        public void setY2(double y2){
		this.y2 = y2;
        }
  
    /*getter methods 
    * Returns x2 and y2
    */
    public double getX2(){
        return x2;
    }
	public double getY2(){
		return y2;
	}

    @Override
    public void resolveCollision(DynamicGameObject dObj){
	}
}
