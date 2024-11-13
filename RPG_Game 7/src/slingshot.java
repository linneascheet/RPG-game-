import javax.swing.ImageIcon;

public class slingshot extends Ranged {

    public slingshot() {
        super(); 
    }

    public slingshot(int x, int y) {
        super(x, y, 100, 100, 59,78, 156, new ImageIcon("slingshot.png")); // Set hammer image
    }

    @Override
    public String toString() {
        return "slingshot";
    }
}
