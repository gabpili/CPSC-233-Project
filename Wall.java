import java.lang.Math;

public class Wall extends StaticObject{
	private double x2;
	private double y2;

	public Wall(double x, double y, String name, double x2, double y2){
		super(x, y, name, 0, Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2)) / 2);
		setX2(x2);
		setY2(y2);
	}

	public Wall(Wall copy){
		super(copy);
		double x2 = copy.x2;
		double y2 = copy.y2;
	}

		/* setters */
	public void setX2(double x2){
		this.x2 = x2;
	}
    public void setY2(double y2){
		this.y2 = y2;
    }

	 /*getters */
    public double getX2(){
        return x2;
    }
	public double getY2(){
		return y2;
	}

	@Override
	public void resolveCollision(DynamicObject dObj){
	}
}
