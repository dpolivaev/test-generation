package ruleengine.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ruleengine.internal.Permutation;

public class PermutationTest {

	@Test
	public void permutationStartsOrdered() {
		Permutation permutation = new Permutation(5);
		for(int i = 0; i <= 4; i++)
			assertThat(permutation.at(i), equalTo(i));
	}
	@Test
	public void permutationIsNotOrderedAfterShuffle() {
		Permutation permutation = new Permutation(5);
		permutation.shuffle();
		int movedNumberCounter = 0;
		for(int i = 0; i <= 4; i++)
			if(i != permutation.at(i))
				movedNumberCounter++;
		assertThat(movedNumberCounter, not(equalTo(0)));
	}

}
