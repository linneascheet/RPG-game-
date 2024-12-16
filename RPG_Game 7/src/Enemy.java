import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Enemy extends Characters {
    private int dx; // Horizontal speed
    private int dy; // Vertical speed

    public Enemy() {
        super();
        this.dx = 4; // Default horizontal speed
        this.dy = 4; // Default vertical speed
    }

    public Enemy(int x, int y, int w, int h, int speed, int hea, int dam, int st, ImageIcon pic, Weapons wea, ArrayList<Weapons> list) {
        super(x, y, w, h, speed, hea, dam, st, pic, wea, list);
        this.dx = 4; // Default horizontal speed
        this.dy = 4; // Default vertical speed
    }

    public void bounceMove(int screenWidth, int screenHeight) {
        // Update position
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    
        // Check for collisions with the edges of the screen
        if (this.getX() <= 0 || this.getX() + this.getWidth() >= Math.min(screenWidth, 600)) {
            dx = -dx; // Reverse horizontal direction
            // Ensure the enemy stays within bounds
            if (this.getX() <= 0) {
                this.setX(0); // Prevent overshooting on the left
            } else if (this.getX() + this.getWidth() > 600) {
                this.setX(600 - this.getWidth()); // Prevent overshooting beyond x = 600
            }
        }
    
        if (this.getY() <= 0 || this.getY() + this.getHeight() >= screenHeight) {
            dy = -dy; // Reverse vertical direction
            // Ensure the enemy stays within bounds
            if (this.getY() <= 0) {
                this.setY(0); // Prevent overshooting at the top
            } else if (this.getY() + this.getHeight() > screenHeight) {
                this.setY(screenHeight - this.getHeight()); // Prevent overshooting at the bottom
            }
        }
    }
    
    public void followPlayer(int playerX, int playerY) {
        // Calculate the difference in positions
        int deltaX = playerX - this.getX();
        int deltaY = playerY - this.getY();

        // Calculate distance and normalize
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Avoid division by zero and move towards the player
        if (distance > 0.1) { // Use a small threshold to avoid jittering
            int moveX = (int) (deltaX / distance * this.getSpeed());
            int moveY = (int) (deltaY / distance * this.getSpeed());

            // Update enemy's position
            this.setX(this.getX() + moveX);
            this.setY(this.getY() + moveY);
        }
    }

    // New method to adjust enemy speed dynamically
    public void setSpeed(int newSpeed) {
        this.dx = (this.dx > 0 ? newSpeed : -newSpeed); // Adjust horizontal speed while maintaining direction
        this.dy = (this.dy > 0 ? newSpeed : -newSpeed); // Adjust vertical speed while maintaining direction
    }
}
