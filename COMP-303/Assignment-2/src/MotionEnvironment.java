import java.util.List;



/**
 * Represents the environment in which avatars move.
 * A motion strategy uses this interface to query only what is required
 * in order to compute and validate movement.
 *
 * <p>
 * This interface allows the motion logic to be decoupled from the concrete
 * {@code GamePanel} class. Any class that supplies world boundary dimensions,
 * avatar positions, and obstacle information may serve as a motion environment.
 * </p>
 *
 * <p>
 * Responsibility of the caller: return non-null values and ensure that
 * the data accurately represents the current world state.
 * </p>
 *
 * @see MotionCommand
 * @see GamePanel
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

interface MotionEnvironment {

    /**
     * Returns the horizontal boundary of the world in pixels.
     *
     * @return the maximum allowed X coordinate
     */

    int getXBOUND();

    /**
     * Returns the vertical boundary of the world in pixels.
     *
     * @return the maximum allowed Y coordinate
     */

    int getYBOUND();

    /**
     * Returns all rectangular obstacles that may block movement.
     * The returned list is expected to be read only.
     *
     * @return a list of obstacles within the environment
     */

    List<Obstacle> getOBSTACLES();

    /**
     * Returns all avatars that currently exist in the world.
     * The returned list is expected to be read only.
     *
     * @return a list of avatars within the environment
     */

    List<Avatar> getAVATARS();
}