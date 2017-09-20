package com.jhdit.datastructures.trie;

import java.util.*;

public class WordTrie implements Trie {
    private final Map<Character, WordTrie> children = new HashMap<Character, WordTrie>();
    private final SortedSet<String> descendantWords = new TreeSet<String>();
    private final SortedSet<String> exactWords = new TreeSet<String>();

    @Override
    public void add(String word) {
        addNormalised( word.toLowerCase() );
    }

    private void addNormalised(String word) {
        WordTrie current = this;
        descendantWords.add(word);
        for (Character c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                current.children.put(c, new WordTrie());
            }
            current = current.children.get(c);
            current.descendantWords.add(word);
        }
        current.exactWords.add(word);
    }

    @Override
    public SortedSet<String> findPartial(final String wordStart) {
        Optional<WordTrie> current = getTrieForLastChar(wordStart);
        if (current.isPresent())    {
            return current.get().descendantWords;
        }
        return Collections.emptySortedSet();
    }

    @Override
    public SortedSet<String> findExact(String word) {
        Optional<WordTrie> current = getTrieForLastChar(word);
        if (current.isPresent())    {
            return current.get().exactWords;
        }
        return Collections.emptySortedSet();
    }

    Optional<WordTrie> getTrieForLastChar(String word) {
        WordTrie current = this;
        for (final Character c : word.toLowerCase().toCharArray()) {
            current = current.children.get(c);
            if (current == null) {
                return Optional.empty();
            }
        }
        return Optional.of(current);
    }
}
