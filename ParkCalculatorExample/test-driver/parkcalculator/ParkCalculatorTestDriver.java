package parkcalculator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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


	public void selectIllegalLot(BadValues value) {
		// TODO Auto-generated method stub
		
	}

	public void setIllegalEntryDate(BadValues value) {
		// TODO Auto-generated method stub
		
	}

	public void setIllegalEntryDayPart(BadValues value) {
		// TODO Auto-generated method stub
		
	}

	public void setIllegalEntryTime(BadValues value) {
		// TODO Auto-generated method stub
		
	}

	public void setIllegalLeavingDate(BadValues value) {
		// TODO Auto-generated method stub
		
	}

	public void setIllegalLeavingDayPart(BadValues value) {
		// TODO Auto-generated method stub
		
	}

	public void setIllegalLeavingTime(BadValues value) {
		// TODO Auto-generated method stub
		
	}

	public void useIllegalDateInCalculation() {
		// TODO Auto-generated method stub
		
	}

	public void verifyParkingCosts(int expectedCosts) {
		assertThat(calculatedCosts, equalTo(expectedCosts));
	}

	public void useLeavingDateEarlierThanEntryDate() {
		// TODO Auto-generated method stub
		
	}
}
