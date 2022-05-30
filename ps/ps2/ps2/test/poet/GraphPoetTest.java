/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

    // Testing strategy
    //   GraphPoet(file)
    //     1. init by empty file (raise a `IllegalArgumentException`)
    //     2. init by a file (test by its `toString` method)
    //   poem(input) -> str
    //     1. input is null/""
    //     2. input is not null

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGraphPoetByEmptyFile() throws IOException {
        // cover 1
        File emptyFile = new File("./test/poet/empty-corpus.txt");
        GraphPoet poet = new GraphPoet(emptyFile);
    }

    @Test
    public void testGraphPoetByNonEmptyFile() throws IOException {
        // cover 2
        File nonEmptyFile = new File("./test/poet/test-corpus.txt");
        GraphPoet poet = new GraphPoet(nonEmptyFile);
        assertEquals("new: {worlds=1, civilizations=1, life=1}\n" +
            "worlds: {to=1}\n" +
            "explore: {strange=1}\n" +
            "and: {new=1}\n" +
            "to: {explore=1, seek=1}\n" +
            "civilizations: {}\n" +
            "seek: {out=1}\n" +
            "strange: {new=1}\n" +
            "life: {and=1}\n" +
            "out: {new=1}\n", poet.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPoemByEmptyInput() throws IOException {
        // cover 1
        GraphPoet poet = new GraphPoet(new File("./test/poet/test-corpus.txt"));
        assertEquals("", poet.poem(""));
    }

    @Test
    public void testPoemByNonEmptyInput() throws IOException {
        // cover 2
        GraphPoet poet = new GraphPoet(new File("test/poet/test-corpus.txt"));
        assertEquals("seek to explore strange new life and exciting synergies!",
            poet.poem("Seek to explore new and exciting synergies!"));
    }

}
