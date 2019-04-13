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
                Map currentMap = new base.Map(null, carList, driverList, 200, 150);

                //call methods from MapIO class to load from chosen file
                try {ResourceIO.loadStaticObstacle("resource\\map" + mapName + "-data\\StaticObstacle.txt", currentMap);
                }catch (FileNotFoundException f) {System.out.println("no resource\\map" + mapName + "-data\\StaticObstacle.txt");}

                try {ResourceIO.loadMissilePickup("resource\\map" + mapName + "-data\\MissilePickUp.txt", currentMap);
                }catch (FileNotFoundException f) {System.out.println("no resource\\map" + mapName + "-data\\MissilePickUp.txt");}

                try {ResourceIO.loadSpeedboostPickup("resource\\map" + mapName + "-data\\SpeedBoostPickup.txt", currentMap);
                }catch (FileNotFoundException f) {System.out.println("no resource\\map" + mapName + "-data\\SpeedBoostPickup.txt");}

                try {ResourceIO.loadFinishLine("resource\\map" + mapName + "-data\\FinishLine.txt", currentMap);
                }catch (FileNotFoundException f) {System.out.println("no resource\\map" + mapName + "-data\\FinishLine.txt");}

                try {ResourceIO.loadSpeedboostTile("resource\\map" + mapName + "-data\\SpeedBoostTile.txt", currentMap);
                }catch (FileNotFoundException f) {System.out.println("no resource\\map" + mapName + "-data\\SpeedBoostTile.txt");}

                try {ResourceIO.loadWall("resource\\map" + mapName + "-data\\Wall.txt", currentMap);
                }catch (FileNotFoundException f) {System.out.println("no resource\\map" + mapName + "-data\\Wall.txt");}

                try {ResourceIO.loadCheckpoint("resource\\map" + mapName + "-data\\Checkpoint.txt", currentMap);
                }catch (FileNotFoundException f) {System.out.println("no resource\\map" + mapName + "-data\\Checkpoint.txt");}

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
				Car newCar = ResourceIO.loadCar(carName);
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
