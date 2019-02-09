package com.finabler.randomnumber.digest;

public class FakeTransactionReferenceNumberExcption extends RuntimeException {
    private static final long serialVersionUID = -26;

    public FakeTransactionReferenceNumberExcption(String message) {
        super(message);
    }
}