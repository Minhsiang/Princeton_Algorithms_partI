import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

	private final Set<Point2D> pointSet;

	public PointSET() {
		// construct an empty set of points
		this.pointSet = new TreeSet<>();
	}

	public boolean isEmpty() {
		return this.pointSet.isEmpty();
	}

	public int size() {
		return this.pointSet.size();
	}

	public void insert(Point2D p) {

		this.pointSet.add(p);
	}

	public boolean contains(Point2D p) {
		return this.pointSet.contains(p);
	}

	public void draw() {
		// draw all points to standard draw
		Iterator<Point2D> iterator = this.pointSet.iterator();
		while (iterator.hasNext()) {
			Point2D point = iterator.next();
			point.draw();
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle (or on the boundary)
		Queue<Point2D> queue = new Queue<Point2D>();

		Iterator<Point2D> iterator = this.pointSet.iterator();
		while (iterator.hasNext()) {
			Point2D point = iterator.next();
			if (rect.contains(point)) {
				queue.enqueue(point);
			}
		}
		return queue;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		Iterator<Point2D> iterator = this.pointSet.iterator();
		Point2D nearest = null;
		double nearestDistance = Double.MAX_VALUE;
		while (iterator.hasNext()) {
			Point2D point = iterator.next();
			double pointDistance = point.distanceSquaredTo(p);

			if (pointDistance < nearestDistance) {
				nearest = point;
				nearestDistance = pointDistance;
			}
		}
		return nearest;
	}

	public static void main(String[] args) {

		// initialize the two data structures with point from file
		String filename = "C:\\Users\\minhsiang0617\\Desktop\\Algorithm\\kd tree\\kdtree\\horizontal8.txt";
		In in = new In(filename);
		PointSET brute = new PointSET();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			brute.insert(p);
		}

		// process nearest neighbor queries
//		StdDraw.enableDoubleBuffering();
//		while (true) {
//
//			// the location (x, y) of the mouse
//			double x = StdDraw.mouseX();
//			double y = StdDraw.mouseY();
//			// double x = 0.5;
//			// double y = 0.4;
//			Point2D query = new Point2D(x, y);
//
//			// draw all of the points
//			StdDraw.clear();
//			StdDraw.setPenColor(StdDraw.BLACK);
//			StdDraw.setPenRadius(0.01);
//			brute.draw();
//
//			// draw in red the nearest neighbor (using brute-force algorithm)
//			StdDraw.setPenRadius(0.03);
//			StdDraw.setPenColor(StdDraw.RED);
//			brute.nearest(query).draw();
//			StdDraw.setPenRadius(0.02);
//
//			StdDraw.show();
//			StdDraw.pause(40);
//		}

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();

        // process range search queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x0 = x1 = StdDraw.mouseX();
                y0 = y1 = StdDraw.mouseY();
                isDragging = true;
            }

            // user is dragging a rectangle
            else if (StdDraw.isMousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
            }

            // user stops dragging rectangle
            else if (!StdDraw.isMousePressed() && isDragging) {
                isDragging = false;
            }

            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw the rectangle
            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                     Math.max(x0, x1), Math.max(y0, y1));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();


            StdDraw.show();
            StdDraw.pause(20);		
        }

	}

}