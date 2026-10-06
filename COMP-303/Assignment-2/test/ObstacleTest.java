import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;



/**
 * Unit tests for the Obstacle class. Assertions are tested only when assertions
 * are expected while setters validate using exceptions.
 *
 * <p>These tests verify:</p>
 *
 * <ul>
 *     <li>Constructor preconditions and object initialization</li>
 *     <li>Getter and setter behavior, including validation</li>
 *     <li>Bounds calculation and drawing safety checks</li>
 * </ul>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

public class ObstacleTest {

    @Test
    public void testConstructorValidParameters() {
        Obstacle obs = new Obstacle(0, 0, 1, 1);
        assertEquals(0, obs.getX());
        assertEquals(0, obs.getY());
        assertEquals(1, obs.getWidth());
        assertEquals(1, obs.getHeight());
        assertEquals(Color.GRAY, obs.getColor());
    }

    @Test
    public void testConstructorBoundaryValues() {
        Obstacle obs = new Obstacle(1, 1, Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(1, obs.getX());
        assertEquals(1, obs.getY());
        assertEquals(Integer.MAX_VALUE, obs.getWidth());
        assertEquals(Integer.MAX_VALUE, obs.getHeight());
    }

    @Test
    public void testConstructorInvalidParameters() {
        assertThrows(AssertionError.class, () -> new Obstacle(-1, 0, 10, 10));
        assertThrows(AssertionError.class, () -> new Obstacle(0, -1, 10, 10));
        assertThrows(AssertionError.class, () -> new Obstacle(0, 0, 0, 10));
        assertThrows(AssertionError.class, () -> new Obstacle(0, 0, 10, 0));
        assertThrows(AssertionError.class, () -> new Obstacle(0, 0, -10, 10));
        assertThrows(AssertionError.class, () -> new Obstacle(0, 0, 10, -10));
    }

    @Test
    public void testGettersAndSettersFunctionality() {
        Obstacle obs = new Obstacle(5, 7, 8, 12);

        obs.setX(10);
        obs.setY(20);
        obs.setWidth(30);
        obs.setHeight(40);
        obs.setColor(Color.BLUE);

        assertEquals(10, obs.getX());
        assertEquals(20, obs.getY());
        assertEquals(30, obs.getWidth());
        assertEquals(40, obs.getHeight());
        assertEquals(Color.BLUE, obs.getColor());
    }

    @Test
    public void testSettersInvalidParameters() {
        // The setters do not have asserts
        Obstacle obs = new Obstacle(1, 1, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> obs.setX(-5));
        assertThrows(IllegalArgumentException.class, () -> obs.setY(-10));
        assertThrows(IllegalArgumentException.class, () -> obs.setWidth(0));
        assertThrows(IllegalArgumentException.class, () -> obs.setHeight(-20));

        assertThrows(AssertionError.class, () -> obs.setColor(null));
    }

    @Test
    public void testGetBoundsCorrectness() {
        Obstacle obs = new Obstacle(10, 20, 30, 40);
        Rectangle r = obs.getBounds();
        assertEquals(10, r.x);
        assertEquals(20, r.y);
        assertEquals(30, r.width);
        assertEquals(40, r.height);
    }

    @Test
    public void testDrawThrowsOnNullGraphics() {
        Obstacle obs = new Obstacle(0, 0, 10, 10);
        assertThrows(AssertionError.class, () -> obs.draw(null));
    }

    @Test
    public void testSetColorDoesNotChangeBounds() {
        Obstacle obs = new Obstacle(5, 5, 10, 20);
        obs.setColor(Color.RED);
        assertEquals(5, obs.getX());
        assertEquals(5, obs.getY());
        assertEquals(10, obs.getWidth());
        assertEquals(20, obs.getHeight());
    }

    @Test
    public void testGetBoundsReturnsNewObject() {
        Obstacle obs = new Obstacle(10, 20, 30, 40);
        Rectangle first = obs.getBounds();
        Rectangle second = obs.getBounds();
        assertNotSame(first, second);
    }
}