import java.lang.Math;

public class Wall extends BasicGameObject{
	/**
	 * Instance Variables 
	 */
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	
	/**
	 * Constructor consists arguments: x, y, name, x2 and y2
	 * values are set using a constructor in the BasicGameObject 
	 * and setter methods within this Wall class.
	 */ 
	public Wall(double startX, double startY, String name, double endX, double endY) {
		super(startX, startY, name, 0, Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)) / 2);
		setStartX(startX);
		setStartY(startY);
		setEndX(endX);
		setEndY(endY);
	}
	
    /**
	 * Copy Constructor 
	 */
	public Wall(Wall copy) {

		super(copy);
		startX = copy.startX;
		startY = copy.startY;
		endX = copy.endX;
		endY = copy.endY;
	}
	
    /** 
     * Sets given value of startX to this.startX
     */
	public void setStartX(double startX) {
		this.startX = startX;
	}
	
    /** 
     * Sets given value of startY to this.startY
     */
	public void setStartY(double startY) {
		this.startY = startY;
	}
	
    /** 
     * Sets methods sets given value of endX to this.endX
     */
	public void setEndX(double endX) {
		this.endX = endX;
	}
	
    /** 
     * Sets methods sets given value of endY to this.endY
     */
    public void setEndY(double endY) {
	this.endY = endY;
    }
	
    /**
     * Returns value of startX
     */
    public double getStartX() {
        return startX;
    }
	
    /**
     * Returns value of startY
     */
	public double getStartY() {
		return startY;
	}
    
    /**
     * Getter methods returns value of endX
     */
    public double getEndX() {
        return endX;
    }
	
    /**
     * Getter methods returns value of endY
     */
	public double getEndY() {
		return endY;
	}
	
    /**
	 * The method resolveCollision is overided from BasicGameObject
     * argument name is dObj of type DynamicGameObject
     */
	@Override
	public void resolveCollision(DynamicGameObject dObj){
	}
}
