package randomnumber.generator;

import randomnumber.digest.RandomNumberDigest;

/**
 * A utility to generate a custom RandomNumberGenerator
 */
public class RandomNumberGeneratorBuilder {

    private int length;
    private RandomNumberDigest digest;
    private String staticIdentifier;
    private int identifierPosition;

    public static RandomNumberGeneratorBuilder newBuilder() {
        return new RandomNumberGeneratorBuilder();
    }

    /**
     * Prepare and build a {@code RandomNumberGenerator} from the provided details
     */
    public RandomNumberGenerator build() {
        VariableRandomNumberGenerator randomNumberGenerator = new VariableRandomNumberGenerator(length, digest);
        randomNumberGenerator.setStaticIdentifier(staticIdentifier, identifierPosition);
        reset();
        return randomNumberGenerator;
    }

    private void reset() {
        length = 0;
        digest = null;
        staticIdentifier = null;
        identifierPosition = -1;
    }

    /**
     * Length of the random string
     * 
     * @param length Length of the random string
     */
    public RandomNumberGeneratorBuilder length(int length) {
        this.length = length;
        return this;
    }

    /**
     * Set the digest
     * 
     * @param digest Type of digest
     */
    public RandomNumberGeneratorBuilder digest(RandomNumberDigest digest) {
        this.digest = digest;
        return this;
    }

    /**
     * 
     * @param identifier Static identifier to be placed in the random string
     * @param position   index, the given static identifier is to be placed - 0
     *                   being the first position
     * @return
     */
    public RandomNumberGeneratorBuilder staticIdentifier(String identifier, int position) {
        this.staticIdentifier = identifier;
        this.identifierPosition = position;
        return this;
    }

}