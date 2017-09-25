package com.jhdit.datastructures.trie

import spock.lang.Specification
import spock.lang.Unroll

class WordTrieSpec extends Specification {

    @Unroll
    void "exact match with result for #inputWord"() {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findExact(inputWord)

        then:
            words.size() == 1
            words.first() == expected

        where:
            inputWord || expected
            "ant"     || "ant"
            "Ant"     || "ant"
            "Bee"     || "bee"
            "BEE"     || "bee"
    }

    @Unroll
    void "exception thrown for exact match with invalid input #inputWord"() {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            wordTrie.findExact(inputWord)

        then:
            IllegalArgumentException e = thrown()

        where:
            inputWord || _
            null      || _
            ""        || _
            " "       || _
    }

    void "exact match without result"() {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findExact("unicorn")

        then:
            words.size() == 0
    }


    void "partial match with single result"() {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findPartial("b")

        then:
            words.size() == 1
            words.containsAll(['bee'])
    }

    void "partial match with multiple results"() {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findPartial("ant")

        then:
            words.size() == 3
            words.containsAll(['ant', 'anteater', 'antelope'])
    }

    void "partial match with no results"() {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findPartial("c")

        then:
            words.size() == 0
    }

    WordTrie createTestWordTrie() {
        return createTestWordTrie(["alligator", "Ant", "AntEater", "antelope", "bee"] as Set<String>)
    }

    WordTrie createTestWordTrie(Set<String> words) {
        WordTrie wordTrie = new WordTrie()
        words.each() {
            wordTrie.add(it)
        }
        return wordTrie
    }

    void "empty result set cannot be manipulated"() {
        given:
            Trie trie = new WordTrie()

        when:
            def results = trie.findExact("bogus")

        then:
            results.size() == 0

        when:
            results.add("bad")

        then:
            final UnsupportedOperationException e = thrown()

        and:
            trie.findExact("bogus").size() == 0

    }

    void "result set cannot be manipulated"() {
        given:
            Trie trie = createTestWordTrie()

        when:
            def results = trie.findPartial("ant")

        then:
            results.size() == 3

        when:
            results.add("bad")

        then:
            final UnsupportedOperationException e = thrown()

        and:
            trie.findPartial("ant").size() == 3

    }


    void "prefix words can be removed"() {
        given:
            Trie trie = createTestWordTrie()

        when:
            def results = trie.findPartial("ant")

        then:
            results.size() == 3

        and:
            trie.findExact("ant").size() == 1

        when:
            trie.remove("ant")

        then:
            trie.findPartial("ant").size() == 2

        and:
            trie.findExact("ant").size() == 0

    }

    void "single words can be removed"() {
        given:
            WordTrie wordTrie = createTestWordTrie()

        when:
            SortedSet<String> words = wordTrie.findPartial("b")

        then:
            words.size() == 1
            words.containsAll(['bee'])

        when:
            wordTrie.remove('bee')
            words = wordTrie.findPartial("b")

        then:
            words.size() == 0

//        and:
//
//            wordTrie.display()
//            !wordTrie.getTrieForLastChar('bee').isPresent()
   }
}
