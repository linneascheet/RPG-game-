import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import javax.swing.*;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private BufferedImage back;
    private int key, x, y, score;
    private ArrayList<Characters> charList;
    private ArrayList<Integer> highScores; 
    private String screen;
    private Characters player;
    private ImageIcon startBg;
    private ImageIcon chooseBg; 
    private ImageIcon gameBg;
    private String welcome;
    private double time;
    private int i;
    private Queue<Enemy> enemies;
    ArrayList<Ranged> rangedWeap;
    private Timer enemyFireTimer;
    private boolean reset;
    private boolean gameOver;
    private ImageIcon gifImage;
    private File saveFile;



    public Game() {
        new Thread(this).start();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        key = -1;
        x = 0;
        y = 0;
        charList = setCharList();
        player = new Characters();
        screen = "start";
        rangedWeap = new ArrayList<Ranged>();
        startBg = new ImageIcon("startbackground.png");
        chooseBg = new ImageIcon("classroom1.png");
        gameBg = new ImageIcon("creepyschool.jpg");
        welcome = "Welcome to Linnea's School Game";
        saveFile = new File("saved_file2.0.txt");
        time = System.currentTimeMillis();
        enemies = setEs();
        score = 3;
        reset = false;
        gameOver = false;
        gifImage = new ImageIcon("giphy.gif");
        highScores = new ArrayList<>();
        loadScores();

        // Create the timer but don't start it yet
        enemyFireTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEnemyWeapon();
            }
        });
    }

    private void loadScores() {
        try (Scanner scanner = new Scanner(saveFile)) {
            while (scanner.hasNextInt()) {
                highScores.add(scanner.nextInt());
            }
            System.out.println("Loaded scores: " + highScores);
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. Creating a new one.");
            createFile();
        }
    }
    private void saveScores() {
        try (FileWriter writer = new FileWriter(saveFile)) {
            // Write high scores with an identifier
            writer.write("High Scores:\n");
            for (int i = 0; i < highScores.size(); i++) {
                writer.write((i + 1) + ". " + highScores.get(i) + "\n");
            }
    
            System.out.println("Scores saved: " + highScores);
        } catch (IOException e) {
            System.out.println("Error writing scores to file.");
            e.printStackTrace();
        }
    }
    

    private void updateScores(int newScore) {
        highScores.add(newScore);
        highScores.sort((a, b) -> b - a); // Sort in descending order
        if (highScores.size() > 5) {
            highScores = new ArrayList<>(highScores.subList(0, 5)); // Keep top 5 scores
        }
        saveScores();
    }
    
    
    

    public void createFile() {
        try {
           
            if (saveFile.createNewFile()) {
                System.out.println("Successfully created file!");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readFile(){
        Scanner sc;
        try {
        sc = new Scanner (saveFile);
        while(sc.hasNext()){
            System.out.println(sc.next());
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}
    

public void writeToFile() {
    try (FileWriter myWriter = new FileWriter(saveFile)) {
        if (enemies.isEmpty()) { 
            // Save the high scores instead of just "win"
            myWriter.write("High Scores:\n");
            for (int highScore : highScores) {
                myWriter.write(highScore + "\n");
            }
        } else {
            myWriter.write("You have " + enemies.size() + " enemies left\n");
        }
        System.out.println("Successfully wrote to file.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

            
        
    
public void reset() {
    score = 0;
    resetCharacter();
    enemies = setEs();
    gameOver = false;
    screen = "start";
    // Ensure timer is stopped on reset
    enemyFireTimer.stop();
}

    public void resetCharacter() {
        player.setPosition(800, 300);
    }
    public void resetEnemy() {
       
    }



    public Queue<Enemy> setEs() {
        Queue<Enemy> temp = new LinkedList<>();
        temp.add(new Woolweaver(300, 500));
        temp.add(new Woolweaver(200, 500));
        temp.add(new Woolweaver(100, 500));
        temp.add(new Woolweaver(30, 500));

        return temp;
    }

    public ArrayList<Characters> setCharList() {
        ArrayList<Characters> temp = new ArrayList<>();
        temp.add(new Zach(20, 520));
        temp.add(new Evan(420, 520));
        temp.add(new Richie(820, 520));
        temp.add(new Reese(1220, 520));
        return temp;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(5); // Corrected sleep method
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    public void paint(Graphics g) {
        Graphics2D twoDgraph = (Graphics2D) g;
        if (back == null) {
            back = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB); // Corrected initialization
        }

        Graphics g2d = back.createGraphics();
        g2d.clearRect(0, 0, getSize().width, getSize().height);
        g2d.setFont(new Font("Impact", Font.BOLD, 50));
      
        drawScreen(g2d);
        welcome.substring(0, i);
        twoDgraph.drawImage(back, null, 0, 0);
    }

    //public void drawSelectScreen(Graphics g2d) {
       // player.drawChar(g2d);
       // g2d.drawString("" + player.toString(), 200, 200); // Corrected drawString
    //}

    public void drawStartScreen(Graphics g2d) {
        g2d.drawImage(startBg.getImage(), 0, 0, getWidth(), getHeight(), this);
        g2d.drawString(welcome.substring(0, i), 380, 420);
        if (i < welcome.length()) {
            if (System.currentTimeMillis() - time > 100) {
                time = System.currentTimeMillis();
                i++;
            }
        }
    }

    public void drawChooseScreen(Graphics g2d) {
        g2d.drawImage(chooseBg.getImage(), 0, 0, getWidth(), getHeight(), this);
        g2d.setFont(new Font("Impact", Font.BOLD, 50));
        g2d.setColor(Color.WHITE);
        int gifX = 460; // X-coordinate for the GIF
        int gifY = 100; // Y-coordinate for the GIF
        g2d.drawImage(gifImage.getImage(), gifX, gifY, this);
        g2d.drawString("press 1", 50, 450);
        g2d.drawString("press 2", 450, 450);
        g2d.drawString("press 3", 850, 450);
        g2d.drawString("press 4", 1250, 450);

    

        //g2d.drawString("Select your character to begin!", 400, 150);
        g2d.setColor(Color.WHITE);
        int namex = 150;
    
        g2d.setFont(new Font("Impact", Font.BOLD, 40));
        for (Characters c : charList) {
            c.drawChar(g2d);
            g2d.drawString(c.getName(), namex, 500);
    
            // Draw the character's weapon if it has one
            Weapons weapon = c.getWeapon();
            if (weapon != null) {
                weapon.drawWeap(g2d); // Draw the weapon
            }
    
            namex += 420;
        }
    }
    
    public void drawGameScreen(Graphics g2d) {
        g2d.drawImage(gameBg.getImage(), 0, 0, getWidth(), getHeight(), this);

        // Draw player

        if (enemies.isEmpty()) {
    // Display "YOU WIN" message
    g2d.setFont(new Font("Impact", Font.BOLD, 100));
    g2d.setColor(Color.GREEN);
    g2d.drawString("YOU WIN", 200, 300);

    // Update and save the player's score
    if (!highScores.contains(score)) { // Avoid updating repeatedly
        updateScores(score); // Add the player's score to the high scores
    }

    // Display high score prompt
    g2d.setFont(new Font("Impact", Font.PLAIN, 40));
    g2d.setColor(Color.YELLOW);
    g2d.drawString("Press R to Restart", 200, 400);
    g2d.drawString("Press H to View High Scores", 200, 450);

    return;
}
        
        if (player != null) {
            player.drawChar(g2d);
        }
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + score, 800, 200);
        
        ArrayList<Ranged> projectilesToRemove = new ArrayList<>();
        
        // Handle projectiles and check for collisions
        for (Ranged projectile : rangedWeap) {
            if (projectile instanceof Sword) {
                projectile.setX(projectile.getX() + 10); // Enemy missile moves right
            } else {
                projectile.setX(projectile.getX() - 15); // Player projectile moves left
            }
            
            projectile.drawWeap(g2d);
            
            // Check for collision with player (if it's a Sword projectile)
            if (projectile instanceof Sword && checkCollision(projectile, player)) {
                projectilesToRemove.add(projectile);
                decreaseScore();
            } 
            if (score == 0) {
                g2d.clearRect(0,0,getSize().width,getSize().height);
                g2d.drawString("YOU LOSE", 200, 200);
            } 
            // Check for collision with enemy (if it's not a Sword projectile)
            else if (!(projectile instanceof Sword)) {
                Enemy currentEnemy = enemies.peek();
                if (currentEnemy != null && checkCollision(projectile, currentEnemy)) {
                    projectilesToRemove.add(projectile);
                    currentEnemy.takeDamage();
                    
                    // If enemy's lives are zero, remove it from the queue
                    if (currentEnemy.getScore() <= 0) {
                        enemies.poll(); // Remove defeated enemy from the queue
                        score += 1;
                    }
                }
            }
            
            // Remove projectiles that are off-screen
            if (projectile.getX() < 0 || projectile.getX() > getWidth()) {
                projectilesToRemove.add(projectile);
            }
        }
        
        rangedWeap.removeAll(projectilesToRemove);
        
        // Draw the current enemy
        Enemy currentEnemy = enemies.peek();
        if (currentEnemy != null) {
            currentEnemy.drawChar(g2d);
        }
    }

    private void decreaseScore() {
        score--;
        if (score <= 0) {
            gameOver = true; // Set game over flag to true when lives reach 0
            enemyFireTimer.stop(); // Stop enemy from firing
        }
    }

    public void drawHighscoreScreen(Graphics g2d) {
        g2d.setFont(new Font("Impact", Font.BOLD, 50));
        g2d.setColor(Color.YELLOW);
        g2d.drawString("High Scores", 400, 100);
    
        g2d.setFont(new Font("Impact", Font.PLAIN, 30));
        int y = 200; // Starting y-coordinate for scores
        for (int i = 0; i < highScores.size(); i++) {
            g2d.drawString((i + 1) + ". " + highScores.get(i), 400, y);
            y += 50;
        }
    }
    

    
    
    
        
        private boolean checkCollision(Ranged projectile, Characters character) {
            Rectangle projectileBox = new Rectangle(
                projectile.getX(), 
                projectile.getY(), 
                projectile.getWidth(), 
                projectile.getHeight()
            );
        
            Rectangle characterBox = new Rectangle(
                character.getX(), 
                character.getY(), 
                character.getWidth(), 
                character.getHeight()
            );
        
            if (projectileBox.intersects(characterBox)) {
                character.takeDamage(); // Reduce lives when hit
                return true;  // Collision detected
            }
        
            return false;
        }
        
        private boolean checkCollision(Ranged projectile, Enemy enemy) {
            Rectangle projectileBox = new Rectangle(
                projectile.getX(), 
                projectile.getY(), 
                projectile.getWidth(), 
                projectile.getHeight()
            );
        
            Rectangle enemyBox = new Rectangle(
                enemy.getX(), 
                enemy.getY(), 
                enemy.getWidth(), 
                enemy.getHeight()
            );
        
            if (projectileBox.intersects(enemyBox)) {
                enemy.takeDamage(); // Reduce lives when hit
                return true;  // Collision detected
            }
        
            return false;
        }
        

    private void drawScreen(Graphics g2d) {
        switch (screen) {
            case "start":
                drawStartScreen(g2d);
                break;
            case "choose":
                drawChooseScreen(g2d);
                break;
            case "selection":
                drawSelectScreen(g2d);
                break;
            case "game":
                drawGameScreen(g2d);
                break; 
            case "highscores":
                drawHighscoreScreen(g2d);
                break;
            case "win":
                drawWinScreen(g2d);
            break;
                
            default:
                break;
        }
    }
    public void drawWinScreen(Graphics g2d) {
        g2d.setFont(new Font("Impact", Font.BOLD, 100));
        g2d.setColor(Color.GREEN);
        g2d.drawString("YOU WIN!", 200, 300);
    
        g2d.setFont(new Font("Impact", Font.PLAIN, 40));
        g2d.setColor(Color.YELLOW);
        g2d.drawString("Final Score: " + score, 200, 400);
    
        // Display high score
        int topScore = highScores.isEmpty() ? 0 : highScores.get(0);
        g2d.drawString("High Score: " + topScore, 200, 450);
    
        g2d.drawString("Press R to Restart", 200, 500);
        g2d.drawString("Press H to View High Scores", 200, 550);
    }
    
    

    public void drawSelectScreen(Graphics g2d){
        if (player != null) {
            player.drawChar(g2d);
            g2d.setFont(new Font("Times new Roman", Font.BOLD, 30));
            g2d.drawString("You picked " + player.toString(), 100, 200);
        }
    }
    public void attack(){
        if(player.getWeapon() instanceof Ranged){
        
            //rangedWeap.add(new Ranged(player.getX(), player.getY(), player.getWeapon().getDam(),player.getWeapon().getDuribility ));
        }
        else{
    
        }
    }

    
        public void fireEnemyWeapon() {
            Enemy currentEnemy = enemies.peek();  // Get the enemy from the queue or list
            if (currentEnemy != null && player != null) {
                System.out.println("Enemy firing at player");
        
                // Adjust this to use the enemy's position rather than the player's position
                Ranged projectile = new Sword(
                    currentEnemy.getX() + currentEnemy.getWidth(), // X position near the enemy
                    currentEnemy.getY() + currentEnemy.getHeight() / 2  // Y position at the middle of the enemy
                );
        
                // Add the projectile to the rangedWeap collection
                rangedWeap.add(projectile);
            }
        }

        public void takeDamage() {
            this.score--; // Decrease player lives
        
            if (this.score <= 0) {
                System.out.println("Player has no lives left. Game over.");
                // Add any additional game-over handling code here, like resetting the game or ending the session
            }
        }
        
        
    
    public void fireWeapon() {
        System.out.println("fireWeapon called"); // Debug print
        if (player != null && !enemies.isEmpty()) {
            System.out.println("Player and enemies exist"); // Debug print
    
            Ranged projectile = null;
            if (player.getWeapon() instanceof Hammer) {
                projectile = new Hammer(
                    player.getX() + player.getWidth() - 700, // Add a small offset to ensure it's on screen
                    player.getY() + player.getHeight() / 2
                );
            } else if (player.getWeapon() instanceof Laser) {
                projectile = new Laser(
                    player.getX() + player.getWidth() - 700, 
                    player.getY() + player.getHeight() / 2
                );
            } else if (player.getWeapon() instanceof slingshot) {
                projectile = new slingshot(
                    player.getX() + player.getWidth() - 700, 
                    player.getY() + player.getHeight() / 2
                );
            } else if (player.getWeapon() instanceof taser) {
                projectile = new taser(
                    player.getX() + player.getWidth() - 700, 
                    player.getY() + player.getHeight() / 2
                );
            }
            
            
            // Add more conditions here for additional weapon types
    
            if (projectile != null) {
                System.out.println("Created projectile at: " + projectile.getX() + ", " + projectile.getY()); // Debug print
                rangedWeap.add(projectile);
                System.out.println("Current number of projectiles: " + rangedWeap.size()); // Debug print
            }
        }

    }
    
    

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();

        System.out.println(key);
        int speed= 10;
        if (gameOver) return;
    
        if (key == 32) {
            screen = "choose"; // Switch to choose screen on space
            enemyFireTimer.stop();
        }

        if (key == 38) { // Up arrow key
            player.setY(player.getY() - speed); // Move up
        } 
        if (key == 40) { // Down arrow key
            player.setY(player.getY() + speed); // Move down
        }
        if (key == 37) { // Left arrow key
            player.setX(player.getX() - speed); // Move left
        }
        if (key == 39) { // Right arrow key
            player.setX(player.getX() + speed); // Move right
        }
    
        if (key == 49) {
            screen = "selection";
            player = charList.get(0);
            player.setPosition(800, 500); // Set position to (800, 300)
        }
        if (key == 50) {
            screen = "selection";
            player = charList.get(1);
            player.setPosition(800, 500); // Set position to (800, 300)
        }
        if (key == 51) {
            screen = "selection";
            player = charList.get(2);
            player.setPosition(800, 500); // Set position to (800, 300)
        }
        if (key == 52) {
            screen = "selection";
            player = charList.get(3);
            player.setPosition(800, 500); // Set position to (800, 300)
        }
        if (key == 53) {
            screen = "selection";
            player = charList.get(4);
            player.setPosition(800, 500); // Set position to (800, 300)
        }
        if (gameOver || enemies.isEmpty()) {
            enemyFireTimer.stop();
            if (key == KeyEvent.VK_R) {
                reset();
                screen = "game";
                // Don't start timer until player presses Enter again
            } else if (key == KeyEvent.VK_H) {
                screen = "highscores";
            }
        }
    
        // Add a key press to switch to game screen
        if (key == KeyEvent.VK_ENTER) {
            if (player != null) {
                screen = "game";
                System.out.println("Switching to Game Screen");
                // Only start enemy firing when entering game screen with a selected player
                enemyFireTimer.start();
            } else {
                System.out.println("No player selected. Cannot switch to game.");
            }
            repaint();
        }
        if (key == KeyEvent.VK_H) { // Press 'H' to view high scores
    screen = "highscores";
    repaint();
}

    
        if (key == 70) { // f key
            fireWeapon();
        }

        if (key==48);
        reset=true;
    }
   

    
    
    
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // Not used
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
       // x = arg0.getX();
       // y = arg0.getY();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // Not used
        enemies.remove(); // Example of handling mouse click on an enemy
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        System.out.println("entered");
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        System.out.println("exited");
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        System.out.println("you clicked at " + arg0.getY());
        x = arg0.getX();
        y = arg0.getY();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // Not used
    }
}
