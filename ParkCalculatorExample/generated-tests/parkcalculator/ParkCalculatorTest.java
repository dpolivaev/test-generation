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
	public void test001_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotVALET_PARKING_pre3Weeks0Pre3Days0ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 0 + 0) * 18);
	}
	
	@Test
	public void test002_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotVALET_PARKING_pre3Weeks0Pre3Days1ParkingTime5HoursOrLessPre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 0 + 1) * 18 + 12);
	}
	
	@Test
	public void test003_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotVALET_PARKING_pre3Weeks0Pre3Days6ParkingTime5HoursOrLessPre3Hours4Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 4, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 0 + 6) * 18 + 12);
	}
	
	@Test
	public void test004_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotVALET_PARKING_pre3Weeks1Pre3Days1ParkingTime5HoursOrLessPre3Hours5Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 5, 
			/* minutes*/ 0, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 1 + 1) * 18 + 12);
	}
	
	@Test
	public void test005_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotVALET_PARKING_pre3Weeks1Pre3Days6ParkingTimeMoreThan5HoursPre3Hours5Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 1 + 6) * 18 + 18);
	}
	
	@Test
	public void test006_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotVALET_PARKING_pre3Weeks6Pre3Days0ParkingTimeMoreThan5HoursPre3Hours22Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 22, 
			/* minutes*/ 0, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 6 + 0) * 18 + 18);
	}
	
	@Test
	public void test007_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotVALET_PARKING_pre3Weeks6Pre3Days6ParkingTimeMoreThan5HoursPre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 6 + 6) * 18 + 18);
	}
	
	@Test
	public void test008_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotVALET_PARKING_pre3Weeks26ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 26 + 4) * 18);
	}
	
	@Test
	public void test009_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotSHORT_TERM_PARKING_pre3Weeks0Pre3Days0ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *0 + 0) * 24);
	}
	
	@Test
	public void test010_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotSHORT_TERM_PARKING_pre3Weeks0Pre3Days1ParkingTime1HourOrLessPre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *0 + 1) * 24 + 2);
	}
	
	@Test
	public void test011_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotSHORT_TERM_PARKING_pre3Weeks0Pre3Days6ParkingTime1HourOrLessPre3Hours0Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *0 + 6) * 24 + 2);
	}
	
	@Test
	public void test012_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotSHORT_TERM_PARKING_pre3Weeks1Pre3Days1ParkingTime1HourOrLessPre3Hours1Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 1, 
			/* minutes*/ 0, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *1 + 1) * 24 + 2);
	}
	
	@Test
	public void test013_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotSHORT_TERM_PARKING_pre3Weeks1Pre3Days6ParkingTime11HoursOrLessWithAdditional12HourPre3Hours1Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 1, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *1 + 6) * 24 + 1 * 2 + 1);
	}
	
	@Test
	public void test014_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotSHORT_TERM_PARKING_pre3Weeks6Pre3Days0ParkingTime11HoursOrLessWithAdditional12HourPre3Hours11Pre3Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 11, 
			/* minutes*/ 30, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *6 + 0) * 24 + 11 * 2 + 1);
	}
	
	@Test
	public void test015_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotSHORT_TERM_PARKING_pre3Weeks6Pre3Days6ParkingTime11HoursOrLessWithAdditionalHourPre3Hours1Pre3Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 1, 
			/* minutes*/ 31, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *6 + 6) * 24 + 1 * 2 + 2);
	}
	
	@Test
	public void test016_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotSHORT_TERM_PARKING_pre3Weeks26ParkingTime11HoursOrLessWithAdditionalHourPre3Hours11Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 11, 
			/* minutes*/ 59, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *26 + 4) * 24 + 11 * 2 + 2);
	}
	
	@Test
	public void test017_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotSHORT_TERM_PARKING_pre3Weeks0Pre3Days0ParkingTime12HoursOrMorePre3Hours12Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 12, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *0 + 0) * 24 + 24);
	}
	
	@Test
	public void test018_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotSHORT_TERM_PARKING_pre3Weeks0Pre3Days1ParkingTime12HoursOrMorePre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 *0 + 1) * 24 + 24);
	}
	
	@Test
	public void test019_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks0Pre3Days6ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 78);
	}
	
	@Test
	public void test020_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks1Pre3Days1ParkingTime6HourOrLessPre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 93);
	}
	
	@Test
	public void test021_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks1Pre3Days6ParkingTime6HourOrLessPre3Hours5Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 59, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 168);
	}
	
	@Test
	public void test022_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks6Pre3Days0ParkingTime6HourOrLessPre3Hours6Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 6, 
			/* minutes*/ 0, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 480);
	}
	
	@Test
	public void test023_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks6Pre3Days6ParkingTimeMoreThan6HoursPre3Hours6Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 6, 
			/* minutes*/ 1, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 559);
	}
	
	@Test
	public void test024_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks26ParkingTimeMoreThan6HoursPre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 2093);
	}
	
	@Test
	public void test025_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks0Pre3Days0ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 0);
	}
	
	@Test
	public void test026_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks0Pre3Days1ParkingTime5HourOrLessPre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 12);
	}
	
	@Test
	public void test027_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks0Pre3Days6ParkingTime5HourOrLessPre3Hours4Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 4, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 70);
	}
	
	@Test
	public void test028_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks1Pre3Days1ParkingTime5HourOrLessPre3Hours5Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 5, 
			/* minutes*/ 0, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 80);
	}
	
	@Test
	public void test029_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks1Pre3Days6ParkingTimeMoreThan5HoursPre3Hours5Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 130);
	}
	
	@Test
	public void test030_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks6Pre3Days0ParkingTimeMoreThan5HoursPre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 370);
	}
	
	@Test
	public void test031_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks6Pre3Days6ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 420);
	}
	
	@Test
	public void test032_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks26ParkingTime5HourOrLessPre3Hours5Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 5, 
			/* minutes*/ 59, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1612);
	}
	
	@Test
	public void test033_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotECONOMY_LOT_PARKING_pre3Weeks0Pre3Days0ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 0);
	}
	
	@Test
	public void test034_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotECONOMY_LOT_PARKING_pre3Weeks0Pre3Days1ParkingTime4HourOrLessPre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 11);
	}
	
	@Test
	public void test035_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotECONOMY_LOT_PARKING_pre3Weeks0Pre3Days6ParkingTime4HourOrLessPre3Hours3Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 3, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 62);
	}
	
	@Test
	public void test036_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotECONOMY_LOT_PARKING_pre3Weeks1Pre3Days1ParkingTime4HourOrLessPre3Hours4Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 4, 
			/* minutes*/ 0, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 71);
	}
	
	@Test
	public void test037_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotECONOMY_LOT_PARKING_pre3Weeks1Pre3Days6ParkingTimeMoreThan4HoursPre3Hours4Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 4, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 117);
	}
	
	@Test
	public void test038_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotECONOMY_LOT_PARKING_pre3Weeks6Pre3Days0ParkingTimeMoreThan4HoursPre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 333);
	}
	
	@Test
	public void test039_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotECONOMY_LOT_PARKING_pre3Weeks6Pre3Days6ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 378);
	}
	
	@Test
	public void test040_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotECONOMY_LOT_PARKING_pre3Weeks26ParkingTime4HourOrLessPre3Hours3Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 3, 
			/* minutes*/ 0, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1446);
	}
	
	@Test
	public void test041_focSelectIllegalLotFocWrongValueNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ NULL_VALUE);
	}
	
	@Test
	public void test042_focSelectIllegalLotFocWrongValueBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ BAD_VALUE);
	}
	
	@Test
	public void test043_focUseIllegalDateInCalculationIllegalElementEntryDatePre2WrongValueNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setIllegalEntryDate(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test044_focUseIllegalDateInCalculationIllegalElementEntryTimePre2WrongValueBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setIllegalEntryTime(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test045_focUseIllegalDateInCalculationIllegalElementEntryDayPartPre2WrongValueNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalEntryDayPart(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test046_focUseIllegalDateInCalculationIllegalElementLeavingDatePre2WrongValueBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDate(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test047_focUseIllegalDateInCalculationIllegalElementLeavingTimePre2WrongValueNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setIllegalLeavingTime(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test048_focUseIllegalDateInCalculationIllegalElementLeavingDayPartPre2WrongValueBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDayPart(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test049_focUseLeavingDateEarlierThanEntryDate() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Focus 1
		driver.useLeavingDateEarlierThanEntryDate();
	}
	
}
