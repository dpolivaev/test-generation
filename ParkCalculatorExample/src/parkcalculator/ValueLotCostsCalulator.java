package parkcalculator;

public class ValueLotCostsCalulator implements LotCostsCalulator {

	private static final int COSTS_PER_5_HOURS = 12;
	private static final int COSTS_PER_DAY = 18;

	public int calculateCosts(DateTimeDifference difference) {
		long completeDayCosts = difference.days() * COSTS_PER_DAY;
		if(difference.startedHours() == 0)
			return (int) completeDayCosts;
		if(difference.startedHours() <= 5)
			return (int) (completeDayCosts + COSTS_PER_5_HOURS);
		return (int) (completeDayCosts + COSTS_PER_DAY);
	}

}
