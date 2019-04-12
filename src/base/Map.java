package base;

import java.util.ArrayList;
import java.lang.Math;

import gameobj.*;

public class Map{
    private ArrayList<BasicGameObject> basicObjList = new ArrayList<BasicGameObject>();
    private ArrayList<DynamicGameObject> dynamicObjList = new ArrayList<DynamicGameObject>();
    private ArrayList<Driver> driverList = new ArrayList<Driver>();

    private int width;
    private int height;

	/**
	 * Constructor takes in driverList of type Driver as well as integers
	 * width and height and uses a constructor within the class to initialize
	 * the values given values.
	 */
    public Map(ArrayList<Driver> driverList, int width, int height){
        this(null, null, driverList, width, height);

    }

	/**
	 * full constructor
	 */
    public Map(
	    ArrayList<BasicGameObject> basicObjList,
	    ArrayList<DynamicGameObject> dynamicObjList,
	    ArrayList<Driver> driverList, int width, int height) {

        if (basicObjList != null) {
            this.basicObjList.addAll(basicObjList);

        }
        if (dynamicObjList != null) {
            this.dynamicObjList.addAll(dynamicObjList);

        }
        this.driverList.addAll(driverList);
        this.width = width;
        this.height = height;

    }

    /**
	 * method returns list of basic objects
	 * list contains non-moving obstacles and walls
	 */
    public ArrayList<BasicGameObject> getBasicObjList() {
        return basicObjList;

    }

    /**
	 * method returns list of dynamic objects
	 * list contains cars and moving obstacles
	 */
    public ArrayList<DynamicGameObject> getDynamicObjList() {
        return dynamicObjList;

    }

    /**
	 * returns exact list of drivers
	 */
    public ArrayList<Driver> getDriverList() {
      return driverList;

    }

    /**
	 * method returns width
	 */
    public int getWidth() {
        return width;

    }

    /**
	 * method returns height
	 */
    public int getHeight() {
        return height;

    }

    /**
	 * Add given basic object to the BasicGameObject list
	 */
    public void addBasicGameObject(BasicGameObject s1) {
        this.basicObjList.add(s1);

    }

    /**
	 * Add given dynamic object to the DynamicGameObject list
	 */
    public void addDynamicGameObject(DynamicGameObject d1) {
        this.dynamicObjList.add(d1);

    }

	/**
	 *
	 */
    public void giveInput(ArrayList<Character> character, double time) {
        for (Driver i: driverList) {
            i.takeInput(character, time);

        }
    }

	/**
	 *
	 */
  	public void giveInput(String input, double time) {
        ArrayList<Character> inputInChar = new ArrayList<Character>();

        for (int i = 0; i < input.length(); i++) {
            inputInChar.add(Character.valueOf(input.charAt(i)));

        }
        giveInput(inputInChar, time);

    }

	/**
	 *
	 */
    public ArrayList<BasicGameObject> getProximityObjects(DynamicGameObject d, double proximity) {
        ArrayList<BasicGameObject> withinProximity = new ArrayList<BasicGameObject>();

        for (BasicGameObject o: basicObjList) {
            if (o.distance(d) <= proximity) {
            withinProximity.add(o);

            }
        }

        return withinProximity;

    }

    /**
	 * Method will detect collisions using the Axis Align Bounding-Boxes (AABB).
	 * Method takes in a DynamicGameObject named dObj and an array list of type BasicGameObject sObjs.
	 *
	 * Creates a new array list for potential collisions. Then iterates through the given list
	 * using a for loop. Check if the maximum radius (maxR) is less than the total length of x and
	 * maximum raidus of the basic object.
	 *
	 * If all conditions in the if statement are satisfied, the basic object, "s", that would potentially
	 * collide with the dObj is added into type BasicGameObject  array list "potentialCollisions".
	 */
    public ArrayList<BasicGameObject> detectAABB(DynamicGameObject dObj,
        ArrayList<? extends BasicGameObject> sObjs) {

        ArrayList<BasicGameObject> potentialCollisions = new ArrayList<BasicGameObject>();

        for (BasicGameObject s : sObjs) {
            if (dObj.getMaxR() < s.getX() + s.getMaxR() &&
                dObj.getMaxR() + s.getX() > s.getX() &&
                dObj.getMaxR() < s.getY() + s.getMaxR() &&
                dObj.getY() + s.getMaxR() > s.getY()) {

                potentialCollisions.add(s);

            }
        }
        return potentialCollisions;

    }

    /**
	 * test if two objects b (moving object) and a are colliding using the
	 * Separating Axis Theorem (SAT)
	 *
	 * resource: Separating Axis Theorem for Oriented Bounding Boxes by Johnny Huynh www.jkh.me
	 *
	 * @return true if colliding, false if not
	 */
    public Manifold testSAT(DynamicGameObject dObj, Wall wall) {
        double[] penetrationArray;

        Vector pointA = new Vector(dObj.getX(), dObj.getY());
        Vector d = dObj.getDirection();
        Vector dOrth = d.rotateOrthogonalCW();

        Vector wallStart = new Vector(wall.getStartX(), wall.getStartY());
        Vector wallEnd = new Vector(wall.getEndX(), wall.getEndY());
        Vector wallLength = wallEnd.add(wallStart.negate());
        Vector pointB = wallStart.add(wallLength.multiply(0.5));
        Vector dWall = wallLength.normalize();
        Vector dWallOrth = dWall.rotateOrthogonalCW();

        Vector t = pointB.add(pointA.negate());
        Vector objHalfW = dOrth.multiply(dObj.getHalfW());
        Vector objHalfH = d.multiply(dObj.getHalfH());
        Vector wallH = wallEnd.add(pointB.negate());

        penetrationArray = new double[]{
            // Case 1: axis to test is direction orthogonal to wall
            Math.abs(objHalfW.dot(dWallOrth)) + Math.abs(objHalfH.dot(dWallOrth)) - Math.abs(t.dot(dWallOrth)),
            // Case 2: axis to test is direction of dynamic object
            dObj.getHalfH() + Math.abs(wallH.dot(d)) - Math.abs(t.dot(d)),
            // Case 3: axis to test is direction orthogonal of dynamic object
            dObj.getHalfW() + Math.abs(wallH.dot(dOrth)) - Math.abs(t.dot(dOrth))
        };

        int min = 0;

        for (int i = 0; i < 3; i++) {
            if (penetrationArray[i] <= 0) {
                // a separating axis is found, meaning there is no collision and thus no manifold
                return null;

            }else if (penetrationArray[i] < penetrationArray[min]) {
                min = i;

            }
        }

        Vector normal, pointP, pointC;

        if (min == 0) {
            // wall is being penetrated along its orthogonal direction
            normal = dWallOrth.multiply(-Math.signum(dWallOrth.dot(t)));
            pointP = objHalfW.multiply(Math.signum(objHalfW.dot(normal.negate()))).add(
                objHalfH.multiply(Math.signum(objHalfH.dot(normal.negate())))).add(pointA);

        }else if (min == 1) {
            // dObj is being penetrated along its direction
            normal = d.multiply(Math.signum(d.dot(t)));
            pointP = wallH.multiply(Math.signum(wallH.dot(normal.negate()))).add(pointB);

        }else {
            // dObj is being penetrated along its orthogonal direction
            normal = dOrth.multiply(Math.signum(dOrth.dot(t)));
            pointP = wallH.multiply(Math.signum(wallH.dot(normal.negate()))).add(pointB);
        }
        // Manifold(pointC, pointP, depth, normal, vel)
        return new Manifold(pointP.add(normal.multiply(penetrationArray[min])), 
            pointP, penetrationArray[min], normal, dObj.getVelocity());

    }

    public Manifold testSAT(DynamicGameObject dObj, BasicGameObject bObj) {
        double[] penetrationArray = new double[4];

        Vector pointA = new Vector(dObj.getX(), dObj.getY());
        Vector dA = dObj.getDirection();
        Vector dAOrth = dA.rotateOrthogonalCW();

        Vector pointB = new Vector(bObj.getX(), bObj.getY());
        Vector dB = bObj.getDirection();
        Vector dBOrth = dB.rotateOrthogonalCW();

        Vector halfWA = dAOrth.multiply(dObj.getHalfW());
        Vector halfHA = dA.multiply(dObj.getHalfH());
        Vector halfWB = dBOrth.multiply(bObj.getHalfW());
        Vector halfHB = dB.multiply(bObj.getHalfH());

        Vector t;
        // Case 1: axis to test is direction of dynamic object
        t = pointB.add(pointA.negate());
        penetrationArray[0] =  dObj.getHalfH() + Math.abs(halfWB.dot(dA)) + Math.abs(halfHB.dot(dA)) - Math.abs(t.dot(dA));
        
        // Case 2: axis to test is direction orthogonal of dynamic object
        penetrationArray[1] = dObj.getHalfW() + Math.abs(halfWB.dot(dAOrth)) + Math.abs(halfHB.dot(dAOrth)) - Math.abs(t.dot(dAOrth));

        // Case 3: axis to test is direction of basic object
        t = pointA.add(pointB.negate());
        penetrationArray[2] = bObj.getHalfH() + Math.abs(halfWA.dot(dB)) + Math.abs(halfHA.dot(dB)) - Math.abs(t.dot(dB));

        // Case 4: axis to test is direction orthogonal of basic object
        penetrationArray[3] = bObj.getHalfW() + Math.abs(halfWA.dot(dBOrth)) + Math.abs(halfHB.dot(dBOrth)) - Math.abs(t.dot(dBOrth));

        int min = 0;

        for (int i = 0; i < 3; i++) {
            if (penetrationArray[i] <= 0) {
                // a separating axis is found, meaning there is no collision and thus no manifold
                return null;

            }else if (penetrationArray[i] < penetrationArray[min]) {
                min = i;

            }
        }

        Vector normal, pointP, pointC;

        if (min == 0) {
            // dObj is being penetrated along its direction
            t = pointA.add(pointB.negate());
            normal = dA.multiply(Math.signum(dA.dot(t)));
            pointP = halfWB.multiply(Math.signum(dBOrth.dot(normal))).add(
                halfHB.multiply(Math.signum(dB.dot(normal)))).add(pointB);
            // Manifold(pointC, pointP, depth, normal, vel)
            return new Manifold(pointP.add(normal.multiply(-penetrationArray[0])), 
                pointP, penetrationArray[0], normal, dObj.getVelocity());

        }else if (min == 1) {
            // dObj is being penetrated along its orthogonal direction
            t = pointA.add(pointB.negate());
            normal = dAOrth.multiply(Math.signum(dAOrth.dot(t)));
            pointP = halfWB.multiply(Math.signum(dBOrth.dot(normal))).add(
                halfHB.multiply(Math.signum(dB.dot(normal)))).add(pointB);
            // Manifold(pointC, pointP, depth, normal, vel)
            return new Manifold(pointP.add(normal.multiply(-penetrationArray[1])), 
                pointP, penetrationArray[1], normal, dObj.getVelocity());

        }else if (min == 2){
            // bObj is being penetrated along its direction
            t = pointB.add(pointA.negate());
            normal = dB.multiply(-Math.signum(dB.dot(t)));
            pointP = halfWA.multiply(Math.signum(dAOrth.dot(normal.negate()))).add(
                halfHA.multiply(Math.signum(dA.dot(normal.negate())))).add(pointA);
            // Manifold(pointC, pointP, depth, normal, vel)
            return new Manifold(pointP.add(normal.multiply(penetrationArray[2])), 
                pointP, penetrationArray[2], normal, dObj.getVelocity());

        }else {
            // bObj is being penetrated along its orthogonal direction
            t = pointB.add(pointA.negate());
            normal = dBOrth.multiply(-Math.signum(dBOrth.dot(t)));
            pointP = halfWA.multiply(Math.signum(dAOrth.dot(normal.negate()))).add(
                halfHA.multiply(Math.signum(dA.dot(normal.negate())))).add(pointA);
            // Manifold(pointC, pointP, depth, normal, vel)
            return new Manifold(pointP.add(normal.multiply(penetrationArray[3])), 
                pointP, penetrationArray[3], normal, dObj.getVelocity());

        }
        

    }

    /**
	 * performs SAT tests between given DynamicGameObject and every object in list
	 *
	 * @return list of objects that are colliding with dObj
	 */
    /*
    public ArrayList<BasicGameObject> detectSATCollisions(DynamicGameObject dObj,
        ArrayList<? extends BasicGameObject> sObjs) {

        ArrayList<BasicGameObject> colliding = new ArrayList<BasicGameObject>();

        for (BasicGameObject o: sObjs) {
            if (dObj != o && testSAT(dObj, o)) {
                colliding.add(o);

            }
        }
        return colliding;

    }*/

    /**
     * performs collision testing between every DynamicGameObject and all other objects
     * resolves all detected collisions
     */
    public void collisionDetectResolveAll() {
    	for (DynamicGameObject o: dynamicObjList) {
    		for (BasicGameObject b: basicObjList) {
                Manifold m = null;
                if (b instanceof Wall) {
                    m = testSAT(o, (Wall) b);

                }else {
                    m = testSAT(o, b);

                }

                if (m != null) {
                    b.resolveCollision(o, m);

                }

                if (!(o instanceof Car)) {
                    o.resolveCollision(o, m);

                }
    		}
    		for (BasicGameObject d: dynamicObjList) {
                Manifold m = testSAT(o, d);

    			if (m != null) {
                    d.resolveCollision(o, m);

                }

                if (!(o instanceof Car)) {
                    o.resolveCollision(o, m);

                }
    		}
    	}
    }

    /**
     * apply flag action to basic object o
     */
    public ArrayList<BasicGameObject> handleFlag(BasicGameObject o, Flag f, double time){
        ArrayList<BasicGameObject> toUpdate = new ArrayList<BasicGameObject>();
        toUpdate.add(o);
        switch (f.toString()) {
            case ("DESTROY"):
                basicObjList.remove(o);
                break;

        }

        if (o instanceof Pickup) {
            Pickup o_ = (Pickup) o;

            switch (f.toString()) {
                case ("TIMED_DISABLE"):
                    double before = f.valueAt(0);
                    if (before > 0) {
                        o_.setActive(false);
                        o_.addFlag(new Flag(Flag.HandlingMethod.TIMED_DISABLE, before - time));

                    }else {
                        o_.setActive(true);

                    }
                    break;

            }
        }

        return toUpdate;

    }

    /**
     * apply flag action to dynamic object o
     */
    public ArrayList<BasicGameObject> handleFlag(DynamicGameObject o, Flag f, double time) {
        ArrayList<BasicGameObject> toUpdate = new ArrayList<BasicGameObject>();
        toUpdate.add(o);

    	switch (f.toString()) {
            case ("DESTROY"):
                dynamicObjList.remove(o);
                break;

            case ("ADD_SPEED"):
                o.setSpeed(o.getSpeed() + f.valueAt(0));
                break;

            case ("SET_SPEED"):
                o.setSpeed(f.valueAt(0));
                break;

            case ("ADD_DIRECTION"):
                o.setDirection(o.getDirection().rotate(f.valueAt(0)));
                break;

            case ("SET_DIRECTION"):
                o.setDirection(f.valueAt(0));
                break;

            case ("EXPOLSION"):
                for(BasicGameObject x: getProximityObjects(o, f.valueAt(0))) {
                    if (x instanceof Car){
                        x.addFlag(new Flag(Flag.HandlingMethod.RESPAWN));
                        toUpdate.add(x);

                    }
                    else if (x instanceof StaticObstacle && x.getMass() < f.valueAt(1)) {
                        x.addFlag(new Flag(Flag.HandlingMethod.DESTROY));
                        toUpdate.add(x);

                    }

                }
                break;
        }

        if (o instanceof Car) {
        	Driver i = null;

        	for (Driver i_: driverList) {
        		if (i_.getAttachedCar() == o) {
        			i = i_;

        		}
        	}

        	if (i != null) {
        		switch (f.toString()) {
            		case ("NEXT_SECTION"):
            			if (i.getSection() == f.valueAt(0) - 1) {
            				i.setSection((int) f.valueAt(0));

            			}
            			break;

    				case ("NEXT_LAP"):
    					if (i.getSection() == f.valueAt(0)) {
    						i.setSection(0);
    						i.setLap(i.getLap() + 1);

    					}
    					break;

                    case ("PICKUP_MISSLE"):
                        i.setItem(new MissilePickup());
                        break;

                    case ("PICKUP_SPEEDBOOST"):
                        i.setItem(new SpeedboostPickup());
                        break;

                    case ("RESPAWN"):
                        Checkpoint cp = null;
                        for (BasicGameObject x: basicObjList) {
                            if (x instanceof Checkpoint) {
                                Checkpoint x_ = (Checkpoint) x;
                                if (x_.getNumber() == i.getSection()) {
                                    cp = x_;

                                }
                            }
                        }

                        if (cp != null) {
                            Vector vec = new Vector(cp.getEndX() - cp.getStartX(), cp.getEndY() - cp.getStartY()).multiply(0.5);
                            Car c = new Car((Car) o);
                            c.setX(cp.getStartX() + vec.getI());
                            c.setY(cp.getStartY() + vec.getJ());
                            i.setAttachedCar(c);
                            toUpdate.add(c);

                        }
                        break;

                    case ("SPAWN_MISSLE"):
                        double vParallel = o.getDirection().dot(o.getVelocity());
                        MissileProjectile mp = new MissileProjectile(f.valueAt(0), f.valueAt(1), vParallel + 30, f.valueAt(2));
                        addDynamicGameObject(mp);
                        toUpdate.add(mp);
                        break;

            	}
        	}
        }
        return toUpdate;

    }

    /**
	 * handle all flags of each object and tick all DynamicGameObjects to change positions
	 */
    public ArrayList<BasicGameObject> tickAll(double time){
    	ArrayList<BasicGameObject> toUpdate = new ArrayList<BasicGameObject>();

        for (BasicGameObject o: new ArrayList<BasicGameObject>(basicObjList)) {
        	if (!o.getFlags().isEmpty()) {
				for (Flag f: o.getFlags()) {
		        	o.removeFlag(f);
                    for (BasicGameObject b: handleFlag(o, f, time)) {
                        if (!toUpdate.contains(b)) {
                            toUpdate.add(b);

                        }
                    }
		        }
        	}
        }

        for (DynamicGameObject o: new ArrayList<DynamicGameObject>(dynamicObjList)) {
        	if (!o.getFlags().isEmpty()) {
				for (Flag f: o.getFlags()) {
		        	o.removeFlag(f);
                    for (BasicGameObject b: handleFlag(o, f, time)) {
                        if (!toUpdate.contains(b)) {
                            toUpdate.add(b);

                        }
                    }

		        }
        	}else {
        		toUpdate.add(o);
        	}
        	o.tick(time);

        }

        return toUpdate;

    }
}
