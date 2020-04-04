package kmeanstest;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 
 * Refactoring to improve the understanding
 * 
 * @author edson
 *
 */
public class KmeansData {
	/**
	 * Sorted List of pixels in a picture.
	 */
	public ArrayList<Point> pixels;
	/**
	 * kmeans
	 */
	public int numberOfColorsKmeans;
	/**
	 * clusters
	 */
	public Cluster[] clusters;
	/**
	 * image
	 */
	public BufferedImage image;

	public KmeansData(ArrayList<Point> pixels, BufferedImage image) {
		this.pixels = pixels;
		this.image = image;
	}
}