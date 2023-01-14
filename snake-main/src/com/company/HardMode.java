package com.company;

import javax.swing.*;
import java.awt.*;

public class HardMode extends EasyMode {

    static final int DELAY = 50;
    Timer timer = new Timer(DELAY,this);
    int poisonx;
    int poisony;

    public void start() {
        poison();
        food();
        play = true;
        timer.start();
    }

    public void draw(Graphics g) {

        if(play) {

            Color colour = new Color(random.nextInt(100), random.nextInt(100), random.nextInt(100));

            g.setColor(Color.red);
            g.fillRect(poisonx, poisony, pixel, pixel);

            g.setColor(colour);
            g.fillRect(foodx, foody, pixel, pixel);

            for(int i = 0; i< snakeLength; i++) {
                    g.setColor(colour);
                    g.fillRect(x[i], y[i], pixel, pixel);
            }
            g.setColor(Color.white);
            g.setFont( new Font("Serif", Font.BOLD, 40));
            g.drawString(String.valueOf(score), (screenWidth - getFontMetrics(g.getFont()).stringWidth(String.valueOf(score))), g.getFont().getSize());

        }
        else {
            if(gameEnded) gameOver(g);
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

        if(((x[0] == poisonx) && (y[0] == poisony)) || x[0] < 0 || y[0] < 0 || x[0] > screenWidth-pixel || y[0] > screenHeight-pixel) play = false;

        if(!play) timer.stop();
    }

    public void poison(){
        poisonx = random.nextInt((screenWidth / pixel))* pixel;
        poisony = random.nextInt((screenHeight / pixel))* pixel;
    }

    public void food(){
        foodx = random.nextInt((screenWidth / pixel))* pixel;
        foody = random.nextInt((screenHeight / pixel))* pixel;
        poison();
    }
}
