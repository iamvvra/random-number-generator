package com.finabler.randomnumber.generator;

import com.finabler.randomnumber.digest.Mod97Digest;

public interface RandomNumberGenerator {

    public String generate();

    public boolean verify(String randomNumber);

    public static RandomNumberGenerator verifiableTransactionReferenceNumberGenerator() {
        return new VerifiableTransactionReferenceNumberGenerator(new Mod97Digest());
    }

}