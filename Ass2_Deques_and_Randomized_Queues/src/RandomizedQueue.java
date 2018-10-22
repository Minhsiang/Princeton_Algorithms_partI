import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] array;
	private int numbers;

	private int head = -1;
	private int tail;

	public RandomizedQueue() {
		this.array = (Item[]) new Object[2];
	}

	public boolean isEmpty() {
		return this.numbers == 0;
	}

	public int size() {
		return this.numbers;
	}

	public void enqueue(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		this.numbers++;

		if (this.head < 0)
			this.head = 0;

		this.array[this.tail] = item;

		// tail index overflow
		if (this.tail + 1 == this.array.length) {
			this.tail = 0;
		} else {
			this.tail++;
		}

		// double up array
		if (this.numbers + 1 == this.array.length)
			this.resize(this.array.length * 2);

	}

	// not finish?? shrink array
	public Item dequeue() {
		if (this.numbers == 0 || this.head < 0) {
			throw new NoSuchElementException();
		}
		this.numbers--;

		Item item = this.array[this.head];
		this.array[this.head] = null;

		// head index overflow
		if (this.head + 1 == this.array.length) {
			if (this.numbers == 0)
				this.head = -1;
			else
				this.head = 0;
		} else {
			if (this.numbers == 0) {
				this.head = -1;
				this.tail = 0;
			} else {
				this.head++;
			}
		}

		// shrink array
		if (this.numbers > 0 && this.numbers == (this.array.length / 4))
			this.resize(this.array.length / 2);

		return item;
	}

	public Item sample() {
		if (this.numbers == 0)
			throw new NoSuchElementException();

		return this.array[(this.head + StdRandom.uniform(this.numbers)) % array.length];
	}

	private void resize(int newSize) {
		Item[] copy = (Item[]) new Object[newSize];

		int index = 0;
		if (head <= tail) {
			for (int i = head; i < tail; i++) {
				copy[index++] = this.array[i];
			}
		} else {
			for (int i = head; i < array.length; i++) {
				copy[index++] = this.array[i];
			}
			for (int j = 0; j <= tail; j++) {
				copy[index++] = this.array[j];
			}
		}
		this.array = copy;
		this.head = 0;
		this.tail = this.numbers;
	}

	// @Override
	// public String toString() {
	// StringBuilder builder = new StringBuilder();
	// builder.append("length:" + this.array.length);
	// builder.append(" numbers:" + this.numbers);
	// builder.append(" head:" + this.head);
	// builder.append(" tail:" + this.tail);
	// builder.append("[");
	//
	// for (int i = 0; i < this.array.length; i++) {
	// if (this.array[i] == null) {
	// if (i != this.array.length - 1)
	// builder.append(',');
	// } else {
	// builder.append(this.array[i]);
	// if (i != this.array.length - 1)
	// builder.append(',');
	// }
	// }
	//
	// builder.append("]");
	// return builder.toString();
	// }

	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {

		private int index = numbers;
		private final int[] sequenceArray = StdRandom.permutation(numbers);

		@Override
		public boolean hasNext() {
			return index > 0;
		}

		@Override
		public Item next() {
			if (index == 0)
				throw new NoSuchElementException();

			return array[head + sequenceArray[--this.index]];
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public static void main(String[] args) {
		// RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		// System.out.println(rq);
		//
		// rq.enqueue(1);
		// rq.dequeue();
		// System.out.println(rq);
		//
		// rq.enqueue(2);
		// rq.enqueue(3);
		// rq.enqueue(4);
		// rq.enqueue(5);
		// rq.enqueue(6);
		// rq.dequeue();
		// rq.dequeue();
		// rq.dequeue();
		// rq.dequeue();
		// rq.dequeue();
		//
		// System.out.println(rq);
		//
		// rq.dequeue();
		// rq.dequeue();
		// System.out.println(rq);
		// rq.enqueue(7);
		// rq.enqueue(8);
		// rq.dequeue();
		// rq.enqueue(9);
		// System.out.println(rq);
		// rq.enqueue(10);
		// System.out.println(rq);
		// rq.enqueue(11);
		// System.out.println(rq);

		// for(Integer integer: rq)
		// System.out.println(integer);
		// System.out.println("------");
		// for(Integer integer: rq)
		// System.out.println(integer);

	}
}
