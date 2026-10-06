//No collaborations
import java.io.*;
import java.util.*;

public class Open_Addressing {
    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number 2: 2^(w-1) < A < 2^w
    int w; //where w>r
    int r;
    public int[] Table;

    int loadFactor; // n/m -- look for where it will be updated
    int n; //number of KEYS INSERTED



    protected Open_Addressing(int w, int seed, int A) {

        this.w = w;
        this.r = (int) (w-1)/2 +1;
        this.m = power2(r);

        //Changes the value of A to a different number if it matches our default key
        if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
        }
        else{
            this.A = A;
        }

        //Creation of the Hash table
        this.Table = new int[m];
        for (int i =0; i<m; i++) {
            Table[i] = -1;
        }

    }

    /** Calculate 2^w*/
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        if(seed>=0){
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max-min-1);
        return i+min+1;
    }

    /**Implements the hash function g(k)*/
    public int probe(int key, int i) {
        //TODO: implement this function and change the return statement.
        if (i >= 0 || i < this.m) {
            int h = ((this.A * key) % power2(this.w)) >> (this.w - this.r);
            int hashValue = (h + i) % power2(this.r);

            if (hashValue >= 0 && hashValue < this.m) {
                return hashValue;
            }
            else {
                throw new IllegalArgumentException("Hash value out of range");
            }
        }

        throw new IllegalArgumentException("Value i out of range");

        //return -1; //Meaning i is not within bounds or hashValue is not [0,m]
    }


    /**Inserts key k into hash table. Returns the number of collisions encountered*/
    public int insertKey(int key){
        //TODO : implement this and change the return statement.
        int collision = 0;
        int i = 0;

        while (i < this.m) {//maybe error here
            int hashValue = probe(key,i);

            if (Table[hashValue] == -1 || Table[hashValue] == -2) {
                Table[hashValue] = key;
                this.n++;
                return collision;
            }
            else {
                i++;
                collision++;
            }
        }

        return -1; //Failure to insert
    }

    /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
    public int insertKeyArray (int[] keyArray){
        int collision = 0;
        for (int key: keyArray) {
            collision += insertKey(key);
        }
        return collision;
    }

    /**Removes key k from the hash table. Returns the number of collisions encountered*/
    public int removeKey(int key){ //Hidden cases not passed
        //TODO: implement this and change the return statement
        int collision = 0, i = 0;
        int hashValue;


        while (i < this.m) {
            hashValue = probe(key,i);

            if (Table[hashValue] == -1) { //The key doesn't exist
                collision++;
                return collision;
            }
            else if (Table[hashValue] == key) {// Found
                Table[hashValue] = -2;
                return collision;
            }
            else { //Continue looping
                i++;
                collision++;
            }
        }

        return collision;
    }
}