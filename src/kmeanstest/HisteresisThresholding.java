package kmeanstest;

import java.util.ArrayList;

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
public class HisteresisThresholding
{
  /**
   * 
   * @param In
   * @param eo
   * @param tl
   * @param th
   * @param w
   * @param h
   * @return
   */
  public ArrayList evaluate(double[] In, double[] eo, double tl, double th, int w, int h)
  {
    boolean[] marked=new boolean[In.length];
    ArrayList chains=new ArrayList();

    for (int x=0;x<w;x++)
      for (int y=0;y<h;y++)
      {
        int c=y*w+x;
        if (!marked[c]&&In[c]>th)
        {
          ArrayList points=new ArrayList();

          this.followChain(In,eo,marked,points,x,y,w,h,th,tl);
          chains.add(points);
        }
      }
      return chains;
  }

  /**
   * 
   * @param In
   * @param eo
   * @param marked
   * @param points
   * @param x
   * @param y
   * @param w
   * @param h
   * @param th
   * @param tl
   */
  private void followChain(double[] In, double[] eo, boolean[] marked, ArrayList points, int x, int y, int w, int h, double th, double tl)
  {
    int c=y*w+x;
    if (marked[c]) return;
    points.add(new java.awt.Point(x,y));
    marked[c]=true;


    int d=NonMaxSuppression.bestApproxEdgeDirection(eo[c]);
    switch (d)
    {
      case NonMaxSuppression.ZERO:
        if (x>0&&In[y*w+(x-1)]>tl) this.followChain(In,eo,marked,points,x-1,y,w,h,th,tl);
        if (x<w-1&&In[y*w+(x+1)]>tl) this.followChain(In,eo,marked,points,x+1,y,w,h,th,tl);
        break;
      case NonMaxSuppression.QUARANTACINQUE:
        if (x>0&&y<h-1&&In[(y+1)*w+(x-1)]>tl) this.followChain(In,eo,marked,points,x-1,y+1,w,h,th,tl);
        if (x<w-1&&y>0&&In[(y-1)*w+(x+1)]>tl) this.followChain(In,eo,marked,points,x+1,y-1,w,h,th,tl);
        break;
      case NonMaxSuppression.NOVANTA:
        if (y>0&&In[(y-1)*w+x]>tl) this.followChain(In,eo,marked,points,x,y-1,w,h,th,tl);
        if (y<h-1&&In[(y+1)*w+x]>tl) this.followChain(In,eo,marked,points,x,y+1,w,h,th,tl);
        break;
      case NonMaxSuppression.CENTOTRENTACINQUE:
        if (x>0&&y>0&&In[(y-1)*w+(x-1)]>tl) this.followChain(In,eo,marked,points,x-1,y-1,w,h,th,tl);
        if (x<w-1&&y<h-1&&In[(y+1)*w+(x+1)]>tl) this.followChain(In,eo,marked,points,x+1,y+1,w,h,th,tl);
        break;
    }
  }
}