import javax.swing.ImageIcon;

public class Sword extends Ranged {

    // Default constructor
    public Sword() {
        super(); // Calls the default constructor of Ranged, which calls Weapons
    }

    // Constructor with parameters
    public Sword(int x, int y) {
        super(x, y, 90, 90, 59, 78, 156, new ImageIcon("sword.png")); // Calling the Ranged constructor, which calls Weapons
    }

    @Override
    public String toString() {
        return "Sword";
    }
}
