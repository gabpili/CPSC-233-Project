import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

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

		ArrayList<ArrayList<StaticObject>> objectsInProximity = new ArrayList<ArrayList<StaticObject>>();

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
		Car mainCar = currentMap.getDynamicObjList().get(0);
		System.out.println(mainCar);
		for(StaticObject o: objectsInProximity.get(0)){
			System.out.println(o.getName() + " " + o.distance(mainCar) + 
				"m from main car, ");

			double carDir = mainCar.getDirection();
			double carToObj = o.directionFrom(mainCar);
			double dDirection = carDir - carToObj;

			if(dDirection >= Math.PI){
				System.out.print((int) Math.toDegrees(carDir + carToObj) + "deg to the right");
			}else if(dDirection >= 0){
				System.out.print((int) Math.toDegrees(dDirection) + "deg to the left");
			}else if(dDirection >= -Math.PI){
				System.out.print((int) Math.toDegrees(-dDirection) + "deg to the right");
			}else{
				System.out.print((int) Math.toDegrees(Math.PI * 2 + dDirection) + "deg to the left");
			}
		}
	}

	public static void main(String[] args){
		
	}
}