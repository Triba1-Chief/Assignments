package finalproject;

import finalproject.system.Tile;
import java.util.ArrayList;
import java.util.LinkedList;

public class GraphTraversal
{
	public static ArrayList<Tile> BFS(Tile s) {
		ArrayList<Tile> BFSlist = new ArrayList<Tile>();
		LinkedList<Tile> templist = new LinkedList<Tile>();

		if (s != null) {
			s.isStart = true;
			templist.addLast(s);

			while (!templist.isEmpty()) {
				Tile cur = templist.removeFirst();
				BFSlist.add(cur);

				for (Tile neighbor : cur.neighbors) {
					if (!neighbor.isStart && neighbor.isWalkable()) {
						neighbor.isStart = true;
						templist.addLast(neighbor);
					}
				}
			}

			return BFSlist;
		}

		return null;
	}

	public static ArrayList<Tile> DFS(Tile s) {
		ArrayList<Tile> DFSlist = new ArrayList<Tile>();

		if (s != null) {
			s.isStart = true;
			DFSlist.add(s);

			for (Tile neighbor : s.neighbors) {
				if (!neighbor.isStart && neighbor.isWalkable()) {
					DFSlist.addAll(DFS(neighbor));
				}
			}

			return DFSlist;
		}

		return null;
	}
}