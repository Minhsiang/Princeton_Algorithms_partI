import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

	private class Node implements Comparable<Node> {
		private final Point2D point;
		private RectHV rect;
		private Node left, right;
		private int level; // root is in level 1

		public RectHV getRect() {
			return rect;
		}

		public void setRect(RectHV rect) {
			this.rect = rect;
		}

		public Node(Point2D point) {
			this.point = point;
		}

		public Point2D getPoint() {
			return this.point;
		}

		public Node getLeft() {
			return left;
		}

		public Node getRight() {
			return right;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		@Override
		public int compareTo(Node o) {

			if (this.level % 2 != 0) { // Vertical
				return this.point.x() > o.point.x() ? -1 : 1;
			} else { // Horizontal
				return this.point.y() > o.point.y() ? -1 : 1;
			}
		}

		@Override
		public String toString() {

			String leftNode = this.left == null ? "null" : "left-child";
			String rightNode = this.right == null ? "null" : "right-child";
			;

			return "Node [point=" + point.x() + ":" + point.y() + ", left=" + leftNode + ", right=" + rightNode
					+ ", level=" + level + "]";
		}

	}
	///////////////////////////////////

	private Node root;
	private int size = 0;

	public KdTree() {
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int size() {
		return this.size;
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException("call insert() with a null key");

		Node x = new Node(p);

		if (this.root == null) {
			this.root = x;
			this.root.setLevel(1);
			this.root.setRect(new RectHV(0.0, 0.0, 1.0, 1.0));
			this.size++;
			return;
		}

		boolean isLeft = false;
		Node parent = this.root;
		Node current = this.root;

		while (current != null) {
			parent = current;
			if (current.getPoint().equals(x.getPoint()))
				return;

			if (current.compareTo(x) < 0) { // left child
				current = current.left;
				isLeft = true;
			} else { // right child
				current = current.right;
				isLeft = false;
			}
		}

		double xMin, yMin, xMax, yMax;

		if (parent.getLevel() % 2 != 0) { // Vertical
			if (isLeft) {
				xMin = parent.getRect().xmin();
				yMin = parent.getRect().ymin();
				xMax = parent.getPoint().x();
				yMax = parent.getRect().ymax();
			} else {
				xMin = parent.getPoint().x();
				yMin = parent.getRect().ymin();
				xMax = parent.getRect().xmax();
				yMax = parent.getRect().ymax();
			}

		} else { // Horizontal
			if (isLeft) {
				xMin = parent.getRect().xmin();
				yMin = parent.getRect().ymin();
				xMax = parent.getRect().xmax();
				yMax = parent.getPoint().y();
			} else {
				xMin = parent.getRect().xmin();
				yMin = parent.getPoint().y();
				xMax = parent.getRect().xmax();
				yMax = parent.getRect().ymax();
			}
		}

		if (isLeft) {
			parent.left = x;
		} else {
			parent.right = x;
		}
		x.setLevel(parent.getLevel() + 1);
		x.setRect(new RectHV(xMin, yMin, xMax, yMax));
		this.size++;

	}

	public boolean contains(Point2D p) {
		if (this.root == null)
			return false;
		
		if(p == null) 
			throw new IllegalArgumentException();
			

		Node node = new Node(p);
		Node current = root;
		while (current != null) {
			if (current.getPoint().equals(node.getPoint()))
				return true;

			if (current.compareTo(node) <= 0) { // left child
				current = current.left;
			} else { // right child
				current = current.right;
			}
		}
		return false;
	}

	public void draw() {
		Stack<Node> stack = new Stack<>();
		Node node = null;

		if (this.root != null) {
			stack.push(root);
		}
		while (!stack.isEmpty()) {
			node = stack.pop();
			node.getPoint().draw();

			if (node.getRight() != null)
				stack.push(node.getRight());
			if (node.getLeft() != null)
				stack.push(node.getLeft());
		}
	}

	public Iterable<Point2D> range(RectHV rect) {

		if(rect == null) {
			throw new IllegalArgumentException();
		}
		Queue<Point2D> queue = new Queue<Point2D>();
		Stack<Node> stack = new Stack<>();
		Node node = null;

		if(this.root != null)
			stack.push(root);
		
		while (!stack.isEmpty()) {
			node = stack.pop();

			if (node.getRect().intersects(rect)) {
				if (rect.contains(node.getPoint())) {
					queue.enqueue(node.getPoint());
				}
				if (node.getRight() != null)
					stack.push(node.getRight());
				if (node.getLeft() != null)
					stack.push(node.getLeft());
			}
		}
		return queue;
	}

	private Node findTheContraryChild(Node championNode, Node queryNode) {
		if (championNode.getLevel() % 2 != 0) { // Vertical
			if (queryNode.getPoint().x() < championNode.getPoint().x()) {
				return championNode.right;
			} else {
				return championNode.left;
			}
		} else { // Horizontal
			if (queryNode.getPoint().y() < championNode.getPoint().y()) {
				return championNode.right;
			} else {
				return championNode.left;
			}
		}
	}

	public Point2D nearest(Point2D p) {

		if (this.root == null)
			return null;
		
		if(p == null) 
			throw new IllegalArgumentException();
		

		Node queryNode = new Node(p);
		Node championNode = this.root;
		Node current = this.root;
		Stack<Node> stack = new Stack<>();

		double championDistance = queryNode.getPoint().distanceSquaredTo(championNode.getPoint());
		double currentDistance;
		stack.push(this.findTheContraryChild(root, queryNode));
		stack.push(root);
		do {
			current = stack.pop();

			if (current != null && !current.getRect().contains(queryNode.getPoint())) {
				if (current.getRect().distanceSquaredTo(queryNode.getPoint()) >= championDistance)
					continue;
			}

			while (current != null) {
				currentDistance = queryNode.getPoint().distanceSquaredTo(current.getPoint());
				if (currentDistance < championDistance) {
					championNode = current;
					championDistance = currentDistance;
				}

				if (current.getRect().distanceSquaredTo(queryNode.getPoint()) < championDistance) {
					stack.push(this.findTheContraryChild(current, queryNode));
				}

				if (current.compareTo(queryNode) < 0) { // left child
					current = current.left;
				} else { // right child
					current = current.right;
				}
			}
		} while (!stack.isEmpty());
		return championNode.getPoint();
	}

	public static void main(String[] args) {
		// initialize the two data structures with point from file
		String filename = "C:\\Users\\minhsiang0617\\Desktop\\Algorithm\\kd tree\\kdtree\\input10.txt";
		In in = new In(filename);
		KdTree kdTree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdTree.insert(p);
		}

		// process nearest neighbor queries
		StdDraw.enableDoubleBuffering();
		while (true) {

			// the location (x, y) of the mouse
			double x = StdDraw.mouseX();
			double y = StdDraw.mouseY();
			Point2D query = new Point2D(x, y);

			// draw all of the points
			StdDraw.clear();
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.01);
			kdTree.draw();

			// draw in blue the nearest neighbor (using kd-tree algorithm)
			StdDraw.setPenRadius(0.03);
			StdDraw.setPenColor(StdDraw.BLUE);
			kdTree.nearest(query).draw();

			StdDraw.show();
			StdDraw.pause(40);
		}
	}
}