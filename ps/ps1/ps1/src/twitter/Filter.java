/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * <p>
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     *
     * @param tweets   a list of tweets with distinct ids, not modified by this method.
     * @param username Twitter username, required to be a valid Twitter username as
     *                 defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     * in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> ans = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (username.equals(tweet.getAuthor())) {
                ans.add(tweet);
            }
        }
        return ans;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     *
     * @param tweets   a list of tweets with distinct ids, not modified by this method.
     * @param timespan timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     * in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet> ans = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().isAfter(timespan.getStart()) &&
                tweet.getTimestamp().isBefore(timespan.getEnd())) {
                ans.add(tweet);
            }
        }
        return ans;
    }

    /**
     * Find tweets that contain certain words.
     *
     * @param tweets a list of tweets with distinct ids, not modified by this method.
     * @param words  a list of words to search for in the tweets.
     *               A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when
     * represented as a sequence of nonempty words bounded by space characters
     * and the ends of the string) includes *at least one* of the words
     * found in the words list. Word comparison is not case-sensitive,
     * so "Obama" is the same as "obama".  The returned tweets are in the
     * same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> tweetList = new ArrayList<>();

        for (Tweet tweet : tweets) {
            // split the tweet text into a list of words
            String text = tweet.getText().toLowerCase().replaceAll("[^\\w\\s]", "");
            List<String> textList = new ArrayList<>(Arrays.asList(text.split(" ")));
            // if word is in the tweet text, add to result
            for (String word : words) {
                if (textList.contains(word.toLowerCase())) {
                    tweetList.add(tweet);
                    break;
                }
            }
        }
        return tweetList;
    }

}
