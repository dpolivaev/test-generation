package parkcalculator;

public class DateTimeDifference {

	final private long differenceInMilliSeconds;

	public DateTimeDifference(long differenceInMilliSeconds) {
		this.differenceInMilliSeconds = differenceInMilliSeconds;
	}

	public long differenceInMilliSeconds() {
		return differenceInMilliSeconds;
	}

	public int weeks() {
		return (int) (differenceInMilliSeconds / (1000 * 60 * 60 * 24 * 7));
	}

	public int days() {
		return (int) (differenceInMilliSeconds / (1000 * 60 * 60 * 24) % 7);
	}

	public int startedHours() {
		return hours() + (minutes() > 0 ? 1 : 0);
	}

	public int hours() {
		return (int) (differenceInMilliSeconds / (1000 * 60 * 60) % 24);
	}

	public int minutes() {
		return (int) (differenceInMilliSeconds / (1000 * 60) % 60);
	}

}
