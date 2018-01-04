package com.lwx.nn.nnbird;

import com.lwx.nn.GameObjects.Column;
import com.lwx.nn.nnbird.NeuralNetwork.Network;
import com.lwx.nn.nnbird.NeuralNetwork.Neuron;
import com.lwx.nn.view.GamePad;

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

    public NeuralNetworkBird(int index){
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
            image = ImageIO.read(GamePad.class.getResource("/Image/0.png"));
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
                images[i] = ImageIO.read(GamePad.class.getResource("/Image/" + i + ".png"));
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
    public void resetAll(){
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

    public boolean canFly(double distance,double height){
        boolean is = false;
        if(network.getOutput(distance,positionY - height)<=0) is = true;
        return is;
    }

    public void flyUp() {
        speed = v0;
    }

    public boolean hit(){
        if (positionY <= 15 || positionY >= 478) {
            isAlive = false;
        }//判断是否撞到上面和下面
        return isAlive;
    }
    public boolean hit(Column column){
        if(positionX+width/2 >column.getPositionX() - column.getWidth()/2
                && positionX-width/2 <column.getPositionX() + column.getWidth()/2){
            if(positionY - height/2 < column.getPositionY() - column.getGap()/2||
                    positionY + height/2 > column.getPositionY() + column.getGap()/2){
                isAlive = false;
            }
        }
        return isAlive;
    }

    //遗传交叉，随机选择目标的三个染色体进行更新
    public void crossover(NeuralNetworkBird bird0,NeuralNetworkBird bird1) {
//        double distanceCount = bird0.getMaxDistance()+bird1.getMaxDistance();
//        double rate = bird0.getMaxDistance() / distanceCount;
//        for (int i = 0; i < 10; i++) {
//            this.network.setNeural(i,
//                    bird0.getNetwork().getNeural(i).getW() * rate + bird1.getNetwork().getNeural(i).getW() * (1 - rate)
//                    , bird0.getNetwork().getNeural(i).getBias() * rate + bird1.getNetwork().getNeural(i).getBias() * (1 - rate));
//        }
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < this.network.getNeuronNum(); i++) {
            numberList.add(i);
        }
        for (int i = 0; i < 5; i++) {
            int index = numberList.get((int) (Math.random() * numberList.size()));
            this.network.setNeuron(index,bird0.getNetwork().getNeuron(index).getW()
                    ,bird0.getNetwork().getNeuron(index).getBias());
            numberList.remove((Integer) index);
        }
        for (Integer i:numberList){
            this.network.setNeuron(i,bird1.getNetwork().getNeuron(i).getW()
                    ,bird1.getNetwork().getNeuron(i).getBias());
        }
    }

    //部分变异
    public void variate(int n){
        ArrayList<Integer> numberList = new ArrayList<>();
        Vector<Double> w = new Vector<>();
        for (int i = 0; i < this.network.getNeuronNum(); i++) {
            numberList.add(i);
        }
        for (int i = 0; i < n; i++) {
            w.clear();
            int index = numberList.get((int) (Math.random() * numberList.size()));
            if(index >= network.getLayer0NeuronNum()){
                for (int j = 0;j<network.getLayer1().getNeuronNum();j++){
                    w.add((Math.random()>0.5)?Math.random()* Neuron.maxW:-Math.random()* Neuron.maxW);
                }
            }else{
                for (int j = 0;j<network.getLayer0().getNeuronNum();j++){
                    w.add((Math.random()>0.5)?Math.random()* Neuron.maxW:-Math.random()* Neuron.maxW);
                }
            }
            double bias = (Math.random()>0.5)?Math.random()* Neuron.maxBias:-Math.random()* Neuron.maxBias;
            this.network.setNeuron(index,w,bias);
            numberList.remove((Integer)index);
        }
    }

    //带变异最优遗传
    public void variate(Network network){
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < this.network.getNeuronNum(); i++) {
            numberList.add(i);
        }
        for (int i = 0; i < 8; i++) {
            int index = numberList.get((int) (Math.random() * numberList.size()));
            this.network.setNeuron(index, network.getNeuron(index).getW(),network.getNeuron(index).getBias());
            numberList.remove((Integer)index);
        }
    }
}