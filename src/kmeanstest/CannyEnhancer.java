package kmeanstest;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

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
public class CannyEnhancer
{
  private double[] es;
  private double[] eo;

  /**
   * 
   * @param image
   */
  public void evaluate(BufferedImage image)
  {
    int w=image.getWidth();
    int h=image.getHeight();


    image=this.smoothing(image,w,h);

    BufferedImage dx=this.derivateX(image,w,h);

    BufferedImage dy=this.derivateY(image,w,h);

    int[] dataX=new int[w*h];
    int[] dataY=new int[w*h];
    dx.getRGB(0,0,w,h,dataX,0,w);
    dy.getRGB(0,0,w,h,dataY,0,w);

    for (int i=0;i<dataX.length;i++)
    {
      dataX[i]&=0xFF;
      dataY[i]&=0xFF;
    }

    this.edgeEval(dataX,dataY);
  }

  /**
   * 
   * @return
   */
  public double[] getEdgeStrength()
  {
    return es;
  }

  /**
   * 
   * @return
   */
  public double[] getEdgeNormal()
  {
    return eo;
  }

  private BufferedImage smoothing(BufferedImage image, int w, int h)
  {
    BufferedImage smooth=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    float[] data=
    {
      0.0113F,0.0838F,0.0113F,
      0.0838F,0.6193F,0.0838F,
      0.0113F,0.0838F,0.0113F
    };
    Kernel k=new Kernel(3,3,data);
    new ConvolveOp(k).filter(image,smooth);
    return smooth;
  }

  private BufferedImage derivateY(BufferedImage image, int w, int h)
  {
    BufferedImage dy=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    float[] data=
    {
      -1,
       0,
       1
    };
    Kernel k=new Kernel(1,3,data);
    new ConvolveOp(k).filter(image,dy);
    return dy;
  }

  private BufferedImage derivateX(BufferedImage image, int w, int h)
  {
    BufferedImage dx=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    float[] data=
    {
      -1, 0, 1,
    };
    Kernel k=new Kernel(3,1,data);
    new ConvolveOp(k).filter(image,dx);
    return dx;
  }

  /**
   * 
   * @param dx
   * @param dy
   */
  private void edgeEval(int[] dx, int[] dy)
  {
    es=new double[dx.length];
    eo=new double[dx.length];
    for (int i=0;i<es.length;i++)
    {

      es[i]=java.awt.Point.distance(dx[i],dy[i],0,0);

      eo[i]=Math.atan2(dy[i],dx[i]);
      if (eo[i]<0) eo[i]+=2*Math.PI;
    }
  }
}