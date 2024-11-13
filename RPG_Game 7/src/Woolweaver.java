
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Woolweaver extends Enemy{


    public Woolweaver(){
        super();

    }

   


public String toString(){
    return "Woolweaver";
}
public Woolweaver(int x, int y){ 
    super(x, y, 300, 200, 1000, 5, 10, 1, new ImageIcon("woolweaver.png"), new Sword(x+130,y), setList());
        
        // Set the weapon's position based on the character's position
        super.getWeapon().setX(super.getX() + super.getWidth());
    }

    public static ArrayList<Weapons> setList() {
        ArrayList<Weapons> temp = new ArrayList<>(); 
        temp.add(new Sword()); // Ensure Hammer is properly defined
        return temp;
}
}
