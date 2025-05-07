package gym.Exception;

public class ClientNotRegisteredException extends Exception {

    // Constructor with no arguments
    public ClientNotRegisteredException() {
        super("Client is not registered.");
    }

    // Constructor with a custom message
    public ClientNotRegisteredException(String message) {
        super(message);
    }

    // Constructor with a custom message and a cause
    public ClientNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with a cause
    public ClientNotRegisteredException(Throwable cause) {
        super(cause);
    }
}
