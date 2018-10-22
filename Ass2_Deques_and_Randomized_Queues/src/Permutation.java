import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {

	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<>();

		int times = Integer.parseInt(args[0]);

//		String[] readAllStrings = StdIn.readAllStrings();
//		for(String str: readAllStrings)
//			System.out.println();
		
		
		while (!StdIn.isEmpty()) {
			String str = StdIn.readString();
			rq.enqueue(str);
		}
		
		Iterator<String> iterator = rq.iterator();
		while (iterator.hasNext() && times > 0) {
			System.out.println(iterator.next());
			times--;
		}
	}

}
