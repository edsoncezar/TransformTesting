package kmeanstest;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import weka.core.matrix.Matrix;

/**
 * 
 * A program that I made a long time ago, following a tutorial that I tried
 * however couldn't found now
 * 
 * K-means clustering is a type of unsupervised learning, which is used when you
 * have unlabeled data , the goal of this algorithm is to find groups in the
 * data, with the number of groups represented by the variable K. The algorithm
 * works iteratively to assign each data point to one of K groups based on the
 * features that are provided. Data points are clustered based on feature
 * similarity. The results of the K-means clustering algorithm are:
 * 
 * The centroids of the K clusters, which can be used to label new data
 * 
 * Labels for the training data (each data point is assigned to a single
 * cluster)
 * 
 * @author edson
 *
 */
public class Kmeans {

	/**
	 * Sorted List of pixels in a picture.
	 */
	private ArrayList<Point> pixels = new ArrayList<Point>();

	/**
	 * kmeans
	 */
	private int numberOfColorsKmeans;

	/**
	 * clusters
	 */
	private Cluster[] clusters;

	/**
	 * image
	 */
	private BufferedImage image = null;

	/**
	 *
	 */
	public Kmeans(int kmeans, BufferedImage imageKmeans) {
		numberOfColorsKmeans = kmeans;
		image = imageKmeans;
		for (int x = 0; x < imageKmeans.getWidth(); x++) {
			for (int y = 0; y < imageKmeans.getHeight(); y++) {
				int color = imageKmeans.getRGB(x, y);
				int blue, green, red;
				blue = color & 0xFF;
				green = (color >> 8) & 0xFF;
				red = (color >> 16) & 0xFF;
				double[][] colorOfArray = { { red, green, blue } };
				pixels.add(new Point(new Matrix(colorOfArray), x, y));
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String[] files = { "images/rider.jpg", "images/honey.jpg" };

		for (String filestring : files) {

			BufferedImage imageOne = ImageIO.read(new File(filestring));
			BufferedImage imageTwo = ImageIO.read(new File(filestring));

			Kmeans kmeans = new Kmeans(6, imageOne);

			ImageIcon imageDisplayOne = new ImageIcon(kmeans.getSegmentation());
			ImageIcon imageDisplayTwo = new ImageIcon(imageTwo);

			// Use a label to display the image
			JFrame frame = new JFrame(filestring);
			JLabel labelOne = new JLabel(imageDisplayOne);
			JLabel labelTwo = new JLabel(imageDisplayTwo);

			frame.getContentPane().add(labelOne, BorderLayout.EAST);
			frame.getContentPane().add(labelTwo, BorderLayout.WEST);
			frame.pack();
			frame.setVisible(true);
		}
	}

	public BufferedImage getSegmentation() {
		doKMeans();

		System.out.println("After the kmeans algorithm");
		System.out.println("--------------------->");

		return paint();
	}

	public ArrayList<Point> getPoints() {
		doKMeans();
		ArrayList<Point> points = new ArrayList<Point>();
		for (Cluster cluster : clusters) {
			points.addAll(cluster.getMembers());
		}
		return points;
	}

	/**
	 * @return
	 */
	private BufferedImage paint() {
		for (Cluster c : clusters) {
			for (Point p : c.getMembers()) {
				image.setRGB(p.getX(), p.getY(), c.getRGB() < 0 ? 0 : c.getRGB());
			}
		}
		return image;
	}

	/**
	 *
	 */
	private void doKMeans() {

		Matrix[] matrixKmeans = createColors();
		clusters = new Cluster[numberOfColorsKmeans];

		System.out.println("Before the kmeans algorithm");

		for (int iterations = 0; iterations < 1; iterations++) {
			System.out.println("--------------------->");

			for (int i = 0; i < matrixKmeans.length; i++) {
				clusters[i] = new Cluster(matrixKmeans[i]);
			}

			for (Point point : pixels) {
				addToNearestCluster(point);
			}
		}
	}

	private Matrix[] createColors() {
		Matrix[] matrixKmeans = new Matrix[numberOfColorsKmeans];

		double[][] ball = { { 255, 0, 0 } };
		matrixKmeans[0] = new Matrix(ball);

		double[][] green = { { 0, 255, 0 } };
		matrixKmeans[1] = new Matrix(green);

		double[][] blue = { { 0, 0, 255 } };
		matrixKmeans[2] = new Matrix(blue);

		double[][] yellow = { { 255, 255, 0 } };
		matrixKmeans[3] = new Matrix(yellow);

		double[][] white = { { 255, 255, 255 } };
		matrixKmeans[4] = new Matrix(white);

		double[][] black = { { -50, -100, -50 } };
		matrixKmeans[5] = new Matrix(black);

		return matrixKmeans;

	}

	/**
	 * 
	 * @param point
	 */
	private void addToNearestCluster(Point point) {
		double entfernung = Double.MAX_VALUE;
		Cluster temp = null;

		for (Cluster cluster : clusters) {
			double distance = point.getRgb().minus(cluster.getMatrix()).normF();
			if (distance < entfernung) {
				entfernung = distance;
				temp = cluster;
			}
		}
		temp.addMember(point);
	}

	public ArrayList<Point> getPixels() {
		return pixels;
	}

	public void setPixels(ArrayList<Point> pixels) {
		this.pixels = pixels;
	}

	public int getNumberOfColorsKmeans() {
		return numberOfColorsKmeans;
	}

	public void setNumberOfColorsKmeans(int numberOfColorsKmeans) {
		this.numberOfColorsKmeans = numberOfColorsKmeans;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
