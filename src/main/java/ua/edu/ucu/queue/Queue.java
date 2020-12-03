package ua.edu.ucu.queue;

import ua.edu.ucu.queue.immutable.ImmutableLinkedList;

import java.util.ArrayList;
import java.util.Iterator;

public class Queue implements Iterable<String>{
    private ImmutableLinkedList queue;

    public Queue(ImmutableLinkedList queue)
    {
        this.queue = queue;
    }

    public ImmutableLinkedList getQueue()
    {
        return this.queue;
    }

    public Object peek()
    {
        return queue.getFirst();
    }

    public Object dequeue()
    {
        Object first = queue.getFirst();
        queue = queue.removeFirst();
        return first;
    }

    public void enqueue(Object obj)
    {
        queue = queue.addLast(obj);
    }

    @Override
    public Iterator<String> iterator() {
        ArrayList<String> toIter = new ArrayList<>();
        while (this.queue.size() != 0) {
            toIter.add((String) dequeue());
        }
        return toIter.iterator();
    }
}