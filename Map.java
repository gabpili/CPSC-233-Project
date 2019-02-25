import java.util.ArrayList;

public class Map{

  private ArrayList<StaticObject> staticObjList = new ArrayList<StaticObject>();
  private ArrayList<DynamicObject> dynamicObjList = new ArrayList<DynamicObject>();
  private ArrayList<Interface> interfaceList = new ArrayList<Interface>();
  private int width;
  private int height;

  public Map(ArrayList<StaticObject> staticObjList, ArrayList<DynamicObject> dynamicObjList, ArrayList<Interface> interfaceList, int width, int height){
    for(StaticObject o: staticObjList)this.staticObjList.add(o);
    for(DynamicObject o: dynamicObjList)this.dynamicObjList.add(o);
    for(Interface i: interfaceList)this.interfaceList.add(i);
    this.width = width;
    this.height = height;
  }

  public ArrayList<StaticObject> getStaticObjList(){
    ArrayList<StaticObject> copy = new ArrayList<StaticObject>();
    for (StaticObject i: this.staticObjList){
      copy.add(new StaticObject(i));
    }

    return copy;
  }

  public ArrayList<DynamicObject> getDynamicObjList(){
    ArrayList<DynamicObject> copy = new ArrayList<DynamicObject>();
    for (DynamicObject i: this.dynamicObjList){
      copy.add(new DynamicObject(i));
    }

    return copy;
  }

  public void addStaticObject(StaticObject s1){
    this.staticObjList.add(new StaticObject(s1));
  }

  public void addDynamicObject(DynamicObject d1){
    this.dynamicObjList.add(new DynamicObject(d1));
  }

  public void giveInput(ArrayList<Character> character, double time){
    for (Interface i: interfaceList){
      i.takeInput(character, time);

    }

  }

  public void giveInput(String input, double time){
    ArrayList<Character> copy = new ArrayList<Character>();
    for (int i = 0; i < input.length(); i++){
      copy.add(new Character(input.charAt(i)));
    }

    giveInput(copy, time);
  }

  public ArrayList<Integer> getProximityObjects(Car car){
    ArrayList<Integer> copy = new ArrayList<Integer>();
    for (int i = 0; i < staticObjList.size(); i++){
      if (i.distance(car) <= 30){
        copy.add(new Integer(i));

      }
    }

    return copy;
  }
}
