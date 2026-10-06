import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;



/**
 * Tests that any MotionEnvironment implementation can be used by a motion strategy.
 *
 * <p>
 * These tests verify correct usage of the interface by checking that:
 * </p>
 *
 * <ul>
 *     <li>The interface provides boundary and collision information to the motion algorithm</li>
 *     <li>A motion algorithm reads obstacle and avatar lists from the interface</li>
 *     <li>Movement performed through MotionCommand respects the world limits</li>
 * </ul>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

public class MotionEnvironmentTest {

    /**
     * Mock implementation of MotionEnvironment for isolated testing.
     */

    private static class MockEnvironment implements MotionEnvironment {
        private final int xBound;
        private final int yBound;
        private final List<Obstacle> obstacles;
        private final List<Avatar> avatars;

        MockEnvironment(int xBound, int yBound, List<Obstacle> obstacles, List<Avatar> avatars) {
            this.xBound = xBound;
            this.yBound = yBound;
            this.obstacles = obstacles;
            this.avatars = avatars;
        }

        @Override
        public int getXBOUND() {
            return xBound;
        }

        @Override
        public int getYBOUND() {
            return yBound;
        }

        @Override
        public List<Obstacle> getOBSTACLES() {
            return obstacles;
        }

        @Override
        public List<Avatar> getAVATARS() {
            return avatars;
        }
    }

    @Test
    public void testEnvironmentProvidesCorrectBounds() {
        MotionEnvironment env = new MockEnvironment(500, 300, Collections.emptyList(),
                Collections.emptyList());

        assertEquals(500, env.getXBOUND());
        assertEquals(300, env.getYBOUND());
    }

    @Test
    public void testEnvironmentProvidesObstaclesAndAvatars() {
        Avatar avatar = new Avatar(10, 10, 10, Motion.RANDOMMOTION);
        Obstacle obstacle = new Obstacle(100, 100, 50, 50);

        MotionEnvironment env = new MockEnvironment(500, 300, List.of(obstacle), List.of(avatar));

        assertEquals(1, env.getAVATARS().size());
        assertEquals(1, env.getOBSTACLES().size());
        assertTrue(env.getAVATARS().contains(avatar));
        assertTrue(env.getOBSTACLES().contains(obstacle));
    }

    @Test
    public void testMovementUsesEnvironmentBounds() {
        Avatar avatar = new Avatar(490, 290, 10, Motion.RANDOMMOTION);
        MotionEnvironment env = new MockEnvironment(500, 300, Collections.emptyList(), List.of(avatar));

        MotionCommand motion = Motion.RANDOMMOTION;
        motion.motion(avatar, env);

        assertTrue(avatar.getX() >= 0 && avatar.getX() <= env.getXBOUND());
        assertTrue(avatar.getY() >= 0 && avatar.getY() <= env.getYBOUND());
    }
}