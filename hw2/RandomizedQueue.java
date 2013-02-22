import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size = 0;

    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();

        if (size == queue.length)
            resize(2 * queue.length);

        queue[size++] = item;
    }

    public Item dequeue() {
        verifyEmpty();

        int index = StdRandom.uniform(size);
        Item item = queue[index];

        if (index == size - 1) {
            queue[index] = null;
        } else {
            queue[index] = queue[size-1];
            queue[size-1] = null;
        }

        size -= 1;

        if (size > 0 && size == queue.length / 4)
            resize(queue.length / 2);

        return item;
    }

    public Item sample() {
        verifyEmpty();

        return queue[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void verifyEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    private void resize(int max) {
        assert max >= size;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private int[] indx;

        public RandomizedQueueIterator() {
            indx = new int[size];
            for (int j = 0; j < size; j++)
                indx[j] = j;
            StdRandom.shuffle(indx);
        }

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return queue[indx[i++]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int i = 0; i < 5; i++)
            rq.enqueue(i);

        for (int i = 0; i < 5; i++)
            System.out.println(rq.sample());
    }
}
