import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private final int size;
	private final int trials;


	private final double[] resultOfTrials;

	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid

		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("n or trials must be large than zero");
		}
		this.size = n;
		this.trials = trials;
		resultOfTrials = new double[trials];

		int numOfMatrix = n * n;

		for (int i = 0; i < trials; i++) {
			resultOfTrials[i] = this.trial() / numOfMatrix;
		}
	}

	private double trial() {
		Percolation percolation = new Percolation(this.size);

		while (!percolation.percolates()) {
			int randomRow = StdRandom.uniform(1, this.size + 1);
			int randomCol = StdRandom.uniform(1, this.size + 1);
			percolation.open(randomRow, randomCol);
		}
		return percolation.numberOfOpenSites();
	}

	public double mean() {
		return StdStats.mean(resultOfTrials);
	}

	public double stddev() {
		return StdStats.stddev(resultOfTrials);
	}

	public double confidenceLo() {
		return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(this.trials));
	}

	public double confidenceHi() {
		return this.mean() +  ((1.96 * this.stddev()) / Math.sqrt(this.trials));
	}

	public static void main(String[] args) {
		
//		int n = StdIn.readInt();
//		int trials = StdIn.readInt();
//
//		PercolationStats percolationStats = new PercolationStats(n, trials);
//		System.out.println("mean:" + percolationStats.mean());
//		System.out.println("stddev:" + percolationStats.stddev());
//		System.out.println("condifenceLo:" + percolationStats.confidenceLo());
//		System.out.println("condifenceHi:" + percolationStats.confidenceHi());
	}
}