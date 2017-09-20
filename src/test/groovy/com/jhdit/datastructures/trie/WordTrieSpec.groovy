package com.jhdit.datastructures.trie

import spock.lang.Specification

class WordTrieSpec extends Specification {

    void "exact match with result"()   {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findExact( "ant")

        then:
            words.size() == 1
            words.first() == "ant"
    }

    void "exact match without result"()   {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findExact( "unicorn")

        then:
            words.size() == 0
    }


    void "partial match with single result"()   {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findPartial( "b")

        then:
            words.size() == 1
            words.containsAll(['bee'])
    }

    void "partial match with multiple results"()   {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findPartial( "ant")

        then:
            words.size() == 3
            words.containsAll(['ant', 'anteater', 'antelope'])
    }

    void "partial match with no results"()   {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findPartial( "c")

        then:
            words.size() == 0
    }

    WordTrie createTestWordTrie()    {
        WordTrie wordTrie = new WordTrie()
        wordTrie.add("alligator")
        wordTrie.add("ant")
        wordTrie.add("anteater")
        wordTrie.add("antelope")
        wordTrie.add("bee")
        return wordTrie
    }

    void "empty result set cannot be manipulated"()    {
        given:
            Trie trie = new WordTrie()

        when:
            def results = trie.findExact("bogus")

        then:
            results.size() == 0

        when:
            results.add("Bad")

        then:
            trie.findExact("bogus").size() == 0

    }

}