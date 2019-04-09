package gameobj;

import base.Flag;

public class Checkpoint extends Wall{
	
	private int number;

	/**
	 * Constructor that takes in 6 arguments and sets given the startX, startY, name
	 * endX and endY values using a constructor in the Wall class and sets number given
	 * number value using method within this class.
	 */
	public Checkpoint (double x, double y, String name, double x2, double y2, int number) {
		super(x, y, name, x2, y2);
		setNumber(number);

	}

	/**
	 * Copy constructor.
	 */
	public Checkpoint(Checkpoint copy) {
		super(copy);
		this.number = copy.number;
		
	}

	/**
	 * Sets given number with number variable.
	 */
	public void setNumber(int number) {
		this.number = number;

	}

	/**
	 * Returns the value of the number.
	 */
	 public int getNumber() {
		return number;

	}

	/**
	 * Override method that takes in a DynamicGameObject and adds a
	 */
	@Override
	public void resolveCollision(DynamicGameObject dObj) {
		dObj.addFlag(new Flag(Flag.HandlingMethod.NEXT_SECTION, number));

	}
}
