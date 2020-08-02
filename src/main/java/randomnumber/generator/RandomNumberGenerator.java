package randomnumber.generator;

import randomnumber.digest.RandomNumberDigest;

/**
 * Random number generator
 */
public interface RandomNumberGenerator {

    public String generate();

    public boolean verify(String randomNumber);

    public int getLength();

    public RandomNumberDigest getDigest();

    /**
     * Deprecated. Use createVariableRandomNumberGenerator with 16 digit Creates a
     * new instance of fixed length 16 digit random number generator.
     */
    @Deprecated
    public static RandomNumberGenerator verifiableTransactionReferenceNumberGenerator() {
        return new VerifiableTransactionReferenceNumberGenerator(RandomNumberDigest.defaultDigest());
    }

    /**
     * Create a new random number generator that will generate random string of the
     * given length
     * 
     * @param length Length of the random string
     */
    @Deprecated
    public static RandomNumberGenerator createVariableRandomNumberGenerator(int length, RandomNumberDigest digest) {
        return new VariableRandomNumberGenerator(length, digest);
    }

}