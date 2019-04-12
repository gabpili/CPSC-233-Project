import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

import gameobj.*;

import java.util.ArrayList;

public class MapIO{
	/**
	 * Method takes in a file name and a map. Using try/catch, the method reads the file name
	 * and takes data from it in order to create a new object. If the amount of arugments
	 * are insufficient to create a new StaticObstacle, the exception will be caught and the
	 * application will crash.
	 */
	public static base.Map loadStaticObstacle(String fileName, base.Map currentMap) throws FileNotFoundException, IOException, 
	IllegalArgumentException{
	    	try{
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
			catch(IllegalArgumentException e){
				System.out.println("The file " + fileName + " contains improper number of attributes to create static object");
				System.exit(0);
			}
			return currentMap;

	    }
		/**
		 * loadMissilePickup Method takes in a file name and a map. Using try/catch, the method reads the file name
		 * and takes data from it in order to create a new object then returns the currentMap. If the amount of arguments
		 * are insufficient to create the new MissilePickup object, the exception will be caught and the
		 * application will crash.
		 */
	    public static base.Map loadMissilePickup(String fileName, base.Map currentMap) throws FileNotFoundException, IOException, 
	    IllegalArgumentException{
	    	try{
				File mapFile = new File(fileName);
				BufferedReader inputStream = new BufferedReader(new FileReader(mapFile));

			    String line;
				while ((line = inputStream.readLine()) != null) {	            	
					// colon denotes end of array
					if (line.endsWith(":")){
						String[] values = line.split(":");						
						String str = " ";
						//convert to string in order to split again; create an arraylist that will contain arguments
						for (String element : values){
							str += element;
							ArrayList<Double> arguments = new ArrayList<Double>();

							//get rid of commas and add those elements into arguments list
							for (String e: str.split(",")){
								arguments.add(Double.valueOf(e));
								
							}

							double x = arguments.get(0);
							double y = arguments.get(1);

							//add missile pickup to map
							currentMap.addBasicGameObject(new MissilePickup(x,y));

						}
					}
		       	}
			}
			catch(IllegalArgumentException e){
				System.out.println("The file " + fileName + " contains improper number of attributes to create missile pickup object");
				System.exit(0); //crash the game if there aren't enough arguments to create object
			}
			return currentMap;
	    }

		/**
		 * loadSpeedboostPickup Method takes in a file name and a map. Using try/catch, the method reads the file name
		 * and takes data from it in order to create a new object then returns the currentMap. If the amount of arguments
		 * are insufficient to create a new SpeedboostPickup object, the exception will be caught and the
		 * application will crash.
		 */
	    public static base.Map loadSpeedboostPickup(String fileName, base.Map currentMap) throws FileNotFoundException, 
	    IOException, IllegalArgumentException{
	    	try{
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
							ArrayList<Double> arguments = new ArrayList<Double>();

							//get rid of commas and add those elements into the arguments list
							for (String e: str.split(",")){
								arguments.add(Double.valueOf(e.trim()));
								
							}

							double x = arguments.get(0);
							double y = arguments.get(1);

							//add speedboost pickup to map
							currentMap.addBasicGameObject(new SpeedboostPickup(x,y));

						}
					}
		       	}
			}
			catch(IllegalArgumentException e){
				System.out.println("The file " + fileName + " contains improper number of attributes to create speedboost pickup object");
				System.exit(0);
			}
			return currentMap;
	    }

		/**
		 * loadFinishLine Method takes in a file name and a map. Using try/catch, the method reads the file name
		 * and takes data from it in order to create a new object then returns the currentMap. If the amount of arguments
		 * are insufficient to create a new FinishLine object, the exception will be caught and the
		 * application will crash.
		 */
	    public static base.Map loadFinishLine(String fileName, base.Map currentMap) throws FileNotFoundException, IOException,
	     IllegalArgumentException{
	    	try{
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

							double x = Double.parseDouble(arguments.get(0));
							double y = Double.parseDouble(arguments.get(1));
							String name = arguments.get(2);
							double x2 = Double.parseDouble(arguments.get(3));
							double y2 = Double.parseDouble(arguments.get(4));
							int lastNumber = Integer.parseInt(arguments.get(5));

							//add finish line to map
							currentMap.addBasicGameObject(new FinishLine(x, y, name, x2, y2, lastNumber));
						}
					}	
		       	}
			}
			catch(IllegalArgumentException e){
				System.out.println("The file " + fileName + " contains improper number of attributes to create static object");
				System.exit(0);
			}
			return currentMap;
	    }

		/**
		 * loadSpeedboostTile Method takes in a file name and a map. Using try/catch, the method reads the file name
		 * and takes data from it in order to create a new object then returns the currentMap. If the amount of arguments
		 * are insufficient to create the new SpeedboostTile object, the exception will be caught and the
		 * application will crash.
		 */
	 	public static base.Map loadSpeedboostTile(String fileName, base.Map currentMap) throws FileNotFoundException, IOException, IllegalArgumentException{
	    	try{
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
							ArrayList<Double> arguments = new ArrayList<Double>();

							//get rid of commas and add those elements into the arguments list
							for (String e: str.split(",")){
								arguments.add(Double.valueOf(e.trim()));
							}

							double x = arguments.get(0);
							double y = arguments.get(1);
							double direction = arguments.get(2);

							//add speedboost tile to map
							currentMap.addBasicGameObject(new SpeedboostTile(x, y, Math.toRadians(direction)));

						}
					}
		       	}
			}
			catch(IllegalArgumentException e){
				System.out.println("The file " + fileName + " contains improper number of attributes to create static object");
				System.exit(0);
			}

		return currentMap;
		}
		
		/**
		 * Method takes in a file name and a map. Using try/catch, the method reads the file name
		 * and takes data from it in order to create a new object. If the amount of arugments
		 * are insufficient to create new Walls, the exception will be caught and the
		 * application will crash.
		 */
		public static base.Map loadWall(String fileName, base.Map currentMap) throws FileNotFoundException, IOException, IllegalArgumentException{
	    	try{
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

							//add walls to map
							currentMap.addBasicGameObject(new Wall(startX, startY, name, endX, endY));

						}
					}
		       	}
			}
			catch(IllegalArgumentException e){
				System.out.println("The file" + fileName + "contains improper number of attributes to create a wall");
				System.exit(0);
			}

		return currentMap;
		}
		
		/**
		 * Method takes in a file name and a map. Using try/catch, the method reads the file name
		 * and takes data from it in order to create a new object. If the amount of arugments
		 * are insufficient to create a new Checkpoint, the exception will be caught and the
		 * application will crash.
		 */
		public static base.Map loadCheckpoint(String fileName, base.Map currentMap) throws FileNotFoundException, IOException, IllegalArgumentException{
	    	try{
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
			catch(IllegalArgumentException e){
				System.out.println("The file " + fileName + " contains improper number of attributes to create checkpoints");
				System.exit(0);
			}

		return currentMap;
		}

	}