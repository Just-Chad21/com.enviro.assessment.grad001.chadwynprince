package myapp.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(long id) {
        super("Resource id " + id + " " + "not found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(Long id, String message) {
        super(message + id);
    }
}
