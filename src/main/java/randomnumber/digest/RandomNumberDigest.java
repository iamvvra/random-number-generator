package randomnumber.digest;

public interface RandomNumberDigest {
    public String digest(String input);

    public boolean verify(String input);

    public static RandomNumberDigest defaultDigest() {
        return new Mod97Digest();
    }
}