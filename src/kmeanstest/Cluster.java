package kmeanstest;

import java.util.ArrayList;

import weka.core.matrix.Matrix;

/**
 * 
 * Like I did it a long time ago using a tutorial and others sources I'm keeping
 * the original algorithm that I made, I really don't like or recommend use of
 * inner class (anonymous class)
 * 
 * @author edson
 *
 */
public class Cluster {

	private ArrayList<Point> members = new ArrayList<Point>();

	private Matrix matrix;

	/**
	 *
	 */
	public Cluster(Matrix matrix) {
		this.matrix = matrix;
	}

	public void addMember(Point member) {
		members.add(member);
	}

	/**
	 * @return the matrix
	 */
	public ArrayList<Point> getMembers() {
		return members;
	}

	/**
	 * @return the matrix
	 */
	public Matrix getNewMatrix() {
		Matrix newMatrix = new Matrix(1, 3);
		for (Point p : members) {
			newMatrix = newMatrix.plus(p.getRgb());
		}
		double[][] anz = { { (newMatrix.get(0, 0) / members.size()), (newMatrix.get(0, 1) / members.size()),
				(newMatrix.get(0, 2) / members.size()) } };
		newMatrix = new Matrix(anz);
		return newMatrix;
	}

	/**
	 * @return the matrix
	 */
	public Matrix getMatrix() {
		return matrix;
	}

	public int getRGB() {
		int red = (int) matrix.get(0, 0);
		int green = (int) matrix.get(0, 1);
		int blue = (int) matrix.get(0, 2);
		int rgb = (red << 16) + (green << 8) + blue;
		return rgb;

	}
}
