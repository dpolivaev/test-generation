package parkcalculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class ParkDateTimeTest {

	@Test
	public void parsesDate() throws Exception {
		Date date = new ParkDateTime().setDate("12/31/2001").setTime("1:00").setDayPart("am").date();
		Date expectedDate = new SimpleDateFormat("MM/dd/yy h:mm a").parse("12/31/2001 1:00 AM");
		assertThat(date, equalTo(expectedDate));
	}

	@Test
	public void calculatesDifference() throws Exception {
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/31/2001").setTime("1:00").setDayPart("am");
		ParkDateTime laterDate = new ParkDateTime().setDate("12/31/2001").setTime("1:01").setDayPart("am");
		DateTimeDifference difference = laterDate.substract(earlierDate);
		assertThat(difference.differenceInMilliSeconds(), equalTo(60L*1000));
	}
	
	
	@Test(expected=IllegalTimeException.class)
	public void illegalEntryDate(){
		ParkDateTime earlierDate = new ParkDateTime().setDate("Unknown").setTime("1:00").setDayPart("am");
		ParkDateTime leavingDate = new ParkDateTime().setDate("12/31/2001").setTime("1:01").setDayPart("am");
		leavingDate.substract(earlierDate);
	}
	
	@Test(expected=IllegalTimeException.class)
	public void nullEntryDate(){
		ParkDateTime earlierDate = new ParkDateTime().setDate(null).setTime("1:00").setDayPart("am");
		ParkDateTime leavingDate = new ParkDateTime().setDate("12/31/2001").setTime("1:01").setDayPart("am");
		leavingDate.substract(earlierDate);
	}

	@Test(expected=IllegalTimeException.class)
	public void illegalLeavingDate(){
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/31/2001").setTime("1:00").setDayPart("am");
		ParkDateTime leavingDate = new ParkDateTime().setDate("Unknown").setTime("1:01").setDayPart("am");
		leavingDate.substract(earlierDate);
	}
	
	@Test(expected=IllegalTimeException.class)
	public void nullLeavingDate(){
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/31/2001").setTime("1:00").setDayPart("am");
		ParkDateTime leavingDate = new ParkDateTime().setDate(null).setTime("1:01").setDayPart("am");
		leavingDate.substract(earlierDate);
	}

	@Test
	public void calculatesWeekDifference() throws Exception {
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/24/2001").setTime("1:00").setDayPart("am");
		ParkDateTime laterDate = new ParkDateTime().setDate("12/31/2001").setTime("2:01").setDayPart("am");
		DateTimeDifference difference = laterDate.substract(earlierDate);
		assertThat(difference.weeks(), equalTo(1));
	}

	@Test
	public void calculatesDayDifferenceForWeek() throws Exception {
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/24/2001").setTime("1:00").setDayPart("am");
		ParkDateTime laterDate = new ParkDateTime().setDate("12/31/2001").setTime("3:01").setDayPart("am");
		DateTimeDifference difference = laterDate.substract(earlierDate);
		assertThat(difference.days(), equalTo(0));
	}

	@Test
	public void calculatesDayDifferenceFor8Days() throws Exception {
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/24/2001").setTime("1:00").setDayPart("am");
		ParkDateTime laterDate = new ParkDateTime().setDate("01/01/2002").setTime("3:01").setDayPart("am");
		DateTimeDifference difference = laterDate.substract(earlierDate);
		assertThat(difference.days(), equalTo(1));
	}

	@Test
	public void calculatesHourDifferenceFor36Hours() throws Exception {
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/24/2001").setTime("1:00").setDayPart("am");
		ParkDateTime laterDate = new ParkDateTime().setDate("12/25/2001").setTime("1:00").setDayPart("pm");
		DateTimeDifference difference = laterDate.substract(earlierDate);
		assertThat(difference.hours(), equalTo(12));
	}

	@Test
	public void calculatesMinuteDifferenceFor90Minutes() throws Exception {
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/24/2001").setTime("1:00").setDayPart("am");
		ParkDateTime laterDate = new ParkDateTime().setDate("12/24/2001").setTime("2:30").setDayPart("am");
		DateTimeDifference difference = laterDate.substract(earlierDate);
		assertThat(difference.minutes(), equalTo(30));
	}

	@Test
	public void calculatesStartedHoursDifferenceFor90Minutes() throws Exception {
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/24/2001").setTime("1:00").setDayPart("am");
		ParkDateTime laterDate = new ParkDateTime().setDate("12/24/2001").setTime("2:30").setDayPart("am");
		DateTimeDifference difference = laterDate.substract(earlierDate);
		assertThat(difference.startedHours(), equalTo(2));
	}

	@Test
	public void calculatesStartedHoursDifferenceFor60Minutes() throws Exception {
		ParkDateTime earlierDate = new ParkDateTime().setDate("12/24/2001").setTime("1:00").setDayPart("am");
		ParkDateTime laterDate = new ParkDateTime().setDate("12/24/2001").setTime("2:00").setDayPart("am");
		DateTimeDifference difference = laterDate.substract(earlierDate);
		assertThat(difference.startedHours(), equalTo(1));
	}
}
