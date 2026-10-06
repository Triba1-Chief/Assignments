import java.util.*;
import java.lang.*;

public class A3Q3 {
    public static double arduino(double[][] locations) {
        double wire = 0.0; //Total length wire required

        boolean[] visited = new boolean[locations.length];
        double[] minDist = new double[locations.length];
        Arrays.fill(minDist, Double.MAX_VALUE);
        minDist[0] = 0;

        for (int i = 0; i < locations.length; i++) {
            // Find the unvisited node with the smallest distance
            int u = -1;
            for (int j = 0; j < locations.length; j++) {
                if (!visited[j] && (u == -1 || minDist[j] < minDist[u])) {
                    u = j;
                }
            }

            visited[u] = true;
            wire += minDist[u];

            // Update the min distances for neighbors
            for (int v = 0; v < locations.length; v++) {
                if (!visited[v]) {
                    double dist = euclidean(locations[u], locations[v]);
                    if (dist < minDist[v]) {
                        minDist[v] = dist;
                    }
                }
            }
        }

        wire = truncate(wire);
        return wire;
    }

    private static double euclidean(double[] a, double[] b) {
        double dx = a[0] - b[0];
        double dy = a[1] - b[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static double truncate(double value) {
        double factor = Math.pow(10, 2);
        return Math.floor(value * factor) / factor;
    }
}