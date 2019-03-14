import java.lang.Math;

public class Wall extends BasicGameObject{
	private double startX;
	private double startY;
	private double endX;
	private double endY;

	public Wall(double startX, double startY, String name, double endX, double endY) {
		super(startX, startY, name, 0, Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)) / 2);
		setStartX(startX);
		setStartY(startY);
		setEndX(endX);
		setEndY(endY);
	}

	public Wall(Wall copy) {
		super(copy);
		startX = copy.startX;
		startY = copy.startY;
		endX = copy.endX;
		endY = copy.endY;
	}

	// setters
	public void setStartX(double startX) {
		this.startX = startX;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

	public void setEndX(double endX) {
		this.endX = endX;
	}

    public void setEndY(double endY) {
		this.endY = endY;
    }

	// getters
    public double getStartX() {
        return startX;
    }

	public double getStartY() {
		return startY;
	}

    public double getEndX() {
        return endX;
    }

	public double getEndY() {
		return endY;
	}

	@Override
	public void resolveCollision(DynamicGameObject dObj){
	}
}
