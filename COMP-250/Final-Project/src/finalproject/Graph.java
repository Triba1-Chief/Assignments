package finalproject;

import java.util.ArrayList;
import finalproject.system.Tile;

public class Graph {
    ArrayList<Tile> vertices;
    ArrayList<Edge> edges;

	public Graph(ArrayList<Tile> vertices) {
		this.vertices = vertices;
        this.edges = new ArrayList<Edge>();
	}

    public void addEdge(Tile origin, Tile destination, double weight){
        if (this.vertices.contains(origin) && this.vertices.contains(destination)) {
            this.edges.add(new Edge(origin, destination, weight));
        }
    }

	public ArrayList<Edge> getAllEdges() {
		return this.edges;
	}

	public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> neighbors = new ArrayList<Tile>();

        if (this.vertices.contains(t)) {
            for (Edge edge : edges) {
                if (edge.getStart().equals(t)) {
                    neighbors.add(edge.getEnd());
                }
            }
        }

        return neighbors;
    }

	public double computePathCost(ArrayList<Tile> path) {
		double counter = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            for (Edge edge : edges) {
                if (edge.getStart().equals(path.get(i)) && edge.getEnd().equals(path.get(i+1))) {
                    counter += edge.weight;
                    break;
                }
            }
        }

        return counter;
	}

    public static class Edge{
    	Tile origin;
    	Tile destination;
    	double weight;

        public Edge(Tile s, Tile d, double cost){
            this.origin = s;
            this.destination = d;
        	this.weight = cost;
        }

        public Tile getStart(){
            return this.origin;
        }

        public Tile getEnd() {
            return this.destination;
        }
    }
}