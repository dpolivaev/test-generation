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
import parkcalculator.testgeneration.LongTermGarageParkingCalculator;
@SuppressWarnings("unused")
public class ParkCalculatorTest {
	ParkCalculatorTestDriver driver = new ParkCalculatorTestDriver();
	
	@Description("category: success\n"
+"dayCosts: (7 * 0 + 0) * 18\n"
+"parking time: 0 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"VALET_PARKING costs 0 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req2", coverage={"VALET_PARKING costs (7 * 0 + 0) * 18"}),
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"VALET_PARKING costs (7 * 0 + 0) * 18"})
		}
	)
	@Test
	public void test001_calculateParkingCostsVALET_PARKING_01119701200AmWeeks0Days0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 0 + 0) * 18);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 0 + 1) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"}),
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"VALET_PARKING costs (7 * 0 + 1) * 18 + 12"})
		}
	)
	@Test
	public void test002_calculateParkingCostsVALET_PARKING_123119991200PmWeeks0Days1Hours0Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 0 + 1) * 18 + 12);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 0 + 6) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"VALET_PARKING costs (7 * 0 + 6) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"})
		}
	)
	@Test
	public void test003_calculateParkingCostsVALET_PARKING_022820162359PmWeeks0Days6Hours4Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 6, 
			/* hours*/ 4, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 0 + 6) * 18 + 12);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 1 + 1) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"VALET_PARKING costs (7 * 1 + 1) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"})
		}
	)
	@Test
	public void test004_calculateParkingCostsVALET_PARKING_123119991200PmWeeks1Days1Hours5Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 1, 
			/* hours*/ 5, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 1 + 1) * 18 + 12);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 1 + 6) * 18\n"
+"parking time: more than 5 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"VALET_PARKING costs more than 5 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req2", coverage={"VALET_PARKING costs (7 * 1 + 6) * 18 + 18"})
		}
	)
	@Test
	public void test005_calculateParkingCostsVALET_PARKING_022820162359PmWeeks1Days6Hours5Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 1 + 6) * 18 + 18);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 6 + 0) * 18\n"
+"parking time: more than 5 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req2", coverage={"VALET_PARKING costs (7 * 6 + 0) * 18 + 18"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"VALET_PARKING costs more than 5 hours"})
		}
	)
	@Test
	public void test006_calculateParkingCostsVALET_PARKING_01119701200AmWeeks6Days0Hours22Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 0, 
			/* hours*/ 22, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 6 + 0) * 18 + 18);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 6 + 6) * 18\n"
+"parking time: more than 5 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req2", coverage={"VALET_PARKING costs (7 * 6 + 6) * 18 + 18"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"VALET_PARKING costs more than 5 hours"})
		}
	)
	@Test
	public void test007_calculateParkingCostsVALET_PARKING_022820162359PmWeeks6Days6Hours23Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 6 + 6) * 18 + 18);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 26 + 4) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"VALET_PARKING costs (7 * 26 + 4) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"VALET_PARKING costs 5 hours or less"})
		}
	)
	@Test
	public void test008_calculateParkingCostsVALET_PARKING_01119701200AmWeeks26Hours0Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 0, 
			/* minutes*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ (7 * 26 + 4) * 18 + 12);
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test009_calculateParkingCostsSHORT_TERM_PARKING_123119991200PmWeeks26Hours0Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 0, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 4464);
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"first hour"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test010_calculateParkingCostsSHORT_TERM_PARKING_022820162359PmWeeks0Days1Hours0Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 26);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test011_calculateParkingCostsSHORT_TERM_PARKING_01119701200AmWeeks0Days6Hours0Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 146);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test012_calculateParkingCostsSHORT_TERM_PARKING_123119991200PmWeeks0Days0Hours0Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 2);
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test013_calculateParkingCostsSHORT_TERM_PARKING_123119991200PmWeeks1Days1Hours1Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 1, 
			/* hours*/ 1, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 196);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test014_calculateParkingCostsSHORT_TERM_PARKING_022820162359PmWeeks1Days6Hours1Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 6, 
			/* hours*/ 1, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 314);
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 1/2 hour"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test015_calculateParkingCostsSHORT_TERM_PARKING_01119701200AmWeeks6Days0Hours1Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 0, 
			/* hours*/ 1, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1011);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		}
	)
	@Test
	public void test016_calculateParkingCostsSHORT_TERM_PARKING_01119701200AmWeeks6Days6Hours1Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 6, 
			/* hours*/ 1, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1156);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 1/2 hour"})
		}
	)
	@Test
	public void test017_calculateParkingCostsSHORT_TERM_PARKING_123119991200PmWeeks1Days6Hours11Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 6, 
			/* hours*/ 11, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 335);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		}
	)
	@Test
	public void test018_calculateParkingCostsSHORT_TERM_PARKING_022820162359PmWeeks1Days1Hours11Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 1, 
			/* hours*/ 11, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 216);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test019_calculateParkingCostsSHORT_TERM_PARKING_01119701200AmWeeks6Days6Hours11Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 6, 
			/* hours*/ 11, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1174);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		}
	)
	@Test
	public void test020_calculateParkingCostsSHORT_TERM_PARKING_022820162359PmWeeks6Days0Hours11Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 0, 
			/* hours*/ 11, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1032);
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test021_calculateParkingCostsSHORT_TERM_PARKING_123119991200PmWeeks0Days6Hours12Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 6, 
			/* hours*/ 12, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 168);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test022_calculateParkingCostsSHORT_TERM_PARKING_022820162359PmWeeks0Days0Hours12Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 0, 
			/* hours*/ 12, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 24);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test023_calculateParkingCostsSHORT_TERM_PARKING_123119991200PmWeeks0Days1Hours12Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 1, 
			/* hours*/ 12, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 48);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test024_calculateParkingCostsSHORT_TERM_PARKING_01119701200AmWeeks26Hours12Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 12, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 4488);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test025_calculateParkingCostsSHORT_TERM_PARKING_123119991200PmWeeks0Days6Hours23Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 168);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test026_calculateParkingCostsSHORT_TERM_PARKING_022820162359PmWeeks0Days0Hours23Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 0, 
			/* hours*/ 23, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 24);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test027_calculateParkingCostsSHORT_TERM_PARKING_01119701200AmWeeks0Days1Hours23Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 1, 
			/* hours*/ 23, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 48);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test028_calculateParkingCostsSHORT_TERM_PARKING_022820162359PmWeeks1Days1Hours23Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 1, 
			/* hours*/ 23, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 216);
	}
	
	@Description("category: success\n"
+"modelCallResult: 468")
	@Test
	public void test029_calculateParkingCostsLONG_TERM_GARAGE_PARKING_123119991200PmWeeks6Days0Hours0Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(6, 0, 0 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 548")
	@Test
	public void test030_calculateParkingCostsLONG_TERM_GARAGE_PARKING_01119701200AmWeeks6Days6Hours0Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(6, 6, 0 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2082")
	@Test
	public void test031_calculateParkingCostsLONG_TERM_GARAGE_PARKING_123119991200PmWeeks26Hours0Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 0, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(26, 4, 0 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 548")
	@Test
	public void test032_calculateParkingCostsLONG_TERM_GARAGE_PARKING_022820162359PmWeeks6Days6Hours0Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(6, 6, 0 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 472")
	@Test
	public void test033_calculateParkingCostsLONG_TERM_GARAGE_PARKING_01119701200AmWeeks6Days0Hours1Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 0, 
			/* hours*/ 1, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(6, 0, 1 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 158")
	@Test
	public void test034_calculateParkingCostsLONG_TERM_GARAGE_PARKING_123119991200PmWeeks1Days6Hours1Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 6, 
			/* hours*/ 1, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(1, 6, 1 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 17")
	@Test
	public void test035_calculateParkingCostsLONG_TERM_GARAGE_PARKING_01119701200AmWeeks0Days1Hours1Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 1, 
			/* hours*/ 1, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(0, 1, 1 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 82")
	@Test
	public void test036_calculateParkingCostsLONG_TERM_GARAGE_PARKING_022820162359PmWeeks0Days6Hours1Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 6, 
			/* hours*/ 1, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(0, 6, 1 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 12")
	@Test
	public void test037_calculateParkingCostsLONG_TERM_GARAGE_PARKING_022820162359PmWeeks0Days0Hours5Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 0, 
			/* hours*/ 5, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(0, 0, 5 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2092")
	@Test
	public void test038_calculateParkingCostsLONG_TERM_GARAGE_PARKING_123119991200PmWeeks26Hours5Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 5, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(26, 4, 5 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 10")
	@Test
	public void test039_calculateParkingCostsLONG_TERM_GARAGE_PARKING_01119701200AmWeeks0Days0Hours5Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 0, 
			/* hours*/ 5, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(0, 0, 5 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 25")
	@Test
	public void test040_calculateParkingCostsLONG_TERM_GARAGE_PARKING_01119701200AmWeeks0Days1Hours5Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 1, 
			/* hours*/ 5, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(0, 1, 5 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 90")
	@Test
	public void test041_calculateParkingCostsLONG_TERM_GARAGE_PARKING_123119991200PmWeeks0Days6Hours6Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 6, 
			/* hours*/ 6, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(0, 6, 6 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 169")
	@Test
	public void test042_calculateParkingCostsLONG_TERM_GARAGE_PARKING_022820162359PmWeeks1Days6Hours6Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 6, 
			/* hours*/ 6, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(1, 6, 6 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 104")
	@Test
	public void test043_calculateParkingCostsLONG_TERM_GARAGE_PARKING_01119701200AmWeeks1Days1Hours6Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 1, 
			/* hours*/ 6, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(1, 1, 6 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 559")
	@Test
	public void test044_calculateParkingCostsLONG_TERM_GARAGE_PARKING_123119991200PmWeeks6Days6Hours6Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 6, 
			/* hours*/ 6, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(6, 6, 6 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 481")
	@Test
	public void test045_calculateParkingCostsLONG_TERM_GARAGE_PARKING_022820162359PmWeeks6Days0Hours23Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 0, 
			/* hours*/ 23, 
			/* minutes*/ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(6, 0, 23 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2093")
	@Test
	public void test046_calculateParkingCostsLONG_TERM_GARAGE_PARKING_022820162359PmWeeks26Hours23Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 23, 
			/* minutes*/ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(26, 4, 23 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2093")
	@Test
	public void test047_calculateParkingCostsLONG_TERM_GARAGE_PARKING_01119701200AmWeeks26Hours23Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 23, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(26, 4, 23 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 13")
	@Test
	public void test048_calculateParkingCostsLONG_TERM_GARAGE_PARKING_123119991200PmWeeks0Days0Hours23Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 0, 
			/* hours*/ 23, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ new LongTermGarageParkingCalculator().calculateCosts(0, 0, 23 ,59));
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"0 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 6 weeks 0 days"})
		}
	)
	@Test
	public void test049_calculateParkingCostsLONG_TERM_SURFACE_PARKING_123119991200PmWeeks6Days0Hours0Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"0 hours 1 minutes"})
		}
	)
	@Test
	public void test050_calculateParkingCostsLONG_TERM_SURFACE_PARKING_01119701200AmWeeks6Days6Hours0Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"0 hours 59 minutes"})
		}
	)
	@Test
	public void test051_calculateParkingCostsLONG_TERM_SURFACE_PARKING_022820162359PmWeeks1Days6Hours0Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"4 hours 1 minutes"})
		}
	)
	@Test
	public void test052_calculateParkingCostsLONG_TERM_SURFACE_PARKING_123119991200PmWeeks1Days1Hours4Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 1, 
			/* hours*/ 4, 
			/* minutes*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"4 hours 59 minutes"})
		}
	)
	@Test
	public void test053_calculateParkingCostsLONG_TERM_SURFACE_PARKING_022820162359PmWeeks26Hours4Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 4, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"4 hours 0 minutes"})
		}
	)
	@Test
	public void test054_calculateParkingCostsLONG_TERM_SURFACE_PARKING_01119701200AmWeeks1Days1Hours4Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 1, 
			/* hours*/ 4, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 5 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"5 hours 59 minutes"})
		}
	)
	@Test
	public void test055_calculateParkingCostsLONG_TERM_SURFACE_PARKING_022820162359PmWeeks1Days6Hours5Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 5 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"5 hours 0 minutes"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 6 weeks 0 days"})
		}
	)
	@Test
	public void test056_calculateParkingCostsLONG_TERM_SURFACE_PARKING_01119701200AmWeeks6Days0Hours5Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 0, 
			/* hours*/ 5, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 5 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"5 hours 1 minutes"})
		}
	)
	@Test
	public void test057_calculateParkingCostsLONG_TERM_SURFACE_PARKING_123119991200PmWeeks6Days6Hours5Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 23 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"23 hours 59 minutes"})
		}
	)
	@Test
	public void test058_calculateParkingCostsLONG_TERM_SURFACE_PARKING_123119991200PmWeeks0Days6Hours23Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 23 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"23 hours 0 minutes"})
		}
	)
	@Test
	public void test059_calculateParkingCostsLONG_TERM_SURFACE_PARKING_01119701200AmWeeks0Days1Hours23Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 1, 
			/* hours*/ 23, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 23 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"23 hours 1 minutes"})
		}
	)
	@Test
	public void test060_calculateParkingCostsLONG_TERM_SURFACE_PARKING_022820162359PmWeeks0Days6Hours23Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 0, 
			/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success\n"
+"parking time: 0 hours\n"
+"dayCosts: 63")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 0 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 63"}),
			@GoalCoverage(goal = "requirement coverage", item="Req18", coverage={"ECONOMY_LOT_PARKING costs 63"})
		}
	)
	@Test
	public void test061_calculateParkingCostsECONOMY_LOT_PARKING_022820162359PmWeeks1Days1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 63);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 108")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"}),
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 110"})
		}
	)
	@Test
	public void test062_calculateParkingCostsECONOMY_LOT_PARKING_123119991200PmWeeks1Days6Hours0Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 1, 
			/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 110);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 324")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 332"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test063_calculateParkingCostsECONOMY_LOT_PARKING_01119701200AmWeeks6Days0Hours3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 0, 
			/* hours*/ 3, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 332);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 378")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 386"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test064_calculateParkingCostsECONOMY_LOT_PARKING_123119991200PmWeeks6Days6Hours4Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 6, 
			/* days*/ 6, 
			/* hours*/ 4, 
			/* minutes*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 386);
	}
	
	@Description("category: success\n"
+"parking time: more than 4 hours\n"
+"dayCosts: 1440")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 1449"})
		}
	)
	@Test
	public void test065_calculateParkingCostsECONOMY_LOT_PARKING_01119701200AmWeeks26Hours4Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 4, 
			/* minutes*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1449);
	}
	
	@Description("category: success\n"
+"parking time: more than 4 hours\n"
+"dayCosts: 1440")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 1449"})
		}
	)
	@Test
	public void test066_calculateParkingCostsECONOMY_LOT_PARKING_022820162359PmWeeks26Hours23Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks*/ 26, 
			/* days*/ 4, 
			/* hours*/ 23, 
			/* minutes*/ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1449);
	}
	
	@Description("category: failure")
	@Test
	public void test067_selectIllegalLotNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ NULL_VALUE);
	}
	
	@Description("category: failure")
	@Test
	public void test068_selectIllegalLotBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ BAD_VALUE);
	}
	
	@Description("category: failure\n"
+"illegalElement: entry date")
	@Test
	public void test069_useIllegalDateInCalculationNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Precondition 2
		driver.setIllegalEntryDate(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure\n"
+"illegalElement: entry time")
	@Test
	public void test070_useIllegalDateInCalculationBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setIllegalEntryTime(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure\n"
+"illegalElement: entry day part")
	@Test
	public void test071_useIllegalDateInCalculationNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalEntryDayPart(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure\n"
+"illegalElement: leaving date")
	@Test
	public void test072_useIllegalDateInCalculationBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDate(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure\n"
+"illegalElement: leaving time")
	@Test
	public void test073_useIllegalDateInCalculationBAD_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setIllegalLeavingTime(/* wrongValue*/ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure\n"
+"illegalElement: leaving day part")
	@Test
	public void test074_useIllegalDateInCalculationNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDayPart(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test075_useLeavingDateEarlierThanEntryDate() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Focus 1
		driver.useLeavingDateEarlierThanEntryDate();
	}
	
}
