import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Weapons {

   private int x,y,w,h, dam, durabillity, dps, dx, dy;
     private ImageIcon pic;
    public Weapons(){

    }
    public Weapons(int xV, int yV, int width, int height, int d, int dur, int dp, ImageIcon p){
        x=xV;
        y=yV;
        w=width;
        h=height;
        dam=d;
        durabillity=dur;
        dps=dp;
        pic =p;
        dx=0;
        dy=0;
    }

   /*  void setX(int i) {
       x=i;
    }*/
    void setY(int i) {
        y=i;
     }

     public void drawWeap(Graphics g2d) {
        if (pic != null && pic.getImage() != null) {
            g2d.drawImage(pic.getImage(), x, y, w, h, null);
        }
    }
    /*public void setY(int y2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setY'");
    }  
    */

    public int getX() {
        return x;
    }
//check for mouse colision
    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }
    public void setX(int newX) {
        this.x = newX;
    }
  /*   public void setX() {
        this.x = x;
    }

    public void setY() {
        this.y = y;
    }*/
}
