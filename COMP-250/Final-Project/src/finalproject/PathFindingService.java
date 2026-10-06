package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;

	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();

    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {
        ArrayList<Tile> generatedPath = new ArrayList<Tile>();

        if (g.vertices.contains(startNode)) {
            for (Tile tile : g.vertices) {
                tile.costEstimate = Double.POSITIVE_INFINITY;
                tile.predecessor = null;
            }

            startNode.costEstimate = 0;
            TilePriorityQ minheap = new TilePriorityQ(g.vertices);

            while (minheap.size != 0) {
                Tile temp = minheap.removeMin();

                for (Tile tile : g.getNeighbors(temp)) {
                    if (tile.costEstimate > temp.costEstimate + findWeight(temp, tile)) {
                        tile.costEstimate = temp.costEstimate + findWeight(temp, tile);
                        tile.predecessor = temp;
                        minheap.updateKeys(tile, tile.predecessor, tile.costEstimate);
                    }
                }
            }

            for (Tile tile : g.vertices) {
                if (tile.isDestination) {
                    Tile cur = tile;

                    while (cur != null) {
                        generatedPath.add(cur);
                        cur = cur.predecessor;
                    }

                    break;
                }
            }

            int sIndex = 0;
            int eIndex = generatedPath.size() - 1;

            while (sIndex < eIndex) {
                Tile temp = generatedPath.get(sIndex);
                generatedPath.set(sIndex, generatedPath.get(eIndex));
                generatedPath.set(eIndex, temp);
                sIndex++;
                eIndex--;
            }
        }

        return generatedPath;
    }

    public ArrayList<Tile> findPath(Tile start, Tile end) {
        ArrayList<Tile> generatedPath = new ArrayList<Tile>();

        if (g.vertices.contains(start) && g.vertices.contains(end)) {
            for (Tile tile : g.vertices) {
                tile.costEstimate = Double.POSITIVE_INFINITY;
                tile.predecessor = null;
            }

            start.costEstimate = 0;
            TilePriorityQ minheap = new TilePriorityQ(g.vertices);

            while (minheap.size != 0) {
                Tile temp = minheap.removeMin();

                for (Tile tile : g.getNeighbors(temp)) {
                    if (tile.costEstimate > temp.costEstimate + findWeight(temp, tile)) {
                        tile.costEstimate = temp.costEstimate + findWeight(temp, tile);
                        tile.predecessor = temp;
                        minheap.updateKeys(tile, tile.predecessor, tile.costEstimate);
                    }
                }
            }

            Tile cur = end;
            while (cur != null) {
                generatedPath.add(cur);
                cur = cur.predecessor;
            }

            int sIndex = 0;
            int eIndex = generatedPath.size() - 1;

            while (sIndex < eIndex) {
                Tile temp = generatedPath.get(sIndex);
                generatedPath.set(sIndex, generatedPath.get(eIndex));
                generatedPath.set(eIndex, temp);
                sIndex++;
                eIndex--;
            }
        }

        return generatedPath;
    }

    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
        ArrayList<Tile> generatedPath = new ArrayList<Tile>();

    	if (g.vertices.contains(start) && waypoints != null) {
            Tile temp = start;

            for (Tile tile : waypoints) {
                if (g.vertices.contains(tile)) {
                    if (!tile.isDestination) {
                        ArrayList<Tile> tempPath = findPath(temp, tile);

                        if (!tempPath.isEmpty()) {
                            tempPath.remove(tile);
                        }

                        generatedPath.addAll(tempPath);
                    }

                    temp = tile;
                }
                else {
                    return null;
                }
            }

            generatedPath.addAll(findPath(temp));
        }

        return generatedPath;
    }

    private double findWeight (Tile nSource, Tile destination) {
        for (int i = 0; i < g.edges.size(); i++) {
            if (g.edges.get(i).getStart().equals(nSource) && g.edges.get(i).getEnd().equals(destination)) {
                return g.edges.get(i).weight;
            }
        }

        return 0;
    }
}
