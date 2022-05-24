# Poetic Walks

[TOC]

## Problem 1

> Devise, document, and implement tests for `Graph<String>`

根据一定的 partition strategy, 即可构建出 test.

特别地, 测试非法值时, 给定更强的 spec:
* `set()`: if weight < 0, raise a `IllegalArgumentException`
