package com.lwx.nn.nnbird.NeuralNetwork;

import java.util.Vector;

public class Neuron {
    private Vector<Double> w;
    private double bias;

    private int inputNum;

    public static double maxW = 1;
    public static double maxBias = 0.5;

    public Neuron(int inputNum) {
        w = new Vector<>();
        this.inputNum = inputNum;
        for (int i = 0;i<inputNum;i++){
            w.add((Math.random()>0.5)?Math.random()*maxW:-Math.random()*1);
        }
        bias = (Math.random()>0.5)?Math.random()*maxBias:-Math.random()*0.1;
    }

//    public double getOutput(double input_1,double input_2){
//        double mulResult = input_1*w1 + input_2*w2 + bias;
//        //double result = Math.tanh(mulResult);//tanh
//        double result = 1/(1+Math.exp(-mulResult));//sigmoid
//        //double result = max(0,mulResult);//ReLU
//        return result;
//    }

    public double getOutput(Vector<Double> inputs){
        double mulResult = 0;
        if(inputs.size() != inputNum)
            return 0;
        for (int i = 0;i<inputNum;i++){
            mulResult += inputs.get(i) * w.get(i);
        }
        double result = Math.tanh(mulResult);//tanh
        //double result = 1/(1+Math.exp(-mulResult-bias));//sigmoid
        //double result = max(0,mulResult);//ReLU
        return result;
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

    public void setW(int index,double w){
        if(index < this.w.size()){
            this.w.set(index,w);
        }
    }

    public void setBias(double bias) {
        this.bias = bias;
    }
}
