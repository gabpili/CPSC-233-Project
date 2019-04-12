import java.util.ArrayList;
import java.lang.Math;

import base.Map;
import base.Driver;
import gameobj.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.control.Button;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class GraphicalApp extends Application {

	class LoadMapEventHandler implements EventHandler<ActionEvent> {
		private String level;
		private ArrayList<Driver> driverList;
		private ArrayList<DynamicGameObject> carList;
		private Stage primaryStage;


		public LoadMapEventHandler(String level, ArrayList<Driver> driverList, ArrayList<DynamicGameObject> carList, Stage primaryStage){
			this.level = level;
			this.driverList = driverList;
			this.carList = carList;
			this.primaryStage = primaryStage;
		}

		@Override
			public void handle(ActionEvent e){
				try {
					ArrayList<Driver> driverList = new ArrayList<Driver>();
					ArrayList<DynamicGameObject> carList = new ArrayList<DynamicGameObject>();

					// calls loadCar method to load a car.
					Car car = loadCar("Normal Car");

					car.setX(90);
					car.setY(10);
					driverList.add(new Driver(car));
					carList.add(car);

					// create a test map
					Map currentMap = new base.Map(null, carList, driverList, 200, 150);

					//call methods from MapIO class to load from chosen file
					currentMap = MapIO.loadStaticObstacle("map"+level+"-data/StaticObstacle"+level+".txt", currentMap);
					currentMap = MapIO.loadMissilePickup("map"+level+"-data/MissilePickUp"+level+".txt", currentMap);
					currentMap = MapIO.loadSpeedboostPickup("map"+level+"-data/SpeedboostPickup"+level+".txt", currentMap);
					currentMap = MapIO.loadFinishLine("map"+level+"-data/FinishLine"+level+".txt", currentMap);
					currentMap = MapIO.loadSpeedboostTile("map"+level+"-data/SpeedBoostTile"+level+".txt", currentMap);
					currentMap = MapIO.loadWall("map"+level+"-data/Wall"+level+".txt", currentMap);
					currentMap = MapIO.loadCheckpoint("map"+level+"-data/Checkpoint"+level+".txt", currentMap);
					FinishLine finish = null;
					for (BasicGameObject o: currentMap.getBasicObjList()) {
						if (o instanceof FinishLine) {
							finish = (FinishLine) o;
							break;

						}
					}

					if (finish != null) {
						double dx = finish.getEndX() - finish.getStartX();
						double dy = finish.getEndY() - finish.getStartY();
						double centreX = finish.getStartX() + dx / 2;
						double centreY = finish.getStartY() + dy / 2;

						car.setX(centreX);
						car.setY(centreY);
						car.setDirection(Math.atan2(dy , dx) - Math.PI / 2);

					}

					GameDisplay gameDisplay = new GameDisplay(currentMap, true, 60);

					primaryStage.setScene(gameDisplay.getScene());
					primaryStage.show();

					gameDisplay.start();

				}catch(Exception ex) {
					ex.printStackTrace();

				}
			}

	}

    class LoadCarEventHandler implements EventHandler<ActionEvent>{
        private String carName;
		private ArrayList<Driver> driverList;
		private ArrayList<DynamicGameObject> carList;
		private Stage primaryStage;
		private Scene nextScene;

		public LoadCarEventHandler(String carName, ArrayList<Driver> driverList, ArrayList<DynamicGameObject> carList, Stage primaryStage, Scene nextScene){
			this.carName = carName;
			this.driverList = driverList;
			this.carList = carList;
			this.primaryStage = primaryStage;
			this.nextScene = nextScene;
		}

		@Override
		public void handle(ActionEvent e){
			try {
				Car newCar = loadCar(carName);
				driverList.add(new Driver(newCar));
				carList.add(newCar);
				primaryStage.setScene(nextScene);

			}catch (Exception ex) {
				ex.printStackTrace();
				System.out.println(ex.getMessage());

			}
		}


	}

	/**
	* creates a Car out of values within a text file with the name of the Car to be created
	*/
	public static Car loadCar(String carName) throws FileNotFoundException, IOException, IllegalArgumentException {
		File carFile = new File("car-data/"+carName + ".txt");
		BufferedReader inputStream = new BufferedReader(new FileReader(carFile));

		/* Declaring an empty array that will later on be filled with
		arguments from the text chosen car and its corresponding
		text file.

		Loop will continue until the text file runs out of lines. Adds
		the lines into the 'arguments' array. */
		ArrayList<Double> arguments = new ArrayList<Double>();
		String line;
		while ((line = inputStream.readLine()) != null) {
			if (line.charAt(0) != '/') {
				arguments.add(Double.valueOf(line));

			}
		}

		if (arguments.size() != 12) {
			throw new IllegalArgumentException("The file <" + carFile + "> contains improper number of attributes to create a car; needs 12");

		}

		double halfW = arguments.get(0);
		double halfH = arguments.get(1);
		double mass = arguments.get(2);
		double engine = arguments.get(3);
		double brake = arguments.get(4);
		double drag = arguments.get(5);
		double rollingResistance = arguments.get(6);
		double frontToAxle = arguments.get(7);
		double backToAxle = arguments.get(8);
		double turnLimit = arguments.get(9);
		double corneringStiffnessFront = arguments.get(10);
		double corneringStiffnessBack = arguments.get(11);

		inputStream.close();

		// return selected car and create new car object with values from text file.
		return new Car(carName, halfW, halfH, mass,
			engine, brake, drag, rollingResistance,
			frontToAxle, backToAxle,
			turnLimit,
			corneringStiffnessFront, corneringStiffnessBack);

	}

    /**
     * starts program with stage/scene setup of all of its nodes
     * starts animation timer to loop program through the game loop steps
     */
	public void start(Stage primaryStage) throws Exception {

		ArrayList<Driver> driverList = new ArrayList<Driver>();
		ArrayList<DynamicGameObject> carList = new ArrayList<DynamicGameObject>();

		VBox carScreenRoot = new VBox();
		VBox mapScreenRoot = new VBox();

        Scene selectCar = new Scene(carScreenRoot, 200, 200);
        Scene selectMap = new Scene(mapScreenRoot, 200, 200);


	    Button car1 = new Button("Normal Car");
		Button car2 = new Button("Magic School Bus");
		//Button car3 = new Button("Spicy Car");

		carScreenRoot.getChildren().add(car1);
		carScreenRoot.getChildren().add(car2);


		car1.setOnAction(new LoadCarEventHandler("Normal Car", driverList, carList, primaryStage, selectMap));
		car2.setOnAction(new LoadCarEventHandler("Magic School Bus", driverList, carList, primaryStage, selectMap));

        Button map1 = new Button("Sweet Map");
		Button map2 = new Button("Salty Map");
		Button map3 = new Button("Spicy Map");

        mapScreenRoot.getChildren().add(map1);
		mapScreenRoot.getChildren().add(map2);
		mapScreenRoot.getChildren().add(map3);

		map1.setOnAction(new LoadMapEventHandler("1",  driverList, carList, primaryStage));
		map2.setOnAction(new LoadMapEventHandler("2",  driverList, carList, primaryStage));
		map3.setOnAction(new LoadMapEventHandler("3",  driverList, carList, primaryStage));

        primaryStage.setScene(selectCar);
		primaryStage.setTitle("Racing Game");
		primaryStage.show();

	}


	/**
	 * launches program
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
