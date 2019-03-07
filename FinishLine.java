public class FinishLine extends Checkpoint{

	public FinishLine (double x, double y, String name, double x2, double y2, int number){
			super(x, y, name, x2, y2, number);
	}

		public FinishLine(FinishLine copy){
			super(copy);
		}

@Override
		public void resolveCollision(DynamicObject dObj){
		}
	}
