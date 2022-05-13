/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * Testing strategy for Filter
     *
     * cover general subdomain of these partitions:
     *   partition on tweets.size:
     *     tweets.size = 0
     *     tweets.size = 1
     *     tweets.size > 1
     *
     * 1. writtenBy(List<Tweet> tweets, String username) -> ans
     * cover the cartesian product of partition below and general partition:
     *   partition on ans.size:
     *     ans.size = 0
     *     ans.size = 1
     *     ans.size > 1
     *
     * 2. inTimespan(List<Tweet> tweets, Timespan timespan) -> ans
     * cover the cartesian product of partition below and general partition:
     *   partition on ans.size:
     *     ans.size = 0
     *     ans.size = 1
     *     ans.size > 1
     *
     * 3. containing(List<Tweet> tweets, List<String> words) -> ans
     * cover the cartesian product of partitions below and general partition:
     *   partition on words.size:
     *     words.size = 0
     *     words.size = 1
     *     words.size > 1
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T10:29:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T10:29:49Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "huangfy", "huangblog.com is a blog about a real person @0711feiyu", d3);
    private static final Tweet tweet4 = new Tweet(4, "huangfy", "@huang-feiyu @huangfeiyu", d4);

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // cover ans.size = 0
    @Test
    public void testWrittenByNoneResult() {
        // tweets.size = 0
        List<Tweet> writtenBy0 = Filter.writtenBy(Collections.emptyList(), "huangfy");
        // tweets.size = 1
        List<Tweet> writtenBy1 = Filter.writtenBy(Arrays.asList(tweet1), "huangfy");
        // tweets.size > 1
        List<Tweet> writtenBy2 = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "huangfy");

        assertEquals("expected empty list", 0, writtenBy0.size());
        assertEquals("expected empty list", 0, writtenBy1.size());
        assertEquals("expected empty list", 0, writtenBy2.size());
    }

    // cover ans.size = 1
    @Test
    public void testWrittenBySingleResult() {
        // tweets.size = 1
        List<Tweet> writtenBy0 = Filter.writtenBy(Arrays.asList(tweet1), "alyssa");
        // tweets.size > 1
        List<Tweet> writtenBy1 = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");

        assertEquals("expected singleton list", 1, writtenBy0.size());
        assertTrue("expected list to contain tweet", writtenBy0.contains(tweet1));
        assertEquals("expected singleton list", 1, writtenBy1.size());
        assertTrue("expected list to contain tweet", writtenBy1.contains(tweet1));
    }

    // cover ans.size > 1
    @Test
    public void testWrittenByMultipleResult() {
        // tweets.size > 1
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4), "huangfy");

        assertEquals(2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet4));
    }

    // cover ans.size = 0
    @Test
    public void testInTimespanNoneResult() {
        Instant testStart = Instant.parse("2018-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2022-02-17T12:00:00Z");
        Timespan timespan = new Timespan(testStart, testEnd);

        // tweets.size = 0
        List<Tweet> inTimespan0 = Filter.inTimespan(Collections.emptyList(), timespan);
        // tweets.size = 1
        List<Tweet> inTimespan1 = Filter.inTimespan(Arrays.asList(tweet1), timespan);
        // tweets.size > 1
        List<Tweet> inTimespan2 = Filter.inTimespan(Arrays.asList(tweet1, tweet2), timespan);

        assertTrue("expected empty list", inTimespan0.isEmpty());
        assertTrue("expected empty list", inTimespan1.isEmpty());
        assertTrue("expected empty list", inTimespan2.isEmpty());
    }

    // cover ans.size = 1
    @Test
    public void testInTimespanOneResult() {
        Instant testStart = Instant.parse("2016-02-17T10:28:00Z");
        Instant testEnd = Instant.parse("2016-02-17T10:30:00Z");
        Timespan timespan = new Timespan(testStart, testEnd);

        // tweets.size = 1
        List<Tweet> inTimespan1 = Filter.inTimespan(Arrays.asList(tweet3), timespan);
        // tweets.size > 1
        List<Tweet> inTimespan2 = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), timespan);

        assertFalse("expected non-empty list", inTimespan1.isEmpty());
        assertEquals(1, inTimespan1.size());
        assertTrue("expected list to contain tweets", inTimespan1.contains(tweet3));
        assertFalse("expected non-empty list", inTimespan2.isEmpty());
        assertEquals(1, inTimespan2.size());
        assertTrue("expected list to contain tweets", inTimespan2.contains(tweet3));
    }

    // cover ans.size > 1
    @Test
    public void testInTimespanMultipleResult() {
        Instant testStart = Instant.parse("2016-02-17T09:28:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:30:00Z");
        Timespan timespan = new Timespan(testStart, testEnd);

        // tweets.size > 1
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4), timespan);

        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2, tweet3, tweet4)));
        assertEquals(0, inTimespan.indexOf(tweet1));
        assertEquals(1, inTimespan.indexOf(tweet2));
        assertEquals(2, inTimespan.indexOf(tweet3));
        assertEquals(3, inTimespan.indexOf(tweet4));
    }

    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     *
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
