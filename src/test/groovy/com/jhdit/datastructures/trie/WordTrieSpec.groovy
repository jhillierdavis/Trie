package com.jhdit.datastructures.trie

import spock.lang.Specification
import spock.lang.Unroll

class WordTrieSpec extends Specification {

    @Unroll
    void "exact match with result for #inputWord"()   {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findExact( inputWord )

        then:
            words.size() == 1
            words.first() == expected

        where:
            inputWord||expected
            "ant"||"ant"
            "Ant"||"ant"
            "Bee"||"bee"
            "BEE"||"bee"
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
        return createTestWordTrie(["alligator", "Ant", "AntEater", "antelope", "bee"] as Set<String>)
    }

    WordTrie createTestWordTrie(Set<String> words)    {
        WordTrie wordTrie = new WordTrie()
        words.each() {
            wordTrie.add(it)
        }
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
            results.add("bad")

        then:
            final UnsupportedOperationException  e = thrown()

        and:
            trie.findExact("bogus").size() == 0

    }

}
