import java.awt.*;
import java.util.*;



/**
 * Represents an avatar character within the simulation.
 *  <p>
 *  An Avatar maintains its position, size, color, current motion strategy,
 *  and state for incremental motion (for random linear movement), and can
 *  draw itself or delegate motion decisions to its Motion strategy.
 *  </p>
 *
 *  <p>
 *  The constructor assumes that all arguments are valid. The caller is responsible for validating the preconditions
 *  listed below:
 *  <UL>
 *    <li>x must be zero or greater</li>
 *    <li>y must be zero or greater</li>
 *    <li>size must be greater than zero and within the GamePanel dimensions</li>
 *    <li>motionType must not be null</li>
 *  </UL>
 *  </p>
 *
 * <p>
 * If these conditions are not satisfied, program behavior is undefined by this class. Assertions used are only internal
 * checks to verify that the code is not misusing the class during development.
 * </p>
 *
 *  @author Samuel Kinyua
 *  @version 1.0
 */

class Avatar {
    private int x, y, size;
    private Color color;
    private Random rand;
    private Motion motionType;
    private int linearMovesRemaining = 0;
    private int[] currentDirection = null; //direction of current straight-line motion
    private boolean isRandomLinearActive = false; //check if avatar is in RandomLinearMotion state

    /**
     * Constructs an avatar at the specified position with given size, random color and motion type.
     *
     * @param x initial x-coordinate (must be non-negative)
     * @param y initial y-coordinate (must be non-negative)
     * @param size diameter of the avatar (must be positive)
     * @param motionType the avatar's initial motion algorithm
     * @throws AssertionError at runtime if parameters are invalid when Assertions are activated
     *
     *
     * @pre x >= 0
     * @pre y >= 0
     * @pre size > 0
     * @pre motionType != null
     */

    public Avatar(int x, int y, int size, Motion motionType) {
        assert x >= 0 && y >= 0 && size > 0: "X, Y and size must be positive, and size > 0.";
        assert motionType != null: "Invalid motion type has been given.";

        this.x = x;
        this.y = y;
        this.size = size;
        this.rand = new Random();
        this.color = new Color(randColor(), randColor(), randColor());
        this.motionType = motionType;
    }

    /**
     * Moves the avatar according to its current Motion strategy.
     * All required collision and environment data are passed as parameters.
     *
     * @param gamePanel contains information about the current GamePanel object
     * @throws AssertionError at runtime if parameter is invalid when Assertions are activated
     *
     * @pre gamePanel != null
     */

    public void move(GamePanel gamePanel) {
        assert gamePanel != null: "GamePanel cannot be null.";

        this.motionType.motion(this, gamePanel);
    }

    /**
     * Renders the avatar onto the provided Graphics context.
     *
     * @param g the Graphics context in which to draw the avatar
     * @throws AssertionError at runtime if parameter is invalid when Assertions are activated
     *
     * @pre g != null
     */

    public void draw(Graphics g) {
        assert g != null: "Graphics must not be null.";
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    private int randColor() {
        return 100 + rand.nextInt(156);
    }

    //Getter methods for Avatar attributes
    public int getX () {return this.x;}
    public int getY () {return this.y;}
    public int getSize () {return this.size;}
    public Color getColor () {return this.color;}
    public Random getRand () {return this.rand;}
    public Motion getMotionType () {return this.motionType;}
    public int[] getCurrentDirection() {return this.currentDirection;}
    public boolean isRandomLinearActive() {return isRandomLinearActive;}
    public int getLinearMovesRemaining() {return linearMovesRemaining;}

    //Setter methods for Avatar attributes
    public void setMotionType(Motion motionType) {this.motionType = motionType;}
    public void setColor () {this.color = new Color(randColor(), randColor(), randColor());}
    public void setRandomLinearActive(boolean isRandomLinearActive) {this.isRandomLinearActive = isRandomLinearActive;}
    public void setCurrentDirection(int[] direction) {
        if (direction == null || direction.length != 2) {
            throw new IllegalArgumentException("Direction must be a 2 element array.");
        }

        int dx = direction[0];
        int dy = direction[1];

        // Only one of dx, dy can be nonzero and must be exactly +-1
        boolean valid = (Math.abs(dx) == 1 && dy == 0) || (Math.abs(dy) == 1 && dx == 0);

        if (!valid) {
            throw new IllegalArgumentException("Direction must be cardinal: {1,0}, {-1,0}, {0,1}, or {0,-1}");
        }

        this.currentDirection = direction;
    }

    public void setLinearMovesRemaining(int linearMovesRemaining) {
        if (linearMovesRemaining < 0) {throw new IllegalArgumentException("Linear moves remaining must be equal to " +
                "or greater than zero.");}

        this.linearMovesRemaining = linearMovesRemaining;
    }

    public void setX (int x) {
        if (x < 0) {throw new IllegalArgumentException("X must be equal to or greater than zero.");}

        this.x = x;
    }

    public void setY (int y) {
        if (y < 0) {throw new IllegalArgumentException("Y must be equal to or greater than zero.");}

        this.y = y;
    }

    public void setSize (int size) {
        if (size <= 0) {throw new IllegalArgumentException("Size must be greater than zero.");}

        this.size = size;
    }
}