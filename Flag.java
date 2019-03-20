public class Flag{
	/**
	 * determines type of flag and how it should be handled
	 */
    private HandlingMethod method;

    /**
     * generic variable used by some flag handling methods
     */
    private double value;

    public enum HandlingMethod {
        DESTROY {
            @Override
            public String toString() {
                return "DESTROY";

            }
        },
        ADD_SPEED {
            @Override
            public String toString() {
                return "ADD_SPEED";

            }
        },
        SET_SPEED {
            @Override
            public String toString() {
                return "SET_SPEED";

            }
        },
        ADD_DIRECTION {
            @Override
            public String toString() {
                return "ADD_DIRECTION";

            }
        },
        SET_DIRECTION {
            @Override
            public String toString() {
                return "SET_DIRECTION";

            }
        },
        NEXT_SECTION {
        	@Override
        	public String toString() {
        		return "NEXT_SECTION";

        	}
        },
        NEXT_LAP {
        	@Override
        	public String toString() {
        		return "NEXT_LAP";

        	}
        };
    }

    /**
     * full constructor
     */
    public Flag(double value, HandlingMethod method) {
        this(method);
        this.value = value;

    }

    /**
     * constructor without value 
     */
    public Flag(HandlingMethod method) {
        this.method = method;

    }

    /**
     * get value
     */
    public double getValue() {
        return value;

    }

    /**
     * return handling method
     */
    @Override
    public String toString() {
        return method.toString();

    }
}
