package com.lwx.nn.gameobjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Column {
    public BufferedImage image = null;
    private int positionX;
    private int positionY;
    private int width;
    private int height;
    private int gap;//柱子之间的缝隙
    private int distance;//相邻两根柱子之间的间距
    private int id;//柱子的编号，0或者1

    public Column(int id) {
        try {
            image = ImageIO.read(Column.class.getResource("/Image/column3.png"));
            height = image.getHeight();
            width = image.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.id = id;
        gap = 200;
        distance = 280;
        positionX = 440 + id * distance;
        positionY = (int) (Math.random() * 150) + 150;
    }

    public void move(){
        positionX-=3;
        if(positionX <= - (width/2)){
            positionX = 490;
            positionY = (int) (Math.random() * 150) + 150;
        }
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void reset(){
        positionX = 470 + id * distance;
        positionY = (int) (Math.random() * 150) + 150;
    }
}
