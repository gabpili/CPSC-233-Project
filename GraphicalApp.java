import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;


public class GraphicalApp extends Application {
	private static int scale = 4;
	private static Map currentMap;
    private static HashMap<BasicGameObject, Shape> objDisplay = new HashMap<BasicGameObject, Shape>();
	private static ArrayList<BasicGameObject> toUpdate = new ArrayList<BasicGameObject>();
	private static ArrayList<KeyCode> keysPressed = new ArrayList<KeyCode>();
    private static boolean showFpsOverlay = true;
	private static Car mainCar = new Car(50, 5, "Magic School Bus", Math.toRadians(90), 1, 2.4, 8.2, 0.3);

	/**
	 * detects collision between each DynamicGameObject and every BasicGameObject
	 * colliding objects have collisions resolved as per the BasicGameObject's specific method of resolution
	 */
	private static void collisionStep(double time) {
		for (DynamicGameObject o: currentMap.getDynamicObjList()) {
			for (BasicGameObject s: currentMap.detectSATCollisions(o, currentMap.getStaticObjList())) {
				s.resolveCollision(o);
			}

			for (BasicGameObject d: currentMap.detectSATCollisions(o, currentMap.getDynamicObjList())) {
				d.resolveCollision(o);
			}
		}
	}

	/**
	 * passes in input as characters into map
	 */
	private static void inputStep(double time) {
		if (!keysPressed.isEmpty()) {
			ArrayList<Character> inputCharacters = new ArrayList<Character>();

			for (KeyCode k: keysPressed) {
				if (k.isLetterKey()) {
					inputCharacters.add(Character.valueOf(k.toString().charAt(0)));
				}
			}
			currentMap.giveInput(inputCharacters, time);
		}else {
			currentMap.giveInput("", time);
		}
	}

	/**
	 * ticks all objects, handling all flags and changing positions
	 * all objects that were changed are added to toUpdate so their shape counterparts can be updated
	 */
	private static void tickStep(double time) {
		toUpdate = currentMap.tickAll(time);
	}

	/**
	 * sets coordinates of Line shape based on Wall object
	 */
    private static void updateDisplayShape(Line l, Wall o) {
        l.setStartX(o.getX() * scale);
        l.setStartY(o.getY() * scale);
        l.setEndX(o.getX2() * scale);
        l.setEndY(o.getY2() * scale);
    }

    /**
     * sets coordinate and width/height of Rectangle shape based on given object
     */
    private static void updateDisplayShape (Rectangle r, BasicGameObject o) {
        r.setX((o.getX() - o.getHalfW()) * scale);
        r.setY((o.getY() - o.getHalfH()) * scale);
		if (o instanceof DynamicGameObject) {
			DynamicGameObject o_ = (DynamicGameObject) o;
			r.setRotate(Math.toDegrees(o_.getDirection()) + 90);
		}
    }

    /**
     * updates every shape of objects that were put to be updated in toUpdate
     * @return shapes to remove from windowPane
     */
	private static ArrayList<Shape> displayStep() {
		ArrayList<Shape> toRemove = new ArrayList<Shape>();
		if (!toUpdate.isEmpty()) {

	        for (BasicGameObject o: toUpdate) {
	            if (!currentMap.getStaticObjList().contains(o) &&
					!currentMap.getDynamicObjList().contains(o)) {
	            	toRemove.add(objDisplay.get(o));
	                objDisplay.remove(o);
	            }else {
	                if (o instanceof Wall) {
	                    updateDisplayShape((Line) objDisplay.get(o), (Wall) o);
	                }else {
	                    updateDisplayShape((Rectangle) objDisplay.get(o), o);
	                }
	            }
	        }
		}

		return toRemove;
	}

	/**
	 * takes list of objects and returns hashmap pairs of object to shape
	 */
    private static HashMap<BasicGameObject, Shape> createDisplayShapes(
		ArrayList<? extends BasicGameObject> objList) {
        HashMap<BasicGameObject, Shape> temp = new HashMap<BasicGameObject, Shape>();
        for (BasicGameObject o: objList) {
            if (o instanceof Wall) {
                Line newShape = new Line();
				updateDisplayShape(newShape, (Wall) o);
                temp.put(o, newShape);
            } else {
                Rectangle newShape = new Rectangle();
				updateDisplayShape(newShape, o);
                newShape.setWidth(o.getHalfW() * 2 * scale);
                newShape.setHeight(o.getHalfH() * 2 * scale);
                temp.put(o, newShape);
            }
        }

        return(temp);
    }

    /**
     * starts program with stage/scene setup of all of its nodes
     * starts animation timer to loop program through the game loop steps
     */
	public void start (Stage primaryStage) throws Exception {
		Group root = new Group();

		// game window with physical game objects
        Pane gameWindow = new Pane();
        objDisplay.putAll(createDisplayShapes(currentMap.getStaticObjList()));
        objDisplay.putAll(createDisplayShapes(currentMap.getDynamicObjList()));
        gameWindow.getChildren().addAll(objDisplay.values());

		// testing info screen
		Pane infoScreen = new Pane();
		Label carInfo = new Label();
		Label collidingInfo = new Label();
		infoScreen.getChildren().add(carInfo);
		infoScreen.getChildren().add(collidingInfo);
		carInfo.setLayoutY(300);
		collidingInfo.setLayoutY(315);

		// fps overlay
        Group fpsOverlay = new Group();
        Label fpsLabel = new Label();
        Label gameFpsLabel = new Label();
        fpsOverlay.getChildren().add(fpsLabel);
        fpsOverlay.getChildren().add(gameFpsLabel);
        gameFpsLabel.setLayoutY(20);

        // add to root
        root.getChildren().add(gameWindow);
		root.getChildren().add(infoScreen);
		root.getChildren().add(fpsOverlay);
        Scene scene = new Scene(root, currentMap.getWidth() * scale,
			currentMap.getHeight() * scale);
		// handle key press + release
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			/**
			 * adds KeyCode to keysPressed if it isn't already in it
			 */
			@Override
			public void handle(KeyEvent e) {
				KeyCode k = e.getCode();
				if (!keysPressed.contains(k)) {
					keysPressed.add(k);
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			/**
			 * removes KeyCode in keysPressed if it exists
			 */
			@Override
			public void handle(KeyEvent e) {
				KeyCode k = e.getCode();
				if (keysPressed.contains(k)) {
					keysPressed.remove(k);
				}
			}
		});

		// setup and show stage
		primaryStage.setScene(scene);
        primaryStage.setTitle("Project C");
        primaryStage.show();


        AnimationTimer animator = new AnimationTimer(){
            private final int idealGameFps = 30; // 0 < fps <= 60
            private double gameFps; // actual game fps
            private double animFps; // actual animation fps
            private long gameLast = 0;
            private long animLast = 0;
            private int count = 0;

            /**
             * called every animation frame, which java attempts to keep at 60 fps
             * time between frames and fps are kept track to maintain an ideal game fps
             * ideal game fps, which is lower than animation fps, is to lower load on cpu
             */
            @Override
            public void handle(long now){
                // maintain game fps
                animFps = 1000000000d / (now - animLast);

                if (count > animFps / idealGameFps){
                    gameFps = 1000000000d / (now - gameLast);

                    gameFrame((now - gameLast) / 1000000000d);

                    if (showFpsOverlay) {
                        gameFpsLabel.setText("" + (int)(gameFps));
	                    fpsLabel.setText("" + (int)(animFps));
                    }
                    gameLast = now;
                    count = 0;
                }
                animLast = now;
                count++;
            }

            /**
             * steps through the 4 game loop process and updates info labels
             */
            private void gameFrame(double time) {
                collisionStep(time);
                inputStep(time);
                tickStep(time);
                gameWindow.getChildren().removeAll(displayStep());

				carInfo.setText("" + mainCar);
				collidingInfo.setText("Section: " + currentMap.getDriverList().get(0).getSection()
					+ " Lap " + currentMap.getDriverList().get(0).getLap()
					+ "\n" + currentMap.detectSATCollisions(mainCar, currentMap.getStaticObjList())
					+ "\n" + currentMap.detectSATCollisions(mainCar, currentMap.getDynamicObjList()));

            }
        };

        animator.start();
	}

	/**
	 * loads test map and launches program
	 */
	public static void main(String[] args) {
		ArrayList<Driver> driverList = new ArrayList<Driver>();
		driverList.add(new Driver(mainCar));
		ArrayList<Car> carList = new ArrayList<Car>();
		carList.add(mainCar);
		carList.add(new Car(mainCar));
		carList.add(new Car(mainCar));
		carList.add(new Car(mainCar));

		currentMap = PresetMaps.loadMap1(carList, driverList);
		/*
		currentMap = new Map(driverList, 150, 200);

		currentMap.addDynamicGameObject(mainCar);

		for (int i=0; i<5; i++) {
			currentMap.addBasicGameObject(new StaticObstacle(48, 10 + 4 * i, "RCone" + i, 0.15, 0.15));
			currentMap.addBasicGameObject(new StaticObstacle(52, 5 + 4 * i, "LCone" + i, 0.15, 0.15));
		}

		currentMap.addBasicGameObject(new Wall(60, 10, "wall0", 80, 60));
		currentMap.addBasicGameObject(new FinishLine(10, 150, "finish", 30, 150, 3));
		currentMap.addBasicGameObject(new Checkpoint(50, 150, "cp1", 50, 170, 1));
		currentMap.addBasicGameObject(new Checkpoint(80, 150, "cp2", 80, 170, 2));
		currentMap.addBasicGameObject(new Checkpoint(130, 150, "cp3", 145, 165, 3));*/

		launch(args);

	}


}
