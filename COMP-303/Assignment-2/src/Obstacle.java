import java.awt.*;
import java.util.*;



/**
 * Represents a static rectangular obstacle within the simulation environment.
 *
 * <p>
 * Obstacles are gray by default and are initialized with integer coordinates and dimensions on a uniform grid.
 * </p>
 *
 * <p>
 * The constructor assumes that all arguments are valid. The caller is responsible for validating the preconditions
 * listed below:
 * <UL>
 * <li>x must be zero or greater</li>
 * <li>y must be zero or greater</li>
 * <li>width must be greater than zero and within the GamePanel dimensions</li>
 * <li>height must be greater than zero and within the GamePanel dimensions</li>
 * </UL>
 * </p>
 *
 * <p>
 * If these conditions are not satisfied, program behavior is undefined by this class. Assertions used are only internal
 * checks to verify that the code is not misusing the class during development.
 * </p>
 *
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

class Obstacle {
    private int x, y, width, height;
    private Color color = Color.GRAY;

    /**
     * Initializes a new obstacle at the specified grid position and with the specified size.
     *
     * @param x      x-coordinate of the obstacle (must be >= 0)
     * @param y      y-coordinate of the obstacle (must be >= 0)
     * @param width  width of the obstacle (must be > 0)
     * @param height height of the obstacle (must be > 0)
     * @throws AssertionError at runtime if parameters are invalid when Assertions are activated
     *
     * @pre x >= 0
     * @pre y >= 0
     * @pre width > 0
     * @pre height > 0
     */

    public Obstacle(int x, int y, int width, int height) {
        assert x >= 0 && y >= 0 && width > 0 && height > 0: "X and Y >= 0 and width and height > 0.";

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns a {@link Rectangle} representing the obstacle's bounds, for collision detection.
     *
     * @return a Rectangle representing this obstacle's area
     */

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Draws the obstacle as a filled rectangle on the specified graphics context.
     *
     * @param g the Graphics context to draw into
     * @throws AssertionError at runtime if g is invalid when Assertions are activated
     */

    public void draw(Graphics g) {
        assert g != null: "Graphics must not be null.";

        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    //Getter methods for Obstacle attributes
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Color getColor() {
        return color;
    }

    //Setter methods for Obstacle attributes
    public void setX(int x) {
        if (x < 0) {throw new IllegalArgumentException("X must be equal to or greater than zero.");}

        this.x = x;
    }

    public void setY(int y) {
        if (y < 0) {throw new IllegalArgumentException("Y must be equal to or greater than zero.");}

        this.y = y;
    }

    public void setWidth(int width) {
        if (width <= 0) {throw new IllegalArgumentException("Width must be greater than zero.");}

        this.width = width;
    }

    public void setHeight(int height) {
        if (height <= 0) {throw new IllegalArgumentException("Height must be greater than zero.");}

        this.height = height;
    }

    public void setColor(Color color) {
        assert color != null : "Color must not be null.";

        this.color = color;
    }
}