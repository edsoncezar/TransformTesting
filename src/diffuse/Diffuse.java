package diffuse;

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
 *  This filter diffuses an image by moving its pixels in random directions.
 *  
 *  Found in Java Image Filters, just create an interface to use this 
 *  filters tests, http://www.jhlabs.com/ip/filters/
 * 
 * 
 *  @author edson
 *
 */
public class Diffuse extends JPanel {

	private static String pictureToTransform = "images/mostri.jpg";
	
	private static String pictureToTransformTwo = "images/mostri.png";

	public static void main(String[] args) throws IOException {

		addPictureToTransform(pictureToTransform);
		
		addPictureToTransform(pictureToTransformTwo);

	}

	private static void addPictureToTransform(String picture) throws IOException {
		// Read an image.
		BufferedImage input = ImageIO.read(new File(picture));
		
		// Read an image.
	    BufferedImage output = ImageIO.read(new File(picture));
		
		DiffuseFilter diffuse = new DiffuseFilter();

		BufferedImage imageDiffuse = diffuse.filter(input, output);

		DisplayJAI jai = new DisplayJAI(imageDiffuse);
		JScrollPane pane = new JScrollPane(jai);

		DisplayJAI jaiN = new DisplayJAI(input);
		JScrollPane paneN = new JScrollPane(jaiN);

		showFrame(pane, paneN);
	}

	private static void showFrame(JScrollPane pane, JScrollPane paneN) {
		// Use a label to display the image
		JFrame frame = new JFrame();
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		frame.pack();
		frame.setTitle("DIFFUSE");
		frame.setVisible(true);

		JFrame frame1 = new JFrame();
		frame1.getContentPane().add(paneN, BorderLayout.CENTER);
		frame1.pack();
		frame1.setTitle("ORIGINAL PICTURE");
		frame1.setVisible(true);
	}
}
