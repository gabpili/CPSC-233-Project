import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import gameobj.BasicGameObject;
import gameobj.DynamicGameObject;
import base.Flag;

import java.lang.Math;
import java.util.ArrayList;
import org.junit.Test;


public class BasicGameObjectTest{

	public static final String CLASSNAME = "BasicGameObject";
	public static final String FILENAME = CLASSNAME + ".java";
	class MockBasicGameObject extends BasicGameObject{

		public MockBasicGameObject(double x, double y, String name, double halfW, double halfH, double mass){
			super(x, y, name, halfW, halfH, mass);

		}

		public MockBasicGameObject(BasicGameObject toCopy){
			super(toCopy);

		}

		@Override
        public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException{

        }

	}

	@Test
	public void test_Constructor_Full(){
		BasicGameObject b = new MockBasicGameObject(1.0, 2.0, "Testing BasicGameObject", 0.5, 0.5, 5.0);

		assertEquals("Created BasicGameObject - testing x", 1.0, b.getX(), 0.00001);
		assertEquals("Created BasicGameObject - testing y", 2.0, b.getY(), 0.00001);
		assertEquals("Created BasicGameObject - testing name", "Testing BasicGameObject", b.getName());
		assertEquals("Created BasicGameObject - testing half width", 0.5, b.getHalfW(), 0.00001);
		assertEquals("Created BasicGameObject - testing half height", 0.5, b.getHalfH(), 0.00001);
		assertEquals("Created BasicGameObject - testing mass", 5.0, b.getMass(), 0.00001);

	}

	@Test
	public void test_CopyConstructor(){
		BasicGameObject b = new MockBasicGameObject(new MockBasicGameObject(1.0, 2.0, "Testing BasicGameObject", 0.5, 0.5, 5.0));

		assertEquals("Created BasicGameObject - testing x", 1.0, b.getX(), 0.00001);
		assertEquals("Created BasicGameObject - testing y", 2.0, b.getY(), 0.00001);
		assertEquals("Created BasicGameObject - testing name", "Testing BasicGameObject", b.getName());
		assertEquals("Created BasicGameObject - testing half width", 0.5, b.getHalfW(), 0.00001);
		assertEquals("Created BasicGameObject - testing half height", 0.5, b.getHalfH(), 0.00001);
		assertEquals("Created BasicGameObject - testing mass", 5.0, b.getMass(), 0.00001);

	}

	@Test
	public void test_getter_and_setter_X(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", 0, 0, 0);
		b.setX(1.0);
		assertEquals("Created BasicGameObject then changed X.", 1.0, b.getX(), 0.00001);

	}

	@Test
	public void test_getter_and_setter_Y(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", 0, 0, 0);
		b.setY(1.0);
		assertEquals("Created BasicGameObject then changed X.", 1.0, b.getY(), 0.00001);

	}

	@Test
	public void test_getHalfW_10(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", 10, 5, 2);
		assertEquals("Created BasicGameObject", 10.0, b.getHalfW(), 0.00001);

	}

	@Test
	public void test_getHalfH_5(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", 10, 5, 2);
		assertEquals("Created BasicGameObject", 5.0, b.getHalfH(), 0.00001);

	}

	@Test
	public void test_getHalfW_Negative_10(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", -10, -5, -2);
		assertEquals("Created BasicGameObject", 10.0, b.getHalfW(), 0.00001);

	}

	@Test
	public void test_getHalfH_Negative_5(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", -10, -5, -2);
		assertEquals("Created BasicGameObject", 5.0, b.getHalfH(), 0.000001);

	}

	@Test
	public void test_getMass_2(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", 10, 5, 2);
		assertEquals("Created BasicGameObject", 2.0, b.getMass(), 0.000001);

	}
	@Test
	public void test_getMass_Negative_2(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", -10, -5, -2);
		assertEquals("Created BasicGameObject", 2.0, b.getMass(), 0.00001);

	}

	@Test
	public void test_addFlag(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", 10, 5, 2);
		Flag f = new Flag(Flag.HandlingMethod.DESTROY);
		b.addFlag(f);
		assertEquals("Created BasicGameObject and added a flag", new Flag(Flag.HandlingMethod.DESTROY), b.getFlags().get(0));
	}

	@Test
	public void test_removeFlag(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", 10, 5, 2);
		Flag f = new Flag(Flag.HandlingMethod.DESTROY);
		b.addFlag(f);
		b.removeFlag(new Flag(Flag.HandlingMethod.DESTROY));
		assertEquals("Created Basic BasicGameObject and removing flag", new ArrayList<Flag>(), b.getFlags());
	}

	@Test
	public void test_getFlag(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "Testing BasicGameObject", 10, 5, 2);
		Flag f1 = new Flag(Flag.HandlingMethod.ADD_SPEED, 7);
		Flag f2 = new Flag(Flag.HandlingMethod.ADD_DIRECTION, 1);
		ArrayList<Flag> expected = new ArrayList<Flag>();
		b.addFlag(f1);
		b.addFlag(f2);
		expected.add(new Flag(f1));
		expected.add(new Flag(f2));
		assertEquals("Created BasicGameObject getting a flag", expected, b.getFlags());

	}

	@Test
	public void test_toString(){
		BasicGameObject b = new MockBasicGameObject(0, 0, "bub", 10, 5, 2);
		assertEquals("Expected to string to return <name> x:<x>m y:<y>m ", "bub x:0m y:0m ", b.toString());
	}

}
