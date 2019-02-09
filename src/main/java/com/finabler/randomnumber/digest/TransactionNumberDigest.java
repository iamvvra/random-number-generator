package com.finabler.randomnumber.digest;

public interface TransactionNumberDigest {
    public String digest(String txnNumber);

    public boolean verify(String txnNumber);
}