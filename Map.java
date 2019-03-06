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
        this.staticObjList.addAll(staticObjList);
        this.dynamicObjList.addAll(dynamicObjList);
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

    public void addStaticObject(StaticObject s1) {
        this.staticObjList.add(s1);
    }

    public void addDynamicObject(DynamicObject d1) {
        this.dynamicObjList.add(d1);
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

    public ArrayList<StaticObject> getProximityObjects(Car car) {
        ArrayList<StaticObject> copy = new ArrayList<StaticObject>();
            for (StaticObject o: staticObjList) {
                if (o.distance(car) <= 30) {
                copy.add(o);

            }
        }

        return copy;
    }

    public void tickAll(){
        for (DynamicObject o: dynamicObjList) {

        }
    }
}
