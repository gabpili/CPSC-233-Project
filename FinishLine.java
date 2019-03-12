public class FinishLine extends Checkpoint{

    private int lastNumber;

	public FinishLine (double x, double y, String name, double x2, double y2, int number, int lastNumber) {
		super(x, y, name, x2, y2, number);
		this.lastNumber = lastNumber;

	}

	public FinishLine(FinishLine copy) {
		super(copy);
		copy.lastNumber = lastNumber;

	}

	@Override
	public void resolveCollision(DynamicObject dObj) {
		/*
		if (dObj instanceof Car) {
			Interface i = ((Car) dObj).getInterface();
			if (i.getSection() == lastNumber) {
				i.setSection(super.getNumber());
				i.setLap(i.getLap() + 1);

			}
		}*/
	}

}