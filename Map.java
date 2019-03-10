import java.util.ArrayList;
import java.lang.Math;

public class Map{

    private ArrayList<StaticObject> staticObjList = new ArrayList<StaticObject>();
    private ArrayList<DynamicObject> dynamicObjList = new ArrayList<DynamicObject>();
    private ArrayList<Interface> interfaceList = new ArrayList<Interface>();
    private int width;
    private int height;

    public Map(ArrayList<Interface> interfaceList, int width, int height){
        this(null, null, interfaceList, width, height);
    }

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

    public ArrayList<StaticObject> getStaticObjList() {
        return staticObjList;
    }

    public ArrayList<DynamicObject> getDynamicObjList() {
        return dynamicObjList;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addStaticObject(StaticObject s1) {
        this.staticObjList.add(s1);
    }

    public void addDynamicObject(DynamicObject d1) {
        this.dynamicObjList.add(d1);
    }

    public StaticObject removeStaticObject(StaticObject toRemove) {
        staticObjList.remove(toRemove);
        return toRemove;
    }

    public DynamicObject removeDynamicObject(DynamicObject toRemove) {
        dynamicObjList.remove(toRemove);
        return toRemove;
    }

    public void giveInput(ArrayList<Character> character, double time) {
        for (Interface i: interfaceList) {
            i.takeInput(character, time);
        }
    }

  public void giveInput(String input, double time) {
        ArrayList<Character> copy = new ArrayList<Character>();
            for (int i = 0; i < input.length(); i++) {
                copy.add(new Character(input.charAt(i)));
            }
        giveInput(copy, time);
    }

    public ArrayList<StaticObject> getProximityObjects(DynamicObject d, double proximity) {
        ArrayList<StaticObject> copy = new ArrayList<StaticObject>();
            for (StaticObject o: staticObjList) {
                if (o.distance(d) <= proximity) {
                copy.add(o);
            }
        }

        return copy;
    }

    public ArrayList<StaticObject> detectAABB(DynamicObject dObj, ArrayList<StaticObject> sObjs){
        // create new list for potential collisions
        ArrayList<StaticObject> potentialCollisions = new ArrayList<StaticObject>();
        // iterating through list of static objects
        for (StaticObject s : sOBjs){
            if (dObj.getMaxR() < s.getX() + s.getMaxR() && 
                dObj.getMaxR() + dObj.getX() > s.getX() &&             
                dObj.getMaxR() < s.getY() + s.getMaxR() &&
                dObj.getY() + dObj.getMaxR() > s.getY()){

                potentialCollisions.add(s);
            }
        }
        return potentialCollisions;
    }


    public boolean testSAT(DynamicObject b, StaticObject a) {
        double tx, ty;
        if (a instanceof Wall){
            double ai = a.getX2() - a.getX();
            double aj = a.getY2() - a.getY();
            double di = b.getX() - a.getX();
            double dj = b.getY() - a.getY();
            double proj = ((di*ai) + (dj*aj)) / (ai*ai + aj*aj);
            tx = di - proj*ai;
            ty = dj - proj*aj;
        }
        else{
            tx = a.getX() - b.getX();
            ty = a.getY() - b.getY();
        }
        double dir = b.getDirection();
        double theta = dir + (Math.PI/2);
        double cTh = Math.cos(theta);
        double cD = Math.cos(dir);
        double sTh = Math.sin(theta);
        double sD = Math.sin(dir);

        return !(Math.abs(tx) > a.getHalfW()
            + Math.abs(b.getHalfW() * cTh)
            + Math.abs(b.getHalfW() * cD) ||
            Math.abs(ty) > a.getHalfH()
            + Math.abs(b.getHalfH() * sTh)
            + Math.abs(b.getHalfH() * sD) ||
            Math.abs(tx * cTh + ty * sTh) > b.getHalfW()
            + Math.abs(a.getHalfW() * cTh)
            + Math.abs(a.getHalfH() * sTh) ||
            Math.abs(tx * cD + ty * sD) > b.getHalfH()
            + Math.abs(a.getHalfW() * cD)
            + Math.abs(a.getHalfH() * sD));
    }

    public ArrayList<StaticObject> detectSATCollisions(DynamicObject dObj, ArrayList<StaticObject> sObjs){
        for (StaticObject o : sObjs){
            if (!testSAT(dObj, o)){
                sObjs.remove(o);
            }
        }
        return sObjs;
    }
    public void tickAll(double time){
        for (DynamicObject o: dynamicObjList) {
            o.tick(time);
        }
    }
}
