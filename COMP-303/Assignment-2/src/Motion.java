import java.awt.*;
import java.util.*;




/**
 * Defines the available motion strategies for an Avatar.
 *
 * <p>
 * Each enum constant acts as a strategy and provides a concrete implementation
 * of the {@link MotionCommand#motion(Avatar, MotionEnvironment)} method.
 * </p>
 *
 * <ul>
 *     <li><strong>RANDOMMOTION:</strong> Moves in a random direction every update.</li>
 *     <li><strong>RANDOMLINEARMOTION:</strong> Moves in a straight line until blocked,
 *         then changes direction. After four straight segments, it stops.</li>
 * </ul>
 *
 * <p>
 * These strategies are stateless and shared across all avatars
 * (saving memory by not creating multiple instances of the strategies).
 * Strategy logic modifies the state of the provided Avatar instance.
 * </p>
 *
 * @author Samuel Kinyua
 * @version 1.0
 */

enum Motion implements MotionCommand {
    RANDOMMOTION {
        @Override
        public void motion(Avatar avatar, MotionEnvironment gamePanel) {
            assert avatar != null;
            assert gamePanel != null;

            int dx = avatar.getRand().nextInt(11) - 5;
            int dy = avatar.getRand().nextInt(11) - 5;

            int newX = Math.max(0, Math.min(avatar.getX() + dx, gamePanel.getXBOUND() - avatar.getSize()));
            int newY = Math.max(0, Math.min(avatar.getY() + dy, gamePanel.getYBOUND() - avatar.getSize()));

            Rectangle futureBounds = new Rectangle(newX, newY, avatar.getSize(), avatar.getSize());

            for (Obstacle obs : gamePanel.getOBSTACLES()) {
                if (obs.getBounds().intersects(futureBounds)) {
                    return;
                }
            }

            avatar.setX(newX);
            avatar.setY(newY);
        }
    },


    RANDOMLINEARMOTION {
        @Override
        public void motion(Avatar avatar, MotionEnvironment gamePanel) {
            assert avatar != null;
            assert gamePanel != null;

            // Initialize state if starting motion
            if (!avatar.isRandomLinearActive() || avatar.getLinearMovesRemaining() <= 0) {
                avatar.setLinearMovesRemaining(4);
                avatar.setRandomLinearActive(true);
                avatar.setCurrentDirection(DIRECTIONS[avatar.getRand().nextInt(DIRECTIONS.length)]);
            }

            // Stop after 4 movements
            if (avatar.getLinearMovesRemaining() <= 0) {
                avatar.setRandomLinearActive(false);
                return;
           }

            int[] dir = avatar.getCurrentDirection();
            int newX = avatar.getX() + dir[0];
            int newY = avatar.getY() + dir[1];

            Rectangle futureBounds = new Rectangle(newX, newY, avatar.getSize(), avatar.getSize());

            //Check for collision with boundary, obstacles or avatars respectively
            boolean hitObstacle = newX < 0
                    || newY < 0
                    || newX + avatar.getSize() > gamePanel.getXBOUND()
                    || newY + avatar.getSize() > gamePanel.getYBOUND()
                    || gamePanel.getOBSTACLES().stream().anyMatch(ob -> ob.getBounds().intersects(futureBounds))
                    || gamePanel.getAVATARS().stream().anyMatch(a -> a != avatar
                    && new Rectangle(a.getX(), a.getY(), a.getSize(), a.getSize()).intersects(futureBounds));

            //Change direction of movement
            if (hitObstacle) {
                avatar.setLinearMovesRemaining(avatar.getLinearMovesRemaining() - 1);

                if (avatar.getLinearMovesRemaining() > 0) {
                    avatar.setCurrentDirection(DIRECTIONS[avatar.getRand().nextInt(DIRECTIONS.length)]);
                }
                else {
                    avatar.setRandomLinearActive(false);
                }

                return;
            }

            avatar.setX(newX);
            avatar.setY(newY);
        }

    };
}
