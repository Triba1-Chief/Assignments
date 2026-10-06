/**
 * Strategy interface for movement behaviors assignable to Avatars.
 *
 *
 * <p>
 * Implementations control how an avatar moves, given the world size,
 * obstacles, and all other avatars (enabling collision detection).
 * </p>
 *
 * @see Avatar
 * @see MotionEnvironment
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

interface MotionCommand {
    int[][] DIRECTIONS = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    /**
     * Applies a movement step using this motion strategy.
     *
     * @param avatar    the avatar being moved
     * @param gamePanel the environment that provides collision and boundary info
     *
     * @pre avatar != null
     * @pre gamePanel != null
     */

    void motion(Avatar avatar, MotionEnvironment gamePanel);
}