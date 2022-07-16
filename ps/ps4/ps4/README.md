# Multiplayer Minesweeper

> [MIT 6.005: ps4](https://ocw.mit.edu/ans7870/6/6.005/s16/psets/ps4/)

[TOC]

## Problem 1

> set up the server to deal with multiple clients.

Use `ExecutorService` as a thread pool.

## Problem 2

> design a data structure for Minesweeper.

According to [Board.java](./src/minesweeper/Board.java) first comment inside the class.

NOTE: X, Y is reversed for some reason.

## Problem 3

> make the entire system thread-safe.

In my case, I used `synchronized` to make the entire system thread-safe.

Every method writing or reading from the board is synchronized.

## Problem 4

> initialize the board based on command-line options.

```
Usage:

MinesweeperServer [--debug | --no-debug] [--port PORT]
                  [--size SIZE_X,SIZE_Y | --file FILE]
```

