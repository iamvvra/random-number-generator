package randomnumber.digest;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

final class Mod97Digest implements RandomNumberDigest {

    @Override
    public String digest(String randomNumber) {
        return prepare().andThen(createDigest()).apply(randomNumber);
    }

    private Function<String, String> createDigest() {
        return (randomNumber) -> {
            BigInteger modValue = new BigInteger(randomNumber).mod(BigInteger.valueOf(97));
            return randomNumber.substring(0, randomNumber.length() - 2) + "" + (98 - modValue.intValue());
        };
    }

    private Function<String, String> prepare() {
        return (randomNumber) -> randomNumber.substring(0, randomNumber.length() - 2) + "00";
    }

    @Override
    public boolean verify(String randomNumber) {
        BigInteger one = new BigInteger(randomNumber).mod(BigInteger.valueOf(97));
        return Optional.of(one.equals(BigInteger.ONE)).filter(mod -> mod == true)
                .orElseThrow(() -> new InvalidDigestException(randomNumber + " digest is invalid"));
    }

}