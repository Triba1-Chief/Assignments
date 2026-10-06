package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
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
                    tempGraph.addEdge(tile, neighbor, ((MetroTile) tile).metroTimeCost);
                }
                else {
                    tempGraph.addEdge(tile, neighbor, neighbor.timeCost);
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
