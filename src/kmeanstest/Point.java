package kmeanstest;

import weka.core.matrix.Matrix;

/**
 * @author schorsch
 *
 */
public class Point implements kmeanstest.MultiDimensionComparable<Point>{
        private int x;
        private int y;
        private Matrix rgb;

        /**
         * @param x
         * @param y
         * @param rgb
         */
        public Point(Matrix rgb, int x, int y) {
                super();
                this.x = x;
                this.y = y;
                this.rgb = rgb;
        }
        /**
         * @return the rgb
         */
        public Matrix getRgb() {
                return rgb;
        }
        /**
         * @param rgb the rgb to set
         */
        public void setRgb(Matrix rgb) {
                this.rgb = rgb;
        }
        /**
         * @return the x
         */
        public int getX() {
                return x;
        }
        /**
         * @return the y
         */
        public int getY() {
                return y;
        }
        /* (non-Javadoc)
         * @see us.kdTree.MultiDimensionComparable#compareToDimension(java.lang.Object, int)
         */
        public int compareToDimension(Point other, int dim) {
                return (int) (rgb.get(0,dim) - other.getRgb().get(0, dim));
        }
        /* (non-Javadoc)
         * @see us.kdTree.MultiDimensionComparable#getNumOfDimensions()
         */
        public int getNumOfDimensions() {
                return 3;
        }

        public String toString() {
                return "[r:"+rgb.get(0,0)+", g:"+rgb.get(0,1)+", b:"+rgb.get(0,2)+"; (" + x + ", " + y +")]";
        }

        public boolean equals(Object other) {
                if (!(other instanceof Point)) {
                        return false;
                }
                return hashCode() == other.hashCode();

        }

        public int hashCode() {
                int red = (int) rgb.get(0,0);
                int green = (int) rgb.get(0,1);
                int blue = (int) rgb.get(0,2);
                int rgb = (red << 16) + (green << 8) + blue;
                return rgb;
        }
}
