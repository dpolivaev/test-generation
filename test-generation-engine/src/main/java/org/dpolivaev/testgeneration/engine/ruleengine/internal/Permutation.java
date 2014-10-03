package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import java.util.Random;

import org.dpolivaev.testgeneration.engine.ruleengine.SharedRandomHolder;

public class Permutation {

    private static Random SHARED_RANDOM = SharedRandomHolder.SHARED_RANDOM;

	public Permutation(int size) {
        this(size, SHARED_RANDOM);
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
        for (int i = permutationArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int a = permutationArray[index];
            permutationArray[index] = permutationArray[i];
            permutationArray[i] = a;
        }
    }

    public int at(int valueIndex) {
        return valueIndex < permutationArray.length ?  permutationArray[valueIndex] : valueIndex;
    }

}