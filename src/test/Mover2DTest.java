package test;

import graphing.Point;
import graphing.Vector;
import org.junit.Test;
import simulation.Mover2D;

import static org.junit.Assert.*;


public class Mover2DTest {

    @Test
    public void testGetOrientation() throws Exception {


        Mover2D mover = new Mover2D();
        mover.setMaxSpeed(5.0);
        mover.setVelocity(0, -1);

        assertTrue(-90.0 == Math.toDegrees(mover.getOrientation()));

    }

    @Test
    public void testTick() throws Exception {

        Mover2D mover = new Mover2D(new Point(0,0), new Vector(-0.65, 0), new Vector(-0.13, 0), 1);

        int ticksPerSecond = (int) (1.0 / Mover2D.TICK_PERIOD);
        for(int i = 0; i < 25*ticksPerSecond; i++, mover.tick());

        System.out.println(mover.getPosition());

    }


}