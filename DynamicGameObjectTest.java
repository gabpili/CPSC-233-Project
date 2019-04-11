import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import gameobj.DynamicGameObject;
import base.Vector;

import org.junit.Test;

public class DynamicGameObjectTest{

    public static final String CLASSNAME = "DynamicGameObject";
	public static final String FILENAME = CLASSNAME + ".java";
    class MockDynamicGameObject extends DynamicGameObject{

        public MockDynamicGameObject(double x, double y, String name, double halfW, double halfH, double mass) {
            super(x, y, name, halfW, halfH, mass);
        }

        public MockDynamicGameObject(double x, double y, String name, double halfW, double halfH, double mass,
        	double speed, double direction) {
            super(x, y, name, halfW, halfH, mass, speed, direction);
        }


        public MockDynamicGameObject(double x, double y, String name, double halfW, double halfH, double mass,
        	Vector velocity, Vector direction) {
        	super(x, y, name, halfW, halfH, mass, velocity, direction);
        }


        public MockDynamicGameObject(MockDynamicGameObject toCopy) {
            super(toCopy);
        }

        @Override
        public void resolveCollision(DynamicGameObject dObj) throws IllegalArgumentException{

        }

    }

    @Test
	public void test_constructorWithFiveArguments()
	{
		MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8);

		assertEquals("Unexpected X",5.0, d.getX(), 0.00001);
        assertEquals("Unexpected Y", 6.0, d.getY(), 0.00001);
        assertEquals("Unexpected Name", "bub", d.getName());
        assertEquals("Unexpected Half Height", 0.5, d.getHalfH(), 0.000001);
		assertEquals("Unexpected Half Width",0.5, d.getHalfW(), 0.00001);
		assertEquals("Unexpected Mass", 0.8, d.getMass(), 0.00001);
	}

    @Test
	public void test_constructorWithSevenArguments()
	{
		MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, 10.0, 5.0);

		assertEquals("Unexpected X",5.0, d.getX(), 0.00001);
        assertEquals("Unexpected Y", 6.0, d.getY(), 0.00001);
        assertEquals("Unexpected Name", "bub", d.getName());
        assertEquals("Unexpected Half Height", 0.5, d.getHalfH(), 0.000001);
		assertEquals("Unexpected Half Width",0.5, d.getHalfW(), 0.00001);
		assertEquals("Unexpected Mass", 0.8, d.getMass(), 0.00001);
        assertEquals("Unexpected Speed", 10.0, d.getSpeed(), 0.00001);
        assertEquals("Unexpected Direction", 5 - 2 * Math.PI, d.getDirection().theta(), 0.000001);
	}

    @Test
	public void test_fullConstructor()
	{
        Vector vel = new Vector(3.0, 6.0);
        Vector dir = new Vector(1.2);
		MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, vel, dir);

		assertEquals("Unexpected X",5.0, d.getX(), 0.00001);
        assertEquals("Unexpected Y", 6.0, d.getY(), 0.00001);
        assertEquals("Unexpected Name", "bub", d.getName());
        assertEquals("Unexpected Half Height", 0.5, d.getHalfH(), 0.000001);
		assertEquals("Unexpected Half Width",0.5, d.getHalfW(), 0.00001);
		assertEquals("Unexpected Mass", 0.8, d.getMass(), 0.00001);
        assertEquals("Unexpected Velocity", vel, d.getVelocity());
        assertEquals("Unexpected Direction", dir, d.getDirection());

	}

    @Test
	public void test_copyConstructor()
	{
        Vector vel = new Vector(3.0, 6.0);
        Vector dir = new Vector(1.2);
		MockDynamicGameObject d = new MockDynamicGameObject(new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, vel, dir));

        assertEquals("Unexpected X",5.0, d.getX(), 0.00001);
        assertEquals("Unexpected Y", 6.0, d.getY(), 0.00001);
        assertEquals("Unexpected Name", "bub", d.getName());
        assertEquals("Unexpected Half Height", 0.5, d.getHalfH(), 0.000001);
		assertEquals("Unexpected Half Width",0.5, d.getHalfW(), 0.00001);
		assertEquals("Unexpected Mass", 0.8, d.getMass(), 0.00001);
        assertEquals("Unexpected Velocity", vel, d.getVelocity());
        assertEquals("Unexpected Direction", dir, d.getDirection());
	}

    @Test
	public void test_getter_and_setter_Speed_nonZeroInitial_nonZeroFinal()
	{
        Vector vel = new Vector(3.0, 6.0);
        Vector dir = new Vector(1.2);
		MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, vel, dir);
		d.setSpeed(3);
		assertEquals("Created MockDynamicGameObject then changed the speed", 3, d.getSpeed(), 0.00001);
	}

    @Test
    public void test_getter_and_setter_Speed_zeroInitial_nonZeroFinal()
    {
        MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, 0, 6.0);
        d.setSpeed(3);
        assertEquals("Created MockDynamicGameObject then changed the speed", 3, d.getSpeed(), 0.00001);
    }

    @Test
    public void test_getter_and_setter_Speed_zeroInitial_zeroFinal()
    {
        MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, 0, 6.0);
        d.setSpeed(0);
        assertEquals("Created MockDynamicGameObject then changed the speed", 0, d.getSpeed(), 0.00001);
    }

    @Test
    public void test_getter_and_setter_Speed_nonZeroInitial_zeroFinal()
    {
        MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, 7.0, 6.0);
        d.setSpeed(0);
        assertEquals("Created MockDynamicGameObject then changed the speed", 0, d.getSpeed(), 0.00001);
    }

    @Test
	public void test_getter_and_setter_Velocity()
	{
        Vector velI = new Vector(3.0, 6.0);
        Vector velF = new Vector(5.0, 8.0);
        Vector dir = new Vector(1.2);
		MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, velI, dir);
		d.setVelocity(velF);
		assertEquals("Created MockDynamicGameObject then changed the velocity", velF, d.getVelocity());
	}

    @Test
	public void test_getter_and_setter_Direction()
	{
        Vector vel = new Vector(3.0, 6.0);
        Vector dirI = new Vector(1.2);
        Vector dirF = new Vector(3.4);
		MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, vel, dirI);
		d.setDirection(dirF);
		assertEquals("Created MockDynamicGameObject then changed the direction", dirF, d.getDirection());
	}

    @Test
	public void test_tickMethod()
	{
        Vector vel = new Vector(0.5, 1);
        Vector dir = new Vector(3.4);
		MockDynamicGameObject d = new MockDynamicGameObject(0, 0, "bub", 0.5, 0.5, 0.8, vel, dir);
		d.tick(1.0);
		assertEquals("Created MockDynamicGameObject then ticked the volocity", 0.5, d.getX(), 0.00001);
        assertEquals("Created MockDynamicGameObject then ticked the volocity", 1, d.getY(), 0.00001);

	}

    @Test
	public void test_toString() {
		MockDynamicGameObject d = new MockDynamicGameObject(5.0, 6.0, "bub", 0.5, 0.5, 0.8, 7.0, 6.0);
		assertEquals("Expected to string to return <name> x:<x>m y:<y>m <speed>m/s <direction>deg", "bub x:5m y:6m 7m/s -16deg", d.toString());
	}
}
