import java.util.ArrayList;
import java.lang.Math;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import base.Map;
import base.Driver;
import gameobj.*;

public class ConsoleApp {
	public static Car chooseCarFromMenu(Scanner keyboard) {
		Car newCar = null;

		System.out.println("Choose a car: Normal Car; Magical School Bus");

		while (newCar == null) {
			try {
				newCar = ResourceIO.loadCar(keyboard.nextLine());

			}catch (FileNotFoundException e) {
				System.out.println("car not found");

			}catch (IOException e) {
				System.out.println(e.getMessage());

			}
		}

		return newCar;

	}

	public static Map chooseMapFromMenu(Scanner keyboard, ArrayList<DynamicGameObject> carList, ArrayList<Driver> driverList) {
		Map currentMap = new Map(null, carList, driverList, 200, 150);

		System.out.println("Choose a map flavour: Sweet; Salty; Spicy");

		String[] mapNames = new String[]{"Sweet", "Salty", "Spicy"};
		String mapName = "";
		while(mapName == "") {
			try {
				mapName = "" + java.util.Arrays.asList(mapNames).indexOf(keyboard.nextLine());
				String directory = "resource\\map" + mapName + "-data";

				if (!(new File(directory).exists())) {
					throw new FileNotFoundException(directory + " not found");

				}

				//call methods from MapIO class to load from chosen file
		        try {ResourceIO.loadStaticObstacle(directory + "\\StaticObstacle.txt", currentMap);
		        }catch (FileNotFoundException f) {System.out.println(directory + "\\StaticObstacle.txt");}

		        try {ResourceIO.loadMissilePickup(directory + "\\MissilePickUp.txt", currentMap);
		        }catch (FileNotFoundException f) {System.out.println(directory + "\\MissilePickUp.txt");}

		        try {ResourceIO.loadSpeedboostPickup(directory + "\\SpeedBoostPickup.txt", currentMap);
		        }catch (FileNotFoundException f) {System.out.println(directory + "\\SpeedBoostPickup.txt");}

		        try {ResourceIO.loadFinishLine(directory + "\\FinishLine.txt", currentMap);
		        }catch (FileNotFoundException f) {System.out.println(directory + "\\FinishLine.txt");}

		        try {ResourceIO.loadSpeedboostTile(directory + "\\SpeedBoostTile.txt", currentMap);
		        }catch (FileNotFoundException f) {System.out.println(directory + "\\SpeedBoostTile.txt");}

		        try {ResourceIO.loadWall(directory + "\\Wall.txt", currentMap);
		        }catch (FileNotFoundException f) {System.out.println(directory + "\\Wall.txt");}

		        try {ResourceIO.loadCheckpoint(directory + "\\Checkpoint.txt", currentMap);
		        }catch (FileNotFoundException f) {System.out.println(directory + "\\Checkpoint.txt");}

		        FinishLine finish = null;
		        for (BasicGameObject o: currentMap.getBasicObjList()) {
		            if (o instanceof FinishLine) {
		                finish = (FinishLine) o;
		                break;

		            }
		        }

		        Car car = (Car) carList.get(0);

		        if (finish != null) {
		            double dx = finish.getEndX() - finish.getStartX();
		            double dy = finish.getEndY() - finish.getStartY();
		            double centreX = finish.getStartX() + dx / 2;
		            double centreY = finish.getStartY() + dy / 2;

		            car.setX(centreX);
		            car.setY(centreY);
		            car.setDirection(Math.atan2(dy , dx) - Math.PI / 2);

		        }

			}catch (FileNotFoundException e) {
				System.out.println("map not found");
				mapName = "";

			}catch (IOException e) {
				System.out.println(e.getMessage());
				mapName = "";

			}
		}

		return currentMap;

	}

	/**
     * detects collision between each DynamicGameObject and every object
     * colliding objects have collisions resolved as per the BasicGameObject's specific method of resolution
     */
    private static void collisionStep(Map currentMap) {
        currentMap.collisionDetectResolveAll();

    }

	/**
     * passes in input as characters into map
     */
    private static void inputStep(Scanner keyboard, Map currentMap, double time) {
    	currentMap.giveInput(keyboard.nextLine(), time);

    }

    /**
     * ticks all objects, handling all flags and changing positions
     */
    private static void tickStep(Map currentMap, double time) {
        currentMap.tickAll(time);

    }

    /**
     * 
     */
    private static void displayStep(Map currentMap, Car mainCar) {
    	System.out.println(mainCar);
    	for (BasicGameObject o: currentMap.getProximityObjects(mainCar, 20)) {
			System.out.print(o.getName() + " " + (int) o.distance(mainCar) + 
				"m from main car, ");

			double carDir = mainCar.getDirection().theta();
			double carToObj = o.directionFrom(mainCar);
			double dDirection = carDir - carToObj;

			if(dDirection >= Math.PI){
				System.out.println((int) Math.toDegrees(carDir + carToObj) + "deg to the right");

			}else if(dDirection >= 0){
				System.out.println((int) Math.toDegrees(dDirection) + "deg to the left");

			}else if(dDirection >= -Math.PI){
				System.out.println((int) Math.toDegrees(-dDirection) + "deg to the right");

			}else{
				System.out.println((int) Math.toDegrees(Math.PI * 2 + dDirection) + "deg to the left");

			}
		}
    }

	public static void gameFrame(Scanner keyboard, Map currentMap, double time, Car mainCar) {
		// 4 game loop process
        inputStep(keyboard, currentMap, time);
        tickStep(currentMap, time);
        collisionStep(currentMap);
        displayStep(currentMap, mainCar);

	}

	public static void main(String[] args) {
		ArrayList<DynamicGameObject> carList = new ArrayList<DynamicGameObject>();
		ArrayList<Driver> driverList = new ArrayList<Driver>();
		Scanner keyboard = new Scanner(System.in);

		Car mainCar = chooseCarFromMenu(keyboard);
		driverList.add(new Driver(mainCar));
		carList.add(mainCar);
		Map currentMap = chooseMapFromMenu(keyboard, carList, driverList);

		while (true) {
			gameFrame(keyboard, currentMap, 0.5, mainCar);

		}
	}
}