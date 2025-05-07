package gym.Exception;

public class DuplicateClientException extends Exception {
//    public DuplicateClientException() {
//        super("Duplicate client exception.");
//    }
//
//    // בנאי עם הודעה מותאמת
//    public DuplicateClientException(String message) {
//        super(message);
//    }
public String getMessage() {
    return super.getMessage();
}
    public DuplicateClientException(String message) {
        super(message);
    }
}
