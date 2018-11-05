import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private WeightedQuickUnionUF site;
	private int size;
	private boolean[][] matrix;
	private int count; // count of open site
	private int extraPointInSite;

	private enum Site {
		BLOCK(false), OPEN(true);
		private final boolean value;

		private Site(boolean value) {
			this.value = value;
		}

		public boolean getValue() {
			return value;
		}
	}

	private int mapping(int row, int col) {
		return (row * size) + (col + 1);
	}

	private void validate(int p) {
		if (p <= 0 || p > size) {
			throw new IllegalArgumentException("index " + p + " is not between 1 and " + size);
		}
	}

	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		this.matrix = new boolean[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				this.matrix[i][j] = Site.BLOCK.getValue();
			}

		this.size = n;
		this.count = 0;

		this.site = new WeightedQuickUnionUF(n * n + 2);
		this.extraPointInSite = n * n + 1;
	}

	public void open(int row, int col) {
		this.validate(row);
		this.validate(col);

		int realRow = row - 1;
		int realCol = col - 1;

		if (this.matrix[realRow][realCol] != Site.BLOCK.getValue()) {
			return;
		}

		this.matrix[realRow][realCol] = Site.OPEN.getValue();
		count++;

		// row situation
		if (realRow == 0) { // first row
			this.site.union(0, this.mapping(realRow, realCol));

			if ((this.size > realRow + 1) && this.matrix[realRow + 1][realCol] != Site.BLOCK.getValue()) {
				this.site.union(this.mapping(realRow + 1, realCol), this.mapping(realRow, realCol));
			}

		}
		if (realRow == this.size - 1) { // last row
			this.site.union(this.extraPointInSite, this.mapping(realRow, realCol));

			if ((realRow - 1 >= 0) && this.matrix[realRow - 1][realCol] != Site.BLOCK.getValue()) {
				this.site.union(this.mapping(realRow, realCol), this.mapping(realRow - 1, realCol));
			}

		}
		if ((realRow != 0) && (realRow != this.size - 1)) {
			if (this.matrix[realRow - 1][realCol] != Site.BLOCK.getValue()) {
				this.site.union(this.mapping(realRow, realCol), this.mapping(realRow - 1, realCol));
			}

			if (this.matrix[realRow + 1][realCol] != Site.BLOCK.getValue()) {
				this.site.union(this.mapping(realRow + 1, realCol), this.mapping(realRow, realCol));
			}
		}

		// column situation
		if (realCol == 0) {
			if ((this.size > realCol + 1) && this.matrix[realRow][realCol + 1] != Site.BLOCK.getValue()) {
				this.site.union(this.mapping(realRow, realCol), this.mapping(realRow, realCol + 1));
			}
		} else if (realCol == this.size - 1) {
			if ((realCol - 1 >= 0) && this.matrix[realRow][realCol - 1] != Site.BLOCK.getValue()) {
				this.site.union(this.mapping(realRow, realCol), this.mapping(realRow, realCol - 1));
			}
		} else {
			if (this.matrix[realRow][realCol + 1] != Site.BLOCK.getValue()) {
				this.site.union(this.mapping(realRow, realCol), this.mapping(realRow, realCol + 1));
			}
			if (this.matrix[realRow][realCol - 1] != Site.BLOCK.getValue()) {
				this.site.union(this.mapping(realRow, realCol), this.mapping(realRow, realCol - 1));
			}
		}

	}

	public boolean isOpen(int row, int col) {
		this.validate(row);
		this.validate(col);

		return this.matrix[row - 1][col - 1] == Site.OPEN.getValue();
	}

	public boolean isFull(int row, int col) {
		this.validate(row);
		this.validate(col);

		int realRow = row - 1;
		int realCol = col - 1;
		return this.site.find(0) == this.site.find(this.mapping(realRow, realCol));
	}

	public int numberOfOpenSites() {
		return this.count;
	}

	public boolean percolates() {
		return this.site.find(0) == this.site.find(this.extraPointInSite);
	}

//	public void printMatrix() {
//
//		for (int i = 0; i < this.size; i++) {
//			for (int j = 0; j < this.size; j++) {
//
//				boolean status = this.matrix[i][j];
//				if (status == Site.BLOCK.getValue()) {
//					StdOut.print("B ");
//				} else {
//					if (this.site.connected(0, this.mapping(i, j))) {
//						StdOut.print("F ");
//					} else {
//						StdOut.printf("O ");
//					}
//				}
//
//			}
//			StdOut.println();
//		}
//
//	}

	public static void main(String[] args) {
		// int n = StdIn.readInt();
		// Percolation percolation = new Percolation(n);
		// while (!StdIn.isEmpty()) {
		// int p = StdIn.readInt();
		// int q = StdIn.readInt();
		// percolation.open(p, q);
		// }
		// System.out.println("------");
		// percolation.printMatrix();
		// StdOut.println("number of open sites:" +
		// percolation.numberOfOpenSites());
		// StdOut.println("is percolates:" + percolation.percolates());
	}

}
