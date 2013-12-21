package parkcalculator;

public class LotCostsCalulatorFactory {

	public static final String ECONOMY_LOT_PARKING = "Economy Lot Parking";
	public static final String LONG_TERM_SURFACE_PARKING = "Long-Term Surface Parking";
	public static final String LONG_TERM_GARAGE_PARKING = "Long-Term Garage Parking";
	public static final String VALET_PARKING = "Valet Parking";
	public static final String SHORT_TERM_PARKING = "Short-Term (hourly) Parking";

	public LotCostsCalulator calculator(String lot) {
		if(VALET_PARKING.equals(lot))
			return new ValueLotCostsCalulator();
		if(SHORT_TERM_PARKING.equals(lot))
			return new ShortTermLotCostsCalulator();
		if(LONG_TERM_GARAGE_PARKING.equals(lot))
			return new LongTermLotCostsCalulator(78, 13, 2);
		if(LONG_TERM_SURFACE_PARKING.equals(lot))
			return new LongTermLotCostsCalulator(60, 10, 2);
		if(ECONOMY_LOT_PARKING.equals(lot))
			return new LongTermLotCostsCalulator(54, 9, 2);
		throw new IllegalArgumentException("unknown lot");
	}

}
