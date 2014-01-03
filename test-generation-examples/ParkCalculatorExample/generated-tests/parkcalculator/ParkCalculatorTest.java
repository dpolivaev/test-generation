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
	public void test001__01119701200AmVALET_PARKING_weeks0Days0() throws Exception {
		
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
	public void test002__123119991200PmVALET_PARKING_weeks0Days1Hours0Minutes1() throws Exception {
		
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
	public void test003__022820162359PmVALET_PARKING_weeks0Days6Hours4Minutes59() throws Exception {
		
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
	public void test004__123119991200PmVALET_PARKING_weeks1Days1Hours5Minutes0() throws Exception {
		
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
	public void test005__022820162359PmVALET_PARKING_weeks1Days6Hours5Minutes1() throws Exception {
		
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
	public void test006__01119701200AmVALET_PARKING_weeks6Days0Hours22Minutes0() throws Exception {
		
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
	public void test007__022820162359PmVALET_PARKING_weeks6Days6Hours23Minutes59() throws Exception {
		
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
	public void test008__01119701200AmVALET_PARKING_weeks26Hours0Minutes1() throws Exception {
		
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
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req4", coverage={""})
		}
	)
	@Test
	public void test009__123119991200PmSHORT_TERM_PARKING_weeks26Hours0Minutes0() throws Exception {
		
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
	public void test010__022820162359PmSHORT_TERM_PARKING_weeks0Days1Hours0Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 30, 
			/* weeks*/ 0);
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
	public void test011__01119701200AmSHORT_TERM_PARKING_weeks0Days6Hours0Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 31, 
			/* weeks*/ 0);
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
	public void test012__123119991200PmSHORT_TERM_PARKING_weeks0Days0Hours0Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
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
	public void test013__123119991200PmSHORT_TERM_PARKING_weeks1Days1Hours1Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 1, 
			/* minutes*/ 59, 
			/* weeks*/ 1);
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
	public void test014__022820162359PmSHORT_TERM_PARKING_weeks1Days6Hours1Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 1, 
			/* minutes*/ 0, 
			/* weeks*/ 1);
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
	public void test015__01119701200AmSHORT_TERM_PARKING_weeks6Days0Hours1Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 1, 
			/* minutes*/ 30, 
			/* weeks*/ 6);
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
	public void test016__01119701200AmSHORT_TERM_PARKING_weeks6Days6Hours1Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 1, 
			/* minutes*/ 31, 
			/* weeks*/ 6);
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
	public void test017__123119991200PmSHORT_TERM_PARKING_weeks1Days6Hours11Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 11, 
			/* minutes*/ 30, 
			/* weeks*/ 1);
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
	public void test018__022820162359PmSHORT_TERM_PARKING_weeks1Days1Hours11Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 11, 
			/* minutes*/ 31, 
			/* weeks*/ 1);
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
	public void test019__01119701200AmSHORT_TERM_PARKING_weeks6Days6Hours11Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 11, 
			/* minutes*/ 0, 
			/* weeks*/ 6);
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
	public void test020__022820162359PmSHORT_TERM_PARKING_weeks6Days0Hours11Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 11, 
			/* minutes*/ 59, 
			/* weeks*/ 6);
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
	public void test021__123119991200PmSHORT_TERM_PARKING_weeks0Days6Hours12Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 12, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
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
	public void test022__022820162359PmSHORT_TERM_PARKING_weeks0Days0Hours12Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 12, 
			/* minutes*/ 30, 
			/* weeks*/ 0);
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
	public void test023__123119991200PmSHORT_TERM_PARKING_weeks0Days1Hours12Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 12, 
			/* minutes*/ 31, 
			/* weeks*/ 0);
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
	public void test024__01119701200AmSHORT_TERM_PARKING_weeks26Hours12Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 12, 
			/* minutes*/ 59, 
			/* weeks*/ 26);
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
	public void test025__123119991200PmSHORT_TERM_PARKING_weeks0Days6Hours23Minutes31() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 31, 
			/* weeks*/ 0);
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
	public void test026__022820162359PmSHORT_TERM_PARKING_weeks0Days0Hours23Minutes30() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 23, 
			/* minutes*/ 30, 
			/* weeks*/ 0);
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
	public void test027__01119701200AmSHORT_TERM_PARKING_weeks0Days1Hours23Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ SHORT_TERM_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 23, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
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
	public void test028__022820162359PmSHORT_TERM_PARKING_weeks1Days1Hours23Minutes59() throws Exception {
		
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
		driver.verifyParkingCosts(/* expectedCosts*/ 216);
	}
	
	@Description("category: success\n"
+"parking time: 0 hours\n"
+"dayCosts: 6 * 78 + 0 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req10", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 0 * 13"}),
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 0 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 0 * 13"})
		}
	)
	@Test
	public void test029__123119991200PmLONG_TERM_GARAGE_PARKING_weeks6Days0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 6 * 78 + 0 * 13);
	}
	
	@Description("category: success\n"
+"parking time: 6 hour or less\n"
+"dayCosts: 6 * 78 + 6 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 6 hour or less"}),
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 6 * 13 + 0 * 2 + 2"})
		}
	)
	@Test
	public void test030__01119701200AmLONG_TERM_GARAGE_PARKING_weeks6Days6Hours0Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 6 * 78 + 6 * 13 + 0 * 2 + 2);
	}
	
	@Description("category: success\n"
+"parking time: 6 hour or less\n"
+"dayCosts: 26 * 78 + 4 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 26 * 78 + 4 * 13 + 5 * 2 + 2"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 6 hour or less"})
		}
	)
	@Test
	public void test031__123119991200PmLONG_TERM_GARAGE_PARKING_weeks26Hours5Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
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
		driver.verifyParkingCosts(/* expectedCosts*/ 26 * 78 + 4 * 13 + 5 * 2 + 2);
	}
	
	@Description("category: success\n"
+"parking time: 6 hour or less\n"
+"dayCosts: 6 * 78 + 6 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req8", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 6 * 13 + 6 * 2 + 0"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING 6 hour or less"})
		}
	)
	@Test
	public void test032__022820162359PmLONG_TERM_GARAGE_PARKING_weeks6Days6Hours6Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 6, 
			/* minutes*/ 0, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 6 * 78 + 6 * 13 + 6 * 2 + 0);
	}
	
	@Description("category: success\n"
+"parking time: more than 6 hours\n"
+"dayCosts: 6 * 78 + 0 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING more than 6 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={"LONG_TERM_GARAGE_PARKING costs 6 * 78 + 0 * 13 + 13"})
		}
	)
	@Test
	public void test033__01119701200AmLONG_TERM_GARAGE_PARKING_weeks6Days0Hours6Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 6, 
			/* minutes*/ 1, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 6 * 78 + 0 * 13 + 13);
	}
	
	@Description("category: success\n"
+"parking time: more than 6 hours\n"
+"dayCosts: 1 * 78 + 6 * 13")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req9", coverage={"LONG_TERM_GARAGE_PARKING costs 1 * 78 + 6 * 13 + 13"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req7", coverage={"LONG_TERM_GARAGE_PARKING more than 6 hours"})
		}
	)
	@Test
	public void test034__123119991200PmLONG_TERM_GARAGE_PARKING_weeks1Days6Hours23Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1 * 78 + 6 * 13 + 13);
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"0 hours 0 minutes"})
		}
	)
	@Test
	public void test035__01119701200AmLONG_TERM_SURFACE_PARKING_weeks0Days1Hours0Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
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
	public void test036__022820162359PmLONG_TERM_SURFACE_PARKING_weeks0Days6Hours0Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 0 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req12", coverage={"0 hours 59 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 0 weeks 0 days"})
		}
	)
	@Test
	public void test037__022820162359PmLONG_TERM_SURFACE_PARKING_weeks0Days0Hours0Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
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
	public void test038__123119991200PmLONG_TERM_SURFACE_PARKING_weeks26Hours4Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 4, 
			/* minutes*/ 1, 
			/* weeks*/ 26);
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
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 0 weeks 0 days"})
		}
	)
	@Test
	public void test039__01119701200AmLONG_TERM_SURFACE_PARKING_weeks0Days0Hours4Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 4, 
			/* minutes*/ 59, 
			/* weeks*/ 0);
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
	public void test040__01119701200AmLONG_TERM_SURFACE_PARKING_weeks0Days1Hours4Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 4, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
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
	public void test041__123119991200PmLONG_TERM_SURFACE_PARKING_weeks0Days6Hours5Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
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
	public void test042__022820162359PmLONG_TERM_SURFACE_PARKING_weeks1Days6Hours5Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 5, 
			/* minutes*/ 0, 
			/* weeks*/ 1);
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
	public void test043__01119701200AmLONG_TERM_SURFACE_PARKING_weeks1Days1Hours5Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 5, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
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
	public void test044__123119991200PmLONG_TERM_SURFACE_PARKING_weeks6Days6Hours23Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING();
	}
	
	@Description("category: success")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req11", coverage={"LONG_TERM_SURFACE_PARKING 23 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req13", coverage={"23 hours 0 minutes"}),
			@GoalCoverage(goal = "requirement coverage", item="Req14", coverage={"LONG_TERM_SURFACE_PARKING 6 weeks 0 days"})
		}
	)
	@Test
	public void test045__022820162359PmLONG_TERM_SURFACE_PARKING_weeks6Days0Hours23Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 23, 
			/* minutes*/ 0, 
			/* weeks*/ 6);
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
	public void test046__022820162359PmLONG_TERM_SURFACE_PARKING_weeks26Hours23Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_SURFACE_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 23, 
			/* minutes*/ 1, 
			/* weeks*/ 26);
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
	public void test047__01119701200AmECONOMY_LOT_PARKING_weeks26() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 4, 
			/* hours*/ 0, 
			/* minutes*/ 0, 
			/* weeks*/ 26);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 1440);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 0")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"}),
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 2"})
		}
	)
	@Test
	public void test048__123119991200PmECONOMY_LOT_PARKING_weeks0Days0Hours0Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 2);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 54")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 62"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test049__123119991200PmECONOMY_LOT_PARKING_weeks0Days6Hours3Minutes59() throws Exception {
		
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
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 9")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 17"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test050__01119701200AmECONOMY_LOT_PARKING_weeks0Days1Hours4Minutes0() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 4, 
			/* minutes*/ 0, 
			/* weeks*/ 0);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 17);
	}
	
	@Description("category: success\n"
+"parking time: more than 4 hours\n"
+"dayCosts: 324")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"}),
			@GoalCoverage(goal = "requirement coverage", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 333"})
		}
	)
	@Test
	public void test051__022820162359PmECONOMY_LOT_PARKING_weeks6Days0Hours4Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 0, 
			/* hours*/ 4, 
			/* minutes*/ 1, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 333);
	}
	
	@Description("category: success\n"
+"parking time: more than 4 hours\n"
+"dayCosts: 378")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req17", coverage={"ECONOMY_LOT_PARKING costs 387"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING more than 4 hours"})
		}
	)
	@Test
	public void test052__123119991200PmECONOMY_LOT_PARKING_weeks6Days6Hours23Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "12/31/1999 12:00 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 23, 
			/* minutes*/ 59, 
			/* weeks*/ 6);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 387);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 108")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 110"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test053__022820162359PmECONOMY_LOT_PARKING_weeks1Days6Hours0Minutes1() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "02/28/2016 23:59 pm");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 6, 
			/* hours*/ 0, 
			/* minutes*/ 1, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 110);
	}
	
	@Description("category: success\n"
+"parking time: 4 hour or less\n"
+"dayCosts: 63")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="Req16", coverage={"ECONOMY_LOT_PARKING costs 71"})
		},
		again = {
			@GoalCoverage(goal = "requirement coverage", item="Req15", coverage={"ECONOMY_LOT_PARKING 4 hour or less"})
		}
	)
	@Test
	public void test054__01119701200AmECONOMY_LOT_PARKING_weeks1Days1Hours3Minutes59() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ ECONOMY_LOT_PARKING);
	// Precondition 2
		driver.setEntryTime(/* entryTime*/ "01/1/1970 12:00 am");
	// Precondition 3
		driver.setLeavingTime(/* days*/ 1, 
			/* hours*/ 3, 
			/* minutes*/ 59, 
			/* weeks*/ 1);
	// Focus 1
		driver.calculateParkingCosts();
	// Verification 1
		driver.verifyParkingCosts(/* expectedCosts*/ 71);
	}
	
	@Description("category: failure")
	@Test
	public void test055_selectIllegalLotNULL_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ NULL_VALUE);
	}
	
	@Description("category: failure")
	@Test
	public void test056_selectIllegalLotBAD_VALUE() throws Exception {
		
	// Focus 1
		driver.selectIllegalLot(/* wrongValue*/ BAD_VALUE);
	}
	
	@Description("category: failure\n"
+"illegalElement: entry date")
	@Test
	public void test057_useIllegalDateInCalculationNULL_VALUE() throws Exception {
		
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
	public void test058_useIllegalDateInCalculationBAD_VALUE() throws Exception {
		
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
	public void test059_useIllegalDateInCalculationNULL_VALUE() throws Exception {
		
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
	public void test060_useIllegalDateInCalculationBAD_VALUE() throws Exception {
		
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
	public void test061_useIllegalDateInCalculationBAD_VALUE() throws Exception {
		
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
	public void test062_useIllegalDateInCalculationNULL_VALUE() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ LONG_TERM_GARAGE_PARKING);
	// Precondition 2
		driver.setIllegalLeavingDayPart(/* wrongValue*/ NULL_VALUE);
	// Focus 1
		driver.useIllegalDateInCalculation();
	}
	
	@Description("category: failure")
	@Test
	public void test063_useLeavingDateEarlierThanEntryDate() throws Exception {
		
	// Precondition 1
		driver.selectLot(/* lot*/ VALET_PARKING);
	// Focus 1
		driver.useLeavingDateEarlierThanEntryDate();
	}
	
}
