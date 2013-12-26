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
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 0 hours"}),
			@GoalCoverage(goal = "requirements", item="Req2", coverage={"VALET_PARKING costs (7 * 0 + 0) * 18"}),
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 0 + 0) * 18"})
		}
	)
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
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"}),
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 0 + 1) * 18 + 12"})
		}
	)
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
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 0 + 6) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"})
		}
	)
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
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 1 + 1) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"})
		}
	)
	@Test
	public void test004_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotVALET_PARKING_pre3Weeks1Pre3Days1ParkingTime5HoursOrLessPre3Hours5Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
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
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs more than 5 hours"}),
			@GoalCoverage(goal = "requirements", item="Req2", coverage={"VALET_PARKING costs (7 * 1 + 6) * 18 + 18"})
		}
	)
	@Test
	public void test005_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotVALET_PARKING_pre3Weeks1Pre3Days6ParkingTimeMoreThan5HoursPre3Hours5Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
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
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req2", coverage={"VALET_PARKING costs (7 * 6 + 0) * 18 + 18"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs more than 5 hours"})
		}
	)
	@Test
	public void test006_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotVALET_PARKING_pre3Weeks6Pre3Days0ParkingTimeMoreThan5HoursPre3Hours22Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
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
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req2", coverage={"VALET_PARKING costs (7 * 6 + 6) * 18 + 18"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs more than 5 hours"})
		}
	)
	@Test
	public void test007_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotVALET_PARKING_pre3Weeks6Pre3Days6ParkingTimeMoreThan5HoursPre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
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
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 26 + 4) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"})
		}
	)
	@Test
	public void test008_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotVALET_PARKING_pre3Weeks26ParkingTime5HoursOrLessPre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 26 + 4) * 18 + 12);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 0 hours"}),
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 26 + 4) * 24"})
		}
	)
	@Test
	public void test009_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotSHORT_TERM_PARKING_pre3Weeks26ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
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
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 26 + 4) * 24);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 1 hour or less"}),
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 0 + 1) * 24 + 2"})
		}
	)
	@Test
	public void test010_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotSHORT_TERM_PARKING_pre3Weeks0Pre3Days1ParkingTime1HourOrLessPre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 0 + 1) * 24 + 2);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 0 + 6) * 24 + 2"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 1 hour or less"})
		}
	)
	@Test
	public void test011_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotSHORT_TERM_PARKING_pre3Weeks0Pre3Days6ParkingTime1HourOrLessPre3Hours0Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 0 + 6) * 24 + 2);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 0 + 0) * 24 + 2"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 1 hour or less"})
		}
	)
	@Test
	public void test012_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotSHORT_TERM_PARKING_pre3Weeks0Pre3Days0ParkingTime1HourOrLessPre3Hours1Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 1, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 0 + 0) * 24 + 2);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 11 hours or less with additional 1/2 hour"}),
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 1 + 1) * 24 + 1 * 2 + 1"})
		}
	)
	@Test
	public void test013_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotSHORT_TERM_PARKING_pre3Weeks1Pre3Days1ParkingTime11HoursOrLessWithAdditional12HourPre3Hours1Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 1, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 1 + 1) * 24 + 1 * 2 + 1);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 1 + 6) * 24 + 11 * 2 + 1"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 11 hours or less with additional 1/2 hour"})
		}
	)
	@Test
	public void test014_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotSHORT_TERM_PARKING_pre3Weeks1Pre3Days6ParkingTime11HoursOrLessWithAdditional12HourPre3Hours11Pre3Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 11, 
			/* minutes*/ 30, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 1 + 6) * 24 + 11 * 2 + 1);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 11 hours or less with additional hour"}),
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 6 + 0) * 24 + 1 * 2 + 2"})
		}
	)
	@Test
	public void test015_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotSHORT_TERM_PARKING_pre3Weeks6Pre3Days0ParkingTime11HoursOrLessWithAdditionalHourPre3Hours1Pre3Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 1, 
			/* minutes*/ 31, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 6 + 0) * 24 + 1 * 2 + 2);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 6 + 6) * 24 + 11 * 2 + 2"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 11 hours or less with additional hour"})
		}
	)
	@Test
	public void test016_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotSHORT_TERM_PARKING_pre3Weeks6Pre3Days6ParkingTime11HoursOrLessWithAdditionalHourPre3Hours11Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 11, 
			/* minutes*/ 59, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 6 + 6) * 24 + 11 * 2 + 2);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 12 hours or more"}),
			@GoalCoverage(goal = "requirements", item="Req6", coverage={"SHORT_TERM_PARKING costs (7 * 1 + 6) * 24 + 24"})
		}
	)
	@Test
	public void test017_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotSHORT_TERM_PARKING_pre3Weeks1Pre3Days6ParkingTime12HoursOrMorePre3Hours12Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 12, 
			/* minutes*/ 0, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 1 + 6) * 24 + 24);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req6", coverage={"SHORT_TERM_PARKING costs (7 * 1 + 1) * 24 + 24"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 12 hours or more"})
		}
	)
	@Test
	public void test018_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotSHORT_TERM_PARKING_pre3Weeks1Pre3Days1ParkingTime12HoursOrMorePre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 1 + 1) * 24 + 24);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req10", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 6 * 13"}),
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 0 hours"}),
			@GoalCoverage(goal = "requirements", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 6 * 13"})
		}
	)
	@Test
	public void test019_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks6Pre3Days6ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
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
		driver.verifyParkingCosts(/* expectedCosts*/ 6 * 78 + 6 * 13);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 6 hour or less"}),
			@GoalCoverage(goal = "requirements", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 0 * 13 + 0 * 2 + 2"})
		}
	)
	@Test
	public void test020_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks6Pre3Days0ParkingTime6HourOrLessPre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 6 * 78 + 0 * 13 + 0 * 2 + 2);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 0 * 78 + 6 * 13 + 5 * 2 + 2"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 6 hour or less"})
		}
	)
	@Test
	public void test021_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks0Pre3Days6ParkingTime6HourOrLessPre3Hours5Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 0 * 78 + 6 * 13 + 5 * 2 + 2);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 0 * 78 + 0 * 13 + 6 * 2 + 0"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 6 hour or less"})
		}
	)
	@Test
	public void test022_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks0Pre3Days0ParkingTime6HourOrLessPre3Hours6Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 6, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 0 * 78 + 0 * 13 + 6 * 2 + 0);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING more than 6 hours"}),
			@GoalCoverage(goal = "requirements", item="Req9", coverage={"LONG_TERM_GARAGE_PARKING costs 0 * 78 + 1 * 13 + 13"})
		}
	)
	@Test
	public void test023_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks0Pre3Days1ParkingTimeMoreThan6HoursPre3Hours6Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 6, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 0 * 78 + 1 * 13 + 13);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req9", coverage={"LONG_TERM_GARAGE_PARKING costs 26 * 78 + 4 * 13 + 13"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING more than 6 hours"})
		}
	)
	@Test
	public void test024_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_GARAGE_PARKING_pre3Weeks26ParkingTimeMoreThan6HoursPre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 26 * 78 + 4 * 13 + 13);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"0 hours 0 minutes"})
		}
	)
	@Test
	public void test025_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks0Pre3Days6Pre3Hours0Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"0 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 0 weeks 0 days"})
		}
	)
	@Test
	public void test026_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks0Pre3Days0Pre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 59 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"0 hours 59 minutes"})
		}
	)
	@Test
	public void test027_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks0Pre3Days1Pre3Hours0Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"4 hours 1 minutes"})
		}
	)
	@Test
	public void test028_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks1Pre3Days1Pre3Hours4Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 4, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 59 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"4 hours 59 minutes"})
		}
	)
	@Test
	public void test029_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks1Pre3Days6Pre3Hours4Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 4, 
			/* minutes*/ 59, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"4 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 6 weeks 0 days"})
		}
	)
	@Test
	public void test030_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks6Pre3Days0Pre3Hours4Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 4, 
			/* minutes*/ 0, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 5 hours 59 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"5 hours 59 minutes"})
		}
	)
	@Test
	public void test031_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks6Pre3Days6Pre3Hours5Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 59, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 5 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"5 hours 0 minutes"})
		}
	)
	@Test
	public void test032_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks26Pre3Hours5Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 5, 
			/* minutes*/ 0, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 5 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"5 hours 1 minutes"})
		}
	)
	@Test
	public void test033_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks6Pre3Days6Pre3Hours5Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 1, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 23 hours 59 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"23 hours 59 minutes"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 6 weeks 0 days"})
		}
	)
	@Test
	public void test034_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks6Pre3Days0Pre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
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
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 23 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"23 hours 0 minutes"})
		}
	)
	@Test
	public void test035_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks1Pre3Days6Pre3Hours23Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 0, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 23 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"23 hours 1 minutes"})
		}
	)
	@Test
	public void test036_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotLONG_TERM_SURFACE_PARKING_pre3Weeks1Pre3Days1Pre3Hours23Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 23, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING 0 hours"}),
			@GoalCoverage(goal = "requirements", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 9"}),
			@GoalCoverage(goal = "requirements", item="Req18", coverage={"ECONOMY_LOT_PARKING costs 9"})
		}
	)
	@Test
	public void test037_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotECONOMY_LOT_PARKING_pre3Weeks0Pre3Days1ParkingTime0Hours() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 9);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"}),
			@GoalCoverage(goal = "requirements", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 56"})
		}
	)
	@Test
	public void test038_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotECONOMY_LOT_PARKING_pre3Weeks0Pre3Days6ParkingTime4HourOrLessPre3Hours0Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 56);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 8"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test039_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotECONOMY_LOT_PARKING_pre3Weeks0Pre3Days0ParkingTime4HourOrLessPre3Hours3Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 3, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 8);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 1448"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test040_focCalculateParkingCostsPre2EntryTime01119701200AmPre1LotECONOMY_LOT_PARKING_pre3Weeks26ParkingTime4HourOrLessPre3Hours4Pre3Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 4, 
			/* minutes*/ 0, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1448);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"}),
			@GoalCoverage(goal = "requirements", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 9"})
		}
	)
	@Test
	public void test041_focCalculateParkingCostsPre2EntryTime123119991200PmPre1LotECONOMY_LOT_PARKING_pre3Weeks0Pre3Days0ParkingTimeMoreThan4HoursPre3Hours4Pre3Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 4, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 9);
	}
	
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 18"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"})
		}
	)
	@Test
	public void test042_focCalculateParkingCostsPre2EntryTime022820162359PmPre1LotECONOMY_LOT_PARKING_pre3Weeks0Pre3Days1ParkingTimeMoreThan4HoursPre3Hours23Pre3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
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
		driver.verifyParkingCosts(/* expectedCosts*/ 18);
	}
	
	@Test
	public void test043_focSelectIllegalLotFocWrongValueNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ NULL_VALUE);
	}
	
	@Test
	public void test044_focSelectIllegalLotFocWrongValueBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ BAD_VALUE);
	}
	
	@Test
	public void test045_focUseIllegalDateInCalculationIllegalElementEntryDatePre2WrongValueNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setIllegalEntryDate(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test046_focUseIllegalDateInCalculationIllegalElementEntryTimePre2WrongValueBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setIllegalEntryTime(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test047_focUseIllegalDateInCalculationIllegalElementEntryDayPartPre2WrongValueNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalEntryDayPart(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test048_focUseIllegalDateInCalculationIllegalElementLeavingDatePre2WrongValueBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDate(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test049_focUseIllegalDateInCalculationIllegalElementLeavingTimePre2WrongValueBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setIllegalLeavingTime(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test050_focUseIllegalDateInCalculationIllegalElementLeavingDayPartPre2WrongValueNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDayPart(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Test
	public void test051_focUseLeavingDateEarlierThanEntryDate() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Focus 1
		driver.useLeavingDateEarlierThanEntryDate();
	}
	
}
