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
		private String mapName;
		private ArrayList<Driver> driverList;
		private ArrayList<DynamicGameObject> carList;
		private Stage primaryStage;


		public LoadMapEventHandler(String mapName, ArrayList<Driver> driverList, ArrayList<DynamicGameObject> carList, Stage primaryStage){
			this.mapName = mapName;
			this.driverList = driverList;
			this.carList = carList;
			this.primaryStage = primaryStage;
		}

		@Override
		public void handle(ActionEvent e){
			try {
				// create a test map
				Map currentMap = new Map(null, carList, driverList, 200, 200);
				currentMap.addBasicGameObject(new MissilePickup(30, 100));
				currentMap.addBasicGameObject(new SpeedboostPickup(60, 100));
				currentMap.addBasicGameObject(new StaticObstacle(40, 40, "Box", 1, 1, 250));
				currentMap.addBasicGameObject(new StaticObstacle(44, 40, "Small Box", 0.4, 0.4, 60));
				currentMap.addBasicGameObject(new StaticObstacle(38, 41, "Barrel", 0.2, 0.2, 300));
				currentMap.addBasicGameObject(new StaticObstacle(39, 38, "Small Box", 0.4, 0.4, 60));
				currentMap.addBasicGameObject(new StaticObstacle(35, 42, "Small Box", 0.4, 0.4, 60));
				currentMap.addBasicGameObject(new StaticObstacle(42, 43, "Small Box", 0.4, 0.4, 60));
				currentMap.addBasicGameObject(new FinishLine(10, 100, "Finish", 10, 120, 0));

				//Map currentMap = loadMap(mapName, carList, driverList);

				GameDisplay gameDisplay = new GameDisplay(currentMap, true, 100);

				primaryStage.setScene(gameDisplay.getScene());

				gameDisplay.start();

			}catch(Exception ex) {
				ex.printStackTrace();
				System.out.println(ex.getMessage());

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
		File carFile = new File(carName + ".txt");
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

		map1.setOnAction(new LoadMapEventHandler("Sweet Map",  driverList, carList, primaryStage));
		map2.setOnAction(new LoadMapEventHandler("Salty Map",  driverList, carList, primaryStage));
		map3.setOnAction(new LoadMapEventHandler("Spicy Map",  driverList, carList, primaryStage));

        primaryStage.setScene(selectCar);
		primaryStage.setTitle("Project C");
		primaryStage.show();

	}


	/**
	 * launches program
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
