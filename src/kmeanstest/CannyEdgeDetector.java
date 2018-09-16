package kmeanstest;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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
public class CannyEdgeDetector
{
  /**
   * 
   * @param image
   * @return
   */
  public static BufferedImage evaluate(BufferedImage image)
  {
    int w=image.getWidth();
    int h=image.getHeight();


    BufferedImage bwImage=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    ColorConvertOp filtro=new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),null);
    filtro.filter(image,bwImage);


    CannyEnhancer ce=new CannyEnhancer();
    ce.evaluate(bwImage);
    double[] es=ce.getEdgeStrength();
    double[] eo=ce.getEdgeNormal();

    double[] In=new NonMaxSuppression().evaluate(es,eo,w,h);

    ArrayList chains=new HisteresisThresholding().evaluate(In,eo,0,10,w,h);

    int[] data=new int[w*h];

    Arrays.fill(data,0xFFFFFFFF);
    Iterator iterC=chains.iterator();
    while (iterC.hasNext())
    {
      Iterator iterP=((ArrayList)iterC.next()).iterator();
      while (iterP.hasNext())
      {
    	java.awt.Point p=(java.awt.Point)iterP.next();

        data[p.y*w+p.x]=0xFF000000;
      }
    }
    BufferedImage canny=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    canny.setRGB(0,0,w,h,data,0,w);
    return canny;
  }
}