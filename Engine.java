import java.util.Scanner;
import java.util.ArrayList;

public class Engine{
	private static Map currentMap;
	private static ArrayList<Interface> currentInterfaces = new ArrayList<Interface>();
	private static ArrayList<Car> currentCars = new ArrayList<Car>();

	public static void mainLoop(double time){
		// 1. collision
		ArrayList<Car> activeCars = new ArrayList<Car>();

		for(DynamicObject o: currentMap.getDynamicObjList()){
			if(o instanceof Car)activeCars.add(o);
		}

		ArrayList<ArrayList<StaticObject> objectsInProximity = new ArrayList<ArrayList<StaticObject>>();

		for(int i=0; i<activeCars.size(); i++){
			objectsInProximity.set(i, Map.getProximityObjects(activeCars.get(i)));
		}
		// objectsInProximity contains arrays of objects close to a car, for each car in activeCars


		// 2. input
		Scanner keyboard = new Scanner(System.in);
		String keysIn = keyboard.nextLine();

		Map.giveInput(keysIn);


		// 3. tick
		for(DynamicObject o: currentMap.getDynamicObjList()){
			// ticks each dynamic object for given time
			o.tick(time);
		}


		// 4. display
		Car mainCar = currentMap.getDynamicObjList().get(0)
		System.out.println(mainCar);
		for(StaticObject o: objectsInProximity.get(0)){
			double dirFromCar = o.directionFrom(mainCar) - mainCar.getDirection();
			System.out.println(o.getName() + " " + o.distance(mainCar) + 
				"m from main car, ");


		}
	}

	public static void main(String[] args){
		for()
	}
}