package com.lwx.nn.nnbird;

import com.lwx.nn.GameObjects.Column;

import java.util.*;

public class BirdGroup {
    Vector<NeuralNetworkBird> birds;
    private int gen;//代数
    private int birdNum;

    private static Comparator<Map.Entry<Integer, Integer>> comparator
            = new Comparator<Map.Entry<Integer, Integer>>() {
        @Override
        public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
            return o2.getValue()-o1.getValue();
        }
    };

    public BirdGroup(int birdNum){
        this.birdNum = birdNum;
        gen = 1;
        birds = new Vector<NeuralNetworkBird>();

        for (int i = 0; i < birdNum; i++) {
            NeuralNetworkBird bird = new NeuralNetworkBird(i);
            birds.add(bird);
        }
    }

    public Vector<NeuralNetworkBird> getBirds() {
        return this.birds;
    }

    public void step(Column column0, Column column1, int maxDistance){
        for (int i = 0; i < birdNum; i++) {
            if (birds.get(i).isAlive()){
                birds.get(i).setMaxDistance(maxDistance);
                birds.get(i).hit();
                birds.get(i).hit(column0);
                birds.get(i).hit(column1);
                birds.get(i).step();
                birds.get(i).fly();
            }
        }
    }

    public boolean isAllDie(){
        boolean isAllDie = true;
        for (int i = 0; i < birdNum; i++) {
            if (birds.get(i).isAlive()){
                isAllDie = false;
                break;
            }
        }
        if (isAllDie){
            evolve();
        }
        return isAllDie;
    }

    public void birdsFly(double distance,double height){
        for (int i = 0; i < birdNum; i++) {
            if (birds.get(i).isAlive() && birds.get(i).canFly(distance,birds.get(i).getPositionY()-height)){
                birds.get(i).flyUp();
            }
        }
    }

    public void birdsFly(){
        for (int i = 0; i < birdNum; i++) {
            if (birds.get(i).isAlive() && birds.get(i).canFly(0,320)){
                birds.get(i).flyUp();
            }
        }
    }

    public void reset(){
        for (int i = 0; i < birdNum; i++) {
            birds.get(i).resetAll();
        }
    }

    private void evolve(){
        Map<Integer,Integer> evolveMap = new HashMap<>();
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>();
        for (int i = 0; i < birdNum; i++) {
            evolveMap.put(i,birds.get(i).getMaxDistance());//index,dis
            birds.get(i).reset();
        }
        for (Map.Entry<Integer,Integer> entry:evolveMap.entrySet()){
            list.add(entry);
        }
        list.sort(comparator);

        //↓恶心。
//        if(gen > 100 && birds.get(list.get(0).getKey()).getMaxDistance()
//                - birds.get(list.get(4).getKey()).getMaxDistance() <= 100)
        if(gen > 10 &&birds.get(list.get(0).getKey()).getMaxDistance()<=70)
        {
            evolveMap.clear();
            for (int i = 0; i < birdNum; i++) {
                evolveMap.put(i , 100 - birds.get(i).getLastRank());
            }
            for (Map.Entry<Integer,Integer> entry : evolveMap.entrySet()){
                list.add(entry);
            }
            list.sort(comparator);
        }
        else{
            for (int i = 0; i < birdNum; i++) {
                System.out.println(list.get(i).getKey() + " "+birds.get(list.get(i).getKey()).getMaxDistance());
                birds.get(list.get(i).getKey()).setLastRank(i);
            }
            System.out.println("*******");
        }

        //最优秀4个直接进入下一代
        //选2个通过最优交叉
        birds.get(list.get(4).getKey()).crossover(birds.get(list.get(0).getKey())
                ,birds.get(list.get(1).getKey()));
        birds.get(list.get(5).getKey()).crossover(birds.get(list.get(0).getKey())
                ,birds.get(list.get(1).getKey()));

        //选4个，通过随机两个优秀的进行遗传
        for (int i = 6; i < 10; i++) {
            birds.get(list.get(i).getKey()).crossover(birds.get(list.get((int)(Math.random()*5)).getKey())
                    ,birds.get(list.get((int)(Math.random()*5)).getKey()));
        }

        //随机变异
        for (int i = 0; i < 10; i++) {
            birds.get(list.get(i).getKey()).variate(i / 3);
        }
    }

}
