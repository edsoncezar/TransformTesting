package kmeanstest;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

/**
 * Some tests that I did in the past I believe I use some 
 * examples from Programming in Java Advanced Imaging, I used a paper 
 * in the past, in 2005 I believe, I found these links today
 *
 *
 * https://docs.oracle.com/cd/E19957-01/806-5413-10/806-5413-10.pdf
 * 
 * http://seer.ufrgs.br/rita/article/viewFile/rita_v11_n1_p93-124/3555
 * 
 * This application renders an Edge Detection, this one use JAI
 * I did one using algorithms before
 * 
 * @author edson
 */
public class RenderEdgeDetection {

	private static String pictureToTransform = "images/lovely.jpg";

	public static void main(String[] args) throws IOException {

		logImageInformation(pictureToTransform);

		// Read an image.
		BufferedImage input = ImageIO.read(new File(pictureToTransform));
		// Create a gray level image of the same size.
		BufferedImage im = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
		
		ParameterBlock parameter = new ParameterBlock();
		
		parameter.addSource(im);
		
		parameter.add(KernelJAI.GRADIENT_MASK_SOBEL_HORIZONTAL);
		
		parameter.add(KernelJAI.GRADIENT_MASK_SOBEL_VERTICAL);
		
		RenderedOp output = JAI.create("GradientMagnitude", parameter);
		
		// Get the graphics context for the gray level image.
		Graphics2D g2d = im.createGraphics();
		// Render the input image on it.
		g2d.drawImage(input, 0, 0, null);
		// Store the resulting image using the PNG format.
		ImageIO.write(im, "PNG", new File("images/rendered_lovely_1.png"));

		DisplayJAI jai = new DisplayJAI(output.getAsBufferedImage());
		JScrollPane pane = new JScrollPane(jai);

		DisplayJAI jaiN = new DisplayJAI(input);
		JScrollPane paneN = new JScrollPane(jaiN);

		// Use a label to display the image
		JFrame frame = new JFrame();
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		frame.pack();
		frame.setTitle("EDGE DETECTION");
		frame.setVisible(true);

		JFrame frame1 = new JFrame();
		frame1.getContentPane().add(paneN, BorderLayout.CENTER);
		frame1.pack();
		frame1.setTitle("ORIGINAL PICTURE");
		frame1.setVisible(true);

	}

	/**
	 * This method prints to the console information about an image.
	 * 
	 * @param imageString the image file name.
	 */
	private static void logImageInformation(String imageString) {
		
		System.out.println("===================================================");
		// Display image data. First, the image size (non-JAI related).
		File image = new File(imageString);
		System.out.printf("File: %s Size in bytes: %d\n", imageString, image.length());
		// Open the image (using the name passed as a command line parameter)
		PlanarImage pi = JAI.create("fileload", imageString);
		// Now let's display the image dimensions and coordinates.
		System.out.print("Dimensions: ");
		System.out.print(pi.getWidth() + "x" + pi.getHeight() + " pixels");
		// Remember getMaxX and getMaxY return the coordinate of the next point!
		System.out.println(" (from " + pi.getMinX() + "," + pi.getMinY() + " to " + (pi.getMaxX() - 1) + ","
				+ (pi.getMaxY() - 1) + ")");
		// Tiles number, dimensions and coordinates.
		System.out.print("Tiles: ");
		System.out.print(pi.getTileWidth() + "x" + pi.getTileHeight() + " pixels" + " (" + pi.getNumXTiles() + "x"
				+ pi.getNumYTiles() + " tiles)");
		System.out.print(" (from " + pi.getMinTileX() + "," + pi.getMinTileY() + " to " + pi.getMaxTileX() + ","
				+ pi.getMaxTileY() + ")");
		System.out.println(" offset: " + pi.getTileGridXOffset() + "," + pi.getTileGridXOffset());
		// Display info about the SampleModel of the image.
		SampleModel sm = pi.getSampleModel();
		System.out.println("Number of bands: " + sm.getNumBands());
		System.out.print("Data type: ");
		
		switch (sm.getDataType()) {
		case DataBuffer.TYPE_BYTE: {
			System.out.println("byte");
			break;
		}
		case DataBuffer.TYPE_SHORT: {
			System.out.println("short");
			break;
		}
		case DataBuffer.TYPE_USHORT: {
			System.out.println("ushort");
			break;
		}
		case DataBuffer.TYPE_INT: {
			System.out.println("int");
			break;
		}
		case DataBuffer.TYPE_FLOAT: {
			System.out.println("float");
			break;
		}
		case DataBuffer.TYPE_DOUBLE: {
			System.out.println("double");
			break;
		}
		case DataBuffer.TYPE_UNDEFINED: {
			System.out.println("undefined");
			break;
		}
		}
		
		// Display info about the ColorModel of the image.
		ColorModel cm = pi.getColorModel();
		if (cm != null) {
			System.out.println("Number of color components: " + cm.getNumComponents());
			System.out.print("Colorspace components' names: ");
			for (int i = 0; i < cm.getNumComponents(); i++)
				System.out.print(cm.getColorSpace().getName(i) + " ");
			System.out.println();
			System.out.println("Bits per pixel: " + cm.getPixelSize());
			System.out.print("Transparency: ");
			switch (cm.getTransparency()) {
			case Transparency.OPAQUE: {
				System.out.println("opaque");
				break;
			}
			case Transparency.BITMASK: {
				System.out.println("bitmask");
				break;
			}
			case Transparency.TRANSLUCENT: {
				System.out.println("translucent");
				break;
			}
			}
		} else
			System.out.println("No color model.");
	}
}