
import java.util.ArrayList;
import java.lang.Math;

public class Map{
	/**
	 * Instance variables
	 */
    private ArrayList<BasicGameObject> staticObjList = new ArrayList<BasicGameObject>();
    private ArrayList<DynamicGameObject> dynamicObjList = new ArrayList<DynamicGameObject>();
    private ArrayList<Driver> driverList = new ArrayList<Driver>();
    private int width;
    private int height;

	/**
	 * Constructor takes in driverList of type Driver as well as integers
	 * width and height and uses a constructor within the class to initialize
	 * the values given values.
	 */
    public Map(ArrayList<Driver> driverList, int width, int height){
        this(null, null, driverList, width, height);
    }

	/**
	 *
	 */
    public Map(
	    ArrayList<BasicGameObject> staticObjList,
	    ArrayList<DynamicGameObject> dynamicObjList,
	    ArrayList<Driver> driverList, int width, int height) {
        if (staticObjList != null) {
            this.staticObjList.addAll(staticObjList);
        }
        if (dynamicObjList != null) {
            this.dynamicObjList.addAll(dynamicObjList);
        }
        this.driverList.addAll(driverList);
        this.width = width;
        this.height = height;
    }

    /**
	 * method returns list of static objects
	 * list contains non-moving obstacles and walls
	 */
    public ArrayList<BasicGameObject> getStaticObjList() {
        return staticObjList;
    }

    /**
	 * method returns list of dynamic objects
	 * list contains cars and moving obstacles
	 */
    public ArrayList<DynamicGameObject> getDynamicObjList() {
        return dynamicObjList;
    }

    /**
	 *
	 */
    public ArrayList<Driver> getDriverList() {
      return driverList;
    }

    /**
	 * method returns width
	 */
    public int getWidth() {
        return width;
    }

    /**
	 * method returns height
	 */
    public int getHeight() {
        return height;
    }

    /**
	 * Add given static object to the BasicGameObject list
	 */
    public void addBasicGameObject(BasicGameObject s1) {
        this.staticObjList.add(s1);
    }

    /**
	 * Add given dynamic object to the DynamicGameObject list
	 */
    public void addDynamicGameObject(DynamicGameObject d1) {
        this.dynamicObjList.add(d1);
    }

    /**
	 * Remove given static object from the list of static objects: 'staticObjList'
	 */
    public BasicGameObject removeBasicGameObject(BasicGameObject toRemove) {
        staticObjList.remove(toRemove);
        return toRemove;
    }

    /**
	 * Remove given dynamic object from the list of dynamic objects, "dynamicObjList"
	 */
    public DynamicGameObject removeDynamicGameObject(DynamicGameObject toRemove) {
        dynamicObjList.remove(toRemove);
        return toRemove;
    }

	/**
	 *
	 */
    public void giveInput(ArrayList<Character> character, double time) {
        for (Driver i: driverList) {
            i.takeInput(character, time);
        }
    }

	/**
	 *
	 */
  public void giveInput(String input, double time) {
        ArrayList<Character> copy = new ArrayList<Character>();
            for (int i = 0; i < input.length(); i++) {
                copy.add(Character.valueOf(input.charAt(i)));
            }
        giveInput(copy, time);
    }

	/**
	 *
	 */
    public ArrayList<BasicGameObject> getProximityObjects(DynamicGameObject d, double proximity) {
        ArrayList<BasicGameObject> copy = new ArrayList<BasicGameObject>();
            for (BasicGameObject o: staticObjList) {
                if (o.distance(d) <= proximity) {
                copy.add(o);
            }
        }

        return copy;
    }

    /**
	 * Method will detect collisions using the Axis Align Bounding-Boxes (AABB).
	 * Method takes in a DynamicGameObject named dObj and an array list of type BasicGameObject sObjs.
	 *
	 * Creates a new array list for potential collisions. Then iterates through the given list
	 * using a for loop. Check if the maximum radius (maxR) is less than the total length of x and
	 * maximum raidus of the static object.
	 *
	 * If all conditions in the if statement are satisfied, the static object, "s", that would potentially
	 * collide with the dObj is added into type BasicGameObject  array list "potentialCollisions".
	 */
    public ArrayList<BasicGameObject> detectAABB(DynamicGameObject dObj,
        ArrayList<? extends BasicGameObject> sObjs){
        ArrayList<BasicGameObject> potentialCollisions = new ArrayList<BasicGameObject>();
        for (BasicGameObject s : sObjs){
            if (dObj.getMaxR() < s.getX() + s.getMaxR() &&
                dObj.getMaxR() + s.getX() > s.getX() &&
                dObj.getMaxR() < s.getY() + s.getMaxR() &&
                dObj.getY() + s.getMaxR() > s.getY()){

                potentialCollisions.add(s);
            }
        }
        return potentialCollisions;
    }

    /**
	 * test if two objects b (moving object) and a are colliding using the
	 * Separating Axis Theorem (SAT)
	 *
	 * resource: Separating Axis Theorem for Oriented Bounding Boxes by Johnny Huynh
	 *		www.jkh.me
	 *
	 * @return true if colliding, false if not
	 */
    public boolean testSAT(DynamicGameObject b, BasicGameObject a) {
        double tx, ty;
        double cD = Math.cos(b.getDirection());
        double sD = Math.sin(b.getDirection());

        if (a instanceof Wall) {
            Wall a_ = (Wall) a;

            double adx = a_.getX2() - a_.getX();
            double ady = a_.getY2() - a_.getY();
            double dx = b.getX() - a_.getX();
            double dy = b.getY() - a_.getY();
            double dx2 = b.getX() - a_.getX2();
            double dy2 = b.getY() - a_.getY2();
            double p = ((dx*adx) + (dy*ady)) / (adx*adx + ady*ady);

            // true if the center of b projected onto the wall, which is a point, is on the wall
            if (Math.abs(a_.getX2() - a_.getX()) == Math.abs(p * adx) + Math.abs(a_.getX2() - a_.getX() - p * adx) &&
                Math.abs(a_.getY2() - a_.getY()) == Math.abs(p * ady) + Math.abs(a_.getY2() - a_.getY() - p * ady)) {

                tx = dx - p*adx;
                ty = dy - p*ady;

                return !(Math.abs(ty * adx - tx * ady) > a_.getHalfW() * Math.sqrt(ady * ady + adx * adx)
                    + Math.abs(b.getHalfW() * (sD * ady + cD * adx))
                    + Math.abs(b.getHalfH() * (sD * adx - cD * ady)) ||
                    Math.abs(ty * cD - tx * sD) > b.getHalfW()
                    + Math.abs(a_.getHalfH() * (ady * cD - adx * sD)) ||
                    Math.abs(tx * cD + ty * sD) > b.getHalfH()
                    + Math.abs(a_.getHalfH() * (adx * cD + ady * sD)));

            }else {
                if (Math.sqrt(dx * dx + dy * dy) <= Math.sqrt(dx2 * dx2 + dy2 * dy2)) {
                    tx = dx;
                    ty = dy;

                }else {
                    tx = dx2;
                    ty = dy2;

                }
                return !(Math.abs(ty * adx - tx * ady) >
                    Math.abs(b.getHalfW() * (sD * ady + cD * adx))
                    + Math.abs(b.getHalfH() * (sD * adx - cD * ady)) ||
                    Math.abs(tx * adx + ty * ady) >
                    Math.abs(b.getHalfW() * (cD * ady - sD * adx))
                    + Math.abs(b.getHalfH() * (cD * adx + sD * ady)) ||
                    Math.abs(ty * cD - tx * sD) > b.getHalfW() ||
                    Math.abs(tx * cD + ty * sD) > b.getHalfH());

            }
        }
        else{
            tx = a.getX() - b.getX();
            ty = a.getY() - b.getY();

            return !(Math.abs(tx) > a.getHalfW()
                + Math.abs(b.getHalfW() * -sD)
                + Math.abs(b.getHalfH() * cD) ||
                Math.abs(ty) > a.getHalfH()
                + Math.abs(b.getHalfW() * cD)
                + Math.abs(b.getHalfH() * sD) ||
                Math.abs(ty * cD - tx * sD) > b.getHalfW()
                + Math.abs(a.getHalfW() * -sD)
                + Math.abs(a.getHalfH() * cD) ||
                Math.abs(tx * cD + ty * sD) > b.getHalfH()
                + Math.abs(a.getHalfW() * cD)
                + Math.abs(a.getHalfH() * sD));

        }
    }

    /**
	 * performs SAT tests between given DynamicGameObject and every object in list
	 *
	 * @return set of objects that are colliding with dObj
	 */
    public ArrayList<BasicGameObject> detectSATCollisions(DynamicGameObject dObj,
        ArrayList<? extends BasicGameObject> sObjs){
        ArrayList<BasicGameObject> colliding = new ArrayList<BasicGameObject>();
            for (BasicGameObject o: sObjs) {
                if (dObj != o && testSAT(dObj, o)) {
                colliding.add(o);

            }
        }
        return colliding;

    }

    /**
     * apply flag action to object o
     */
    public void handleFlag(BasicGameObject o, Flag f){

        if (o instanceof DynamicGameObject) {
            DynamicGameObject o_ = (DynamicGameObject) o;

            switch (f.getHandlingMethod()) {
                case ("DESTROY"):
                    removeDynamicGameObject(o_);
                    break;

                case ("ADD_SPEED"):
                    o_.setSpeed(o_.getSpeed() + f.getValue());
                    break;

                case ("SET_SPEED"):
                    o_.setSpeed(f.getValue());
                    break;

                case ("ADD_DIRECTION"):
                    o_.setDirection(o_.getDirection() + f.getValue());
                    break;

                case ("SET_DIRECTION"):
                    o_.setDirection(f.getValue());
                    break;
            }

            if (o instanceof Car) {
            	Driver i = null;

            	for (Driver i_: driverList) {
            		if (i_.getAttachedCar() == o) {
            			i = i_;
            		}
            	}

            	if (i != null) {
            		switch (f.getHandlingMethod()) {
	            		case ("NEXT_SECTION"):
	            			if (i.getSection() < f.getValue()) {
	            				i.setSection((int) f.getValue());

	            			}
	            			break;

	    				case ("NEXT_LAP"):
	    					if (i.getSection() == f.getValue()) {
	    						i.setSection(0);
	    						i.setLap(i.getLap() + 1);
	    					}
	    					break;
	            	}
            	}
            }
        }else {

            switch (f.getHandlingMethod()) {
                case ("DESTROY"):
                    removeBasicGameObject(o);
                    break;

                default:;
            }
        }

    }

    /**
	 * handle all flags of each object and tick all DynamicGameObjects to change positions
	 */
    public ArrayList<BasicGameObject> tickAll(double time){
    	ArrayList<BasicGameObject> toUpdate = new ArrayList<BasicGameObject>();

        for (BasicGameObject o: new ArrayList<BasicGameObject>(staticObjList)) {
        	if (!o.getFlags().isEmpty()) {
        		for (Flag f: o.getFlags()) {
	                handleFlag(o, f);
	            }

	            toUpdate.add(o);
        	}

        }
        for (DynamicGameObject o: new ArrayList<DynamicGameObject>(dynamicObjList)) {
            for (Flag f: o.getFlags()){
                handleFlag(o, f);
                o.removeFlag(f);
            }
            o.tick(time);
            toUpdate.add(o);
        }

        return toUpdate;
    }

}
