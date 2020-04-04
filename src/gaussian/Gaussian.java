package gaussian;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;


/**
 * 
 *  Gaussian, In image processing, a Gaussian blur (also known as Gaussian smoothing) 
 *  is the result of blurring an image by a Gaussian function
 *  
 *  Found in Java Image Filters, just create an interface to use this 
 *  filters tests, http://www.jhlabs.com/ip/filters/
 * 
 * 
 *  @author edson
 *
 */
public class Gaussian extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1523329478849673875L;
	
	private static String pictureToTransform = "images/folle.jpg";

	public static void main(String[] args) throws IOException {

		// Read an image.
		BufferedImage input = ImageIO.read(new File(pictureToTransform));
		
		// Read an image.
	    BufferedImage output = ImageIO.read(new File(pictureToTransform));
		
		GaussianFilter gaussian = new GaussianFilter();

		BufferedImage imageGaussian = gaussian.filter(input, output);

		DisplayJAI jai = new DisplayJAI(imageGaussian);
		JScrollPane pane = new JScrollPane(jai);

		DisplayJAI jaiN = new DisplayJAI(input);
		JScrollPane paneN = new JScrollPane(jaiN);

		// Use a label to display the image
		JFrame frame = new JFrame();
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		frame.pack();
		frame.setTitle("GAUSSIAN");
		frame.setVisible(true);

		JFrame frame1 = new JFrame();
		frame1.getContentPane().add(paneN, BorderLayout.CENTER);
		frame1.pack();
		frame1.setTitle("ORIGINAL PICTURE");
		frame1.setVisible(true);

	}
}
