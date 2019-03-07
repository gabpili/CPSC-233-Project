public class FinishLine extends Checkpoint{

		public FinishLine (int number, double x2, double y2, String name, double halfW, double halfH){
				super(x2, y2, name, halfW, halfH);
		}

		public FinishLine(FinishLine copy){
			super(copy);
		}

@Override
		public void resolveCollision(DynamicObject){
		}
}
