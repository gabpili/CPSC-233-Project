import java.lang.Math;
import java.util.ArrayList;

public class PresetMaps {
	public static void positionCarsToFinish(ArrayList<Car> carList, FinishLine finish) {
		double length = Math.sqrt(Math.pow(finish.getX2() - finish.getX(), 2) + Math.pow(finish.getY2() - finish.getY(), 2));
		double columni = (finish.getY() - finish.getY2()) / length;
		double columnj = (finish.getX2() - finish.getX()) / length;
		double column1x = finish.getX() + (finish.getX2() - finish.getX()) / 3;
		double column1y = finish.getY() + (finish.getY2() - finish.getY()) / 3;
		double column2x = column1x + (finish.getX2() - finish.getX()) / 3;
		double column2y = column1y + (finish.getY2() - finish.getY()) / 3;

		for (int i = 0; i < carList.size(); i++) {
			Car c = carList.get(i);
			if (i % 2 == 0) {
				c.setX(column1x + columni * (i * 4 + 3));
				c.setY(column1y + columnj * (i * 4 + 3));
				c.setDirection(Math.atan2(-columnj, -columni));

			}else {
				c.setX(column2x + columni * (i * 4 + 5));
				c.setY(column2y + columnj * (i * 4 + 3));
				c.setDirection(Math.atan2(-columnj, -columni));

			}
		}
	}

	public static Map loadMap1(ArrayList<Car> carList, ArrayList<Interface> interfaceList) {
		Map map1 = new Map(interfaceList, 100, 100);

		map1.addStaticObject(new Wall(30, 0, "wall", 70, 0));
		map1.addStaticObject(new Wall(30, 100, "wall", 70, 100));
		map1.addStaticObject(new Wall(0, 30, "wall", 0, 70));
		map1.addStaticObject(new Wall(100, 30, "wall", 100, 70));
		map1.addStaticObject(new Wall(0, 30, "wall", 30, 0));
		map1.addStaticObject(new Wall(70, 0, "wall", 100, 30));
		map1.addStaticObject(new Wall(0, 70, "wall", 30, 100));
		map1.addStaticObject(new Wall(70, 100, "wall", 100, 70));

		map1.addStaticObject(new Wall(40, 20, "wall", 60, 20));
		map1.addStaticObject(new Wall(40, 80, "wall", 60, 80));
		map1.addStaticObject(new Wall(20, 40, "wall", 20, 60));
		map1.addStaticObject(new Wall(80, 40, "wall", 80, 60));
		map1.addStaticObject(new Wall(20, 40, "wall", 40, 20));
		map1.addStaticObject(new Wall(60, 20, "wall", 80, 40));
		map1.addStaticObject(new Wall(20, 60, "wall", 40, 80));
		map1.addStaticObject(new Wall(60, 80, "wall", 80, 60));

		map1.addStaticObject(new StaticObstacle(20, 20, "cone", 0.2, 0.2));
		map1.addStaticObject(new StaticObstacle(21, 17, "cone", 0.2, 0.2));
		map1.addStaticObject(new StaticObstacle(22, 14, "cone", 0.2, 0.2));
		map1.addStaticObject(new StaticObstacle(27, 10, "cone", 0.2, 0.2));
		map1.addStaticObject(new StaticObstacle(15, 26, "cone", 0.2, 0.2));
		map1.addStaticObject(new StaticObstacle(27, 27, "cone", 0.2, 0.2));

		FinishLine finish = new FinishLine(0, 50, "finish", 20, 50, 3);
		map1.addStaticObject(finish);
		map1.addStaticObject(new Checkpoint(50, 0, "checkpoint1", 50, 20, 1));
		map1.addStaticObject(new Checkpoint(80, 50, "checkpoint2", 100, 50, 2));
		map1.addStaticObject(new Checkpoint(50, 80, "checkpoint3", 50, 100, 3));

		for (DynamicObject o: carList) {
			map1.addDynamicObject(o);
		}

		positionCarsToFinish(carList, finish);

		return map1;
	}
}
