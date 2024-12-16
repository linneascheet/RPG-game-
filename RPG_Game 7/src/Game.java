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
    private int currentLevel;
    private ImageIcon level2Bg;
    private Queue<Enemy> level2Enemies;




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
        currentLevel = 1;
        level2Bg = new ImageIcon("school2.png"); // Add your level 2 background image
        level2Enemies = new LinkedList<>();
        // Create the timer but don't start it yet
        enemyFireTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEnemyWeapon(); // Calls the updated method that includes following and firing
            }
        });
        
    }

    public void checkLevelTransition() {
        if (currentLevel == 1 && enemies.isEmpty() && player.getX() >= getWidth() - player.getWidth()) {
            currentLevel = 2;
            setupLevel2();
        }
    }

    public void setupLevel2() {
        player.setPosition(800, 300);
        rangedWeap.clear();
        level2Enemies = new LinkedList<>();
        level2Enemies.add(new Teacher(300, 300));
        level2Enemies.add(new Teacher(200, 400));
        level2Enemies.add(new Teacher(100, 200));
        level2Enemies.add(new Teacher(400, 500));
        level2Enemies.add(new Teacher(500, 300));
        level2Enemies.add(new Teacher(550, 100));
        level2Enemies.add(new Teacher(600, 200));
    
        // Set faster speeds for enemies in level 2
        for (Enemy enemy : level2Enemies) {
            enemy.setSpeed(8); // Increase speed (adjust as needed)
        }
    
        enemies = level2Enemies;
        gameOver = false;
        reset = false;
        enemyFireTimer.restart();
    }
    

    

    private void saveScores() {
        try (FileWriter writer = new FileWriter(saveFile)) {
            for (int score : highScores) {
                writer.write(score + "\n"); // Write only integers, one per line
            }
            System.out.println("Scores saved: " + highScores);
        } catch (IOException e) {
            System.out.println("Error writing scores to file.");
            e.printStackTrace();
        }
    }
    
    private void loadScores() {
        highScores.clear(); // Clear previous scores
        if (!saveFile.exists()) {
            createFile(); // Ensure the file is created if it doesn't exist
            return;
        }
        try (Scanner scanner = new Scanner(saveFile)) {
            while (scanner.hasNextInt()) {
                highScores.add(scanner.nextInt()); // Read integers only
            }
            System.out.println("Loaded scores: " + highScores);
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found.");
        }
    }
    

    private void updateScores(int newScore) {
        highScores.add(newScore);
        highScores.sort((a, b) -> b - a); // Sort in descending order
        if (highScores.size() > 10) {
            highScores = new ArrayList<>(highScores.subList(0, 10)); // Keep top 10 scores
        }
        saveScores();
    }
   // public void updateEnemyActions() {
      //  Enemy currentEnemy = enemies.peek(); // Get the current enemy
      //  if (currentEnemy != null) {
            // Make the enemy bounce around
          //  currentEnemy.bounceMove(getWidth(), getHeight());
    
            // Make the enemy fire projectiles
          //  fireEnemyWeapon();
       // }
   // }
    
    
    
    
    

    public void createFile() {
        try {
            if (saveFile.createNewFile()) {
                System.out.println("Successfully created file!");
                highScores = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    highScores.add(0); // Default scores
                }
                saveScores(); // Save the default scores
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
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
    // Reset entire game state
    score = 3;
    currentLevel = 1;
    resetCharacter();
    enemies = setEs();
    rangedWeap.clear();
    gameOver = false;
    screen = "start";
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
        g2d.setColor(Color.ORANGE);
        int gifX = 460; // X-coordinate for the GIF
        int gifY = 100; // Y-coordinate for the GIF

        g2d.drawImage(gifImage.getImage(), gifX, gifY, this);
        ImageIcon buttonImage = new ImageIcon("notebook1.png"); // Load the image
        g2d.drawImage(buttonImage.getImage(), 0, 350, 300, 200, this); // Draw the image at specified location
        g2d.drawImage(buttonImage.getImage(), 405, 350, 300, 200, this); // Draw the image at specified location
        g2d.drawImage(buttonImage.getImage(), 805, 350, 320, 200, this); // Draw the image at specified location
        g2d.drawImage(buttonImage.getImage(), 1205, 350, 320, 200, this); // Draw the image at specified location


       
        g2d.drawString("press 1", 50, 450);
        g2d.drawString("press 2", 450, 450);
        g2d.drawString("press 3", 850, 450);
        g2d.drawString("press 4", 1250, 450);

    

        //g2d.drawString("Select your character to begin!", 400, 150);
        g2d.setColor(Color.PINK);
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
        // Draw background depending on the level
        if (currentLevel == 1) {
            g2d.drawImage(gameBg.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else if (currentLevel == 2) {
            g2d.drawImage(level2Bg.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    
        // Draw level and score
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Level: " + currentLevel, 700, 200);
        g2d.drawString("Score: " + score, 800, 200);
    
        // Make sure to update enemy movement if we're in the game screen
        if (screen.equals("game")) {
            if (!enemies.isEmpty()) {
                // Get only the first enemy to act
                Enemy currentEnemy = enemies.peek();
                if (currentEnemy != null) {
                    currentEnemy.bounceMove(getWidth(), getHeight());
                    currentEnemy.drawChar(g2d);
                }
            }
        }
    
        // Handle player character
        if (player != null) {
            player.drawChar(g2d);
            checkLevelTransition();
        }
    
        // Handle projectiles and remove out-of-screen ones
        ArrayList<Ranged> projectilesToRemove = new ArrayList<>();
        for (Ranged projectile : rangedWeap) {
            projectile.setX(projectile.getX() + (projectile instanceof Sword ? 10 : -15));
            projectile.drawWeap(g2d);
    
            // Check if this is an enemy projectile hitting the player
            if (projectile instanceof Sword && checkCollision(projectile, player)) {
                projectilesToRemove.add(projectile);
                decreaseScore(); // Decrease player's score/lives
                continue;
            }
    
            // Check collision with current enemy
            Enemy currentEnemy = enemies.peek();
            if (currentEnemy != null && checkCollision(projectile, currentEnemy)) {
                projectilesToRemove.add(projectile);
                currentEnemy.takeDamage();
    
                if (currentEnemy.getScore() <= 0) {
                    enemies.poll(); // Remove the defeated enemy
                    score++;       // Update the score
                }
            }
    
            // Remove projectiles that leave the screen
            if (projectile.getX() < 0 || projectile.getX() > getWidth()) {
                projectilesToRemove.add(projectile);
            }
        }
        rangedWeap.removeAll(projectilesToRemove);
    
        // Draw current enemy
        Enemy currentEnemy = enemies.peek();
        if (currentEnemy != null) {
            currentEnemy.drawChar(g2d);
        }
    
        // Visualize hitboxes for debugging
      //  g2d.setColor(Color.RED);
       // for (Ranged projectile : rangedWeap) {
        //    g2d.drawRect(projectile.getX(), projectile.getY(), 
          //               projectile.getWidth(), projectile.getHeight());
       // }
       // if (player != null) {
       //     g2d.drawRect(player.getX(), player.getY(), 
       //                  player.getWidth(), player.getHeight());
       // }
      //  if (currentEnemy != null) {
       //     g2d.drawRect(currentEnemy.getX(), currentEnemy.getY(),
      //                   currentEnemy.getWidth(), currentEnemy.getHeight());
      //  }
    
        // Display win message for level 2
        if (currentLevel == 2 && enemies.isEmpty()) {
            g2d.setFont(new Font("Impact", Font.BOLD, 100));
            g2d.setColor(Color.GREEN);
            g2d.drawString("GAME COMPLETE!", 200, 300);
            if (!highScores.contains(score)) {
                updateScores(score);
            }
            g2d.setFont(new Font("Impact", Font.PLAIN, 40));
            g2d.setColor(Color.YELLOW);
            g2d.drawString("Press R to Restart", 200, 400);
            g2d.drawString("Press H to View High Scores", 200, 450);
        } else if (currentLevel == 1 && enemies.isEmpty()) {
            g2d.setFont(new Font("Impact", Font.PLAIN, 30));
            g2d.setColor(Color.YELLOW);
            g2d.drawString("Level Complete! Move right to continue →", 400, 300);
        }
    }
    

    private void decreaseScore() {
        score--;
        if (score <= 0) {
            gameOver = true;
            enemyFireTimer.stop();
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
        Enemy currentEnemy = enemies.peek(); // Only act on the first enemy
        if (currentEnemy != null) {
            // Follow player
          //  currentEnemy.followPlayer(player.getX(), player.getY());
          currentEnemy.bounceMove(getWidth(), getHeight());
            // Fire projectile
            Ranged projectile = new Sword(
                currentEnemy.getX() + currentEnemy.getWidth(),
                currentEnemy.getY() + currentEnemy.getHeight() / 2
            );
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
        if (gameOver) return;

        if (key == 32) {
            screen = "choose"; // Switch to choose screen on space
            enemyFireTimer.stop();
        }

        if (player == null) return; // Check if the player is selected

        int speed = player.getSpeed(); // Get the speed of the selected character

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
            player.setPosition(800, 500);
        }
        if (key == 50) {
            screen = "selection";
            player = charList.get(1);
            player.setPosition(800, 500);
        }
        if (key == 51) {
            screen = "selection";
            player = charList.get(2);
            player.setPosition(800, 500);
        }
        if (key == 52) {
            screen = "selection";
            player = charList.get(3);
            player.setPosition(800, 500);
        }
        if (key == 53) {
            screen = "selection";
            player = charList.get(4);
            player.setPosition(800, 500);
        }

        if (gameOver || enemies.isEmpty()) {
            enemyFireTimer.stop();
            if (key == KeyEvent.VK_R) {
                reset();
                screen = "game";
            } else if (key == KeyEvent.VK_H) {
                screen = "highscores";
            }
        }
        if (key == KeyEvent.VK_ENTER) {
            if (player != null) {
                screen = "game";
                System.out.println("Switching to Game Screen");
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

        if (key == 48) {
            reset = true;
        }
    }


    
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
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
    }
}
