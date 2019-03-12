
import java.util.ArrayList;
import java.lang.Math;

public class Map{
	/**
	 * Instance variables
	 */
    private ArrayList<StaticObject> staticObjList = new ArrayList<StaticObject>();
    private ArrayList<DynamicObject> dynamicObjList = new ArrayList<DynamicObject>();
    private ArrayList<Interface> interfaceList = new ArrayList<Interface>();
    private int width;
    private int height;
    
	/**
	 * Constructor takes in interfaceList of type Interface as well as integers
	 * width and height and uses a constructor within the class to initialize
	 * the values given values.
	 */
    public Map(ArrayList<Interface> interfaceList, int width, int height){
        this(null, null, interfaceList, width, height);
    }
    
	/**
	 * 
	 */
    public Map(
    ArrayList<StaticObject> staticObjList,
    ArrayList<DynamicObject> dynamicObjList,
    ArrayList<Interface> interfaceList, int width, int height) {
        if (staticObjList != null) {
            this.staticObjList.addAll(staticObjList);
        }
        if (dynamicObjList != null) {
            this.dynamicObjList.addAll(dynamicObjList);
        }
        this.interfaceList.addAll(interfaceList);
        this.width = width;
        this.height = height;
    }
    
    /**
	 * method returns list of static objects
	 */
    public ArrayList<StaticObject> getStaticObjList() {
        return staticObjList;
    }
    
    /**
	 * method returns list of dynamic objects
	 */
    public ArrayList<DynamicObject> getDynamicObjList() {
        return dynamicObjList;
    }

    /**
	 * 
	 */
    public ArrayList<Interface> getInterfaceList() {
      return interfaceList;
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
	 * Add given static object to the static object list
	 */
    public void addStaticObject(StaticObject s1) {
        this.staticObjList.add(s1);
    }
    
    /**
	 * Add given dynamic object to the static object list
	 */
    public void addDynamicObject(DynamicObject d1) {
        this.dynamicObjList.add(d1);
    }
    
    /**
	 * Remove given static object from the list of static objects: 'staticObjList'
	 */
    public StaticObject removeStaticObject(StaticObject toRemove) {
        staticObjList.remove(toRemove);
        return toRemove;
    }
    
    /**
	 * Remove given dynamic object from the list of dynamic objects, "dynamicObjList"
	 */
    public DynamicObject removeDynamicObject(DynamicObject toRemove) {
        dynamicObjList.remove(toRemove);
        return toRemove;
    }
    
	/**
	 * 
	 */
    public void giveInput(ArrayList<Character> character, double time) {
        for (Interface i: interfaceList) {
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
    public ArrayList<StaticObject> getProximityObjects(DynamicObject d, double proximity) {
        ArrayList<StaticObject> copy = new ArrayList<StaticObject>();
            for (StaticObject o: staticObjList) {
                if (o.distance(d) <= proximity) {
                copy.add(o);
            }
        }

        return copy;
    }
    
    /**
	 * Method will detect collisions using the Axis Align Bounding-Box Theorem.
	 * Method takes in a DynamicObject named dObj and an array list of type StaticObject sObjs.
	 * 
	 * Creates a new array list for potential collisions. Then iterates through the given list
	 * using a for loop. Check if the maximum radius (maxR) is less than the total length of x and
	 * maximum raidus of the static object.
	 * 
	 * If all conditions in the if statement are satisfied, the static object, "s", that would potentially 
	 * collide with the dObj is added into type StaticObject  array list "potentialCollisions".
	 */
    public ArrayList<StaticObject> detectAABB(DynamicObject dObj, ArrayList<StaticObject> sObjs){
        ArrayList<StaticObject> potentialCollisions = new ArrayList<StaticObject>();
        for (StaticObject s : sObjs){
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
	 * 
	 */
    public boolean testSAT(DynamicObject b, StaticObject a) {
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
	 * 
	 */
    public ArrayList<StaticObject> detectSATCollisions(DynamicObject dObj, ArrayList<StaticObject> sObjs){
        ArrayList<StaticObject> colliding = new ArrayList<StaticObject>();
            for (StaticObject o: sObjs) {
                if (testSAT(dObj, o)) {
                colliding.add(o);

            }
        }
        return colliding;

    }
  
    public void handleFlag(StaticObject o, Flag f){
        System.out.println("handling");

        if (o instanceof DynamicObject){
            DynamicObject o_ = (DynamicObject) o;

            switch (f.getHandlingMethod()){

                case ("DESTROY"):
                    removeDynamicObject(o_);
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
        }
        else{

            switch (f.getHandlingMethod()){

                case ("DESTROY"):
                    System.out.println("lllll");
                    removeStaticObject(o);
                    break;

                default:;
            }
        }

    }
  
    /**
	 * 
	 */
    public void tickAll(double time){
        for (StaticObject o: staticObjList) {
            for (Flag f: o.getFlags()) {
                handleFlag(o, f);
            }
        }
        for (DynamicObject o: dynamicObjList) {
            for (Flag f: o.getFlags()){
                handleFlag(o, f);
            }
            o.tick(time);
        }
    }
    
    /**
	 * 
	 */
    public static void main(String[] args) {

    }
}
