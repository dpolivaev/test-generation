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
	
	@Description("dayCosts: (7 * 0 + 0) * 18\n"
+"parking time: 0 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 0 hours"}),
			@GoalCoverage(goal = "requirements", item="Req2", coverage={"VALET_PARKING costs (7 * 0 + 0) * 18"}),
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 0 + 0) * 18"})
		}
	)
	@Test
	public void test001_calculateParkingCosts01119701200AmVALET_PARKING_weeks0Days0() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 0 + 1) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"}),
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 0 + 1) * 18 + 12"})
		}
	)
	@Test
	public void test002_calculateParkingCosts123119991200PmVALET_PARKING_weeks0Days1Hours0Minutes1() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 0 + 6) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 0 + 6) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"})
		}
	)
	@Test
	public void test003_calculateParkingCosts022820162359PmVALET_PARKING_weeks0Days6Hours4Minutes59() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 1 + 1) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 1 + 1) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"})
		}
	)
	@Test
	public void test004_calculateParkingCosts123119991200PmVALET_PARKING_weeks1Days1Hours5Minutes0() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 1 + 6) * 18\n"
+"parking time: more than 5 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs more than 5 hours"}),
			@GoalCoverage(goal = "requirements", item="Req2", coverage={"VALET_PARKING costs (7 * 1 + 6) * 18 + 18"})
		}
	)
	@Test
	public void test005_calculateParkingCosts022820162359PmVALET_PARKING_weeks1Days6Hours5Minutes1() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 6 + 0) * 18\n"
+"parking time: more than 5 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req2", coverage={"VALET_PARKING costs (7 * 6 + 0) * 18 + 18"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs more than 5 hours"})
		}
	)
	@Test
	public void test006_calculateParkingCosts01119701200AmVALET_PARKING_weeks6Days0Hours22Minutes0() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 6 + 6) * 18\n"
+"parking time: more than 5 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req2", coverage={"VALET_PARKING costs (7 * 6 + 6) * 18 + 18"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs more than 5 hours"})
		}
	)
	@Test
	public void test007_calculateParkingCosts022820162359PmVALET_PARKING_weeks6Days6Hours23Minutes59() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 26 + 4) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req3", coverage={"VALET_PARKING costs (7 * 26 + 4) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"})
		}
	)
	@Test
	public void test008_calculateParkingCosts01119701200AmVALET_PARKING_weeks26Hours0Minutes1() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 26 + 4) * 24\n"
+"parking time: 0 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 0 hours"}),
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 26 + 4) * 24"})
		}
	)
	@Test
	public void test009_calculateParkingCosts123119991200PmSHORT_TERM_PARKING_weeks26() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 0 + 1) * 24\n"
+"parking time: 1 hour or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 1 hour or less"}),
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 0 + 1) * 24 + 2"})
		}
	)
	@Test
	public void test010_calculateParkingCosts022820162359PmSHORT_TERM_PARKING_weeks0Days1Hours0Minutes1() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 0 + 6) * 24\n"
+"parking time: 1 hour or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 0 + 6) * 24 + 2"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 1 hour or less"})
		}
	)
	@Test
	public void test011_calculateParkingCosts01119701200AmSHORT_TERM_PARKING_weeks0Days6Hours0Minutes59() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 0 + 0) * 24\n"
+"parking time: 1 hour or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 0 + 0) * 24 + 2"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 1 hour or less"})
		}
	)
	@Test
	public void test012_calculateParkingCosts123119991200PmSHORT_TERM_PARKING_weeks0Days0Hours1Minutes0() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 1 + 1) * 24\n"
+"parking time: 11 hours or less with additional 1/2 hour")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 11 hours or less with additional 1/2 hour"}),
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 1 + 1) * 24 + 1 * 2 + 1"})
		}
	)
	@Test
	public void test013_calculateParkingCosts123119991200PmSHORT_TERM_PARKING_weeks1Days1Hours1Minutes1() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 1 + 6) * 24\n"
+"parking time: 11 hours or less with additional 1/2 hour")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 1 + 6) * 24 + 11 * 2 + 1"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 11 hours or less with additional 1/2 hour"})
		}
	)
	@Test
	public void test014_calculateParkingCosts022820162359PmSHORT_TERM_PARKING_weeks1Days6Hours11Minutes30() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 6 + 0) * 24\n"
+"parking time: 11 hours or less with additional hour")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 11 hours or less with additional hour"}),
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 6 + 0) * 24 + 1 * 2 + 2"})
		}
	)
	@Test
	public void test015_calculateParkingCosts01119701200AmSHORT_TERM_PARKING_weeks6Days0Hours1Minutes31() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 6 + 6) * 24\n"
+"parking time: 11 hours or less with additional hour")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req5", coverage={"SHORT_TERM_PARKING costs (7 * 6 + 6) * 24 + 11 * 2 + 2"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 11 hours or less with additional hour"})
		}
	)
	@Test
	public void test016_calculateParkingCosts01119701200AmSHORT_TERM_PARKING_weeks6Days6Hours11Minutes59() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 1 + 6) * 24\n"
+"parking time: 12 hours or more")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 12 hours or more"}),
			@GoalCoverage(goal = "requirements", item="Req6", coverage={"SHORT_TERM_PARKING costs (7 * 1 + 6) * 24 + 24"})
		}
	)
	@Test
	public void test017_calculateParkingCosts123119991200PmSHORT_TERM_PARKING_weeks1Days6Hours12Minutes0() throws Exception {
		
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
	
	@Description("dayCosts: (7 * 1 + 1) * 24\n"
+"parking time: 12 hours or more")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req6", coverage={"SHORT_TERM_PARKING costs (7 * 1 + 1) * 24 + 24"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req4", coverage={"SHORT_TERM_PARKING 12 hours or more"})
		}
	)
	@Test
	public void test018_calculateParkingCosts022820162359PmSHORT_TERM_PARKING_weeks1Days1Hours23Minutes59() throws Exception {
		
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
	
	@Description("parking time: 0 hours\n"
+"dayCosts: 6 * 78 + 6 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req10", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 6 * 13"}),
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 0 hours"}),
			@GoalCoverage(goal = "requirements", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 6 * 13"})
		}
	)
	@Test
	public void test019_calculateParkingCosts01119701200AmLONG_TERM_GARAGE_PARKING_weeks6Days6() throws Exception {
		
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
	
	@Description("parking time: 6 hour or less\n"
+"dayCosts: 6 * 78 + 0 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 6 hour or less"}),
			@GoalCoverage(goal = "requirements", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 0 * 13 + 0 * 2 + 2"})
		}
	)
	@Test
	public void test020_calculateParkingCosts022820162359PmLONG_TERM_GARAGE_PARKING_weeks6Days0Hours0Minutes1() throws Exception {
		
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
	
	@Description("parking time: 6 hour or less\n"
+"dayCosts: 0 * 78 + 6 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 0 * 78 + 6 * 13 + 5 * 2 + 2"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 6 hour or less"})
		}
	)
	@Test
	public void test021_calculateParkingCosts123119991200PmLONG_TERM_GARAGE_PARKING_weeks0Days6Hours5Minutes59() throws Exception {
		
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
	
	@Description("parking time: 6 hour or less\n"
+"dayCosts: 0 * 78 + 0 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 0 * 78 + 0 * 13 + 6 * 2 + 0"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 6 hour or less"})
		}
	)
	@Test
	public void test022_calculateParkingCosts022820162359PmLONG_TERM_GARAGE_PARKING_weeks0Days0Hours6Minutes0() throws Exception {
		
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
	
	@Description("parking time: more than 6 hours\n"
+"dayCosts: 0 * 78 + 1 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING more than 6 hours"}),
			@GoalCoverage(goal = "requirements", item="Req9", coverage={"LONG_TERM_GARAGE_PARKING costs 0 * 78 + 1 * 13 + 13"})
		}
	)
	@Test
	public void test023_calculateParkingCosts123119991200PmLONG_TERM_GARAGE_PARKING_weeks0Days1Hours6Minutes1() throws Exception {
		
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
	
	@Description("parking time: more than 6 hours\n"
+"dayCosts: 26 * 78 + 4 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req9", coverage={"LONG_TERM_GARAGE_PARKING costs 26 * 78 + 4 * 13 + 13"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING more than 6 hours"})
		}
	)
	@Test
	public void test024_calculateParkingCosts01119701200AmLONG_TERM_GARAGE_PARKING_weeks26Hours23Minutes59() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"0 hours 0 minutes"})
		}
	)
	@Test
	public void test025_calculateParkingCosts123119991200PmLONG_TERM_SURFACE_PARKING_weeks0Days6Hours0Minutes0() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"0 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 0 weeks 0 days"})
		}
	)
	@Test
	public void test026_calculateParkingCosts022820162359PmLONG_TERM_SURFACE_PARKING_weeks0Days0Hours0Minutes1() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 59 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"0 hours 59 minutes"})
		}
	)
	@Test
	public void test027_calculateParkingCosts01119701200AmLONG_TERM_SURFACE_PARKING_weeks0Days1Hours0Minutes59() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"4 hours 1 minutes"})
		}
	)
	@Test
	public void test028_calculateParkingCosts022820162359PmLONG_TERM_SURFACE_PARKING_weeks1Days1Hours4Minutes1() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 59 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"4 hours 59 minutes"})
		}
	)
	@Test
	public void test029_calculateParkingCosts123119991200PmLONG_TERM_SURFACE_PARKING_weeks1Days6Hours4Minutes59() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req12", coverage={"4 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 6 weeks 0 days"})
		}
	)
	@Test
	public void test030_calculateParkingCosts01119701200AmLONG_TERM_SURFACE_PARKING_weeks6Days0Hours4Minutes0() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 5 hours 59 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"5 hours 59 minutes"})
		}
	)
	@Test
	public void test031_calculateParkingCosts123119991200PmLONG_TERM_SURFACE_PARKING_weeks6Days6Hours5Minutes59() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 5 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"5 hours 0 minutes"})
		}
	)
	@Test
	public void test032_calculateParkingCosts022820162359PmLONG_TERM_SURFACE_PARKING_weeks26Hours5Minutes0() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 5 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"5 hours 1 minutes"})
		}
	)
	@Test
	public void test033_calculateParkingCosts01119701200AmLONG_TERM_SURFACE_PARKING_weeks6Days6Hours5Minutes1() throws Exception {
		
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
	
	@Description("")
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
	public void test034_calculateParkingCosts123119991200PmLONG_TERM_SURFACE_PARKING_weeks6Days0Hours23Minutes59() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 23 hours 0 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"23 hours 0 minutes"})
		}
	)
	@Test
	public void test035_calculateParkingCosts01119701200AmLONG_TERM_SURFACE_PARKING_weeks1Days6Hours23Minutes0() throws Exception {
		
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
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 23 hours 1 minutes"}),
			@GoalCoverage(goal = "requirements", item="Req13", coverage={"23 hours 1 minutes"})
		}
	)
	@Test
	public void test036_calculateParkingCosts022820162359PmLONG_TERM_SURFACE_PARKING_weeks1Days1Hours23Minutes1() throws Exception {
		
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
	
	@Description("parking time: 0 hours\n"
+"dayCosts: 9")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING 0 hours"}),
			@GoalCoverage(goal = "requirements", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 9"}),
			@GoalCoverage(goal = "requirements", item="Req18", coverage={"ECONOMY_LOT_PARKING costs 9"})
		}
	)
	@Test
	public void test037_calculateParkingCosts022820162359PmECONOMY_LOT_PARKING_weeks0Days1() throws Exception {
		
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
	
	@Description("parking time: 4 hour or less\n"
+"dayCosts: 54")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"}),
			@GoalCoverage(goal = "requirements", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 56"})
		}
	)
	@Test
	public void test038_calculateParkingCosts123119991200PmECONOMY_LOT_PARKING_weeks0Days6Hours0Minutes1() throws Exception {
		
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
	
	@Description("parking time: 4 hour or less\n"
+"dayCosts: 0")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 8"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test039_calculateParkingCosts01119701200AmECONOMY_LOT_PARKING_weeks0Days0Hours3Minutes59() throws Exception {
		
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
	
	@Description("parking time: 4 hour or less\n"
+"dayCosts: 1440")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 1448"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test040_calculateParkingCosts01119701200AmECONOMY_LOT_PARKING_weeks26Hours4Minutes0() throws Exception {
		
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
	
	@Description("parking time: more than 4 hours\n"
+"dayCosts: 0")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"}),
			@GoalCoverage(goal = "requirements", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 9"})
		}
	)
	@Test
	public void test041_calculateParkingCosts123119991200PmECONOMY_LOT_PARKING_weeks0Days0Hours4Minutes1() throws Exception {
		
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
	
	@Description("parking time: more than 4 hours\n"
+"dayCosts: 9")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirements", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 18"})
		},
		again = {
			@GoalCoverage(goal = "requirements", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"})
		}
	)
	@Test
	public void test042_calculateParkingCosts022820162359PmECONOMY_LOT_PARKING_weeks0Days1Hours23Minutes59() throws Exception {
		
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
	
	@Description("")
	@Test
	public void test043_selectIllegalLotNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ NULL_VALUE);
	}
	
	@Description("")
	@Test
	public void test044_selectIllegalLotBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ BAD_VALUE);
	}
	
	@Description("illegalElement: entry date")
	@Test
	public void test045_useIllegalDateInCalculationNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setIllegalEntryDate(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("illegalElement: entry time")
	@Test
	public void test046_useIllegalDateInCalculationBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setIllegalEntryTime(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("illegalElement: entry day part")
	@Test
	public void test047_useIllegalDateInCalculationNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalEntryDayPart(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("illegalElement: leaving date")
	@Test
	public void test048_useIllegalDateInCalculationBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDate(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("illegalElement: leaving time")
	@Test
	public void test049_useIllegalDateInCalculationBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setIllegalLeavingTime(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("illegalElement: leaving day part")
	@Test
	public void test050_useIllegalDateInCalculationNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDayPart(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("")
	@Test
	public void test051_useLeavingDateEarlierThanEntryDate() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Focus 1
		driver.useLeavingDateEarlierThanEntryDate();
	}
	
}
