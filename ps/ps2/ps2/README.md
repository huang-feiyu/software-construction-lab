# Poetic Walks

[TOC]

## Problem 1

> Devise, document, and implement tests for `Graph<String>`

根据一定的 partition strategy, 即可构建出 test.

特别地, 测试非法值时, 给定更强的 spec:
* `set()`: if weight < 0, raise a `IllegalArgumentException`

## Problem 2

> implement weighted directed graphs with `String` labels

Document *Abstract Function* and *Representation invariant* (and how to avoid RI
exposure).

I only implemented `ConcreteEdgesGraph`, because both are the same in my mind.
(When I use my tests to test Implementation, I found that most bugs are in tests...)

## Problem 3

> implement generic `Graph<L>`

Nothing to say here.

## Problem 4

> implement `GraphPoet`:<br/>
> how a poet is initialized with a corpus and then, given an input, uses a word
> affinity graph defined by that corpus to transform the input poetically

我忽略了标点作为分隔符的处理以及大小写的处理, 大体是没有错误的.
