import javax.swing.ImageIcon;

public class Laser extends Ranged {

    public Laser() {
        super(); 
    }

    public Laser(int x, int y) {
        super(x, y, 70, 60, 59,78, 156, new ImageIcon("laser1.png")); // Set hammer image
    }

    @Override
    public String toString() {
        return "Laser";
    }
}
