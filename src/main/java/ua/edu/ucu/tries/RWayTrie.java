package ua.edu.ucu.tries;


import ua.edu.ucu.queue.Queue;
import ua.edu.ucu.queue.immutable.ImmutableLinkedList;

public class RWayTrie implements Trie {
    private static final int R = 256; // radix
    private final Node trieRoot = new Node(R);
    private int size = 0;
    private static class Node {
        public char c;
        public Object v = null;
        public int n;
        public Node[] next;

        Node(char c, int R) {
            this.c = c;
            next = new Node[R];
        }
        Node(int R) { next = new Node[R]; }

        public String toString() { return ""+c; }
    }

    @Override
    public void add(Tuple t) {
            trieRoot.next[t.term.charAt(0)] =
                    addHelper(trieRoot.next[t.term.charAt(0)], t.term, 0, t.weight);
        }

    private Node addHelper(Node node, String s, int i, Object value) {
        if (node == null) {
            Node n = new Node(s.charAt(i), R);
            node = n;
            size++;
        }
        if (i+1 == s.length()) { node.v = value; return node; }
        if (node.next[s.charAt(i+1)] == null) node.n++;
        node.next[s.charAt(i+1)] = addHelper(node.next[s.charAt(i+1)], s, i+1, value);
        return node;
    }

    @Override
    public boolean contains(String word) {
        return containsHelper(trieRoot.next[word.charAt(0)], word, 0);
    }

    private boolean containsHelper(Node node, String s, int i) {
        if (i<s.length() && node == null)return false;
        else if (i == s.length()-1) {
            if (node.v!=null) {
                System.out.println(node.v);
                return true;
            } else return false;
        } else return containsHelper(node.next[s.charAt(i+1)], s, i+1);
    }

    @Override
    public boolean delete(String word) {
        boolean r = deleteHelper(trieRoot.next[word.charAt(0)], word, 0);
        if (r && (--trieRoot.next[word.charAt(0)].n) == 0) {
            trieRoot.next[word.charAt(0)] = null;
            return true;
        } else return false;
    }

    private boolean deleteHelper(Node node, String s, int i) {
        if (i <s.length() && node == null) {
            return false;
        } else if (i == s.length()-1) {
            return node.n == 0;
        } else {
            boolean t = deleteHelper(node.next[s.charAt(i+1)], s, i+1);

            if (t && (--node.next[s.charAt(i+1)].n) == 0) {
                node.next[s.charAt(i+1)] = null;
                return true;
            } else return false;
        }
    }

    @Override
    public Iterable<String> words() {
        { return wordsWithPrefix(""); }
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pre) {
        Queue q = new Queue(new ImmutableLinkedList());
        collect(get(trieRoot, pre, 0), pre, q);
        return q;
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s, int k) {
        Queue q = new Queue(new ImmutableLinkedList());
        collect(get(trieRoot, s, 0), s, q);
        return q;
    }

    private void collect(Node x, String pre, Queue q) {
        if (x == null) return;
        if (x.v != null) q.enqueue(pre);
        for (char c = 0; c < R; c++)
            collect(x.next[c], pre + c, q);
    }

    private Node get(Node x, String key, int d)
    { // Return node associated with key in the sub trie rooted at x.
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d); // Use dth key char to identify sub trie.
        return get(x.next[c], key, d+1);
    }

    @Override
    public int size() {
        return size;
    }

}



