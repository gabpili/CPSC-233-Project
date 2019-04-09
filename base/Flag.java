package base;

import java.util.Arrays;

/**
 * used to signal a requested change of an object that holds the flag
 * flags are handled and objects are affected in Map before DynamicGameObjects are ticked
 * each flag is defined based on the handling method and the values needed to handle the flag
 */
public final class Flag{
	/**
	 * determines type of flag and how it should be handled
	 */
    private HandlingMethod method;

    /**
     * generic variables used by some flag handling methods
     */
    private double[] values = new double[1];

    public static enum HandlingMethod {
        DESTROY(0),
        ADD_SPEED(1), // speed
        SET_SPEED(1), // speed
        ADD_DIRECTION(1), // direction in rads
        SET_DIRECTION(1), // direction in rads
        NEXT_SECTION(1), // last number
        NEXT_LAP(1), // last number
        TIMED_DISABLE(1), // time left in sec
        PICKUP_MISSILE(0),
        PICKUP_SPEEDBOOST(0),
        EXPOLSION(2), // radius, strength
        RESPAWN(0),
        SPAWN_MISSILE(3); // x, y, direction

        private int numOfValues;

        HandlingMethod(int numOfValues) {
            this.numOfValues = numOfValues;

        }

        public int getNumOfValues() {
            return numOfValues;

        }
    }

    /**
     * constructor for at least 2 values
     */
    public Flag(HandlingMethod method, double[] values) throws IllegalArgumentException {
        if (method.getNumOfValues() == values.length) {
            this.values = values.clone();
            this.method = method;

        }else {
            throw new IllegalArgumentException("The Flag object of method <" + method + "> has been created with improper number of values; needs " + method.getNumOfValues());

        }
    }

    /**
     * constructor for 1 value
     */
    public Flag(HandlingMethod method, double value) throws IllegalArgumentException {
        if (method.getNumOfValues() == 1) {
            values = new double[]{value};
            this.method = method;

        }else {
            throw new IllegalArgumentException("The Flag object of method <" + method + "> has been created with improper number of values; needs " + method.getNumOfValues());

        }
    }

    /**
     * constructor with new value
     */
    public Flag(HandlingMethod method) throws IllegalArgumentException {
        if (method.getNumOfValues() == 0) {
            this.method = method;

        }else {
            throw new IllegalArgumentException("The Flag object of method <" + method + "> has been created with improper number of values; needs " + method.getNumOfValues());

        }
    }

    /**
     * copy constructor
     */
    public Flag(Flag toCopy) {
        this.method= toCopy.method;
        this.values = toCopy.values.clone();

    }

    public double valueAt(int index) {
        return values[index];

    }

    /**
     * returns handling method in string
     */
    @Override
    public String toString() {
        return method.toString();

    }

    /**
     * compares Flag objects for same values
     */
    public boolean equals(Flag toCompare) {
        return this.method == toCompare.method && Arrays.equals(this.values, toCompare.values);

    }
}
