import java.util.ArrayList;

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

    public void tickAll(double time){
        for (DynamicObject o: dynamicObjList) {
            o.tick(time);
        }
    }
}
