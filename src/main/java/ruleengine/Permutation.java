package ruleengine;

import java.util.Random;

public class Permutation {

    public Permutation(int size) {
        this(size, new Random());
    }

    public Permutation(int size, Random random) {
        this.random = random;
        this.permutationArray = rangeFrom0untilSize(size);
    }

    final private int[] permutationArray;
    final private Random random;

    public int[] rangeFrom0untilSize(int size) {
        int[] permutation = new int[size];
        for (int i = 0; i < size; i++)
            permutation[i] = i;
        return permutation;
    }

    public void shuffle() {
        for (int i = permutationArray.length - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);
            int a = permutationArray[index];
            permutationArray[index] = permutationArray[i];
            permutationArray[i] = a;
        }
    }

    public int at(int valueIndex) {
        return permutationArray[valueIndex];
    }

}