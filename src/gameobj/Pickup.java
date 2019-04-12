package gameobj;

public abstract class Pickup extends BasicGameObject {
    private boolean active = true;

    /**
     * constructor with halfW and halfH initialized as 0.5 and mass as 0
     */
    public Pickup(double x, double y, String name) {
        super(x, y, name, 0.5, 0.5, 0);

    }

    /**
     * copy constructor
     */
    public Pickup(Pickup toCopy) {
        super(toCopy);

    }

    /**
     * gets state of active
     */
    public boolean getActive() {
        return active;

    }

    /**
     * sets state of active
     */
    public void setActive(boolean active) {
        this.active = active;

    }
}
