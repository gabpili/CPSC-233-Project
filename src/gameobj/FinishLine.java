package gameobj;

import base.Flag;
import base.Manifold;

public class FinishLine extends Checkpoint{

    /**
     *Number of the last Checkpoint
     */
    private int lastNumber;

	/**
	 * Constructor takes in six arguments: x, y, name, x2, y2, lastNumber
	 *  x, y, name, x2 and y2 variables are extended from the CheckPoint class using 'super' keyword
	 */
	public FinishLine (double x, double y, String name, double x2, double y2, int lastNumber) {
		super(x, y, name, x2, y2, 0);
		this.lastNumber = lastNumber;

	}
    /**
	 *Copy Constructor
	 */
	public FinishLine(FinishLine copy) {
		super(copy);
		copy.lastNumber = lastNumber;

	}

    /**
     * adds a next lap flag to the car
     */
	@Override
	public void resolveCollision(DynamicGameObject dObj, Manifold manifold) throws IllegalArgumentException {
		dObj.addFlag(new Flag(Flag.HandlingMethod.NEXT_LAP, lastNumber));

	}
}
