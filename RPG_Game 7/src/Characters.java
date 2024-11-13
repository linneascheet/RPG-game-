import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Characters {
    private int x, y, w, h, speed, health, damage, stam, dx, dy;
    private ImageIcon pic;
    private Weapons weap;
    private ArrayList<Weapons> weaponList;
    private int lives = 3;

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void takeDamage() {
        this.lives--;  // Decrease lives by 1 when damage is taken
        if (this.lives <= 0) {
           
        }
    }
    
    
    public int getLives() {
        return lives;
    }

    public Characters() {
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        speed = 0;
        health = 0;
        damage = 0;
        stam = 0;
        dx = 0;
        dy = 0;
        pic = new ImageIcon(); // Consider providing a default image here
        weaponList = new ArrayList<>(); 
    }

    public Characters(int xV, int yV, int width, int height, int sp, int hea, int dam, int st, ImageIcon p, Weapons we, ArrayList<Weapons> list) {
        x = xV;
        y = yV;
        w = width;
        h = height;
        speed = sp;
        health = hea;
        damage = dam;
        stam = st;
        pic = p;
        weap = we;
        weaponList = list; 
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int hea) {
        health = hea;
    }

    public int getStamina() {
        return stam;
    }

    public void setStam(int newStam) {
        stam = newStam;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int sp) {
        speed = sp;
    }

    public int getDamage() {
        return damage; // Corrected to return damage
    }

    public void setDamage(int dam) {
        damage = dam;
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(int height) {
        h = height;
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(int width) {
        w = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x1) {
        x = x1;
    }

    public int getY() {
        return y;
    }

    public void setY(int y1) {
        y = y1;
    }

    public int getdX() {
        return dx;
    }

    public void setdX(int dx1) {
        dx = dx1;
    }

    public int getdY() {
        return dy;
    }

    public void setdy(int dy1) {
        dy = dy1;
    }
    public String getName(){
        return "error";
    }
    public Weapons getWeapon(){
        return weap;
    }
    public void setWeapons(ArrayList <Weapons> list){
        weaponList = list;

    }
    

    public void drawChar(Graphics g2d) {
        g2d.drawImage(pic.getImage(), x, y, w, h, null); 
    }
}
