package kmeanstest;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import javax.swing.filechooser.FileView;
import javax.imageio.*;

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
public class CannyEdgeDetectorFrame
{
  public static void main(String[] args)
  {
    JFileChooser fileChooser=new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setFileFilter(new JPGFilter());
    fileChooser.setFileFilter(new PNGFilter());
    fileChooser.setFileView(new ImageView());

    JFrame f=new JFrame();
    final JLabel label=new JLabel();
    JButton apri=new JButton("Apri");
    JButton canny=new JButton("Canny");
    JPanel panel=new JPanel();
    panel.add(apri);
    panel.add(canny);
    f.getContentPane().add(label,BorderLayout.CENTER);
    f.getContentPane().add(panel,BorderLayout.SOUTH);
    apri.addActionListener(new OpenListener(label,fileChooser));
    
    BufferedImage imageDefault;
	try {
		imageDefault = ImageIO.read(new File("images/gorgeous.jpg"));
		ImageIcon image = new ImageIcon(imageDefault);
		label.setIcon(image);
	    doCannyEdgeDetector(label, image);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    
    canny.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        ImageIcon icon=(ImageIcon)label.getIcon();
        if (icon!=null)
        {
          doCannyEdgeDetector(label, icon);
        }
      }

	private void doCannyEdgeDetector(final JLabel label, ImageIcon icon) {
		BufferedImage image=(BufferedImage)icon.getImage();
          image=CannyEdgeDetector.evaluate(image);
          label.setIcon(new ImageIcon(image));
	}
    });
    f.setSize(600,600);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.show();
  }

  private static void doCannyEdgeDetector(JLabel label, ImageIcon image) {
	BufferedImage image1=(BufferedImage)image.getImage();
    image1=CannyEdgeDetector.evaluate(image1);
    label.setIcon(new ImageIcon(image1));
	
  }
}

/**
 * 
 * @author edson
 *
 */
class JPGFilter extends FileFilter
{
  public boolean accept(File f)
  {
    String str=f.getName().toLowerCase();
    return str.endsWith(".jpg")||str.endsWith(".jpeg")||f.isDirectory();
  }

  public String getDescription()
  {
    return "IMMAGINE JPG";
  }

  public String toString()
  {
    return "jpg";
  }
}

/**
 * 
 * @author edson
 *
 */
class PNGFilter extends FileFilter
{
  public boolean accept(File f)
  {
    return f.getName().toLowerCase().endsWith(".png")||f.isDirectory();
  }

  public String getDescription()
  {
    return "IMMAGINE PNG";
  }

  public String toString()
  {
    return "png";
  }
}

/**
 * 
 * @author edson
 *
 */
class ImageView extends FileView
{
  private ImageIcon icon;

  public ImageView()
  {
    icon=new ImageIcon("icon.gif");
  }

  public Icon getIcon(File f)
  {
    String str=f.getName().toLowerCase();
    if (str.endsWith(".jpeg")||str.endsWith(".jpg")||str.endsWith(".png")) return icon;
    else return null;
  }

  public String getDescription(File f) {return null;}
  public String getName(File f) {return null;}
  public String getTypeDescription(File f) {return null;}
  public Boolean isTraversable(File f) {return null;}
}

/**
 * 
 * @author edson
 *
 */
class OpenListener implements ActionListener
{
  private JLabel label;
  private JFileChooser fileChooser;

  public OpenListener(JLabel l, JFileChooser fc)
  {
    label=l;
    fileChooser=fc;

    fileChooser.setCurrentDirectory(new File("d:\\images\\"));
  }

  public void actionPerformed(ActionEvent e)
  {

    fileChooser.setDialogTitle("Apri file");

    int risposta=fileChooser.showOpenDialog(label);
    if (risposta==fileChooser.APPROVE_OPTION) 
    {

      File f=fileChooser.getSelectedFile();
      try
      {

        Image imm=ImageIO.read(f);

        label.setIcon(new ImageIcon(imm));
      }
      catch (Exception ex) {}
    }
  }

}