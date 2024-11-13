import javax.swing.ImageIcon;

public class taser extends Ranged {

    public taser() {
        super(); 
    }

    public taser(int x, int y) {
        super(x, y, 100, 100, 59,78, 156, new ImageIcon("taser.png")); // Set hammer image
    }

    @Override
    public String toString() {
        return "taser";
    }
}
