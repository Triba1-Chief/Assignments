//No collaborations
import java.util.*;

public class A3Q2 {
    static Map<String, ArrayList<String>> map = new HashMap<>(); //Keeps track of flight connections
    static Map<String, Boolean> cycle = new HashMap<>(); //Keeps track if a city is in a cycle

    public static String[] time_pass(String[][] itinerary, String[] cities) {
        map.clear();
        cycle.clear();

        String[] answer = new String[cities.length];

        for (String[] airport : itinerary) {
            map.putIfAbsent(airport[0], new ArrayList<>());
            map.get(airport[0]).add(airport[1]);
            cycle.putIfAbsent(airport[0], false);
            cycle.putIfAbsent(airport[1], false);
        }

        for (String city : map.keySet()) {
            if (!cycle.get(city)) { //Only check if not already known
                cycle_finder(city, new HashSet<>());
            }
        }

        // Determine if each city succeeds or fails
        for (int i = 0; i < cities.length; i++) {
            answer[i] = cycle.getOrDefault(cities[i], false) ? "succeed" : "failed";
        }

        return answer;
    }

    private static boolean cycle_finder(String current, Set<String> path) {
        if (cycle.get(current) ) { //Base case: already explored
            return true;
        }
        if (path.contains(current)) { //Base case: Cycle detected
            cycle.put(current, true);
            return true;
        }
        if (!map.containsKey(current) || map.get(current).isEmpty()) { //Base case: No outgoing flights
            return false;
        }

        path.add(current);

        for (String airport : map.get(current)) {
            if (cycle_finder(airport, path)) {
                cycle.put(current, true);
                return true;
            }
        }

        path.remove(current);
        return false;
    }
}