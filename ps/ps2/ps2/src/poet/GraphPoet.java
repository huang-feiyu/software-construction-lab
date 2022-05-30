/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;
import java.util.*;

import graph.ConcreteEdgesGraph;
import graph.Graph;

/**
 * A graph-based poetry generator.
 *
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 *
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 *
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 *
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 *
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {

    private final Graph<String> graph = new ConcreteEdgesGraph<String>().empty();

    // Abstraction function:
    //   AF(corpus) = a graphPoet who can generate a poem with an input
    // Representation invariant:
    //   a non-empty graph
    // Safety from rep exposure:
    //   graph is private, and can only be modified by its own constructor.

    /**
     * Create a new poet with the graph from corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        if (corpus == null) {
            throw new IllegalArgumentException();
        }
        Scanner reader = new Scanner(corpus);
        ArrayList<String> words = new ArrayList<>();
        while (reader.hasNext()) {
            words.addAll(Arrays.asList(reader.nextLine().toLowerCase().split(" ")));
        }
        for (int i = 0; i < words.size() - 1; i++) {
            graph.set(words.get(i), words.get(i + 1), 1);
        }
        if (graph.vertices().equals(Collections.emptySet())) {
            throw new IllegalArgumentException();
        }
        checkRep();
    }

    private void checkRep() {
        assert !graph.vertices().equals(Collections.emptySet());
    }

    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        if (input == null || input.equals("")) {
            throw new IllegalArgumentException();
        }
        String[] inputWords = input.toLowerCase().split(" ");
        for (int i = 0; i < inputWords.length; i++) {
            System.out.println(inputWords[i]);
        }
        ArrayList<String> poemWords = new ArrayList<>();
        for (int i = 0; i < inputWords.length - 1; i++) {
            poemWords.add(inputWords[i]);
            Map<String, Integer> candidates = graph.targets(inputWords[i]);
            String bridge = null;
            int maxWeight = 0;
            for (String candidate : candidates.keySet()) {
                if (graph.targets(candidate).containsKey(inputWords[i + 1]) &&
                    maxWeight < graph.targets(candidate).get(inputWords[i + 1]) + candidates.get(candidate)) {
                    bridge = candidate;
                    maxWeight = graph.targets(candidate).get(inputWords[i + 1]) + candidates.get(candidate);
                }
            }
            if (maxWeight != 0 && bridge != null) {
                poemWords.add(bridge);
            }
        }
        poemWords.add(inputWords[inputWords.length - 1]);
        StringBuilder ans;
        ans = new StringBuilder(poemWords.get(0));
        for (int i = 1; i < poemWords.size(); i++) {
            ans.append(" ").append(poemWords.get(i));
        }
        return ans.toString();
    }

    public String toString() {
        return graph.toString();
    }
}
