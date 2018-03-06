package com.lwx.nn.nnbird.neuralnetwork;

import java.util.ArrayList;

public class DNA {
    private static final int DNA_SIZE = 20;
    private ArrayList<Integer> chain;

    public DNA(ArrayList<Integer> chain) {
        if (chain.size() == DNA_SIZE)
            this.chain = chain;
        else
            this.chain = new ArrayList<>();
    }

    public ArrayList<Integer> getChain() {
        return chain;
    }

    public void setChain(ArrayList<Integer> chain) {
        if (chain.size() == DNA_SIZE)
            this.chain = chain;
    }

    public void setChain(int index, int value) {
        if (index < chain.size() && ((value == 1) || (value == 0)))
            chain.set(index, value);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
