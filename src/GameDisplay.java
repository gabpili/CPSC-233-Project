import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

import base.Map;
import base.Driver;
import gameobj.BasicGameObject;
import gameobj.Wall;
import gameobj.DynamicGameObject;
import gameobj.Car;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import javafx.animation.AnimationTimer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GameDisplay extends AnimationTimer {
    /**
     * scale of pixels to metres
     */
    private int scale = 4;

    /**
     * current loaded Map object
     * holds all info about physical objects
     */
    private Map currentMap;

    /**
     * list of object:shape pairs
     * object references are used purely for accessing shape information and comparisons, and are not mutated
     */
    private HashMap<BasicGameObject, Shape> objDisplay = new HashMap<BasicGameObject, Shape>();

    /**
     * list of javafx KeyCodes caught by key press event handlers
     */
    private ArrayList<KeyCode> keysPressed = new ArrayList<KeyCode>();

    private Pane gameWindow = new Pane();
    private Scene scene;

    private Pane debugOverlay = new Pane();
    private Label carInfo = new Label();
    private	Label collidingInfo = new Label();
    private Label fpsLabel = new Label();
    private Label gameFpsLabel = new Label();
    private boolean showDebugOverlay;

    /**
     * test driver
     */
    private Driver mainDriver;
    /**
     * test car
     */
    private Car mainCar;

    private final int fpsLimit; // 0 < fps
    private double gameFps; // actual game fps
    private double animFps; // actual animation fps
    private long gameLast = 0;
    private long animLast = 0;
    private int count = 0;

    private boolean paused = false;

    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private PrintStream out = new PrintStream(buffer);

    /**
     * Constructor that takes in 3 arguments
     */
    public GameDisplay(Map currentMap, boolean showDebugOverlay, int fpsLimit) {
        debugOverlay.getChildren().add(carInfo);
        debugOverlay.getChildren().add(collidingInfo);
        debugOverlay.getChildren().add(fpsLabel);
        debugOverlay.getChildren().add(gameFpsLabel);
        carInfo.setLayoutY(100);
        collidingInfo.setLayoutY(315);
        gameFpsLabel.setLayoutY(20);

        this.currentMap = currentMap;
        this.showDebugOverlay = showDebugOverlay;
        this.fpsLimit = fpsLimit;

        // game window with physical game objects
        objDisplay.putAll(createDisplayShapes(currentMap.getBasicObjList()));
        objDisplay.putAll(createDisplayShapes(currentMap.getDynamicObjList()));

        gameWindow.getChildren().addAll(objDisplay.values());
        gameWindow.getChildren().add(debugOverlay);

        scene = new Scene(gameWindow, currentMap.getWidth() * scale, currentMap.getHeight() * scale);
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

        mainDriver = currentMap.getDriverList().get(0);
        mainCar = mainDriver.getAttachedCar();
        if (showDebugOverlay) {
        	System.setOut(out);

        }
    }

    /**
     * Getter method that returns the value of scene
     */
    public Scene getScene() {
        return scene;

    }

    /**
     * detects collision between each DynamicGameObject and every BasicGameObject
     * colliding objects have collisions resolved as per the BasicGameObject's specific method of resolution
     */
    private void collisionStep(double time) {
        currentMap.collisionDetectResolveAll();

    }

    /**
     * passes in input as characters into map
     */
    private void inputStep(double time) {
        if (!keysPressed.isEmpty()) {
            ArrayList<Character> inputCharacters = new ArrayList<Character>();

            for (KeyCode k: keysPressed) {
                if (k.isLetterKey()) {
                    System.out.println(k);
                    if (k.toString().charAt(0) == 'K') {
                        paused = true;
                    }
                    if (k.toString().charAt(0) == 'J') {
                        paused = false;
                    }
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
    private ArrayList<BasicGameObject> tickStep(double time) {
        return currentMap.tickAll(time);

    }

    /**
     * sets coordinates of Line shape based on Wall object
     */
    private void updateDisplayShape(Line l, Wall o) {
        l.setStartX(o.getStartX() * scale);
        l.setStartY(o.getStartY() * scale);
        l.setEndX(o.getEndX() * scale);
        l.setEndY(o.getEndY() * scale);

    }

    /**
     * sets coordinate and width/height of Rectangle shape based on given object
     */
    private void updateDisplayShape (Rectangle r, BasicGameObject o) {
        r.setX((o.getX() - o.getHalfW()) * scale);
        r.setY((o.getY() - o.getHalfH()) * scale);

        r.setRotate(Math.toDegrees(o.getDirection().theta()) + 90);

    }

    private Shape createDisplayShape(BasicGameObject o) {
        if (o instanceof Wall) {
            Line newShape = new Line();
            updateDisplayShape(newShape, (Wall)o);
            return newShape;

        }else {
            Rectangle newShape = new Rectangle();
            updateDisplayShape(newShape, o);
            newShape.setWidth(o.getHalfW() * 2 * scale);
            newShape.setHeight(o.getHalfH() * 2 * scale);
            return newShape;

        }
    }

    /**
     * takes list of objects and creates hashmap pairs of object to shape
     */
    private HashMap<BasicGameObject, Shape> createDisplayShapes(
        ArrayList<? extends BasicGameObject> objList) {

        HashMap<BasicGameObject, Shape> temp = new HashMap<BasicGameObject, Shape>();

        for (BasicGameObject o: objList) {
            temp.put(o, createDisplayShape(o));

        }

        return(temp);

    }

    /**
     * updates every shape of objects that were put to be updated in toUpdate
     * @return shapes to remove from windowPane
     */
    private void displayStep(ArrayList<BasicGameObject> toUpdate) {
        if (!toUpdate.isEmpty()) {

            for (BasicGameObject o: toUpdate) {
                if (!currentMap.getBasicObjList().contains(o) &&
                    !currentMap.getDynamicObjList().contains(o)) {
                    gameWindow.getChildren().remove(objDisplay.get(o));
                    objDisplay.remove(o);

                }else if (!objDisplay.containsKey(o)) {
                    Shape newShape = createDisplayShape(o);
                    objDisplay.put(o, newShape);
                    gameWindow.getChildren().add(newShape);

                }else if (o instanceof Wall) {
                    updateDisplayShape((Line) objDisplay.get(o), (Wall) o);

                }else {
                    updateDisplayShape((Rectangle) objDisplay.get(o), o);

                }
            }
        }
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
        if (paused) {
            time = 0;
        }
    	//time /= 6;
        // 4 game loop process
        ArrayList<BasicGameObject> toUpdate = tickStep(time);
        collisionStep(time);
        inputStep(time);
        displayStep(toUpdate);

        // update debug overlay labels
        carInfo.setText("" + mainCar
        	+ "\n" + buffer.toString());
        ArrayList<BasicGameObject> testColliding = new ArrayList<BasicGameObject>();
        System.out.println(currentMap.getBasicObjList());
        for (BasicGameObject o: currentMap.getBasicObjList()) {
            System.out.print(0);
            if (o instanceof Wall) {
                if (currentMap.testSAT(mainCar, (Wall) o) != null) testColliding.add(o);
            }else {
                if (currentMap.testSAT(mainCar, o) != null) testColliding.add(o);
            }

        }
        collidingInfo.setText("Section: " + mainDriver.getSection()
            + " Lap " + mainDriver.getLap()
            + "\n" + testColliding);

        buffer.reset();

    }
}
