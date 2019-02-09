package com.finabler.randomnumber.digest;

import java.math.BigInteger;
import java.util.Optional;

public class Mod10Digest implements TransactionNumberDigest {

    @Override
    public String digest(String txnNumber) {
        BigInteger mod1 = new BigInteger(txnNumber).mod(BigInteger.valueOf(97));
        return txnNumber.substring(0, txnNumber.length() - 2) + "" + (98 - mod1.intValue());
    }

    @Override
    public boolean verify(String txnNumber) {
        BigInteger one = new BigInteger(txnNumber).mod(BigInteger.valueOf(97));
        return Optional.of(one.intValue() == 1).filter(mod -> mod == true)
                .orElseThrow(() -> new FakeTransactionReferenceNumberExcption(txnNumber + " looks fake"));
    }

}