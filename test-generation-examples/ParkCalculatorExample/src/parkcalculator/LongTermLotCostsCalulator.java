package parkcalculator;

public class LongTermLotCostsCalulator implements LotCostsCalulator {

	final private long maximalCostsPerWeek;
	final private long maximalCostsPerDay;
	final private long costsPerHour;

	public LongTermLotCostsCalulator(long costsPerWeek, long costsPerDay, long costsPerHour) {
		super();
		this.maximalCostsPerWeek = costsPerWeek;
		this.maximalCostsPerDay = costsPerDay;
		this.costsPerHour = costsPerHour;
	}

	@Override
	public int calculateCosts(DateTimeDifference difference) {
		long weeks = difference.weeks();
		long days = difference.days();
		long hours = difference.startedHours();
		final int costs = (int) (weeks * maximalCostsPerWeek + days * maximalCostsPerDay + Math.min(hours * costsPerHour, maximalCostsPerDay));
			return costs;
	}
}
