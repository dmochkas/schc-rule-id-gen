package com.dochkas.schcRuleId.model;

import java.util.*;

public class Pool implements Iterable<BitSeqBase> {

    private final SortedSet<Node> memory = new TreeSet<>();

    public int size() {
        return memory.size();
    }

    public void add(BitSeqBase id) {
        final Node node = new Node();
        node.value = id;
        memory.add(node);
    }

    /**
     * Splits values in the pools skipping n top prefixes.
     * @param n - number of top prefixes to skip. Negative values are considered 0.
     */
    public void split(int n) {
        n = Math.max(0, n);
        final Set<Node> children = new HashSet<>();
        Node target = null;
        int i = 0;
        for (Node node : memory) {
            i++;
            if (i <= n) {
                continue;
            } else if (i == n + 1) {
                target = node;
            }

            final Node childNodeLeft = new Node();
            childNodeLeft.value = BitSeqBase.BitSeqBuilder
                    .fromBitId(node.value)
                    .appendBit(0)
                    .build(node.value.getClass());

            final Node childNodeRight = new Node();
            childNodeRight.value = BitSeqBase.BitSeqBuilder
                    .fromBitId(node.value)
                    .appendBit(1)
                    .build(node.value.getClass());

            children.add(childNodeLeft);
            children.add(childNodeRight);
        }

        if (target != null) {
            memory.tailSet(target).clear();
        }

        memory.addAll(children);
    }

    public void fullSplit() {
        this.split(0);
    }

    public BitSeqBase pop() {
        final Node top = memory.first();
        memory.remove(top);
        return top.value;
    }

    @Override
    public Iterator<BitSeqBase> iterator() {
        return memory.stream().map(node -> node.value).iterator();
    }

    @Override
    public String toString() {
        return memory.toString();
    }

    private static class Node implements Comparable<Node> {
        private BitSeqBase value;

        @Override
        public int compareTo(Node node) {
            return this.value.compareTo(node.value);
        }

        @Override
        public String toString() {
            return String.format("Node(%s)", value);
        }
    }
}
