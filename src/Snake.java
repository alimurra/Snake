/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author murra9546
 */

public class Snake extends JComponent implements KeyListener{

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    //declare snake variables
    Rectangle player = new Rectangle(WIDTH/2,HEIGHT/2,20,20);
    
    //declare food variables
    //randomize food position
    //MAKE INTO A METHOD
//    public void 
    int randNumx1 = (int)(Math.random()*(WIDTH - 20 + 1))+ 20;
    int randNumy1 = (int)(Math.random()*(HEIGHT - 20 + 1))+ 20;

    Rectangle food = new Rectangle(randNumx1,randNumy1, 20, 20);
    
    //speed variable
    int speed = 3;
    //keyboard
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    
    //key controls to allow fluent movement of the snake
    boolean right1 = false;
    boolean left1 = false;
    boolean up1 = false;
    boolean down1 = false;
    
    //array of snake
    int snakearray = 50;
    int[] snake = new int[snakearray];
    
    //string to say game over
    String gameover = "Game Over!";
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
        
        //draw the player
        g.setColor(Color.red);
        g.fillRect(player.x, player.y, player.width, player.height);
        
        //draw the food
        g.setColor(Color.BLUE);
        g.fillRect(food.x, food.y, food.width, food.height);
        
        //new color
        Color purple = new Color(115,35,235);
        
        if(player.y < 0) //if the snake hits the top
        {
            g.clearRect(0, 0, WIDTH, HEIGHT); //clear the screen
            g.setColor(purple);
            g.drawString(gameover, WIDTH/2, HEIGHT/2); //game over
        }
        
        if(player.x + player.width > WIDTH) //if the snake hits the right side
        {
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.drawString(gameover, WIDTH/2, HEIGHT/2);
        }
        
        if(player.x < 0) //if the snake hits the left side
        {
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.drawString(gameover, WIDTH/2, HEIGHT/2);
        }
        
        
        if(player.y + player.height > HEIGHT) //if the snake hits the bottom
        {
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.drawString(gameover, WIDTH/2, HEIGHT/2);
        }
       

        
        // GAME DRAWING ENDS HERE
    }
    
    
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 

            //if the snake hits the boundries of the screen
            if(player.y < 0 || player.x + player.width > WIDTH || player.x < 0 || player.y + player.height > HEIGHT) 
            {
                done = true; //end the game
            }

            //movements for snake
            if(right) //if the user presses right, then the snake will only move right
            {
                player.x ++;
                right1 = true;
                up1 = false;
                down1 = false;
                left1 = false;
            }else if(left) //if the user presses left, then the snake will only move left
            {
                player.x --;
                left1 = true;
                up1 = false;
                down1 = false;
                right1 = false;
            }else if(up) //if the user presses up, then the snake will only move up
            {
                player.y --;
                up1 = true;
                right1 = false;
                down1 = false;
                left1 = false;
            }else if(down) //if the user presses down, then the snake will only move down
            {
                player.y ++;
                down1 = true;
                up1 = false;
                right1 = false;
                left1 = false;
           }

            //to allow the snake to move only freely after one press of the key
            if(right1)
            {
                player.x += speed;
            }else if(left1)
            {
                player.x -= speed;
            }else if(up1)
            {
                player.y -= speed;
            }else if(down1)
            {
                player.y += speed;
            }
            
            //do the collisions with the snake and the food
          
            if(player.intersects(food))
            {
                handlecollisions(player,food);
            }
            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        Snake game = new Snake();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
     int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_RIGHT)
        {
            right = true;
        }else if(keyCode == KeyEvent.VK_LEFT)
        {
            left = true;
        }else if(keyCode == KeyEvent.VK_UP)
        {
            up = true;
        }else if(keyCode == KeyEvent.VK_DOWN)
        {
            down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_RIGHT)
        {
            right = false;
        }else if(keyCode == KeyEvent.VK_LEFT)
        {
            left = false;
        }else if(keyCode == KeyEvent.VK_UP)
        {
            up = false;
        }else if(keyCode == KeyEvent.VK_DOWN)
        {
            down = false;
        }
    }
    
    //method for collisions
    public void handlecollisions(Rectangle player, Rectangle food)
    {
        //hit from the bottom
//        if(player.y > )
//        {
//            
//        }
    }

            
    
    
}
