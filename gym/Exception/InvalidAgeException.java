package gym.Exception;

public class InvalidAgeException extends Exception {
    public InvalidAgeException() {
        super("Invalid age or date provided.");
    }

    // בנאי עם הודעה מותאמת
    public InvalidAgeException(String message) {
        super(message);
    }
}
