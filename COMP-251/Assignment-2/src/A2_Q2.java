//No collaboration
import java.util.*;
import java.math.BigInteger;

public class A2_Q2 {
    static long swap;

    public static BigInteger num_swaps(int[] numbers) {
        swap = 0;
        ArrayList<Integer> sortedArray = mergeSort(numbers);

        return BigInteger.valueOf(swap);
    }

    public static ArrayList<Integer> mergeSort(int[] numbers) {
        ArrayList<Integer> sortedArray = new ArrayList<>();

        //Base case
        if (numbers.length == 1) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(numbers[0]);
            return temp;
        }

        //Recursion
        ArrayList<Integer> leftArray = mergeSort(Arrays.copyOfRange(numbers, 0, Math.floorDiv(numbers.length, 2)));
        ArrayList<Integer> rightArray = mergeSort(Arrays.copyOfRange(numbers, Math.floorDiv(numbers.length, 2), numbers.length));

        int leftCounter = 0, rightCounter = 0;

        //Merge
        while (leftCounter < leftArray.size() && rightCounter < rightArray.size()) {
            if (leftArray.get(leftCounter) <= rightArray.get(rightCounter)) {
                sortedArray.add(leftArray.get(leftCounter));
                leftCounter++;
            } else {
                sortedArray.add(rightArray.get(rightCounter));
                rightCounter++;
                // Every remaining element in the left array is greater → swap
                swap += leftArray.size() - leftCounter;
            }
        }

        // Add remaining elements from left array (Everything left in LA is smaller than RA)
        while (leftCounter < leftArray.size()) {
            sortedArray.add(leftArray.get(leftCounter));
            leftCounter++;
        }

        // Add remaining elements from right array
        while (rightCounter < rightArray.size()) {
            sortedArray.add(rightArray.get(rightCounter));
            rightCounter++;
        }

        return sortedArray;
    }
}