package gameobj;

public abstract class Pickup extends BasicGameObject {

    private boolean active = true;

    public Pickup(double x, double y, String name) {
        super(x, y, name, 0.5, 0.5, 0);

    }

    public Pickup(Pickup toCopy) {
        super(toCopy);

    }

    public boolean getActive() {
        return active;

    }

    public void setActive(boolean active) {
        this.active = active;

    }
}
