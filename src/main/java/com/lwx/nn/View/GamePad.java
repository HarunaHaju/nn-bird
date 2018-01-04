package com.lwx.nn.view;

import com.lwx.nn.nnbird.BirdGroup;
import com.lwx.nn.GameObjects.Column;
import com.lwx.nn.GameObjects.Ground;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePad extends JPanel {

    private BufferedImage imageBackGround = null;
    private Ground ground;
    private Column column0;
    private Column column1;

    private Font font;

    private BirdGroup birds;
    private int gen;//鸟的代数
    private int distance;//最长距离
    private int birdNum;
    private int bestDis;
    private int bestGen;

    public GamePad(){
        birdNum = 10;
        this.ground = new Ground();
        gen = 1;
        bestGen = 1;
        bestDis = 0;
        distance = 0;
        font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
        column0 = new Column(0);
        column1 = new Column(1);
        birds = new BirdGroup(birdNum);
        try {
            imageBackGround = ImageIO.read(GamePad.class.getResource("/bg.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setSize(432,644);
        this.setBackground(Color.blue);
    }

    @Override
    public void paint(Graphics graphics) {

        super.paint(graphics);

        graphics.drawImage(imageBackGround, 0, 0, null);

        graphics.drawImage(column0.image, column0.getPositionX() - column0.getWidth() / 2
                , column0.getPositionY() - column0.getHeight() / 2, null);//绘制水管0

        graphics.drawImage(column1.image, column1.getPositionX() - column1.getWidth() / 2
                , column1.getPositionY() - column1.getHeight() / 2, null);//绘制水管1


        for (int i = 0; i < birdNum; i++) {
            if(birds.getBirds().get(i).isAlive()) {
                graphics.drawImage(birds.getBirds().get(i).image,
                        birds.getBirds().get(i).getPositionX() - birds.getBirds().get(i).getWidth() / 2
                        , birds.getBirds().get(i).getPositionY() - birds.getBirds().get(i).getHeight() / 2, null);
            }
        }

        graphics.drawImage(ground.image, ground.getPositionX()
                , ground.getPositionY(), null);//绘制草坪

        graphics.setFont(this.font);
        graphics.drawString("Gen:"+gen,0,20);
        graphics.drawString("Dis:"+distance,0,40);
        graphics.drawString("BestGen:"+bestGen,0,60);
        graphics.drawString("BestDis:"+bestDis,0,80);
    }

    public void logicStep(){
        distance++;
        ground.step();
        column0.move();
        column1.move();
        birds.step(column0,column1,distance);

        if (column0.getPositionX()>260&&column0.getPositionX()<=460){
            birds.birdsFly(column0.getPositionX()-215,column0.getPositionY());
        }else if(column1.getPositionX()>260&&column1.getPositionX()<=460){
            birds.birdsFly(column1.getPositionX()-215,column1.getPositionY());
        }else{
            birds.birdsFly();
        }
//        if (column0.getPositionX()>260&&column0.getPositionX()<=460){
//            birds.birdsFly(column0.getPositionX()-215,column0.getPositionY());
//        }else{
//            birds.birdsFly(column1.getPositionX()-215,column1.getPositionY());
//        }

        if (birds.isAllDie()){
            if(distance>=bestDis)
            {
                bestDis = distance;
                bestGen = gen;
            }
            gen++;
            distance = 0;
            column0.reset();
            column1.reset();
        }
    }
    public void resetAll(){
        distance = 0;
        bestDis = 0;
        bestGen = 1;
        gen = 1;
        column0.reset();
        column1.reset();
        birds.reset();
    }
}
