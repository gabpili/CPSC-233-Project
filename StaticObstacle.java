public class StaticObstacle extends StaticObject{
	/**
	 * Constructor takes in five arguments: x, y, name, half width and half height
	 * sets them using a constructor from the StaticObject class.
	 */
	public StaticObstacle(double x, double y, String name, double halfW, double halfH){
		super(x, y, name, halfW, halfH);
	}
			/**
			 * Overriding method from StaticObject class that uses a constructor in the StaticObject
			 * 
			 * Prints out the list of flags
	 		 */
			@Override
			public void resolveCollision(DynamicObject dObj){
				super.addFlag(new Flag(Flag.HandlingMethod.DESTROY));
				System.out.println(super.getFlags().get(0).getHandlingMethod());

			}
}
