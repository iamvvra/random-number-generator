package com.finabler.randomnumber.generator;

import static org.assertj.core.api.Assertions.assertThat;

import com.finabler.randomnumber.digest.FakeTransactionReferenceNumberExcption;
import com.finabler.randomnumber.digest.Mod10Digest;

import org.junit.Test;

public class Mod10DigestTest {

    @Test
    public void shouldDigestTheGivenNumericString() {
        String digestString = new Mod10Digest().digest("12300");
        System.out.println(digestString);
        assertThat(digestString).isNotNull();
        assertThat(digestString).doesNotEndWith("00");
    }

    @Test
    public void shouldReturnTrueWhenDigestPasses() {
        Mod10Digest mod10Digest = new Mod10Digest();
        String digestString = mod10Digest.digest("12300");
        assertThat(mod10Digest.verify(digestString)).isTrue();
    }

    @Test(expected = FakeTransactionReferenceNumberExcption.class)
    public void shouldThrowErrorWhenGivenStringDoesNotDigest() {
        Mod10Digest mod10Digest = new Mod10Digest();
        System.out.println(mod10Digest.verify("1234"));
    }
}