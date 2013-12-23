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
		calendar.add(Calendar.DAY_OF_MONTH, weeks * 7 + days);
		calendar.add(Calendar.HOUR, hours);
		calendar.add(Calendar.MINUTE, minutes);
		Date leavingTime = calendar.getTime();
		return leavingTime;
	}
	public void calculateParkingCosts() {
		calculatedCosts = parkingCostsCalculator.calculateCosts();
	}

	public void verifyParkingCostsForLONG_TERM_GARAGE_PARKING() {
		
	}

	public void verifyParkingCostsForECONOMY_LOT_PARKING() {
		// TODO Auto-generated method stub
		
	}

	public void verifyParkingCostsForLONG_TERM_SURFACE_PARKING() {
		// TODO Auto-generated method stub
		
	}

	public void verifyParkingCostsForSHORT_TERM_PARKING() {
		int expectedCosts = (weeks * 7 + days) * 24;
		int partialDayCosts;
		if(hours > 0){
			partialDayCosts = hours * 2;
			if(minutes > 30)
				partialDayCosts += 2;
			else if(minutes > 0)
				partialDayCosts += 1;
		}
		else if(minutes > 0)
			partialDayCosts = 2;
		else
			partialDayCosts = 0;
		expectedCosts += Math.min(24, partialDayCosts);
		assertThat(calculatedCosts,equalTo(expectedCosts));
	}

	public void verifyParkingCostsForVALET_PARKING() {
		int expectedCosts = (weeks * 7 + days) * 18;
		if(hours > 5)
			expectedCosts += 18;
		else if(hours != 0 || minutes != 0)
			expectedCosts += 12;
		assertThat(calculatedCosts,equalTo(expectedCosts));
		
	}

	public void illegalEntryDate(BadValues nullValue) {
		// TODO Auto-generated method stub
		
	}

	public void illegalEntryDayPart(BadValues nullValue) {
		// TODO Auto-generated method stub
		
	}

	public void illegalEntryTime(BadValues nullValue) {
		// TODO Auto-generated method stub
		
	}

	public void illegalLeavingDate(BadValues nullValue) {
		// TODO Auto-generated method stub
		
	}

	public void illegalLeavingDayPart(BadValues nullValue) {
		// TODO Auto-generated method stub
		
	}

	public void illegalLeavingTime(BadValues nullValue) {
		// TODO Auto-generated method stub
		
	}

	public void illegalLot(BadValues nullValue) {
		// TODO Auto-generated method stub
		
	}
}
