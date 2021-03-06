package com.lwx.nn.nnbird.neuralnetwork;

import com.lwx.nn.nnbird.BirdGroup;

import java.util.ArrayList;

public class DNA {
    private ArrayList<Integer> chain;

    public DNA(ArrayList<Integer> chain) {
        if (chain.size() == BirdGroup.DNA_SIZE)
            this.chain = chain;
        else
            this.chain = new ArrayList<>();
    }

    public double translate() {
        return this.translate(0);
    }

    public double translate(int type) {
        double max = type == 0 ? BirdGroup.maxW : BirdGroup.maxBias;
        double min = type == 0 ? BirdGroup.minW : BirdGroup.minBias;
        double result = 0;

        for (int i = 0; i < BirdGroup.DNA_SIZE; i++) {
            result += chain.get(i) * Math.pow(2, BirdGroup.DNA_SIZE - 1 - i);
        }

        result = result / Math.pow(2, BirdGroup.DNA_SIZE) * (max - min) + min;
        return result;
    }

    public void mutate() {
        chain.forEach(gene -> {
            gene = (Math.random() > BirdGroup.MUTATE_RATE) ? gene : 1 - gene;
        });
    }

    public void crossover(DNA dna) {
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < BirdGroup.DNA_SIZE; i++) {
            numberList.add(i);
        }
        for (int i = 0; i < BirdGroup.DNA_SIZE / 2; i++) {
            int index = numberList.get((int) (Math.random() * numberList.size()));
            this.setChain(index, dna.getChain().get(index));
            numberList.remove((Integer) index);
        }
    }

    public ArrayList<Integer> getChain() {
        return chain;
    }

    public void setChain(ArrayList<Integer> chain) {
        if (chain.size() == BirdGroup.DNA_SIZE)
            this.chain = chain;
    }

    public void setChain(int index, int value) {
        if (index < chain.size() && ((value == 1) || (value == 0)))
            chain.set(index, value);
    }

    @Override
    public int hashCode() {
        return chain.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "DNA{" +
                "chain=" + chain +
                '}' + "w:" + translate(0) +" b:"+translate(1);
    }
}
