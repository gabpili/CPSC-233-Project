import java.util.ArrayList;

import base.Map;
import base.Driver;
import gameobj.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

import javafx.scene.control.Button;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class GraphicalApp extends Application {
	/**
	 *
	 */
	public static Car loadCar(String carName) throws FileNotFoundException, IOException, IllegalArgumentException {
	    File carFile = new File(carName + ".txt");
	    BufferedReader inputStream = new BufferedReader(new FileReader(carFile));

	    /* Declaring an empty array that will later on be filled with
		arguments from the text chosen car and its corresponding
		text file.

	    Loop will continue until the text file runs out of lines. */
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
		Group menuroot = new Group();

     	Button button = new Button();
		button.setText("Start");
		button.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				try {
					ArrayList<Driver> driverList = new ArrayList<Driver>();
					ArrayList<DynamicGameObject> carList = new ArrayList<DynamicGameObject>();
					Car car = loadCar(getParameters().getRaw().get(0));
					car.setX(10);
					car.setY(70);
					driverList.add(new Driver(car));
					carList.add(car);

					Map currentMap = new Map(null, carList, driverList, 500, 500);
					currentMap.addBasicGameObject(new MisslePickup(30, 80));
					currentMap.addBasicGameObject(new SpeedboostPickup(60, 80));
					currentMap.addBasicGameObject(new StaticObstacle(40, 40, "Box", 1, 1, 250));
					currentMap.addBasicGameObject(new StaticObstacle(44, 40, "Small Box", 0.4, 0.4, 60));
					currentMap.addBasicGameObject(new StaticObstacle(38, 41, "Barrel", 0.2, 0.2, 300));
					currentMap.addBasicGameObject(new StaticObstacle(39, 38, "Small Box", 0.4, 0.4, 60));
					currentMap.addBasicGameObject(new StaticObstacle(35, 42, "Small Box", 0.4, 0.4, 60));
					currentMap.addBasicGameObject(new StaticObstacle(42, 43, "Small Box", 0.4, 0.4, 60));
					currentMap.addBasicGameObject(new FinishLine(10, 60, "Finish", 10, 80, 0));
					currentMap.addBasicGameObject(new Wall(100, 100, "bub", 80, 220));

					GameDisplay gameDisplay = new GameDisplay(currentMap, true, 60);

					primaryStage.setScene(gameDisplay.getScene());
					primaryStage.show();

					gameDisplay.start();

				}catch(Exception ex) {
					ex.printStackTrace();

				}

			}
	   });

	    menuroot.getChildren().add(button);

		Scene menu = new Scene(menuroot);

		// setup and show stage
		primaryStage.setScene(menu);
		primaryStage.setTitle("Project C");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	/**
	 * loads test map and launches program
	 */
	public static void main(String[] args) {

		launch(args);

	}

	/**
	 * inner class that runs a timer on an fps and holds Pane object of all displayed game shapes
	 * every game frame, the game loop steps are executed to run the game
	 *
	 * optional debugOverlay for testing
	 */

}
