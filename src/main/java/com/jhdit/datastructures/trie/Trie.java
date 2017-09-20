package com.jhdit.datastructures.trie;

import java.util.SortedSet;

public interface Trie {
    /**
     * Add word
     */
    public void add(String word);

    /**
     * @return All words with the specified prefix
     */
    public SortedSet<String> findPartial(String prefix);

    /**
     * @return All availability exactly matching the specified word
     */
    public SortedSet<String> findExact(String word);
}
