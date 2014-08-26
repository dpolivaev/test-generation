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
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"valet parking costs 0 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req2", coverage={"valet parking costs (7 * 0 + 0) * 18"}),
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"valet parking costs (7 * 0 + 0) * 18"})
		}
	)
	@Test
	public void test001_valetParking01119701200AmWeeks0Days0CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts((7 * 0 + 0) * 18);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 0 + 1) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"valet parking costs 5 hours or less"}),
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"valet parking costs (7 * 0 + 1) * 18 + 12"})
		}
	)
	@Test
	public void test002_valetParking123119991200PmWeeks0Days1Hours0Minutes1CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts((7 * 0 + 1) * 18 + 12);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 0 + 6) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"valet parking costs (7 * 0 + 6) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"valet parking costs 5 hours or less"})
		}
	)
	@Test
	public void test003_valetParking022820162359PmWeeks0Days6Hours4Minutes59CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts((7 * 0 + 6) * 18 + 12);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 1 + 1) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"valet parking costs (7 * 1 + 1) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"valet parking costs 5 hours or less"})
		}
	)
	@Test
	public void test004_valetParking123119991200PmWeeks1Days1Hours5Minutes0CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts((7 * 1 + 1) * 18 + 12);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 1 + 6) * 18\n"
+"parking time: more than 5 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"valet parking costs more than 5 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req2", coverage={"valet parking costs (7 * 1 + 6) * 18 + 18"})
		}
	)
	@Test
	public void test005_valetParking022820162359PmWeeks1Days6Hours5Minutes1CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts((7 * 1 + 6) * 18 + 18);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 6 + 0) * 18\n"
+"parking time: more than 5 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req2", coverage={"valet parking costs (7 * 6 + 0) * 18 + 18"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"valet parking costs more than 5 hours"})
		}
	)
	@Test
	public void test006_valetParking01119701200AmWeeks6Days0Hours22Minutes0CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts((7 * 6 + 0) * 18 + 18);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 6 + 6) * 18\n"
+"parking time: more than 5 hours")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req2", coverage={"valet parking costs (7 * 6 + 6) * 18 + 18"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"valet parking costs more than 5 hours"})
		}
	)
	@Test
	public void test007_valetParking01119701200AmWeeks6Days6Hours23Minutes59CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts((7 * 6 + 6) * 18 + 18);
	}
	
	@Description("category: success\n"
+"dayCosts: (7 * 26 + 4) * 18\n"
+"parking time: 5 hours or less")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req3", coverage={"valet parking costs (7 * 26 + 4) * 18 + 12"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req1", coverage={"valet parking costs 5 hours or less"})
		}
	)
	@Test
	public void test008_valetParking123119991200PmWeeks26Hours0Minutes1CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts((7 * 26 + 4) * 18 + 12);
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test009_shortTermParking022820162359PmWeeks0Days0Hours0Minutes0CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(0);
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
	public void test010_shortTermParking022820162359PmWeeks0Days1Hours0Minutes30CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(26);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test011_shortTermParking01119701200AmWeeks0Days6Hours0Minutes31CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(146);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test012_shortTermParking123119991200PmWeeks6Days0Hours0Minutes59CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(1010);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test013_shortTermParking022820162359PmWeeks6Days6Hours1Minutes0CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(1154);
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
	public void test014_shortTermParking123119991200PmWeeks26Hours1Minutes30CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(4467);
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
	public void test015_shortTermParking01119701200AmWeeks1Days1Hours1Minutes31CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(196);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		}
	)
	@Test
	public void test016_shortTermParking01119701200AmWeeks1Days6Hours1Minutes59CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(316);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test017_shortTermParking123119991200PmWeeks0Days0Hours11Minutes0CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(22);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 1/2 hour"})
		}
	)
	@Test
	public void test018_shortTermParking022820162359PmWeeks6Days0Hours11Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 11, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(1031);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		}
	)
	@Test
	public void test019_shortTermParking123119991200PmWeeks1Days1Hours11Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 11, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(216);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req5", coverage={"additional 2/2 hour"})
		}
	)
	@Test
	public void test020_shortTermParking022820162359PmWeeks26Hours11Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 11, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(4488);
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
	public void test021_shortTermParking01119701200AmWeeks26Hours12Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 12, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(4488);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test022_shortTermParking022820162359PmWeeks0Days0Hours12Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 12, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(24);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test023_shortTermParking123119991200PmWeeks1Days1Hours12Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 12, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(216);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test024_shortTermParking01119701200AmWeeks6Days0Hours12Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 12, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(1032);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test025_shortTermParking022820162359PmWeeks1Days1Hours23Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 23, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(216);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test026_shortTermParking123119991200PmWeeks0Days0Hours23Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
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
		driver.verifyParkingCosts(24);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test027_shortTermParking01119701200AmWeeks26Hours23Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 23, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(4488);
	}
	
	@Description("category: success")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req6", coverage={""})
		}
	)
	@Test
	public void test028_shortTermParking022820162359PmWeeks6Days0Hours23Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 23, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(1032);
	}
	
	@Description("category: success\n"
+"modelCallResult: 468")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test029_longTermGarageParking01119701200AmWeeks6Days0Hours0Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 0, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(6, 0, 0 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 548")
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
	public void test030_longTermGarageParking123119991200PmWeeks6Days6Hours0Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 0, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(6, 6, 0 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 93")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test031_longTermGarageParking01119701200AmWeeks1Days1Hours0Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 0, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(1, 1, 0 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 158")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test032_longTermGarageParking123119991200PmWeeks1Days6Hours0Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 0, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(1, 6, 0 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""})
		}
	)
	@Test
	public void test033_longTermGarageParking022820162359PmWeeks0Days0Hours1Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 1, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(0, 0, 1 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 17")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""})
		}
	)
	@Test
	public void test034_longTermGarageParking123119991200PmWeeks0Days1Hours1Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 1, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(0, 1, 1 ,30));
	}
	
	@Description("category: success\n"
+"modelCallResult: 82")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""})
		}
	)
	@Test
	public void test035_longTermGarageParking022820162359PmWeeks0Days6Hours1Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 1, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(0, 6, 1 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2084")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test036_longTermGarageParking01119701200AmWeeks26Hours1Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 1, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(26, 4, 1 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 10")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""})
		}
	)
	@Test
	public void test037_longTermGarageParking123119991200PmWeeks0Days0Hours5Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 5, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(0, 0, 5 ,0));
	}
	
	@Description("category: success\n"
+"modelCallResult: 480")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test038_longTermGarageParking022820162359PmWeeks6Days0Hours5Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 5, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(6, 0, 5 ,30));
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
	public void test039_longTermGarageParking01119701200AmWeeks26Hours5Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 5, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(26, 4, 5 ,31));
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
	public void test040_longTermGarageParking01119701200AmWeeks1Days1Hours5Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 5, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(1, 1, 5 ,59));
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
	public void test041_longTermGarageParking022820162359PmWeeks1Days1Hours6Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 6, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(1, 1, 6 ,0));
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
	public void test042_longTermGarageParking123119991200PmWeeks6Days0Hours6Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 6, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(6, 0, 6 ,30));
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
	public void test043_longTermGarageParking022820162359PmWeeks0Days0Hours6Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 6, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(0, 0, 6 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2093")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test044_longTermGarageParking01119701200AmWeeks26Hours6Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 6, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(26, 4, 6 ,59));
	}
	
	@Description("category: success\n"
+"modelCallResult: 2093")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test045_longTermGarageParking123119991200PmWeeks26Hours23Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 23, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(26, 4, 23 ,0));
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
	public void test046_longTermGarageParking123119991200PmWeeks1Days1Hours23Minutes30CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 23, 
			/* minutes */ 30);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(1, 1, 23 ,30));
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
	public void test047_longTermGarageParking01119701200AmWeeks0Days0Hours23Minutes31CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 23, 
			/* minutes */ 31);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(0, 0, 23 ,31));
	}
	
	@Description("category: success\n"
+"modelCallResult: 481")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={""}),
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={""})
		}
	)
	@Test
	public void test048_longTermGarageParking022820162359PmWeeks6Days0Hours23Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 23, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(new LongTermGarageParkingCalculator().calculateCosts(6, 0, 23 ,59));
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 0 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"0 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"long term surface parking 6 weeks 0 days"})
		}
	)
	@Test
	public void test049_longTermSurfaceParking123119991200PmWeeks6Days0Hours0Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 0, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 0 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"0 hours 1 minutes"})
		}
	)
	@Test
	public void test050_longTermSurfaceParking01119701200AmWeeks6Days6Hours0Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 0, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 0 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"0 hours 59 minutes"})
		}
	)
	@Test
	public void test051_longTermSurfaceParking022820162359PmWeeks1Days1Hours0Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 0, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 4 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"4 hours 0 minutes"})
		}
	)
	@Test
	public void test052_longTermSurfaceParking022820162359PmWeeks1Days6Hours4Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 4, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 4 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"4 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"long term surface parking 0 weeks 0 days"})
		}
	)
	@Test
	public void test053_longTermSurfaceParking123119991200PmWeeks0Days0Hours4Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 4, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 4 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"4 hours 59 minutes"})
		}
	)
	@Test
	public void test054_longTermSurfaceParking01119701200AmWeeks0Days1Hours4Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 1, 
			/* hours */ 4, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 5 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"5 hours 0 minutes"})
		}
	)
	@Test
	public void test055_longTermSurfaceParking123119991200PmWeeks0Days6Hours5Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 5, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 5 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"5 hours 1 minutes"})
		}
	)
	@Test
	public void test056_longTermSurfaceParking01119701200AmWeeks26Hours5Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 5, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 5 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"5 hours 59 minutes"})
		}
	)
	@Test
	public void test057_longTermSurfaceParking022820162359PmWeeks1Days1Hours5Minutes59CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 23 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"23 hours 0 minutes"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"long term surface parking 6 weeks 0 days"})
		}
	)
	@Test
	public void test058_longTermSurfaceParking01119701200AmWeeks6Days0Hours23Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 23, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 23 hours 1 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"23 hours 1 minutes"})
		}
	)
	@Test
	public void test059_longTermSurfaceParking123119991200PmWeeks26Hours23Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 26, 
			/* days */ 4, 
			/* hours */ 23, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"long term surface parking 23 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"23 hours 59 minutes"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"long term surface parking 0 weeks 0 days"})
		}
	)
	@Test
	public void test060_longTermSurfaceParking022820162359PmWeeks0Days0Hours23Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 23, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLongTermSurfaceParking();
	}
	
	@Description("category: success\n"
+"parking time: 0 hours\n"
+"dayCosts: 1440")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"economy lot parking 0 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"economy lot parking costs 1440"}),
			@GoalCoverage(goal = "requirement coverage", item="Req18", coverage={"economy lot parking costs 1440"})
		}
	)
	@Test
	public void test061_economyLotParking022820162359PmWeeks26CalculateParkingCosts() throws Exception {
		
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
		driver.verifyParkingCosts(1440);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 63")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"economy lot parking 4 hour or less"}),
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"economy lot parking costs 65"})
		}
	)
	@Test
	public void test062_economyLotParking01119701200AmWeeks1Days1Hours0Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 1, 
			/* hours */ 0, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(65);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 108")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"economy lot parking costs 116"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"economy lot parking 4 hour or less"})
		}
	)
	@Test
	public void test063_economyLotParking123119991200PmWeeks1Days6Hours3Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 1, 
			/* days */ 6, 
			/* hours */ 3, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(116);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 324")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"economy lot parking costs 332"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"economy lot parking 4 hour or less"})
		}
	)
	@Test
	public void test064_economyLotParking01119701200AmWeeks6Days0Hours4Minutes0CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 0, 
			/* hours */ 4, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(332);
	}
	
	@Description("category: success\n"
+"parking time: more than 4 hours\n"
+"dayCosts: 378")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"economy lot parking more than 4 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req17", coverage={"economy lot parking costs 387"})
		}
	)
	@Test
	public void test065_economyLotParking123119991200PmWeeks6Days6Hours4Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 6, 
			/* days */ 6, 
			/* hours */ 4, 
			/* minutes */ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(387);
	}
	
	@Description("category: success\n"
+"parking time: more than 4 hours\n"
+"dayCosts: 0")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req17", coverage={"economy lot parking costs 9"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"economy lot parking more than 4 hours"})
		}
	)
	@Test
	public void test066_economyLotParking022820162359PmWeeks0Days0Hours23Minutes59CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 0, 
			/* hours */ 23, 
			/* minutes */ 59);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(9);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 9")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"economy lot parking costs 11"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"economy lot parking 4 hour or less"})
		}
	)
	@Test
	public void test067_economyLotParking022820162359PmWeeks0Days1Hours0Minutes1CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
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
		driver.verifyParkingCosts(11);
	}
	
	@Description("category: success\n"
+"parking time: 0 hours\n"
+"dayCosts: 54")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"economy lot parking costs 54"}),
			@GoalCoverage(goal = "requirement coverage", item="Req18", coverage={"economy lot parking costs 54"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"economy lot parking 0 hours"})
		}
	)
	@Test
	public void test068_economyLotParking01119701200AmWeeks0Days6CalculateParkingCosts() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* parking lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entry time */ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setParkingDuration(/* weeks */ 0, 
			/* days */ 6, 
			/* hours */ 0, 
			/* minutes */ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(54);
	}
	
	@Description("category: failure")
	@Test
	public void test069_selectIllegalLotNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue */ NULL_VALUE);
	}
	
	@Description("category: failure")
	@Test
	public void test070_selectIllegalLotBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue */ BAD_VALUE);
	}
	
	@Description("category: failure")
	@Test
	public void test071_entryDateNULL_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setIllegalEntryDate(/* wrongValue */ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test072_entryTimeBAD_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setIllegalEntryTime(/* wrongValue */ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test073_entryDayPartBAD_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setIllegalEntryDayPart(/* wrongValue */ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test074_leavingDateNULL_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ VALET_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDate(/* wrongValue */ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test075_leavingTimeNULL_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalLeavingTime(/* wrongValue */ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test076_leavingDayPartBAD_VALUE_useIllegalDateInCalculation() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ VALET_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDayPart(/* wrongValue */ BAD_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test077_useLeavingDateEarlierThanEntryDate() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot */ LONG_TERM_SURFACE_PARKING);
	// Focus 1
		driver.useLeavingDateEarlierThanEntryDate();
	}
	
}
