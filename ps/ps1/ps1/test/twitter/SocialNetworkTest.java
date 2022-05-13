/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * Testing strategy for SocialNetwork
     *
     * cover subdomain of these partitions:
     *   partition on tweets:
     *     tweets.size = 0
     *     tweets.size = 1
     *     tweets.size > 1
     *
     * 1. guessFollowsGraph(List<Tweet> tweets) -> graph:Map<String, Set<String>>
     * cover the cartesian product of partitions below and general partition:
     *   partition on graph.size:
     *     graph.size = 0
     *     graph.size > 0
     *   partitions on graph.Set:
     *     graph.Set.size = 0
     *     graph.Set.size > 0
     *
     * 2. influencers(Map<String, Set<String>> followsGraph) -> ans
     * cover the cartesian product of partition below and general partition:
     *   partition on ans.size:
     *     ans.size = 0
     *     ans.size > 0
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T10:29:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T10:29:49Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype @huangfeiyu", d2);
    private static final Tweet tweet3 = new Tweet(3, "huangfy", "huangblog.com is a blog about a real person @0711feiyu", d3);
    private static final Tweet tweet4 = new Tweet(4, "huangfy", "@huang-feiyu @huangfeiyu", d4);

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // graph.size() = 0
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph0 = SocialNetwork.guessFollowsGraph(new ArrayList<>());

        assertTrue("expected empty graph", followsGraph0.isEmpty());
    }

    // graph.size() > 0
    @Test
    public void testGuessFollowsGraphNotEmpty() {
        Map<String, Set<String>> followsGraph1 = SocialNetwork.guessFollowsGraph(Collections.singletonList(tweet3));
        Map<String, Set<String>> followsGraph2 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3, tweet4));

        assertEquals(1, followsGraph1.size());
        assertEquals(1, followsGraph1.get("huangfy").size());
        assertTrue(followsGraph1.get("huangfy").contains("0711feiyu"));
        assertEquals(1, followsGraph2.size());
        assertEquals(3, followsGraph2.get("huangfy").size());
        assertTrue(followsGraph2.get("huangfy").contains("0711feiyu"));
        assertTrue(followsGraph2.get("huangfy").contains("huangfeiyu"));
        assertTrue(followsGraph2.get("huangfy").contains("huang-feiyu"));
    }

    // ans.size = 0
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected empty list", influencers.isEmpty());
    }

    // ans.size = 1
    @Test
    public void testInfluencersNotEmpty() {
        Map<String, Set<String>> followsGraph1 = SocialNetwork.guessFollowsGraph(Collections.singletonList(tweet3));
        Map<String, Set<String>> followsGraph2 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet3, tweet4));
        List<String> influencers1 = SocialNetwork.influencers(followsGraph1);
        List<String> influencers2 = SocialNetwork.influencers(followsGraph2);

        assertEquals(2, influencers1.size());
        assertTrue(influencers1.contains("0711feiyu"));
        assertTrue(influencers1.contains("huangfy"));
        assertEquals(5, influencers2.size());
        assertEquals("huangfeiyu", influencers2.get(0));
        assertTrue(influencers2.contains("0711feiyu"));
        assertTrue(influencers2.contains("huang-feiyu"));
        assertTrue(influencers2.contains("huangfeiyu"));
        assertTrue(influencers2.contains("bbitdiddle"));

    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     *
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
