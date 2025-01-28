package myapp.exceptions;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException() {
        super("Input is empty. Please provide valid data.");
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
