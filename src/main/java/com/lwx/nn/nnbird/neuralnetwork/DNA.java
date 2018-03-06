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

        return 0;
    }

    public void mutate() {
        chain.forEach(gene -> {
            gene = (Math.random() > BirdGroup.CROSSOVER_RATE) ? gene : 1 - gene;
        });
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
}
