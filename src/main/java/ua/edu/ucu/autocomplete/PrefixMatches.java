package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;

/**
 *
 * @author andrii
 */
public class PrefixMatches {
    static final int minSize = 2;
    private Trie trie;
    private Object IndexOutOfBoundsException;
    private int size = 0;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int added = 0;
        for (String str: strings) {
            String[] newStr = str.split("\\s+");
            for (String s: newStr) {
                int len = str.length();
                if (len >= 2 && !trie.contains(s)) {
                    trie.add(new Tuple(str, len));
                    added++;
                }
            }
        }
        size += added;
        return added;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        size--;
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < minSize) {
            throw new IndexOutOfBoundsException();
        }
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        int len = pref.length();
        if (len < minSize) {
            throw new IndexOutOfBoundsException();
        }
        else if (len == 2) {
            k = len + 1;
        }

        Iterable<String> words = trie.wordsWithPrefix(pref);
        ArrayList<String> res = new ArrayList<>();
        for (String w: words) {
            if (w.length() < (len + k)) {
                res.add(w);
            }
        }
        return res;
    }

    public int size() {
        return size;
    }
}
