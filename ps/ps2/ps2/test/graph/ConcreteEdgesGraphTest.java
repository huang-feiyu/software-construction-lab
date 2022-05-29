/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

/**
 * Tests for ConcreteEdgesGraph.
 * <p>
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * <p>
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest{

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */

    // Testing strategy for ConcreteEdgesGraph.toString()
    //   1. empty graph
    //   2. non-empty graph

    @Test
    public void testToStringEmptyGraph() {
        assertEquals("", emptyInstance().toString());
    }

    @Test
    public void testToStringNonEmptyGraph() {
        Graph<String> graph = emptyInstance();
        graph.set("a", "b", 2);
        assertEquals("a: {b=2}\nb: {}\n", graph.toString());
    }

    /*
     * <s>Testing Edge...</s> There is no need to test Edge.
     */

}
