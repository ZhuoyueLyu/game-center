package group_0617.csc207.gamecentre;

import org.junit.Test;

import group_0617.csc207.gamecentre.dataBase.Tuple;

import static org.junit.Assert.*;

/**
 * The unit test for Tuple
 */
public class TupleTest {

    /**
     * The tuple used to test
     */
    private Tuple tuple;

    /**
     * Setting up the tuple with string
     */
    private void setUpStringTuple() {
        this.tuple = new Tuple("First", "Second");
    }

    /**
     * Setting up the tuple with Integer
     */
    private void setUpIntegerTuple() {
        this.tuple = new Tuple(1, 2);
    }

    /**
     * Setting up the tuple with elements of different class
     */
    private void setUpDifferentClassTuple() {
        this.tuple = new Tuple(1, "Second");
    }

    /**
     * Setting up the tuple with null
     */
    private void setUpTupleWithNull() {
        this.tuple = new Tuple(null, null);
    }

    /**
     * Test whether getX works
     */
    @Test
    public void testGetX() {
        setUpStringTuple();
        assertEquals("First", tuple.getX());
        setUpIntegerTuple();
        assertEquals(1, tuple.getX());
        setUpDifferentClassTuple();
        assertEquals(1, tuple.getX());
        setUpTupleWithNull();
        assertNull(tuple.getX());
    }

    /**
     * Test whether setX works
     */
    @Test
    public void testSetX() {
        setUpStringTuple();
        String newX = "newFirst";
        tuple.setX(newX);
        assertEquals(newX, tuple.getX());
    }

    /**
     * Test whether getY works
     */
    @Test
    public void testGetY() {
        setUpStringTuple();
        assertEquals("Second", tuple.getY());
        setUpIntegerTuple();
        assertEquals(2, tuple.getY());
        setUpDifferentClassTuple();
        assertEquals("Second", tuple.getY());
        setUpTupleWithNull();
        assertNull(tuple.getY());
    }

    /**
     * Test whether set X works
     */
    @Test
    public void testSetY() {
        setUpStringTuple();
        String newY = "newSecond";
        tuple.setY(newY);
        assertEquals(newY, tuple.getY());
    }
}