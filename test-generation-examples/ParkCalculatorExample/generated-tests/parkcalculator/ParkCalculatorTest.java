package parkcalculator;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import org.dpolivaev.testgeneration.engine.java.Description;
import org.dpolivaev.testgeneration.engine.java.Coverage;
import org.dpolivaev.testgeneration.engine.java.GoalCoverage;

import static parkcalculator.Lots.*;
import static parkcalculator.BadValues.*;
import parkcalculator.testgeneration.LongTermGarageParkingCalculator;
import parkcalculator.ParkCalculatorTestDriver;
import static parkcalculator.ParkCalculatorTestDriver.*;

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
	public void test001_VALET_PARKING_01119701200AmWeeks0Days0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 0, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ (7 * 0 + 0) * 18);
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
	public void test002_VALET_PARKING_123119991200PmWeeks0Days1Hours0Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 0, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ (7 * 0 + 1) * 18 + 12);
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
	public void test003_VALET_PARKING_022820162359PmWeeks0Days6Hours4Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 4, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ (7 * 0 + 6) * 18 + 12);
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
	public void test004_VALET_PARKING_123119991200PmWeeks1Days1Hours5Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 5, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ (7 * 1 + 1) * 18 + 12);
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
	public void test005_VALET_PARKING_022820162359PmWeeks1Days6Hours5Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 5, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ (7 * 1 + 6) * 18 + 18);
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
	public void test006_VALET_PARKING_01119701200AmWeeks6Days0Hours22Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 22, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ (7 * 6 + 0) * 18 + 18);
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
	public void test007_VALET_PARKING_01119701200AmWeeks6Days6Hours23Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 23, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ (7 * 6 + 6) * 18 + 18);
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
	public void test008_VALET_PARKING_123119991200PmWeeks26Hours0Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ VALET_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 0, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ (7 * 26 + 4) * 18 + 12);
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test009_SHORT_TERM_PARKING_022820162359PmWeeks0Days0Hours0Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 0, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 0);
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
	public void test010_SHORT_TERM_PARKING_022820162359PmWeeks0Days1Hours0Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 0, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 26);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test011_SHORT_TERM_PARKING_01119701200AmWeeks0Days6Hours0Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 0, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 146);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test012_SHORT_TERM_PARKING_123119991200PmWeeks6Days0Hours0Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 0, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 1010);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test013_SHORT_TERM_PARKING_022820162359PmWeeks6Days6Hours1Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 1, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 1154);
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
	public void test014_SHORT_TERM_PARKING_123119991200PmWeeks26Hours1Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 1, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 4467);
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
	public void test015_SHORT_TERM_PARKING_01119701200AmWeeks1Days1Hours1Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 1, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 196);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		}
	)
	@Test
	public void test016_SHORT_TERM_PARKING_01119701200AmWeeks1Days6Hours1Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 1, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 316);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test017_SHORT_TERM_PARKING_123119991200PmWeeks0Days0Hours11Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 11, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 22);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 1/2 hour"})
		}
	)
	@Test
	public void test018_SHORT_TERM_PARKING_022820162359PmWeeks0Days1Hours11Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 11, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 47);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		}
	)
	@Test
	public void test019_SHORT_TERM_PARKING_123119991200PmWeeks0Days6Hours11Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 11, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 168);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		}
	)
	@Test
	public void test020_SHORT_TERM_PARKING_022820162359PmWeeks6Days0Hours11Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 11, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 1032);
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
	public void test021_SHORT_TERM_PARKING_01119701200AmWeeks6Days6Hours12Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 12, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 1176);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test022_SHORT_TERM_PARKING_123119991200PmWeeks1Days1Hours12Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 12, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 216);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test023_SHORT_TERM_PARKING_022820162359PmWeeks1Days6Hours12Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 12, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 336);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test024_SHORT_TERM_PARKING_01119701200AmWeeks26Hours12Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 12, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 4488);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test025_SHORT_TERM_PARKING_01119701200AmWeeks0Days0Hours23Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 23, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 24);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test026_SHORT_TERM_PARKING_123119991200PmWeeks0Days1Hours23Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 23, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 48);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test027_SHORT_TERM_PARKING_022820162359PmWeeks0Days6Hours23Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 23, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 168);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test028_SHORT_TERM_PARKING_123119991200PmWeeks26Hours23Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 23, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 4488);
	}
	
	@Description("category: success\n"
+"modelCallResult: 91")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test029_LONG_TERM_GARAGE_PARKING_01119701200AmWeeks1Days1Hours0Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 0, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(1, 1, 0 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 158")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test030_LONG_TERM_GARAGE_PARKING_022820162359PmWeeks1Days6Hours0Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 0, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(1, 6, 0 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 470")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test031_LONG_TERM_GARAGE_PARKING_123119991200PmWeeks6Days0Hours0Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 0, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(6, 0, 0 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 548")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test032_LONG_TERM_GARAGE_PARKING_01119701200AmWeeks6Days6Hours0Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 0, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(6, 6, 0 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2082")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test033_LONG_TERM_GARAGE_PARKING_022820162359PmWeeks26Hours1Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 1, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(26, 4, 1 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 472")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test034_LONG_TERM_GARAGE_PARKING_123119991200PmWeeks6Days0Hours1Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 1, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(6, 0, 1 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 550")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test035_LONG_TERM_GARAGE_PARKING_01119701200AmWeeks6Days6Hours1Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 1, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(6, 6, 1 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 4")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""})
		}
	)
	@Test
	public void test036_LONG_TERM_GARAGE_PARKING_022820162359PmWeeks0Days0Hours1Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 1, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(0, 0, 1 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 23")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""})
		}
	)
	@Test
	public void test037_LONG_TERM_GARAGE_PARKING_022820162359PmWeeks0Days1Hours5Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 5, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(0, 1, 5 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 90")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""})
		}
	)
	@Test
	public void test038_LONG_TERM_GARAGE_PARKING_01119701200AmWeeks0Days6Hours5Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 5, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(0, 6, 5 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 103")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test039_LONG_TERM_GARAGE_PARKING_123119991200PmWeeks1Days1Hours5Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 5, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(1, 1, 5 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 168")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test040_LONG_TERM_GARAGE_PARKING_123119991200PmWeeks1Days6Hours5Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 5, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(1, 6, 5 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2092")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test041_LONG_TERM_GARAGE_PARKING_01119701200AmWeeks26Hours6Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 6, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(26, 4, 6 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 481")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test042_LONG_TERM_GARAGE_PARKING_022820162359PmWeeks6Days0Hours6Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 6, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(6, 0, 6 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 559")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test043_LONG_TERM_GARAGE_PARKING_01119701200AmWeeks6Days6Hours6Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 6, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(6, 6, 6 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 104")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test044_LONG_TERM_GARAGE_PARKING_022820162359PmWeeks1Days1Hours6Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 6, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(1, 1, 6 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 169")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test045_LONG_TERM_GARAGE_PARKING_123119991200PmWeeks1Days6Hours23Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 23, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(1, 6, 23 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 13")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""})
		}
	)
	@Test
	public void test046_LONG_TERM_GARAGE_PARKING_123119991200PmWeeks0Days0Hours23Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 23, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(0, 0, 23 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 26")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""})
		}
	)
	@Test
	public void test047_LONG_TERM_GARAGE_PARKING_022820162359PmWeeks0Days1Hours23Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 23, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(0, 1, 23 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 91")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""})
		}
	)
	@Test
	public void test048_LONG_TERM_GARAGE_PARKING_01119701200AmWeeks0Days6Hours23Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 23, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ new LongTermGarageParkingCalculator().calculateCosts(0, 6, 23 ,59));
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"0 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 0 weeks 0 days"})
		}
	)
	@Test
	public void test049_LONG_TERM_SURFACE_PARKING_01119701200AmWeeks0Days0Hours0Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 0, 
			/* minutes */ 0);
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
	public void test050_LONG_TERM_SURFACE_PARKING_022820162359PmWeeks0Days1Hours0Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 0, 
			/* minutes */ 1);
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
	public void test051_LONG_TERM_SURFACE_PARKING_123119991200PmWeeks0Days6Hours0Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 0, 
			/* minutes */ 59);
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
	public void test052_LONG_TERM_SURFACE_PARKING_022820162359PmWeeks26Hours4Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 4, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 4 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"4 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 6 weeks 0 days"})
		}
	)
	@Test
	public void test053_LONG_TERM_SURFACE_PARKING_01119701200AmWeeks6Days0Hours4Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 4, 
			/* minutes */ 1);
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
	public void test054_LONG_TERM_SURFACE_PARKING_123119991200PmWeeks6Days6Hours4Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 4, 
			/* minutes */ 59);
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
		}
	)
	@Test
	public void test055_LONG_TERM_SURFACE_PARKING_123119991200PmWeeks1Days1Hours5Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 5, 
			/* minutes */ 0);
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
	public void test056_LONG_TERM_SURFACE_PARKING_01119701200AmWeeks1Days6Hours5Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 5, 
			/* minutes */ 1);
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
	public void test057_LONG_TERM_SURFACE_PARKING_022820162359PmWeeks1Days1Hours5Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 5, 
			/* minutes */ 59);
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
	public void test058_LONG_TERM_SURFACE_PARKING_123119991200PmWeeks1Days6Hours23Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 23, 
			/* minutes */ 0);
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
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 0 weeks 0 days"})
		}
	)
	@Test
	public void test059_LONG_TERM_SURFACE_PARKING_01119701200AmWeeks0Days0Hours23Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 23, 
			/* minutes */ 1);
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
	public void test060_LONG_TERM_SURFACE_PARKING_022820162359PmWeeks0Days1Hours23Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 23, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success\n"
+"parking time: 0 hours\n"
+"dayCosts: 1440")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 0 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 1440"}),
			@GoalCoverage(goal = "requirement coverage", item="Req18", coverage={"ECONOMY_LOT_PARKING costs 1440"})
		}
	)
	@Test
	public void test061_ECONOMY_LOT_PARKING_022820162359PmWeeks26CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 0, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 1440);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 324")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"}),
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 326"})
		}
	)
	@Test
	public void test062_ECONOMY_LOT_PARKING_123119991200PmWeeks6Days0Hours0Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 0, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 326);
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
	public void test063_ECONOMY_LOT_PARKING_01119701200AmWeeks6Days6Hours3Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 3, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 386);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 0")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 8"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test064_ECONOMY_LOT_PARKING_123119991200PmWeeks0Days0Hours4Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 4, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 8);
	}
	
	@Description("category: success\n"
+"parking time: more than 4 hours\n"
+"dayCosts: 9")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 18"})
		}
	)
	@Test
	public void test065_ECONOMY_LOT_PARKING_01119701200AmWeeks0Days1Hours4Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 4, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 18);
	}
	
	@Description("category: success\n"
+"parking time: more than 4 hours\n"
+"dayCosts: 54")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 63"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"})
		}
	)
	@Test
	public void test066_ECONOMY_LOT_PARKING_022820162359PmWeeks0Days6Hours23Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 23, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts */ 63);
	}
	
	@Description("category: failure")
	@Test
	public void test067_selectIllegalLotNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue */ NULL_VALUE);
	}
	
	@Description("category: failure")
	@Test
	public void test068_selectIllegalLotBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue */ BAD_VALUE);
	}
	
	@Description("category: failure")
	@Test
	public void test069_entryDateNULL_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalEntryDate(/* wrongValue */ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test070_entryTimeBAD_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setIllegalEntryTime(/* wrongValue */ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test071_entryDayPartNULL_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setIllegalEntryDayPart(/* wrongValue */ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test072_leavingDateBAD_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDate(/* wrongValue */ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test073_leavingTimeBAD_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ VALET_PARKING);
	// Precondition 2
		driver.setIllegalLeavingTime(/* wrongValue */ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test074_leavingDayPartNULL_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ VALET_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDayPart(/* wrongValue */ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test075_useLeavingDateEarlierThanEntryDate() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ VALET_PARKING);
	// Focus 1
		driver.useLeavingDateEarlierThanEntryDate();
	}
	
}
