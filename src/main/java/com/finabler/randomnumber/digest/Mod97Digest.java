package com.finabler.randomnumber.digest;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

public class Mod97Digest implements TransactionNumberDigest {

    @Override
    public String digest(String txnNumber) {
        return prepare().andThen(createDigest()).apply(txnNumber);
    }

    private Function<String, String> createDigest() {
        return (preparedTxnNo) -> {
            BigInteger modValue = new BigInteger(preparedTxnNo).mod(BigInteger.valueOf(97));
            return preparedTxnNo.substring(0, preparedTxnNo.length() - 2) + "" + (98 - modValue.intValue());
        };
    }

    private Function<String, String> prepare() {
        return (txnNumber) -> txnNumber.substring(0, txnNumber.length() - 2) + "00";
    }

    @Override
    public boolean verify(String txnNumber) {
        BigInteger one = new BigInteger(txnNumber).mod(BigInteger.valueOf(97));
        return Optional.of(one.intValue() == 1).filter(mod -> mod == true)
                .orElseThrow(() -> new FakeTransactionReferenceNumberExcption(txnNumber + " looks fake"));
    }

}