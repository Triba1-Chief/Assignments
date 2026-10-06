import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;



/**
 * Unit tests for the GamePanel class.
 *
 * <p>
 * These tests verify:
 * </p>
 *
 * <ul>
 *     <li>panel constants for world boundaries</li>
 *     <li>avatar and obstacle initialization values</li>
 *     <li>correct behavior when the timer triggers avatar movement</li>
 * </ul>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

public class GamePanelTest {
    private GamePanel panel;

    @BeforeEach
    public void setUp() {
        panel = new GamePanel(); // invokes constructor, sets up avatars and obstacles
    }

    @Test
    public void testBoundsConstants() {
        assertEquals(800, panel.getXBOUND());
        assertEquals(600, panel.getYBOUND());
    }

    @Test
    public void testAvatarInitialization() {
        List<Avatar> avatars = panel.getAVATARS();
        assertEquals(10, avatars.size(), "There should be exactly NUM_AVATARS avatars.");

        for (Avatar avatar : avatars) {
            assertNotNull(avatar, "Avatars must be initialized.");
            assertTrue(avatar.getX() >= 0 && avatar.getX() < panel.getXBOUND());
            assertTrue(avatar.getY() >= 0 && avatar.getY() < panel.getYBOUND());
            assertEquals(20, avatar.getSize(), "Avatar size constant.");
            assertNotNull(avatar.getMotionType(), "All avatars must have a motion type.");
        }
    }

    @Test
    public void testObstacleInitialization() {
        List<Obstacle> obstacles = panel.getOBSTACLES();
        assertEquals(3, obstacles.size(), "There should be exactly three fixed obstacles.");

        for (Obstacle obs : obstacles) {
            assertNotNull(obs, "Obstacles must be initialized.");
            assertTrue(obs.getWidth() > 0, "Obstacle width positive.");
            assertTrue(obs.getHeight() > 0, "Obstacle height positive.");
            assertEquals(Color.GRAY, obs.getColor());
        }
    }

    @Test
    public void testActionPerformedTrigger() {
        List<Avatar> avatars = panel.getAVATARS();
        Avatar avatar = avatars.get(0);

        int prevX = avatar.getX();
        int prevY = avatar.getY();

        ActionEvent event = new ActionEvent(panel, ActionEvent.ACTION_PERFORMED, "test");

        //Ensure movements happens on several updates
        for(int i = 0; i < 10; i++) {
            assertDoesNotThrow(() -> panel.actionPerformed(event));
        }

        assertTrue(avatar.getX() >= 0 && avatar.getX() < panel.getXBOUND());
        assertTrue(avatar.getY() >= 0 && avatar.getY() < panel.getYBOUND());

        assertTrue(prevX != avatar.getX() || prevY != avatar.getY(),
                "Avatar should change position when actionPerformed triggers motion");
    }
}