package com.jhdit.datastructures.trie;

import java.util.SortedSet;

public interface Trie {
    /**
     * Add word
     */
    void add(String word);

    /**
     * @return All words with the specified prefix
     */
    SortedSet<String> findPartial(String prefix);

    /**
     * @return All availability exactly matching the specified word
     */
    SortedSet<String> findExact(String word);
}
