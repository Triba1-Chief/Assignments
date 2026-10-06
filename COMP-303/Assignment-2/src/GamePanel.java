import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;



/**
 * A custom JPanel that simulates and displays moving avatars and obstacles within a 2D environment.
 *
 * <p>
 * The GamePanel creates a randomized set of avatars and fixed obstacles,
 * manages animation using a Swing Timer, and handles visual rendering.
 * Each avatar is updated at every timer event, supporting movement, collision
 * with obstacles, and avoidance of boundary violations.
 * </p>
 *
 * <ul>
 *   <li>Avatars use a Motion algorithm determined at creation.</li>
 *   <li>Obstacles are gray rectangles that block avatar movement.</li>
 *   <li>The display updates every 10 milliseconds.</li>
 * </ul>
 *
 * <p>
 * The constructor assumes that initialization succeeds without invalid inputs.
 * The class takes ownership of avatar and obstacle creation, so no external input
 * validation is required here.
 * </p>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

class GamePanel extends JPanel implements ActionListener, MotionEnvironment {
    private final int NUM_AVATARS = 10;
    private final int AVATAR_SIZE = 20;
    private final Avatar[] AVATARS;
    private final java.util.List<Obstacle> OBSTACLES;
    private final javax.swing.Timer TIMER;
    private final int XBOUND = 800;//Length of frame
    private final int YBOUND = 600;//Width of frame

    /**
     * Constructs a GamePanel, initializing randomized avatars and fixed obstacles,
     * then starts the animation timer.
     *
     * <p>
     * This class controls object creation. All preconditions regarding valid avatar or
     * obstacle data are enforced by Avatar and Obstacle constructors.
     * </p>
     */

    public GamePanel() {
        setBackground(Color.BLACK);
        AVATARS = new Avatar[NUM_AVATARS];
        OBSTACLES = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < NUM_AVATARS; i++) {
            int x = rand.nextInt(XBOUND - AVATAR_SIZE);
            int y = rand.nextInt(YBOUND - AVATAR_SIZE);
            AVATARS[i] = new Avatar(x, y, AVATAR_SIZE, Motion.values()[rand.nextInt(Motion.values().length)]);
            //This randomizes the motion assigned to the avatar
        }

        OBSTACLES.add(new Obstacle(200, 150, 100, 200));
        OBSTACLES.add(new Obstacle(500, 300, 150, 100));
        OBSTACLES.add(new Obstacle(350, 50, 80, 80));

        TIMER = new javax.swing.Timer(10, this);
        TIMER.start();
    }

    /**
     * Paints all obstacles and avatars onto the panel.
     *
     * @param g the Graphics context in which to paint
     * @pre g!=null
     */

    @Override
    protected void paintComponent(Graphics g) {
        assert g != null: "Graphics must not be null";

        super.paintComponent(g);
        for (Obstacle obs : OBSTACLES) {
            obs.draw(g);
        }
        for (Avatar avatar : AVATARS) {
            avatar.draw(g);
        }
    }

    /**
     * Handles animation timer events.
     *
     * <p>
     * Each update:
     * <ul>
     *   <li>Moves each avatar using its assigned motion algorithm</li>
     *   <li>Triggers a repaint of the panel</li>
     * </ul>
     * </p>
     *
     * @param e the ActionEvent triggered by the Timer
     * @pre e != null
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        assert e != null: "Action event must not be null";

        for (Avatar avatar : AVATARS) {
            avatar.move(this);
        }
        repaint();
    }

    //Getter methods for GamePanel
    public int getXBOUND() { return XBOUND;}
    public int getYBOUND() { return YBOUND;}
    public java.util.List<Obstacle> getOBSTACLES() { return Collections.unmodifiableList(OBSTACLES);}
    public java.util.List<Avatar> getAVATARS() { return Collections.unmodifiableList(Arrays.asList(AVATARS));}
}