import java.lang.Math;
import java.util.ArrayList;

public abstract class StaticObject{
	private double x;
	private double y;
	private String name;
	private double halfW;
	private double halfH;
	private double maxR;
	private ArrayList<Flag> flags = new ArrayList<Flag>();

	public StaticObject(double x, double y, String name, double halfW, double halfH){
		setX(x);
		setY(y);
		this.name = name;
		this.halfW = halfW;
		this.halfH = halfH;
		maxR = Math.sqrt(halfW * halfW + halfH * halfH);

	}

	public StaticObject(StaticObject copy){
		double x = copy.x;
		double y = copy.y;
		String name = copy.name;
		double halfW = copy.halfW;
		double halfH = copy.halfH;

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

	public double getHalfW(){
		return halfW;
	}

	public double getHalfH(){
		return halfH;
	}

	public double getMaxR(){
		return maxR;
	}

	public void addFlag(Flag f){
		flags.add(f);
	}

	public ArrayList<Flag> getFlags(){
		return new ArrayList<Flag>(flags);
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

	abstract void resolveCollision(DynamicObject dObj);

}
