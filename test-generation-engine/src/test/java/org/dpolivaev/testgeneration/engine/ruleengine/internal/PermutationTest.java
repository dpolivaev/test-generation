package org.dpolivaev.testgeneration.engine.ruleengine.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.dpolivaev.testgeneration.engine.ruleengine.internal.Permutation;
import org.junit.Test;

public class PermutationTest {
	final int size = 50;

	@Test
	public void permutationStartsOrdered() {
		Permutation permutation = new Permutation(size);
		for(int i = 0; i < size; i++)
			assertThat(permutation.at(i), equalTo(i));
	}
	@Test
	public void permutationIsNotOrderedAfterShuffle() {
		Permutation permutation = new Permutation(size);
		permutation.shuffle();
		int movedNumberCounter = 0;
		for(int i = 0; i < size; i++)
			if(i != permutation.at(i))
				movedNumberCounter++;
		assertThat(movedNumberCounter, not(equalTo(0)));
	}

}
