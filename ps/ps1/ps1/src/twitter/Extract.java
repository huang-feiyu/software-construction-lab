/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * <p>
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     *
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     * every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty() || tweets.size() == 1) {
            return null;
        }
        long leastNanoDiff = Long.MAX_VALUE;
        Timespan minTimespan = getTimespanOfTweets(tweets.get(0), tweets.get(1));
        for (int i = 0; i < tweets.size(); i++) {
            for (int j = i + 1; j < tweets.size(); j++) {
                long tmpNanoDiff = getEpochDiffOfTweets(tweets.get(i), tweets.get(j));
                if (leastNanoDiff > tmpNanoDiff) {
                    leastNanoDiff = tmpNanoDiff;
                    minTimespan = getTimespanOfTweets(tweets.get(i), tweets.get(j));
                }
            }
        }
        return minTimespan;
    }

    /* Get the time period of two tweets. */
    private static Timespan getTimespanOfTweets(Tweet tweet1, Tweet tweet2) {
        Instant instant1 = tweet1.getTimestamp();
        Instant instant2 = tweet2.getTimestamp();
        if (instant1.isAfter(instant2)) {
            return new Timespan(instant2, instant1);
        } else {
            return new Timespan(instant1, instant2);
        }
    }

    /* Get the abs(nano) diff of two tweets. */
    private static long getEpochDiffOfTweets(Tweet tweet1, Tweet tweet2) {
        return Math.abs(tweet1.getTimestamp().getEpochSecond() - tweet2.getTimestamp().getEpochSecond());
    }

    /**
     * Get usernames mentioned in a list of tweets.
     *
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     * A username-mention is "@" followed by a Twitter username (as
     * defined by Tweet.getAuthor()'s spec).
     * The username-mention cannot be immediately preceded or followed by any
     * character valid in a Twitter username.
     * For this reason, an email address like bitdiddle@mit.edu does NOT
     * contain a mention of the username mit.
     * Twitter usernames are case-insensitive, and the returned set may
     * include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentioned = new HashSet<>();

        for (Tweet tweet : tweets) {
            // from stackoverflow: https://stackoverflow.com/questions/40365596/extracting-twitter-username-from-a-given-text-java-regex
            Matcher m = Pattern.compile("(?<=@)([\\w-]+)").matcher(tweet.getText()); // Analyze text with a regex that will capture usernames preceded by @
            while (m.find()) {
                // Stores all username (without @)
                mentioned.add(m.group(1));
            }
        }

        return mentioned;
    }

}
