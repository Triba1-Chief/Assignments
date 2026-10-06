import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;



/**
 * Unit tests for the Avatar class.
 * Assertions are tested only when assertions are expected while setters validate using exceptions.
 *
 * <p>
 * These tests verify:
 * </p>
 *
 * <ul>
 *     <li>constructor preconditions and correct state initialization</li>
 *     <li>getter and setter behavior, including validation rules</li>
 *     <li>movement and drawing safety checks related to the selected motion strategy</li>
 * </ul>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

public class AvatarTest {
    private Motion dummyMotion = Motion.RANDOMMOTION;

    @Test
    public void testConstructorValidParameters() {
        Avatar avatar = new Avatar(0, 0, 1, dummyMotion);
        assertEquals(0, avatar.getX());
        assertEquals(0, avatar.getY());
        assertEquals(1, avatar.getSize());
        assertNotNull(avatar.getColor());
        assertEquals(dummyMotion, avatar.getMotionType());
        assertNotNull(avatar.getRand());
        assertEquals(0, avatar.getLinearMovesRemaining());
        assertNull(avatar.getCurrentDirection());
        assertFalse(avatar.isRandomLinearActive());
    }

    @Test
    public void testConstructorInvalidParameters() {
        assertThrows(AssertionError.class, () -> new Avatar(-1, 0, 10, dummyMotion));
        assertThrows(AssertionError.class, () -> new Avatar(0, -1, 10, dummyMotion));
        assertThrows(AssertionError.class, () -> new Avatar(0, 0, -1, dummyMotion));
        assertThrows(AssertionError.class, () -> new Avatar(0, 0, 10, null));
    }

    @Test
    public void testGettersAndSettersFunctionality() {
        Avatar avatar = new Avatar(10, 20, 30, dummyMotion);
        Color color1 = avatar.getColor();
        avatar.setX(100);
        avatar.setY(200);
        avatar.setSize(40);
        avatar.setMotionType(Motion.RANDOMLINEARMOTION);
        avatar.setColor();


        assertEquals(100, avatar.getX());
        assertEquals(200, avatar.getY());
        assertEquals(40, avatar.getSize());
        assertEquals(Motion.RANDOMLINEARMOTION, avatar.getMotionType());
        assertThrows(IllegalArgumentException.class, () -> avatar.setSize(-1));
        assertNotEquals(color1, avatar.getColor(), "Colors should change after setColor()");
    }

    @Test
    public void testMotionRelatedState() {
        Avatar avatar = new Avatar(1, 1, 10, dummyMotion);

        int[] valid1 = {1, 0};
        int[] valid2 = {0, -1};
        int[] invalid = {-1, -1};

        avatar.setCurrentDirection(valid1);
        assertArrayEquals(valid1, avatar.getCurrentDirection());

        avatar.setCurrentDirection(valid2);
        assertArrayEquals(valid2, avatar.getCurrentDirection());

        assertThrows(IllegalArgumentException.class, () -> avatar.setCurrentDirection(invalid));
    }

    @Test
    public void testDrawThrowsOnNullGraphics() {
        Avatar avatar = new Avatar(0, 0, 10, dummyMotion);
        assertThrows(AssertionError.class, () -> avatar.draw(null));
    }
}