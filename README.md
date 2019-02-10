# random-number-generator
A library to generate varioud kinds of random numbers used as references to transactions

## VerifiableTransactionReferenceNumberGenerator
This is a 16 digit all numeric random number. It has two parts - first half generated from uuid, and the second half generated from nanoseconds. 

This random number has digest value included in it, this allows the number to be verified by the digest value.


