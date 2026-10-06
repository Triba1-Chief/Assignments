//No collaborations
/****************************
*
* COMP251 template file
*
* Assignment 1, Question 2b
*
*****************************/

/* contructor: creates a partition of n elements. */
/* Each element is in a separate disjoint set */
public class DisjointSetsB {

    private int[] par;
    private int[] rank;

    /* contructor: creates a partition of n elements. */
    /* Each element is in a separate disjoint set */
    DisjointSetsB(int n) {
        if (n>0) {
            par = new int[n];
            rank = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }

    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }

    /* find representative of element i */
    public int find(int i) {

        /* Fill this method (The statement return 0 is here only to compile) */
        if (i <= par.length) {
            if (par[i] == i) { //base case
                return i;
            } else { //recursive case for find
                par[i] = find(par[i]); //Path compression linking everything to the root for faster search
                return par[i];
            }
        }

        throw new IllegalArgumentException("i is out of bounds.");
    }

    /* merge sets containing elements i and j */
    public int union(int i, int j) {

        /* Fill this method (The statement return 0 is here only to compile) */
        int parentI = find(i);
        int parentJ = find(j);

        if (parentI == parentJ) {//They have the same parent
            return parentI;
        }

        if (rank[parentI] <= rank[parentJ]) {//Comparing the ranks of the parents (less than or equal treated same)
            par[parentI] = par[parentJ];
            rank[parentJ]++;
            return par[parentI];
        } else {
            par[parentJ] = par[parentI];
            rank[parentI]++;
            return par[parentJ];
        }
    }


    /* move i to the set containing j */
    public void move(int i, int j) {
        int parentI = find(i);
        int parentJ = find(j);
        int interimParent = -1;

        if (parentI != parentJ) { //roots have to be different
            if (i == parentI) { //if the element to be moved is a root
                for (int x = 0; x < this.par.length; x++) {
                    if (x != parentI && parentI == find(x)) {
                        interimParent = x; //Gets a new root
                        break;
                    }
                }

                for (int y = 0; y < this.par.length; y++) { //Assigns the remaining elements the new root
                    if (y != parentI && parentI == find(y)) {
                        par[y] = interimParent;
                        rank[parentI]--; //reduce rank of old root
                        rank[interimParent]++; //increase rank of new root
                    }
                }
            }

            par[i] = parentJ;
            rank[parentJ]++;
        }
    }

    /* return the sum of elements in the set of i */
    public int sum_elements(int i) {

        /* Fill this method */
        int parentI = find(i);
        int sum = 0;

        for (int j = 0; j < par.length; j++) {
            if (find(j) == parentI) {//Adds all the elements in that set
                sum += j;
            }
        }
        return sum;
    }

    public static void main(String[] args) {

        DisjointSetsB myset = new DisjointSetsB(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);

    }
}