import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class ConsoleApp {
	private static Map currentMap;


	public static void collisionStep() {

	}

	public static void inputStep(Scanner keyboard, double time) {
		currentMap.giveInput(keyboard.nextLine(), time);
	}

	public static void tickStep(double time) {
		currentMap.tickAll(time);
	}

	public static void displayStep() {
		DynamicObject mainCar = currentMap.getDynamicObjList().get(0);
		System.out.println(mainCar);
		ArrayList<StaticObject> objectsNearCar = currentMap.getProximityObjects(mainCar, 30);

		for (StaticObject o: objectsNearCar) {
			System.out.print(o.getName() + " " + (int) o.distance(mainCar) +
				"m from main car, ");

			double carDir = mainCar.getDirection();
			double carToObj = o.directionFrom(mainCar);
			double dDirection = carDir - carToObj;

			if (dDirection >= Math.PI) {
				System.out.println((int) Math.toDegrees(carDir + carToObj) + "deg to the right");
			}else if (dDirection >= 0) {
				System.out.println((int) Math.toDegrees(dDirection) + "deg to the left");
			}else if (dDirection >= -Math.PI) {
				System.out.println((int) Math.toDegrees(-dDirection) + "deg to the right");
			}else {
				System.out.println((int) Math.toDegrees(Math.PI * 2 + dDirection) + "deg to the left");
			}
		}
	}

	public static void mainLoop(double time){
		Scanner keyboard = new Scanner(System.in);
		while (true) {
			collisionStep();
			inputStep(keyboard, time);
			tickStep(time);
			displayStep();
		}
	}

	public static void main(String[] args) {
		Car mainCar = new Car(50, 0, "Magic School Bus", Math.toRadians(90), 8.2, 0.3);
		ArrayList<Interface> interfaceList = new ArrayList<Interface>();
		interfaceList.add(new Interface(mainCar));

		currentMap = new Map(interfaceList, 200, 200);

		currentMap.addDynamicObject(mainCar);

		for (int i=0; i<5; i++) {
			currentMap.addStaticObject(new StaticObstacle(30, 10 + 10 * i, "RCone" + i));
			currentMap.addStaticObject(new StaticObstacle(70, 5 + 10 * i, "LCone" + i));
		}

		mainLoop(1.0/4);

	}
}
