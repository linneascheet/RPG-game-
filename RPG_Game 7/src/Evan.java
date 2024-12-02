import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Evan extends Characters {
    public Evan() {
        super();
    }

    public Evan(int x, int y) {
        // Call the updated Characters constructor with the additional parameter
        super(x, y, 400, 300, 1000, 5, 10, 1, new ImageIcon("Evan.png"), new taser(x-100,y+100), setList());
        
        // Set the weapon's position based on the character's position
        super.getWeapon().setX(super.getX() + super.getWidth());
    }

    @Override
    public String toString() {
        return "You picked Evan! Health: " + super.getHealth() + 
               " Speed: " + super.getSpeed() + 
               " Damage: " + super.getDamage() + 
               " Stamina: " + super.getStamina();
    }

    @Override
    public String getName() {
        return "Evan";
    }

    public static ArrayList<Weapons> setList() {
        ArrayList<Weapons> temp = new ArrayList<>(); 
        temp.add(new taser()); // Ensure Hammer is properly defined
        return temp;
    }
}
