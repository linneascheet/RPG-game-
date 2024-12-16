import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Zach extends Characters {
    public Zach() {
        super();
    }

    public Zach(int x, int y) {
        // Call the updated Characters constructor with the additional parameter
        super(x, y, 400, 300, 25, 5, 10, 1, new ImageIcon("Zach.png"), new Hammer(x-30,y+100), setList());
        
        // Set the weapon's position based on the character's position
        super.getWeapon().setX(super.getX() + super.getWidth());
    }

    @Override
    public String toString() {
        return "You picked Zach! Health: " + super.getHealth() + 
               " Speed: " + super.getSpeed() + 
               " Damage: " + super.getDamage() + 
               " Stamina: " + super.getStamina();
    }

    @Override
    public String getName() {
        return "Zach";
    }

    public static ArrayList<Weapons> setList() {
        ArrayList<Weapons> temp = new ArrayList<>(); 
        temp.add(new Hammer()); // Ensure Hammer is properly defined
        return temp;
    }
}
