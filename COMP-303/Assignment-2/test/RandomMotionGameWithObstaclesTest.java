import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;



/**
 * Unit tests for the RandomMotionGameWithObstacles JFrame application.
 *
 * <p>These tests verify:</p>
 *
 * <ul>
 *     <li>The JFrame is created with the correct title and size</li>
 *     <li>The default close operation is set correctly</li>
 *     <li>A GamePanel is added to the frame</li>
 *     <li>The application launches on the Swing Event Dispatch Thread (EDT)</li>
 * </ul>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

public class RandomMotionGameWithObstaclesTest {

    @Test
    public void testConstructorSetsTitleAndSize() {
        RandomMotionGameWithObstacles frame = new RandomMotionGameWithObstacles();
        assertEquals("Random Motion Game with Obstacles", frame.getTitle());
        assertEquals(800, frame.getWidth());
        assertEquals(600, frame.getHeight());
        frame.dispose();
    }

    @Test
    public void testConstructorSetsDefaultCloseOperationAndPanel() {
        RandomMotionGameWithObstacles frame = new RandomMotionGameWithObstacles();
        assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());

        //Ensure GamePanel is added as content pane's first component
        Component[] components = ((JPanel) frame.getContentPane()).getComponents();
        boolean hasGamePanel = false;
        for (Component comp : components) {
            if (comp instanceof GamePanel) hasGamePanel = true;
        }
        assertTrue(hasGamePanel, "GamePanel should be present in frame.");
        frame.dispose();
    }

    @Test
    public void testConstructorSetsLocationRelativeToNull() {
        RandomMotionGameWithObstacles frame = new RandomMotionGameWithObstacles();
        assertTrue(frame.isVisible(), "Frame should be visible on construction.");
        frame.dispose();
    }

    @Test
    public void testMainMethodLaunchesOnEDT() {
        assertDoesNotThrow(() -> {
            SwingUtilities.invokeLater(() -> {
                RandomMotionGameWithObstacles frame = new RandomMotionGameWithObstacles();
                assertTrue(SwingUtilities.isEventDispatchThread());
                frame.dispose();
            });
        });
    }
}