package org.dpolivaev.testgeneration.engine.ruleengine;

import java.util.Random;

public class SharedRandomHolder {
	private static long initial_seed = 1;
	public static Random SHARED_RANDOM = new Random(initial_seed);
	final public static long RANDOM_SEED = -1;
	static public void setSeed(long seed){
	SHARED_RANDOM = seed == RANDOM_SEED ? new Random() : new Random(seed);
	}
}
