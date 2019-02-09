package com.finabler.randomnumber.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.finabler.randomnumber.digest.FakeTransactionReferenceNumberExcption;

import org.junit.Test;

public class TransactionReferenceNumberGeneratorTest {

    @Test
    public void shouldGenerateAllNumeric16LengthTransactionReferenceNumber() {
        RandomNumberGenerator randomNumberGenerator = RandomNumberGenerator
                .verifiableTransactionReferenceNumberGenerator();
        String txnRefNumber = randomNumberGenerator.generate();
        assertThat(txnRefNumber).isNotNull();
        assertThat(txnRefNumber.length()).isEqualTo(16);
        assertThat(txnRefNumber).matches("^[0-9]*$");
    }

    @Test
    public void shouldReturnTrueWhen16DigitTxnRefNumberIsAuthentic() {
        RandomNumberGenerator randomNumberGenerator = RandomNumberGenerator
                .verifiableTransactionReferenceNumberGenerator();
        String txnNumber = randomNumberGenerator.generate();
        boolean authentic = randomNumberGenerator.verify(txnNumber);
        assertThat(authentic).isTrue();
    }

    @Test(expected = FakeTransactionReferenceNumberExcption.class)
    public void shouldThrowFakeTransactionNumberExceptionForWrongRefNumbers() {
        RandomNumberGenerator randomNumberGenerator = RandomNumberGenerator
                .verifiableTransactionReferenceNumberGenerator();
        randomNumberGenerator.verify("1234567890987653");
        randomNumberGenerator.verify("2387398200011211");
    }

    @Test
    public void shouldNotCreateDuplicateNumbers() {
        RandomNumberGenerator randomNumberGenerator = RandomNumberGenerator
                .verifiableTransactionReferenceNumberGenerator();
        List<String> randomNos = IntStream.range(0, 1000).mapToObj(i -> randomNumberGenerator.generate())
                .collect(Collectors.toList());
        assertThat(randomNos).doesNotHaveDuplicates();
    }

}