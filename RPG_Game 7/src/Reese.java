import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Reese extends Characters {
    public Reese() {
        super();
    }

    public Reese(int x, int y) {
        // Call the updated Characters constructor with the additional parameter
        super(x, y, 400, 300, 15, 5, 10, 1, new ImageIcon("Reese.png"), new Laser(x-200,y-50), setList());
        
        // Set the weapon's position based on the character's position
        super.getWeapon().setX(super.getX() + super.getWidth());
    }

    @Override
    public String toString() {
        return "Reese! Health: " + super.getHealth() + 
               " Speed: " + super.getSpeed(); 
           //    " Damage: " + super.getDamage() + 
            //   " Stamina: " + super.getStamina();
    }

    @Override
    public String getName() {
        return "Reese";
    }

    public static ArrayList<Weapons> setList() {
        ArrayList<Weapons> temp = new ArrayList<>(); 
        temp.add(new Laser()); // Ensure Hammer is properly defined
        return temp;
    }
}
