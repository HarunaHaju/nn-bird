package com.lwx.nn.nnbird;

import com.lwx.nn.gameobjects.Column;

import java.util.*;

public class BirdGroup {
    private Vector<NeuralNetworkBird> birds;
    private int gen;//代数
    private int birdNum;

    public static final double MUTATE_RATE = 0.01;
    public static final double CROSSOVER_RATE = 0.8;
    public static final int DNA_SIZE = 32;
    public static final double minW = -1;
    public static final double maxW = 1;
    public static final double minBias = -0.01;
    public static final double maxBias = 0.01;

    private static Comparator<Map.Entry<Integer, Integer>> comparator
            = (Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) -> o2.getValue() - o1.getValue();

    public BirdGroup(int birdNum) {
        this.birdNum = birdNum;
        gen = 1;
        birds = new Vector<>();

        for (int i = 0; i < birdNum; i++) {
            NeuralNetworkBird bird = new NeuralNetworkBird(i);
            birds.add(bird);
        }
    }

    public Vector<NeuralNetworkBird> getBirds() {
        return this.birds;
    }

    public void step(Column column0, Column column1, int maxDistance) {
        for (int i = 0; i < birdNum; i++) {
            if (birds.get(i).isAlive()) {
                birds.get(i).setMaxDistance(maxDistance);
                birds.get(i).hit();
                birds.get(i).hit(column0);
                birds.get(i).hit(column1);
                birds.get(i).step();
                birds.get(i).fly();
            }
        }
    }

    public boolean isAllDie() {
        boolean isAllDie = true;
        for (int i = 0; i < birdNum; i++) {
            if (birds.get(i).isAlive()) {
                isAllDie = false;
                break;
            }
        }
        if (isAllDie) {
            evolve();
        }
        return isAllDie;
    }

    public void birdsFly(double distance, double height) {
        for (int i = 0; i < birdNum; i++) {
            if (birds.get(i).isAlive() && birds.get(i).canFly(distance, birds.get(i).getPositionY() - height)) {
                birds.get(i).flyUp();
            }
        }
    }

    public void birdsFly() {
        for (int i = 0; i < birdNum; i++) {
            if (birds.get(i).isAlive() && birds.get(i).canFly(0, 320)) {
                birds.get(i).flyUp();
            }
        }
    }

    public void reset() {
        for (int i = 0; i < birdNum; i++) {
            birds.get(i).resetAll();
        }
    }

    private void evolve() {
        Map<Integer, Integer> evolveMap = new HashMap<>();
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>();

        for (int i = 0; i < birdNum; i++) {
            evolveMap.put(i, birds.get(i).getMaxDistance());//index,dis
            birds.get(i).reset();
        }
        list.addAll(evolveMap.entrySet());
        list.sort(comparator);

        if (gen > 10 && birds.get(list.get(0).getKey()).getMaxDistance() <= 70) {
            evolveMap.clear();
            for (int i = 0; i < birdNum; i++) {
                evolveMap.put(i, 100 - birds.get(i).getLastRank());
            }
            list.addAll(evolveMap.entrySet());
            list.sort(comparator);
        } else {
            for (int i = 0; i < birdNum; i++) {
                birds.get(list.get(i).getKey()).setLastRank(i);
            }
        }

        //crossover
        birds.forEach(bird -> {
            bird.crossover(birds.get(list.get(0).getKey()));
        });

        //mutate
        birds.forEach(NeuralNetworkBird::mutate);
    }

}
