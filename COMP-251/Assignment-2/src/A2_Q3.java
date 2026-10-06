//No collaborations
import java.util.*;

public class A2_Q3 {
    static final int STAIRSLIMIT = 1000; // Can't go above this
    static final int GROUNDLIMIT = 0; // Can't go below this
    static HashMap<String, String> STEPTRACKER = new HashMap<>(); // Memoization structure

    public static String directions(int[] distances) {
        STEPTRACKER.clear(); // Reset for successive calls

        // Check if all elements are zero
        if (Arrays.stream(distances).allMatch(d -> d == 0)) {
            return "IMPOSSIBLE";
        }

        // Start by moving up
        String direction = steps(1, distances[0], distances);
        return direction.equals("IMPOSSIBLE") ? "IMPOSSIBLE" : "U" + direction;
    }

    public static String steps(int index, int currentLevel, int[] distances) {
        if (currentLevel < GROUNDLIMIT || currentLevel > STAIRSLIMIT) return "IMPOSSIBLE";
        if (index == distances.length) return (currentLevel == GROUNDLIMIT) ? "" : "IMPOSSIBLE";

        String key = index + "," + currentLevel;
        if (STEPTRACKER.containsKey(key)) return STEPTRACKER.get(key);

        // Recursively check both possible moves
        String up = steps(index + 1, currentLevel + distances[index], distances);
        String down = steps(index + 1, currentLevel - distances[index], distances);

        String result = "IMPOSSIBLE";

        if (!up.equals("IMPOSSIBLE") && !down.equals("IMPOSSIBLE")) {
            result = cost(distances, up, down);
        } else if (!up.equals("IMPOSSIBLE")) {
            result = "U" + up;
        } else if (!down.equals("IMPOSSIBLE")) {
            result = "D" + down;
        }

        STEPTRACKER.put(key, result);
        return result;
    }

    private static String cost(int[] distances, String up, String down) {
        int maxUp = getMaxDisplacement(distances, "UU" + up);
        int maxDown = getMaxDisplacement(distances, "UD" + down);
        return (maxUp < maxDown) ? "U" + up : "D" + down;
    }

    private static int getMaxDisplacement(int[] distances, String path) {
        int displacement = 0, maxDisplacement = 0;
        for (int i = 0; i < path.length(); i++) {
            displacement += (path.charAt(i) == 'U') ? distances[i] : -distances[i];
            maxDisplacement = Math.max(maxDisplacement, displacement);
        }
        return maxDisplacement;
    }
}