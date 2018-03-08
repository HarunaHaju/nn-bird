package com.lwx.nn.nnbird;

import com.lwx.nn.gameobjects.Column;
import com.lwx.nn.nnbird.neuralnetwork.Network;
import com.lwx.nn.nnbird.neuralnetwork.Neuron;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

public class NeuralNetworkBird {
    private int index;
    private Network network;

    private boolean isAlive;
    private int maxDistance;
    private int lastRank;//上次的排名，防止训练失效

    public BufferedImage image = null;//图片
    private int positionX = 0;//X坐标
    private int positionY = 0;//Y坐标
    private int width = 0;//宽度
    private int height = 0;//高度
    public int size = 0;//大小

    private BufferedImage[] images;//图片数组容器
    private int imageIndex = 0;//图片下标
    private int indexHelp = 0;
    private int flySpeed;

    //重力加速度
    private double g = 0;
    private double t = 0;
    private double v0 = 0;
    private double speed = 0;
    private double s = 0;

    public NeuralNetworkBird(int index) {
        isAlive = true;
        network = new Network();
        this.index = index;
        lastRank = index;
        g = 5;
        v0 = 16;
        t = 0.25;
        speed = v0;
        s = 0;
        try {
            image = ImageIO.read(NeuralNetworkBird.class.getResource("/Image/0.png"));
            width = image.getWidth();
            height = image.getHeight();
            positionX = 215;
            positionY = 240;
            size = 40;
            images = new BufferedImage[8];
            imageIndex = 0;
            indexHelp = 0;
            flySpeed = 5;
            for (int i = 0; i < 8; i++) {
                images[i] = ImageIO.read(NeuralNetworkBird.class.getResource("/Image/" + i + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public int getIndex() {
        return index;
    }

    public Network getNetwork() {
        return network;
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

    public BufferedImage[] getImages() {
        return images;
    }

    public void setImages(BufferedImage[] images) {
        this.images = images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getLastRank() {
        return lastRank;
    }

    public void setLastRank(int lastRank) {
        this.lastRank = lastRank;
    }

    public void fly() {
        indexHelp++;
        imageIndex += indexHelp / flySpeed;
        indexHelp %= flySpeed;
        imageIndex %= 8;
        this.image = this.images[imageIndex];
    }

    public void reset() {
        positionX = 215;
        positionY = 240;
        speed = v0;
        t = 0.25;
        isAlive = true;
    }

    public void resetAll() {
        positionX = 215;
        positionY = 240;
        speed = v0;
        t = 0.25;
        network = new Network();
        isAlive = true;
    }

    public void step() {
        double v0 = speed;
        s = v0 * t - g * t * t / 2;
        positionY = positionY - (int) s;
        speed = v0 - g * t;//移动后的速度
    }

    public boolean canFly(double distance, double height) {
        boolean is = false;
        if (network.getOutput(distance, positionY - height) <= 0) is = true;
        return is;
    }

    public void flyUp() {
        speed = v0;
    }

    public boolean hit() {
        if (positionY <= 15 || positionY >= 478) {
            isAlive = false;
        }//判断是否撞到上面和下面
        return isAlive;
    }

    public boolean hit(Column column) {
        if (positionX + width / 2 > column.getPositionX() - column.getWidth() / 2
                && positionX - width / 2 < column.getPositionX() + column.getWidth() / 2) {
            if (positionY - height / 2 < column.getPositionY() - column.getGap() / 2 ||
                    positionY + height / 2 > column.getPositionY() + column.getGap() / 2) {
                isAlive = false;
            }
        }
        return isAlive;
    }

    //遗传交叉，随机选择目标的n个染色体进行更新
    public void crossover(NeuralNetworkBird bird) {
        bird.network.crossover(bird.getNetwork());
    }

    public void mutate() {

    }
}