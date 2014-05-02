package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import java.util.Random;

public class Permutation {

    final public static long  RANDOM_SEED = -1;
    private static long initial_seed = 1;
    private static Random RANDOM = new Random(initial_seed);

	public Permutation(int size) {
        this(size, RANDOM);
    }

	void setRandom(long seed){
		RANDOM = seed == RANDOM_SEED ? new Random() : new Random(seed);
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