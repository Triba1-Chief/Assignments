import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;



/**
 * Main application window for the Random Motion Game with Obstacles simulation.
 *
 * <p>
 * This class sets up a JFrame containing a {@link GamePanel}, which animates avatars
 * moving among obstacles. It establishes the window size, title, and exit behavior,
 * and ensures the window opens centered on the user's screen.
 * </p>
 *
 * <ul>
 *   <li>Initializes the main simulation panel.</li>
 *   <li>Starts the Swing event loop for animation and GUI updates.</li>
 *   <li>Ensures clean application exit via the system close operation.</li>
 * </ul>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

public class RandomMotionGameWithObstacles extends JFrame {

    /**
     * Creates a new window for the random motion simulation and initializes game settings.
     * Sets the application title, window size and location, adds the simulation panel,
     * and makes the frame visible.
     */

    public RandomMotionGameWithObstacles() {
        setTitle("Random Motion Game with Obstacles");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new GamePanel());
        setVisible(true);
    }

    /**
     * Launches the simulation in the Event Dispatch Thread (EDT) for thread-safety and
     * proper Swing behavior.
     *
     * @param args command-line arguments (not used)
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new RandomMotionGameWithObstacles();
            }
            catch (Exception e) {//Graceful exit
                JOptionPane.showMessageDialog(null, "Sorry an error occurred in the execution" +
                                "of the program. Here's the error " + e.toString() + ". Application will now close.");
                System.exit(1);
            }
        });
    }
}