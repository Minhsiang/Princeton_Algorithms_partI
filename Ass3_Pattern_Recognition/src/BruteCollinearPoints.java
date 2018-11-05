import java.util.Arrays;

import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

	private LineSegment[] lineSegment;
	private int numOfLineSegment;

	public BruteCollinearPoints(Point[] points) {

		// finds all line segments containing 4 points

		// Corner cases. Throw a java.lang.IllegalArgumentException if the
		// argument to the constructor is null, if any point in the array is
		// null, or if the argument to the constructor contains a repeated
		// point.
		if (points == null) {
			throw new IllegalArgumentException();
		}
		for (Point point : points) {
			if (point == null)
				throw new IllegalArgumentException();
		}
		Merge.sort(points);
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i].equals(points[i + 1])) {
				throw new IllegalArgumentException();
			}
		}

		this.lineSegment = new LineSegment[0];

		for (int i = 0; i < points.length - 3; i++)
			for (int j = i + 1; j < points.length - 2; j++)
				for (int k = j + 1; k < points.length - 1; k++)
					for (int l = k + 1; l < points.length; l++) {

						double slopeWithJ = points[i].slopeTo(points[j]);
						double slopeWithK = points[i].slopeTo(points[k]);
						double slopeWithL = points[i].slopeTo(points[l]);
						if (slopeWithJ == slopeWithK && slopeWithJ == slopeWithL) {
							lineSegment = Arrays.copyOf(lineSegment, lineSegment.length + 1);
							this.lineSegment[numOfLineSegment++] = new LineSegment(points[i], points[l]);
						}

					}
	}

	public int numberOfSegments() {
		return this.numOfLineSegment;
	}

	public LineSegment[] segments() {
		return this.lineSegment;
	}

	public static void main(String[] args) {

		// // read the n points from a file
		//// In in = new In(args[0]);
		//
		// int n = StdIn.readInt();
		// Point[] points = new Point[n];
		// for (int i = 0; i < n; i++) {
		// int x = StdIn.readInt();
		// int y = StdIn.readInt();
		// points[i] = new Point(x, y);
		// }
		//
		// // draw the points
		//
		// StdDraw.enableDoubleBuffering();
		// StdDraw.setXscale(0, 32768);
		// StdDraw.setYscale(0, 32768);
		// StdDraw.setPenRadius(0.008);
		// StdDraw.setPenColor(StdDraw.BLUE);
		// for (Point p : points) {
		// p.draw();
		// }
		// StdDraw.show();
		//
		// // print and draw the line segments
		// StdDraw.setPenRadius(0.002);
		// StdDraw.setPenColor(StdDraw.RED);
		// BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		// System.out.println("number of line
		// segment:"+collinear.numOfLineSegment);
		//
		// for (LineSegment segment : collinear.segments()) {
		// StdOut.println(segment);
		// segment.draw();
		// }
		//
		// StdDraw.show();
	}
}