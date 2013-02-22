import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        verifyItem(item);

        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = null;

        if (isEmpty()) {
            newNode.next = null;
            last = newNode;
        } else {
            newNode.next = first;
            first.prev = newNode;
        }

        first = newNode;

        size += 1;
    }
    
    public void addLast(Item item) {
        verifyItem(item);

        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;

        if (isEmpty()) {
            newNode.prev = null;
            first = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
        }

        last = newNode;

        size += 1;
    }

    public Item removeFirst() {
        verifyEmpty();

        Item result = first.item;

        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }

        size -= 1;

        return result;
    }

    public Item removeLast() {
        verifyEmpty();

        Item result = last.item;
        
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }

        size -= 1;

        return result;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void verifyItem(Item item) {
        if (item == null)
            throw new NullPointerException();
    }

    private void verifyEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
