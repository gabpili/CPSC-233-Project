import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
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
	/**
	 * scale of pixels to metres
	 */
	private static int scale = 4;

	/** 
	 * current loaded Map object
	 * holds all info about physical objects
	 */
	private static Map currentMap;

	/**
	 * list of pairs of object to its shape on display
	 * object references are used purely for accessing shape information and comparisons, and are not mutated 
	 */
    private static HashMap<BasicGameObject, Shape> objDisplay = new HashMap<BasicGameObject, Shape>();

    /**
     * list of javafx KeyCodes caught by key press events
     */
	private static ArrayList<KeyCode> keysPressed = new ArrayList<KeyCode>();

	/**
	 * test Car object
	 */
	private static Car mainCar;


	/**
	 * detects collision between each DynamicGameObject and every BasicGameObject
	 * colliding objects have collisions resolved as per the BasicGameObject's specific method of resolution
	 */
	private static void collisionStep(double time) {
		currentMap.collisionDetectResolveAll();
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
	 * @return all changed objects to have their shape counterparts updated
	 */
	private static ArrayList<BasicGameObject> tickStep(double time) {
		return currentMap.tickAll(time);

	}

	/**
	 * sets coordinates of Line shape based on Wall object
	 */
    private static void updateDisplayShape(Line l, Wall o) {
        l.setStartX(o.getStartX() * scale);
        l.setStartY(o.getStartY() * scale);
        l.setEndX(o.getEndX() * scale);
        l.setEndY(o.getEndY() * scale);

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
	private static ArrayList<Shape> displayStep(ArrayList<BasicGameObject> toUpdate) {
		ArrayList<Shape> shapesToRemove = new ArrayList<Shape>();

		if (!toUpdate.isEmpty()) {

	        for (BasicGameObject o: toUpdate) {
	            if (!currentMap.getBasicObjList().contains(o) &&
					!currentMap.getDynamicObjList().contains(o)) {
	            	shapesToRemove.add(objDisplay.get(o));
	                objDisplay.remove(o);

	            }else if (o instanceof Wall) {
                    updateDisplayShape((Line) objDisplay.get(o), (Wall) o);

                }else {
                    updateDisplayShape((Rectangle) objDisplay.get(o), o);

                }
	        }
		}

		return shapesToRemove;
	}

	/**
	 * takes list of objects and creates hashmap pairs of object to shape
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
        objDisplay.putAll(createDisplayShapes(currentMap.getBasicObjList()));
        objDisplay.putAll(createDisplayShapes(currentMap.getDynamicObjList()));

        GameDisplay gameDisplay = new GameDisplay(100, true);

        // add to root
        root.getChildren().add(gameDisplay.getDebugOverlay());
        root.getChildren().add(gameDisplay.getGameWindow());

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

        gameDisplay.start();

	}

	/**
	 * loads test map and launches program
	 */
	public static void main(String[] args) {
		ArrayList<Driver> interfaceList = new ArrayList<Driver>();
		interfaceList.add(new Driver(new Car(50, 5, "Magic School Bus", Math.toRadians(90), 1, 2.4, 8.2, 0.3)));
		mainCar = interfaceList.get(0).getAttachedCar();

		ArrayList<Car> carList = new ArrayList<Car>();
		carList.add(mainCar);
		carList.add(new Car(mainCar));
		carList.add(new Car(mainCar));
		carList.add(new Car(mainCar));

		currentMap = PresetMaps.loadMap1(carList, interfaceList);

		launch(args);

	}

	/**
	 * inner class that runs a timer on an fps and holds Pane object of all displayed game shapes
	 * every game frame, the game loop steps are executed to run the game
	 *
	 * optional debugOverlay for testing
	 */
	class GameDisplay extends AnimationTimer {
		private final int fpsLimit; // 0 < fps
	    private double gameFps; // actual game fps
	    private double animFps; // actual animation fps
	    private long gameLast = 0;
	    private long animLast = 0;
	    private int count = 0;

	    private Pane gameWindow = new Pane();

	    private Pane debugOverlay = new Pane();
		private Label carInfo = new Label();
		private	Label collidingInfo = new Label();
	    private Label fpsLabel = new Label();
	    private Label gameFpsLabel = new Label();
	    private boolean showDebugOverlay;

	    private Driver mainDriver;

	    public GameDisplay(int fpsLimit, boolean showDebugOverlay) {
	    	this.fpsLimit = fpsLimit;

	        gameWindow.getChildren().addAll(objDisplay.values());

			debugOverlay.getChildren().add(carInfo);
			debugOverlay.getChildren().add(collidingInfo);
			debugOverlay.getChildren().add(fpsLabel);
	        debugOverlay.getChildren().add(gameFpsLabel);
	        carInfo.setLayoutY(300);
			collidingInfo.setLayoutY(315);
	        gameFpsLabel.setLayoutY(20);

	        this.showDebugOverlay = showDebugOverlay;
			
			mainDriver = currentMap.getDriverList().get(0);

	    }

	    public Pane getGameWindow() {
	    	return gameWindow;

	    }

	    public Pane getDebugOverlay() {
	    	return debugOverlay;

	    }

	    /**
	     * called every animation frame, which java attempts to keep at 60 fps
	     * the time between frames and fps are kept track to maintain given fps limit
	     * the purpose of a lower fps limit is to possibly lower load on cpu
	     */
	    @Override
	    public void handle(long now){
	        // maintain game fps
	        animFps = 1000000000d / (now - animLast);

	        if (count > animFps / fpsLimit){
	            gameFps = 1000000000d / (now - gameLast);

	            gameFrame((now - gameLast) / 1000000000d);

	            if (showDebugOverlay) {
	                fpsLabel.setText("" + (int)(animFps));
	                gameFpsLabel.setText("" + (int)(gameFps));
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
	    	// 4 game loop process
	        collisionStep(time);
	        inputStep(time);
	        ArrayList<BasicGameObject> toUpdate = tickStep(time);
	        gameWindow.getChildren().removeAll(displayStep(toUpdate));

	        // update debug overlay labels
			carInfo.setText("" + mainCar);
			collidingInfo.setText("Section: " + mainDriver.getSection()
				+ " Lap " + mainDriver.getLap()
				+ "\n" + currentMap.detectSATCollisions(mainCar, currentMap.getBasicObjList())
				+ "\n" + currentMap.detectSATCollisions(mainCar, currentMap.getDynamicObjList()));

	    }
	}
}


