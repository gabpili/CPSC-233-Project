import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

import gameobj.*;
import base.Map;

import java.util.ArrayList;

public class ResourceIO{
    /*
    public static File new File(String resource) throws FileNotFoundException, IOException {
        System.out.println("resource\\" + resource);
        URL fileURL = ClassLoader.getSystemClassLoader().getResource("resource\\" + resource);
        if (fileURL == null) {
            throw new FileNotFoundException("resource\\" + resource + " not found");

        }else {
            System.out.println(fileURL.getPath());
            return new File(fileURL.getFile());

        }

    }*/

    /**
    * creates a Car out of values within a text file with the name of the Car to be created
    */
    public static Car loadCar(String carName) throws FileNotFoundException, IOException, 
        IllegalArgumentException {
        File carFile = new File("resource\\car-data\\" + carName + ".txt");
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
	 * Method takes in a file name and a map. Using try/catch, the method reads the file name
	 * and takes data from it in order to create a new object. If the amount of arugments
	 * are insufficient to create the new StaticObstacle, the exception will be caught and the
	 * application will crash.
	 */
	public static void loadStaticObstacle(String fileName, Map currentMap) throws FileNotFoundException, IOException, 
        IllegalArgumentException {
		File mapFile = new File(fileName);
		BufferedReader inputStream = new BufferedReader(new FileReader(mapFile));

	    String line;
		while ((line = inputStream.readLine()) != null) {	            	
			//denotes end of array
			if (line.endsWith(":")) {
				String[] values = line.split(":");						
				String str = " ";
				//convert to string in order to split again; create an arraylist
				for (String element : values){
					str += element;
					ArrayList<String> arguments = new ArrayList<String>();

					//get rid of commas and add those elements into the arguments list
					for (String e: str.split(",")){
						arguments.add(e.trim());
						
					}

					double x = Double.parseDouble(arguments.get(0));
					double y = Double.parseDouble(arguments.get(1));
					String name = arguments.get(2);
					double halfW = Double.parseDouble(arguments.get(3));
					double halfH = Double.parseDouble(arguments.get(4));
					double mass = Double.parseDouble(arguments.get(5));

					//add static obstacles to map
					currentMap.addBasicGameObject(new StaticObstacle(x, y, name, halfW, halfH, mass));

				}
			}
       	}
    }
	/**
	 * loadMissilePickup Method takes in a file name and a map. Using try/catch, the method reads the file name
	 * and takes data from it in order to create a new object then returns the currentMap. If the amount of arguments
	 * are insufficient to create the new MissilePickup object, the exception will be caught and the
	 * application will crash.
	 */
    public static void loadMissilePickup(String fileName, Map currentMap) throws FileNotFoundException, IOException,
        IllegalArgumentException {
		File mapFile = new File(fileName);
		BufferedReader inputStream = new BufferedReader(new FileReader(mapFile));

	    String line;
		while ((line = inputStream.readLine()) != null) {	            	
			// colon denotes end of array
			if (line.endsWith(":")) {
				String[] values = line.split(":");						
				String str = " ";
				//convert to string in order to split again; create an arraylist that will contain arguments
				for (String element : values) {
					str += element;
					ArrayList<Double> arguments = new ArrayList<Double>();

					//get rid of commas and add those elements into arguments list
					for (String e: str.split(",")) {
						arguments.add(Double.valueOf(e));
						
					}

					double x = arguments.get(0);
					double y = arguments.get(1);

					//add static obstacles to map
					currentMap.addBasicGameObject(new MissilePickup(x,y));

				}
			}
       	}
    }
	/**
	 * loadSpeedboostPickup Method takes in a file name and a map. Using try/catch, the method reads the file name
	 * and takes data from it in order to create a new object then returns the currentMap. If the amount of arguments
	 * are insufficient to create the new SpeedboostPickup object, the exception will be caught and the
	 * application will crash.
	 */
    public static void loadSpeedboostPickup(String fileName, Map currentMap) throws FileNotFoundException, IOException,
        IllegalArgumentException {
		File mapFile = new File(fileName);
		BufferedReader inputStream = new BufferedReader(new FileReader(mapFile));

	    String line;
		while ((line = inputStream.readLine()) != null) {	            	
			//denotes end of array
			if (line.endsWith(":")){
				String[] values = line.split(":");						
				String str = " ";
				//convert to string in order to split again; create an arraylist
				for (String element : values) {
					str += element;
					ArrayList<Double> arguments = new ArrayList<Double>();

					//get rid of commas and add those elements into the sObj list
					for (String e: str.split(",")) {
						arguments.add(Double.valueOf(e.trim()));
						
					}

					double x = arguments.get(0);
					double y = arguments.get(1);

					//add static obstacles to map
					currentMap.addBasicGameObject(new SpeedboostPickup(x,y));

				}
			}
       	}
    }
	/**
	 * loadFinishLine Method takes in a file name and a map. Using try/catch, the method reads the file name
	 * and takes data from it in order to create a new object then returns the currentMap. If the amount of arguments
	 * are insufficient to create the new FinishLine object, the exception will be caught and the
	 * application will crash.
	 */
    public static void loadFinishLine(String fileName, Map currentMap) throws FileNotFoundException, IOException,
        IllegalArgumentException {
		File mapFile = new File(fileName);
		BufferedReader inputStream = new BufferedReader(new FileReader(mapFile));

	    String line;
		while ((line = inputStream.readLine()) != null) {	            	
			//denotes end of array
			if (line.endsWith(":")) {
				String[] values = line.split(":");						
				String str = " ";
				//convert to string in order to split again; create an arraylist
				for (String element : values) {
					str += element;
					ArrayList<String> arguments = new ArrayList<String>();

					//get rid of commas and add those elements into the sObj list
					for (String e: str.split(",")) {
						arguments.add(e.trim());
						
					}

					double x = Double.parseDouble(arguments.get(0));
					double y = Double.parseDouble(arguments.get(1));
					String name = arguments.get(2);
					double x2 = Double.parseDouble(arguments.get(3));
					double y2 = Double.parseDouble(arguments.get(4));
					int lastNumber = Integer.parseInt(arguments.get(5));

					//add static obstacles to map
					currentMap.addBasicGameObject(new FinishLine(x, y, name, x2, y2, lastNumber));
				}
			}	
       	}
    }
	/**
	 * loadSpeedboostTile Method takes in a file name and a map. Using try/catch, the method reads the file name
	 * and takes data from it in order to create a new object then returns the currentMap. If the amount of arguments
	 * are insufficient to create the new SpeedboostTile object, the exception will be caught and the
	 * application will crash.
	 */
 	public static void loadSpeedboostTile(String fileName, Map currentMap) throws FileNotFoundException, IOException,
        IllegalArgumentException {
		File mapFile = new File(fileName);
		BufferedReader inputStream = new BufferedReader(new FileReader(mapFile));

	    String line;
		while ((line = inputStream.readLine()) != null) {	            	
			//denotes end of array
			if (line.endsWith(":")) {
				String[] values = line.split(":");						
				String str = " ";
				//convert to string in order to split again; create an arraylist
				for (String element : values) {
					str += element;
					ArrayList<Double> arguments = new ArrayList<Double>();

					//get rid of commas and add those elements into the sObj list
					for (String e: str.split(",")) {
						arguments.add(Double.valueOf(e.trim()));

					}

					double x = arguments.get(0);
					double y = arguments.get(1);
					double direction = arguments.get(2);

					//add static obstacles to map
					currentMap.addBasicGameObject(new SpeedboostTile(x, y, Math.toRadians(direction)));

				}
			}
       	}
	}
	public static void loadWall(String fileName, Map currentMap) throws FileNotFoundException, IOException,
        IllegalArgumentException {
		File mapFile = new File(fileName);
		BufferedReader inputStream = new BufferedReader(new FileReader(mapFile));

	    String line;
		while ((line = inputStream.readLine()) != null) {	            	
			//denotes end of array
			if (line.endsWith(":")) {
				String[] values = line.split(":");						
				String str = " ";
				//convert to string in order to split again; create an arraylist
				for (String element : values) {
					str += element;
					ArrayList<String> arguments = new ArrayList<String>();

					//get rid of commas and add those elements into the sObj list
					for (String e: str.split(",")) {
						arguments.add(e.trim());

					}

					double startX = Double.parseDouble(arguments.get(0));
					double startY = Double.parseDouble(arguments.get(1));
					String name = arguments.get(2);
					double endX = Double.parseDouble(arguments.get(3));
					double endY = Double.parseDouble(arguments.get(4));

					//add walls to map
					currentMap.addBasicGameObject(new Wall(startX, startY, name, endX, endY));

				}
			}
       	}
		/**
		 * Method takes in a file name and a map. Using try/catch, the method reads the file name
		 * and takes data from it in order to create a new object. If the amount of arugments
		 * are insufficient to create a new Checkpoint, the exception will be caught and the
		 * application will crash.
		 */
		public static void loadCheckpoint(String fileName, base.Map currentMap) throws FileNotFoundException, IOException, IllegalArgumentException{
			File mapFile = new File(fileName);
			BufferedReader inputStream = new BufferedReader(new FileReader(mapFile));

			String line;
			while ((line = inputStream.readLine()) != null) {	            	
				//denotes end of array
				if (line.endsWith(":")){
					String[] values = line.split(":");						
					String str = " ";
					//convert to string in order to split again; create an arraylist
					for (String element : values){
						str += element;
						ArrayList<String> arguments = new ArrayList<String>();

						//get rid of commas and add those elements into the arguments list
						for (String e: str.split(",")){
							arguments.add(e.trim());
						}

						double startX = Double.parseDouble(arguments.get(0));
						double startY = Double.parseDouble(arguments.get(1));
						String name = arguments.get(2);
						double endX = Double.parseDouble(arguments.get(3));
						double endY = Double.parseDouble(arguments.get(4));
						int number = Integer.parseInt(arguments.get(5));

						//add checkpoints to map
						currentMap.addBasicGameObject(new Checkpoint(startX, startY, name, endX, endY, number));

					}
				}
			}
		}		
	}

    public static void main(String[] args) {
        try {
            File test = new File("car-data\\Normal Car.txt");

        }catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}
