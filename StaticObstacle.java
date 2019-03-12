public class StaticObstacle extends StaticObject{
	/**
	 * Constructor takes in five arguments: x, y, name, half width and half height
	 * sets them using a constructor from the StaticObject class.
	 */
	public StaticObstacle(double x, double y, String name, double halfW, double halfH){
		super(x, y, name, halfW, halfH);

	}
	/**
	 * flags this obstacle to be destroyed and flags the colliding dObj to change speed by -5
	 */
	@Override
	public void resolveCollision(DynamicObject dObj){
		super.addFlag(new Flag(Flag.HandlingMethod.DESTROY));
		dObj.addFlag(new Flag(-5, Flag.HandlingMethod.ADD_SPEED));

	}
}
