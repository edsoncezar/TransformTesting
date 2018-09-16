package kmeanstest;

import java.util.*;

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
public class NonMaxSuppression
{
  public final static int ZERO=0;
  public final static int QUARANTACINQUE=1;
  public final static int NOVANTA=2;
  public final static int CENTOTRENTACINQUE=3;

  private final static double[] direzioni={0,Math.PI/4,Math.PI/2,3*Math.PI/4};

  /**
   * 
   * @param es
   * @param eo
   * @param w
   * @param h
   * @return
   */
  public double[] evaluate(double[] es, double[] eo, int w, int h)
  {
    double[] In=new double[es.length];
    Arrays.fill(In,0);

    for (int x=0;x<w;x++)
      for (int y=0;y<h;y++)
      {
        int c=y*w+x;

        int d=this.bestApproxEdgeNormal(eo[c]);
        boolean elimina=false;

        switch (d)
        {
          case NonMaxSuppression.ZERO:
            if (x>0&&es[y*w+(x-1)]>=es[c]) elimina=true;
            if (x<w-1&&es[y*w+(x+1)]>=es[c]) elimina=true;
            break;
          case NonMaxSuppression.QUARANTACINQUE:
            if (x>0&&y<h-1&&es[(y+1)*w+(x-1)]>=es[c]) elimina=true;
            if (x<w-1&&y>0&&es[(y-1)*w+(x+1)]>=es[c]) elimina=true;
            break;
          case NonMaxSuppression.NOVANTA:
            if (y>0&&es[(y-1)*w+x]>=es[c]) elimina=true;
            if (y<h-1&&es[(y+1)*w+x]>=es[c]) elimina=true;
            break;
          case NonMaxSuppression.CENTOTRENTACINQUE:
            if (x>0&&y>0&&es[(y-1)*w+(x-1)]>=es[c]) elimina=true;
            if (x<w-1&&y<h-1&&es[(y+1)*w+(x+1)]>=es[c]) elimina=true;
            break;
        }
        if (!elimina) In[c]=es[c];
        else es[c]=0;
      }
      return In;
  }


  /**
   * 
   * @param edgeNormal
   * @return
   */
  public static int bestApproxEdgeNormal(double edgeNormal)
  {
    int d=0;
    for (int i=1;i<direzioni.length;i++)
      if (Math.abs(direzioni[d]-edgeNormal)>Math.abs(direzioni[i]-edgeNormal)) d=i;
    return d;
  }

  /**
   * 
   * @param edgeNormal
   * @return
   */
  public static int bestApproxEdgeDirection(double edgeNormal)
  {
    int d=bestApproxEdgeNormal(edgeNormal);
    switch (d)
    {
      case NonMaxSuppression.ZERO:
        return NonMaxSuppression.NOVANTA;
      case NonMaxSuppression.QUARANTACINQUE:
        return NonMaxSuppression.CENTOTRENTACINQUE;
      case NonMaxSuppression.NOVANTA:
        return NonMaxSuppression.ZERO;
      case NonMaxSuppression.CENTOTRENTACINQUE:
        return NonMaxSuppression.QUARANTACINQUE;
      default:
        return -1;
    }
  }
}