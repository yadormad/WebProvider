package server.admin;

public class StopThreadException extends Throwable {
    public StopThreadException(String message) {
        super(message);
    }

    public StopThreadException() {
        super();
    }
}
