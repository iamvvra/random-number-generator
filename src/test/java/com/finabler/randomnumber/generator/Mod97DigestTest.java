package com.finabler.randomnumber.generator;

import static org.assertj.core.api.Assertions.assertThat;

import com.finabler.randomnumber.digest.FakeTransactionReferenceNumberExcption;
import com.finabler.randomnumber.digest.Mod97Digest;

import org.junit.Test;

public class Mod97DigestTest {

    @Test
    public void shouldDigestTheGivenNumericString() {
        String digestString = new Mod97Digest().digest("12300");
        assertThat(digestString).isNotNull();
        assertThat(digestString).doesNotEndWith("00");
    }

    @Test
    public void shouldReturnTrueWhenDigestPasses() {
        Mod97Digest mod97Digest = new Mod97Digest();
        String digestString = mod97Digest.digest("12300");
        assertThat(mod97Digest.verify(digestString)).isTrue();
    }

    @Test(expected = FakeTransactionReferenceNumberExcption.class)
    public void shouldThrowErrorWhenGivenStringDoesNotDigest() {
        Mod97Digest mod97Digest = new Mod97Digest();
        System.out.println(mod97Digest.verify("1234"));
    }
}