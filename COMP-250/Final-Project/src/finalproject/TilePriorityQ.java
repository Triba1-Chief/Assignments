package finalproject;

import java.util.ArrayList;
import finalproject.system.Tile;

public class TilePriorityQ {
	int size;
	Tile[] heaplist;

	private void downHeap (int startIndex, int maxIndex) {
		int k = startIndex;

		while (2 * k <= maxIndex) {
			int child = 2 * k;

			if (child < maxIndex) {
				if (heaplist[child + 1].costEstimate < heaplist[child].costEstimate) {
					child = child + 1;
				}
			}

			if (heaplist[child].costEstimate < heaplist[k].costEstimate) {
				Tile temp = heaplist[child];
				heaplist[child] = heaplist[k];
				heaplist[k] = temp;
				k = child;
			}
			else {
				break;
			}
		}
	}

	private void upHeap(int index) {
		while (index > 1) {
			int parentIndex = index / 2;

			if (heaplist[index].costEstimate < heaplist[parentIndex].costEstimate) {
				Tile temp = heaplist[index];
				heaplist[index] = heaplist[parentIndex];
				heaplist[parentIndex] = temp;
				index = parentIndex;
			} else {
				break;
			}
		}
	}

	public TilePriorityQ (ArrayList<Tile> vertices) {
		this.heaplist = new Tile[vertices.size() + 1];
		this.size = vertices.size();

		for (int i = 0; i < vertices.size(); i++) {
			heaplist[i+1] = vertices.get(i);
			upHeap(i);
		}
	}

	public Tile removeMin() {
		if (size > 0) {
			Tile temp = heaplist[1];
			heaplist[1] = heaplist[size];
			size = size - 1;
			downHeap(1, size);
			return temp;
		}
		return null;
	}

	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		for (int i = 1; i<= size; i++) {
			if (heaplist[i].equals(t)) {
				t.predecessor = newPred;
				t.costEstimate = newEstimate;
				upHeap(i);
				downHeap(1, size);
				break;
			}
		}
	}
}