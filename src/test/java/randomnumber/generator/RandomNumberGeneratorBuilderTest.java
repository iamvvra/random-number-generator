package randomnumber.generator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import randomnumber.digest.RandomNumberDigest;

public class RandomNumberGeneratorBuilderTest {

    @Test
    public void shouldCreateRandomNumberGeneratorWithGivenSizeAndDigest() {
        RandomNumberGenerator randomNumberGenerator = RandomNumberGeneratorBuilder.newBuilder().length(10)
                .digest(RandomNumberDigest.defaultDigest()).build();
        assertThat(randomNumberGenerator).isNotNull();
        assertThat(randomNumberGenerator.getLength()).isEqualTo(10);
        assertThat(randomNumberGenerator.getDigest()).isNotNull();
    }
}