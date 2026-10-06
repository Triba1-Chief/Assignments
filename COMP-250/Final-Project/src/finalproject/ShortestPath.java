package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class ShortestPath extends PathFindingService {
    public ShortestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
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
        g = tempGraph;
	}
}