package randomnumber.generator;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import randomnumber.digest.InvalidDigestException;
import randomnumber.digest.RandomNumberDigest;

public class VariableRandomNumberGenerator implements RandomNumberGenerator {

    private int length;
    private RandomNumberDigest digest;
    private String staticIdentifier;
    private int identifierPosition = -1;

    public VariableRandomNumberGenerator(int length) {
        this(length, null);
    }

    public VariableRandomNumberGenerator(int length, RandomNumberDigest digest) {
        this.length = length;
        this.digest = digest;
    }

    @Override
    public String generate() {
        return createRandom().andThen(replaceStatic()).andThen(digest()).apply(firstHalf(), secondHalf());
    }

    private Function<String, String> replaceStatic() {
        return (rN) -> {
            return Optional.ofNullable(staticIdentifier).filter(r -> identifierPosition > -1)
                    .map(r -> rN.substring(0, identifierPosition) + r
                            + rN.substring(identifierPosition + staticIdentifier.length()))
                    .orElse(rN);
        };
    }

    @Override
    public boolean verify(String randomNumber) {
        return Optional.ofNullable(digest).map(d -> d.verify(randomNumber))
                .orElseThrow(() -> new InvalidDigestException("Digest not set"));
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public RandomNumberDigest getDigest() {
        return digest;
    }

    private BiFunction<Supplier<String>, Supplier<String>, String> createRandom() {
        return (c1, c2) -> {
            String randomNumber = numberJoiner().apply(c1.get(), c2.get());
            if (randomNumber.length() != length) {
                throw new RuntimeException("Error generatating random of length " + length);
            }
            return randomNumber;
        };
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
                        .substring(0, length / 2);
            } else {
                randomNumber = String.valueOf(mostSignificantBits.multiply(mostSignificantBits).add(mostSignificantBits)
                        .add(leastSignificantBits)).substring(0, length / 2);
            }
            return randomNumber;
        };
    }

    private Supplier<String> secondHalf() {
        return () -> {

            String nanoTime = String.valueOf(System.nanoTime());
            int start = nanoTime.length() - (length / 2);
            String secondHalfRandom = nanoTime.substring(start);
            return secondHalfRandom;
        };
    }

    private BiFunction<String, String, String> numberJoiner() {
        return (part1, part2) -> new StringBuilder(part1).append(part2).toString();
    }

    private Function<String, String> digest() {
        return (txnNumber) -> Optional.ofNullable(digest).map(digest -> digest.digest(txnNumber)).orElse(txnNumber);
    }

    public void setStaticIdentifier(String staticIdentifier, int identifierPosition) {
        this.staticIdentifier = staticIdentifier;
        this.identifierPosition = identifierPosition;
    }

}