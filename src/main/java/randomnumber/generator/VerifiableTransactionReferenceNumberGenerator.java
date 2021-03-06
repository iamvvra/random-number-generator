package randomnumber.generator;

import java.math.BigInteger;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import randomnumber.digest.RandomNumberDigest;

@Deprecated
final class VerifiableTransactionReferenceNumberGenerator extends VariableRandomNumberGenerator {

    private static final int LENGTH = 16;
    private RandomNumberDigest transactionNumberDigest;

    public VerifiableTransactionReferenceNumberGenerator(RandomNumberDigest transactionNumberDigest) {
        super(LENGTH);
        this.transactionNumberDigest = transactionNumberDigest;
    }

    @Override
    public String generate() {
        return createRandom().andThen(digest()).apply(firstHalf(), secondHalf());
    }

    @Override
    public boolean verify(String txnNumber) {
        return transactionNumberDigest.verify(txnNumber);
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    private BiFunction<Supplier<String>, Supplier<String>, String> createRandom() {
        return (c1, c2) -> combine().apply(c1.get(), c2.get());
    }

    private Supplier<String> firstHalf() {
        return () -> {
            UUID uuid = UUID.randomUUID();
            BigInteger mostSignificantBits = BigInteger.valueOf(uuid.getMostSignificantBits());
            BigInteger leastSignificantBits = BigInteger.valueOf(uuid.getLeastSignificantBits());
            String randomNumber = null;
            if (mostSignificantBits.compareTo(leastSignificantBits) < 0) {
                randomNumber = String
                        .valueOf(leastSignificantBits.multiply(leastSignificantBits).add(mostSignificantBits))
                        .substring(0, 8);
            } else {
                randomNumber = String.valueOf(mostSignificantBits.multiply(mostSignificantBits).add(mostSignificantBits)
                        .add(leastSignificantBits)).substring(0, 8);
            }
            return randomNumber;
        };
    }

    private Supplier<String> secondHalf() {
        return () -> {
            String nanoTime = String.valueOf(System.nanoTime());
            String secondHalfRandom = new StringBuilder(
                    String.valueOf(nanoTime).substring(nanoTime.length() - 8, nanoTime.length())).toString();
            return secondHalfRandom;
        };
    }

    private BiFunction<String, String, String> combine() {
        return (part1, part2) -> new StringBuilder(part1).append(part2).toString();
    }

    private Function<String, String> digest() {
        return (txnNumber) -> transactionNumberDigest.digest(txnNumber);
    }

}