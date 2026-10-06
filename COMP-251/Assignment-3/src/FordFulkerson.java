import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		Stack<Integer> stack = new Stack<>();
		Map<Integer, Integer> parent = new HashMap<>();
		Set<Integer> visited = new HashSet<>();

		stack.push(source);
		visited.add(source);

		while (!stack.isEmpty()) {
			int current = stack.pop();

			if (current == destination) break;

			for (Edge e : graph.getEdges()) {
				if (e.nodes[0] == current && e.weight > 0 && !visited.contains(e.nodes[1])) {
					stack.push(e.nodes[1]);
					visited.add(e.nodes[1]);
					parent.put(e.nodes[1], current);
				}
			}
		}

		if (!parent.containsKey(destination)) return path; //No path

		Integer current = destination;
		while (current != null) {
			path.add(current);
			current = parent.get(current);
		}

		Collections.reverse(path);
		return path;
	}


	public static String fordfulkerson(WGraph graph) {
		int maxFlow = 0;
		WGraph residual = new WGraph(graph); // Create a residual copy
		Integer source = graph.getSource();
		Integer destination = graph.getDestination();

		ArrayList<Integer> path = pathDFS(source, destination, residual);
		while (!path.isEmpty()) {
			// Find bottleneck capacity
			int bottleneck = Integer.MAX_VALUE;
			for (int i = 0; i < path.size() - 1; i++) {
				Edge e = residual.getEdge(path.get(i), path.get(i + 1));
				bottleneck = Math.min(bottleneck, e.weight);
			}

			// Augment flow in residual graph
			for (int i = 0; i < path.size() - 1; i++) {
				int u = path.get(i), v = path.get(i + 1);

				Edge forward = residual.getEdge(u, v);
				if (forward != null) forward.weight -= bottleneck;

				Edge backward = residual.getEdge(v, u);
				if (backward != null) {
					backward.weight += bottleneck;
				} else {
					residual.getEdges().add(new Edge(v, u, bottleneck));
				}
			}

			maxFlow += bottleneck;
			path = pathDFS(source, destination, residual);
		}

		// Final flow graph output
		StringBuilder answer = new StringBuilder();
		answer.append(maxFlow).append("\n");
		answer.append(graph.getSource()).append(" ").append(graph.getDestination()).append("\n");
		answer.append(graph.getNbNodes());

		for (Edge e : graph.getEdges()) {
			Edge residualEdge = residual.getEdge(e.nodes[0], e.nodes[1]);
			int originalCapacity = e.weight;
			int remaining = (residualEdge != null) ? residualEdge.weight : 0;
			int flow = originalCapacity - remaining;
			answer.append("\n").append(e.nodes[0]).append(" ").append(e.nodes[1]).append(" ").append(flow);
		}

		return answer.toString();
	}
}
