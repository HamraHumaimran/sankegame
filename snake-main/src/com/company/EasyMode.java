package com.company;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;
import static java.lang.StrictMath.*;

public class EasyMode extends JPanel implements ActionListener{

    int snakeLength = 3;

    private ImageIcon snake;

    int score;
    int foodx;
    int foody;

    final static int left = 1;
    final static int right = 2;
    final static int up = 3;
    final static int down = 4;
    int dir = 2;

    boolean play = false;
    boolean gameEnded = false;
    Timer timer = new Timer(delay,this);
    Random random;

    static final int screenWidth = 1000;
    static final int screenHeight = 1000;
    static final int pixel = 50;
    static final int size = (int) ((screenWidth * screenHeight)/(pow(pixel, 2)));
    static final int delay = 250;

    final int[] x = new int[size];
    final int[] y = new int[size];

    EasyMode() {
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(27, 67, 50));
        this.setFocusable(true);
        this.addKeyListener(new newKeyAdapter());
    }

    public class newKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if(e.getKeyCode() == KeyEvent.VK_LEFT && dir != right) dir = left;
            if(e.getKeyCode() == KeyEvent.VK_RIGHT && dir != left) dir = right;
            if(e.getKeyCode() == KeyEvent.VK_UP && dir != down) dir = up;
            if(e.getKeyCode() == KeyEvent.VK_DOWN && dir != up) dir = down;
        }
    }

    public void move(){
        for(int l = snakeLength; l>0; l--) {
            x[l] = x[l-1];
            y[l] = y[l-1];
        }

        if(dir == right) x[0] += pixel;
        if(dir == left) x[0] -= pixel;
        if(dir == up) y[0] -= pixel;
        if(dir == down) y[0] += pixel;

    }

    public void start() {
        food();
        play = true;
        gameEnded = false;
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(!play && !gameEnded) {

            snake = new ImageIcon("snake1.png");
            snake.paintIcon(this, g, 0, -300);

            g.setColor(new Color(8, 28, 21));
            g.fillRect(0, 0, screenWidth-1, (screenHeight-1)/10);
            g.setColor(new Color(149, 213, 178));
            g.drawRect(1, 2, screenWidth-1, (screenHeight-1)/10);
            g.setFont( new Font("Ink Free",Font.BOLD, 70));
            FontMetrics m = getFontMetrics(g.getFont());
            g.drawString("Gra Snake", (screenWidth - m.stringWidth("Gra Snake"))/2, g.getFont().getSize());

            g.setFont( new Font("Serif",Font.BOLD, 70));
            FontMetrics m1 = getFontMetrics(g.getFont());
            g.drawString("Menu", (screenWidth - m1.stringWidth("Menu"))/2, 300);

            g.drawRect(200, 350, 600, 400);
            g.setColor(new Color(45, 106, 79));
            g.fillRect(200, 350, 600, 400);

            snake = new ImageIcon("snake.png");
            snake.paintIcon(this, g, -400, 50);
        }

        if(play) {

            for(int i = 0; i < screenHeight / pixel; i++) {
                g.drawLine(i * pixel, 0, i * pixel, screenHeight);
                g.drawLine(0, i* pixel, screenWidth, i* pixel);
            }

            g.setColor(new Color(45, 100, 4));
            g.fillRect(foodx, foody, pixel, pixel);

            int b = 4;
            for(int i = 0; i < snakeLength; i++) {
                if(i == 0) {
                    g.setColor(new Color(149, 213, 178));
                    g.fillRoundRect(x[i], y[i], pixel, pixel, 10, 10);
                    g.setColor(Color.BLACK);
                    g.fillOval(x[i]+10, y[i]+10, 10, 10);
                }
                else {
                        g.setColor(new Color(45, 100, b));
                        g.fillRect(x[i], y[i], pixel, pixel);
                        if(b<245) b += 5;
                }
            }

            g.setColor(Color.white);
            g.setFont( new Font("Serif", Font.BOLD, 40));
            g.drawString(String.valueOf(score), (screenWidth - getFontMetrics(g.getFont()).stringWidth(String.valueOf(score))), g.getFont().getSize());
        }

        else if(gameEnded) gameOver(g);

    }
    public void food(){
        foodx = random.nextInt((screenWidth / pixel))* pixel;
        foody = random.nextInt((screenHeight / pixel))* pixel;
    }

    public void checkApple() {
        if((x[0] == foodx) && (y[0] == foody)) {
            score++;
            snakeLength++;
            food();
        }
    }
    public void endGame() {

        gameEnded = true;

        for(int i = snakeLength; i>0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                play = false;
                break;
            }
        }

        if(x[0] < 0 || y[0] < 0 || x[0] > screenWidth-pixel || y[0] > screenHeight-pixel) play = false;

        if(!play) timer.stop();
    }

    public void gameOver(Graphics g) {

        snake = new ImageIcon("snake.png");
        snake.paintIcon(this, g, -400, 0);

        snake = new ImageIcon("snake.png");
        snake.paintIcon(this, g, -400, -400);

        snake = new ImageIcon("snake.png");
        snake.paintIcon(this, g, -400, -800);

        g.drawRect(200, 350, 600, 400);
        g.setColor(new Color(45, 106, 79));
        g.fillRect(200, 350, 600, 400);

        g.setColor(new Color(149, 213, 178));
        g.setFont( new Font("Serif",Font.BOLD, 60));
        g.drawString("Koniec gry!", (screenWidth - getFontMetrics(g.getFont()).stringWidth("Koniec gry!"))/2, screenHeight /2);

        g.setColor(new Color(149, 213, 178));
        g.setFont( new Font("Serif",Font.BOLD, 50));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Twoj wynik: "+ score, (screenWidth - metrics1.stringWidth("Twoj wynik: "+ score))/2, screenHeight/2 + 100);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(play) {
            move();
            checkApple();
            endGame();
        }
        repaint();
    }
}
