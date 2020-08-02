package randomnumber.generator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import randomnumber.digest.InvalidDigestException;
import randomnumber.digest.RandomNumberDigest;

public class Mod97DigestTest {

    @Test
    public void shouldDigestTheGivenNumericString() {
        String digestString = RandomNumberDigest.defaultDigest().digest("12300");
        assertThat(digestString).isNotNull();
        assertThat(digestString).doesNotEndWith("00");
    }

    @Test
    public void shouldReturnTrueWhenDigestPasses() {
        RandomNumberDigest mod97Digest = RandomNumberDigest.defaultDigest();
        String digestString = mod97Digest.digest("12300");
        assertThat(mod97Digest.verify(digestString)).isTrue();
    }

    @Test(expected = InvalidDigestException.class)
    public void shouldThrowErrorWhenGivenStringDoesNotDigest() {
        RandomNumberDigest mod97Digest = RandomNumberDigest.defaultDigest();
        mod97Digest.verify("1234");
    }
}