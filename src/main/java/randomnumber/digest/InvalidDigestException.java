package randomnumber.digest;

public class InvalidDigestException extends RuntimeException {
    private static final long serialVersionUID = -26;

    public InvalidDigestException(String message) {
        super(message);
    }
}