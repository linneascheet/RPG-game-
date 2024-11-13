import javax.swing.ImageIcon;

public class Hammer extends Ranged {

    public Hammer() {
        super(); 
    }

    public Hammer(int x, int y) {
        super(x, y, 100, 100, 59,78, 156, new ImageIcon("hammer.png")); // Set hammer image
    }

    @Override
    public String toString() {
        return "Hammer";
    }
}
