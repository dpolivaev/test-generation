package parkcalculator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;

import static org.dpolivaev.tsgen.java.ExceptionMatcher.throwsException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class ParkCalculatorTestDriver {
	static private DateFormat dateTimeParser = new SimpleDateFormat("MM/dd/yy h:mm a");
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm");
	private static final SimpleDateFormat dayPartFormatter = new SimpleDateFormat("a");
	private ParkingCostsCalculator parkingCostsCalculator;
	private Date entryTime;
	private int weeks;
	private int days;
	private int hours;
	private int minutes;
	private int calculatedCosts;
	
	
	
	public void selectLot(String lot) {
		parkingCostsCalculator = new ParkingCostsCalculator();
		parkingCostsCalculator.selectLot(lot);
	}
	
	public void setEntryTime(String entryTimeString) throws ParseException {
		entryTime = dateTimeParser.parse(entryTimeString);
		parkingCostsCalculator
			.setEntryDate(formatDate(entryTime))
			.setEntryTime(formatTime(entryTime))
			.setEntryDayPart(formatDayPart(entryTime));
	}
	
	private String formatDate(Date time) {
		return dateFormatter.format(time);
	}
	
	private String formatTime(Date time) {
		return timeFormatter.format(time);
	}

	private String formatDayPart(Date time) {
		return dayPartFormatter.format(time);
	}

	public void setLeavingTime(int days, int hours, int minutes, int weeks) {
		this.weeks = weeks;
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		Date leavingTime = leavingTime();
		parkingCostsCalculator
		.setLeavingDate(formatDate(leavingTime))
		.setLeavingTime(formatTime(leavingTime))
		.setLeavingDayPart(formatDayPart(leavingTime));
	}

	private Date leavingTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(entryTime);
		calendar.add(Calendar.MINUTE, ((weeks * 7 + days) *24 + hours) * 60 + minutes);
		Date leavingTime = calendar.getTime();
		return leavingTime;
	}
	public void calculateParkingCosts() {
		calculatedCosts = parkingCostsCalculator.calculateCosts();
	}


	public void selectIllegalLot(final BadValues value) {
		parkingCostsCalculator = new ParkingCostsCalculator();
		Assert.assertThat(new Runnable() {
			@Override
			public void run() {
				switch (value) {
				case BAD_VALUE:
					parkingCostsCalculator.selectLot("unknown");
					break;
				default:
					parkingCostsCalculator.selectLot(null);
					break;
				}
			}
		}, throwsException(IllegalArgumentException.class));
		
	}
	
	class TimeManipulator{
		String entryDate;
		String entryTime;
		String entryDayPart;
		String leavingDate;
		String leavingTime;
		String leavingDayPart;
		TimeManipulator(String timeString) throws ParseException{
			Date time = dateTimeParser.parse(timeString);
			entryDate = formatDate(time);
			entryTime = formatTime(time);
			entryDayPart = formatDayPart(time);
			leavingDate = formatDate(time);
			leavingTime = formatTime(time);
			leavingDayPart = formatDayPart(time);
		}
		void apply(){
			parkingCostsCalculator
			.setEntryDate(entryDate)
			.setEntryTime(entryTime)
			.setEntryDayPart(entryDayPart);
			parkingCostsCalculator
			.setLeavingDate(leavingDate)
			.setLeavingTime(leavingTime)
			.setLeavingDayPart(leavingDayPart);
		}
	}

	public void setIllegalEntryDate(BadValues value) throws Exception {
		TimeManipulator timeManipulator = new TimeManipulator("12/31/1999 12:00 pm");
		switch(value){
		case BAD_VALUE: timeManipulator.entryDate="22/22/2222"; break;
		case NULL_VALUE: timeManipulator.entryDate=null; break;
		}
		timeManipulator.apply();
	}

	public void setIllegalEntryDayPart(BadValues value) throws Exception {
		TimeManipulator timeManipulator = new TimeManipulator("12/31/1999 12:00 pm");
		switch(value){
		case BAD_VALUE: timeManipulator.entryDayPart=""; break;
		case NULL_VALUE: timeManipulator.entryDayPart=null; break;
		}
		timeManipulator.apply();
	}

	public void setIllegalEntryTime(BadValues value) throws Exception {
		TimeManipulator timeManipulator = new TimeManipulator("12/31/1999 12:00 pm");
		switch(value){
		case BAD_VALUE: timeManipulator.entryTime=""; break;
		case NULL_VALUE: timeManipulator.entryTime=null; break;
		}
		timeManipulator.apply();
	}

	public void setIllegalLeavingDate(BadValues value) throws Exception {
		TimeManipulator timeManipulator = new TimeManipulator("12/31/1999 12:00 pm");
		switch(value){
		case BAD_VALUE: timeManipulator.leavingDate="unknown"; break;
		case NULL_VALUE: timeManipulator.leavingDate=null; break;
		}
		timeManipulator.apply();
	}

	public void setIllegalLeavingDayPart(BadValues value) throws Exception {
		TimeManipulator timeManipulator = new TimeManipulator("12/31/1999 12:00 pm");
		switch(value){
		case BAD_VALUE: timeManipulator.leavingDayPart=""; break;
		case NULL_VALUE: timeManipulator.leavingDayPart=null; break;
		}
		timeManipulator.apply();
	}

	public void setIllegalLeavingTime(BadValues value) throws Exception {
		TimeManipulator timeManipulator = new TimeManipulator("12/31/1999 12:00 pm");
		switch(value){
		case BAD_VALUE: timeManipulator.leavingTime=""; break;
		case NULL_VALUE: timeManipulator.leavingTime=null; break;
		}
		timeManipulator.apply();
	}

	public void useIllegalDateInCalculation() {
		Assert.assertThat(new Runnable() {
			@Override
			public void run() {
					parkingCostsCalculator.calculateCosts();
			}
		}, throwsException(IllegalTimeException.class));
	}

	public void useLeavingDateEarlierThanEntryDate() {
		Assert.assertThat(new Runnable() {
			@Override
			public void run() {
					parkingCostsCalculator.calculateCosts();
			}
		}, throwsException(IllegalTimeException.class));
	}

	public void verifyParkingCosts(int expectedCosts) {
		assertThat(calculatedCosts, equalTo(expectedCosts));
	}

	public void verifyParkingCostsForLotLONG_TERM_SURFACE_PARKING() {
		int expectedCosts = weeks * 60 + days * 10;
		int partialDayCosts = hours * 2;
		if(minutes > 0)
			partialDayCosts += 2;
		expectedCosts += Math.min(10, partialDayCosts);
		assertThat(calculatedCosts,equalTo(expectedCosts));
	}
	
}
