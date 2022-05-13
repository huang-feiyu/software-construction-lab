/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * Testing strategy for Extract
     *
     * 1. getTimeSpan(List<Tweet> tweets)
     * cover subdomain of these partitions:
     *   partition on tweets:
     *     tweets.size = 0/1 (implicitly defined, return null instead)
     *     tweets.size = 2
     *     tweets.size > 2
     *
     * 2. getMentionedUsers(List<Tweet> tweets) -> mentioned
     * cover the cartesian product of these partitions: (except 0,1)
     *   partition on tweets.size:
     *     tweets.size = 0
     *     tweets.size = 1
     *     tweets.size > 1
     *   partition on number of mentioned people:
     *     mentioned.size = 0
     *     mentioned.size = 1
     *     mentioned.size > 1
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

    // covers tweets.size = 0/1
    @Test
    public void testGetTimeInvalidTweets() {
        assertNull(Extract.getTimespan(Collections.emptyList()));
        assertNull(Extract.getTimespan(Collections.singletonList(tweet1)));
    }

    // covers tweets.size = 2
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    // covers tweets.size > 2
    @Test
    public void testGetTimespanOverTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }

    // covers mentioned.size = 0
    @Test
    public void testGetMentionedUsersNoMention() {
        // tweets.size = 0
        Set<String> mentionedUsers0 = Extract.getMentionedUsers(Collections.emptyList());
        // tweets.size = 1
        Set<String> mentionedUsers1 = Extract.getMentionedUsers(Arrays.asList(tweet1));
        // tweets.size > 1
        Set<String> mentionedUsers2 = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2));

        assertTrue("tweets.size = 0: expected empty set", mentionedUsers0.isEmpty());
        assertTrue("tweets.size = 1: expected empty set", mentionedUsers1.isEmpty());
        assertTrue("tweets.size > 1: expected empty set", mentionedUsers2.isEmpty());
    }

    // covers mentioned.size = 1
    @Test
    public void testGetMentionedUsersOneMention() {
        // tweets.size = 1
        Set<String> mentionedUsers1 = Extract.getMentionedUsers(Collections.singletonList(tweet3));
        // tweets.size > 1
        Set<String> mentionedUsers2 = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3));

        assertEquals(mentionedUsers1.size(), 1);
        assertTrue(mentionedUsers1.contains("0711feiyu"));
        assertEquals(mentionedUsers2.size(), 1);
        assertTrue(mentionedUsers2.contains("0711feiyu"));
    }

    // covers mentioned.size > 1
    @Test
    public void testGetMentionedUsersOverOneMention() {
        // tweets.size > 1
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3, tweet4));

        assertEquals(mentionedUsers.size(), 3);
        assertTrue(mentionedUsers.contains("0711feiyu"));
        assertTrue(mentionedUsers.contains("huangfeiyu"));
        assertTrue(mentionedUsers.contains("huang-feiyu"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     *
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
