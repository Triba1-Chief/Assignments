import java.io.Serializable;
import java.util.ArrayList;
import java.text.*;
import java.lang.Math;


public class DecisionTree implements Serializable {

	DTNode rootDTNode;
	int minSizeDatalist;
	public static final long serialVersionUID = 343L;

	public DecisionTree(ArrayList<Datum> datalist , int min) {
		minSizeDatalist = min;
		rootDTNode = (new DTNode()).fillDTNode(datalist);
	}

	class DTNode implements Serializable{
		public static final long serialVersionUID = 438L;
		boolean leaf;
		int label = -1;
		int attribute;
		double threshold;
		DTNode left, right;

		DTNode() {
			leaf = true;
			threshold = Double.MAX_VALUE;
		}

		DTNode fillDTNode(ArrayList<Datum> datalist) {
			ArrayList<Datum> data1 = new ArrayList<>();
			ArrayList<Datum> data2 = new ArrayList<>();
			int i = 1;

			if (!(datalist == null || datalist.isEmpty())) {
				if (datalist.size() >= minSizeDatalist) {
					while (i < datalist.size()) {
						if (datalist.get(i).y == datalist.get((i - 1)).y) {
							i++;
						} else {
							double best_avg_entropy = Double.POSITIVE_INFINITY;
							int best_attr = -1;
							double best_threshold = -1;
							double entropyBeforeSplit = calcEntropy(datalist);
							Datum labelDiffObj = datalist.get(i);

							for (i = 0; i < labelDiffObj.x.length; i++) {
                                for (Datum a : datalist) {
									for (Datum b : datalist) {
										if (b.x[i] < a.x[i]) {
											data1.add(b);
										} else {
											data2.add(b);
										}
									}

									double leftEntropy = ((double) data1.size() / datalist.size()) * (calcEntropy(data1));
									double rightEntropy = ((double) data2.size() / datalist.size()) * (calcEntropy(data2));
									double current_avg_entropy = leftEntropy + rightEntropy;

									if (best_avg_entropy > current_avg_entropy) {
										best_avg_entropy = current_avg_entropy;
										best_attr = i;
										best_threshold = a.x[i];
									}

									data1.clear();
									data2.clear();
								}
							}

							if (Math.pow((best_avg_entropy - entropyBeforeSplit), 2) < 0.0001) {
								this.label = findMajority(datalist);
								return this;
							}

							this.leaf = false;
							this.attribute = best_attr;
							this.threshold = best_threshold;

							for (Datum datum : datalist) {
								if (datum.x[this.attribute] < this.threshold) {
									data1.add(datum);
								} else {
									data2.add(datum);
								}
							}

							this.left = (new DTNode()).fillDTNode(data1);
							this.right = (new DTNode()).fillDTNode(data2);
							return this;
						}
					}
				}

				this.label = findMajority(datalist);
				return this;
			}

			this.leaf = false;
			return this;
		}

		int findMajority(ArrayList<Datum> datalist) {
			int [] votes = new int[2];

			for (Datum data : datalist) {
				votes[data.y]+=1;
			}

			if (votes[0] >= votes[1])
				return 0;
			else
				return 1;
		}

		int classifyAtNode(double[] xQuery) {
			if (rootDTNode != null && xQuery.length == 2) {
				if (this.leaf) {
					return this.label;
				}
				else {
					if (xQuery[this.attribute] < this.threshold) {
						return this.left.classifyAtNode(xQuery);
					}
					else {
						return this.right.classifyAtNode(xQuery);
					}
				}
			}
			return -1;
		}

		public boolean equals(Object dt2) {
			DTNode curr1 = this;
			DTNode curr2;

			if (dt2 instanceof DTNode) {
				curr2 = (DTNode) dt2;

				if (curr2.leaf) {
					return curr1.leaf && (curr1.label == curr2.label);
				}

				return (curr1.attribute == curr2.attribute) && (curr1.threshold == curr2.threshold) &&
						(curr1.left.equals(curr2.left) && curr1.right.equals(curr2.right));
			}

			return false;
		}
	}

	double calcEntropy(ArrayList<Datum> datalist) {
		double entropy = 0;
		double px = 0;
		float [] counter= new float[2];
		if (datalist.size()==0)
			return 0;

		for (Datum d : datalist) {
			counter[d.y]+=1;
		}

		for (int i = 0 ; i< counter.length ; i++) {
			if (counter[i]>0) {
				px = counter[i]/datalist.size();
				entropy -= (px*Math.log(px)/Math.log(2));
			}
		}

		return entropy;
	}

	int classify(double[] xQuery ) {
		return this.rootDTNode.classifyAtNode( xQuery );
	}

	String checkPerformance( ArrayList<Datum> datalist) {
		DecimalFormat df = new DecimalFormat("0.000");
		float total = datalist.size();
		float count = 0;

		for (int s = 0 ; s < datalist.size() ; s++) {
			double[] x = datalist.get(s).x;
			int result = datalist.get(s).y;
			if (classify(x) != result) {
				count = count + 1;
			}
		}

		return df.format((count/total));
	}

	public static boolean equals(DecisionTree dt1,  DecisionTree dt2) {
		return dt1.rootDTNode.equals(dt2.rootDTNode);
	}

	private static void printNStr(int n, String str) {
		for ( int i = 0 ; i< n ; i++ ) {
			System.out.printf("%s",str);
		}
	}

	private void logNode( DTNode currentNode, int depth, String leftOrRight) {
		if ( currentNode.leaf ) {
			printNStr( depth, " ");
			System.out.printf("%s", "{ ");
			System.out.printf("%s", "depth: " + depth + ",   "  );
			System.out.printf("%s", "leaf: " + currentNode.leaf + ",   "   );
			System.out.printf("%s", "label: " + currentNode.label + ",   "   );
			System.out.printf("%s",leftOrRight + " },\n");
			return;
		}

		printNStr( depth, " ");
		System.out.printf("%s", "{ ");
		System.out.printf("%s", "depth: " + depth + ",   "  );
		System.out.printf("%s", "attribute: " + currentNode.attribute + ",   "  );
		System.out.printf("%s", "threshold: " + currentNode.threshold + ",   "  );
		System.out.printf("%s", "leaf: " + currentNode.leaf + ",   "   );
		System.out.printf("%s",leftOrRight + " },\n");

		logNode(currentNode.left, depth+1, "left");
		logNode(currentNode.right, depth+1, "right");
	}
	void logTree() {
		logNode(rootDTNode, 0, "root");
	}
}
