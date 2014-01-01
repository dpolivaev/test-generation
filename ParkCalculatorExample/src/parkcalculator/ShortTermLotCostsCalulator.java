package parkcalculator;

public class ShortTermLotCostsCalulator implements LotCostsCalulator {

	private static final int COSTS_PER_HOUR = 2;
	private static final int COSTS_PER_DAY = 24;
	private final LongTermLotCostsCalulator longTermLotCostsCalulator;

	public ShortTermLotCostsCalulator() {
		longTermLotCostsCalulator = new LongTermLotCostsCalulator(COSTS_PER_DAY * 7, COSTS_PER_DAY, COSTS_PER_HOUR);
	}

	@Override
	public int calculateCosts(DateTimeDifference difference) {
		int hours = difference.hours();
		long minutes = difference.minutes();
		final int costs = longTermLotCostsCalulator.calculateCosts(difference);
		if(hours > 0 && hours < 12 && minutes > 0 && minutes <= 30)
			return costs - 1;
		return costs;
	}

}
