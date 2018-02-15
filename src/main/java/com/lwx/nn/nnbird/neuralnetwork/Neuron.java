package com.lwx.nn.nnbird.neuralnetwork;

import com.lwx.nn.nnbird.BirdGroup;

import java.util.HashMap;
import java.util.Vector;

public class Neuron {
    private Vector<Double> w;
    private double bias;

    private int inputNum;
    private HashMap<Integer, Vector<Integer>> DNA;

    public Neuron(int inputNum) {
        w = new Vector<>();
        this.inputNum = inputNum;

        DNA = new HashMap<>();
        //inputNum + 1 means bias
        for (int i = 0; i < inputNum + 1; i++) {
            Vector<Integer> dnaChain = new Vector<>();
            for (int j = 0; j < BirdGroup.DNA_SIZE; j++) {
                dnaChain.add((Math.random() > 0.5) ? 0 : 1);
            }
            DNA.put(i, dnaChain);
        }

        for (int i = 0; i < inputNum; i++) {
            w.add((Math.random() > 0.5) ? Math.random() * BirdGroup.maxW : Math.random() * BirdGroup.minW);
        }
        bias = (Math.random() > 0.5) ? Math.random() * BirdGroup.maxBias : Math.random() * BirdGroup.minBias;

    }

//    public Vector<Vector<Integer>> translateDNA(){
//        for (int i = 0; i < w.size(); i++) {
//            Vector<Integer> dnaChain = new Vector<>(BirdGroup.DNA_SIZE);
//
//        }
//    }

//    public double getOutput(double input_1,double input_2){
//        double mulResult = input_1*w1 + input_2*w2 + bias;
//        //double result = Math.tanh(mulResult);//tanh
//        double result = 1/(1+Math.exp(-mulResult));//sigmoid
//        //double result = max(0,mulResult);//ReLU
//        return result;
//    }

    public double getOutput(Vector<Double> inputs) {
        double mulResult = 0;
        if (inputs.size() != inputNum)
            return 0;
        for (int i = 0; i < inputNum; i++) {
            mulResult += inputs.get(i) * w.get(i);
        }
        return tanh(mulResult);
    }

    public Vector<Double> getW() {
        return w;
    }

    public int getInputNum() {
        return inputNum;
    }

    public double getBias() {
        return bias;
    }

    public void setW(Vector<Double> w) {
        this.w = w;
    }

    public void setW(int index, double w) {
        if (index < this.w.size()) {
            this.w.set(index, w);
        }
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    private double tanh(double input) {
        return Math.tanh(input);
    }

    private double sigmoid(double input) {
        return 1 / (1 + Math.exp(-input - bias));
    }

    private double ReLu(double input) {
        return Math.max(0, input);
    }
}
