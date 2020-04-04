package kmeanstest;

/**
 * Some tests that I did in the past I believe I use some 
 * examples from Programming in Java Advanced Imaging, I used a paper 
 * in the past, in 2005 I believe, I found these links today
 *
 * https://docs.oracle.com/cd/E19957-01/806-5413-10/806-5413-10.pdf
 * 
 * http://seer.ufrgs.br/rita/article/viewFile/rita_v11_n1_p93-124/3555
 * 
 * This application renders a color image on a pure black-and-white image created from scratch.
 * 
 * @author edson
 */
public interface MultiDimensionComparable<T> {
        /**
         * Compares Multi-Dimensional Types in one dimension
         * @param other
         * @param dim
         * @return -1 if this < other, 0 if this = other, 1 if this > other
         */
        public int compareToDimension(T other, int dim);
        
}
