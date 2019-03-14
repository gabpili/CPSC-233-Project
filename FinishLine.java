public class FinishLine extends Checkpoint{

    private int lastNumber;

	public FinishLine (double x, double y, String name, double x2, double y2, int lastNumber) {
		super(x, y, name, x2, y2, 0);
		this.lastNumber = lastNumber;

	}

	public FinishLine(FinishLine copy) {
		super(copy);
		copy.lastNumber = lastNumber;

	}

	@Override
	public void resolveCollision(DynamicGameObject dObj) {
		dObj.addFlag(new Flag(lastNumber, Flag.HandlingMethod.NEXT_LAP));
	}

}
