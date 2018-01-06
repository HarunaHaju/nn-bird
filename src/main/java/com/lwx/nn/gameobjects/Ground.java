package com.lwx.nn.gameobjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Ground {
    public BufferedImage image = null;
    private int positionX;
    private int positionY;
    private int height = 0;
    private int width = 0;
    public Ground(){
        try {
            image = ImageIO.read(Ground.class.getResource("/Image/ground.png"));
            height = image.getHeight();
            width = image.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        positionX = 0;
        positionY = 500;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void step(){
        positionX -= 3;
        if(positionX == -108){
            positionX = 0;
        }
    }
}
