package tritone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 *  Tritone, a technique that gives an ‘antique’ feel to your image.
 *  
 *  Found in Java Image Filters, just create an interface to use this 
 *  filters tests, http://www.jhlabs.com/ip/filters/
 * 
 * 
 *  @author edson
 *
 */
public class Tritone extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileName;

	public Tritone(String file) {

		fileName = file;
		setBackground(Color.white);

	}

	public void paint(Graphics g) {
		try {
			Graphics2D g2D;
			g2D = (Graphics2D) g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Read an image.
			BufferedImage input = ImageIO.read(new File(fileName));
			// Read an image.
			BufferedImage output = ImageIO.read(new File(fileName));

			TritoneFilter tritone = new TritoneFilter();

			BufferedImage imageTritone = tritone.filter(input, output);

			AffineTransform aTran = new AffineTransform();
			aTran.translate(50.0f, 20.0f);
			g2D.transform(aTran);
			g2D.drawImage(imageTritone, new AffineTransform(), this);
		} catch (Exception e) {
		}
	}

	public static void main(String s[]) {
		JFrame frame1 = new JFrame("STARLING");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame1.getContentPane().add("Center", new Tritone("images/startling.jpeg"));
		frame1.pack();
		frame1.setSize(new Dimension(700, 500));
		frame1.setVisible(true);
		
		JFrame frame2 = new JFrame("STARLING 1");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame2.getContentPane().add("Center", new Tritone("images/startling1.jpg"));
		frame2.pack();
		frame2.setSize(new Dimension(700, 500));
		frame2.setVisible(true);
		
		JFrame frame3 = new JFrame("STARLING 2");
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame3.getContentPane().add("Center", new Tritone("images/startling2.jpg"));
		frame3.pack();
		frame3.setSize(new Dimension(700, 500));
		frame3.setVisible(true);
	}
}
