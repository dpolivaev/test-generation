package parkcalculator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkDateTime {
	static private DateFormat dateTimeParser = new SimpleDateFormat("MM/dd/yy h:mm a");

	private String date;
	private String time;
	private String dayPart;

	public ParkDateTime() {
	}

	public ParkDateTime setDate(String date) {
		this.date = date;
		return this;
	}

	public ParkDateTime setTime(String time) {
		this.time = time;
		return this;
	}

	public ParkDateTime setDayPart(String dayPart) {
		if(dayPart == null)
			this.dayPart = null;
		else
			this.dayPart = dayPart.toUpperCase();
		return this;
	}

	public Date date(){
		String dateTimeAsString = new StringBuilder().append(date).append(' ').append(time).append(' ').append(dayPart).toString();
		try {
			return dateTimeParser.parse(dateTimeAsString);
		} catch (Exception e) {
			throw new IllegalTimeException(e);
		}
	}

	public DateTimeDifference substract(ParkDateTime earlierDate) {
		return new DateTimeDifference(date().getTime() - earlierDate.date().getTime());
	}
}