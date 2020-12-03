package ua.edu.ucu.queue.immutable;


public class ImmutableLinkedList implements ImmutableList{
    private final Node head;
    private final Object[] nodes;
    private final int size;

    public ImmutableLinkedList(Object[] objects) {
        this.head = new Node(null);
        Node curNode = head;
        for (Object obj: objects) {
            curNode.data = obj;
            curNode.next = new Node(null);
            curNode = curNode.next;
        }
        this.size = objects.length;
        this.nodes = objects;
    }

    public ImmutableLinkedList() {
        this.head = new Node(null);
        this.nodes = new Object[0];
        this.size = 0;
    }

    public void checkIndex(int index) {
        if (index - 1 > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(size, e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        return addAll(index, new Object[] {e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        checkIndex(index);
        int newSize = size + c.length;
        Object[] newObjects = new Object[newSize];

        int i = 0;
        for (; i < index; i++) {
            newObjects[i] = nodes[i];
        }
        int temp = index + c.length;
        for (;i < temp; i++) {
            newObjects[i] = c[i - index];
        }
        for (; i < newSize; i++){
            newObjects[i] = nodes[i-c.length];
        }
        return new ImmutableLinkedList(newObjects);
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        Node curNode = head;
        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }
        return curNode.data;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        checkIndex(index);
        Node curNode = head;
        int newSize = size - 1;
        Object[] newObjects = new Object[newSize];
        int i = 0;
        for (; i < index; i++) {
            curNode = curNode.next;
            newObjects[i] = nodes[i];
        }
        curNode.next = curNode.next.next;

        for (; i < newSize; i++) {
            newObjects[i] = nodes[i+1];
        }

        return new ImmutableLinkedList(newObjects);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        checkIndex(index);
        nodes[index] = e;
        Node curNode = head;
        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }
        curNode.data = e;
        return new ImmutableLinkedList(nodes);
    }

    @Override
    public int indexOf(Object e) {
        Node curNode = head;
        for (int i = 0; i < size; i++) {
            if (curNode.data.equals(e)) {
                return i;
            }
            curNode = curNode.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList(new Object[0]);
    }

    @Override
    public boolean isEmpty() {
        return (head.data == null);
    }

    @Override
    public Object[] toArray() { return nodes; }

    public ImmutableLinkedList addFirst(Object e) { return add(0, e); }

    public ImmutableLinkedList addLast(Object e) {
        return add(size, e);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(size - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }
    public ImmutableLinkedList removeLast() {
        return remove(size - 1);
    }

}
