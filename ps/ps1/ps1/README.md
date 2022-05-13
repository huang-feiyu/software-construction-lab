# Tweet Tweet

> [MIT 6.005: ps1](https://ocw.mit.edu/ans7870/6/6.005/s16/psets/ps1/)
>
> `README` records the hints and bugs.

[TOC]

(There is some typos in my comments. After all, I am not good at English.)

## Overview

* Test-First Programming:
  1. Study the specification of the method carefully.
  2. Write JUnit tests for the method according to the spec.
  3. Implement the method according to the spec.
  4. Revise your implementation and improve your test cases until your implementation passes all your tests.

Follow the spec, anyway.

## Problem 1

> Extracting data from tweets

A partition example:

```java
/*
 * Testing strategy
 *
 * cover the cartesian product of these partitions:
 *   partition on a: positive, negative, 0
 *   partition on b: positive, negative, 0
 *   partition on a: 1, !=1
 *   partition on b: 1, !=1
 *   partition on a: small (fits in a long value), or large (doesn't fit)
 *   partition on b: small, large
 * 
 * cover the subdomains of these partitions:
 *   partition on signs of a and b:
 *      both positive
 *      both negative
 *      different signs
 *      one or both are 0
 */
```

1. the same answer

I used to use `Instant.getNano()`, it is sensitive enough to differ two instants.

```java
/* Get the abs(nano) diff of two tweets. */
private static long getEpochDiffOfTweets(Tweet tweet1, Tweet tweet2) {
    return Math.abs(tweet1.getTimestamp().getEpochSecond() - tweet2.getTimestamp().getEpochSecond());
}
```

## Problem 2

> Filtering lists of tweets

The test for `containing()` is missed, because it's too many.

## Problem 3

> Inferring a social network

I just write part of the tests.

## Problem 4

It's boring, and I'm tired, so skip this problem.
