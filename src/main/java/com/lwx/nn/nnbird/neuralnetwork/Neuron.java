package com.lwx.nn.nnbird.neuralnetwork;

import com.lwx.nn.nnbird.BirdGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Neuron {
    private int inputNum;
    private HashMap<Integer, DNA> dna;

    public Neuron(int inputNum) {
        this.inputNum = inputNum;

        dna = new HashMap<>();

        //inputNum + 1 means bias, use type to distinction
        for (int i = 0; i < inputNum + 1; i++) {
            ArrayList<Integer> dnaChain = new ArrayList<>();
            for (int j = 0; j < BirdGroup.DNA_SIZE; j++) {
                dnaChain.add((Math.random() > 0.5) ? 0 : 1);
            }
            dna.put(i, new DNA(dnaChain));
        }
    }

    public double getOutput(Vector<Double> inputs) {
        double mulResult = 0;
        if (inputs.size() != inputNum)
            return 0;

        //mul x and w
        for (int i = 0; i < inputNum; i++) {
            mulResult += inputs.get(i) * dna.get(i).translate();
        }

        //add bias and use tanh
        return tanh(mulResult + inputNum * dna.get(inputNum).translate(1));
    }

    private double tanh(double input) {
        return Math.tanh(input);
    }

    private double ReLu(double input) {
        return Math.max(0, input);
    }
}
