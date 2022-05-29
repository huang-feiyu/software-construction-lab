/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 *
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices, edges) = a string graph
    // Representation invariant:
    //   `vertices` contains all the vertices in `edges`.
    // Safety from rep exposure:
    //   All fields are private, and can only be changed by methods with spec.

    ConcreteEdgesGraph() {
        checkRep();
    }

    // Check that the rep invariant is true
    private void checkRep() {
        Set<String> verticesInEdges = new HashSet<>();
        for (Edge edge : edges) {
            verticesInEdges.add(edge.getPrev());
            verticesInEdges.add(edge.getNext());
        }
        assert vertices.containsAll(verticesInEdges);
    }

    @Override
    public boolean add(String vertex) {
        if (vertex == null || vertex.equals("")) {
            return false;
        }
        boolean isVertexInVertices = vertices.contains(vertex);
        vertices.add(vertex);
        checkRep();
        return !isVertexInVertices;
    }

    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) {
            throw new java.lang.IllegalArgumentException("set: weight is less than 0");
        }

        if (!vertices.contains(target) || !vertices.contains(source)) {
            add(source);
            add(target);
            // add an edge
            if (weight > 0)
                edges.add(new Edge(source, target, weight));
            checkRep();
            return 0;
        }

        add(source);
        add(target);

        Edge setEdge = new Edge(source, target);
        int edgeIndex = getEdgeIndex(setEdge);
        int prevWeight = edgeIndex == -1 ? 0 : edges.get(edgeIndex).getWeight();
        // remove
        edges.remove(setEdge);
        if (weight > 0) {
            // change
            edges.add(new Edge(source, target, weight));
        }

        checkRep();
        return prevWeight;
    }

    @Override
    public boolean remove(String vertex) {
        if (vertex == null || vertex.equals("") || !vertices.contains(vertex)) {
            checkRep();
            return false;
        }
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.isLabelIn(vertex));
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        checkRep();
        return vertices;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sourcesMap = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getNext().equals(target)) {
                sourcesMap.put(edge.getPrev(), edge.getWeight());
            }
        }
        return sourcesMap;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targetsMap = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getPrev().equals(source)) {
                targetsMap.put(edge.getNext(), edge.getWeight());
            }
        }
        return targetsMap;
    }

    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (String v : vertices) {
            ans.append(v).append(": ").append(targets(v)).append("\n");
        }
        return ans.toString();
    }

    private int getEdgeIndex(Edge edge) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).equals(edge)) {
                return i;
            }
        }
        return -1;
    }
}

/**
 * An edge contains: two same type objects, `prev` and `next`; a weight.
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {

    private final String prev;
    private final String next;
    private final int weight;

    // Abstraction function:
    //   AF(prev, next, weight) = a weighted edge from prev to next
    //                          = prev {weight}-> next
    // Representation invariant:
    //   prev and next are different strings
    //   weight > 0
    // Safety from rep exposure:
    //   All fields(basic) are private, and all types are immutable.

    Edge(String prev, String next, int weight) {
        this.prev = prev;
        this.next = next;
        this.weight = weight;
        checkRep();
    }

    Edge(String prev, String next) {
        this.prev = prev;
        this.next = next;
        this.weight = 1; // default value
        checkRep();
    }

    // Check that the rep invariant is true
    // (Because all fields are immutable, so there is no need to put CheckRep everywhere.)
    private void checkRep() {
        assert !prev.equals(next);
        assert weight > 0;
    }

    public boolean isLabelIn(String label) {
        return label.equals(prev) || label.equals(next);
    }

    public String getPrev() {
        return prev;
    }

    public String getNext() {
        return next;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return prev + " " + "{" + weight + "}" + "-> " + next;
    }

    // need a new hashCode() method
    public boolean equals(Edge that) {
        return that.getPrev().equals(this.getPrev()) &&
               that.getNext().equals(this.getNext());
    }

}
