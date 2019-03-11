public class StaticObstacle extends StaticObject{
	public StaticObstacle(double x, double y, String name, double halfW, double halfH){
		super(x, y, name, halfW, halfH);
	}

@Override
			public void resolveCollision(DynamicObject dObj){
				super.addFlag(new Flag(Flag.HandlingMethod.DESTROY));
				System.out.println(super.getFlags().get(0).getHandlingMethod());

			}
}
