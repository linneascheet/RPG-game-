import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Richie extends Characters {
    public Richie() {
        super();
    }

    public Richie(int x, int y) {
        // Call the updated Characters constructor with the additional parameter
        super(x, y, 400, 300, 1000, 5, 10, 1, new ImageIcon("Richie.png"),  new slingshot(x,y+100), setList());
        
        // Set the weapon's position based on the character's position
        super.getWeapon().setX(super.getX() + super.getWidth());
    }

    @Override
    public String toString() {
        return "You picked Richie! Health: " + super.getHealth() + 
               " Speed: " + super.getSpeed() + 
               " Damage: " + super.getDamage() + 
               " Stamina: " + super.getStamina();
    }

    @Override
    public String getName() {
        return "Richie";
    }

    public static ArrayList<Weapons> setList() {
        ArrayList<Weapons> temp = new ArrayList<>(); 
        temp.add(new slingshot()); // Ensure Hammer is properly defined
        return temp;
    }
}
