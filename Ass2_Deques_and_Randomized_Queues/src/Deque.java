import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private int numbers;
	private Node first;
	private Node last;

	public Deque() {
	}

	public boolean isEmpty() {
		return numbers == 0;
		
		
	}

	public int size() {
		return this.numbers;
	}

	public void addFirst(Item item) {
		if (item == null)
			throw new IllegalArgumentException();

		this.numbers++;

		Node node = new Node();
		node.item = item;
		node.previous = null;
		node.next = this.first;

		if (this.numbers == 1) {
			this.first = node;
			this.last = this.first;
		} else {
			this.first.previous = node;
			this.first = node;
		}
	}

	public void addLast(Item item) {
		if (item == null)
			throw new IllegalArgumentException();

		this.numbers++;

		Node node = new Node();
		node.item = item;
		node.previous = this.last;
		node.next = null;

		if (this.numbers == 1) {
			this.last = node;
			this.first = this.last;
		} else {
			this.last.next = node;
			this.last = node;

		}
	}

	public Item removeFirst() {
		if (this.numbers == 0)
			throw new NoSuchElementException();

		this.numbers--;

		Item item = this.first.item;

		if (this.numbers == 0) {
			this.first = null;
			this.last = null;
		} else {
			this.first = this.first.next;
			this.first.previous = null;
		}

		return item;
	}

	public Item removeLast() {
		if (this.numbers == 0)
			throw new NoSuchElementException();

		this.numbers--;

		Item item = this.last.item;

		if (this.numbers == 0) {
			this.first = null;
			this.last = null;
		} else {
			this.last = this.last.previous;
			this.last.next = null;
		}

		return item;
	}

	@Override
	public Iterator<Item> iterator() {
		// return an iterator over items in order from front to end
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private int index = numbers;
		private Node next = first;

		@Override
		public boolean hasNext() {
			return index > 0;
		}

		@Override
		public Item next() {
			if (this.next == null)
				throw new NoSuchElementException();

			this.index--;
			Node node = this.next;
			this.next = this.next.next;
			return node.item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private class Node {
		Item item;
		Node next;
		Node previous;
	}

	public static void main(String[] args) {
//
//		Deque<Integer> deque = new Deque<>();
//		deque.addLast(4);
//		deque.addLast(3);	
//		deque.addLast(2);	
//		deque.addLast(1);	
//
//		deque.removeFirst();
//		deque.removeFirst();
////		deque.removeLast();
//
//		for(Integer integer: deque){
//			System.out.println(integer);
//		}

	}
}
