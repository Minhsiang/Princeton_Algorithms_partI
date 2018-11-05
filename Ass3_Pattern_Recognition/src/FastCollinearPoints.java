import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

	private List<LineSegment> lineSegment;
	private Point[] points;

	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
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
		this.points = Arrays.copyOf(points, points.length);
		this.lineSegment = new ArrayList<>();

		// System.out.println("Array:" + Arrays.toString(this.points));

		for (int i = 0; i < this.points.length - 3; i++) {

			Point[] copyOfRange = Arrays.copyOfRange(this.points, i + 1, this.points.length);
			Arrays.sort(copyOfRange, this.points[i].slopeOrder());

			// System.out.println("--------------");
			// System.out.println("points[i]:" + points[i].toString());
			// System.out.println("copyOfRange:(" + copyOfRange.length + ")" +
			// Arrays.toString(copyOfRange));

			int numOfSamePointSlope = 0;
			Point temp = null;
			double firstSlope = 0.0;
			double secondSlope = 0.0;

			for (int index = 0; index < copyOfRange.length; index++) {
				if (temp == null) {
					temp = copyOfRange[index];
					numOfSamePointSlope = 1;
					continue;
				}

				firstSlope = this.points[i].slopeTo(temp);
				secondSlope = this.points[i].slopeTo(copyOfRange[index]);

				if (firstSlope == secondSlope) {
					temp = copyOfRange[index];
					numOfSamePointSlope++;
				} else if (numOfSamePointSlope >= 3 && !this.isSubSegments(i, this.points[i], firstSlope)) {
					this.lineSegment.add(new LineSegment(this.points[i], temp));
					numOfSamePointSlope = 0;
					temp = null;
				} else {
					temp = copyOfRange[index];
					numOfSamePointSlope = 1;
				}
			}
			if (numOfSamePointSlope >= 3 && !this.isSubSegments(i, this.points[i], firstSlope)) {
				this.lineSegment.add(new LineSegment(this.points[i], temp));
			}

		}

	}

	private boolean isSubSegments(int endIndex, Point checkPoint, Double checkSlope) {
		Double tempSlope = 0.0;


		for (int i = 0; i < endIndex; i++) {
			tempSlope = this.points[i].slopeTo(checkPoint);
			if (tempSlope.equals(checkSlope)) {
				return true;
			}
		}
		return false;
	}

	public int numberOfSegments() {
		return this.lineSegment.size();
	}

	public LineSegment[] segments() {
		LineSegment[] result = (LineSegment[]) this.lineSegment.toArray(new LineSegment[this.lineSegment.size()]);
		return result;
	}

	public static void main(String[] args) {

		// read the n points from a file
		// In in = new In(args[0]);

		int n = StdIn.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = StdIn.readInt();
			int y = StdIn.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(-250, 32768);
		StdDraw.setYscale(-250, 32768);
		StdDraw.setPenRadius(0.008);
		StdDraw.setPenColor(StdDraw.BLUE);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		StdDraw.setPenRadius(0.002);
		StdDraw.setPenColor(StdDraw.RED);
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		System.out.println("number of line segment:" + collinear.numberOfSegments());

		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}

		StdDraw.show();
	}
}