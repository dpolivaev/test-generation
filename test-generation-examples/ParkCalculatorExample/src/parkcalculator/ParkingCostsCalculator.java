package parkcalculator;


public class ParkingCostsCalculator {

	private ParkDateTime entryDateTime = new ParkDateTime();
	private ParkDateTime leavingDateTime = new ParkDateTime();
	private LotCostsCalulator lotCostsCalulator = null;
	private LotCostsCalulatorFactory calculatorFactory = new LotCostsCalulatorFactory();
	
	public ParkingCostsCalculator selectLot(String lot) {
		lotCostsCalulator = calculatorFactory.calculator(lot);
		return this;
	}

	public ParkingCostsCalculator setEntryDate(String entryDate) {
		entryDateTime.setDate(entryDate);
		return this;
	}

	public ParkingCostsCalculator setEntryTime(String entryTime) {
		entryDateTime.setTime(entryTime);
		return this;
	}

	public ParkingCostsCalculator setEntryDayPart(String entryDayPart) {
		entryDateTime.setDayPart(entryDayPart);
		return this;
	}

	public ParkingCostsCalculator setLeavingDate(String leavingDate) {
		leavingDateTime.setDate(leavingDate);
		return this;
	}

	public ParkingCostsCalculator setLeavingTime(String leavingTime) {
		leavingDateTime.setTime(leavingTime);
		return this;
	}

	public ParkingCostsCalculator setLeavingDayPart(String leavingDayPart) {
		leavingDateTime.setDayPart(leavingDayPart);
		return this;
	}

	public int calculateCosts() {
		DateTimeDifference difference = leavingDateTime.substract(entryDateTime);
		if(difference.differenceInMilliSeconds() <= 0)
			return 0;
		return lotCostsCalulator.calculateCosts(difference);
	}

}
