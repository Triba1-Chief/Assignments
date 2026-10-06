import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.util.*;
import java.util.List;



/**
 * Unit tests for Motion enum implementations.
 *
 * <p>
 * These tests verify correct behavior for both motion algorithms:
 * </p>
 *
 * <ul>
 *     <li><b>RandomMotion</b> stays within bounds and avoids obstacles</li>
 *     <li><b>RandomLinearMotion</b> follows the specified algorithm:
 *         <ul>
 *             <li>Initializes internal motion state on first call</li>
 *             <li>Moves in a straight line until hitting a boundary, obstacle, or avatar</li>
 *             <li>Chooses a new direction after collision</li>
 *             <li>Stops moving after four straight segments</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

public class MotionTest {
    @Test
    public void testRandomMotionWithinBoundsAndNoObstacle() {
        Avatar avatar = new Avatar(50, 50, 10, Motion.RANDOMMOTION);
        MockGamePanel panel = new MockGamePanel(100, 100, Collections.emptyList(),
                Collections.singletonList(avatar));

        Motion.RANDOMMOTION.motion(avatar, panel);

        assertTrue(avatar.getX() >= 0 && avatar.getX() <= panel.getXBOUND() - avatar.getSize());
        assertTrue(avatar.getY() >= 0 && avatar.getY() <= panel.getYBOUND() - avatar.getSize());
    }

    @Test
    public void testRandomMotionHitsObstacleAndDoesNotMove() {
        Avatar avatar = new Avatar(20, 20, 10, Motion.RANDOMMOTION);
        // Place obstacle at every adjacent spot
        List<Obstacle> obstacles = Arrays.asList(
                new Obstacle(15, 20, 10, 10),
                new Obstacle(20, 15, 10, 10),
                new Obstacle(25, 20, 10, 10),
                new Obstacle(20, 25, 10, 10)
        );
        MockGamePanel panel = new MockGamePanel(50, 50, obstacles, List.of(avatar));

        // Repeat to increase chance of movement attempt blocked
        for (int i = 0; i < 10; i++) {
            Motion.RANDOMMOTION.motion(avatar, panel);
        }

        assertEquals(20, avatar.getX());
        assertEquals(20, avatar.getY());
    }

    @Test
    public void testRandomLinearMotionInitialStateAndMovement() {
        Avatar avatar = new Avatar(10, 10, 10, Motion.RANDOMLINEARMOTION);
        MockGamePanel panel = new MockGamePanel(30, 30, Collections.emptyList(), List.of(avatar));

        // On first call, should initialize state and move once in a cardinal direction
        assertFalse(avatar.isRandomLinearActive());
        Motion.RANDOMLINEARMOTION.motion(avatar, panel);
        assertTrue(avatar.isRandomLinearActive());
        assertEquals(4, avatar.getLinearMovesRemaining());

        // Should actually move, not remain static
        assertTrue(avatar.getX() != 10 || avatar.getY() != 10);
    }

    @Test
    public void testRandomLinearMotionStopsAfterFourSegments() {
        Avatar avatar = new Avatar(5, 5, 10, Motion.RANDOMLINEARMOTION);

        Obstacle left  = new Obstacle(4, 5, 10, 10);
        Obstacle right = new Obstacle(6, 5, 10, 10);
        Obstacle up    = new Obstacle(5, 4, 10, 10);
        Obstacle down  = new Obstacle(5, 6, 10, 10);

        MockGamePanel panel = new MockGamePanel(100, 100, List.of(left, right, up, down), List.of(avatar));

        Motion.RANDOMLINEARMOTION.motion(avatar, panel);
        Motion.RANDOMLINEARMOTION.motion(avatar, panel);
        Motion.RANDOMLINEARMOTION.motion(avatar, panel);
        Motion.RANDOMLINEARMOTION.motion(avatar, panel);

        assertFalse(avatar.isRandomLinearActive(), "Should be inactive after four straight segments");
        assertEquals(0, avatar.getLinearMovesRemaining(), "Segments remaining should be zero");

        // Position should never change because every attempt was blocked
        assertEquals(5, avatar.getX());
        assertEquals(5, avatar.getY());
    }

    @Test
    public void testRandomLinearMotionObstacleBlocksAndChangesDirection() {
        Avatar avatar = new Avatar(20, 20, 10, Motion.RANDOMLINEARMOTION);
        Obstacle obstacle = new Obstacle(21, 20, 10, 10);
        MockGamePanel panel = new MockGamePanel(40, 40, List.of(obstacle), List.of(avatar));

        avatar.setCurrentDirection(new int[]{1, 0});
        avatar.setLinearMovesRemaining(4);
        avatar.setRandomLinearActive(true);

        Motion.RANDOMLINEARMOTION.motion(avatar, panel);

        assertEquals(3, avatar.getLinearMovesRemaining());
    }

    @Test
    public void testRandomLinearMotionAvatarCollisionBlocksMovement() {
        Avatar avatar1 = new Avatar(10, 10, 10, Motion.RANDOMLINEARMOTION);
        Avatar avatar2 = new Avatar(11, 10, 10, Motion.RANDOMLINEARMOTION);
        List<Avatar> avatarList = Arrays.asList(avatar1, avatar2);
        MockGamePanel panel = new MockGamePanel(30, 30, Collections.emptyList(), avatarList);

        avatar1.setCurrentDirection(new int[]{1, 0}); // moving right towards avatar2
        avatar1.setLinearMovesRemaining(4);
        avatar1.setRandomLinearActive(true);

        Motion.RANDOMLINEARMOTION.motion(avatar1, panel);

        // Should detect collision and decrement moves without moving
        assertEquals(3, avatar1.getLinearMovesRemaining());
        assertEquals(10, avatar1.getX());
        assertEquals(10, avatar1.getY());
    }
}