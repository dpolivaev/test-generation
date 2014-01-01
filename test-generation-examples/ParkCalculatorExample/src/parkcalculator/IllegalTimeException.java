package parkcalculator;

public class IllegalTimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IllegalTimeException(String message) {
		super(message);
	}

	public IllegalTimeException(Exception e) {
		super(e);
	}

}
