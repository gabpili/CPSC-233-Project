import java.util.ArrayList;

public class Map{

  private ArrayList<StaticObject> staticObjList = new ArrayList<StaticObject>();
  private ArrayList<DynamicObject> dynamicObjList = new ArrayList<Car>();
  private int width;
  private int height;

  public Map(ArrayList<StaticObject> staticObjList, ArrayList<DynamicObject> dynamicObjList, int width, int height){
    this.staticObjList = staticObjList
    this.dynamicObjList = dynamicObjList
    this.width = width;
    this.height = height;
  }

  public ArrayList<StaticObject> getStaticObjList(){
    ArrayList<StaticObject> copy = new ArrayList<StaticObject>();
    
  }
}
