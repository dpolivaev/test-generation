package parkcalculator;

import static org.junit.Assert.*;
import org.junit.Test;

import org.dpolivaev.tsgen.java.Description;
import org.dpolivaev.tsgen.java.Coverage;
import org.dpolivaev.tsgen.java.GoalCoverage;

import parkcalculator.ParkCalculatorTestDriver;
import static parkcalculator.ParkCalculatorTestDriver.*;

import static parkcalculator.Lots.*;
import static parkcalculator.BadValues.*;
@SuppressWarnings("unused")
public class ParkCalculatorTest {
	ParkCalculatorTestDriver driver = new ParkCalculatorTestDriver();
	
	@Test
	public void test001_focCalculateParkingCostsPre1LotVALET_PARKING_pre3ParkingWeeks0Pre3ParkingDays0Pre3ParkingHours0Pre3ParkingMinutes0Pre2EntryTime01119701200Am() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 0, 
			/* parkingHours*/ 0, 
			/* parkingMinutes*/ 0, 
			/* parkingWeeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForVALET_PARKING();
	}
	
	@Test
	public void test002_focCalculateParkingCostsPre1LotVALET_PARKING_pre3ParkingWeeks1Pre3ParkingDays1Pre3ParkingHours1Pre3ParkingMinutes1Pre2EntryTime123119991200Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 1, 
			/* parkingHours*/ 1, 
			/* parkingMinutes*/ 1, 
			/* parkingWeeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForVALET_PARKING();
	}
	
	@Test
	public void test003_focCalculateParkingCostsPre1LotVALET_PARKING_pre3ParkingWeeks6Pre3ParkingDays6Pre3ParkingHours23Pre3ParkingMinutes59Pre2EntryTime022820162359Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 6, 
			/* parkingHours*/ 23, 
			/* parkingMinutes*/ 59, 
			/* parkingWeeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForVALET_PARKING();
	}
	
	@Test
	public void test004_focCalculateParkingCostsPre1LotSHORT_TERM_PARKING_pre3ParkingWeeks0Pre3ParkingDays0Pre3ParkingHours0Pre3ParkingMinutes0Pre2EntryTime01119701200Am() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 0, 
			/* parkingHours*/ 0, 
			/* parkingMinutes*/ 0, 
			/* parkingWeeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForSHORT_TERM_PARKING();
	}
	
	@Test
	public void test005_focCalculateParkingCostsPre1LotSHORT_TERM_PARKING_pre3ParkingWeeks1Pre3ParkingDays1Pre3ParkingHours1Pre3ParkingMinutes1Pre2EntryTime123119991200Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 1, 
			/* parkingHours*/ 1, 
			/* parkingMinutes*/ 1, 
			/* parkingWeeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForSHORT_TERM_PARKING();
	}
	
	@Test
	public void test006_focCalculateParkingCostsPre1LotSHORT_TERM_PARKING_pre3ParkingWeeks6Pre3ParkingDays6Pre3ParkingHours23Pre3ParkingMinutes59Pre2EntryTime022820162359Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 6, 
			/* parkingHours*/ 23, 
			/* parkingMinutes*/ 59, 
			/* parkingWeeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForSHORT_TERM_PARKING();
	}
	
	@Test
	public void test007_focCalculateParkingCostsPre1LotLONG_TERM_GARAGE_PARKING_pre3ParkingWeeks0Pre3ParkingDays0Pre3ParkingHours0Pre3ParkingMinutes0Pre2EntryTime01119701200Am() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 0, 
			/* parkingHours*/ 0, 
			/* parkingMinutes*/ 0, 
			/* parkingWeeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLONG_TERM_GARAGE_PARKING();
	}
	
	@Test
	public void test008_focCalculateParkingCostsPre1LotLONG_TERM_GARAGE_PARKING_pre3ParkingWeeks1Pre3ParkingDays1Pre3ParkingHours1Pre3ParkingMinutes1Pre2EntryTime123119991200Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 1, 
			/* parkingHours*/ 1, 
			/* parkingMinutes*/ 1, 
			/* parkingWeeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLONG_TERM_GARAGE_PARKING();
	}
	
	@Test
	public void test009_focCalculateParkingCostsPre1LotLONG_TERM_GARAGE_PARKING_pre3ParkingWeeks6Pre3ParkingDays6Pre3ParkingHours23Pre3ParkingMinutes59Pre2EntryTime022820162359Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 6, 
			/* parkingHours*/ 23, 
			/* parkingMinutes*/ 59, 
			/* parkingWeeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLONG_TERM_GARAGE_PARKING();
	}
	
	@Test
	public void test010_focCalculateParkingCostsPre1LotLONG_TERM_SURFACE_PARKING_pre3ParkingWeeks0Pre3ParkingDays0Pre3ParkingHours0Pre3ParkingMinutes0Pre2EntryTime01119701200Am() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 0, 
			/* parkingHours*/ 0, 
			/* parkingMinutes*/ 0, 
			/* parkingWeeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLONG_TERM_SURFACE_PARKING();
	}
	
	@Test
	public void test011_focCalculateParkingCostsPre1LotLONG_TERM_SURFACE_PARKING_pre3ParkingWeeks1Pre3ParkingDays1Pre3ParkingHours1Pre3ParkingMinutes1Pre2EntryTime123119991200Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 1, 
			/* parkingHours*/ 1, 
			/* parkingMinutes*/ 1, 
			/* parkingWeeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLONG_TERM_SURFACE_PARKING();
	}
	
	@Test
	public void test012_focCalculateParkingCostsPre1LotLONG_TERM_SURFACE_PARKING_pre3ParkingWeeks6Pre3ParkingDays6Pre3ParkingHours23Pre3ParkingMinutes59Pre2EntryTime022820162359Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 6, 
			/* parkingHours*/ 23, 
			/* parkingMinutes*/ 59, 
			/* parkingWeeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLONG_TERM_SURFACE_PARKING();
	}
	
	@Test
	public void test013_focCalculateParkingCostsPre1LotECONOMY_LOT_PARKING_pre3ParkingWeeks0Pre3ParkingDays0Pre3ParkingHours0Pre3ParkingMinutes0Pre2EntryTime01119701200Am() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 0, 
			/* parkingHours*/ 0, 
			/* parkingMinutes*/ 0, 
			/* parkingWeeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForECONOMY_LOT_PARKING();
	}
	
	@Test
	public void test014_focCalculateParkingCostsPre1LotECONOMY_LOT_PARKING_pre3ParkingWeeks1Pre3ParkingDays1Pre3ParkingHours1Pre3ParkingMinutes1Pre2EntryTime123119991200Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 1, 
			/* parkingHours*/ 1, 
			/* parkingMinutes*/ 1, 
			/* parkingWeeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForECONOMY_LOT_PARKING();
	}
	
	@Test
	public void test015_focCalculateParkingCostsPre1LotECONOMY_LOT_PARKING_pre3ParkingWeeks6Pre3ParkingDays6Pre3ParkingHours23Pre3ParkingMinutes59Pre2EntryTime022820162359Pm() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* parkingDays*/ 6, 
			/* parkingHours*/ 23, 
			/* parkingMinutes*/ 59, 
			/* parkingWeeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForECONOMY_LOT_PARKING();
	}
	
	@Test
	public void test016_focIllegalLotFocWrongValueNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalLot(/* wrongValue*/ NULL_VALUE);
	}
	
	@Test
	public void test017_focIllegalLotFocWrongValueBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalLot(/* wrongValue*/ BAD_VALUE);
	}
	
	@Test
	public void test018_focIllegalEntryDateFocWrongValueNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalEntryDate(/* wrongValue*/ NULL_VALUE);
	}
	
	@Test
	public void test019_focIllegalEntryDateFocWrongValueBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalEntryDate(/* wrongValue*/ BAD_VALUE);
	}
	
	@Test
	public void test020_focIllegalEntryTimeFocWrongValueNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalEntryTime(/* wrongValue*/ NULL_VALUE);
	}
	
	@Test
	public void test021_focIllegalEntryTimeFocWrongValueBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalEntryTime(/* wrongValue*/ BAD_VALUE);
	}
	
	@Test
	public void test022_focIllegalEntryDayPartFocWrongValueNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalEntryDayPart(/* wrongValue*/ NULL_VALUE);
	}
	
	@Test
	public void test023_focIllegalEntryDayPartFocWrongValueBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalEntryDayPart(/* wrongValue*/ BAD_VALUE);
	}
	
	@Test
	public void test024_focIllegalLeavingDateFocWrongValueNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalLeavingDate(/* wrongValue*/ NULL_VALUE);
	}
	
	@Test
	public void test025_focIllegalLeavingDateFocWrongValueBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalLeavingDate(/* wrongValue*/ BAD_VALUE);
	}
	
	@Test
	public void test026_focIllegalLeavingTimeFocWrongValueNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalLeavingTime(/* wrongValue*/ NULL_VALUE);
	}
	
	@Test
	public void test027_focIllegalLeavingTimeFocWrongValueBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalLeavingTime(/* wrongValue*/ BAD_VALUE);
	}
	
	@Test
	public void test028_focIllegalLeavingDayPartFocWrongValueNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalLeavingDayPart(/* wrongValue*/ NULL_VALUE);
	}
	
	@Test
	public void test029_focIllegalLeavingDayPartFocWrongValueBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.illegalLeavingDayPart(/* wrongValue*/ BAD_VALUE);
	}
	
}
