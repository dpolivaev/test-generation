package parkcalculator;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.Test;

public class ParkingCostsCalculatorTest {
// Valet Parking
// $18 per day
// $12 for five hours or less
//
	@Test
	public void valetParkingOneMinute_costs12() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Valet Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("1:01").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(12));
	}

	@Test
	public void valetParkingZeroMinutes_costs0() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Valet Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(0));
	}
	
	@Test
	public void valetParkingFiveHours_costs12() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Valet Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("6:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(12));
	}

	
	@Test
	public void valetParkingFiveHoursOneMinute_costs18() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Valet Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("6:01").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(18));
	}
	
	@Test
	public void valetParkingOneWeek_costs18x7() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Valet Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/08/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(18*7));
	}
	
	@Test
	public void valetParking24HoursOneMinute_costs30() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Valet Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/02/2001").setLeavingTime("1:01").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(30));
	}
	// Long-Term Garage Parking
	// $2.00 per hour
	// $13.00 daily maximum
	// $78.00 per week (7th day free)
	
	@Test
	public void longTermGarageParkingZeroMinutes_costs0() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Garage Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(0));
	}
	
	@Test
	public void longTermGarageParkingoneWeek_costs78() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Garage Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/08/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(78));
	}
	
	@Test
	public void longTermGarageParking1Day_costs13() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Garage Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/02/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(13));
	}
	
	@Test
	public void longTermGarageParking7hours_costs13() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Garage Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("8:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(13));
	}
	
	@Test
	public void longTermGarageParking6hours_costs12() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Garage Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("7:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(12));
	}

	// Long-Term Surface Parking (North Lot)
	// $2.00 per hour
	// $10.00 daily maximum
	// $60.00 per week (7th day free)
	
	@Test
	public void longTermSurfaceParkingZeroMinutes_costs0() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Surface Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(0));
	}
	
	@Test
	public void longTermSurfaceParkingoneWeek_costs60() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Surface Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/08/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(60));
	}
	
	@Test
	public void longTermSurfaceParking1Day_costs10() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Surface Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/02/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(10));
	}
	
	@Test
	public void longTermSurfaceParking6hours_costs10() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Surface Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("7:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(10));
	}
	
	@Test
	public void longTermSurfaceParking4hours_costs8() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Long-Term Surface Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("5:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(8));
	}

	// Economy Lot Parking
	// $2.00 per hour
	// $9.00 daily maximum
	// $54.00 per week (7th day free)
	
	@Test
	public void economyParkingZeroMinutes_costs0() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Economy Lot Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(0));
	}
	
	@Test
	public void economyParkingoneWeek_costs54() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Economy Lot Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/08/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(54));
	}
	
	@Test
	public void economyParking1Day_costs9() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Economy Lot Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/02/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(9));
	}
	
	@Test
	public void economyParking4hours1minute_costs9() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Economy Lot Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("5:01").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(9));
	}
	
	@Test
	public void economyParking3hours1minute_costs8() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Economy Lot Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("4:01").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(8));
	}

	// Short-Term (hourly) Parking
	// $2.00 first hour; $1.00 each additional 1/2 hour
	// $24.00 daily maximum

	@Test
	public void shortTermParkingZeroMinutes_costs0() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Short-Term (hourly) Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(0));
	}
	
	@Test
	public void shortTermParkingOneDay_costs24() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Short-Term (hourly) Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/02/2001").setLeavingTime("1:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(24));
	}
	
	@Test
	public void shortTermParking13hours_costs24() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Short-Term (hourly) Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("2:00").setLeavingDayPart("pm")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(24));
	}
	
	@Test
	public void shortTermParking12hours45minutes_costs24() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Short-Term (hourly) Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("1:45").setLeavingDayPart("pm")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(24));
	}
	

	@Test
	public void shortTermParkingOneHour_costs2() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Short-Term (hourly) Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("2:00").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(2));
	}


	@Test
	public void shortTermParkingOneMinute_costs2() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Short-Term (hourly) Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("1:01").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(2));
	}

	@Test
	public void shortTermParking89Minutes_costs3() {
		ParkingCostsCalculator parkingCostsCalculator = new ParkingCostsCalculator();
		int cost = parkingCostsCalculator.selectLot("Short-Term (hourly) Parking")
		.setEntryDate("01/01/2001").setEntryTime("1:00").setEntryDayPart("am")
		.setLeavingDate("01/01/2001").setLeavingTime("2:29").setLeavingDayPart("am")
		.calculateCosts();
		
		assertThat(cost, CoreMatchers.equalTo(3));
	}
}
