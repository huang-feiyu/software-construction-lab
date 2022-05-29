/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 *
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

    // Testing strategy
    //   init
    //     empty graph
    //
    //   add(vertex) -> boolean
    //     1. add an invalid vertex; vertex is null/"", return false (more spec)
    //     2. add a not contained label; vertex is not null, return true
    //     3. add a contained label; vertex is not null, return false
    //
    //   set(source, target, weight) -> prevWeight
    //     partition on weight:
    //       1. weight > 0
    //       2. weight = 0
    //       3. weight < 0
    //     partition on edge while weight >= 0:
    //       1. new edge
    //       2. existing edge
    //
    //   remove(vertex) -> boolean
    //     1. remove an invalid vertex; vertex is null/"", return false
    //     2. remove a not contained label; vertex is not null, return true
    //     3. remove a contained label; vertex is not null, return false
    //
    //   vertices() -> set
    //     1. empty graph
    //     2. not empty graph
    //
    //   sources(target) -> set
    //     1. set is nil
    //     2. set is not empty
    //
    //   targets(source) -> set
    //     1. set is nil
    //     2. set is not empty


    /**
     * Overridden by implementation-specific test classes.
     *
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
            Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testAddInvalidVertex() {
        // add: cover 1
        Graph<String> stringGraph = emptyInstance();
        assertFalse(stringGraph.add(null));
        assertEquals(Collections.emptySet(), stringGraph.vertices());
        assertFalse(stringGraph.add(""));
        assertEquals(Collections.emptySet(), stringGraph.vertices());
    }

    @Test
    public void testAddValidNotContainedVertex() {
        // add: cover 2
        Graph<String> stringGraph = emptyInstance();
        assertTrue(stringGraph.add("a"));
        assertEquals(Collections.singleton("a"), stringGraph.vertices());
    }

    @Test
    public void testAddValidContainedVertex() {
        // add: cover 3
        Graph<String> stringGraph = emptyInstance();
        stringGraph.add("a");
        assertFalse(stringGraph.add("a"));
        assertEquals(Collections.singleton("a"), stringGraph.vertices());
    }

    @Test
    public void testSetNewEdge() {
        Set<String> ansSet = new HashSet<>();
        ansSet.add("a");
        ansSet.add("b");
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("a", 2);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("b", 2);
        // set: cover 1-1
        Graph<String> stringGraph = emptyInstance();
        assertEquals(0, stringGraph.set("a", "b", 2));
        assertEquals(ansSet, stringGraph.vertices());
        assertEquals(map1, stringGraph.sources("b"));
        assertEquals(map2, stringGraph.targets("a"));
    }

    @Test
    public void testSetExistingEdge() {
        // set: cover 1-2
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("a", "b", 2);
        assertEquals(2, stringGraph.set("a", "b", 3));
    }

    @Test
    public void testSetZeroWeightNewEdge() {
        // set: cover 2-1
        Graph<String> stringGraph = emptyInstance();
        assertEquals(0, stringGraph.set("a", "b", 0));
        assertEquals(Collections.emptyMap(), stringGraph.sources("b"));
        assertEquals(Collections.emptyMap(), stringGraph.targets("a"));
    }

    @Test
    public void testSetZeroWeightExistingEdge() {
        // set: cover 2-2
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("a", "b", 2);
        assertEquals(2, stringGraph.set("a", "b", 0));
        assertEquals(Collections.emptyMap(), stringGraph.sources("a"));
        assertEquals(Collections.emptyMap(), stringGraph.targets("b"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeWeight() {
        // set: cover 3
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("a", "b", -1);
    }

    @Test
    public void testRemoveInvalidVertex() {
        Set<String> ansSet = new HashSet<>();
        ansSet.add("a");
        ansSet.add("b");
        // remove: cover 1
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("a", "b", 2);
        assertFalse(stringGraph.remove(""));
        assertEquals(ansSet, stringGraph.vertices());
        assertFalse(stringGraph.remove(null));
        assertEquals(ansSet, stringGraph.vertices());
    }

    @Test
    public void testRemoveValidNotContainedVertex() {
        Set<String> ansSet = new HashSet<>();
        ansSet.add("a");
        ansSet.add("b");
        // remove: cover 2
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("a", "b", 2);
        assertFalse(stringGraph.remove("c"));
        assertEquals(ansSet, stringGraph.vertices());
    }

    @Test
    public void testRemoveValidContainedVertex() {
        // remove: cover 3
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("a", "b", 2);
        assertTrue(stringGraph.remove("a"));
        assertEquals(Collections.singleton("b"), stringGraph.vertices());
    }

    @Test
    public void testVerticesEmptyGraph() {
        // vertices: cover 1
        Graph<String> stringGraph = emptyInstance();
        assertEquals(Collections.emptySet(), stringGraph.vertices());
    }

    @Test
    public void testVerticesNotEmptyGraph() {
        // vertices: cover 2
        Graph<String> stringGraph = emptyInstance();
        stringGraph.add("b");
        assertEquals(Collections.singleton("b"), stringGraph.vertices());
    }

    @Test
    public void testSourcesEmpty() {
        // sources: cover 1
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("a", "b", 2);
        assertEquals(Collections.emptyMap(), stringGraph.sources("a"));
    }

    @Test
    public void testSourcesNotEmpty() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 2);
        // sources: cover 2
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("a", "b", 2);
        assertEquals(map, stringGraph.sources("b"));
    }

    @Test
    public void testTargetsEmpty() {
        // targets: cover 1
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("a", "b", 2);
        assertEquals(Collections.emptyMap(), stringGraph.targets("b"));
    }

    @Test
    public void testTargetsNotEmpty() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 2);
        // targets: cover 2
        Graph<String> stringGraph = emptyInstance();
        stringGraph.set("b", "a", 2);
        assertEquals(map, stringGraph.targets("b"));
    }

}
