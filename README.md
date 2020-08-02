# random-number-generator
A utility to generate random numeric only strings.

## Dependency
```xml
<!---add this dependency to you pom.xml-->
<dependency>
    <groupId>random-number-generator</groupId>
    <artifactId>random-number-generator</artifactId>
    <version>{version}</version>
</dependency>
```

## Usage
Initialize the `VariableRandomNumberGenerator` class with expected random string length & digest, and generate random strings. The generator can be initialized in 3 ways

```java

// Use builders
RandomNumberGenerator randomNumberGenerator = RandomNumberGeneratorBuilder.BUILDER.length(10)
                .digest(RandomNumberDigest.defaultDigest()).build();
String random1 = randomNumberGenerator.generate(); 

// to create random number generator with an identifier placed statically in the generated random number
// all the random numbers generated will have "01" in the 4th and 5th index fromm left (index starts with 0)
// ex., 2383002875, 1293002938, 9873003222, 7651007221
RandomNumberGenerator randomNumberGenerator = RandomNumberGeneratorBuilder.BUILDER.length(10)
                .digest(RandomNumberDigest.defaultDigest())
                .staticIdentifier("00", 4)
                .build();
```

```java

// Use factory method

RandomNumberGenerator randomNumberGenerator = RandomNumberGenerator.createVariableRandomNumberGenerator(16);
String referenceNumber1 = randomNumberGenerator.generate();
String referenceNumber2 = randomNumberGenerator.generate();
// technically, referenceNumber1.equals(referenceNumber2); //false
```

```java
// straight initialization
VariableRandomNumberGenerator generator = new VariableRandomNumberGenerator(16, RandomNumberDigest.defaultDigest());
String random1 = generator.generate();
```

How to verify the random number?
```java
RandomNumberGenerator randomNumberGenerator = RandomNumberGeneratorBuilder.BUILDER.length(10)
                .digest(RandomNumberDigest.defaultDigest()).build();
String txnRefNumber = randomNumberGenerator.generate();
//somewhere else
boolean validity = randomNumberGenerator.verify(someTxnNumber);
// throws InvalidDigestException when the given input does not digest properly.

```

