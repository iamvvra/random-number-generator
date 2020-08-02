package randomnumber.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import randomnumber.digest.InvalidDigestException;
import randomnumber.digest.RandomNumberDigest;

public class RandomNumberGeneratorTest {

	@Test
	public void shouldGenerateAllNumeric16LengthTransactionReferenceNumber() {
		RandomNumberGenerator randomNumberGenerator = RandomNumberGeneratorBuilder.newBuilder().length(16).build();

		List<String> txnRefNumber = IntStream.range(0, 1000).mapToObj(i -> randomNumberGenerator.generate())
				.collect(Collectors.toList());

		assertThat(txnRefNumber).isNotNull().allMatch(r -> r.length() == 16);
	}

	@Test
	public void shouldReturnGenerateRandomWithVerifiableDigest() {
		RandomNumberGenerator randomNumberGenerator = RandomNumberGeneratorBuilder.newBuilder()
				.digest(RandomNumberDigest.defaultDigest()).length(16).build();
		String txnNumber = randomNumberGenerator.generate();
		boolean authentic = randomNumberGenerator.verify(txnNumber);
		assertThat(authentic).isTrue();
	}

	@Test(expected = InvalidDigestException.class)
	public void shouldThrowExceptionForWrongRefNumbers() {
		RandomNumberGenerator randomNumberGenerator = RandomNumberGeneratorBuilder.newBuilder().length(16).build();
		randomNumberGenerator.verify("1234567890987653");
		randomNumberGenerator.verify("2387398200011211");
	}

	@Test
	public void shouldNotCreateDuplicateNumbers() {
		RandomNumberGenerator randomNumberGenerator = RandomNumberGeneratorBuilder.newBuilder().length(16).build();
		List<String> randomNos = IntStream.range(0, 1000).mapToObj(i -> randomNumberGenerator.generate())
				.collect(Collectors.toList());
		assertThat(randomNos).doesNotHaveDuplicates();
	}

	@Test
	public void shouldCreateRandomNumberGeneratorForGivenLength() {
		RandomNumberGenerator randomNumberGenerator = RandomNumberGeneratorBuilder.newBuilder().length(14).build();
		String rand = randomNumberGenerator.generate();
		assertThat(rand).hasSize(14);
		RandomNumberGenerator randomNumberGenerator16 = RandomNumberGeneratorBuilder.newBuilder().length(16).build();
		assertThat(randomNumberGenerator16.generate()).hasSize(16);
	}

	@Test
	public void shouldCreateRandomNumberWithStaticIdentifierInSpecificPosition() {
		RandomNumberGenerator instrumentNumberGenerator = RandomNumberGeneratorBuilder.newBuilder().length(14)
				.staticIdentifier("9", 6).build();
		RandomNumberGenerator txnNumberGenerator = RandomNumberGeneratorBuilder.newBuilder().length(16)
				.staticIdentifier("5", 5).build();
		assertIdentifier(generateRandom(instrumentNumberGenerator, 10), 14, "9", 6);
		assertIdentifier(generateRandom(txnNumberGenerator, 10), 16, "5", 5);
	}

	private List<String> generateRandom(RandomNumberGenerator randomNumberGenerator, int count) {
		return IntStream.range(0, count).mapToObj(i -> randomNumberGenerator.generate()).collect(Collectors.toList());
	}

	private void assertIdentifier(List<String> randomList, int length, String identifier, int position) {
		assertThat(randomList).allMatch(s -> s.length() == length)
				.allMatch(s -> s.substring(position, position + identifier.length()).equals(identifier));
	}

}