import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.util.*;
import java.util.List;



/**
 * Tests MotionCommand implementations (Motion enum).
 *
 * <p>
 * Validates that:
 * </p>
 *
 * <ul>
 *     <li>movement respects panel bounds</li>
 *     <li>random linear mode initializes correct internal state</li>
 *     <li>obstacles block movement attempts</li>
 *     <li>direction constants are correct (no diagonal movement)</li>
 * </ul>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

// Minimal stub classes for isolated testing
class MockGamePanel implements MotionEnvironment {
    private final int xBound, yBound;
    private final List<Obstacle> obstacles;
    private final List<Avatar> avatars;

    MockGamePanel(int xBound, int yBound, List<Obstacle> obstacles, List<Avatar> avatars) {
        this.xBound = xBound;
        this.yBound = yBound;
        this.obstacles = obstacles;
        this.avatars = avatars;
    }

    @Override
    public int getXBOUND() { return xBound; }
    @Override
    public int getYBOUND() { return yBound; }
    @Override
    public List<Obstacle> getOBSTACLES() { return obstacles; }
    @Override
    public List<Avatar> getAVATARS() { return avatars; }
}

public class MotionCommandTest {
    @Test
    public void testDirectionsFieldContract() {
        int[][] directions = MotionCommand.DIRECTIONS;
        assertEquals(4, directions.length);
        assertTrue(Arrays.stream(directions).anyMatch(dir -> Arrays.equals(dir, new int[]{1, 0})));
        assertTrue(Arrays.stream(directions).anyMatch(dir -> Arrays.equals(dir, new int[]{-1, 0})));
        assertTrue(Arrays.stream(directions).anyMatch(dir -> Arrays.equals(dir, new int[]{0, 1})));
        assertTrue(Arrays.stream(directions).anyMatch(dir -> Arrays.equals(dir, new int[]{0, -1})));
    }

    //Check if avatar moved and stayed within bounds
    @Test
    public void testRandomMotionMovement() {
        Avatar avatar = new Avatar(50, 50, 10, Motion.RANDOMMOTION);
        MockGamePanel panel = new MockGamePanel(100, 100, Collections.emptyList(), List.of(avatar));

        MotionCommand motion = Motion.RANDOMMOTION;
        motion.motion(avatar, panel);

        assertTrue(avatar.getX() >= 0 && avatar.getX() < panel.getXBOUND());
        assertTrue(avatar.getY() >= 0 && avatar.getY() < panel.getYBOUND());
    }

    //Checks avatar status under RandomLinearMotion
    @Test
    public void testRandomLinearMotionState() {
        Avatar avatar2 = new Avatar(10, 10, 10, Motion.RANDOMLINEARMOTION);
        MockGamePanel panel2 = new MockGamePanel(100, 100, Collections.emptyList(), List.of(avatar2));

        MotionCommand linearMotion = Motion.RANDOMLINEARMOTION;
        linearMotion.motion(avatar2, panel2);

        assertTrue(avatar2.isRandomLinearActive());
        assertEquals(4, avatar2.getLinearMovesRemaining());
    }

    @Test
    public void testCollisionBlocksMovementViaInterface() {
        Avatar avatar = new Avatar(20, 20, 10, Motion.RANDOMMOTION);
        List<Obstacle> obstacles = List.of(new Obstacle(21, 20, 10, 10)); //directly blocking next step
        MockGamePanel panel = new MockGamePanel(30, 30, obstacles, List.of(avatar));

        int originalX = avatar.getX(), originalY = avatar.getY();
        MotionCommand motion = Motion.RANDOMMOTION;

        // Try multiple times to ensure obstacle blocking is encountered
        for (int i = 0; i < 5; i++) {
            motion.motion(avatar, panel);
        }

        // Stays within bounds and obstacle area
        assertTrue(avatar.getX() >= 0 && avatar.getX() + avatar.getSize() <= panel.getXBOUND());
        assertTrue(avatar.getY() >= 0 && avatar.getY() + avatar.getSize() <= panel.getYBOUND());

        assertEquals(originalX, avatar.getX());
        assertEquals(originalY, avatar.getY());
    }
}