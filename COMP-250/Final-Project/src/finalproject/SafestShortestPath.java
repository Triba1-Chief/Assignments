package finalproject;

import java.util.ArrayList;
import java.util.LinkedList;
import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
		generateGraph();
	}

	public void generateGraph() {
		Graph tempGraph = new Graph(GraphTraversal.DFS(source));

		for (Tile tile : tempGraph.vertices) {
			for (Tile neighbor : tile.neighbors) {
				if (tile instanceof MetroTile && neighbor instanceof MetroTile) {
					((MetroTile) tile).fixMetro(neighbor);
					tempGraph.addEdge(tile, neighbor, ((MetroTile) tile).metroDistanceCost);
				}
				else {
					tempGraph.addEdge(tile, neighbor, neighbor.distanceCost);
				}
			}
		}

		for (Tile tile : tempGraph.vertices) {
			tile.isStart = false;
		}

		source.isStart = true;
		this.costGraph = tempGraph;

		tempGraph = new Graph(GraphTraversal.DFS(source));

		for (Tile tile : tempGraph.vertices) {
			for (Tile neighbor : tile.neighbors) {
				tempGraph.addEdge(tile, neighbor, neighbor.damageCost);
			}
		}

		for (Tile tile : tempGraph.vertices) {
			tile.isStart = false;
		}

		source.isStart = true;
		this.damageGraph = tempGraph;

		tempGraph = new Graph(GraphTraversal.DFS(source));

		for (Tile tile : tempGraph.vertices) {
			for (Tile neighbor : tile.neighbors) {
				tempGraph.addEdge(tile, neighbor, neighbor.damageCost);
			}
		}

		for (Tile tile : tempGraph.vertices) {
			tile.isStart = false;
		}

		source.isStart = true;
		this.aggregatedGraph = tempGraph;
		g = costGraph;
	}

	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
		ArrayList<Tile> pc = super.findPath(start, waypoints);

		if (damageGraph.computePathCost(pc) < this.health) {
			return pc;
		}

		g = damageGraph;
		ArrayList<Tile> pd = super.findPath(start, waypoints);
		if (g.computePathCost(pd) > this.health) {
			return null;
		}

		while (true) {
			double lambda = (costGraph.computePathCost(pc) - costGraph.computePathCost(pd)) /
							(damageGraph.computePathCost(pd) - damageGraph.computePathCost(pc));

			for (Graph.Edge temp : aggregatedGraph.edges) {
				if (temp.getStart() instanceof MetroTile && temp.getEnd() instanceof MetroTile) {
					temp.weight = temp.getStart().distanceCost + (lambda * temp.getEnd().damageCost);
				}
				else {
					temp.weight = temp.getEnd().distanceCost + (lambda * temp.getEnd().damageCost);
				}
			}

			g = aggregatedGraph;
			ArrayList<Tile> pr = super.findPath(start, waypoints);

			if (Math.abs(g.computePathCost(pr) - g.computePathCost(pc)) < 0.001) {
				return pd;
			} else if (damageGraph.computePathCost(pr) <= this.health) {
				pd = pr;
			} else {
				pc = pr;
			}
		}
	}
}